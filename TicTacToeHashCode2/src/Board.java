import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BoxLayout;

/**
 * @author Mrs. Kelly
 * Sarah Fleming DE Assignment 6a
 * Last edited: 2/17/22
 */
/**
 * @author sarah
 *
 */
/**
 * @author sarah
 *
 */
abstract class Board extends JFrame implements ActionListener {

	/**
	 * matrix that holds the board characters (x's, o's, and spaces)
	 */
	private JButton buttons[][];
	/**
	 * label at the top of the display with the hashcode for the board
	 */
	private JLabel lblHashCode;
	/**
	 * label at the top of the display that says if the board is a winner or loser
	 */
	private JLabel lblWinTitle;
	/**
	 * string representation of the board with 0s, 1s, and 2s
	 */
	public String boardString = "";

	/**
	 * creates a JFrame with the title and calls setUpFrame
	 * @param title of the Board
	 */
	public Board(String title) {
		super(title);
		setupFrame();
	}

	/**
	 * changes the number on the display to the hash code
	 * @param hashcode the hash code for the board
	 */
	public void setHashCodeLabel(int hashcode) {
		lblHashCode.setText("" + hashcode);
	}

	/**
	 * changes the display to say winner or loser
	 * @param result winner or loser
	 */
	public void setWinner(String result) {
		lblWinTitle.setText(result);
	}

	/**
	 * changes the display to say winner if true, loser if false
	 * @param result true for winner, false for loser
	 */
	public void setWinner(boolean result) {
		if (result)
			setWinner("Winner");
		else
			setWinner("Loser");
	}

	/**
	 *  required because of abstract method, but not used
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
	}

	/**
	 * creates a JPanel and adds JLabels with the title, hash code, and winner title
	 * @return JPanel with the hash code and winner title
	 */
	JPanel setupPanelOne() {
		JPanel panel = new JPanel();
		JLabel lblHCTitle = new JLabel("Hash Code");
		;
		lblHashCode = new JLabel("" + myHashCode());
		lblWinTitle = new JLabel(""); // Will say either Winner or Loser
		setWinner(TicTacToe.isWin(boardString));
		panel.setLayout(new FlowLayout());
		panel.add(lblHCTitle);
		panel.add(lblHashCode);
		panel.add(lblWinTitle);
		return panel;
	}

	/**
	 * creates and returns a JPanel, adds a JButton for each space on the board grid
	 * @return JPanel with board display and buttons
	 */
	private JPanel setupPanelTwo() {
		JButton b;
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(TicTacToe.ROWS, TicTacToe.COLS));
		buttons = new JButton[TicTacToe.ROWS][TicTacToe.COLS];

		int count = 1;

		for (int r = 0; r < TicTacToe.ROWS; r++)
			for (int c = 0; c < TicTacToe.COLS; c++) {
				char ch = randomXO();
				b = new JButton("" + ch);
				boardString += ch;
				b.setActionCommand("" + r + ", " + c);
				b.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JButton btn = (JButton) e.getSource();
						btn.setText("" + cycleValue(btn.getText().charAt(0)));
						resetBoardString();
						lblHashCode.setText("" + myHashCode());
					}
				});
				panel.add(b);
				buttons[r][c] = b;
			}

		return panel;
	}

	/**
	 * changes the char: x to o, o to blank, blank to x
	 * @param ch current char value
	 * @return the next char value in the cycle
	 */
	private static char cycleValue(char ch) {
		switch (ch) {
		case 'x':
			return 'o';
		case 'o':
			return ' ';
		default:
			return 'x';
		}
	}

	/**
	 * creates the JFrame and adds the two JPanels (one with JLabels one with JButtons)
	 */
	private void setupFrame() {
		JPanel panel2 = new JPanel();

		// Setup Frame
		super.setSize(250, 200);
		super.setLocationRelativeTo(null);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

		// Setup Panels
		panel2 = setupPanelTwo(); // panelOne displays a value that requires panelTwo to be ready
		super.add(setupPanelOne());
		super.add(panel2);

		super.setVisible(true);
	}

	/**
	 * generates a random character from the 3 possiblities (x, o, or blank)
	 * @return random value: x, o, or space
	 */
	private char randomXO() {
		int rnd = (int) (Math.random() * TicTacToe.CHAR_POSSIBILITIES);
		switch (rnd) {
		case 1:
			return 'x';
		case 2:
			return 'o';
		default:
			return ' ';
		}
	}

	/**
	 * generates a unique hash code for each board possibility, created in TicTacToeHashCode
	 * @return hash code for the board string
	 */
	abstract int myHashCode();

	/**
	 * gets the character at a specific position on the board
	 * @param row the row position of the character on the board
	 * @param col the column position of the character on the board
	 * @return the character at the position
	 */
	public char charAt(int row, int col) {
		String value = buttons[row][col].getText();
		if (value.length() > 0)
			return value.charAt(0);
		else
			return '*';
	}

	/**
	 * sets the buttons on the board to display x's and o's in the right position
	 * @param s board string in 1s 2s and 0s
	 */
	public void show(String s) {
		int pos = 0;
		String letter;
		for (int r = 0; r < TicTacToe.ROWS; r++)
			for (int c = 0; c < TicTacToe.COLS; c++) {
				char ch = s.charAt(pos);
				switch (ch) {
				case '1':
					letter = "x";
					break;
				case '2':
					letter = "o";
					break;
				case '0':
					letter = " ";
					break;
				default:
					letter = "" + ch;
					xx: letter = " ";
				}

				buttons[r][c].setText(letter);
				pos++;
			}
	}

	/**
	 * sets the board string from the updated buttons on the board
	 */
	public void resetBoardString() {
		for (int r = 0; r < TicTacToe.ROWS; r++)
			for (int c = 0; c < TicTacToe.COLS; c++) {
				boardString += buttons[r][c].getText();
			}
	}

	/**
	 * sets the board string and displays it on the buttons
	 * @param s string of 1s, 2s, and 0s representing the board
	 */
	public void setBoardString(String s) {
		boardString = s;
		show(s);
	}

}