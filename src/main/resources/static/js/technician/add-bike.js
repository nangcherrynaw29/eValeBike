import { csrfToken, csrfHeader } from '../util/csrf.js';

document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector('#add-bike-form');
    const dateInput = document.querySelector('#lastTestDate');

    let dateError = document.querySelector('#date-error');
    if (!dateError) {
        dateError = document.createElement('div');
        dateError.id = 'date-error';
        dateError.className = 'text-danger mt-1 small';
        dateError.style.display = 'none';
        dateInput.parentNode.appendChild(dateError);
    }

    // Define validate.js constraints
    const constraints = {
        brand: { presence: { allowEmpty: false, message: "^Brand is required" } },
        model: { presence: { allowEmpty: false, message: "^Model is required" } },
        chassisNumber: { presence: { allowEmpty: false, message: "^Chassis Number is required" } },
        productionYear: {
            presence: { allowEmpty: false, message: "^Production Year is required" },
            numericality: { onlyInteger: true, greaterThan: 1900, lessThanOrEqualTo: new Date().getFullYear(), message: "^Enter a valid year" }
        },
        mileage: {
            presence: { allowEmpty: false, message: "^Mileage is required" },
            numericality: { onlyInteger: true, greaterThanOrEqualTo: 0, message: "^Mileage must be a positive number" }
        },
        gearType: { presence: { allowEmpty: false, message: "^Gear Type is required" } },
        engineType: { presence: { allowEmpty: false, message: "^Engine Type is required" } },
        powerTrain: { presence: { allowEmpty: false, message: "^Power Train is required" } },
        accuCapacity: {
            presence: { allowEmpty: false, message: "^Accu Capacity is required" },
            numericality: { greaterThan: 0, message: "^Must be a positive number" }
        },
        maxSupport: {
            presence: { allowEmpty: false, message: "^Max Support is required" },
            numericality: { greaterThanOrEqualTo: 0, lessThanOrEqualTo: 100, message: "^Must be between 0 and 100" }
        },
        maxEnginePower: {
            presence: { allowEmpty: false, message: "^Max Engine Power is required" },
            numericality: { greaterThan: 0, message: "^Must be a positive number" }
        },
        nominalEnginePower: {
            presence: { allowEmpty: false, message: "^Nominal Engine Power is required" },
            numericality: { greaterThan: 0, message: "^Must be a positive number" }
        },
        engineTorque: {
            presence: { allowEmpty: false, message: "^Engine Torque is required" },
            numericality: { greaterThan: 0, message: "^Must be a positive number" }
        },
        lastTestDate: { presence: { allowEmpty: false, message: "^Last Test Date is required" } }
    };

    form.addEventListener('submit', async e => {
        e.preventDefault();

        clearErrors();
        dateError.style.display = 'none';
        dateError.textContent = '';

        // Collect form data
        const formData = {};
        form.querySelectorAll("input, select").forEach(input => {
            formData[input.name] = input.value.trim();
        });

        // Validate with validate.js
        const errors = validate(formData, constraints);
        if (errors) {
            showErrors(errors);
            return;
        }

        // Additional date check
        const today = new Date();
        const selectedDate = new Date(dateInput.value);
        if (selectedDate > today) {
            dateError.textContent = 'Date cannot be in the future.';
            dateError.style.display = 'block';
            return;
        }

        // Add ownerId from URL param
        const getQueryParam = name => new URLSearchParams(window.location.search).get(name);
        const ownerId = parseInt(getQueryParam('ownerId'));

        // Prepare JSON payload
        const bikeData = {
            brand: formData.brand,
            model: formData.model,
            chassisNumber: formData.chassisNumber,
            productionYear: parseInt(formData.productionYear),
            bikeSize: formData.bikeSize,
            mileage: parseInt(formData.mileage),
            gearType: formData.gearType,
            engineType: formData.engineType,
            powerTrain: formData.powerTrain,
            accuCapacity: parseFloat(formData.accuCapacity),
            maxSupport: parseFloat(formData.maxSupport),
            maxEnginePower: parseFloat(formData.maxEnginePower),
            nominalEnginePower: parseFloat(formData.nominalEnginePower),
            engineTorque: parseFloat(formData.engineTorque),
            lastTestDate: formData.lastTestDate,
            bikeOwnerId: ownerId
        };

        try {
            const response = await fetch('/api/technician/bikes', {
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json',
                    [csrfHeader]: csrfToken
                },
                method: 'POST',
                body: JSON.stringify(bikeData)
            });

            if (response.status === 201) {
                await response.json();
                window.location.href = `/technician/bikes/owner/${ownerId}`;
            } else {
                const errorData = await response.json().catch(() => null);
                dateError.textContent = (errorData && errorData.message) || 'Something went wrong while creating the bike.';
                dateError.style.display = 'block';
            }
        } catch (error) {
            console.error("Error:", error);
            dateError.textContent = 'An error occurred while submitting.';
            dateError.style.display = 'block';
        }
    });

    function clearErrors() {
        const errorElements = document.querySelectorAll(".validation-error");
        errorElements.forEach(el => el.remove());
    }

    function showErrors(errors) {
        for (const field in errors) {
            const messages = errors[field];
            const inputEl = document.querySelector(`[name="${field}"]`);
            if (inputEl) {
                const errorEl = document.createElement("div");
                errorEl.className = "validation-error text-danger mt-1 small";
                errorEl.innerText = messages[0];
                inputEl.insertAdjacentElement("afterend", errorEl);
            }
        }
    }

    // Back to dashboard
    const backToDashboardBtn = document.getElementById("back-to-dashboard-btn");
    if (backToDashboardBtn) {
        backToDashboardBtn.addEventListener("click", () => {
            window.history.back();
        });
    }
});
