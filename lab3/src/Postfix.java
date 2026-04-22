import java.io.*;

/**
 * A recursive-descent parser that converts infix expressions to postfix notation.
 *
 * <p>Supported operators (by precedence, low to high):</p>
 * <ul>
 *   <li>{@code + -} (addition, subtraction)</li>
 *   <li>{@code * /} (multiplication, division)</li>
 *   <li>{@code ( )} (parentheses for grouping)</li>
 * </ul>
 *
 * <p>Grammar (right-recursive):</p>
 * <pre>
 * expr     → term exprRest
 * exprRest → + term exprRest | - term exprRest | ε
 * term     → factor termRest
 * termRest → * factor termRest | / factor termRest | ε
 * factor   → ( expr ) | digit
 * </pre>
 */
class Parser {
	/** The current lookahead token read from standard input. */
	int lookahead;

	/** Accumulated postfix output. */
	StringBuilder output;

	/** Current position in the input (1-based), used for error reporting. */
	int pos;

	/** Whether an error has been detected. */
	boolean hasError;

	/** Error message describing the first error encountered. */
	String errorMsg;

	/**
	 * Constructs a new Parser and reads the first token from standard input.
	 *
	 * @throws IOException if an I/O error occurs while reading input
	 */
	public Parser() throws IOException {
		output = new StringBuilder();
		pos = 0;
		hasError = false;
		errorMsg = null;
		advance();
	}

	/**
	 * Reads the next character from standard input and increments the position counter.
	 *
	 * @throws IOException if an I/O error occurs while reading input
	 */
	void advance() throws IOException {
		lookahead = System.in.read();
		pos++;
	}

	/**
	 * Parses an expression (the entry point for the grammar).
	 *
	 * <p>expr → term exprRest</p>
	 *
	 * @throws IOException if an I/O error occurs while reading input
	 */
	void expr() throws IOException {
		if (hasError) return;
		term();
		exprRest();
	}

	/**
	 * Parses the rest of an expression (addition and subtraction),
	 * with tail recursion eliminated using a while loop.
	 *
	 * <p>exprRest → (+|-) term exprRest | ε</p>
	 *
	 * @throws IOException if an I/O error occurs while reading input
	 */
	void exprRest() throws IOException {
		while (!hasError && (lookahead == '+' || lookahead == '-')) {
			char op = (char) lookahead;
			match(lookahead);
			term();
			if (!hasError) output.append(op);
		}
	}

	/**
	 * Parses a term (multiplication and division).
	 *
	 * <p>term → factor termRest</p>
	 *
	 * @throws IOException if an I/O error occurs while reading input
	 */
	void term() throws IOException {
		if (hasError) return;
		factor();
		termRest();
	}

	/**
	 * Parses the rest of a term (multiplication and division),
	 * with tail recursion eliminated using a while loop.
	 *
	 * <p>termRest → (*|/) factor termRest | ε</p>
	 *
	 * @throws IOException if an I/O error occurs while reading input
	 */
	void termRest() throws IOException {
		while (!hasError && (lookahead == '*' || lookahead == '/')) {
			char op = (char) lookahead;
			match(lookahead);
			factor();
			if (!hasError) output.append(op);
		}
	}

	/**
	 * Parses a factor: either a parenthesized expression or a single digit.
	 *
	 * <p>factor → ( expr ) | digit</p>
	 *
	 * @throws IOException if an I/O error occurs while reading input
	 */
	void factor() throws IOException {
		if (lookahead == '(') {
			match('(');
			expr();
			if (lookahead == ')') {
				match(')');
			} else if (!hasError) {
				setError(pos, "Syntax error: missing closing ')' ");
			}
		} else if (Character.isDigit((char) lookahead)) {
			output.append((char) lookahead);
			match(lookahead);
		} else {
			if (!hasError) {
				if (lookahead == -1) {
					setError(pos, "Syntax error: unexpected end of input, expected operand");
				} else {
					setError(pos, "Syntax error: unexpected character '" + (char) lookahead + "', expected operand");
				}
			}
		}
	}

	/**
	 * Matches the current lookahead token against an expected token.
	 * If they match, advances to the next token; otherwise reports a syntax error.
	 *
	 * @param t the expected token character
	 * @throws IOException if an I/O error occurs while reading input
	 */
	void match(int t) throws IOException {
		if (lookahead == t) {
			advance();
		} else if (!hasError) {
			if (lookahead == -1) {
				setError(pos, "Syntax error: unexpected end of input, expected '" + (char) t + "'");
			} else {
				setError(pos, "Syntax error: expected '" + (char) t + "', got '" + (char) lookahead + "'");
			}
		}
	}

	/**
	 * Records a syntax error at the given position with the given message.
	 *
	 * @param position the 1-based character position where the error was detected
	 * @param message  a human-readable description of the error
	 */
	void setError(int position, String message) {
		hasError = true;
		errorMsg = "Position " + position + ": " + message;
	}

	/**
	 * Skips whitespace characters in the input.
	 *
	 * @throws IOException if an I/O error occurs while reading input
	 */
	void skipWhitespace() throws IOException {
		while (lookahead == ' ' || lookahead == '\t' || lookahead == '\r' || lookahead == '\n') {
			advance();
		}
	}
}

/**
 * Main program that reads an infix expression from standard input
 * and outputs its equivalent postfix (Reverse Polish) notation.
 *
 * <p>Usage: echo "9-5+2" | java Postfix</p>
 *
 * <p>Examples:</p>
 * <pre>
 * Input:  9-5+2
 * Output: 95-2+
 *
 * Input:  95+2       (missing operator between 9 and 5)
 * Output: 9 (error)
 * </pre>
 */
public class Postfix {
	/**
	 * Program entry point. Reads an infix expression, parses it, and prints
	 * the corresponding postfix expression or an error message.
	 *
	 * @param args command-line arguments (ignored)
	 * @throws IOException if an I/O error occurs while reading input
	 */
	public static void main(String[] args) throws IOException {
		System.out.println("Input an infix expression and output its postfix notation:");
		Parser parser = new Parser();
		parser.expr();

		// Check for unconsumed input (skip trailing whitespace)
		parser.skipWhitespace();
		if (parser.lookahead != -1 && !parser.hasError) {
			parser.setError(parser.pos,
				"Syntax error: extra input after valid expression starting at '" + (char) parser.lookahead + "'");
		}

		if (parser.hasError) {
			System.out.println(parser.output + " (error)");
			System.out.println(parser.errorMsg);
		} else {
			System.out.println(parser.output);
		}
		System.out.println("End of program.");
	}
}