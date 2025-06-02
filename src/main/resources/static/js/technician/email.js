import {csrfToken, csrfHeader} from '../util/csrf.js';

async function sendPDFtoServer() {
    const button = document.getElementById('sendEmailBtn');
    const originalButtonText = button.textContent;
    button.disabled = true;
    button.textContent = 'Sending PDF...';

    try {
        const {jsPDF} = window.jspdf;
        const cards = document.querySelectorAll('.row .card');
        if (!cards.length) throw new Error('No tables found');

        const pdf = new jsPDF('p', 'mm', 'a4');
        const pageWidth = pdf.internal.pageSize.getWidth();
        const pageHeight = pdf.internal.pageSize.getHeight();
        const margin = 10;
        const contentWidth = pageWidth - 2 * margin;
        let currentY = margin;

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

            if (currentY + imgHeight > pageHeight - margin) {
                pdf.addPage();
                currentY = margin;
            }

            pdf.addImage(imgData, 'PNG', margin, currentY, imgWidth, imgHeight);
            currentY += imgHeight + 5;
        }

        const pdfBlob = pdf.output('blob');
        const reportId = document.body.dataset.reportId;

        // Step 1: Upload PDF
        const uploadResponse = await fetch(`/api/technician/upload-report-pdf/${reportId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/pdf',
                [csrfHeader]: csrfToken
            },
            body: pdfBlob,
        });

        if (!uploadResponse.ok) {
            const errorText = await uploadResponse.text();
            throw new Error('PDF upload failed: ' + errorText);
        }

        // Step 2: Trigger email
        const emailResponse = await fetch(`/api/technician/send-report-email/${reportId}`, {
            method: 'POST',
            headers: {
                [csrfHeader]: csrfToken
            }
        });

        if (emailResponse.ok) {
            alert('PDF uploaded and email sent successfully!');
        } else {
            const errorText = await emailResponse.text();
            throw new Error('Email sending failed: ' + errorText);
        }

    } catch (err) {
        console.error(err);
        alert('Error: ' + err.message);
    } finally {
        button.disabled = false;
        button.textContent = originalButtonText;
    }
}

document.getElementById('sendEmailBtn').addEventListener('click', sendPDFtoServer);
