package com.example.telecomm;

public class EncryptDecryptData {
    private static final int SECRET_KEY = 3;
    public String dataEncryption(String data){
        String cipherText = "";
        for(int i=0;i<data.length();i++){
            char c = (char) (data.charAt(i)+SECRET_KEY);
            cipherText += c;
        }
        return cipherText;
    }
    public String dataDecryption(String data){
        String plainText = "";
        for(int i=0;i<data.length();i++){
            char c = (char)(data.charAt(i)-SECRET_KEY);
            plainText += c;
        }
        return plainText;
    }
}
