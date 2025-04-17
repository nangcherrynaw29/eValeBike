document.addEventListener('DOMContentLoaded', () => {
    const testId = /*[[${testId}]]*/ '';
    const errorDiv = document.getElementById('error');

    function pollTestStatus() {
        fetch(`/technician/get-test-result/${testId}`, { method: 'GET' })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to fetch test status: ' + response.status);
                }
                return response.json();
            })
            .then(result => {
                if (result.state === 'COMPLETED') {
                    window.location.href = `/technician/test-result/${testId}`;
                } else {
                    setTimeout(pollTestStatus, 2000); // Poll every 2 seconds
                }
            })
            .catch(error => {
                errorDiv.textContent = `Error: ${error.message}. Redirecting to dashboard...`;
                errorDiv.style.display = 'block';
                setTimeout(() => window.location.href = '/technician/bike-dashboard', 3000);
            });
    }

    pollTestStatus()}); // Start polling