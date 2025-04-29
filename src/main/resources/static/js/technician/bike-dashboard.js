document.addEventListener("DOMContentLoaded", () => {
    const removeBikeButtons = document.querySelectorAll('.remove-bike-button');
    removeBikeButtons.forEach(button => button.addEventListener('click', async e => {
        const bikeId = button.getAttribute('data-bike-id');
        button.disabled = true;
        const result = await fetch(`/api/technician/bikes/${bikeId}`, {method: 'DELETE'});
        button.disabled = false;
        if (result.status === 204) {
            document.querySelector(`#bike-${bikeId}`).remove();
        }
    }));
});