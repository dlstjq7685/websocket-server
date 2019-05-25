package util;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Hash {

    public static String getSHA256(String message){

        String hash = null;

        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.reset();
            digest.update(message.getBytes("utf-8"));
            hash = String.format("%064x",new BigInteger(1,digest.digest()));
        } catch (Exception e){

        }
        return hash;
    }

    public static String getSHA512(String message){

        String hash = null;

        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.reset();
            digest.update(message.getBytes("utf-8"));
            hash = String.format("%0128x",new BigInteger(1,digest.digest()));
        } catch (Exception e){

        }
        return hash;
    }

}
