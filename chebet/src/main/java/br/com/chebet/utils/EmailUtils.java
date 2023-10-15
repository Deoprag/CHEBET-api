package br.com.chebet.utils;

import java.util.Random;
import java.util.regex.Pattern;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailUtils {

    private static final Pattern EMAIL = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean isEmail(String emailAddress) {
        return EMAIL.matcher(emailAddress).matches();
    }

    public static String generateCode() {
		String code = "";
		int i = 0;
		while (i < 6) {
			Random rand = new Random();
			code = code + rand.nextInt(9);
			i++;
		}
		return code;
	}

    public String forgotPassword(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        String code = generateCode();
        message.setFrom("mastercafe@deopraglabs.com");
        message.setTo(to);
        message.setSubject("Password Recovery Code");
        message.setText("Hi " + to + ", we received a request to recover your password." + 
                "\n\nYour verification code is: " + code + 
                "\n\n If you didn't request a password recover, don't worry, you can ignore this email." + 
                "\n\nThanks." +
                "\nMasterCafe Accounts Team.");
        return code;
    }
}
