package gui;

import kernel.*;
import xml_parser.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPanel;

public class SmallWorldGUI extends JFrame{
	
	private MainMenuPanel mainMenu;
	private MenuPanel leftBar;
	private MapPanel map;
	private MapBackgroundPanel mapBg;
	private ResourcePanel resPan;


	private SmallWorld sw;
	
	public SmallWorldGUI(SmallWorldParser SWP)
	{
		this.setTitle("SmallWorld");
	    this.setSize(1280, 720);
	    this.setLocationRelativeTo(null);
	    this.setResizable(false);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	    getContentPane().setLayout(new BorderLayout());
	    
	    mainMenu = new MainMenuPanel(this,SWP);
	    getContentPane().add(mainMenu);
	    
	 
	    this.setVisible(true);

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

	public void startGame()
	{
		mainMenu.setVisible(false);
		
		map = new MapPanel(sw.getBoard(),sw.getTribe_list(),sw.getResources());
		
	    resPan = new ResourcePanel(this);
	    leftBar = new MenuPanel(this,this.sw,resPan); 
		
	    setGlassPane(resPan); // We add the resource panel over all the others
	    this.getGlassPane().setVisible(false);

		this.remove(mainMenu);
	    mapBg = new MapBackgroundPanel();
	    getContentPane().add(leftBar);
	    getContentPane().add(mapBg,BorderLayout.EAST);
	    mapBg.add(map);
	    getContentPane().validate();
	    
	    sw.start();
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
	
}
