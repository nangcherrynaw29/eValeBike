const csrfToken = document.querySelector('meta[name="_csrf"]').content;
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

document.addEventListener('DOMContentLoaded', function () {
    const saveAndStartButton = document.querySelector('button[type="submit"].btn-primary');
    const manualTestForm = document.getElementById('manualTestForm');
    const maxLimit = 2000;
    const minLimit = 0.01;

    saveAndStartButton.addEventListener('click', function (event) {
        event.preventDefault(); // Prevent the default form submission

        // Create success and error message divs
        const successMessageDiv = document.createElement('div');
        successMessageDiv.className = 'alert alert-success mt-3';
        successMessageDiv.style.display = 'none';
        successMessageDiv.innerHTML = '<i class="bi bi-check-circle"></i> Bike details updated successfully! Starting test...';
        manualTestForm.appendChild(successMessageDiv);

        const errorMessageDiv = document.createElement('div');
        errorMessageDiv.className = 'alert alert-danger mt-3';
        errorMessageDiv.style.display = 'none';
        manualTestForm.appendChild(errorMessageDiv);

        // Remove previous error messages if any
        const errorMessages = manualTestForm.querySelectorAll('.error-message');
        errorMessages.forEach(msg => msg.remove());

        // Define fields to validate
        const fieldNames = [{field: "accuCapacity", label: "Battery Capacity"}, {
            field: "maxSupport",
            label: "Max Support"
        }, {field: "maxEnginePower", label: "Engine Power Max"}, {
            field: "nominalEnginePower",
            label: "Engine Power Nominal"
        }, {field: "engineTorque", label: "Engine Torque"}];

        let isValid = true;

        // Check each field
        fieldNames.forEach(({field, label}) => {
            const input = document.querySelector(`[name="${field}"]`);
            if (!input) {
                console.warn(`Field ${field} not found`);
                return;
            }

            const value = parseFloat(input.value);
            const errorDiv = document.createElement('div');
            errorDiv.className = 'error-message text-danger mt-2';

            // Validation check
            if (isNaN(value) || value < minLimit || value > maxLimit) {
                errorDiv.innerHTML = `<i class="bi bi-x-circle"></i> <strong>${label}</strong> must be between <strong>${minLimit}</strong> and <strong>${maxLimit}</strong>.`;
                input.parentElement.appendChild(errorDiv); // Add error message below the input
                isValid = false;
            }
        });

        if (!isValid) {
            return; // Stop further execution if validation fails
        }

        // All fields are valid — proceed with form submission
        saveAndStartButton.disabled = true;

        const formData = new FormData(manualTestForm);
        const bikeQR = manualTestForm.dataset.bikeQr;

        // First API call to update bike
        fetch(`/api/technician/manual-test-form/${bikeQR}`, {
            method: 'POST', body: new URLSearchParams(formData), headers: {
                'Content-Type': 'application/x-www-form-urlencoded', [csrfHeader]: csrfToken
            }
        })
            .then(response => {
                if (response.ok) {
                    successMessageDiv.style.display = 'block';
                    const startTestUrl = `/api/technician/start/${bikeQR}?testType=MANUAL`;
                    return fetch(startTestUrl, {
                        method: 'POST', headers: {
                            [csrfHeader]: csrfToken
                        }
                    });
                } else {
                    return response.text().then(text => {
                        errorMessageDiv.innerHTML = `<i class="bi bi-x-circle"></i> <strong>Failed to update bike:</strong> ${text}`;
                        errorMessageDiv.style.display = 'block';
                        saveAndStartButton.disabled = false;
                        throw new Error('Failed to update bike: ' + text);
                    });
                }
            })
            .then(startResponse => {
                if (startResponse) {
                    if (startResponse.redirected) {
                        window.location.href = startResponse.url;
                    } else if (!startResponse.ok) {
                        return startResponse.text().then(text => {
                            errorMessageDiv.innerHTML = `<i class="bi bi-x-circle"></i> <strong>Failed to start test:</strong> ${text}`;
                            errorMessageDiv.style.display = 'block';
                            saveAndStartButton.disabled = false;
                            successMessageDiv.style.display = 'none';
                            throw new Error('Failed to start test: ' + text);
                        });
                    }
                }
            })
            .catch(error => {
                console.error('Error during API calls:', error);
                if (!errorMessageDiv.textContent) {
                    errorMessageDiv.innerHTML = `<i class="bi bi-x-circle"></i> <strong>An error occurred:</strong> ${error.message}`;
                    errorMessageDiv.style.display = 'block';
                }
                saveAndStartButton.disabled = false;
                successMessageDiv.style.display = 'none';
            });
    });
});