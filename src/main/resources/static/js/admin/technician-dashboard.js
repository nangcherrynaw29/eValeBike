document.addEventListener("DOMContentLoaded", () => {
    const addTechnicianBtn = document.getElementById("add-technician-btn");

    if (addTechnicianBtn) {
        addTechnicianBtn.addEventListener("click", () => {
            window.location.href = "/admin/technicians/add";
        });
    }

    const technicianTableBody = document.getElementById("technician-table-body");
    const paginationContainer = document.getElementById("pagination");

    const techniciansPerPage = 5;
    let currentPage = 1;
    let techniciansData = [];

    async function loadTechnicians() {
        try {
            const response = await fetch("/api/admin/technicians");
            if (!response.ok) throw new Error("Failed to fetch technicians");

            techniciansData = await response.json();
            renderTable();
            renderPagination();
        } catch (error) {
            console.error("Error loading technicians:", error);
        }
    }

    function renderTable() {
        technicianTableBody.innerHTML = "";
        const start = (currentPage - 1) * techniciansPerPage;
        const end = start + techniciansPerPage;
        const currentTechnicians = techniciansData.slice(start, end);

        currentTechnicians.forEach(technician => {
            const row = document.createElement("tr");
            row.dataset.id = technician.id;
            row.innerHTML = `
            <td>${technician.name}</td>
            <td>${technician.email}</td>
            <td>
                <button class="btn btn-outline-danger delete-btn" data-id="${technician.id}">
                    <i class="fa-solid fa-trash"></i>
                </button>
            </td>
        `;
            technicianTableBody.appendChild(row);
        });

        attachDeleteEventListeners();
    }

    function renderPagination() {
        paginationContainer.innerHTML = "";

        const totalPages = Math.ceil(techniciansData.length / techniciansPerPage);

        const createPageItem = (text, page, disabled = false, active = false) => {
            const li = document.createElement("li");
            li.className = `page-item ${disabled ? "disabled" : ""} ${active ? "active" : ""}`;
            const a = document.createElement("a");
            a.className = "page-link";
            a.href = "#";
            a.textContent = text;
            a.addEventListener("click", (e) => {
                e.preventDefault();
                if (!disabled) {
                    currentPage = page;
                    renderTable();
                    renderPagination();
                }
            });
            li.appendChild(a);
            return li;
        };

        paginationContainer.appendChild(
            createPageItem("Previous", currentPage - 1, currentPage === 1)
        );

        for (let i = 1; i <= totalPages; i++) {
            paginationContainer.appendChild(
                createPageItem(i, i, false, currentPage === i)
            );
        }

        paginationContainer.appendChild(
            createPageItem("Next", currentPage + 1, currentPage === totalPages)
        );
    }

    // Function to attach event listeners to delete buttons
    function attachDeleteEventListeners() {
        document.querySelectorAll(".delete-btn").forEach(button => {
            button.addEventListener("click", async (e) => {
                e.preventDefault();

                const technicianId = e.currentTarget.dataset.id; // Get technician ID from the button
                if (!technicianId) {
                    alert("Technician ID not found.");
                    return;
                }

                try {
                    // Send a DELETE request to the backend
                    const response = await fetch(`/api/admin/technicians/${technicianId}`, {
                        method: "DELETE",
                        headers: {
                            "Accept": "application/json",
                        },
                    });

                    if (response.ok) {
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
    loadTechnicians();
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