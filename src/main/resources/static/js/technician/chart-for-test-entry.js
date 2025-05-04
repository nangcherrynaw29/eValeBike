
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
//create a dataset for each metric ( label and its related data)
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
    //creating yaxis for dataset

    const yAxes = {};
    let left = true;

    datasets.forEach(ds => {
        yAxes[ds.yAxisID] = {
            type: 'linear',
            position: left ? 'left' : 'right',
            display: true,
            title: {
                display: true,
                text: ds.label
            },
            grid: {
                drawOnChartArea: false
            }
        };
        left = !left;
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


fetchData().then(data => {
    createChart(data, selectedFields);
});
