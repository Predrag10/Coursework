import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.*;

public class Interface {
	public static String playerNumString = JOptionPane.showInputDialog(null, "Enter number of players:");
	public static String sizeOfWorldString = JOptionPane.showInputDialog(null, "Enter the size of the world:");
	public static String runType = JOptionPane.showInputDialog(null, "Run Mode:" + "\nS - Sequential" + "\nP - Parallel");
	public static String runMode;
	public static int playerNum = Integer.parseInt(playerNumString);
	public static int sizeOfWorld = Integer.parseInt(sizeOfWorldString);
	public static int speed = 1000;
	public static int map[][] = new int[sizeOfWorld][sizeOfWorld];
	public static int width = 0;
	public static int height = 0;
	public static int goalX = map.length - 1;
	public static int goalY = map[0].length - 1;
	public static int minMoves = map.length + map[0].length;
	public static Player[] players = new Player[playerNum];
	public static int tileSize = 50;
	public static boolean goalReached = false;
	public static JLabel info = new JLabel("Generation: " + Sequential.generationNo + " and closest distance to goal: "
			+ Sequential.playersDistance[0]);;
	public static JPanel ranking = new JPanel();
	public static JPanel south = new JPanel(new BorderLayout());
	static JLabel[] ranks = new JLabel[playerNum];
	static JButton speedButton = new JButton("Speed up");
	public static double endTime;
	public static Font font1, font2;
	public static JLabel endLabel1, endLabel2, endLabel3;

	public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {

		double startTime = System.nanoTime();
		Listener listener = new Listener();

		// Creating world and players
		width = map.length * tileSize;
		height = map[0].length * tileSize;

		for (int i = 0; i < playerNum; i++) {
			players[i] = new Player(minMoves, map);
			players[i].AddMovesToPlayer();
		}

		for (int i = 0; i < Parallel.playersDistance.length; i++) {
			Parallel.playersDistance[i] = minMoves;
		}

		// Creating frame with components
		JFrame f = new JFrame("Genetic algorhitm");
		GUI gui = new GUI(tileSize, map, players);
		JLabel time;
		f.add(info, BorderLayout.NORTH);
		ranking.setLayout(new GridLayout(playerNum + 2, 0));
		speedButton.addActionListener(listener);
		ranking.add(speedButton);
		ranking.add(new JLabel("Leaderboard"));
		for (int i = 0; i < playerNum; i++) {
			ranks[i] = new JLabel(i + 1 + ". " + Parallel.playersDistance[i]);
			ranking.add(ranks[i], BorderLayout.CENTER);
		}
		f.add(gui, BorderLayout.CENTER);
		f.add(ranking, BorderLayout.EAST);

		// Creating panel with moves of winning player
		JPanel winningMoves = new JPanel();
		f.setPreferredSize(new Dimension(width + 480, (height + (2 * tileSize) + 144)));
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);

		// Use the run type selected by the user
		if (runType.equals("S") || runType.equals("s")) {
			Sequential.RunSequential();
			runMode = "Sequential";
			info = new JLabel("Generation: " + Sequential.generationNo + " and closest distance to goal: " + Sequential.playersDistance[0]);
		} else if (runType.equals("P") || runType.equals("p")) {
			Parallel.RunParallel();
			runMode = "Parallel";
			info = new JLabel("Generation: " + Parallel.generationNo + " and closest distance to goal: " + Parallel.playersDistance[0]);
		} else {
			Sequential.RunSequential();
			runMode = "Sequential";
			info = new JLabel("Generation: " + Sequential.generationNo + " and closest distance to goal: " + Sequential.playersDistance[0]);
		}

		// After the algorithm finishes output the path of the winning player
		// and the time required to reach the destination
		double endTime = System.nanoTime();
		f.remove(ranking);
		
		font1 = new Font("Arial", Font.PLAIN, 20);
		font2 = new Font("Arial", Font.PLAIN, 14);
		
		endLabel1 = new JLabel("Run Mode: " + runMode);
		endLabel1.setHorizontalAlignment(JLabel.CENTER);
		endLabel1.setFont(font1);
		endLabel2 = new JLabel("Path to goal was:");
		endLabel2.setFont(font1);
		endLabel3 = new JLabel("0. Starting position: facing right\n");
		endLabel3.setFont(font2);
		
		
		winningMoves.setLayout(new GridLayout(2 + minMoves * 2, 0));
		winningMoves.add(endLabel2);
		winningMoves.add(endLabel3);
		for (int i = 0; i < players[0].moveOrder.length; i++) {
			JLabel tmpLabel = new JLabel(((i + 1) + ". " + players[0].tmpStrings[i]));
			tmpLabel.setFont(font2);
			winningMoves.add(tmpLabel);
		}
		time = new JLabel("Run time: " + (double) Math.round(((endTime - startTime) / 1000000000) * 1000) / 1000 + " seconds.");
		time.setFont(font1);
		f.add(winningMoves, BorderLayout.EAST);
		south.add(endLabel1, BorderLayout.WEST);
		south.add(time, BorderLayout.EAST);
		f.add(south, BorderLayout.SOUTH);
	}
}