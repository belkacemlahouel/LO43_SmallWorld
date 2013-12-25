package gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ResourcePanel extends JComponent{

	private ArrayList<JPanel> tribesResourceList;
	
	public ResourcePanel(SmallWorldGUI swGUI){
		super();	
		setVisible(false);
		tribesResourceList = new ArrayList<JPanel>();
		//GridBagConstraints gbc1 = new GridBagConstraints();
		this.setLayout(new FlowLayout());
		
		for(int i=0;i<swGUI.getSw().getTribeList().size();i++)
		{
			tribesResourceList.add(new JPanel());
		}
		
		for(int i=0;i<tribesResourceList.size();i++)
		{
			tribesResourceList.get(i).setVisible(true);
			tribesResourceList.get(i).setLayout(new GridLayout(6,1));;
			tribesResourceList.get(i).add(new JLabel("Tribu " + (i+1)));
			tribesResourceList.get(i).add(new JLabel("Bois : "));
			tribesResourceList.get(i).add(new JLabel("Pierre : "));
			tribesResourceList.get(i).add(new JLabel("Nourriture : "));
			tribesResourceList.get(i).add(new JLabel("Plutonium : "));
			tribesResourceList.get(i).add(new JLabel("Metal : "));
		}
		
		for(int i=0;i<tribesResourceList.size();i++)
		{
			this.add(tribesResourceList.get(i));
		}
		
	}
		protected void paintComponent(Graphics g) {
		g.fillOval(100, 100, 100, 100);
	}

	
}
