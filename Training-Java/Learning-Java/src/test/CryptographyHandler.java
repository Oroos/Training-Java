package test;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class CryptographyHandler
{
    private Cipher encryptionCipher;
    private Cipher decryptionCipher;

    private Cipher makeCipher(char[] password, Boolean encryptMode) throws GeneralSecurityException, FileNotFoundException, IOException, ClassNotFoundException
    {
        byte[] bytes = new byte[20];
        SecureRandom.getInstanceStrong()
                    .nextBytes(bytes);

        final SecretKeyFactory factory = SecretKeyFactory.getInstance("PBEWithHmacSHA512AndAES_256");
        final PBEKeySpec keySpec = new PBEKeySpec(password, bytes, 20000);
        SecretKey key = factory.generateSecret(keySpec);

        if(encryptMode)
        {
            encryptionCipher = Cipher.getInstance("PBEWithHmacSHA512AndAES_256/CBC/PKCS5Padding");
            encryptionCipher.init(Cipher.ENCRYPT_MODE, key, encryptionCipher.getParameters());

            return encryptionCipher;
        }
        else
        {
            decryptionCipher = Cipher.getInstance("PBEWithHmacSHA512AndAES_256/CBC/PKCS5Padding");
            decryptionCipher.init(Cipher.DECRYPT_MODE, key, encryptionCipher.getParameters());

            return decryptionCipher;
        }
    }

    public void encryptFile(Path decryptedFilePath, char[] password) throws IOException, GeneralSecurityException, ClassNotFoundException
    {
        Files.deleteIfExists(Paths.get(decryptedFilePath.getParent() + "/temp.txt"));
        Path tempFilePath = Files.createFile(Paths.get(decryptedFilePath.getParent() + "/temp.txt"));

        try(CipherOutputStream cipherOutputStream = new CipherOutputStream(new FileOutputStream(tempFilePath.toFile()), makeCipher(password, true)))
        {
            cipherOutputStream.write(Files.readAllBytes(decryptedFilePath));
        }
        finally
        {
            Files.deleteIfExists(decryptedFilePath);
            tempFilePath.toFile()
                        .renameTo(decryptedFilePath.toFile());
        }
    }

    public void decryptFile(Path encryptedFilePath, char[] password) throws GeneralSecurityException, IOException, ClassNotFoundException
    {
        Files.deleteIfExists(Paths.get(encryptedFilePath.getParent() + "/temp.txt"));
        Path tempFilePath = Files.createFile(Paths.get(encryptedFilePath.getParent() + "/temp.txt"));

        try(CipherInputStream cipherInputStream = new CipherInputStream(new FileInputStream(encryptedFilePath.toFile()), makeCipher(password, false));
            BufferedOutputStream bufferedOuputStream = new BufferedOutputStream(new FileOutputStream(tempFilePath.toFile())))
        {
            int bytesLeft;

            while( (bytesLeft = cipherInputStream.read()) != -1)
            {
                bufferedOuputStream.write(bytesLeft);
            }
        }
        finally
        {
            Files.deleteIfExists(encryptedFilePath);
            tempFilePath.toFile()
                        .renameTo(encryptedFilePath.toFile());
        }
    }

    public static void main(String... args) throws IOException, GeneralSecurityException, ClassNotFoundException
    {
        CryptographyHandler c = new CryptographyHandler();
        char[] pw = {'2'};
        c.encryptFile(Paths.get(".\\src\\test\\testfile"), pw);
        //        c.decryptFile(Paths.get(".\\src\\test\\testfile"),
        //                      pw);
    }

}
