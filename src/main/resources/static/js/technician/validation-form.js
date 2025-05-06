document.getElementById("manualTestForm").addEventListener("submit", function(event) {
    const maxLimit = 2000;
    const fields = ["accuCapacity", "maxSupport", "maxEnginePower", "nominalEnginePower", "engineTorque"];
    let isValid = true;

    fields.forEach(field => {
        const value = parseFloat(document.querySelector(`[name='${field}']`).value);
        if (value < 1 || value > maxLimit) {
            isValid = false;
            alert(`${field} must be between 1 and ${maxLimit}`);
        }
    });

    if (!isValid) {
        event.preventDefault();
    }
});
