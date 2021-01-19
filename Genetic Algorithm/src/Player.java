import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
public class Player implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	String[] tmpStrings = new String[Interface.minMoves * 2];

	static Random r = new Random();
	int map[][];
	int moveOrder[];
	int movesMade = 0;
	int minMoves = 0;
	Queue<Integer> moves = new LinkedList<Integer>();
	int X = 0;
	int Y = 0;
	int face;
	// 0 - up
	// 1 - right
	// 2 - down
	// 3 - left

	public Player(int minMoves, int map[][]) {
		this.map = map;
		this.minMoves = minMoves;
		this.moveOrder = new int[2 * minMoves];
	}

	// Add random moves to a new player
	public void AddMovesToPlayer() {
		// 0 - turn left
		// 1 - turn right
		// 2 - go forward
		for (int i = 0; i < (2 * minMoves); i++) {
			moves.add(r.nextInt(3));
		}
		this.face = 1;
	}

	// Fill the queue with moves for this player in the next generation
	public void ResetPlayer() {
		for (int i = 0; i < moveOrder.length; i++) {
			moves.add(moveOrder[i]);
		}
		this.X = 0;
		this.Y = 0;
		this.movesMade = 0;
		this.face = 1;
	}

	// Check to see if the goal has been reached, if yes show the path from start to finish, if not then
	// Pop the first element from the queue and change the X, Y or face value based on the value
	public void makeMove() {
		if (CheckForVictory(this)) {
			// System.out.println("VICTORY");
			Interface.goalReached = true;
			for (int i = 0; i < tmpStrings.length; i++) {
				if (moveOrder[i] == 0)
					tmpStrings[i] = "turn left";
				else if (moveOrder[i] == 1)
					tmpStrings[i] = "turn right";
				else
					tmpStrings[i] = "go forward";

				Interface.info.setText("Generation: " + Parallel.generationNo + 1 + " and closest distance to goal: " + 0);
			}
		} else {
			moveOrder[this.movesMade] = moves.remove();
			switch (moveOrder[this.movesMade]) {
			case (0):
				this.movesMade++;
				if (face == 0)
					face = 3;
				else
					face--;
				break;
			case (1):
				this.movesMade++;
				if (face == 3)
					face = 0;
				else
					face++;
				break;
			case (2):
				this.movesMade++;
				if (face == 0 && Y - 1 >= 0)
					Y--;
				else if (face == 1 && X + 1 <= map[0].length - 1)
					X++;
				else if (face == 2 && Y + 1 <= map.length - 1)
					Y++;
				else if (face == 3 && X - 1 >= 0)
					X--;
				break;
			default:
				System.out.println("Error making move :(");
				break;
			}
		}
	}

	public static boolean CheckForVictory(Player player) {
		if (player.X == Interface.goalX && player.Y == Interface.goalY)
			return true;
		else
			return false;
	}

	// Change random moves for this player from the old generation based on the
	// percentage of mutation provided
	public void MutatePlayer(int PercentOfMutation) {
		int NoOfMutations = CalculateGeneModifications(PercentOfMutation);
		Random mutate = new Random();
		for (int i = 0; i < NoOfMutations; i++) {
			moveOrder[mutate.nextInt(moveOrder.length)] = r.nextInt(3);
		}
		this.ResetPlayer();
	}

	// Calculate the number of moves that need to change based on the percentage of
	// mutation provided
	public int CalculateGeneModifications(int percent) {
		return Math.floorDiv(percent * moveOrder.length, 100);
	}
}