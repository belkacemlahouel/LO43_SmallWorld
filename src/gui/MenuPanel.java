package gui;

import kernel.individuals.Individual;
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
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/* The MenuPanel class corresponds to the panel that is on the left during the game, giving several options to the player */

public class MenuPanel extends JPanel {
	private JButton pause, addIndiv,save,exit;
	private JLabel mainTitle, barTitle, pauseLabel, game_over, winners;
	private JComboBox tribesComboBox, resourcesComboBox;
	private JCheckBox viewResources;
	private JFileChooser saveFileChooser;
	private JToggleButton addRes;

	/*
	 * @author Belkacem
	 * Boolean for the integration of the action on the play/pause button
	 */
	private boolean play = false;
	
	public MenuPanel(final SmallWorldGUI swGUI, final SmallWorld sw, final ResourcePanel resPan) {
		super();
		GridLayout menuGrid = new GridLayout(15,1);
	    menuGrid.setVgap(5);
	    this.setLayout(menuGrid);
	    this.setBackground(Color.WHITE);
	    this.setPreferredSize(new Dimension(170,720));

	    pauseLabel = new JLabel("EN PAUSE");
	    pauseLabel.setVisible(true);
	    
	    tribesComboBox = new JComboBox();
	    
	    for(int i=0 ; i<sw.getTribeList().size() ; ++i) {
			
			/*
			 * @author Belkacem @date 04/01/14
			 * French names in the buttons
			 */
			String fr_type = null;
			if		(sw.getTribeAt(i).getIndividualType().equalsIgnoreCase ("Human"))	fr_type = "humain";
			else if (sw.getTribeAt(i).getIndividualType().equalsIgnoreCase ("Bee"))		fr_type = "abeille mutante";
			else if (sw.getTribeAt(i).getIndividualType().equalsIgnoreCase ("Robot"))	fr_type = "robot";
			else System.err.println ("- Error type not found");
			
			tribesComboBox.addItem ("Tribu " + (i+1) + " " + fr_type);
	    }
	    
	    resourcesComboBox = new JComboBox();
	    resourcesComboBox.addItem("Pierre");
	    resourcesComboBox.addItem("Bois");
	    resourcesComboBox.addItem("Metal");
	    resourcesComboBox.addItem("Nourriture");
	    resourcesComboBox.addItem("Plutonium");
	    
	    addRes =		new JToggleButton("Ajouter ressource");
	    addIndiv =		new JButton("Ajouter individu");
	    mainTitle =		new JLabel ("Menu d'actions");
	    pause =			new JButton("Play/Pause");
	    viewResources = new JCheckBox("Voir/Cacher ressources");
	    save =			new JButton("Sauvegarder partie");
	    exit =			new JButton("Revenir au menu");
		
	    try {
			saveFileChooser = new JFileChooser(new File("data//save").getCanonicalFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
		/*
		 * @author Belkacem
		 * Implementation of the action after clicking on the play/pause button
		 */
		pause.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				play = !play;
				if(!play)
				{
					pauseLabel.setVisible(true);
				}
				else
				{
					pauseLabel.setVisible(false);
				}
			}
		});
	    
	    addIndiv.addActionListener(new ActionListener(){
	    	
	    	public void actionPerformed(ActionEvent arg0) {	
	    		
	    		/*
				 * @author Belkacem @date 03/01/14
				 * Testing individual types modification
				 * 
				 * @author Belkacem @date 04/01/14
				 * Modification of the way an Individual is added to its tribe
				 * We now use the index in the combo box, to add it to the (index)th tribe
				 */
				
				// System.out.println (tribesComboBox.getSelectedItem() + ", " + sw.getTribeAt(tribesComboBox.getSelectedIndex()).getIndividualType());
				
				if (sw.getTribeList().size() > tribesComboBox.getSelectedIndex()) {
					Individual i = sw.getTribeAt(tribesComboBox.getSelectedIndex()).addIndividual();
					swGUI.getMap().addIndividualGUI(new ElementGUI(i));
				} else System.err.println ("- Error, too big index, the #th tribes does not exist");
				
				/*Individual tmp = null;
				if (sw.getTribeAt(tribesComboBox.getSelectedIndex()).getIndividualType().contains("humain")) {
					tmp = new Human (sw.getTribeAt(tribesComboBox.getSelectedIndex()).getBasePosition(), "");
				} else if (sw.getTribeAt(tribesComboBox.getSelectedIndex()).getIndividualType().contains("abeille")) {
					tmp = new Bee (sw.getTribeAt(tribesComboBox.getSelectedIndex()).getBasePosition(), "");
				} else if (sw.getTribeAt(tribesComboBox.getSelectedIndex()).getIndividualType().contains("robot")) {
					tmp = new Robot (sw.getTribeAt(tribesComboBox.getSelectedIndex()).getBasePosition(), "");
				}
				
				if (tmp != null) sw.addIndividual (tmp, tribesComboBox.getSelectedIndex());
				else System.err.println ("- Error, Individual cannot be added");*/
				
				/*
				 * @author Belkacem Lahouel
				 * Printing each individual on the gui asap (when it it added to the game)
				 */
				swGUI.repaint();
	    	}
		});
	    
	    viewResources.addActionListener (new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resPan.setVisible(((JCheckBox)e.getSource()).isSelected());
			}
	    });
	    
	    save.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				play = false;
				String defaultName = new String("save.xml");
				saveFileChooser.setSelectedFile(new File(defaultName));
				saveFileChooser.showOpenDialog(null);
				JDOMSave jdomSave = new JDOMSave();
				jdomSave.SavetoXML(sw,saveFileChooser.getSelectedFile().getName());
			}
	    });
	    
	    exit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				play = false;
				swGUI.getMapBg().getMapTheme().stop();
				swGUI.abortGame();
			}
	    });
	    
	    this.add(mainTitle);
	    this.add(pause);
	    this.add(tribesComboBox);
	    this.add(addIndiv);
	    this.add(resourcesComboBox);
	    this.add(addRes);
	    this.add(viewResources);
	    this.add(save);
	    this.add(exit);
	    this.add(pauseLabel);
	}
	
	public JComboBox getResourcesComboBox() {
		return resourcesComboBox;
	}

	public void setResourcesComboBox(JComboBox resourcesComboBox) {
		this.resourcesComboBox = resourcesComboBox;
	}

	public JToggleButton getAddRes() {
		return addRes;
	}

	public void setAddRes(JToggleButton addRes) {
		this.addRes = addRes;
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
		addRes.setVisible(false);
		resourcesComboBox.setVisible(false);
	}
	
	/*
	 * @author Belkacem
	 * adding the printing of the winning team
	 */
	public void showWinners (String type, int num) {
		game_over = new JLabel ("\tGAME OVER");
		winners = new JLabel ("Tribu n°" + num + " (" + type + ") a gagné!");
		winners.setVisible (true);
		game_over.setVisible (true);
		add (game_over);
		add (winners);
	}
	
	public void paintComponent(Graphics g)
	{
		try {
	        Image img = ImageIO.read(new File("data//leftbarbackground.png"));
			g.drawImage(img, 0, 0, this);
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
	}
	
	public void showGameOver () {
		game_over = new JLabel ("\tGAME OVER");
		game_over.setVisible (true);
		add (game_over);
	}
}
