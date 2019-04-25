package com.gg.proj.util;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class CustomStringDecryptor {

    public static String decrypt(String toDecrypt) {

        // We use a symmetric encryption to provide a minimal security.
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("p)EkutxF,f;wJ7gxD)zB9h");
        encryptor.setAlgorithm("PBEWithMD5AndTripleDES");
        return encryptor.decrypt(toDecrypt);

    }
}
