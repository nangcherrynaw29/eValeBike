document.addEventListener("DOMContentLoaded", () => {
    const technicianTableBody = document.getElementById("technician-table-body");
    const addTechnicianBtn = document.getElementById("add-technician-btn");

    // Load technicians when the page loads
    loadTechnicians();

    // Redirect to the "Add Technician" page
    if (addTechnicianBtn) {
        addTechnicianBtn.addEventListener("click", (e) => {
            e.preventDefault();
            window.location.href = "/admin/technicians/add";
        });
    }

    // Function to load technicians
    async function loadTechnicians() {
        try {
            const response = await fetch("/api/admin/technicians");
            if (!response.ok) throw new Error("Failed to fetch technicians");

            const technicians = await response.json();
            technicianTableBody.innerHTML = ""; // Clear the table

            // Populate the table with technician data
            technicians.forEach(technician => {
                const row = document.createElement("tr");
                row.dataset.id = technician.id; // Store technician ID in the row
                row.innerHTML = `
                    <td>${technician.id}</td>
                    <td>${technician.name}</td>
                    <td>${technician.email}</td>
                    <td>
                        <button class="delete-btn" data-id="${technician.id}">Delete</button>
                    </td>
                `;
                technicianTableBody.appendChild(row);
            });

            // Attach event listeners to delete buttons
            attachDeleteEventListeners();
        } catch (error) {
            console.error("Error loading technicians:", error);
        }
    }

    // Function to attach event listeners to delete buttons
    function attachDeleteEventListeners() {
        document.querySelectorAll(".delete-btn").forEach(button => {
            button.addEventListener("click", async (e) => {
                e.preventDefault();

                const technicianId = e.target.dataset.id; // Get technician ID from the button
                if (!technicianId) {
                    alert("Technician ID not found.");
                    return;
                }

                // Confirm deletion
                const confirmDelete = confirm("Are you sure you want to delete this technician?");
                if (!confirmDelete) return;

                try {
                    // Send DELETE request to the backend
                    const response = await fetch(`/api/admin/technicians/${technicianId}`, {
                        method: "DELETE",
                        headers: {
                            "Accept": "application/json",
                        },
                    });

                    if (response.ok) {
                        alert(`Technician with ID ${technicianId} deleted successfully.`);
                        loadTechnicians(); // Refresh the table
                    } else {
                        alert("Failed to delete technician.");
                    }
                } catch (error) {
                    console.error("Error deleting technician:", error);
                    alert("An error occurred while deleting.");
                }
            });
        });
    }
});

// Add Technician Form Submission
document.addEventListener("DOMContentLoaded", () => {
    const addTechnicianForm = document.getElementById("add-technician-form");

    if (addTechnicianForm) {
        addTechnicianForm.addEventListener("submit", async (e) => {
            e.preventDefault();

            // Collect form data
            const name = document.getElementById("technician-name").value;
            const email = document.getElementById("technician-email").value;
            const password = document.getElementById("technician-password").value;

            // Create JSON body for the request
            const jsonBody = JSON.stringify({
                name: name,
                email: email,
                password: password,
            });

            try {
                // Send POST request to add a new technician
                const response = await fetch("/api/admin/technicians", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        "Accept": "application/json",
                    },
                    body: jsonBody,
                });

                if (response.status === 201) {
                    // If the technician is created successfully
                    const technician = await response.json();
                    alert(`Technician created successfully with ID #${technician.id}`);
                    window.location.href = "/admin/technicians"; // Redirect to the dashboard
                } else {
                    alert("Something went wrong. Please try again.");
                }
            } catch (error) {
                console.error("Error:", error);
                alert("There was an error submitting the form.");
            }
        });
    }
});

// Back to Dashboard Button
document.addEventListener("DOMContentLoaded", () => {
    const backToDashboardBtn = document.getElementById("back-to-dashboard-btn");

    if (backToDashboardBtn) {
        backToDashboardBtn.addEventListener("click", () => {
            window.location.href = "/admin/technicians";
        });
    }
});