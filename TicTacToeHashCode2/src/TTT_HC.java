import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeSet;

public class TTT_HC extends Board {

	/**
	 * the winners array holds either one winner board or a tree set of winners
	 */
	public TreeSet<String>[] winners;
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
	 * number of columns on each board
	 */
	public final int NUM_COLS = 3;
	/**
	 * number of rows on each board
	 */
	public final int NUM_ROWS = 3;
	/**
	 * total number of winners in the winners text file
	 */
	public final int NUM_WINNERS = 1400;
	/**
	 * number of collisions incremented in the constructor
	 */
	public static int numCollisions = 0;
	/**
	 * file name that holds the winner strings
	 */
	public static String winnersFile = "TicTacToeWinners.txt";
	/**
	 * string for recording the number of collisions in each tenth of the array
	 */
	public static String collisionDistribution = "";
	/**
	 * int for recording the number of collisions in each tenth of the array
	 */
	public static int numCollisionDistribution = 0;

	/**
	 * calls Board's constructor and instantiates and fills winners array from winners input file
	 * @param s board title
	 */
	@SuppressWarnings("unchecked")
	TTT_HC(String s) {
		super(s);
		// instantiate/fill winners array.
		winners = (TreeSet<String>[]) new TreeSet[NUM_WINNERS];

		// read in winners file and fill winners array
		File file = new File(winnersFile);
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException ex) {
			System.out.println("*** Cannot open winners input***"); // if input is invalid
			System.exit(0);
		}
		int tenth = NUM_WINNERS / 10;
		int count = 0;
		while (scanner.hasNextLine()) {
			String boardString = scanner.nextLine();
			setBoardString(toIntString(boardString));
			int index = tttHashCode();
			//System.out.println(boardString + ": " + index);
			if(winners[index] != null) {
				winners[index].add(boardString);
				numCollisions++;
				numCollisionDistribution++;
			}
			else if(winners[index] == null) {
				winners[index] = new TreeSet<String>();
				winners[index].add(boardString);
			}
			count++;
			if (count == tenth) {
				collisionDistribution += numCollisionDistribution + ", ";
				count = 0;
				numCollisionDistribution = 0;
			}				
		}
	}

	/**
	 * new hash function that allows collisions
	 * @return the hash code for the board
	 */
	public int tttHashCode() {
		int result = 0;
		for (int i = 0; i < NUM_ROWS; i++) {
			for (int j = 0; j < NUM_COLS; j++) {
				int index = i * NUM_COLS + j;
				if (charAt(i, j) == 'x')
					result += xInt * (int) (Math.pow(NUM_ROWS, index));
				else if (charAt(i, j) == 'o')
					result += oInt * (int) (Math.pow(NUM_ROWS, index));
				else
					result += 0;
			}
		}
		return result % NUM_WINNERS;
	}

	/**
	 * calculates the total number of chains (adds one for each element that has a tree set with more than one element)
	 * @return the number of chains/buckets
	 */
	public int numChains() {
		int result = 0;
		for (TreeSet<String> tree : winners) {
			if (tree != null && tree.size() > 1)
				result++;
		}
		return result;
	}

	/**
	 * calculates the longest chain in the array
	 * @return longest chain length
	 */
	public int maxChain() {
		int max = 0;
		for (TreeSet<String> tree : winners) {
			if (tree != null && tree.size() > max)
				max = tree.size();
		}
		return max;
	}

	/**
	 * calculates the average chain length for the buckets in the hash table
	 * @return the average chain length
	 */
	public int avgChain() {
		int total = 0;
		for (TreeSet<String> tree : winners) {
			if (tree != null)
				total += tree.size();
		}
		return total / winners.length;
	}
	
	/**
	 * calculates the load factor (number of elements/number of chains)
	 * @return the load factor for the winners array (hash table)
	 */
	public double loadFactor() {
		//number of elements/number of chains
		double numElements = 0;
		double numChains = numChains();
		for (TreeSet<String> tree: winners) {
			if (tree != null)
				numElements += tree.size();
		}
		return numElements/numChains;
	}
	
	//calculate the number of elements in each quarter of the array
	public String qtrDistribution() {
		String s = "";
		int index = 0;
		int qtr = NUM_WINNERS / 4;
		for(int j = 0; j < 4; j++) {
			int num = 0;
			for(int i = index; i < index + qtr; i++) {
				if (winners[i] != null)
					num += winners[i].size();
			}
			s += num + ", ";
			index += qtr;
		}
		return s.substring(0, s.length()-2);
	}
	
	//calculate the number of elements in each tenth of the array

	/**
	 * creates a unique hashcode for all possible values of the game board (3^9)
	 * 
	 * @return hashcode for index
	 */
	@Override
	public int myHashCode() {
		int result = 0;
		for (int i = 0; i < NUM_ROWS; i++) {
			for (int j = 0; j < NUM_COLS; j++) {
				int index = i * NUM_ROWS + j;
				if (charAt(i, j) == 'x')
					result += xInt * (int) (Math.pow(NUM_ROWS, index));
				else if (charAt(i, j) == 'o')
					result += oInt * (int) (Math.pow(NUM_ROWS, index));
				else
					result += 0;
			}
		}
		return result;
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
	 * creates a TTT_HC object; reports load factor, number of collisions, chains, chain length, and distribution
	 * @param args no used
	 */
	public static void main(String[] args) {
		TTT_HC board = new TTT_HC("Tic Tac Toe");
		System.out.println("Load factor: " + board.loadFactor());
		System.out.println("Number of collisions: " + numCollisions);
		System.out.println("Number of chains: " + board.numChains());
		System.out.println("Maximum chain length: " + board.maxChain());
		System.out.println("Average chain length: " + board.avgChain());
		System.out.println("Number of entries in each quarter of the array: " + board.qtrDistribution());
		System.out.println("Number of collisions in each tenth of the array: " + collisionDistribution.substring(0, collisionDistribution.length() - 2));
	}

}
