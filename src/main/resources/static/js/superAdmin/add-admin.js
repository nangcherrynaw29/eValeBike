import { csrfToken, csrfHeader } from '../util/csrf.js';

document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector('#add-admin-form');

    // Define validate.js constraints
    const constraints = {
        name: {
            presence: { allowEmpty: false, message: "^Name is required" },
            length: { minimum: 2, message: "^Name must be at least 2 characters" }
        },
        email: {
            presence: { allowEmpty: false, message: "^Email is required" },
            email: { message: "^Please enter a valid email address" }
        }
    };

    form.addEventListener('submit', async function (e) {
        e.preventDefault();

        // Clear previous errors
        clearErrors();

        // Collect form data
        const name = document.querySelector('#name').value.trim();
        const email = document.querySelector('#email').value.trim();
        const company = document.querySelector('#company').value;

        // Validate with validate.js
        const formData = { name, email };
        const errors = validate(formData, constraints);

        if (errors) {
            showErrors(errors);
            return;
        }

        // Prepare JSON payload
        const jsonBody = JSON.stringify({
            name: name,
            email: email,
            companyId: company
        });

        try {
            const response = await fetch('/api/super-admin/admins', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json',
                    [csrfHeader]: csrfToken
                },
                body: jsonBody
            });

            if (response.status === 201) {
                window.location.href = "/super-admin/admins";
            } else {
                alert('Something went wrong. Please try again.');
            }
        } catch (error) {
            console.error("Error:", error);
            alert('There was an error submitting the form.');
        }
    });

    // Helper to clear error messages
    function clearErrors() {
        const errorElements = document.querySelectorAll(".validation-error");
        errorElements.forEach(el => el.remove());
    }

    // Helper to show errors under each input
    function showErrors(errors) {
        for (const field in errors) {
            const messages = errors[field];
            const inputEl = document.querySelector(`#${field}`);
            if (inputEl) {
                const errorEl = document.createElement("div");
                errorEl.className = "validation-error text-danger mt-1 small";
                errorEl.innerText = messages[0]; // show the first error only
                inputEl.insertAdjacentElement("afterend", errorEl);
            }
        }
    }

    // Back to dashboard button
    const backToDashboardBtn = document.getElementById("back-to-superAdmin-dashboard-btn");
    if (backToDashboardBtn) {
        backToDashboardBtn.addEventListener("click", function () {
            window.location.href = "/super-admin/admins";
        });
    }
});
