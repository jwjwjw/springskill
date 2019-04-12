package com.jw.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class AESUtil {

    private static SecretKeySpec  secretKey;
    private static byte[] key;

    public static void setKey(String myKey){
        MessageDigest sha = null;
        try{
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key,16);
            secretKey = new SecretKeySpec(key,"AES");
        }
        catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }

    public static String encrypt(String plaintext, String secret){
        try{
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE,secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(plaintext.getBytes("UTF-8")));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String ciphertext, String secret){
        try{
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE,secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(ciphertext)));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args){
        final String secretKey = "aaabbbkjhkjlh!234";
        String plaintext = "AES test test!";
        String encryptedText = AESUtil.encrypt(plaintext,secretKey);
        String decryptedString = AESUtil.decrypt(encryptedText,secretKey);

        System.out.println(plaintext);
        System.out.println(encryptedText);
        System.out.println(decryptedString);
    }
}
