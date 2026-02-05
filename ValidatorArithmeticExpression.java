package cis112_2025_1_e4project2;

import java.util.HashMap;
import java.util.Map;

/**
 * Validates arithmetic expressions with +, -, *, / and parentheses.
 * 
 * Grammar:
 * 
 * <pre>
 * expression = term , { ("+" | "-") , term } ; 
 * term = factor , { ("*" | "/") , factor } ; 
 * factor = digits | "(" , expression , ")" ; 
 * digits = digit , { digit } ;
 * </pre>
 */
public class ValidatorArithmeticExpression {

	private String input;
	private int index;

	public ValidatorArithmeticExpression() {
	}

	/**
	 * Validates the arithmetic expression.
	 * 
	 * @return true if valid, false otherwise
	 */
	public boolean isValid(String input) {
		input = input.replaceAll("\\s+", ""); // Remove whitespace
		this.input = input;
this.index = 0;
		try {
			return parseExpression() && index == input.length();
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	// expression = term , { ("+" | "-") , term } ;
	private boolean parseExpression() {
	
		

		if (!parseTerm()) return false;

		while (index < input.length()) {
			char d = input.charAt(index);
			if (d == '+' || d == '-') {
				index++; // consume + or -
				if (!parseTerm()) return false;
			} else {
				break;
			}
		}
		return true;

	}
// term = factor , { ("*" | "/") , factor } ;
	private boolean parseTerm() {
	

		if (!parseFactor()) return false;

		while (index < input.length()) {
			char d = input.charAt(index);
			if (d == '*' || d == '/') {
				index++; // consume * or /
				if (!parseFactor()) return false;
			} else {
				break;
			}
		}
		return true;

	}

	// factor = number | "(" , expression , ")" ;
	private boolean parseFactor() {
		
if (index >= input.length()) return false;

		char d = input.charAt(index);

		if (Character.isDigit(d)) {
			return parseDigits();
		} else if (d == '(') {
			index++; // consume '('
			if (!parseExpression()) return false;
			if (index >= input.length()) return false;
			if (input.charAt(index) == ')') {
				index++; // consume ')'
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}

	}

	// digits = digit , { digit } ;
	private boolean parseDigits() {
		if (!Character.isDigit(peek()))
			return false;
		index++;
		while (index < input.length() && Character.isDigit(peek())) {
			index++;
		}
		return true;
	}

	private char peek() {
		if (index >= input.length()) {
throw new IllegalArgumentException("Unexpected end of input");
		}
		return input.charAt(index);
	}

}
