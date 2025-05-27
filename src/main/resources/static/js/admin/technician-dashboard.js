import { csrfToken, csrfHeader } from '../util/csrf.js';

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
    let allTechnicians = [];

    const filterInput = document.querySelector("#filterValue"); // Updated ID
    const filterDropdown = document.querySelector("#filterType"); // Updated ID
    const notFoundMessage = document.getElementById("no-results-message");
    const searchBtn = document.querySelector("#search-btn");

    async function loadTechnicians() {
        try {
            const response = await fetch("/api/admin/technicians");
            if (!response.ok) throw new Error("Failed to fetch technicians");

            techniciansData = await response.json();
            allTechnicians = [...techniciansData];  // Store a copy for filtering
            renderTable();
            renderPagination();
        } catch (error) {
            console.error("Error loading technicians:", error);
        }
    }

    // Add an event listener for the search button
    searchBtn.addEventListener("click", () => {
        console.log("Search button clicked"); // Debugging
        filterTechnicians(); // Call the filter function when the search button is clicked
    });

    function filterTechnicians() {
        const filterText = filterInput.value.trim().toLowerCase();
        const filterOption = filterDropdown.value;

        console.log("Filtering by:", filterOption); // Debugging
        console.log("Search text:", filterText); // Debugging

        const filteredData = allTechnicians.filter(technician => {
            if (filterOption === "name") {
                return technician.name.toLowerCase().includes(filterText);
            } else if (filterOption === "email") {
                return technician.email.toLowerCase().includes(filterText);
            }
            return true; // Default case if no filter is selected
        });

        console.log("Filtered Data:", filteredData); // Debugging

        // Check if the element exists before modifying its style
        const notFoundMessage = document.getElementById("no-results-message");
        if (notFoundMessage) {
            if (filteredData.length === 0) {
                notFoundMessage.style.display = 'block';
            } else {
                notFoundMessage.style.display = 'none';
            }
        }

        // Update techniciansData and reset pagination
        techniciansData = filteredData;
        currentPage = 1; // Reset to the first page when a filter is applied
        renderTable();
        renderPagination();
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

        paginationContainer.appendChild(createPageItem("Previous", currentPage - 1, currentPage === 1));

        for (let i = 1; i <= totalPages; i++) {
            paginationContainer.appendChild(createPageItem(i, i, false, currentPage === i));
        }

        paginationContainer.appendChild(createPageItem("Next", currentPage + 1, currentPage === totalPages));
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

                const confirmed = confirm("Are you sure you want to delete this technician? \nThis action cannot be undone.");
                if (!confirmed) return;

                try {
                    // Send a DELETE request to the backend
                    const response = await fetch(`/api/admin/technicians/${technicianId}`, {
                        method: "DELETE", headers: {
                            [csrfHeader]: csrfToken, 'Content-Type': 'application/json'
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

            // Create a JSON body for the request
            const jsonBody = JSON.stringify({
                name: name, email: email, password: password,
            });

            try {
                // Send POST request to add a new technician
                const response = await fetch("/api/admin/technicians", {
                    method: "POST", headers: {
                        "Content-Type": "application/json", "Accept": "application/json",
                    }, body: jsonBody,
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

//PENDING APPROVALS PAGE

document.addEventListener('DOMContentLoaded', function () {
    initializePage();
});

function initializePage() {
    // Pending users table
    if (document.getElementById('pending-users-table')) {
        loadPendingUsers();
    }

    // Pending requests card
    const pendingRequestsCard = document.getElementById('pending-requests-card');
    if (pendingRequestsCard) {
        pendingRequestsCard.addEventListener('click', () => {
            window.location.href = '/admin/technicians/pending-approvals';
        });

        fetchPendingRequestsCount();
        setInterval(fetchPendingRequestsCount, 60000);
    }

    // Back button
    const backButton = document.getElementById('back-to-admin-dashboard-btn');
    if (backButton) {
        backButton.addEventListener('click', (e) => {
            e.preventDefault();
            window.location.href = '/admin/technicians';
        });
    }
}

async function loadPendingUsers() {
    const tableBody = document.getElementById('pending-users-table');
    if (!tableBody) return;

    try {
        const response = await fetch('/api/admin/technicians/pending');
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`Failed to fetch pending users: ${response.status} ${errorText}`);
        }

        const users = await response.json();
        tableBody.innerHTML = '';

        if (users.length === 0) {
            const row = document.createElement('tr');
            row.innerHTML = `<td colspan="5" class="text-center">No pending approvals</td>`;
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
        const response = await fetch(`/api/admin/technicians/${userId}/approve`, {
            method: 'POST', headers: {
                [csrfHeader]: csrfToken, 'Content-Type': 'application/json'
            },

        });

        if (!response.ok) throw new Error('Failed to approve user');

        alert('User approved successfully');
        await loadPendingUsers();
    } catch (error) {
        console.error('Error approving user:', error);
        alert('Failed to approve user');
    }
}

async function rejectUser(userId) {
    try {
        const response = await fetch(`/api/admin/technicians/${userId}/reject`, {
            method: 'POST', headers: {
                [csrfHeader]: csrfToken, 'Content-Type': 'application/json'
            },
        });

        if (!response.ok) throw new Error('Failed to reject user');

        alert('User rejected successfully');
        await loadPendingUsers();
    } catch (error) {
        console.error('Error rejecting user:', error);
        alert('Failed to reject user');
    }
}

async function fetchPendingRequestsCount() {
    try {
        const response = await fetch('/api/admin/technicians/pending/count');
        if (!response.ok) throw new Error('Failed to fetch pending requests count');

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