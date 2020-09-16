package com.github.yuitosaito.enigma;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Crypt {

    // アルゴリズム/ブロックモード/パディング方式
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    //Cipher
    private static final Cipher encDeCrypter;
    private static final Base64.Decoder decoder = Base64.getDecoder();
    private static final Base64.Encoder encoder = Base64.getEncoder();
    static {
        try {
            encDeCrypter = Cipher.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new AssertionError(e);
        }
    }


    public static IvParameterSpec getIv(String init_vector) {
        if (init_vector.length() == 16)
            return new IvParameterSpec(init_vector.getBytes(StandardCharsets.UTF_8));
        return null;
    }

    public static SecretKeySpec getKey(String encrypt_key) {
        if (encrypt_key.length() == 16)
            return new SecretKeySpec(encrypt_key.getBytes(StandardCharsets.UTF_8), "AES");
        return null;
    }

    public static String encryptToken(String token, IvParameterSpec iv, SecretKeySpec key) throws InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        encDeCrypter.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] byteToken = encDeCrypter.doFinal(token.getBytes(StandardCharsets.UTF_8));

        return new String(encoder.withoutPadding().encode(byteToken));
    }

    public static String decryptToken(String encryptedToken, IvParameterSpec iv, SecretKeySpec key) {
        try {
            encDeCrypter.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] byteToken = decoder.decode(encryptedToken);

            return new String(encDeCrypter.doFinal(byteToken), StandardCharsets.UTF_8);
        } catch (IllegalArgumentException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException ignored) {
        }
        return null;
    }
}