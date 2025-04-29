// Add a new admin from admin-management page
document.addEventListener("DOMContentLoaded", () => {
    const addAdminBtn = document.querySelector("#add-admin-btn");

    if (addAdminBtn) {
        addAdminBtn.addEventListener("click", async (e) => {
            e.preventDefault();

            try {
                window.location.href = "/super-admin/admins/add";
            } catch (error) {
                console.error("Error navigating:", error);
                alert("Something went wrong while redirecting.");
            }
        });
    }
});

//load the admin table using ajax
document.addEventListener("DOMContentLoaded", async () => {
    const adminTableBody = document.getElementById("admin-table-body");

    try {
        // Fetch admin data from API
        const response = await fetch("/api/super-admin/admins");
        if (!response.ok) throw new Error("Failed to fetch admins");

        const admins = await response.json();
        adminTableBody.innerHTML = ""; // Clear table body

        // Loop through the admins and insert rows dynamically
        admins.forEach(admin => {
            const row = document.createElement("tr");
            row.dataset.id = admin.id;
        //please note that I am using dataset here
            row.innerHTML = `
                <td>${admin.name}</td>
                <td>${admin.email}</td>
                <td>${admin.companyName}</td>
                <td>
                    <button class="btn btn-outline-danger delete-btn" data-id="${admin.id}"><i class="fa-solid fa-trash"></i></button>
                </td>
            `;

            adminTableBody.appendChild(row);
        });

        // Attach event listeners to delete buttons
        attachDeleteEventListeners();
    } catch (error) {
        console.error("Error loading admins:", error);
    }
});

// Function to add event listeners to delete buttons
function attachDeleteEventListeners() {
    document.querySelectorAll(".delete-btn").forEach(button => {
        button.addEventListener("click", async (e) => {
            e.preventDefault();

            const adminRow = e.target.closest("tr");
            const adminId = adminRow.dataset.id;

            if (!adminId) {
                alert("Admin ID not found.");
                return;
            }

            try {
                const response = await fetch(`/api/super-admin/admins/${adminId}`, {
                    method: "DELETE",
                    headers: {
                        "Accept": "application/json"
                    }
                });

                if (response.ok) {
                    adminRow.remove(); // Remove the row from the table
                } else {
                    alert("Failed to delete admin.");
                }
            } catch (error) {
                console.error("Error deleting admin:", error);
                alert("An error occurred while deleting.");
            }
        });
    });
}


///Need to implement edit button




