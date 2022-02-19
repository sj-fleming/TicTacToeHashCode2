import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Scanner;

/*
 * @author Sarah Fleming
 * TicTacToeHashMap part C version 2
 */

public class TicTacToeMyHashMap  {

	//Define a hash map to store the winning strings as Key and true as Value
	public static HashMap<String, Boolean> winnersMap;
	/**
	 * file name that holds the winner strings
	 */
	public static String winnersFile = "TicTacToeWinners.txt";
	/**
	 * total number of winners in the winners text file
	 */
	public final int NUM_WINNERS = 1400;
	public final static int CAPACITY = 1600;

   TicTacToeMyHashMap() {
	   //Instantiate/fill HashMap ... pay attention to initial capacity and load values
	   //initial capacity set to twice the number of expected values
	   winnersMap = new HashMap<String, Boolean>(CAPACITY); 
   
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
			winnersMap.put(scanner.nextLine(), true);			
		}
   
   }

// This method uses reflect to investigate the objects inside the HashMap
// You should be able to update this with your information and determine 
// Information about capacity (different than size()) and what is stored in the cells

   private int capacity() throws NoSuchFieldException, IllegalAccessException {
      Field tableField = HashMap.class.getDeclaredField("table");
      tableField.setAccessible(true);
      Object[] table = (Object[]) tableField.get(winnersMap);
      return table == null ? 0 : table.length;   
   }
   
   // using the same code to get the table of entries as in the capacity method,
   // create a method that will evaluate the table as directed in the assignment.
   // note - if an entry is not null, then it has a value, it may have more than one value
   // see if you can determine how many values it has.  Using the debugger will assist.
   
   public static void report() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
	   Field tableField = HashMap.class.getDeclaredField("table");
	   tableField.setAccessible(true);
	   Object[] table = (Object[]) tableField.get(winnersMap);
	   
//	   for(Object obj: table) {
//		   if(obj != null)
//			   System.out.println(obj.getClass()); //class java.util.HashMap$Node
//	   }
	   
	   //number of entries stored in the table
	   //load factor  
	   //number of entries in each quarter
	   //number of collisions in each tenth
	   //average chain length
	   int numEntries = 0;
	   int numElements = 0;
	   int numChains = 0; //if size > 1
	   int maxChainLength = 0;
	   int numChainedElements = 0;
	   int index1 = 0;
	   int index2 = 0;
	   int qtr = table.length/4;
	   int tenth = table.length/10;
	   String quarters = "";
	   String tenths = "";
	   int qtrCount = 0;
	   int tenthCount = 0;
	   
	  
	   
	   for (Object obj : table) {
		   if (obj != null) {
			   numEntries++;
			   numElements++;
			   qtrCount++;
			   Object map = obj;
			   Field next = map.getClass().getDeclaredField("next");
			   next.setAccessible(true);
			   if (next.get(obj) != null)
				   numChains++; //if the chain is > 1
			   int chainLength = 1;
			   while(obj != null) {
				   obj = next.get(obj);
				   if (obj != null) {
					   numElements++;
					   numChainedElements++;
					   chainLength++;
					   tenthCount++;
				   }
			   }
			   if (chainLength > maxChainLength)
				   maxChainLength = chainLength;
		   }
		   index1++;
		   if (index1 == qtr) {
			   quarters += qtrCount + ", ";
			   qtrCount = 0;
			   index1 = 0;
		   }  
		   
		   index2++;
		   if (index2 == tenth) {
			   tenths += tenthCount + ", ";
			   tenthCount = 0;
			   index2 = 0;
		   }
	   }
	   System.out.println("\tNumber of entries: " + numEntries);
	   System.out.println("\tNumber of elements: " + numElements);
	   System.out.println("\tNumber of chains: " + numChains);
	   System.out.println("\tLoad factor: " + (double)numElements/numChains);
	   System.out.println("\tAverage chain length: " + numChainedElements/numChains);
	   System.out.println("\tMaximum chain length: " + maxChainLength);
	   System.out.println("\tNumber of entries in each quarter: " + quarters.substring(0, quarters.length() -2));
	   System.out.println("\tNumber of collisions in each tenth: " + tenths.substring(0, tenths.length() -2));
   }
   
   @Override
   public int hashCode() {
	   int hash = 0;
	   Field key = null;
	   try {
		key = this.getClass().getDeclaredField("key");
	} catch (NoSuchFieldException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SecurityException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   String s = "" + key;
	  
		for(int i = 0; i < s.length(); i++) {
				if (s.charAt(i) == 'x')
					hash += 1 * (int) (Math.pow(3, i));
				else if (s.charAt(i) == 'o')
					hash += 2 * (int) (Math.pow(3, i));
				else
					hash += 0;
		}
	  
	   return hash % NUM_WINNERS;
   }

   public static void main(String[] args) throws java.io.FileNotFoundException,
                                              NoSuchFieldException, 
                                              IllegalAccessException {

	 //read in and store the strings in your hash map, then close the file (done in constructor)
      TicTacToeMyHashMap m = new TicTacToeMyHashMap();
      
  //print out the capacity using the capacity() method
      System.out.println("Capacity: " + m.capacity());
      
  // print out the other analytical statistics as required in the assignment
      System.out.println("Report: ");
      report();
   }

}