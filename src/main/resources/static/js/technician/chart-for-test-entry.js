
const metricDefinitions = [
    { label: "Battery Voltage", field: "batteryVoltage", color: "#1D4ED8" },
    { label: "Battery Current", field: "batteryCurrent", color: "#F97316" },
    { label: "Battery Capacity", field: "batteryCapacity", color: "#8B5CF6" },
    { label: "Battery Temp (°C)", field: "batteryTemperatureCelsius", color: "#DC2626" },
    { label: "Charge Status", field: "chargeStatus", color: "#EA580C" },
    { label: "Assistance Level", field: "assistanceLevel", color: "#9333EA" },
    { label: "Torque Crank (Nm)", field: "torqueCrankNm", color: "#BCBD22" },
    { label: "Wheel Speed (km/h)", field: "bikeWheelSpeedKmh", color: "#059669" },
    { label: "Cadance RPM", field: "cadanceRpm", color: "#D97706" },
    { label: "Engine RPM", field: "engineRpm", color: "#0EA5E9" },
    { label: "Engine Power (W)", field: "enginePowerWatt", color: "#16A34A" },
    { label: "Wheel Power (W)", field: "wheelPowerWatt", color: "#10B981" },
    { label: "Roll Torque", field: "rollTorque", color: "#2563EB" },
    { label: "Loadcell (N)", field: "loadcellN", color: "#DB2777" },
    { label: "ROL (Hz)", field: "rolHz", color: "#6366F1" },
    { label: "Inclination Horizontal", field: "horizontalInclination", color: "#FF9896" },
    { label: "Inclination Vertical", field: "verticalInclination", color: "#8C564B" },
    { label: "Load Power", field: "loadPower", color: "#374151" },
    { label: "Plug Status", field: "statusPlug", color: "#14B8A6" }
];

const defaultCheckedFields = [
    "batteryVoltage",
    "batteryCurrent",
    "bikeWheelSpeedKmh",
    "enginePowerWatt"
];

let selectedFields = [...defaultCheckedFields];
let chartInstance = null;
let isNormalized = false;

const testId = window.location.pathname.split('/').filter(Boolean).pop();
const metricTogglesContainer = document.getElementById("metricToggles");

// Checkbox rendering
function renderMetricCheckboxes() {
    metricTogglesContainer.innerHTML = "";

    metricDefinitions.forEach(metric => {
        const wrapper = document.createElement("div");
        wrapper.className = "form-check form-check-inline";

        const checkbox = document.createElement("input");
        checkbox.className = "form-check-input";
        checkbox.type = "checkbox";
        checkbox.id = `chk-${metric.field}`;
        checkbox.checked = defaultCheckedFields.includes(metric.field);
        checkbox.dataset.field = metric.field;

        const label = document.createElement("label");
        label.className = "form-check-label fw-semibold";
        label.htmlFor = checkbox.id;
        label.style.color = metric.color;
        label.textContent = metric.label;

        wrapper.appendChild(checkbox);
        wrapper.appendChild(label);
        metricTogglesContainer.appendChild(wrapper);

        checkbox.addEventListener("change", () => {
            if (checkbox.checked) {
                if (!selectedFields.includes(metric.field)) {
                    selectedFields.push(metric.field);
                }
            } else {
                selectedFields = selectedFields.filter(f => f !== metric.field);
            }
            updateChart();
        });
    });
}

async function fetchData(normalized) {
    try {
        const url = normalized
            ? `/api/technician/normalized-test-report-entries/${testId}`
            : `/api/technician/test-report-entries/${testId}`;
        const response = await fetch(url);
        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
        return await response.json();
    } catch (error) {
        console.error("Error fetching data:", error);
        alert("Failed to load data. Please try again.");
        return [];
    }
}

function resampleData(data, intervalSeconds) {
    if (intervalSeconds <= 1) return data;

    const resampled = [];
    let bucket = [];
    let lastTime = null;

    for (const point of data) {
        const currentTime = new Date(point.timestamp).getTime();
        if (lastTime === null) lastTime = currentTime;

        if ((currentTime - lastTime) / 1000 >= intervalSeconds) {
            if (bucket.length > 0) {
                resampled.push(averageBucket(bucket));
            }
            bucket = [point];
            lastTime = currentTime;
        } else {
            bucket.push(point);
        }
    }

    if (bucket.length > 0) {
        resampled.push(averageBucket(bucket));
    }

    return resampled;
}

function averageBucket(bucket) {
    const avg = {};
    const fields = Object.keys(bucket[0]).filter(k => k !== "timestamp");
    avg.timestamp = bucket[0].timestamp;

    for (const field of fields) {
        const sum = bucket.reduce((acc, item) => acc + (item[field] ?? 0), 0);
        avg[field] = sum / bucket.length;
    }
    return avg;
}

function createChart(data, selectedFields) {
    if (!data || data.length === 0) return;

    const startTime = new Date(data[0].timestamp).getTime();
    const labels = data.map(e => {
        const secondsElapsed = Math.round((new Date(e.timestamp).getTime() - startTime) / 1000);
        if (secondsElapsed < 60) return `${secondsElapsed}s`;
        const minutes = Math.floor(secondsElapsed / 60);
        const seconds = secondsElapsed % 60;
        return seconds === 0 ? `${minutes}m` : `${minutes}m ${seconds}s`;
    });

    const datasets = metricDefinitions
        .filter(metric => selectedFields.includes(metric.field))
        .map(metric => ({
            label: metric.label,
            data: data.map(e => e[metric.field] != null ? e[metric.field] : null),
            borderColor: metric.color,
            yAxisID: isNormalized ? 'y' : metric.field,
            fill: false,
            tension: 0.1,
            pointRadius: 3
        }));

    let scales;

    if (isNormalized) {
        scales = {
            x: { title: { display: true, text: "Timestamp" } },
            y: {
                type: 'linear',
                position: 'left',
                min: -1,
                max: 1,
                title: { display: true, text: 'Normalized Value' },
                ticks: { stepSize: 0.2 }
            }
        };
    } else {
        const yAxes = {};
        let left = true;
        datasets.forEach(ds => {
            yAxes[ds.yAxisID] = {
                type: 'linear',
                position: left ? 'left' : 'right',
                display: true,
                title: { display: true, text: ds.label },
                grid: { drawOnChartArea: false }
            };
            left = !left;
        });
        scales = {
            x: { title: { display: true, text: "Elapsed Time" } },
            ...yAxes
        };
    }

    if (chartInstance) chartInstance.destroy();

    chartInstance = new Chart(document.getElementById("metricsChart"), {
        type: 'line',
        data: { labels, datasets },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            interaction: { mode: 'index', intersect: false },
            plugins: {
                legend: { display: false, position: 'bottom', labels: { boxWidth: 10 } }
            },
            scales
        }
    });
}

async function updateChart() {
    const interval = parseInt(document.getElementById("intervalSelector").value);
    let data = await fetchData(isNormalized);
    data = resampleData(data, interval);
    createChart(data, selectedFields);
}

document.getElementById("toggleNormalizationBtn").addEventListener("click", () => {
    isNormalized = !isNormalized;
    document.getElementById("toggleNormalizationBtn").textContent = isNormalized
        ? "Show Raw Data"
        : "Show Normalized Data";
    updateChart();
});

document.getElementById("intervalSelector").addEventListener("change", updateChart);

// Start everything
renderMetricCheckboxes();
updateChart();
