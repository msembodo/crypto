package net.msembodo.crypto;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class Crypto {
	
	public static String encrypt(String plainText, String key) throws 
		NoSuchAlgorithmException,
		NoSuchPaddingException,
		InvalidKeyException,
		IllegalBlockSizeException,
		BadPaddingException,
		UnsupportedEncodingException {
		
		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
		
		// instantiate cipher
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, keySpec);
		
		byte[] encryptedTextBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
		
		return new Base64().encodeAsString(encryptedTextBytes);
	}
	
	public static String decrypt(String encryptedText, String key) throws
		NoSuchAlgorithmException, 
	    NoSuchPaddingException, 
	    InvalidKeyException, 
	    IllegalBlockSizeException, 
	    BadPaddingException, 
	    UnsupportedEncodingException {
		
		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
		
		// instantiate cipher
	    Cipher cipher = Cipher.getInstance("AES");
	    cipher.init(Cipher.DECRYPT_MODE, keySpec);
	    
	    new Base64();
		byte[] encryptedTextBytes = Base64.decodeBase64(encryptedText);
	    byte[] decryptedTextBytes = cipher.doFinal(encryptedTextBytes);
	    
	    return new String(decryptedTextBytes);
	}
	
	public static String hashString(String plainText, String algorithm) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(algorithm);
		md.update(plainText.getBytes());
		
		byte[] byteData = md.digest();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++)
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		
		return sb.toString();
	}

}
