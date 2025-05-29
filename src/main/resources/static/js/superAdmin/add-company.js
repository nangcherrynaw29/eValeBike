import {csrfHeader, csrfToken} from "../util/csrf";

document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("add-company-form");
    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        const name = document.getElementById("name").value;
        const address = document.getElementById("address").value;
        const email = document.getElementById("email").value;
        const phone = document.getElementById("phone").value;

        const companyData = { name, address, email, phone };

        try {
            const response = await fetch("/api/super-admin/companies", {
                method: "POST",
                headers: {
                    [csrfHeader]: csrfToken,
                    'Accept': 'application/json'
                },
                body: JSON.stringify(companyData)
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
});