import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Sarah Fleming
 * DE Assignment 6a
 * Last edited: 2/17/22
 */
public class TicTacToeHashCode extends Board {

	/**
	 * the winners array has a boolean for each possible outcome (true if it is a winner, false otherwise)
	 */
	public static boolean[] winners; 
	/**
	 * number of rows on each board
	 */
	public static final int NUMROWS = 3;
	/**
	 * number of columns on each board
	 */
	public static final int NUMCOLS = 3;
	/**
	 * integer representation of an x
	 */
	public int xInt = 1;
	/**
	 * integer representation of an o
	 */
	public int oInt = 2;
	/**
	 * integer representation of an x (static)
	 */
	public static int xValue = 1;
	/**
	 * integer representation of an o (static)
	 */
	public static int oValue = 2;
	/**
	 * total number of possible outcomes, size of winners array (3^9)
	 */
	public int numOutcomes = (int) Math.pow(3, 9);
	/**
	 * input file used to make the winners array
	 */
	public static String winnersFile = "TicTacToeWinners.txt";
	/**
	 * test file name
	 */
	public static String testFile = "TTT_Tests.txt";

	/**
	 * calls Board's constructor and instantiates and fills winners array from winners input file
	 * @param s TicTacToe board title
	 */
	TicTacToeHashCode(String s) {
		super(s);
		// instantiate/fill winners array.
		winners = new boolean[numOutcomes];

		// read in winners file and fill winners array
		File file = new File(winnersFile);
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException ex) {
			System.out.println("*** Cannot open winners input***"); // if input is invalid
			System.exit(0);
		}

		while (scanner.hasNextLine()) {
			String boardString = scanner.nextLine();
			setBoardString(toIntString(boardString));
			int index = myHashCode();
			//System.out.println(boardString + ": " + index);
			winners[index] = true;
		}
	}


	/**
	 * creates a unique hashcode for all possible values of the game board (3^9)
	 * @return hashcode for index
	 */
	@Override
	public int myHashCode() {
		int result = 0;
		for (int i = 0; i < NUMROWS; i++) {
			for (int j = 0; j < NUMCOLS; j++) {
				int index = i * NUMROWS + j;
				if (charAt(i, j) == 'x')
					result += xInt * (int) (Math.pow(NUMROWS, index));
				else if (charAt(i, j) == 'o')
					result += oInt * (int) (Math.pow(NUMROWS, index));
				else
					result += 0;
			}
		}
		return result;
	}

	/**
	 * checks the winners array for if the board is a winner
	 * @return true if the hash code index in the winners array holds true (the board has a winner)
	 */
	public boolean isWin() {
		return winners[this.myHashCode()];
	}

	/**
	 * returns true if the board is a winner
	 * @param s board string
	 * @return the value in the winner array for the hash code of the board string sent in
	 */
	public boolean isWin(String s) {
		return true;
	}

	/**
	 * converts a string of x's and o's to a string of numbers
	 * @param s the board as a string of x's and o's
	 * @return the board as a string of 0s 1s and 2s
	 */
	public static String toIntString(String s) {
		String nums = "";
		char ch;
		for (int i = 0; i < s.length(); i++) {
			ch = s.charAt(i);
			switch (ch) {
			case 'x':
				nums += xValue;
				break;
			case 'o':
				nums += oValue;
				break;
			case ' ':
				nums += 0;
				break;
			default:
				nums += 0;
				break;
			}

		}
		return nums;
	}

	/**
	 * reads the test file and displays the board of the winners with a 4 second delay
	 * @param args not used
	 * @throws InterruptedException thrown if the thread is interrupted (thread.sleep() called)
	 */
	public static void main(String[] args) throws InterruptedException {
		TicTacToeHashCode board = new TicTacToeHashCode("Tic Tac Toe");

		// reading test file and returning winners
		File file = new File(testFile);
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException ex) {
			System.out.println("*** Cannot open test input***"); // if input is invalid
			System.exit(0);
		}

		while (scanner.hasNextLine()) {
			String s = scanner.nextLine();
			
			if (s.length() == NUMROWS * NUMCOLS && s.contains("x") && s.contains("o")) {
				String numBoard = toIntString(s);
				board.setBoardString(numBoard);
				if (board.isWin()) {
					System.out.println(s + ": winner");
					board.show(numBoard);
					board.setHashCodeLabel(board.myHashCode());
					board.setWinner(true);
					TicTacToe ttt = new TicTacToe();
					// System.out.println(ttt.isWin(s));
					// board.resetBoardString();
					Thread.sleep(4000);
				}
				else
					System.out.println(s);
			}
			else
				System.out.println(s + ": invalid string");
		}
	}
}