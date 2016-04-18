import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Place extends JComponent {
	String name;
	int x, y;
	boolean isClicked = false;

	public Place(int x, int y, String name) {
		this.name = name;
		this.x = x;
		this.y = y;
		setBounds(x - 5, y - 5, 80, 40);
		setMaximumSize(new Dimension(100, 40));
		setMinimumSize(new Dimension(100, 40));
		setPreferredSize(new Dimension(100, 40));
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if(isClicked) {
			g.setColor(Color.MAGENTA);
			g.fillOval(0, 0, 10, 10);
			g.setFont(new Font("Arial", Font.BOLD, 16));
			g.drawString(name, 20, 15);
				
		} else {
			g.setColor(Color.DARK_GRAY);
			g.fillOval(0, 0, 10, 10);
			g.setFont(new Font("Arial", Font.BOLD, 16));
			g.drawString(name, 15, 15);

		}
	}

	public String getName() {
		return name;
		
	}

	public String toString() {
		return name;
		
	}
}