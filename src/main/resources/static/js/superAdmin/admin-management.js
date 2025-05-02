// Add a new admin from the admin-management page
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

// Load the admin table using ajax
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
            // Please note that I am using dataset here
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

document.addEventListener('DOMContentLoaded', function () {
    initializePage();
});

function initializePage() {
    // Initialize a pending users table
    if (document.getElementById('pending-users-table')) {
        loadPendingUsers();
    }

    // Initialize pending requests card
    const pendingRequestsCard = document.getElementById('pending-requests-card');
    if (pendingRequestsCard) {
        pendingRequestsCard.addEventListener('click', () => {
            window.location.href = '/super-admin/admins/pending-approvals';
        });

        // Start fetching pending requests count
        fetchPendingRequestsCount();
        // Refresh count every minute
        setInterval(fetchPendingRequestsCount, 60000);
    }

    // Initialize the back button
    const backButton = document.getElementById('back-to-superAdmin-dashboard-btn');
    if (backButton) {
        backButton.addEventListener('click', (e) => {
            e.preventDefault();
            window.location.href = '/super-admin/admins';
        });
    }
}

async function loadPendingUsers() {
    const tableBody = document.getElementById('pending-users-table');
    if (!tableBody) return;

    try {
        const response = await fetch('/api/super-admin/admins/pending');
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`Failed to fetch pending users: ${response.status} ${errorText}`);
        }

        const users = await response.json();
        tableBody.innerHTML = '';

        if (users.length === 0) {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td colspan="5" class="text-center">No pending approvals</td>
            `;
            tableBody.appendChild(row);
            return;
        }

        users.forEach(user => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${user.name || ''}</td>
                <td>${user.email || ''}</td>
                <td>${user.role || ''}</td>
                <td><span class="badge bg-warning">Pending</span></td>
                <td>
                    <button class="btn btn-success btn-sm approve-btn" data-id="${user.id}">
                        <i class="fas fa-check"></i> Approve
                    </button>
                    <button class="btn btn-danger btn-sm ms-2 reject-btn" data-id="${user.id}">
                        <i class="fas fa-times"></i> Reject
                    </button>
                </td>
            `;
            tableBody.appendChild(row);
        });

        attachApprovalEventListeners();
    } catch (error) {
        console.error('Error loading pending users:', error);
        alert('Failed to load pending users');
    }
}

function attachApprovalEventListeners() {
    document.querySelectorAll('.approve-btn, .reject-btn').forEach(button => {
        button.addEventListener('click', async (e) => {
            // Get the button element
            const btn = e.target.closest('button');
            if (!btn) return;

            const userId = btn.dataset.id;
            if (btn.classList.contains('approve-btn')) {
                await approveUser(userId);
            } else {
                await rejectUser(userId);
            }
        });
    });
}

async function approveUser(userId) {
    try {
        const response = await fetch(`/api/super-admin/admins/${userId}/approve`, {
            method: 'POST'
        });

        if (!response.ok) {
            throw new Error('Failed to approve user');
        }

        alert('User approved successfully');
        await loadPendingUsers();
    } catch (error) {
        console.error('Error approving user:', error);
        alert('Failed to approve user');
    }
}

async function rejectUser(userId) {
    try {
        const response = await fetch(`/api/super-admin/admins/${userId}/reject`, {
            method: 'POST'
        });

        if (!response.ok) {
            throw new Error('Failed to reject user');
        }

        alert('User rejected successfully');
        await loadPendingUsers();
    } catch (error) {
        console.error('Error rejecting user:', error);
        alert('Failed to reject user');
    }
}

async function fetchPendingRequestsCount() {
    try {
        const response = await fetch('/api/super-admin/admins/pending/count');
        if (!response.ok) {
            throw new Error('Failed to fetch pending requests count');
        }
        const count = await response.json();

        const countElement = document.getElementById('new-requests');
        const pendingCard = document.getElementById('pending-requests-card');

        if (countElement && pendingCard) {
            countElement.textContent = count;
            if (count > 0) {
                pendingCard.classList.add('has-pending');
                countElement.classList.add('text-warning');
            } else {
                pendingCard.classList.remove('has-pending');
                countElement.classList.remove('text-warning');
            }
        }
    } catch (error) {
        console.error('Error fetching pending requests:', error);
    }
}