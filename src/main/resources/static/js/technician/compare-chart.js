const metricLabels = {
    batteryVoltage: "Battery Voltage",
    batteryCurrent: "Battery Current",
    batteryCapacity: "Battery Capacity",
    batteryTemperatureCelsius: "Battery Temp (°C)",
    chargeStatus: "Charge Status",
    assistanceLevel: "Assistance Level",
    torqueCrankNm: "Torque Crank (Nm)",
    bikeWheelSpeedKmh: "Wheel Speed (km/h)",
    cadanceRpm: "Cadence (RPM)",
    engineRpm: "Engine RPM",
    enginePowerWatt: "Engine Power (W)",
    wheelPowerWatt: "Wheel Power (W)",
    rollTorque: "Roll Torque",
    loadcellN: "Load Cell (N)",
    rolHz: "Rol Hz",
    horizontalInclination: "Horizontal Inclination",
    verticalInclination: "Vertical Inclination",
    loadPower: "Load Power",
    statusPlug: "Status Plug"
};

document.addEventListener("DOMContentLoaded", () => {
    const chartCanvas = document.getElementById('comparisonChart');
    let chartInstance;

    function renderChart(metric) {
        const labels = Array.from({ length: Math.max(dataReport1.length, dataReport2.length) }, (_, i) => i + 1);
        const values1 = dataReport1.map(e => e[metric]);
        const values2 = dataReport2.map(e => e[metric]);

        if (chartInstance) chartInstance.destroy();

        chartInstance = new Chart(chartCanvas, {
            type: 'line',
            data: {
                labels: labels,
                datasets: [
                    {
                        label: 'Test A',
                        data: values1,
                        borderColor: 'blue',
                        borderWidth: 2,
                        fill: false,
                        tension: 0.2
                    },
                    {
                        label: 'Test B',
                        data: values2,
                        borderColor: 'red',
                        borderWidth: 2,
                        fill: false,
                        tension: 0.2
                    }
                ]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: { position: 'top' }
                },
                scales: {
                    x: {
                        title: {
                            display: true,
                            text: 'nth entry'
                        }
                    },
                    y: {
                        beginAtZero: true,
                        title: {
                            display: true,
                            text: metricLabels[metric] || metric // fallback to code name
                        }
                    }
                }
            }




        });
    }

    // Attach event listeners to metric radio buttons
    document.querySelectorAll('input[name="metric"]').forEach(radio => {
        radio.addEventListener('change', function () {
            renderChart(this.value);
        });
    });

    // Initial render
    const initialMetric = document.querySelector('input[name="metric"]:checked');
    if (initialMetric) renderChart(initialMetric.value);
});
