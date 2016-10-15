package net.msembodo.crypto;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.lexicalscope.jewel.cli.CliFactory;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class App {
	
    public static void main(String[] args ) throws IOException {
    	Switches switches = CliFactory.parseArguments(Switches.class, args);
    	
    	if (switches.isHelp() || args.length == 0) {
    		System.out.println("USAGE: crypto [-e account] [-d account] [--text plaintext] [--code secret]");
    		System.exit(0);
    	}
    		
    	// get account entry from file	
    	CSVReader reader = new CSVReader(new FileReader("accounts.csv"), ',', '"');
    	List<String[]> allRows = reader.readAll();
    	reader.close();
    	
    	if (switches.isList()) {
    		for (String[] row : allRows) 
    			System.out.println(row[0]);
    		System.exit(0);
    	}
    	
    	String key = "";
    	try {
			key = Crypto.hashString(switches.getCode(), "SHA-1").substring(0, 32);
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
    	
    	if (switches.isEncrypt() && switches.isDecrypt())
    		System.err.println("ERROR: mode should be either -e or -d");
    	else {
    		if (switches.isEncrypt()) {
    			// encryption
    			if (!switches.isCode()) {
    	    		System.err.println("Use --help for usage");
    	    		System.exit(1);
    	    	}
    			String plainText = switches.getText();
    			String encryptedText = "";
    			String account = switches.getEncrypt();
    	        try {
    	        	encryptedText = Crypto.encrypt(plainText, key);
    	        	// add account entry to file
    	        	String csv = "accounts.csv";
    	        	CSVWriter writer = new  CSVWriter(new FileWriter(csv, true));
    	        	String[] record = (account + "," + encryptedText).split(",");
    	        	writer.writeNext(record);
    	        	writer.close();
    	        	System.out.println(account + " has been successfully added.");
    	        	
    	        } catch (InvalidKeyException e) {
    	        	e.printStackTrace();
    	        	
    	        } catch (NoSuchAlgorithmException e) {
    				e.printStackTrace();
    				
    			} catch (NoSuchPaddingException e) {
    				e.printStackTrace();
    				
    			} catch (IllegalBlockSizeException e) {
    				e.printStackTrace();
    				
    			} catch (BadPaddingException e) {
    				e.printStackTrace();
    				
    			} catch (UnsupportedEncodingException e) {
    				e.printStackTrace();
    			}
    		}
    		
    		if (switches.isDecrypt()) {
    			// decryption
    			if (!switches.isCode()) {
    	    		System.err.println("Use --help for usage");
    	    		System.exit(1);
    	    	}
    	        try {
    	        	String account = switches.getDecrypt();
    	        	String decryptedText = "";
    	        	String encryptedTextFromRecord = "";
    	        	
    	        	boolean accountExists = false;
    	        	for (String[] row : allRows) {
    	        		if (row[0].equals(account)) {
    	        			accountExists = true;
    	        			encryptedTextFromRecord = row[1];
    	        			break;
    	        		}
    	        	}
    	        	if (accountExists) {
    	        		decryptedText = Crypto.decrypt(encryptedTextFromRecord, key);
    	        		System.out.println(account + "\t" + decryptedText);
    	        		
    	        	} else
    	        		System.out.println("Account does not exist.");
    	        	
    	        } catch (InvalidKeyException e) {
    	        	e.printStackTrace();
    	        	
    	        } catch (NoSuchAlgorithmException e) {
    				e.printStackTrace();
    				
    			} catch (NoSuchPaddingException e) {
    				e.printStackTrace();
    				
    			} catch (IllegalBlockSizeException e) {
    				e.printStackTrace();
    				
    			} catch (BadPaddingException e) {
    				e.printStackTrace();
    				
    			} catch (UnsupportedEncodingException e) {
    				e.printStackTrace();
    			}
    		}
    	}
    }
    
}
