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
    private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCQ+jaLiOaShPhRAvjXyU/6mbB4qxFMDS0MIW7cCUfyxAcoN+HdHvPZKfwJ2wpIS8KoZTJb5EF+8yfq+e7xk+PzyemSPIww2ZQ5Qlt46EdPb1ki3MuCIb1UJNzjl5o0cHy9fPc0id1ci0jCPDhzPMhuO7Xiipt3bRRG07K1HsLGOwIDAQAB";
    private static final String PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJD6NouI5pKE+FEC+NfJT/qZsHirEUwNLQwhbtwJR/LEByg34d0e89kp/AnbCkhLwqhlMlvkQX7zJ+r57vGT4/PJ6ZI8jDDZlDlCW3joR09vWSLcy4IhvVQk3OOXmjRwfL189zSJ3VyLSMI8OHM8yG47teKKm3dtFEbTsrUewsY7AgMBAAECgYA1Fbl1PEqZKeE24Ky3bro/W5k3hgQOYeC1yWQrY6aZqNeqDVRBwDyvruTfwvmv4TXErVFUPm8RCf3610t735gethRnePmLn00SLIG/tl9t6X887JqXb/k4u285as8tn2jP+zVYExRPZsOdp/kc3v2cityaFrhRcuDwbqf/ciysGQJBAMX17fKwB9GycTe7CFwO0mcBwYT4+LJJdZgUcoDGyv+x9tgDMS/axVxXyi1CMtoqxsCfjyOBlYiSN6gKutvuQb8CQQC7e5a/LQG1zj5Ez5GK5XTRqo3cwDgujVkAO4eb5GFLbZjz/SnIrQR0dCuUH1MxGbphJB67PDtHFj7BDpIWEOKFAkALWGjmu8f28K8kboewHvlYBfW9VTeLoEIpESLu1nXT6bMn9ibfgz5EaJOhvGWFs2XorDxb4JVdWmPuMY88A4+pAkAQmoVjMO0x2K4TEf7luiR6XQxrPGq9VK9JIfyEhdlIANDG6ujEaO44EgG6AHwxoQGskLJYCqGFTicIme2+HoixAkA7HslED/qS4BJLtI38vvfF+KxAVN7aWmHYEOJVFeO9LOrOcXjB/t3Yjwx1GHkBkIW0dRR5MKoBvQztfbh5Y0WK";

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
        byte[] byteEncrypted = Base64.getDecoder().decode(encrypted.getBytes());

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
        byte[] decodedBase64PrivateKey = Base64.getDecoder().decode(base64PrivateKey);

        return KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(decodedBase64PrivateKey));
    }


}
