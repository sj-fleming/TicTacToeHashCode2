/**
 * @author Mrs. Kelly Sarah Fleming DE Assignment 6a Last edited: 2/17/22
 */
public class TicTacToe {
	/**
	 * number of rows: 3
	 */
	public final static int ROWS = 3;
	/**
	 * number of columns: 3
	 */
	public final static int COLS = 3;
	/**
	 * number of possible board permutations
	 */
	public final static int POSSIBILITIES = (int) Math.pow(3, 9);
	/**
	 * number of possible characters on the board (x, o, or space)
	 */
	public final static int CHAR_POSSIBILITIES = 3; // x, o or space

	/**
	 * @param b matrix representing the board
	 * @param ch character being searched for on the board
	 * @return the number of times ch is found
	 * traverses the board to find how many occurances of ch there are
	 */
	private static int numChars(char[][] b, char ch) {
		int total = 0;
		for (int r = 0; r < ROWS; r++)
			for (int c = 0; c < COLS; c++)
				if (ch == b[r][c])
					total++;
		return total;
	}

	/**
	 * @param board matrix representing the board
	 * @return true if the board is valid, false otherwise
	 * ensures that there are 3 x's and 2 o's, or 3 o's and 2 x's, and that there is at least one more x or one more o
	 */
	public static boolean valid(char[][] board) {

		// Ensure there are at least 3 xs and 2 os, or 3 os and 2 xs
		// Make sure there are at least one more x or one more o
		int numX = numChars(board, 'x');
		int numO = numChars(board, 'o');
		if (!(numX > 2 || numO > 2))
			return false;
		if ((numX == numO + 1) || (numO == numX + 1))
			return true;
		return false;
	}

