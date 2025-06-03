import { csrfHeader, csrfToken } from "../util/csrf.js";

document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("add-company-form");

    // Define validate.js constraints
    const constraints = {
        name: {
            presence: { allowEmpty: false, message: "^Company name is required" },
            length: { minimum: 2, message: "^Company name must be at least 2 characters" }
        },
        address: {
            presence: { allowEmpty: false, message: "^Address is required" },
            length: { minimum: 5, message: "^Address must be at least 5 characters" }
        },
        email: {
            presence: { allowEmpty: false, message: "^Email is required" },
            email: { message: "^Please enter a valid email address" }
        },
        phone: {
            presence: { allowEmpty: false, message: "^Phone is required" },
            format: {
                pattern: "\\d{7,15}",
                message: "^Phone number must be 7-15 digits"
            }
        }
    };

    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        // Clear previous errors
        clearErrors();

        // Gather form data
        const name = document.getElementById("name").value.trim();
        const address = document.getElementById("address").value.trim();
        const email = document.getElementById("email").value.trim();
        const phone = document.getElementById("phone").value.trim();

        const formData = { name, address, email, phone };

        // Validate
        const errors = validate(formData, constraints);

        if (errors) {
            showErrors(errors);
            return;
        }

        // Create JSON payload
        const companyData = JSON.stringify(formData);

        try {
            const response = await fetch("/api/super-admin/companies", {
                method: "POST",
                headers: {
                    [csrfHeader]: csrfToken,
                    'Content-type': 'application/json'
                },
                body: companyData
            });

            if (response.status === 201) {
                alert("Company added successfully!");
                form.reset();
            } else {
                alert("Failed to add company.");
            }
        } catch (error) {
            console.error("Error:", error);
            alert("An error occurred.");
        }
    });

    function clearErrors() {
        const errorElements = document.querySelectorAll(".validation-error");
        errorElements.forEach(el => el.remove());
    }

    function showErrors(errors) {
        for (const field in errors) {
            const messages = errors[field];
            const inputEl = document.getElementById(field);
            if (inputEl) {
                const errorEl = document.createElement("div");
                errorEl.className = "validation-error text-danger mt-1 small";
                errorEl.innerText = messages[0];
                inputEl.insertAdjacentElement("afterend", errorEl);
            }
        }
    }
});
