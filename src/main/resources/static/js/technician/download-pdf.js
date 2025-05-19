
document.addEventListener('DOMContentLoaded', function () {
    const button = document.getElementById('downloadPdfBtn');
    if (button) {
        button.addEventListener('click', downloadPDF);
    }
});

async function downloadPDF() {
    const { jsPDF } = window.jspdf;
    const button = document.getElementById('downloadPdfBtn');
    const originalButtonText = button.textContent;

    // Set loading state
    button.disabled = true;
    button.textContent = 'Generating PDF...';

    // Target the card elements containing the tables
    const cards = document.querySelectorAll('.row .card');
    if (!cards.length) {
        console.error('No tables found');
        button.disabled = false;
        button.textContent = originalButtonText;
        alert('Error: No tables found to generate PDF.');
        return;
    }

    try {
        const pdf = new jsPDF('p', 'mm', 'a4');
        const pageWidth = pdf.internal.pageSize.getWidth();
        const pageHeight = pdf.internal.pageSize.getHeight();
        const margin = 10;
        const contentWidth = pageWidth - 2 * margin;
        let currentY = margin;

        // Process each card (table) sequentially
        for (let i = 0; i < cards.length; i++) {
            const card = cards[i];
            const canvas = await html2canvas(card, {
                scale: 2,
                useCORS: true,
                backgroundColor: '#ffffff',
                windowWidth: card.scrollWidth,
                windowHeight: card.scrollHeight,
            });

            const imgData = canvas.toDataURL('image/png');
            const imgProps = pdf.getImageProperties(imgData);
            const imgWidth = contentWidth;
            const imgHeight = (imgProps.height * imgWidth) / imgProps.width;

            // Check if adding the image exceeds the page height
            if (currentY + imgHeight > pageHeight - margin) {
                pdf.addPage();
                currentY = margin;
            }

            // Add the image to the PDF
            pdf.addImage(imgData, 'PNG', margin, currentY, imgWidth, imgHeight);
            currentY += imgHeight + 5; // Add small gap between tables
        }

        pdf.save('test-report-tables.pdf');
    } catch (error) {
        console.error('Error generating PDF:', error);
        alert('Failed to generate PDF. Please try again.');
    } finally {
        // Restore button state
        button.disabled = false;
        button.textContent = originalButtonText;
    }
}