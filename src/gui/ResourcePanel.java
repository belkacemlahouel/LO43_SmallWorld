package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class ResourcePanel extends JPanel {

	public ResourcePanel(SmallWorldGUI swGUI){
		super();
		
		this.addKeyListener(new KeyboardListener(swGUI));
		
		this.setFocusable(true);
		this.requestFocusInWindow();
		
		
	    JButton glassButton = new JButton("Hide");
	    this.add(glassButton);
	    this.setOpaque(false);
		this.setVisible(false);
	}
		protected void paintComponent(Graphics g) {
		g.fillOval(100, 100, 100, 100);
	}

	
}
