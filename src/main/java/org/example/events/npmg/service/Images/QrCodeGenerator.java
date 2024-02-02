package org.example.events.npmg.service.Images;

import lombok.RequiredArgsConstructor;
import net.glxn.qrgen.javase.QRCode;
import org.example.events.npmg.exceptions.TextToBigException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class QrCodeGenerator {
    private static final int QR_CODE_SIZE = 500;
    private static final String path = "src/main/resources/qr_codes/";

    @Value("${bonda.app.qrcode.secret}")
    private String secretKey;

    /**
     * Generates a QR code image from the given name and text.
     *
     * @param name The name of the QR code image.
     * @param text The text to be encrypted and encoded in the QR code.
     * @return The file path of the generated QR code image.
     * @throws TextToBigException If the length of the text exceeds 250 characters.
     */
    public String generateQrCode(String name, String text) {
        if (text.length() > 250) {
            throw new TextToBigException("Text for QR code is too big!");
        }
        String encryptedText = encrypt(text);
        String imagePath = path + name;
        ByteArrayOutputStream bout =
                QRCode
                        .from(encryptedText)
                        .withSize(QR_CODE_SIZE, QR_CODE_SIZE)
                        .stream();

        Path filePath = Paths.get(imagePath);
        try {
            Files.write(filePath, bout.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("QR code generated successfully!     " + decrypt(encryptedText));
        return filePath.toString();
    }

    private String encrypt(String data) {
        try {
            Key key = new SecretKeySpec(secretKey.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedData = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            throw new RuntimeException("Error while encrypting: " + e.getMessage());
        }
    }

    private String decrypt(String data) {
        try {
            Key key = new SecretKeySpec(secretKey.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decodedData = Base64.getDecoder().decode(data);
            byte[] decryptedData = cipher.doFinal(decodedData);
            return new String(decryptedData);
        } catch (Exception e) {
            throw new RuntimeException("Error while decrypting: " + e.getMessage());
        }
    }
}