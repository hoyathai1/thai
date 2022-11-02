package com.travel.thai.common.util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAUtil {
    private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCLlSOzkpX7oWVtm_qhpJ2hGZbqMw56vSL8rT1xEGDHb07kIXi-1cmScsonb-Oap_DFIbgpk8eAg_c4djuiFLHlv7pQkNUoydfC6AcZCt6ciDxLBE-9NSckX1cdQYoo5rurkTYQPFTrSM7rlfFC6lv-tWvbs34MO0IHnZSrUOvZ9QIDAQAB";
    private static final String PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIuVI7OSlfuhZW2b-qGknaEZluozDnq9IvytPXEQYMdvTuQheL7VyZJyyidv45qn8MUhuCmTx4CD9zh2O6IUseW_ulCQ1SjJ18LoBxkK3pyIPEsET701JyRfVx1Biijmu6uRNhA8VOtIzuuV8ULqW_61a9uzfgw7QgedlKtQ69n1AgMBAAECgYAcLGIkYLX9MDp0wUL5VWyrk6quYtfNVle1gJIiPBgMqR7XVRo2CpjBAoJjK2ZZVQR4BbJSUg22luxTbSnkMSEm3FlB3OzglrrpUoExfAs0bONVuTr3NdAO-DZgMFXhemt_67Ksm6Y5u4XB4JYrg7EyU_qziUBppOkcQnF9OgnQAQJBAOoOWVN_De61CPX7GDAOMXRj2lYqLY6NxbG_tfwS7VB4RQd31hfyngQVkUkmVU5gIvrjuSG8DQu1U_OLJSQS6vUCQQCYq02-VBAad8z2Gdw3E0rzLrQYQ748yqp1cSeCWT1IwWGZaEtcJJP9INMr5FEzslsMQioURlCFCTv-nTtTzdMBAkBOksyYBDI4LZw5f7bqAwnFKfRVMsmibqOsfVBptCzo88cwr6E23uL_OgJRDUZcDlgP47z1vzyd7pylSNQKHTO1AkBTq98J5uyEdRAh-P5WOP6k1P3jJo2Gp9RQVVm_-MS7sTCacSyMLYfl_S1_KrzORnvb9g812Wub3fnBi87OGUoBAkBsQbi3JfgkigIJiOOSn3U3haJrNYPlTqjGqgoF7xMM0_TfFUa4tZFUrcpxSMYvB_iRrDzWjaHddGzS8ah2_N42";

    /**
     * 1024비트 RSA 키쌍을 생성
     */
    public static KeyPair genRSAKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
        gen.initialize(1024, new SecureRandom());
        return gen.genKeyPair();
    }

    /**
     * Public Key로 RSA 암호화를 수행
     */
    public static String encryptRSA(String plainText)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
        PublicKey publicKey = RSAUtil.getPublicKeyFromBase64Encrypted(PUBLIC_KEY);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] bytePlain = cipher.doFinal(plainText.getBytes());
        return Base64.getUrlEncoder().encodeToString(bytePlain);
    }

    /**
     * Private Key로 RSA 복호화를 수행
     */
    public static String decryptRSA(String encrypted)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidKeySpecException {
        PrivateKey privateKey = RSAUtil.getPrivateKeyFromBase64Encrypted(PRIVATE_KEY);

        Cipher cipher = Cipher.getInstance("RSA");
        byte[] byteEncrypted = Base64.getUrlDecoder().decode(encrypted.getBytes());

        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] bytePlain = cipher.doFinal(byteEncrypted);
        return new String(bytePlain, "utf-8");
    }

    public static PublicKey getPublicKeyFromBase64Encrypted(String base64PublicKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] decodedBase64PubKey = Base64.getUrlDecoder().decode(base64PublicKey);

        return KeyFactory.getInstance("RSA")
                .generatePublic(new X509EncodedKeySpec(decodedBase64PubKey));
    }

    public static PrivateKey getPrivateKeyFromBase64Encrypted(String base64PrivateKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] decodedBase64PrivateKey = Base64.getUrlDecoder().decode(base64PrivateKey);

        return KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(decodedBase64PrivateKey));
    }


}
