/*__________________________________________________________________________________________________________
  - Name:                Frederick Abi Chahine
  - Course:              BIF245 - Algorithms & Data-structures
  - Date Last Modified:  03/05/2021
  - Program Description: This program implements Gale-Shapley, which means that we want to
                         develop a stable marriage between all males and females. We have
                         equal numbers of men and women, thus we need to marry them to each
                         other based on their preference list. A man marries a woman he 
                         prefers if she is single, however, if she is not single then we
                         compare the man to her husband and check which man does this woman
                         prefer more. If she prefers her husband then they remain married 
                         and the other man will remain single until he is dequeued again.
                         If she prefers the man over her husband then she will divorce her
                         husband and marry the other man. Her ex-husband will now be single
                         and enqueued into the single array until he is dequeued later.
                         The program terminates once all men and women are married to exactly
                         one person and a stable marriage is formed. No married man or women 
                         should like someone else that also likes them back.
  - Associated files:    None
  __________________________________________________________________________________________________________*/

public class GaleShapley {
	
	private int[] queue; //this queue will be used in order to store single men. Married men will be dequeued, single / divorced men will be enqueued
	private int first; //a pointer to indicate where to dequeue from
	private int last; //a pointer to indicate where to enqueue from
	
	public GaleShapley(int size) { //size would depend on what we input in main.
		queue = new int[size]; 
		first = 0;
		last = 0;
	}
	
	public void enqueue(int i) {
		
		queue[last]= i; //assigns the man to the index that last is pointing to
		last++; //increments last pointer in order to add to the next index in the next enqueue
		if (last==queue.length) { //simulating a circular array
			last= 0;
		}
	}
	
	public int dequeue() {
		
		int temp = queue[first]; //storing it to return the man for later use
		queue[first]= -99; //sentinel value indicating empty
		first++; //increments first pointer in order to dequeue the next index at the next round
		if (first==queue.length) { //simulating a circular array
			first=0;
		}
		return temp;
	}
	
	public void marriage(int[] marriage_list, int[][] women_preference_list, int[][] men_preference_list, int[] men_proposals) {
			 

		while ( !( (first == last) && (queue[first] == -99) ) ) {
			
			int man = dequeue();
			int preference = men_preference_list[man][men_proposals[man]];
			
			if (marriage_list[preference] == -99) { //implies that the woman is not married and ready to be married
				marriage_list[preference] = man;
			}
			else { //implies that woman is married, so we check which man does she prefer.
				int her_husband = marriage_list[preference];
				int husband_index = -9; 
				int man_index = -9;
				
				for (int i = 0; i < women_preference_list[preference].length; i++) {
					if (women_preference_list[preference][i] == her_husband) {
						husband_index = i;
					}
					if (women_preference_list[preference][i] == man) {
						man_index = i;
					}
				}
				if (husband_index < man_index) { //this implies that she prefers her husband over the man
					enqueue(man); //we re-enqueue man as he is still single
					men_proposals[man] = men_proposals[man] + 1; //we increment by 1 so the next time he will check his next preferred woman
				}
				else { //here she prefers the man over her husband, so we need her to divorce and re-marry the new man.
					marriage_list[preference] = man; //we are changing her married index to the new man
					enqueue(her_husband); //since her ex-husband is now single we should add him to the queue
				}
			}
		}
	}

	public static void main(String[] args) {
		
		int num = 4; //number of men = women
		GaleShapley gale = new GaleShapley(num);
		
		gale.enqueue(0); //we enqueue the men in order to dequeue them respectively and re-enqueue the divorced men.
		gale.enqueue(1);
		gale.enqueue(2);
		gale.enqueue(3);
		
		int[] marriage_list = new int[] {-99,-99,-99,-99}; // the indices will represent the woman # and the value will represent the man #. -99 is a sentinel value that indicates that the woman is not married.
		int[][] women_preference_list = new int[][] {{0,1,2,3},{1,0,3,2},{0,3,2,1},{3,2,0,1}};
		int[][] men_preference_list = new int[][] {{2,1,3,0},{1,0,2,3},{0,2,1,3},{0,1,2,3}};
		int[] men_proposals = new int[num]; //initially all will be 0. This will indicate the number of times man X has proposed before in order to avoid checking his same preference, to progress.
		
		gale.marriage(marriage_list, women_preference_list, men_preference_list, men_proposals);
		
		for (int x = 0; x < marriage_list.length; x++) { //for visual purposes
			System.out.println("Woman " + x + " married to Man " + marriage_list[x]);
		}
		System.out.println("\nAll Done. We have a stable marriage for all!");
	}
	
	/*CODE LOGIC:
	 * Hard code the number of males and females (should be =)
	 * Hard code their preferences
	 * Use 2D array for women and their preferences ( x indices refer to the women, y indices refer to their preference scale, smaller index = more preferable man )
	 * Use Qs for men in order for us to enqueue single men and dequeue married men ~
	 * once the Q is empty => all men and women are married => code terminates
	 * Have an array with sentinel values, substitute these values with the man a woman is married to (index is the woman)
	 * for male preferences, use arrays and have an additional array in which it stores the number of time each man got married, use that to increment their preference list.
	 * */

//	int[][] women_preference_list = new int[][] {{1,0,2},{2,0,1},{0,1,2}};
//	int[][] men_preference_list = new int[][] {{0,2,1},{0,1,2},{1,2,3}};
}
