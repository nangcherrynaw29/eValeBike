
document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('form');
    const checkboxes = document.querySelectorAll('.form-check-input');
    const activateButton = document.querySelector('.btn-primary');

    // Initially disable the button
    activateButton.classList.add('disabled');

    // Function to check if all checkboxes are checked
    function updateButtonState() {
        const allChecked = Array.from(checkboxes).every(checkbox => checkbox.checked);
        if (allChecked) {
            activateButton.classList.remove('disabled');
        } else {
            activateButton.classList.add('disabled');
        }
    }

    // Add event listeners to all checkboxes
    checkboxes.forEach(checkbox => {
        checkbox.addEventListener("change", updateButtonState);
    });

    // Prevent form submission if not all checkboxes are checked
    form.addEventListener('submit', function(event) {
        const allChecked = Array.from(checkboxes).every(checkbox => checkbox.checked);
        if (!allChecked) {
            event.preventDefault();
        }
    });
});