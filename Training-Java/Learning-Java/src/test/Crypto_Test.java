package test;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Crypto_Test
{
    public static void main(final String[] args) throws Exception
    {
        final String message = "This is the secret message... BOO!";
        System.out.println("Original : " + message);
        final byte[] messageBytes = message.getBytes(StandardCharsets.US_ASCII);

        final String password = "some password";
        final byte[] salt = "would be random".getBytes(StandardCharsets.US_ASCII);

        // Create the Key
        final SecretKeyFactory factory = SecretKeyFactory.getInstance("PBEWithHmacSHA512AndAES_256");
        final PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 5000);
        SecretKey key = factory.generateSecret(keySpec);

        // Build the encryption cipher.
        final Cipher cipherEncrypt = Cipher.getInstance("PBEWithHmacSHA512AndAES_256/CBC/PKCS5Padding");
        cipherEncrypt.init(Cipher.ENCRYPT_MODE, key, cipherEncrypt.getParameters());

        // Encrypt!
        final byte[] ciphertext = cipherEncrypt.doFinal(messageBytes);

        // Build the decryption cipher.
        final Cipher cipherDecrypt = Cipher.getInstance("PBEWithHmacSHA512AndAES_256/CBC/PKCS5Padding");
        cipherDecrypt.init(Cipher.DECRYPT_MODE, key, cipherEncrypt.getParameters());

        // Decrypt!
        final String decrypted = new String(cipherDecrypt.doFinal(ciphertext), StandardCharsets.US_ASCII);
        System.out.println("Decrypted: " + decrypted);
    }
}
