package owl.humanresourcesmanagement.constant;

public class RegexConstants {
	private RegexConstants() { }
	
	// message = "Password must contain 1 uppercase/lowercase letter, 1 number and 1 special character!"
	public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*!.,?/])(?=\\S+$).{8,64}$";
	
	// Suitable to E.164 standard
	// May preceded by "+" sign (optional)
	// First digit must between 1 to 9 (cannot start with 0)
	// Phone number must max 15 digits
	public static final String PHONE_REGEX_E164 = "^\\+?[1-9]\\d{1,14}$";
}
