const metricDefinitions = [
    { label: "Battery Voltage", field: "batteryVoltage", color: "#3B82F6" }, // Blue
    { label: "Battery Current", field: "batteryCurrent", color: "#F59E0B" }, // Amber
    { label: "Battery Temp", field: "batteryTemperatureCelsius", color: "#EF4444" }, // Red
    { label: "Wheel Speed", field: "bikeWheelSpeedKmh", color: "#10B981" }, // Emerald
    { label: "Torque Crank", field: "torqueCrankNm", color: "#8B5CF6" }, // Violet
    { label: "Engine RPM", field: "engineRpm", color: "#0EA5E9" }, // Sky
    { label: "Engine Power", field: "enginePowerWatt", color: "#22C55E" }, // Green
    { label: "Loadcell", field: "loadcellN", color: "#EC4899" }, // Pink
    { label: "Load Power", field: "loadPower", color: "#6B7280" }, // Gray
    { label: "Cadance RPM", field: "cadanceRpm", color: "#EAB308" }, // Yellow
    { label: "Plug Status", field: "statusPlug", color: "#06B6D4" } // Cyan
];



// Initialize selectedFields with all metric fields
let chartInstance = null;
let selectedFields = metricDefinitions.map(metric => metric.field);
const testId = window.location.pathname.split('/').filter(Boolean).pop();

async function fetchData() {
    try {
        const response = await fetch(`/api/technician/test-report-entries/${testId}`);
        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
        return await response.json();
    } catch (error) {
        console.error("Error fetching data:", error);
        alert("Failed to load data. Please try again.");
        return [];
    }
}

function createChart(data, selectedFields) {
    if (!data || data.length === 0) {
        console.warn("No data to display");
        return;
    }

    const labels = data.map(e => new Date(e.timestamp).toLocaleTimeString());

    const datasets = metricDefinitions
        .filter(metric => selectedFields.includes(metric.field))
        .map(metric => ({
            label: metric.label,
            data: data.map(e => e[metric.field] != null ? e[metric.field] : null),
            borderColor: metric.color,
            yAxisID: metric.field,
            fill: false,
            tension: 0.1,
            pointRadius: 3 // Make points visible
        }));

    const yAxes = {};
    datasets.forEach(ds => {
        yAxes[ds.yAxisID] = {
            type: 'linear',
            position: datasets.length > 6 ? 'left' : 'right', // Alternate sides for many axes
            display: false, //remove the axis.
            title: { display: false, text: ds.label }, //hide title
            grid: { drawOnChartArea: false }
        };
    });

    if (chartInstance) chartInstance.destroy();

    chartInstance = new Chart(document.getElementById("metricsChart"), {
        type: 'line',
        data: {
            labels,
            datasets
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            interaction: {
                mode: 'index',
                intersect: false
            },
            plugins: {
                legend: {
                    display: true,
                    position: 'bottom',
                    labels: {
                        boxWidth: 10
                    }
                }
            },
            scales: {
                x: {
                    title: { display: true, text: "Timestamp" }
                },
                ...yAxes
            }
        }
    });
}

function renderToggles(allFields, selectedFields, onChange) {


    allFields.forEach(metric => {
        const col = document.createElement("div");
        col.className = "col-md-3 form-check";

        const checkbox = document.createElement("input");
        checkbox.type = "checkbox";
        checkbox.className = "form-check-input";
        checkbox.id = metric.field;
        checkbox.checked = selectedFields.includes(metric.field);
        checkbox.addEventListener("change", () => onChange(metric.field, checkbox.checked));

        const label = document.createElement("label");
        label.className = "form-check-label ms-2";
        label.htmlFor = metric.field;
        label.textContent = metric.label;

        col.appendChild(checkbox);
        col.appendChild(label);
    });
}

fetchData().then(data => {
    createChart(data, selectedFields);
    renderToggles(metricDefinitions, selectedFields, (field, isChecked) => {
        if (isChecked && !selectedFields.includes(field)) {
            selectedFields.push(field);
        } else {
            selectedFields = selectedFields.filter(f => f !== field);
        }
        createChart(data, selectedFields);
    });
});
