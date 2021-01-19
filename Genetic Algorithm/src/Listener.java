import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Listener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		if (Interface.speed == 0) {
			Interface.speed = 1000;
			b.setText("Speed up");
		} else {
			Interface.speed = 0;
			b.setText("Slow down");
		}
	}
}