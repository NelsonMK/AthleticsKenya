package com.example.athleticskenya.Utils;

import android.util.Base64;

import java.nio.charset.StandardCharsets;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Cipher {

    private static String CIPHER_NAME = "AES/CBC/PKCS5PADDING";
    private static int CIPHER_KEY_LENGTH = 16;
    private static String key = "0123456789abcdef";

    public static String encrypt(String data){
        try {
            if (key.length() < CIPHER_KEY_LENGTH) {
                int numPad = CIPHER_KEY_LENGTH - key.length();

                for (int i = 0; i < numPad; i++){
                    key += "0";//0 pad to length 16 bytes
                }
            } else if (key.length() > CIPHER_KEY_LENGTH) {
                key = key.substring(0, CIPHER_KEY_LENGTH);//truncate to 16 bytes
            }

            String iv = "Mbb|{4ij2H2{s/%E";
            IvParameterSpec initVector = new IvParameterSpec(iv.getBytes(StandardCharsets.ISO_8859_1));
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.ISO_8859_1), "AES");

            javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(CIPHER_NAME);
            cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, secretKeySpec, initVector);

            byte[] encryptedData = cipher.doFinal((data.getBytes()));

            String base64_EncryptedData = Base64.encodeToString(encryptedData, Base64.DEFAULT);
            String base64_IV = Base64.encodeToString(iv.getBytes(StandardCharsets.ISO_8859_1), Base64.DEFAULT);

            return base64_EncryptedData + ":" + base64_IV;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String decrypt(String data){
        try {
            if (key.length() < CIPHER_KEY_LENGTH) {
                int numPad = CIPHER_KEY_LENGTH - key.length();

                for (int i = 0; i < numPad; i++){
                    key += "0";//0 pad to length 16 bytes
                }
            } else if (key.length() > CIPHER_KEY_LENGTH) {
                key = key.substring(0, CIPHER_KEY_LENGTH);//truncate to 16 bytes
            }

            String[] parts = data.split(":");

            IvParameterSpec initVector = new IvParameterSpec(Base64.decode(parts[1], Base64.DEFAULT));
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.ISO_8859_1), "AES");

            javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(CIPHER_NAME);
            cipher.init(javax.crypto.Cipher.DECRYPT_MODE, secretKeySpec, initVector);

            byte[] decodeEncryptedData = Base64.decode(parts[0], Base64.DEFAULT);

            byte[] original = cipher.doFinal(decodeEncryptedData);

            return new String(original);

        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
