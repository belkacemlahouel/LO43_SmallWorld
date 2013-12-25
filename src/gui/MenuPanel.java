package gui;

import kernel.*;
import xml_parser.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/* The MenuPanel class corresponds to the panel that is on the left during the game, giving several options to the player */

public class MenuPanel extends JPanel{
	private JButton pause, addIndiv;
	private JLabel mainTitle, barTitle, game_over, winners;
	private JComboBox tribesComboBox;
	
	/*
	 * @author Belkacem
	 * Boolean for the integration of the action on the play/pause button
	 */
	private boolean play = false;
	
	public MenuPanel(final SmallWorldGUI swGUI, final SmallWorld sw)
	{
		
		super();
		GridLayout menuGrid = new GridLayout(15,1);
	    menuGrid.setVgap(5);
	    this.setLayout(menuGrid);
	    this.setBackground(Color.WHITE);
	    this.setPreferredSize(new Dimension(170,720));

	    tribesComboBox = new JComboBox();
	    
	    for(int i=0;i<sw.getTribeList().size();i++)
	    {
	    	tribesComboBox.addItem("Tribe " + (i+1));
	    }
	    
	    addIndiv = new JButton("Ajouter individu");
	    mainTitle = new JLabel ("Menu d'actions");
	    pause = new JButton("Play/Pause");
		
		/*
		 * @author Belkacem
		 * Implmentation of the action after clicking on the play/pause button
		 */
		pause.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				play = !play;
			}
		});
	    
	    addIndiv.addActionListener(new ActionListener(){
	    	
	    	public void actionPerformed(ActionEvent arg0){	
	    		
	    		if(sw.getTribeAt(tribesComboBox.getSelectedIndex()).getPopulation().get(0) instanceof Human)
	    		{
	    			sw.addIndividual(new Human(sw.getBoard().randPosition(),""),tribesComboBox.getSelectedIndex());
	    		}
	    		else if(sw.getTribeAt(tribesComboBox.getSelectedIndex()).getPopulation().get(0) instanceof Robot)
	    		{
	    			sw.addIndividual(new Robot(sw.getBoard().randPosition(),""),tribesComboBox.getSelectedIndex());
	    		}
	    	}
		});
	    
	    this.add(mainTitle);
	    this.add(pause);
	    this.add(tribesComboBox);
	    this.add(addIndiv);
	}

	
	/*
	 * @author Belkacem
	 * Takes care of the action performed on the play/pause button
	 */
	public boolean getPlay () {
		return play;
	}

	/*
	 * @author Belkacem
	 * disabling Buttons to stop possible clicks
	 */
	public void disableButtons() {
		// pause.enable(false);
		// addIndiv.enable(false);
		
		pause.setVisible (false);
		addIndiv.setVisible (false);
		tribesComboBox.setVisible (false);
	}
	
	/*
	 * @author belkacem
	 * adding the printing of the winning team
	 */
	public void showWinners (String type, int num) {
		game_over = new JLabel ("GAME OVER");
		winners = new JLabel ("Tribe n°" + num + " (" + type + ") WON!");
		winners.setVisible (true);
		game_over.setVisible (true);
		add (game_over);
		add (winners);
	}
}
