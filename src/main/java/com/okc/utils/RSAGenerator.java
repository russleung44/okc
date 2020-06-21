package com.okc.utils;

import lombok.Getter;

import java.io.FileWriter;
import java.io.IOException;
import java.security.*;
import java.util.Base64;

/**
 * @author tony
 * @date 2019/7/15 16:57
 */
@Getter
public class RSAGenerator {

    private PrivateKey privateKey;
    private PublicKey publicKey;


    public static void main(String[] args) throws Exception {
        RSAGenerator generator = new RSAGenerator();
        generator.createKeyPair();
        String privateKeyString = getKeyString(generator.getPrivateKey());
        String publicKeyString = getKeyString(generator.getPublicKey());
        String basePath = "src/main/resources/rsa/";
        write2File(basePath + "rsa_1024_private", privateKeyString);
        write2File(basePath + "rsa_1024_public", publicKeyString);
    }

    /**
     * 生成密钥对
     * @throws NoSuchAlgorithmException
     */
    private void createKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("rsa");
        generator.initialize(1024);
        KeyPair keyPair = generator.genKeyPair();
        this.privateKey = keyPair.getPrivate();
        this.publicKey = keyPair.getPublic();
    }

    /**
     * 写入文件
     * @param path
     * @param key
     * @throws IOException
     */
    private static void write2File(String path, String key) throws IOException {
        FileWriter writer = new FileWriter(path);
        writer.write(key);
        writer.flush();
        writer.close();
    }


    /**
     * 得到密钥字符串（经过base64编码）
     * @param key
     * @return
     * @throws Exception
     */
    private static String getKeyString(Key key) throws Exception {
        byte[] keyBytes = key.getEncoded();
        return Base64.getEncoder().encodeToString(keyBytes);
    }
}
