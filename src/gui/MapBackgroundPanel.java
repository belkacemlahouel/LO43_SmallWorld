package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;

public class MapBackgroundPanel extends JPanel {
	
	private Clip mapTheme;
	
	public Clip getMapTheme() {
		return mapTheme;
	}


	public MapBackgroundPanel(){
		super();
		this.setLayout(new FlowLayout());
	    this.setPreferredSize(new Dimension(1100,720));
	    
	    try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("data//title.wav").getAbsoluteFile());
	        mapTheme = AudioSystem.getClip();
	        mapTheme.open(audioInputStream);
	    } catch(Exception ex) {
	        System.out.println("Error while playing sound.");
	        ex.printStackTrace();
	    }
		
	}
	
	public void paintComponent(Graphics g)
	{
		try {
	        Image img = ImageIO.read(new File("data//mapbackground.png"));
			g.drawImage(img, 0, 0, this);
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
	}

}
