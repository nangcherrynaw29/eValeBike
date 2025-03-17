const form = document.querySelector('#add-bike-form');
form.addEventListener('submit', async e => {
    e.preventDefault();
    const response = await fetch('/api/technician/bikes', {
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
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
            lastTestDate: document.querySelector('#lastTestDate').value
        })
    });
    if (response.status === 201) {
        const bike = await response.json();
        window.location = `/technician/bikes`;
    } else {
        alert('Something went wrong while creating the bike');
    }
});