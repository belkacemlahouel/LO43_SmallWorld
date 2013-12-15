import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPanel;

public class SmallWorldGUI extends JFrame{
	
	private MenuPanel pan1;
	private MapPanel pan2;
	private WeakReference<SmallWorld> sw; // Weak
	
	public SmallWorldGUI(SmallWorld sw)
	{
	    this.sw = new WeakReference<SmallWorld> (sw);
		this.setTitle("SmallWorld");
	    this.setSize(1280, 720);
	    this.setLocationRelativeTo(null);
	    this.setResizable(false);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	    getContentPane().setBackground(Color.GREEN);        
	    getContentPane().setLayout(new BorderLayout());
	    
	    pan1 = new MenuPanel(sw);
	    
	    pan2 = new MapPanel(sw.getRes(),sw.getTribeList());
	    	    
	    getContentPane().add(pan1,BorderLayout.WEST);
	    getContentPane().add(pan2,BorderLayout.EAST);
	    this.setVisible(true);
	    getContentPane().validate();
	    
	}
	
	/* This method updates the map, with all the new positions and so on*/
	
	public MenuPanel getPan1() {
		return pan1;
	}

	public void setPan1(MenuPanel pan1) {
		this.pan1 = pan1;
	}

	public MapPanel getPan2() {
		return pan2;
	}

	public void setPan2(MapPanel pan2) {
		this.pan2 = pan2;
	}

	public void updateMapPanel()
	{
		pan2.repaint();
	}
	
	
}
