//Create a new admin

document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector('#add-admin-form');

    form.addEventListener('submit', async function (e) {
        e.preventDefault();

        // Collect form data
        const name = document.querySelector('#name').value;
        const email = document.querySelector('#email').value;
        const companyName = document.querySelector('#companyName').value;

        // Create the data to send as a JSON object
        const jsonBody = JSON.stringify({
            name: name,
            email: email,
            companyName: companyName
        });

        try {
            // Send the POST request with fetch
            const response = await fetch('/api/super-admin', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json', // Send JSON data
                    'Accept': 'application/json' // Expect JSON response
                },
                body: jsonBody // Attach the JSON data
            });

            if (response.status === 201) {
                // If the admin is created successfully (201 Created)
                const admin = await response.json(); // Get the created admin from the response
                alert(`Congrats, your admin was created. It has ID #${admin.id}`);
                window.location.href = "/super-admin/admins"; // Redirect to the admin management page
            } else {
                // If something goes wrong
                alert('Something went wrong. Please try again.');
            }
        } catch (error) {
            console.error("Error:", error);
            alert('There was an error submitting the form.');
        }
    });


});

document.addEventListener("DOMContentLoaded", function () {
    const backToDashboardBtn = document.getElementById("back-to-superAdmin-dashboard-btn");
    if (backToDashboardBtn) {
        backToDashboardBtn.addEventListener("click", function () {
            console.log("Back to dashboard button clicked!");
            window.location.href = "/super-admin/admins";
        });
    }
});

