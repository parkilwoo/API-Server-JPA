package park.ilwoo.jpa_api.common;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * AES Encrypt Util Class
 */
public class AESEncryption {

    private static final byte[] key = "0108070116692101".getBytes(StandardCharsets.UTF_8);
    private static final String ALGORITHM = "AES";

    public static AESEncryption instance = new AESEncryption();

    public String encrypt(String plainText) throws Exception {
          return Base64.getEncoder().encodeToString(this.encrypt(plainText.getBytes(StandardCharsets.UTF_8)));
    }

    public String decrypt(String encText) throws Exception {
        return new String(decrypt(Base64.getDecoder().decode(encText)), StandardCharsets.UTF_8);
    }

    public boolean isEqual(String plainText, String encText) throws Exception {
        String decText = decrypt(encText);
        return decText.equals(plainText);
    }

    private byte[] encrypt(byte[] plainText) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        return cipher.doFinal(plainText);
    }

    private byte[] decrypt(byte[] cipherText) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        return cipher.doFinal(cipherText);
    }

}
