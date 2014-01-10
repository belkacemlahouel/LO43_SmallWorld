package gui;

import kernel.*;
import xml_parser.*;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JFrame;

public class SmallWorldGUI extends JFrame {
	
	private MainMenuPanel mainMenu;
	private MenuPanel leftBar;
	private MapPanel map;
	private MapBackgroundPanel mapBg;
	private EditorPanel editPan;
	private ResourcePanel resPan;

	private SmallWorld sw;
	
	public SmallWorldGUI (SmallWorldParser SWP) {
		super ();
		setTitle("SmallWorld");
	    setSize(1280, 720);
	    setLocationRelativeTo(null);
	    setResizable(false);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	    getContentPane().setLayout(new BorderLayout());
	    
	    editPan = new EditorPanel(this);
	    mainMenu = new MainMenuPanel(this,SWP);
	    mainMenu.setVisible(true);
	    getContentPane().add(mainMenu);
	    
	    setVisible(true);

	    getContentPane().validate();
	}
	
	/* This method changes the main menu into the game view, then starts the game automatically */
	
	public void setSw(SmallWorld sw) {
		this.sw = sw;
	}

	public ResourcePanel getResPan() {
		return resPan;
	}

	public void setResPan(ResourcePanel resPan) {
		this.resPan = resPan;
	}

	public void abortGame() {
		sw = null;
		
		resPan.setVisible(false);
		leftBar.setVisible(false);
		mapBg.setVisible(false);
		map.setVisible(false);
		mainMenu.setVisible(true);
		getContentPane().add(mainMenu);
		editPan.setVisible(false);
		remove(editPan);
		mainMenu.repaint();
		getContentPane().validate();
		mainMenu.getTitleTheme().start();
	}
	
	public void startGame() {
		mainMenu.getTitleTheme().stop();
		mainMenu.setVisible(false);
		editPan.setVisible(false);
		
		map = new MapPanel(sw.getBoard(),sw.getTribeList(),sw.getResources(),this);
	    resPan = new ResourcePanel(this);
	    leftBar = new MenuPanel(this,this.sw,resPan); 
		
	    setGlassPane(resPan); // We add the resource panel over all the others
	    getGlassPane().setVisible(false);
	    mapBg = new MapBackgroundPanel();
	    getContentPane().add(leftBar);
	    getContentPane().add(mapBg,BorderLayout.EAST);
		mapBg.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx=0;
		gbc.gridy=0;
	    mapBg.add(map,gbc);
	    getContentPane().validate();
	    mapBg.getMapTheme().start();
	    
	    sw.start();
	}
	
	public void editMap() {
		mainMenu.setVisible(false);
		remove(mainMenu);
		getContentPane().add(editPan);
		editPan.setVisible(true);
		getContentPane().validate();
		editPan.repaint();
	}
	
	
	/* This method updates the map, with all the new positions and so on*/
	
	public MenuPanel getLeftBar() {
		return leftBar;
	}

	public void setLeftBar(MenuPanel leftBar) {
		this.leftBar = leftBar;
	}

	public MapPanel getMap() {
		return map;
	}

	public void updateMapPanel()
	{
		map.repaint();
		resPan.repaint();
	}
	
	public SmallWorld getSw() {
		return sw;
	}
	
	/*
	 * @author Belkacem
	 * Action after clicking on the play/pause button
	 */
	public boolean getPlay () {
		return leftBar.getPlay();
	}

	/*
	 * @author Belkacem
	 * disabling Buttons to stop possible clicks
	 */
	public void disableButtons () {
		leftBar.disableButtons ();
	}
	
	/*
	 * @author Belkacem
	 * adding the printing of the winning team
	 */
	public void showWinners (String type, int num) {
		leftBar.showWinners(type, num);
	}
	public void showGameOver () {
		leftBar.showGameOver();
	}
	
	public MapBackgroundPanel getMapBg() {
		return mapBg;
	}

	public void setMapBg(MapBackgroundPanel mapBg) {
		this.mapBg = mapBg;
	}	
}
