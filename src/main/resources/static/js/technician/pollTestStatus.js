

const testId = new URLSearchParams(window.location.search).get('testId');

function checkStatus() {
    fetch('/technician/status/' + testId)
        .then(response => response.json())
        .then(data => {
            document.getElementById('status').innerText = 'Status: ' + data.state;
            if (data.state.toLowerCase() === 'completed') {
                // Redirect to visual inspection page instead of test report
                document.getElementById('status').innerText = 'Test Completed. Redirecting to inspection...';
                document.getElementById('testResult').style.display = 'block';
                window.location.href = '/technician/visual-inspection/' + testId;
            } else {
                setTimeout(checkStatus, 5000); // Poll every 5 seconds
            }
        })
        .catch(error => {
            console.error('Error fetching status:', error);
            document.getElementById('status').innerText = 'Error fetching status';
            setTimeout(checkStatus, 5000); // Retry after 5 seconds
        });
}

checkStatus();