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
import kernel.Tribe;

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
			/*
			 * @author Belkacem @date 02/01/14
			 * Modification of the Resource Panel, printing all the information
			 */
			Tribe tmp = swGUI.getSw().getTribeAt(i);
			tribesResourceList.get(i).setVisible(true);
			tribesResourceList.get(i).setLayout(new GridLayout(6,1));;
			tribesResourceList.get(i).add(new JLabel("Tribu " + (i+1) + " (" + tmp.getIndividualType() + ")"));
			tribesResourceList.get(i).add(new JLabel("Bois : " + tmp.getResources().get("Wood")));
			tribesResourceList.get(i).add(new JLabel("Pierre : " + tmp.getResources().get("Rock")));
			tribesResourceList.get(i).add(new JLabel("Nourriture : " + tmp.getResources().get("Food")));
			tribesResourceList.get(i).add(new JLabel("Plutonium : " + tmp.getResources().get("Plutonium")));
			tribesResourceList.get(i).add(new JLabel("Metal : " + tmp.getResources().get("Metal")));
		}
		
		// OMG HOW WEIRD IS THAT? TODO
		for(int i=0;i<tribesResourceList.size();i++)
		{
			this.add(tribesResourceList.get(i));
		}
		
	}
	
	/*protected void paintComponent(Graphics g) {
		g.fillOval(100, 100, 100, 100);
	}*/

	
}
