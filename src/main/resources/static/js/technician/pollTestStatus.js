const testId = new URLSearchParams(window.location.search).get('testId');
function checkStatus() {
    fetch('/technician/status/' + testId)
        .then(response => response.json())
        .then(data => {
            document.getElementById('status').innerText = 'Status: ' + data.state;
            if (data.state.toLowerCase() === 'completed') {
                window.location.href = '/technician/report/' + testId;
            } else {
                setTimeout(checkStatus, 5000); // Poll every 5 seconds
            }
        })
        .catch(error => {
            console.error('Error fetching status:', error);
            document.getElementById('status').innerText = 'Error fetching status';
        });
}
checkStatus();