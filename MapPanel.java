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
	private ArrayList<ElementGUI> humList;
	public ArrayList<ElementGUI> getResList() {
		return resList;
	}

	public void setResList(ArrayList<ElementGUI> resList) {
		this.resList = resList;
	}

	public ArrayList<ElementGUI> getHumList() {
		return humList;
	}

	public void setHumList(ArrayList<ElementGUI> humList) {
		this.humList = humList;
	}

	private static final int  caseSize = 10; // A case has 10px length and width
	
	/* The idea is to create the map Panel with a given XML or ElementList */

	public MapPanel(ArrayList<Resource> resList_,ArrayList<Human> humList_)
	{
		super();
		resList = new ArrayList<ElementGUI>();
		humList = new ArrayList<ElementGUI>();
		createElementGUIResList(resList_);
		createElementGUIHumList(humList_);
		
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
			        g.drawImage(img, x*caseSize, y*caseSize, this);
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
        	g.drawImage(resList.get(n).getImgElement(),resList.get(n).getPos().getX()*10,resList.get(n).getPos().getY()*10, this);
		}
	}
	
	public void drawHumList(Graphics g)
	{		
		//boolean finishedDraw = false;
		
		for(int n=0;n<this.humList.size();n++)
		{
			g.drawImage(humList.get(n).getImgElement(),humList.get(n).getPos().getX()*10,humList.get(n).getPos().getY()*10, this);
		}
		
		/*while(!finishedDraw)
		{
			
			finishedDraw = true;
			for(int n=0;n<this.humList.size();n++)
			{
				if(this.humList.get(n).getOldPos() != this.humList.get(n).getPos())
				{
					finishedDraw = false;
					humList.get(n).setOldPos(this.diffX(humList.get(n).getOldPos(),humList.get(n).getPos())/this.diffX(humList.get(n).getOldPos(),humList.get(n).getPos()),this.diffY(humList.get(n).getOldPos(),humList.get(n).getPos())/this.diffY(humList.get(n).getOldPos(),humList.get(n).getPos()));
					g.drawImage(humList.get(n).getImgElement(),humList.get(n).getOldPos().getX()*10,humList.get(n).getOldPos().getY()*10, this);
				}
			} 
		}*/
		
	}
	
	/* 
	 * This method creates the graphical version of the Resource list
	 */
	
	public void createElementGUIResList(ArrayList<Resource> resList_)
	{
		for(int i=0;i<resList_.size();i++)
		{
			this.resList.add(new ElementGUI(resList_.get(i)));
		}
	}
	
	/* 
	 * This method creates the graphical version of the Human list
	 */
	
	public void createElementGUIHumList(ArrayList<Human> humList_)
	{
		for(int i=0;i<humList_.size();i++)
		{
			this.humList.add(new ElementGUI(humList_.get(i)));
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
	
	
}
