import java.util.concurrent.TimeUnit;

public class Sequential {

	public static int generationNo = 0;
	public static int closestDistance = 40;
	public static int[] playersDistance = new int[Interface.players.length];

	// Running algorithm
	public static void RunSequential() throws InterruptedException {
		while (!Interface.goalReached) {
			PlayGeneration(Interface.minMoves, Interface.players);
			if (!Interface.goalReached) {
				NewGeneration(Interface.players);
			}
			generationNo++;
			Parallel.generationNo++;
		}

		for (int i = 0; i < playersDistance.length; i++) {
			playersDistance[i] = DistanceFromGoal(Interface.players[i]);
		}

		SortPlayers(playersDistance, Interface.players);
	}

	// Play out current generation of players
	public static void PlayGeneration(int numOfMoves, Player players[]) throws InterruptedException {
		int n = 0;
		while (n < (2 * numOfMoves)) {
			for (int i = 0; i < Interface.playerNum; i++)
				players[i].makeMove();
			TimeUnit.MILLISECONDS.sleep(Interface.speed);
			n++;
		}
	}

	// Mutate current generation of players
	public static void NewGeneration(Player players[]) {

		for (int i = 0; i < playersDistance.length; i++) {
			playersDistance[i] = DistanceFromGoal(players[i]);
		}

		SortPlayers(playersDistance, players);

		int noOfBestPlayers = Top10Percent(players.length);
		int averagePlayers = AveragePlayers(players.length);

		for (int i = 0; i < players.length; i++) {
			if (i < noOfBestPlayers) {
				// Keep top 10% of players same
				players[i].ResetPlayer();
			} else if (i >= noOfBestPlayers && i < (averagePlayers + noOfBestPlayers)) {
				// Mutate 11% - 80% of players for 10%
				players[i].MutatePlayer(10);
			} else {
				// Crossover the worst 20% of players with the best player
				Crossover(players[0], players[i]);
			}
		}
		Interface.info.setText("Generation: " + generationNo + " and closest distance to goal: " + playersDistance[0]);
		for (int i = 0; i < Interface.playerNum; i++)
			Interface.ranks[i].setText(i + 1 + ". " + Sequential.playersDistance[i]);
	}

	// Calculate the number of players in the top 10% of all players
	public static int Top10Percent(int numOfPlayers) {
		return Math.floorDiv(numOfPlayers * 10, 100);
	}

	// Calculate the number of players from 11% to 80%
	public static int AveragePlayers(int numOfPlayers) {
		return Math.floorDiv(((numOfPlayers - Top10Percent(numOfPlayers)) * 80), 100);
	}

	// Crossover player x with player y
	// First half of moves from x are now copied to y
	public static void Crossover(Player x, Player y) {
		for (int i = 0; i < Interface.minMoves; i++)
			x.moveOrder[i] = x.moveOrder[i];
		x.ResetPlayer();
		y.ResetPlayer();
	}

	// Calculate the number of moves needed for the player to reach the goal
	public static int DistanceFromGoal(Player player) {
		int distance = (Interface.map.length - player.X - 1) + (Interface.map[0].length - player.Y - 1);
		if (player.face == 0 || player.face == 3)
			distance++;
		return distance;
	}

	// Sort the players based on the distance to the goal after all moves are made
	public static void SortPlayers(int distances[], Player players[]) {
		for (int i = 0; i < distances.length; i++) {
			for (int j = i + 1; j < distances.length; j++) {
				if (distances[i] > distances[j]) {
					int tmpD = distances[i];
					distances[i] = distances[j];
					distances[j] = tmpD;

					Player tmpP = players[i];
					players[i] = players[j];
					players[j] = tmpP;
				}
			}
		}
	}
}