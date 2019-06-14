package util;

import serverException.socketError;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class Hash {

    public static byte[] generateHash(String message)
            throws socketError {

        byte[] result = null;

        try {
            byte[] salt = createSalt();
            result = pbkdf2(message.toCharArray(), salt);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new socketError("E4001");
        }

        return result;
    }


    public static byte[] createSalt() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[32];
        random.nextBytes(salt);
        return salt;
    }

    private static byte[] pbkdf2(char[] password, byte[] salt)
            throws InvalidKeySpecException, NoSuchAlgorithmException {

        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        KeySpec ks = new PBEKeySpec(password, salt, 10000, 64);
        SecretKey s = f.generateSecret(ks);
        return s.getEncoded();
    }


}
