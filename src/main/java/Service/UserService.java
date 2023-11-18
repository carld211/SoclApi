package Service;

import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class UserService {

    public String hashAndStoreEmail(String email) {
        String hashedEmail = hashEmail(email);
        storeHashedEmail(hashedEmail);
        return hashedEmail;
    }

    private String hashEmail(String email) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(email.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void storeHashedEmail(String hashedEmail) {
        try (FileWriter writer = new FileWriter("hashed_emails.txt", true)) {
            writer.write(hashedEmail + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
