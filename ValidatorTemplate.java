

/**
 * Validator for the EBNF grammar:
 * 
 * a = b , c ; b = d , [ e ] ; c = d , { e } ; d = e | f ; e = "E" ; f = "F" ;
 */
public class ValidatorTemplate {
	private String input;
	private int index;

	/**
	 * Validates if the input string matches grammar rule 'a'.
	 * 
	 * @param input The string to validate
	 * @return true if valid, false otherwise
	 */
	public boolean isValid(String input) {
		if (input == null || input.isEmpty()) {
			return false;
		}
		this.input = input;
		this.index = 0;

		try {
			return parseA() && index == input.length();
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
	}

	/**
	 * Parses rule: a = b , c ;
	 */
	private boolean parseA() {
		return parseB() && parseC();
	}

	/**
	 * Parses rule: b = d , [ e ] ;
	 */
	private boolean parseB() {
		return parseD() && parseOptionalE();
	}

	/**
	 * Parses rule: c = d , { e } ;
	 */
	private boolean parseC() {
		return parseD() && parseZeroOrMoreE();
	}

	/**
	 * Parses rule: d = e | f ;
	 */
	private boolean parseD() {
		return parseE() || parseF();
	}

	/**
	 * Parses rule: e = "E" ;
	 */
	private boolean parseE() {
		return tryConsume('E');
	}

	/**
	 * Parses rule: f = "F" ;
	 */
	private boolean parseF() {
		return tryConsume('F');
	}

	/**
	 * Parses optional E: [ e ]
	 */
	private boolean parseOptionalE() {
		parseE(); // Optional - always returns true
		return true;
	}

	/**
	 * Parses zero or more E: { e }
	 */
	private boolean parseZeroOrMoreE() {
		while (parseE()) {
			// Continue consuming E's
		}
		return true;
	}

	/**
	 * Attempts to consume the specified character.
	 * 
	 * @param expected The character to consume
	 * @return true if consumed, false otherwise
	 */
	private boolean tryConsume(char expected) {
		if (index < input.length() && input.charAt(index) == expected) {
			index++;
			return true;
		}
		return false;
	}

	// Test cases
	public static void main(String[] args) {
		System.out.println("\n->" + StackWalker.getInstance().walk(s -> s.skip(0).findFirst()).get().getMethodName());
		ValidatorTemplate validator = new ValidatorTemplate();

		String[] valid = { //
				"EFEE" // b="EF", c="EE"
				, "EEE" // b="E", c="EE"
				, "FEE" // b="FE", c="EE"
				, "FEEE" // b="FE", c="EEE"
		};
		System.out.println("\nvalid");
		for (int i = 0; i < valid.length; i++) {
			System.out.println(validator.isValid(valid[i]) + ":" + valid[i]);
		}

		String[] invalid = { //
				"", "E", "EE", "X" };
		System.out.println("\ninvalid");
		for (int i = 0; i < invalid.length; i++) {
			System.out.println(validator.isValid(invalid[i]) + ":" + invalid[i]);
		}
	}

}
