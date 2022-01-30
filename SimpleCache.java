// Liam Downs
// 11/15/2020
// Implement a Simple Cache with set associativity options for direct-mapped, set associative (2-way/4-way) (LRU replacement policy), and fully associative (LRU replacement policy).
	
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class SimpleCache{

	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);	//Scanner to take options
		
		while (true) {	//Infinite loop unless exit program

			System.out.print("Type number of cache blocks: ");
			int blockSize = input.nextInt();	//Takes block
			
			System.out.print("\nType set associativity #:" 
					+ "\n1. Direct-mapped"
					+ "\n2. 2-way set associative"
					+ "\n3. 4-way set associative"
					+ "\n4. Fully associative"
					+ "\n#: "
			);
			int setAssocOpt = input.nextInt();	//Selects associativity
			int setAssoc;
			if (setAssocOpt == 1) {
				setAssoc = blockSize;
			}
			else if (setAssocOpt == 2) {
				setAssoc = 2;
			}
			else if (setAssocOpt == 3) {
				setAssoc = 4;
			}
			else {
				setAssoc = 1;
			}
			
			System.out.print("\nType policy #:"
					+ "\n1. LRU"
					+ "\n#: "
			);
			int policy = input.nextInt();	//Takes policy
			
			System.out.println("\nType addresses:"
					+ "\nex. [ 0 1 2 3 ]"
			);
			ArrayList<Integer> addressList = new ArrayList<Integer>();	//Takes addresses into list
			input.next();
			while (input.hasNextInt()) {
				addressList.add(input.nextInt());
			}
			input.next();
			
			if (policy == 1) {	//Calls cache method
				LRU(setAssoc, blockSize, addressList);
			}
			else {
				System.out.println("\nPlease select a valid policy.");
			}
			
			System.out.print("\nType c to continue, e to exit: "); //Prompt to continue
			char next = input.next().charAt(0);
			
			if (next == 'e') {	//Exit program
				input.close();
				System.exit(0);
			}
			System.out.println();
			
		}

	}
	
	public static void LRU(int setAssoc, int blockSize, ArrayList<Integer> addressList) {
		
		int setSize = blockSize/setAssoc;	//Computes block and set ratio

		int[][] block = new int[setAssoc][setSize];
		int[] HM = new int[] {0, 0};	//Hit/Miss
		
		
		ArrayList<LinkedList<Integer>> record = new ArrayList<>();	//Creates LRU record and fills block with -1
		for (int i = 0; i < setAssoc; i++) {
			record.add(new LinkedList<Integer>());
			Arrays.fill(block[i], -1);
		}

		for (int i = 0; i < addressList.size(); i++) {
			
			int a = addressList.get(i);
			int b = a % setAssoc;
			
			boolean test = false;	//Checks if address is in block and adjusts record
			for (int j = 0; j < setSize; j++) {
				if (a == block[b][j]) {
					test=true;
					record.get(b).remove((Integer)j);
					record.get(b).add((Integer)j);
					break;
				}
			}
			
			int s = record.get(b).size();	//Adds address to block, adjusts record, and adds to Hit/Miss
			if (test == false) {
				if (s >= setSize) {
					int c = record.get(b).get(0);
					block[b][c] = a;
					record.get(b).remove(0);
					record.get(b).add((Integer)c);
				}
				else {
					block[b][s] = a;
					record.get(b).add((Integer)s);
				}
				HM[1]++;
			}
			else {
				HM[0]++;
			}
			
		}

		System.out.println("\nMiss Rate: " + (HM[1] * 100.0 / (HM[0] + HM[1])) + "%");	//Prints cache
		System.out.print("Cache Content: ");
		for (int i = 0; i < block.length; i++) {
			System.out.print(Arrays.toString(block[i]));
		}
		System.out.println("\n(-1 is empty)");
		
	}
	
}
