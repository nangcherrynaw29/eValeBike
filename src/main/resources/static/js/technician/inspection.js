document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("visualForm");
    // const testId = new URLSearchParams(window.location.search).get('testId');
    const testIdInput = document.getElementById("testId");
    const testId = testIdInput ? testIdInput.value : null;

    form.addEventListener("submit", async (event) => {
        event.preventDefault();
        const submitButton = form.querySelector('button[type="submit"]');
        submitButton.disabled = true;
        submitButton.textContent = 'Submitting...';

        // Gather form data
        const getSelectedValue = (name) => {
            const selectedRadio = document.querySelector(`input[name="${name}"]:checked`);
            return selectedRadio ? selectedRadio.value : 'n/a';
        };

        const formData = {
            testId: testId,
            tires: getSelectedValue('tires'),
            bell: getSelectedValue('bell'),
            cranks: getSelectedValue('cranks'),
            electricWiring: getSelectedValue('electricWiring'),
            frontFork: getSelectedValue('frontFork'),
            handles: getSelectedValue('handles'),
            chainBelt: getSelectedValue('chainBelt'),
            pedals: getSelectedValue('pedals'),
            reflectors: getSelectedValue('reflectors'),
            brakePads: getSelectedValue('brakePads'),
            brakeHandles: getSelectedValue('brakeHandles'),
            brakeCables: getSelectedValue('brakeCables'),
            brakeDiscs: getSelectedValue('brakeDiscs'),
            mudguards: getSelectedValue('mudguards'),
            handleBar: getSelectedValue('handleBar'),
            rearSprocket: getSelectedValue('rearSprocket'),
            frontSprocket: getSelectedValue('frontSprocket'),
            rimsSpokes: getSelectedValue('rimsSpokes'),
            rearSuspension: getSelectedValue('rearSuspension'),
            suspensionFront: getSelectedValue('suspensionFront'),
            saddle: getSelectedValue('saddle')
        };

        const headers = {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        };


        try {
            const response = await fetch(`/api/technician/save-visual-inspection/${testId}`, {
                method: 'POST',
                headers,
                body: JSON.stringify(formData)
            });

            if (response.ok) {
                window.location.href = '/technician/report/' + testId;
            } else {
                const errorText = await response.text();
                alert(`Failed to submit inspection: ${errorText || 'Unknown error'}`);
            }
        } catch (error) {
            console.error('Error during submission:', error);
            alert('An unexpected error occurred. Please try again.');
        } finally {
            submitButton.disabled = false;
            submitButton.textContent = 'Submit';
        }
    });
});