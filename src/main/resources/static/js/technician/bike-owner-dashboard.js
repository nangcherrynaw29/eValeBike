document.addEventListener("DOMContentLoaded", () => {
    const addBikeOwnerBtn = document.querySelector("#add-bikeowner-btn");

    if (addBikeOwnerBtn) {
        addBikeOwnerBtn.addEventListener("click", async (e) => {
            e.preventDefault();

            try {
                window.location.href = "/technician/bike-owners/add";
            } catch (error) {
                console.error("Error navigating:", error);
                alert("Something went wrong while redirecting.");
            }
        });
    }

    const removeBikeOwnersButtons = document.querySelectorAll('.remove-bikeOwners-button');
    removeBikeOwnersButtons.forEach(button => button.addEventListener('click', async e => {
        const bikeOwnerId = button.getAttribute('data-bikeOwner-id');
        button.disabled = true;
        const result = await fetch(`/api/technician/bikeOwners/${bikeOwnerId}`, {method: 'DELETE'});
        button.disabled = false;
        if (result.status === 204) {
            document.querySelector(`#owner-${bikeOwnerId}`).remove();
        }
    }));
});

document.addEventListener("DOMContentLoaded", () => {
    const rowsPerPage = 5;
    let currentPage = 1;
    let bikeOwners = [];

    const tbody = document.getElementById("bike-owner-table-body");
    const pagination = document.getElementById("pagination-controls");

    fetch('/api/technician/bikeOwners')
        .then(res => res.json())
        .then(data => {
            bikeOwners = data;
            renderTable();
            renderPagination();
        });

    function renderTable() {
        tbody.innerHTML = '';
        const start = (currentPage - 1) * rowsPerPage;
        const end = start + rowsPerPage;
        const pageData = bikeOwners.slice(start, end);

        pageData.forEach(owner => {
            const tr = document.createElement('tr');
            tr.id = `owner-${owner.id}`;
            tr.innerHTML = `
                <td>${owner.name}</td>
                <td>${owner.email}</td>
                <td>${owner.phoneNumber}</td>
                <td>${owner.birthDate}</td>
                <td>
                    <a href="/technician/bikes/owner/${owner.id}" class="btn btn-primary">All Bikes</a>
                </td>
                <td>
                    <button class="btn btn-outline-danger remove-bikeOwners-button" data-bikeOwner-id="${owner.id}">
                        <i class="fa-solid fa-trash"></i>
                    </button>
                </td>
            `;
            tbody.appendChild(tr);
        });

        document.querySelectorAll('.remove-bikeOwners-button').forEach(button => {
            button.addEventListener('click', async e => {
                const bikeOwnerId = button.getAttribute('data-bikeOwner-id');
                button.disabled = true;
                const result = await fetch(`/api/technician/bikeOwners/${bikeOwnerId}`, { method: 'DELETE' });
                button.disabled = false;
                if (result.status === 204) {
                    document.querySelector(`#owner-${bikeOwnerId}`).remove();
                }
            });
        });
    }

    function renderPagination() {
        pagination.innerHTML = '';
        const totalPages = Math.ceil(bikeOwners.length / rowsPerPage);

        const addPageItem = (label, page, disabled = false, active = false) => {
            const li = document.createElement('li');
            li.className = `page-item ${disabled ? 'disabled' : ''} ${active ? 'active' : ''}`;
            const a = document.createElement('a');
            a.className = 'page-link';
            a.href = '#';
            a.textContent = label;
            if (!disabled) {
                a.addEventListener('click', (e) => {
                    e.preventDefault();
                    currentPage = page;
                    renderTable();
                    renderPagination();
                });
            }
            li.appendChild(a);
            pagination.appendChild(li);
        };

        addPageItem('Previous', currentPage - 1, currentPage === 1);

        for (let i = 1; i <= totalPages; i++) {
            addPageItem(i, i, false, i === currentPage);
        }

        addPageItem('Next', currentPage + 1, currentPage === totalPages);
    }

    const addBikeOwnerBtn = document.querySelector("#add-bikeowner-btn");
    if (addBikeOwnerBtn) {
        addBikeOwnerBtn.addEventListener("click", (e) => {
            e.preventDefault();
            window.location.href = "/technician/bike-owners/add";
        });
    }
});