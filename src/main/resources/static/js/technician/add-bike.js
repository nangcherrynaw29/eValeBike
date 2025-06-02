import {csrfToken, csrfHeader} from '../util/csrf.js';

const form = document.querySelector('#add-bike-form');
const dateInput = document.querySelector('#lastTestDate');

let dateError = document.querySelector('#date-error');
if (!dateError) {
    dateError = document.createElement('div');
    dateError.id = 'date-error';
    dateError.className = 'text-danger mt-1';
    dateError.style.display = 'none';
    dateInput.parentNode.appendChild(dateError);
}

form.addEventListener('submit', async e => {
    e.preventDefault();

    const getQueryParam = name => new URLSearchParams(window.location.search).get(name);
    const ownerId = parseInt(getQueryParam('ownerId'));

    dateError.style.display = 'none';
    dateError.textContent = '';

    const today = new Date();
    const selectedDate = new Date(dateInput.value);

    if (selectedDate > today) {
        dateError.textContent = 'Date cannot be in the future.';
        dateError.style.display = 'block';
        return;
    }

    const response = await fetch('/api/technician/bikes', {
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            [csrfHeader]: csrfToken

        },
        method: 'POST',
        body: JSON.stringify({
            brand: document.querySelector('#brand').value,
            model: document.querySelector('#model').value,
            chassisNumber: document.querySelector('#chassisNumber').value,
            productionYear: parseInt(document.querySelector('#productionYear').value),
            bikeSize: document.querySelector('#bikeSize').value,
            mileage: parseInt(document.querySelector('#mileage').value),
            gearType: document.querySelector('#gearType').value,
            engineType: document.querySelector('#engineType').value,
            powerTrain: document.querySelector('#powerTrain').value,
            accuCapacity: parseFloat(document.querySelector('#accuCapacity').value),
            maxSupport: parseFloat(document.querySelector('#maxSupport').value),
            maxEnginePower: parseFloat(document.querySelector('#maxEnginePower').value),
            nominalEnginePower: parseFloat(document.querySelector('#nominalEnginePower').value),
            engineTorque: parseFloat(document.querySelector('#engineTorque').value),
            lastTestDate: dateInput.value,
            bikeOwnerId: parseInt(ownerId)
        })
    });

    if (response.status === 201) {
        await response.json();
        window.location.href = `/technician/bikes/owner/${ownerId}`;
    } else {
        const errorData = await response.json().catch(() => null);
        dateError.textContent = (errorData && errorData.message) || 'Something went wrong while creating the bike.';
        dateError.style.display = 'block';
    }
});

document.addEventListener("DOMContentLoaded", function () {
    const backToDashboardBtn = document.getElementById("back-to-dashboard-btn");
    if (backToDashboardBtn) {
        backToDashboardBtn.addEventListener("click", function () {
            console.log("Back to dashboard button clicked!");
            window.location.href = "/technician/bikes";  // needs to be changed to window.history.back(); after
        });
    }
});