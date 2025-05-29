import {csrfToken, csrfHeader} from '../util/csrf.js';

document.addEventListener("DOMContentLoaded", async () => {
    const addAdminBtn = document.querySelector("#add-admin-btn");
    const adminTableBody = document.getElementById("admin-table-body");
    const paginationContainer = document.querySelector(".pagination");

    const filterInput = document.getElementById("filter-input");
    const filterDropDown = document.getElementById("filter-dropdown");
    const searchBtn = document.getElementById("search-btn");
    const tableBody = document.getElementById("admin-table-body");
    const noResultMessage = document.getElementById("no-results-message");


    searchBtn.addEventListener('click', () => {
        const query = filterInput.value.trim();
        const field = filterDropDown.value;
        if (query) fetchFilteredAdmins(query, field);
    });

    function fetchFilteredAdmins(query, field) {
        let params = new URLSearchParams();
        params.append('type', field);
        params.append('value', query);

        const url = `/api/admin/technicians/filterAdmin?${params.toString()}`;
        console.log('Request URL:', url);

        fetch(url, {
            method: 'GET',
            headers: {
                [csrfHeader]: csrfToken,
                'Accept': 'application/json'
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                updateAdminTable(data);
            })
            .catch(error => {
                console.error('Error fetching admins:', error);
            });
    }

    function updateAdminTable(admins) {
        tableBody.innerHTML = '';

        if (admins.length === 0) {
            noResultMessage.style.display = 'block';
            return;
        }

        noResultMessage.style.display = 'none';

        admins.forEach(admin => {
            const row = document.createElement('tr');

            row.innerHTML = `
            <td>${admin.name}</td>
            <td>${admin.email}</td>
            <td>${admin.company.name || ''}</td>
        `;

            tableBody.appendChild(row);
        });
    }

    const adminsPerPage = 5;
    let currentPage = 1;
    let adminData = [];

    if (addAdminBtn) {
        addAdminBtn.addEventListener("click", (e) => {
            e.preventDefault();
            window.location.href = "/super-admin/admins/add";
        });
    }

    async function loadAdminData() {
        try {
            const response = await fetch("/api/super-admin/admins", {
                headers: {
                    [csrfHeader]: csrfToken,
                    "Accept": "application/json"
                }
            });
            if (!response.ok) throw new Error("Failed to load admins");
            adminData = await response.json();
            renderTable();
            renderPagination();
        } catch (error) {
            console.error("Error loading admins:", error);
        }
    }

    function renderTable() {
        adminTableBody.innerHTML = "";

        const start = (currentPage - 1) * adminsPerPage;
        const end = start + adminsPerPage;
        const currentAdmins = adminData.slice(start, end);

        currentAdmins.forEach(admin => {
            const row = document.createElement("tr");
            row.dataset.id = admin.id;
            row.innerHTML = `
                <td>${admin.name}</td>
                <td>${admin.email}</td>
                <td>${admin.company.name}</td>
                <td>
                    <button class="btn btn-sm btn-outline-primary edit-btn me-2" data-id="${admin.id}">
                        <i class="fa-solid fa-pen"></i>
                    </button>
                    <button class="btn btn-sm btn-outline-danger delete-btn" data-id="${admin.id}">
                        <i class="fa-solid fa-trash"></i>
                    </button>
                </td>
            `;
            adminTableBody.appendChild(row);
        });

        attachDeleteEventListeners();
        attachEditEventListeners();
    }

    function renderPagination() {
        paginationContainer.innerHTML = "";
        const totalPages = Math.ceil(adminData.length / adminsPerPage);

        const createPageItem = (label, page, disabled = false, active = false) => {
            const li = document.createElement("li");
            li.className = `page-item ${disabled ? "disabled" : ""} ${active ? "active" : ""}`;

            const a = document.createElement("a");
            a.className = "page-link";
            a.href = "#";
            a.textContent = label;

            a.addEventListener("click", (e) => {
                e.preventDefault();
                if (!disabled && currentPage !== page) {
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

    function attachDeleteEventListeners() {
        document.querySelectorAll(".delete-btn").forEach(button => {
            button.addEventListener("click", async (e) => {
                e.preventDefault();
                const btn = e.currentTarget;
                const adminId = btn.dataset.id;

                if (!adminId) return alert("No admin ID found.");

                const confirmed = confirm("Are you sure you want to delete this admin? \nThis action cannot be undone.");
                if (!confirmed) return;

                try {
                    const response = await fetch(`/api/super-admin/admins/${adminId}`, {
                        method: "DELETE",
                        headers: {[csrfHeader]: csrfToken, "Accept": "application/json"}
                    });

                    if (response.ok) {
                        await loadAdminData();
                    } else {
                        alert("Failed to delete admin.");
                    }
                } catch (error) {
                    console.error("Delete error:", error);
                    alert("An error occurred while deleting.");
                }
            });
        });
    }

    function attachEditEventListeners() {
        document.querySelectorAll(".edit-btn").forEach(button => {
            button.addEventListener("click", (e) => {
                e.preventDefault();
                const btn = e.currentTarget;
                const adminId = btn.dataset.id;
                if (!adminId) return alert("No admin ID found.");
                window.location.href = `/super-admin/admins/edit/${adminId}`;
            });
        });
    }

    await loadAdminData();
});

// SECONDARY PAGE INITIALIZER FOR PENDING USERS AND DASHBOARD

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
            window.location.href = '/super-admin/admins/pending-approvals';
        });

        fetchPendingRequestsCount();
        setInterval(fetchPendingRequestsCount, 60000);
    }

    // Back button
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
        const response = await fetch('/api/super-admin/admins/pending', {
                headers: {
                    [csrfHeader]: csrfToken,
                    'Accept': 'application/json'
                }
            }
        );
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
        const response = await fetch(`/api/super-admin/admins/${userId}/approve`, {
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
        const response = await fetch(`/api/super-admin/admins/${userId}/reject`, {
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
        const response = await fetch('/api/super-admin/admins/pending/count', {
            headers: {
                [csrfHeader]: csrfToken,
                'Accept': 'application/json'
            }
        });
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