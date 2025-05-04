
const metricDefinitions = [
    { label: "Battery Voltage", field: "batteryVoltage", color: "#1D4ED8" }, // Deep Blue
    { label: "Battery Current", field: "batteryCurrent", color: "#F97316" }, // Orange
    { label: "Battery Temp", field: "batteryTemperatureCelsius", color: "#DC2626" }, // Strong Red
    { label: "Wheel Speed", field: "bikeWheelSpeedKmh", color: "#059669" }, // Teal Green
    { label: "Torque Crank", field: "torqueCrankNm", color: "#7C3AED" }, // Purple
    { label: "Engine RPM", field: "engineRpm", color: "#0EA5E9" }, // Sky Blue (kept)
    { label: "Engine Power", field: "enginePowerWatt", color: "#16A34A" }, // Green
    { label: "Loadcell", field: "loadcellN", color: "#DB2777" }, // Strong Pink
    { label: "Load Power", field: "loadPower", color: "#374151" }, // Dark Gray
    { label: "Cadance RPM", field: "cadanceRpm", color: "#D97706" }, // Dark Amber
    { label: "Plug Status", field: "statusPlug", color: "#14B8A6" } // Cyan-Teal
];


let chartInstance = null;
let selectedFields = metricDefinitions.map(metric => metric.field);
const testId = window.location.pathname.split('/').filter(Boolean).pop();
let isNormalized = false; // ← Toggle flag

// Fetch data from API
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

// Create and render chart
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
            yAxisID: metric.field, // For raw data, it will use individual axes per field
            fill: false,
            tension: 0.1,
            pointRadius: 3
        }));

    let scales;

    // Normalize Y-Axis configuration
    if (isNormalized) {
        scales = {
            x: { title: { display: true, text: "Timestamp" } },
            y: {
                type: 'linear',
                position: 'left',
                min: -1,
                max: 1,
                title: { display: true, text: 'Normalized Value' },
                ticks: { stepSize: 0.2 } // Adjust the step size
            }
        };

        // All datasets should use the same Y-axis for normalized data
        datasets.forEach(ds => {
            ds.yAxisID = 'y'; // All datasets use the same y-axis for normalized data
        });
    } else {
        // For raw data, use multiple Y-axes
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
            left = !left; // Alternate between left and right axes
        });
        scales = {
            x: { title: { display: true, text: "Timestamp" } },
            ...yAxes
        };
    }

    // Destroy any existing chart instance to prevent overlap
    if (chartInstance) chartInstance.destroy();

    // Create the new chart
    chartInstance = new Chart(document.getElementById("metricsChart"), {
        type: 'line',
        data: { labels, datasets },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            interaction: { mode: 'index', intersect: false },
            plugins: {
                legend: { display: true, position: 'bottom', labels: { boxWidth: 10 } }
            },
            scales: scales // Use the scales defined above
        }
    });
}

// Update chart data when toggling normalization
async function updateChart() {
    const data = await fetchData(isNormalized);
    createChart(data, selectedFields);
}

// Toggle normalization on button click
document.getElementById("toggleNormalizationBtn").addEventListener("click", () => {
    isNormalized = !isNormalized;
    document.getElementById("toggleNormalizationBtn").textContent =
        isNormalized ? "Show Raw Data" : "Show Normalized Data";
    updateChart();
});

// Initial load
updateChart();
