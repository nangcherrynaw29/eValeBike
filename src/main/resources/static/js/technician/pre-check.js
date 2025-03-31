document.addEventListener("DOMContentLoaded", function () {
    const checkboxes = document.querySelectorAll(".form-check-input");
    const activateButton = document.getElementById("activateTestBench");

    function updateButtonState() {
        // Check if all checkboxes are checked
        const allChecked = Array.from(checkboxes).every(checkbox => checkbox.checked);
        activateButton.disabled = !allChecked;
    }

    // Add event listeners to all checkboxes
    checkboxes.forEach(checkbox => {
        checkbox.addEventListener("change", updateButtonState);
    });

    // Run initially in case checkboxes are pre-checked
    updateButtonState();
});