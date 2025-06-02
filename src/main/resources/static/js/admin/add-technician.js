import { csrfToken, csrfHeader } from '../util/csrf.js';

document.addEventListener("DOMContentLoaded", function () {
    const addTechnicianForm = document.querySelector("#add-technician-form");
    const backToDashboardBtn = document.getElementById("back-to-dashboard-btn");

    // Handle "Back to Dashboard" button
    if (backToDashboardBtn) {
        backToDashboardBtn.addEventListener("click", function () {
            window.location.href = "/admin/technicians";
        });
    }

    // Validation constraints using validate.js
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

    addTechnicianForm.addEventListener("submit", async function (e) {
        e.preventDefault();

        // Clear previous errors
        clearErrors();

        // Collect form data
        const name = document.querySelector("#technician-name").value.trim();
        const email = document.querySelector("#technician-email").value.trim();
        const companySelect = document.querySelector('#company');
        const rawCompanyValue = companySelect ? companySelect.value : null;
        const companyId = rawCompanyValue === "" || rawCompanyValue === undefined ? null : parseInt(rawCompanyValue, 10);

        // Validate form data
        const formData = { name, email };
        const errors = validate(formData, constraints);

        if (errors) {
            // Show validation errors
            showErrors(errors);
            return;
        }

        // If validation passes, create the data to send as a JSON object
        const jsonBody = JSON.stringify({
            name: name,
            email: email,
            companyId: companyId
        });

        try {
            // Send the POST request with fetch
            const response = await fetch("/api/admin/technicians", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Accept": "application/json",
                    [csrfHeader]: csrfToken
                },
                body: jsonBody
            });

            if (response.status === 201) {
                // Technician created successfully
                window.location.href = "/admin/technicians";
            } else {
                alert("Something went wrong. Please try again.");
            }
        } catch (error) {
            console.error("Error:", error);
            alert("There was an error submitting the form.");
        }
    });

    // Helper function to clear previous errors
    function clearErrors() {
        const errorElements = document.querySelectorAll(".validation-error");
        errorElements.forEach(el => el.remove());
    }

    // Helper function to show errors under each input
    function showErrors(errors) {
        for (const field in errors) {
            const messages = errors[field];
            const inputEl = document.querySelector(`#technician-${field}`);
            if (inputEl) {
                const errorEl = document.createElement("div");
                errorEl.className = "validation-error text-danger mt-1 small";
                errorEl.innerText = messages[0]; // show the first error only
                inputEl.insertAdjacentElement("afterend", errorEl);
            }
        }
    }
});
