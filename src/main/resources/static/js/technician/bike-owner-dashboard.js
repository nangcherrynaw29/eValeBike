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