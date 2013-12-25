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
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/* The MenuPanel class corresponds to the panel that is on the left during the game, giving several options to the player */

public class MenuPanel extends JPanel{
	private JButton pause,addIndiv;
	private JLabel mainTitle,barTitle;
	private JComboBox tribesComboBox;
	private JCheckBox viewResources;
	
	public MenuPanel(final SmallWorldGUI swGUI,final SmallWorld sw,final ResourcePanel resPan)
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
	    	tribesComboBox.addItem("Tribe "+(i+1));
	    }
	    
	    addIndiv = new JButton("Ajouter individu");
	    mainTitle = new JLabel ("Menu");
	    pause = new JButton("Play/Pause");
	    viewResources = new JCheckBox("Voir/Cacher ressources");
	    
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
	    		else if(sw.getTribeAt(tribesComboBox.getSelectedIndex()).getPopulation().get(0) instanceof Bee)
	    		{
	    			sw.addIndividual(new Bee(sw.getBoard().randPosition(),""),tribesComboBox.getSelectedIndex());
	    		}
	    	}
		});
	    
	    viewResources.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				resPan.setVisible(((JCheckBox)e.getSource()).isSelected());
			}
	    });
	    
	    this.add(mainTitle);
	    this.add(pause);
	    this.add(tribesComboBox);
	    this.add(addIndiv);
	    this.add(viewResources);
	}

	
	
}
