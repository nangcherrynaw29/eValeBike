import { csrfToken, csrfHeader } from '../util/csrf.js';

document.addEventListener("DOMContentLoaded", () => {
    const removeBikeButtons = document.querySelectorAll('.remove-bike-button');
    removeBikeButtons.forEach(button => button.addEventListener('click', async e => {
        e.preventDefault();
        const bikeId = button.getAttribute('data-bike-id');

        const confirmed = confirm("Are you sure you want to delete this bike? \nThis action is irreversible.");
        if (!confirmed) return;

        button.disabled = true;
        try {
            const result = await fetch(`/api/technician/bikes/${bikeId}`, {
                method: 'DELETE', headers: {
                    [csrfHeader]: csrfToken, 'Content-Type': 'application/json'
                }
            });
            button.disabled = false;

            if (result.status === 204) {
                document.querySelector(`#bike-${bikeId}`)?.remove();
            } else {
                alert("Failed to delete the bike. Please try again.");
            }
        } catch (error) {
            console.error("Error deleting bike:", error);
            alert("An error occurred while deleting the bike.");
            button.disabled = false;
        }
    }));
});

const filterTypeSelect = document.getElementById("filterType");
const filterInput = document.getElementById("filterValue");
const searchBtn = document.getElementById("searchBtn");

searchBtn.addEventListener("click", () => {
    const type = filterTypeSelect.value;
    const value = filterInput.value;

    fetch(`/api/technician/bikes/filterBikes?filterType=${type}&filterValue=${value}`)
        .then(res => res.json())
        .then(bikes => {
            const tbody = document.querySelector("tbody");
            tbody.innerHTML = "";

            bikes.forEach(bike => {
                const row = document.createElement("tr");
                row.innerHTML = `
                   <td><div class="qr-wrapper"> <img th:src="${bike.qrCodeImage}" alt="QR Code" class="img-thumbnail qr-code-img"></div></td>
                    <td>${bike.brand}</td>
                    <td>${bike.model}</td>
                    <td>${bike.chassisNumber}</td>
                    <td>${bike.productionYear}</td>
                    <td>${bike.bikeSize}</td>
                    <td>${bike.mileage}</td>
                    <td>${bike.gearType}</td>
                    <td>${bike.engineType}</td>
                    <td>${bike.powerTrain}</td>
                    <td>${bike.accuCapacity}</td>
                    <td>${bike.maxSupport}</td>
                    <td>${bike.maxEnginePower}</td>
                    <td>${bike.nominalEnginePower}</td>
                    <td>${bike.engineTorque}</td>
                    <td>${bike.lastTestDate}</td>
                    <td><a href="/technician/pre-check/${bike.bikeQR}" class="btn btn-primary btn-sm"><i class="fa-solid fa-list-check"></i> Pre-test Check</a></td>
                    <td><button class="btn btn-outline-danger remove-bike-button" data-bike-id="${bike.bikeQR}"><i class="fa-solid fa-trash"></i></button></td>
                `;
                tbody.appendChild(row);
            });
            if (bikes.length === 0) {
                document.getElementById("no-results-message").style.display = "block";
            } else {
                document.getElementById("no-results-message").style.display = "none";
            }
        });
});