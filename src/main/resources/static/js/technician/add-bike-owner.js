import { csrfToken, csrfHeader } from '../util/csrf.js';

document.addEventListener('DOMContentLoaded', () => {
    const form = document.querySelector('#add-bikeOwner-form');
    const backToDashboardBtn = document.getElementById("back-to-dashboard-btn");

    // Validation constraints
    const constraints = {
        name: {
            presence: { allowEmpty: false, message: "^Name is required" },
            length: { minimum: 2, message: "^Name must be at least 2 characters" }
        },
        email: {
            presence: { allowEmpty: false, message: "^Email is required" },
            email: { message: "^Invalid email format" }
        },
        phoneNumber: {
            presence: { allowEmpty: false, message: "^Phone Number is required" },
            format: {
                pattern: /^[0-9\-\+\s]{7,15}$/,
                message: "^Phone Number must be 7–15 digits"
            }
        },
        birthDate: {
            presence: { allowEmpty: false, message: "^Birth Date is required" }
            // No datetime here because we’ll check separately for "past"
        }
    };

    // Helper: show validation errors
    function showErrors(errors) {
        // Remove previous errors
        form.querySelectorAll('.is-invalid').forEach(el => el.classList.remove('is-invalid'));
        form.querySelectorAll('.invalid-feedback').forEach(el => el.remove());

        if (!errors) return;

        Object.keys(errors).forEach(field => {
            const input = form.querySelector(`[name="${field}"]`);
            if (input) {
                input.classList.add('is-invalid');
                const errorDiv = document.createElement('div');
                errorDiv.className = 'invalid-feedback';
                errorDiv.innerText = errors[field][0];
                input.parentNode.appendChild(errorDiv);
            }
        });
    }

    form.addEventListener('submit', async e => {
        e.preventDefault();

        // Prepare data
        const name = form.name.value.trim();
        const email = form.email.value.trim();
        const phoneNumber = form.phoneNumber.value.trim();
        const birthDate = form.birthDate.value;
        const companySelect = document.querySelector('#company');
        const rawCompanyValue = companySelect ? companySelect.value : null;
        const companyId = rawCompanyValue === "" || rawCompanyValue === undefined ? null : parseInt(rawCompanyValue, 10);

        const formData = { name, email, phoneNumber, birthDate };

        // Validate using validate.js
        const errors = validate(formData, constraints);
        if (errors) {
            showErrors(errors);
            return;
        }

        // Additional: birthDate must be in the past
        const today = new Date();
        today.setHours(0, 0, 0, 0); // remove time part
        const birthDateObj = new Date(birthDate);
        if (birthDateObj >= today) {
            const birthInput = form.querySelector('#birthDate');
            birthInput.classList.add('is-invalid');
            const errorDiv = document.createElement('div');
            errorDiv.className = 'invalid-feedback';
            errorDiv.innerText = "Birth Date must be in the past";
            birthInput.parentNode.appendChild(errorDiv);
            return;
        }

        // If valid, clear errors
        showErrors(null);

        // Submit to server
        try {
            const response = await fetch('/api/technician/bikeOwners', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json',
                    [csrfHeader]: csrfToken
                },
                body: JSON.stringify({
                    name, email, phoneNumber, birthDate, companyId
                })
            });

            if (response.status === 201) {
                window.location.href = '/technician/bike-owners';
            } else {
                const errorData = await response.json().catch(() => null);
                alert(errorData?.message || 'Something went wrong while creating the bike owner.');
            }
        } catch (error) {
            console.error('Error:', error);
            alert('An error occurred while creating the bike owner.');
        }
    });

    // Back button
    if (backToDashboardBtn) {
        backToDashboardBtn.addEventListener("click", (e) => {
            e.preventDefault();
            window.location.href = "/technician/bike-owners";
        });
    }
});
