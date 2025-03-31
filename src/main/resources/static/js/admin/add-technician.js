document.addEventListener("DOMContentLoaded", function () {
    const addTechnicianForm = document.querySelector("#add-technician-form");

    addTechnicianForm.addEventListener("submit", async function (e) {
        e.preventDefault();

        // Collect form data
        const name = document.querySelector("#technician-name").value;
        const email = document.querySelector("#technician-email").value;
        const password = document.querySelector("#technician-password").value;

        // Create the data to send as a JSON object
        const jsonBody = JSON.stringify({
            name: name,
            email: email,
            password: password,
        });

        try {
            // Send the POST request with fetch
            const response = await fetch("/api/admin/technicians", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json", // Send JSON data
                    "Accept": "application/json", // Expect JSON response
                },
                body: jsonBody, // Attach the JSON data
            });

            if (response.status === 201) {
                // If the technician is created successfully (201 Created)
                const technician = await response.json(); // Get the created technician from the response
                window.location.href = "/admin/technicians"; // Redirect back to the technician dashboard
            } else {
                // If something goes wrong
                alert("Something went wrong. Please try again.");
            }
        } catch (error) {
            console.error("Error:", error);
            alert("There was an error submitting the form.");
        }
    });
});

document.addEventListener("DOMContentLoaded", function () {
    const backToDashboardBtn = document.getElementById("back-to-dashboard-btn");
    if (backToDashboardBtn) {
        backToDashboardBtn.addEventListener("click", function () {
            console.log("Back to dashboard button clicked!");
            window.location.href = "/admin/technicians";
        });
    }
});
