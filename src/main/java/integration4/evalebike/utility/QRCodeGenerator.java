package integration4.evalebike.utility;
import integration4.evalebike.service.QrCodeService;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;

public class QRCodeGenerator {

    public static void main(String[] args) throws Exception {
        QrCodeService qrCodeService = new QrCodeService();
        String bikeQr = "123e4567-e89b-12d3-a456-426614174001" ;
        int width = 300;
        int height = 300;

        String base64Image = qrCodeService.generateQrCodeBase64(bikeQr, width, height);

        if (base64Image != null && base64Image.startsWith("data:image/png;base64,")) {
            // Remove prefix and decode
            String base64Data = base64Image.substring("data:image/png;base64,".length());
            byte[] imageBytes = Base64.getDecoder().decode(base64Data);

            // Save to file
            try (OutputStream stream = new FileOutputStream( "QR"+ bikeQr +" -bikeQrCode.png")) {
                stream.write(imageBytes);
                System.out.println("QR code saved as bikeQrCode.png");
            }
        } else {
            System.out.println("Failed to generate QR code.");
        }
    }
}
