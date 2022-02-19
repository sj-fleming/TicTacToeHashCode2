import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Scanner;

/*
 * @author Sarah Fleming
 * TicTacToeHashMap part C version 2
 */

public class TicTacToeHashMap  {

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

   TicTacToeHashMap() {
	   //Instantiate/fill HashMap ... pay attention to initial capacity and load values
	   //initial capacity set to twice the number of expected values
	   winnersMap = new HashMap<String, Boolean>(NUM_WINNERS * 2); 
   
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
   
   // TODO using the same code to get the table of entries as in the capacity method,
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
	   int numEntries = 0;
	   int numElements = 0;
	   int numChains = 0; //if size > 1
	   int maxChainLength = 0;
	   int numChainedElements = 0;
	   
	   //load factor
	   
	   //number of entries in each quarter
	   
	   //number of collisions in each tenth
	   
	   //average chain length
	   
	  
	   
	   for (Object obj : table) {
		   if (obj != null) {
			   numEntries++;
			   numElements++;
			   Object map = obj;
			   Field next = map.getClass().getDeclaredField("next");
			   next.setAccessible(true);
			   while(obj != null) {
				   obj = next.get(obj);
				   if (obj != null)
					   numElements++;
			   }
		   }
		   
		   
//		   HashMap.Node<?, ?> map = table[i];
		
//		   if (table[i] != null && !map.isEmpty()) {
//			   numEntries++;
//			   numElements += map.size();
//			   if (map.size() > 1) {
//				   numChains++;
//				   numChainedElements += map.size();
//			   }
//			   if (map.size() > maxChainLength)
//				   maxChainLength = map.size();
//		   }   
	   }
	   System.out.println("\tNumber of entries: " + numEntries);
	   System.out.println("\tNumber of elements: " + numElements);
	   System.out.println("\tLoad factor: " + numElements/numChains);
	   System.out.println("\tAverage chain length: " + numChainedElements/numChains);
	   System.out.println("\tMaximum chain length: " + maxChainLength);
	   
   }

   public static void main(String[] args) throws java.io.FileNotFoundException,
                                              NoSuchFieldException, 
                                              IllegalAccessException {

	 //read in and store the strings in your hash map, then close the file (done in constructor)
      TicTacToeHashMap m = new TicTacToeHashMap();
      
  //print out the capacity using the capacity() method
      System.out.println("Capacity: " + m.capacity());
      System.out.println("Report: ");
      report();
  // TODO print out the other analytical statistics as required in the assignment
  
   }

}