	/**
	 * @param b matrix representing the board
	 * @return the board as a string
	 * converts the board to a string of the characters in the matrix
	 */
	public static String boardToString(char[][] b) {
		String result = "";
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLS; c++)
				result += b[r][c];
			// result += "\n";
		}
		return result;
	}

	/**
	 * @param board the board string
	 * @return the board as a matrix
	 * creates a matrix with 3 rows and 3 columns and fills it according to the board string
	 */
	public static char[][] stringToBoard(String board) {
		char[][] b = new char[ROWS][COLS];
		int index = 0;
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLS; c++)
				b[r][c] = whichLetter(board.charAt(index++));
		}
		return b;
	}

	/**
	 * @param ch 1, 2, or 0
	 * @return ch as an x, o, or space
	 * converts a number 1, 2, or 3 to the character it represents (x, o, or blank)
	 */
	public static char whichLetter(char ch) {
		switch (ch) {
		case '1':
			return 'x';
		case '2':
			return 'o';
		case '0':
			return ' ';
		default:
			return ch;
		}
	}

	/**
	 * @param s string of 1s, 2s, and 0s representing the board
	 * @return a matrix representing the board
	 * converts a strings of 1s, 2s, and 0s to a matrix board
	 */
	public static char[][] makeBoard(String s) {
		char[][] b = new char[ROWS][COLS];
		int ch = 0;
		for (int r = 0; r < ROWS; r++)
			for (int c = 0; c < COLS; c++) {
				b[r][c] = whichLetter(s.charAt(ch));
				ch++;
			}
		return b;
	}

	/**
	 * @param s original board string (9 characters; 0s, 1s, and 2s)
	 * @return the new string
	 * adds 1 to the last char, adjusting the rest of the characters to make sure the board is still valid
	 */
	private static String addOne(String s) {
		// s is a 9 character string, composed of 0s, 1s, and 2s. Add 1 to the last
		// char, adjusting
		// all the rest of the characters as necessary.
		boolean carry = false;
		char ch[] = s.toCharArray();
		ch[ch.length - 1] = (char) ((int) (ch[ch.length - 1]) + 1);
		for (int n = ch.length - 1; n >= 0; n--) {
			if (carry)
				ch[n] = (char) ((int) (ch[n]) + 1);
			if (ch[n] == '3') {
				carry = true;
				ch[n] = '0';
			} else
				carry = false;
		}
		return new String(ch);
	}

	/**
	 * @return string array of all possible board strings
	 * fills an array with all the possible board strings from 000000000 to 222222222
	 */
	public static String[] fillValues() {
		String strBoard = "000000000";
		String[] values = new String[POSSIBILITIES];
		int index = 0;
		values[index++] = strBoard;
		while (!strBoard.equals("222222222")) {
			strBoard = addOne(strBoard);
			values[index++] = strBoard;
		}
		return values;
	}

	/**
	 * @param board matrix representation of the board
	 * @return true if x or o won on a diagonal
	 * checks both diagonals to see if all 3 are x's or all 3 are o's
	 */
	public static boolean diagonalWin(char[][] board) {
		int wins = 0;
		if ((board[0][0] == 'x' && board[1][1] == 'x' && board[2][2] == 'x')
				|| (board[0][0] == 'o' && board[1][1] == 'o' && board[2][2] == 'o'))
			wins++;

		if ((board[0][2] == 'x' && board[1][1] == 'x' && board[2][0] == 'x')
				|| (board[0][2] == 'o' && board[1][1] == 'o' && board[2][0] == 'o'))
			wins++;

		return wins == 1;
	}

	/**
	 * @param board matrix representation of board
	 * @return true if x or o won on a row
	 * checks each row if all characters in the row are x's and o's
	 */
	public static boolean rowWin(char[][] board) {
		char ch;
		int wins = 0;
		boolean win = true;
		for (int r = 0; r < ROWS; r++) {
			win = true;
			ch = board[r][0];
			for (int c = 0; c < COLS; c++)
				if (ch != board[r][c]) {
					win = false;
					break;
				}
			if (win && ch != ' ')
				wins++;
		}
		return wins == 1;
	}

	/**
	 * @param board matrix representation of board
	 * @return true if x or o won on a column
	 * checks each row if all characters in the column are x's and o's
	 */
	public static boolean colWin(char[][] board) {
		char ch;
		int wins = 0;
		boolean win = true;
		for (int c = 0; c < COLS; c++) {
			win = true;
			ch = board[0][c];
			for (int r = 0; r < ROWS; r++)
				if (ch != board[r][c]) {
					win = false;
					break;
				}
			if (win && ch != ' ')
				wins++;
		}
		return wins == 1;
	}

	/**
	 * @param b matrix representation of board
	 * @return true if the board is a winner, false otherwise
	 * checks if the board is a win (by row, column, or diagonal)
	 */
	public static boolean isWin(char[][] b) {
		int wins = 0;
		if (valid(b)) {
			if (rowWin(b))
				wins++;
			if (colWin(b))
				wins++;
			if (diagonalWin(b))
				wins++;
		}
		return wins == 1;
		// return valid(b) && (rowWin(b) ^ colWin(b) ^ diagonalWin(b));
	}

	/**
	 * @param b matrix representation of the board
	 * @return row, col, dia depending on how the game was won (or loser if lost)
	 * returns a string if the game was won on a row, column, or diagonal (or loser if lost)
	 */
	public static String isWinString(char[][] b) {
		boolean v = valid(b);
		if (v) {
			boolean r = rowWin(b);
			boolean c = colWin(b);
			boolean d = diagonalWin(b);
			int wins = 0;
			if (r)
				wins++;
			if (c)
				wins++;
			if (d)
				wins++;
			if (wins == 1) {
				if (r)
					return "Row";
				if (c)
					return "Col";
				if (d)
					return "Dia";
				return "winner";

			}
		}
		return "loser";
	}

	/**
	 * @param s string representation of board
	 * @return true if winner, false otherwise
	 * converts the board to a matrix and checks if it is a winner
	 */
	public static boolean isWin(String s) {
		char[][] b = stringToBoard(s);
		return isWin(b);
	}

	/**
	 * @param s string representation of board
	 * @return row, col, dia depending on how the game was won (or loser if lost)
	 * converts the string to a matrix and returns a string if the game was won on a row, column, or diagonal (or loser if lost)
	 */
	public static String isWinString(String s) {
		char[][] b = stringToBoard(s);
		return isWinString(b);
	}

	/**
	 * @param args not used
	 * converts a board string to a matrix and tests if it is valid, a row winner, a column winner, a diagonal winner, and a winner
	 */
	public static void main(String[] args) {
		String s = "ooooxxxox";
		char[][] b = stringToBoard(s);
		System.out.println("Valid:   " + valid(b));
		System.out.println("Row Win: " + rowWin(b));
		System.out.println("Col Win: " + colWin(b));
		System.out.println("Dia Win: " + diagonalWin(b));
		System.out.println(isWin(b));

	}
}
