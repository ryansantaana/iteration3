import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class Authenticator {

	public static boolean isCorrectPassword(String pwd, String hash, String salt) {
		MessageDigest digest;
		pwd = salt + pwd;

		try {
			digest = MessageDigest.getInstance("SHA-256");
			byte[] compareBytes = digest.digest(pwd.getBytes(StandardCharsets.UTF_8));
			return Base64.getEncoder().encodeToString(compareBytes).equals(hash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String generateSalt() {
		SecureRandom sr = new SecureRandom();

		byte[] bytes = sr.generateSeed(10);

		return Base64.getEncoder().encodeToString(bytes);
	}

	public static String hashString(String stringIn, String salt) {
		stringIn = salt + stringIn;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hashBytes = digest.digest(stringIn.getBytes(StandardCharsets.UTF_8));
			return Base64.getEncoder().encodeToString(hashBytes);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

}
