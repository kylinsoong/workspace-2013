import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.io.IOUtils;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;


public class PasswordEncryptor {

	private static final String externalPwdKey = "externalpwdkey";

	public static void main(String[] args) {
		String pwdKey = System.getProperty("externalpwdkey");
	    if (pwdKey == null) {
	      System.out.println("External pwd key not specified via system property.");
	      System.exit(1);
	    }
	    
	    System.out.print("Enter clear text secret: ");

	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    String cpwd = null;
	    try {
	      cpwd = br.readLine();
	    } catch (IOException ioe) {
	      System.out.println("IO error trying to read your input!");
	      System.exit(1);
	    }
	    
		try {
			FileInputStream inputStream = new FileInputStream(pwdKey);
			String encKey = IOUtils.toString(inputStream, "UTF-8");
			encKey = encKey.replace("\n", "").replace("\r", "");
			System.out.println(encKey);

			StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
			encryptor.setPassword(encKey);
			encryptor.setAlgorithm("PBEWithMD5AndTripleDES");

			String encryptedPassword = encryptor.encrypt(cpwd);

			System.out.print("Encrypted secret:" + encryptedPassword);
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	    
	    System.out.println(pwdKey);
	    System.out.println(cpwd);
	}

}
