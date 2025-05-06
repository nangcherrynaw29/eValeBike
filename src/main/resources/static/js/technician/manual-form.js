document.addEventListener('DOMContentLoaded', function () {
    const saveAndStartButton = document.querySelector('button[type="submit"].btn-primary');
    const manualTestForm = document.getElementById('manualTestForm');
    const successMessageDiv = document.createElement('div');
    successMessageDiv.className = 'alert alert-success mt-3';
    successMessageDiv.style.display = 'none';
    successMessageDiv.textContent = 'Bike details updated successfully! Starting test...';
    manualTestForm.appendChild(successMessageDiv);
    const errorMessageDiv = document.createElement('div');
    errorMessageDiv.className = 'alert alert-danger mt-3';
    errorMessageDiv.style.display = 'none';
    manualTestForm.appendChild(errorMessageDiv);

    saveAndStartButton.addEventListener('click', function (event) {
        event.preventDefault(); // Prevent the default form submission
        errorMessageDiv.style.display = 'none'; // Hide any previous error messages
        successMessageDiv.style.display = 'none'; // Hide any previous success messages
        saveAndStartButton.disabled = true; // Disable the button

        const formData = new FormData(manualTestForm);
        const bikeQR = manualTestForm.dataset.bikeQr; // Get bikeQR from data attribute

        // First API call: POST to update manual test fields
        fetch(`/api/technician/manual-test-form/${bikeQR}`, {
            method: 'POST',
            body: new URLSearchParams(formData), // Encode form data
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        })
            .then(response => {
                if (response.ok) {
                    successMessageDiv.style.display = 'block';

                    // Second API call: POST to start the test
                    const startTestUrl = `/api/technician/start/${bikeQR}?testType=MANUAL`;
                    return fetch(startTestUrl, {
                        method: 'POST'
                    });
                } else {
                    return response.text().then(text => {
                        errorMessageDiv.textContent = 'Failed to update bike: ' + text;
                        errorMessageDiv.style.display = 'block';
                        saveAndStartButton.disabled = false; // Re-enable the button
                        throw new Error('Failed to update bike: ' + text);
                    });
                }
            })
            .then(startResponse => {
                if (startResponse) {
                    if (startResponse.redirected) {
                        window.location.href = startResponse.url; // Follow the redirect
                    } else if (!startResponse.ok) {
                        return startResponse.text().then(text => {
                            errorMessageDiv.textContent = 'Failed to start test: ' + text;
                            errorMessageDiv.style.display = 'block';
                            saveAndStartButton.disabled = false; // Re-enable the button
                            successMessageDiv.style.display = 'none';
                            throw new Error('Failed to start test: ' + text);
                        });
                    }
                }
            })
            .catch(error => {
                console.error('Error during API calls:', error);
                if (!errorMessageDiv.textContent) {
                    errorMessageDiv.textContent = 'An error occurred: ' + error.message;
                    errorMessageDiv.style.display = 'block';
                }
                saveAndStartButton.disabled = false; // Re-enable the button
                successMessageDiv.style.display = 'none';
            });
    })
});