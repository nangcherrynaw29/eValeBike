document.addEventListener("DOMContentLoaded", () => {
    const scanButton = document.getElementById("scanButton");
    const scannerContainer = document.getElementById("scannerContainer");
    const stopScanButton = document.getElementById("stopScanButton");

    let html5QrCode;

    scanButton.addEventListener("click", () => {
        scannerContainer.style.display = "block";
        scanButton.style.display = "none";

        html5QrCode = new Html5Qrcode("reader");

        html5QrCode.start(
            { facingMode: "environment" },
            {
                fps: 10,
                qrbox: 250,
            },
            (decodedText, decodedResult) => {
                // When QR code is scanned, stop the scanner
                html5QrCode.stop().then(() => {
                    scannerContainer.style.display = "none";
                    scanButton.style.display = "inline-block";

                    // Redirect to the related page with the scanned QR code
                    // Make sure to encodeURIComponent the decodedText for safety
                    const url = `/technician/reports-by-bike/${encodeURIComponent(decodedText)}`;
                    window.location.href = url;
                }).catch(err => {
                    console.error("Failed to stop scanner: ", err);
                });
            },
            (errorMessage) => {
                console.log(errorMessage);
            }
        );
    });

    stopScanButton.addEventListener("click", () => {
        html5QrCode.stop().then(() => {
            scannerContainer.style.display = "none";
            scanButton.style.display = "inline-block";
        }).catch((err) => {
            console.error("Error stopping the scanner: ", err);
        });
    });
});
