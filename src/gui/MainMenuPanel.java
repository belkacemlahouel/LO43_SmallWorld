package gui;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import xml_parser.SmallWorldParser;
import kernel.SmallWorld;

public class MainMenuPanel extends JPanel {
	JButton start,edit,load;
	JFileChooser loadFileChooser;
	Clip titleTheme;

	public MainMenuPanel(final SmallWorldGUI swGUI,final SmallWorldParser SWP)
	{
		super();
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc1 = new GridBagConstraints();
		
		start = new JButton("Demarrage rapide");
		edit = new JButton("Editer une map");
		load = new JButton("Charger fichier");
		try {
			loadFileChooser = new JFileChooser(new File("data//save").getCanonicalFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		start.addActionListener(new ActionListener(){
	    	
	    	public void actionPerformed(ActionEvent arg0){	
	    		titleTheme.stop();
	    		SmallWorld sw = SWP.createSW("data//save//default.xml");
	    		sw.setGui(swGUI);
	    		swGUI.setSw(sw);
	    		swGUI.startGame();
	    	}
		});
		
		try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("data//title2.wav").getAbsoluteFile());
	        titleTheme = AudioSystem.getClip();
	        titleTheme.open(audioInputStream);
	        titleTheme.start();
	    } catch(Exception ex) {
	        System.out.println("Error while playing sound.");
	        ex.printStackTrace();
	    }
		
		load.addActionListener(new ActionListener(){
	    	
	    	public void actionPerformed(ActionEvent arg0){	
	    		loadFileChooser.showOpenDialog(null);
	    		if(loadFileChooser.getSelectedFile() != null)
	    		{
	    			titleTheme.stop();
		    		SmallWorld sw = SWP.createSW(loadFileChooser.getSelectedFile().getPath());
		    		sw.setGui(swGUI);
		    		swGUI.setSw(sw);
		    		swGUI.startGame();
	    		}
	    	}
		});
		

		
		gbc1.fill = GridBagConstraints.HORIZONTAL;
		gbc1.gridx = 1;
		gbc1.ipadx = 100;
		gbc1.ipady = 20;
		gbc1.insets = new Insets(10,10,10,10);
		gbc1.gridy = 1;
		this.add(start,gbc1);
		gbc1.fill = GridBagConstraints.VERTICAL;
		gbc1.gridx = 1;
		gbc1.gridy = 2;
		this.add(edit,gbc1);
		gbc1.gridx = 1;
		gbc1.gridy = 3;
		this.add(load,gbc1);
	}
	
	public void paintComponent(Graphics g)
	{
		try {
	        Image img = ImageIO.read(new File("data//background2.png"));

		    g.drawImage(img,0,0, this);

	      } catch (IOException e) {
	        e.printStackTrace();
	      }
		
	}
	
}
