package gui;

import kernel.*;
import xml_parser.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/* The MenuPanel class corresponds to the panel that is on the left during the game, giving several options to the player */

public class MenuPanel extends JPanel{
	private JButton pause, addIndiv,save,exit;
	private JLabel mainTitle, barTitle, game_over, winners;
	private JComboBox tribesComboBox,resourcesComboBox;
	private JCheckBox viewResources;
	private JFileChooser saveFileChooser;
	private JToggleButton addRes;

	/*
	 * @author Belkacem
	 * Boolean for the integration of the action on the play/pause button
	 */
	private boolean play = false;
	
	public MenuPanel(final SmallWorldGUI swGUI,final SmallWorld sw,final ResourcePanel resPan) {
		super();
		GridLayout menuGrid = new GridLayout(15,1);
	    menuGrid.setVgap(5);
	    this.setLayout(menuGrid);
	    this.setBackground(Color.WHITE);
	    this.setPreferredSize(new Dimension(170,720));

	    tribesComboBox = new JComboBox();
	    
	    for(int i=0 ; i<sw.getTribeList().size() ; ++i) {
	    	// tribesComboBox.addItem("Tribe " + (i+1) + " : " + sw.getTribeAt(i).getPopulation().get(0).getTypeName());
			tribesComboBox.addItem ("Tribe " + (i+1) + " : " + sw.getTribeAt(i).getIndividualType());
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
				/*
				 * @author Belkacem
				 * If we go to pause, we print the SmallWorld status
				 */
				if (!play) System.out.print("" + sw);
			}
		});
	    
	    addIndiv.addActionListener(new ActionListener(){
	    	
	    	public void actionPerformed(ActionEvent arg0) {	
	    		
	    		/*
				 * @author Belkacem @date 03/01/14
				 * Testing individual types modification
				 */
				Individual tmp = null;
				if (sw.getTribeAt(tribesComboBox.getSelectedIndex()).getIndividualType().equals("Human")) {
					tmp = new Human (sw.getTribeAt(tribesComboBox.getSelectedIndex()).getBasePosition(), "");
				} else if (sw.getTribeAt(tribesComboBox.getSelectedIndex()).getIndividualType().equals("Bee")) {
					tmp = new Bee (sw.getTribeAt(tribesComboBox.getSelectedIndex()).getBasePosition(), "");
				} else if (sw.getTribeAt(tribesComboBox.getSelectedIndex()).getIndividualType().equals("Robot")) {
					tmp = new Robot (sw.getTribeAt(tribesComboBox.getSelectedIndex()).getBasePosition(), "");
				}
				sw.addIndividual (tmp, tribesComboBox.getSelectedIndex());
				
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
		game_over = new JLabel ("GAME OVER");
		winners = new JLabel ("Tribe n°" + num + " (" + type + ") WON!");
		winners.setVisible (true);
		game_over.setVisible (true);
		add (game_over);
		add (winners);
	}
}
