package gui;

import kernel.*;
import xml_parser.*;
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
	
	private ArrayList<ElementGUI> resList;
	private ArrayList<ElementGUI> indivList;
	private final int xAxisLength;
	private final int yAxisLength;

	private static final int  caseSize = 10; // A case has 10px length and width
	
	/* The idea is to create the map Panel with a given XML or ElementList */

	public MapPanel(Board b)
	{
		super();
		resList = new ArrayList<ElementGUI>();
		indivList = new ArrayList<ElementGUI>();
		xAxisLength = b.getBoard().length;
		yAxisLength = b.getBoard()[0].length;
	    this.setPreferredSize(new Dimension(xAxisLength*caseSize,yAxisLength*caseSize));  
	    this.setVisible(true);
	}
	
	public void paintComponent(Graphics g){
	    int x;
	    int y;
	    int n;
	    
	    try {
	        Image img = ImageIO.read(new File("data//grass10px.png"));
	        for(y=0;y<yAxisLength;y++)
		    {
		    	for(x=0;x<xAxisLength;x++)
		    	{
			        g.drawImage(img, x*caseSize, y*caseSize, this);
		    	}
		    }
	        
	      } catch (IOException e) {
	        e.printStackTrace();
	      }    
	    
	    drawResList(g);
	    drawIndivList(g);
	    
	 }

	public void drawResList(Graphics g)
	{		
		for(int n=0;n<resList.size();n++)
		{
        	g.drawImage(resList.get(n).getImgElement(),resList.get(n).getPos().getX()*10,resList.get(n).getPos().getY()*10, this);
		}
	}
	
	public void drawIndivList(Graphics g)
	{		
		for(int n=0;n<this.indivList.size();n++)
		{
			g.drawImage(indivList.get(n).getImgElement(),indivList.get(n).getPos().getX()*10,indivList.get(n).getPos().getY()*10, this);
		}
		
	}
	
	/* This function computes the difference on the X axis beetwen this and another position p */
	
	public int diffX(Position p1,Position p2)
	{
		return p1.getX() - p2.getX();
	}
	
	/* This function computes the difference on the Y axis beetwen this and another position p */

	public int diffY(Position p1,Position p2)
	{
		return p1.getY() - p2.getY();
	}

	public ArrayList<ElementGUI> getIndivList() {
		return indivList;
	}

	public void setIndivList(ArrayList<ElementGUI> indivList) {
		this.indivList = indivList;
	}
	
	public ArrayList<ElementGUI> getResList() {
		return resList;
	}

	public void setResList(ArrayList<ElementGUI> resList) {
		this.resList = resList;
	}
	
}
