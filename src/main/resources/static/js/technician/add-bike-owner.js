const form = document.querySelector('#add-bikeOwner-form');
form.addEventListener('submit', async e => {
    e.preventDefault();
    const response = await fetch('/api/technician/bikeOwners', {
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        method: 'POST',
        body: JSON.stringify({
            name: document.querySelector('#name').value,
            email: document.querySelector('#email').value,
            phoneNumber: document.querySelector('#phoneNumber').value,
            birthDate: document.querySelector('#birthDate').value,
        })
    });
    if (response.status === 201) {
        const bikeOwner = await response.json();
        window.location = `/technician/bike-owners`;
    } else {
        alert('Something went wrong while creating the bike owner');
    }
});

document.addEventListener("DOMContentLoaded", function () {
    const backToDashboardBtn = document.getElementById("back-to-dashboard-btn");
    if (backToDashboardBtn) {
        backToDashboardBtn.addEventListener("click", function () {
            console.log("Back to dashboard button clicked!");
            window.location.href = "/technician/bike-owners";
        });
    }
});