package com.okc.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


/**
 * RSA算法，实现数据的加密解密。
 */
@Slf4j
public class RSAUtil {

    private static Cipher cipher;

    static {
        try {
            cipher = Cipher.getInstance("RSA");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }


    /**
     * 使用公钥对明文进行加密，返回BASE64编码的字符串
     *
     * @param publicKey
     * @param plainText
     * @return
     */
    public static String encrypt(PublicKey publicKey, String plainText) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] enBytes = cipher.doFinal(plainText.getBytes());
            return Base64.getEncoder().encodeToString(enBytes);
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用keystore对明文进行加密
     *
     * @param plainText 明文
     * @return
     */
    public static String encrypt(String plainText) {
        try {
            String PUBLIC_KEY_FILE = "rsa/rsa_1024_public";
            Resource resource = new ClassPathResource(PUBLIC_KEY_FILE);
            String publicKeyString = loadKey(resource.getInputStream());
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKeyString));
            byte[] enBytes = cipher.doFinal(plainText.getBytes());
            return Base64.getEncoder().encodeToString(enBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 使用私钥对明文密文进行解密
     *
     * @param privateKey
     * @param enStr
     * @return
     */
    public static String decrypt(PrivateKey privateKey, String enStr) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] deBytes = cipher.doFinal(Base64.getDecoder().decode(enStr));
            return new String(deBytes);
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 使用keystore对密文进行解密
     *
     * @param enStr 密文
     * @return
     */
    public static String decrypt(String enStr) {

        String PRIVATE_KEY_FILE = "rsa/rsa_1024_private";
        Resource resource = new ClassPathResource(PRIVATE_KEY_FILE);
        byte[] deBytes = new byte[0];
        try {
            String privateKeyString = loadKey(resource.getInputStream());
            cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(privateKeyString));
            deBytes = cipher.doFinal((Base64.getDecoder().decode(enStr)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(deBytes);

    }

    private static String loadKey(InputStream in) throws Exception {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String readLine;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                sb.append(readLine);
                sb.append('\r');
            }
            return sb.toString();
        } catch (IOException e) {
            log.error("[error]", e);
            throw new Exception("公钥数据流读取错误");
        } catch (NullPointerException e) {
            log.error("[error]", e);
            throw new Exception("公钥输入流为空");
        }
    }


    /**
     * 得到公钥
     *
     * @param keyString 密钥字符串（经过base64编码）
     * @throws Exception
     */
    private static PublicKey getPublicKey(String keyString) throws Exception {
        byte[] keyBytes;
        keyBytes = Base64.getDecoder().decode(keyString);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 得到私钥
     *
     * @param keyString 密钥字符串（经过base64编码）
     * @throws Exception
     */
    private static PrivateKey getPrivateKey(String keyString) throws Exception {
        byte[] keyBytes;
        keyBytes = Base64.getDecoder().decode(keyString);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }
}