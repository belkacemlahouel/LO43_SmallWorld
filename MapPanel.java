import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/* This class rules the display of the menu */

public class MapPanel extends JPanel {
	
	ArrayList<Resource> resList;
	ArrayList<Human> humList;
	
	/* The idea is to create the map Panel with a given XML or ElementList */

	public MapPanel(ArrayList<Resource> resList_,ArrayList<Human> humList_)
	{
		super();
		resList = resList_;
		humList = humList_;
		
	    this.setPreferredSize(new Dimension(1100,720));  
	    this.setVisible(true);
	}
	
	public void paintComponent(Graphics g){
	    int x;
	    int y;
	    int n;
	    
	    try {
	        Image img = ImageIO.read(new File("grass10px.png"));
	        for(y=0;y<72;y++)
		    {
		    	for(x=0;x<110;x++)
		    	{
			        g.drawImage(img, x*10, y*10, this);
		    	}
		    }
	        
	      } catch (IOException e) {
	        e.printStackTrace();
	      }    
	    
	    drawResList(g);
	    drawHumList(g);
	    
	 }
	
	public void drawResList(Graphics g)
	{		
		for(int n=0;n<resList.size();n++)
		{
			if(!resList.get(n).isDead())
        	g.drawImage(resList.get(n).getImgElement(),resList.get(n).getPosition().getX()*10,resList.get(n).getPosition().getY()*10, this);
        }
	}
	
	public void drawHumList(Graphics g)
	{		
		for(int n=0;n<humList.size();n++)
		{
			if(!humList.get(n).isDead())
        	g.drawImage(humList.get(n).getImgElement(),humList.get(n).getPosition().getX()*10,humList.get(n).getPosition().getY()*10, this);
        }
	}
	
	
}
