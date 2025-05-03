document.addEventListener("DOMContentLoaded", async () => {
    const addAdminBtn = document.querySelector("#add-admin-btn");
    const adminTableBody = document.getElementById("admin-table-body");
    const paginationContainer = document.querySelector(".pagination");

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
            const response = await fetch("/api/super-admin/admins");
            if (!response.ok) throw new Error("Failed to fetch admins");

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
                <td>${admin.companyName}</td>
                <td>
                    <button class="btn btn-outline-danger delete-btn" data-id="${admin.id}">
                        <i class="fa-solid fa-trash"></i>
                    </button>
                </td>
            `;
            adminTableBody.appendChild(row);
        });
        attachDeleteEventListeners();
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

    loadAdminData();

    function attachDeleteEventListeners() {
        document.querySelectorAll(".delete-btn").forEach(button => {
            button.addEventListener("click", async (e) => {
                e.preventDefault();

                const btn = e.currentTarget;
                const adminRow = btn.closest("tr");
                const adminId = btn.dataset.id;

                if (!adminId || !adminRow) {
                    alert("Admin ID or row not found.");
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
                        loadAdminData();
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
});


///Need to implement edit button




