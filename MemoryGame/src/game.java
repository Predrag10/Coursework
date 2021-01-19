import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class game implements ActionListener {

	private static JFrame frame;
	private JPanel choices, southPanel;
	private JLabel label, label2;
	private static JButton easy, medium, hard, hint, hintCard;
	public JButton[][] cards;
	private Font font;
	private board boardGame;
	public static int[][] cardsSelected;
	private static int cardsOpened = 0;
	private static int firstCardValue = -1, firstCardI = -1, firstCardJ = -1;
	private static int correct = 0, score = 0;
	private static int closeCard1I, closeCard1J, closeCard2I, closeCard2J;
	private static boolean cardsMatch = false;

	public game() {
		// creating the frame
		frame = new JFrame("Memory Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1080, 900);
		frame.setLayout(new BorderLayout());

		// adding the elements and structuring them
		choices = new JPanel();
		easy = new JButton("Easy");
		medium = new JButton("Medium");
		hard = new JButton("Hard");
		hint = new JButton("Hint");
		choices.add(easy);
		choices.add(medium);
		choices.add(hard);

		label = new JLabel("Choose your difficulty");
		font = new Font("Arial", Font.PLAIN, 20);
		label.setFont(font);

		easy.setFont(font);
		medium.setFont(font);
		hard.setFont(font);
		hint.setFont(font);
		frame.add(label, BorderLayout.NORTH);
		label.setHorizontalAlignment(JLabel.CENTER);
		frame.add(choices, BorderLayout.CENTER);

		easy.addActionListener(this);
		medium.addActionListener(this);
		hard.addActionListener(this);
		hint.addActionListener(this);

		frame.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (game.easy == e.getSource()) {
			setupDifficulty(4);
		} else if (game.medium == e.getSource()) {
			setupDifficulty(6);
		} else if (game.hard == e.getSource()) {
			setupDifficulty(10);
		} else if (game.hint == e.getSource()) {

			// player used hint button
			if (cardsOpened % 2 != 0) {
				for (int i = 0; i < cards.length; i++) {
					for (int j = 0; j < cards[0].length; j++) {
						if ((i != firstCardI || j != firstCardJ) && firstCardValue == boardGame.getBoard()[i][j]) {
							cards[i][j].setBackground(Color.ORANGE);
							hintCard = cards[i][j];
							hint.setEnabled(false);
							score += 5;
							label.setText("Score: " + score);
						}
					}
				}
			}

		} else {

			// card selected
			JButton clickedCard = (JButton) e.getSource();
			if(hintCard != null  && hintCard.getBackground() != Color.green) {
				hintCard.setBackground(new JButton().getBackground());
			}
			hint.setEnabled(true);

			
			// if we did not match last 2 cards, then hide their value from the player
			if (!cardsMatch) {
				cards[closeCard1I][closeCard1J].setText("");
				cards[closeCard1I][closeCard1J].setBackground(new JButton().getBackground());
				cards[closeCard2I][closeCard2J].setText("");
				cards[closeCard2I][closeCard2J].setBackground(new JButton().getBackground());
				cardsMatch = true;
			}

			

			for (int i = 0; i < cards.length; i++) {
				for (int j = 0; j < cards[0].length; j++) {
					if (clickedCard == cards[i][j] && (i != firstCardI || j != firstCardJ) && cardsSelected[i][j] != 1) {
						
						int cardValue = boardGame.getBoard()[i][j]; // get card value
						
						clickedCard.setText(Integer.toString(cardValue)); // show card value to the player
						clickedCard.setFont(font);
						clickedCard.setBackground(Color.green);

						cardsOpened++; // increase counter for currently opened cards

						
						if (cardsOpened % 2 == 0) { // check to see it the two opened cards match
							if (cardValue == firstCardValue) {
								cardsSelected[firstCardI][firstCardJ] = 1;
								cardsSelected[i][j] = 1;
								cards[i][j].setEnabled(false);
								cards[firstCardI][firstCardJ].setEnabled(false);
								firstCardValue = -1;
								firstCardI = -1;
								firstCardJ = -1;
								correct++;
								score++;
								label.setText("Score: " + score);
							} else { // if the 2 opened cards do not match save the indices to close them
								closeCard1I = i;
								closeCard1J = j;
								closeCard2I = firstCardI;
								closeCard2J = firstCardJ;
								firstCardValue = -1;
								firstCardI = -1;
								firstCardJ = -1;
								
								cardsMatch = false;
								score++;
								label.setText("Score: " + score);
							}
						} else { // player opened 1 card, store its location and value
							firstCardValue = cardValue;
							firstCardI = i;
							firstCardJ = j;
						}
					}
					if (correct == ((cards.length * cards[0].length) / 2)) {						
						label.setText("Your score is " + score);
						label.setFont(new Font("Arial", Font.PLAIN, 40));
						label2 = new JLabel("Best possible score is: " + ((cards[0].length * cards[0].length) / 2));
						label2.setFont(font);
						
						choices.removeAll();
						frame.remove(choices);
						frame.remove(hint);
						frame.remove(label);
						frame.remove(southPanel);
						frame.add(BorderLayout.CENTER, label);
						frame.add(BorderLayout.SOUTH, label2);
						label2.setHorizontalAlignment(JLabel.CENTER);
						frame.repaint();
						break;
					}

				}
			}
		}

	}

	public void setupDifficulty(int selectedDifficulty) {
		choices.removeAll();
		frame.remove(choices);
		label.setText("Score: 0");
		frame.repaint();
		boardGame = new board(selectedDifficulty);

		choices.setLayout(new GridLayout(boardGame.difficulty, boardGame.difficulty));
		frame.add(choices, BorderLayout.CENTER);
		southPanel = new JPanel();
		southPanel.setLayout(new FlowLayout());
		southPanel.add(hint);
		southPanel.add(new JLabel("*Using hint increase your score by 5"));
		frame.add(southPanel, BorderLayout.SOUTH);

		cards = new JButton[boardGame.difficulty][boardGame.difficulty];
		cardsSelected = new int[boardGame.difficulty][boardGame.difficulty];

		for (int i = 0; i < boardGame.difficulty; i++) {
			for (int j = 0; j < boardGame.difficulty; j++) {
				cards[i][j] = new JButton();
				cards[i][j].addActionListener(this);
				choices.add(cards[i][j]);
				cardsSelected[i][j] = 0;
			}
		}
	}
}