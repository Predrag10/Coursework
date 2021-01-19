public class PlayerThread extends Thread{
	
	int numOfMoves;
	Player player;

	public PlayerThread(int numOfMoves, Player player) {
		this.numOfMoves = numOfMoves;
		this.player = player;
	}
	
	public void run() {
		int n = 0;
		while(n < (2 * numOfMoves)) {
			player.makeMove();
			n++;
		}
	}
}