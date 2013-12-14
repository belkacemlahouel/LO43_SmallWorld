import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class MenuPanel extends JPanel {
	JButton Start,Pause;
	
	public MenuPanel()
	{
		super();
		GridLayout menuGrid = new GridLayout(15,1);
	    menuGrid.setVgap(5);
	    this.setLayout(menuGrid);
	    this.setBackground(Color.WHITE);
	    this.setPreferredSize(new Dimension(150,720));
	    JLabel lab1 = new JLabel ("Menu");
	    JButton butt1 = new JButton("Pause");
	    JButton butt2 = new JButton("Play");
	    this.add(lab1);
	    this.add(butt1);
	    this.add(butt2);
	}
	
}
