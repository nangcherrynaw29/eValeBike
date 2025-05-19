
document.getElementById('compareButton').addEventListener('click', function () {
    const selected = Array.from(document.querySelectorAll('input[name="selectedReports"]:checked'))
        .map(cb => cb.value);

    if (selected.length < 2 || selected.length > 2) {
        alert("Please select TWO reports to compare.");
        return;
    }

    // Ensure the URL is correctly formatted
    const url = "/technician/compare/" + selected[0] + "/" + selected[1];

    window.location.href = url;
});