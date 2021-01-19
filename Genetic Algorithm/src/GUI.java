import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Random;

import javax.swing.JPanel;

public class GUI extends JPanel {
	Image position;
	Image goal;
	Image up;
	Image right;
	Image down;
	Image left;
	int tileSize;
	Player players[];
	int map[][];
	Random r = new Random();

	public GUI(int tileSize, int map[][], Player players[]) {
		this.tileSize = tileSize;
		this.map = map;
		this.players = players;

		// Select images for corresponding element
		position = Toolkit.getDefaultToolkit().createImage("Images/square.png");
		goal = Toolkit.getDefaultToolkit().createImage("Images/goal.png");
		up = Toolkit.getDefaultToolkit().createImage("Images/up.png");
		right = Toolkit.getDefaultToolkit().createImage("Images/right.png");
		down = Toolkit.getDefaultToolkit().createImage("Images/down.png");
		left = Toolkit.getDefaultToolkit().createImage("Images/left.png");

	}

	// Draw the images for each component
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map.length; j++) {
				g.drawImage(position, i * tileSize, j * tileSize, tileSize, tileSize, null);
			}
		}
		g.drawImage(goal, Interface.goalX * tileSize, Interface.goalY * tileSize, tileSize, tileSize, null);
		for (int i = 0; i < players.length; i++) {
			for (int j = 0; j < (2 * (map.length + map[0].length)); j++) {
				switch (players[i].face) {
				case (0):
					g.drawImage(up, players[i].X * tileSize, players[i].Y * tileSize, tileSize, tileSize, null);
					break;
				case (1):
					g.drawImage(right, players[i].X * tileSize, players[i].Y * tileSize, tileSize, tileSize, null);
					break;
				case (2):
					g.drawImage(down, players[i].X * tileSize, players[i].Y * tileSize, tileSize, tileSize, null);
					break;
				case (3):
					g.drawImage(left, players[i].X * tileSize, players[i].Y * tileSize, tileSize, tileSize, null);
					break;
				default:
					System.out.println("Error while drawing :(");
					break;
				}
			}
		}

		repaint();
	}
}