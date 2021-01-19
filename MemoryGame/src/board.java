import java.util.ArrayList;
import java.util.Random;

public class board {

	final public int[][] gameboard;
	int difficulty;
	int card;
	Random random = new Random();
	ArrayList<Integer> allCards = new ArrayList<Integer>();
	
	
	//filling up the board with random pairs of numbers
	public board(int difficulty){
		
		this.difficulty = difficulty;
		
		gameboard = new int[difficulty][difficulty];
		
		for(int i = 0; i < difficulty - 1; i += 2) {
			for (int j = 0; j < difficulty; j++) {
				card = random.nextInt(100);
				while(allCards.contains(card)) {
					if(card < 99)
						card++;
					else
						card = 1;
				}
				
				allCards.add(card);
				gameboard[i][j] = card;
				gameboard[i + 1][j] = card;
			}
		}
		
		shuffle();
	}
	
	//used to mix up the positions of the numbers on the board	
	public void shuffle() {
		int index, jindex, temp;
		for (int i = gameboard.length - 1; i > 0; i--) {
			for (int j = gameboard.length - 1; j > 0; j--) {
				index = random.nextInt(i + 1);
				jindex = random.nextInt(j + 1);
				temp = gameboard[index][jindex];
				gameboard[index][jindex] = gameboard[i][j];
				gameboard[i][j] = temp;
			}
		}
	}
	
	public int[][] getBoard(){
		return gameboard;
	}
	
}