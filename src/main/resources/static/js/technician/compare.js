// Extract id1 and id2 from URL path (e.g., /technician/compare/123/456)
const pathParts = window.location.pathname.split('/');
const id1 = pathParts[pathParts.length - 2];
const id2 = pathParts[pathParts.length - 1];

// Validate IDs
if (!id1 || !id2) {
    document.getElementById('error').textContent = 'Error: Missing test report IDs';
    throw new Error('Missing test report IDs');
}

// Fetch data and render bar graph
fetch(`/api/technician/compare/${id1}/${id2}`)
    .then(response => {
        if (!response.ok) {
            return response.json().then(errorData => {
                throw new Error(errorData.error || 'Failed to fetch data');
            });
        }
        return response.json();
    })
    .then(data => {
        const summaries = data.summaries || [];

        // Fields for the bar graph (numerical only)
        const fields = [
            { key: 'batteryVoltage', label: 'Battery Voltage (V)' },
            { key: 'batteryCurrent', label: 'Battery Current (A)' },
            { key: 'batteryCapacity', label: 'Battery Capacity (Ah)' },
            { key: 'batteryTemperatureCelsius', label: 'Battery Temperature (°C)' },
            { key: 'torqueCrankNm', label: 'Torque Crank (Nm)' },
            { key: 'bikeWheelSpeedKmh', label: 'Bike Wheel Speed (km/h)' },
            { key: 'cadanceRpm', label: 'Cadence (RPM)' },
            { key: 'engineRpm', label: 'Engine RPM' },
            { key: 'enginePowerWatt', label: 'Engine Power (W)' },
            { key: 'wheelPowerWatt', label: 'Wheel Power (W)' },
            { key: 'rollTorque', label: 'Roll Torque' },
            { key: 'loadcellN', label: 'Load Cell (N)' },
            { key: 'rolHz', label: 'Roll Hz' },
            { key: 'horizontalInclination', label: 'Horizontal Inclination' },
            { key: 'verticalInclination', label: 'Vertical Inclination' },
            { key: 'loadPower', label: 'Load Power' }
        ];

        // Prepare chart data
        const labels = fields.map(field => field.label);
        const datasets = summaries.map((summary, index) => ({
            label: `Test ${index === 0 ? id1 : id2}`,
            data: fields.map(field => summary ? summary[field.key] || 0 : 0),
            backgroundColor: index === 0 ? 'rgba(75, 192, 192, 0.5)' : 'rgba(255, 99, 132, 0.5)',
            borderColor: index === 0 ? 'rgba(75, 192, 192, 1)' : 'rgba(255, 99, 132, 1)',
            borderWidth: 1
        }));

        // Render bar graph
        const ctx = document.getElementById('comparisonChart').getContext('2d');
        new Chart(ctx, {
            type: 'bar',
            data: { labels, datasets },
            options: {
                responsive: true,
                scales: {
                    y: { beginAtZero: true, title: { display: true, text: 'Value' } },
                    x: { title: { display: true, text: 'Metrics' } }
                },
                plugins: {
                    legend: { display: true, position: 'top' },
                    title: { display: true, text: 'Test Report Comparison' }
                }
            }
        });

        // Clear errors
        document.getElementById('error').textContent = '';
    })
    .catch(error => {
        console.error('Error:', error);
        document.getElementById('error').textContent = `Error: ${error.message}`;
    });