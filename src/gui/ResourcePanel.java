package gui;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import kernel.Tribe;

/* 
 * Author : Luc CADORET
 * This class corresponds to the panel that displays all the resources of each tribes during the game
 */

public class ResourcePanel extends JComponent {

	private JPanel [] panelList;
	private JLabel [][] tribesResourceList;
	private SmallWorldGUI swGUI;
	
	public ResourcePanel (SmallWorldGUI swGUI_) {
		super();	
		setVisible(false);
		this.setLayout(new FlowLayout());
		swGUI = swGUI_;
		tribesResourceList = new JLabel[swGUI.getSw().getTribeList().size()][6];
		panelList = new JPanel [swGUI.getSw().getTribeList().size()];
		
		for(int i=0 ; i<swGUI.getSw().getTribeList().size() ; ++i) {
			panelList[i] = new JPanel();
		}
		
		for(int i=0;i<panelList.length;i++)
		{
			Tribe tmp = swGUI.getSw().getTribeAt(i);
			panelList[i].setVisible(true);
			panelList[i].setLayout(new GridLayout(6,1));
			tribesResourceList[i][0] = (new JLabel("Tribu " + (i+1) + " (" + tmp.getIndividualType() + ")"));
			panelList[i].add(tribesResourceList[i][0]);
			tribesResourceList[i][1]= (new JLabel("Bois : " + tmp.getResources().get("Wood")));
			panelList[i].add(tribesResourceList[i][1]);
			tribesResourceList[i][2]=(new JLabel("Pierre : " + tmp.getResources().get("Rock")));
			panelList[i].add(tribesResourceList[i][2]);
			tribesResourceList[i][3]=(new JLabel("Nourriture : " + tmp.getResources().get("Food")));
			panelList[i].add(tribesResourceList[i][3]);
			tribesResourceList[i][4]=(new JLabel("Plutonium : " + tmp.getResources().get("Plutonium")));
			panelList[i].add(tribesResourceList[i][4]);
			tribesResourceList[i][5]=(new JLabel("Metal : " + tmp.getResources().get("Metal")));
			panelList[i].add(tribesResourceList[i][5]);
		}
		
		/* Whe add on the resource panel */
		for (int i=0 ; i<panelList.length ; ++i) {
			this.add(panelList[i]);
		}
	}
	

	public void paintComponent (Graphics g) {
		for(int i=0 ; i<panelList.length ; ++i) {
			
			Tribe tmp = swGUI.getSw().getTribeAt(i);
			
			tribesResourceList[i][1].setText("Bois : " + tmp.getResources().get("Wood"));
			tribesResourceList[i][2].setText("Pierre : " + tmp.getResources().get("Rock"));
			tribesResourceList[i][3].setText("Nourriture : " + tmp.getResources().get("Food"));
			tribesResourceList[i][4].setText("Plutonium : " + tmp.getResources().get("Plutonium"));
			tribesResourceList[i][5].setText("Metal : " + tmp.getResources().get("Metal"));
			
		}
	}
}