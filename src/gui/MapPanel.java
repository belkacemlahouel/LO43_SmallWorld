package gui;

import kernel.resources.Plutonium;
import kernel.resources.Rock;
import kernel.resources.Food;
import kernel.resources.Metal;
import kernel.resources.Resource;
import kernel.resources.Wood;
import kernel.individuals.Individual;
import kernel.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/* 
 * Author of the whole class : Luc CADORET
 * This class rules the display of the map which show graphically to the player all the elements that are interacting 
 */

public class MapPanel extends JPanel implements MouseListener {
	
	private ArrayList<ElementGUI> resList;
	private ArrayList<ElementGUI> indivList;
	private SmallWorldGUI swGUI;
	private final int xAxisLength;
	private final int yAxisLength;

	private static final int  caseSize = 10; // A case has 10px length and width
	
	/* The idea is to create the map Panel with a given XML or ElementList */

	public MapPanel(Board b,ArrayList<Tribe> swTribeList,ArrayList<Resource> swResList,SmallWorldGUI swGUI_)
	{
		super();
		swGUI = swGUI_; // We need the reference to the MenuPanel, to add the resources on the map, and the board, to add it on them
		resList = new ArrayList<ElementGUI>();
		indivList = new ArrayList<ElementGUI>();
		xAxisLength = b.getBoard().length;
		yAxisLength = b.getBoard()[0].length;
	    this.setPreferredSize(new Dimension(xAxisLength*caseSize,yAxisLength*caseSize));
	    addMouseListener(this);
	    
	    /* We create the ElementGUI depending on the Tribe List and the Resource List of the SmallWorld */
	    
	    for(int i=0;i<swTribeList.size();i++)
	    {
	    	for(int j=0;j<swTribeList.get(i).getPopulation().size();j++)
	    	{
	    		indivList.add(new ElementGUI(swTribeList.get(i).getPopulation().get(j)));
	    	}
	    }
	    
	    for(Resource e:swResList)
	    {
	    	resList.add(new ElementGUI(e));
	    }
	    
	    this.setVisible(true);
	}
	
	/*
	 * Author : Luc CADORET
	 * This function is called at every turn, see if some ElemenGUI are dead since the last turn, and calls all function that
	 * display what's happening on the screen
	 */
	
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
	    try {
	    	for (int i = 0; i < swGUI.getSw().getTribeList().size(); i++ ){
	    		x = swGUI.getSw().getTribeAt(i).getBasePosition().getX();
	    		y = swGUI.getSw().getTribeAt(i).getBasePosition().getY();
	    		
	    		Image base = ImageIO.read(new File("data//" + swGUI.getSw().getTribeAt(i).getIndividualType() + "base.png"));
	    		g.drawImage(base, x*caseSize, y*caseSize, this);
	    	}
	    }catch (IOException e) {
	        e.printStackTrace();
	      }
	    
	    changeElementGUIToDead();
	    drawResList(g);
	    drawIndivList(g);
	    deadCounter();
	    
	 }

	/*
	 * Author : Luc CADORET
	 * This function draws the resources on the map, if they are still alive (their life is > to 0) 
	 */
	
	public void drawResList(Graphics g)
	{		
		for(int n=0;n<resList.size();n++)
		{
			if(!resList.get(n).getElem().isDead())
        	g.drawImage(resList.get(n).getImgElementUp(),resList.get(n).getPos().getX()*10,resList.get(n).getPos().getY()*10, this);
		}
	}
	
	/*
	 * Author : Luc CADORET
	 * This function draw the ElementGUI corresponding to the individuals. The ElementGUI searches the right picture to show on the screen
	 * depending on the direction he's moving, and the fact that he's dead or alive
	 */
	
	public void drawIndivList(Graphics g)
	{		
		for(int n=0;n<this.indivList.size();n++)
		{
			if(this.indivList.get(n).getDeadCount()<30)
			{
				g.drawImage(indivList.get(n).getCorrespondingImg(),indivList.get(n).getPos().getX()*10,indivList.get(n).getPos().getY()*10,this);
		
			}
		}
		
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
	
	/* This function sets the ElementGUI to dead (see the corresponding boolean) if the individual that they are refering to is actually
	 * dead (live <= 0)
	 */
	
	public void changeElementGUIToDead() {
		for(int i=0;i<indivList.size();i++)
		{
			if(indivList.get(i).getElem().isDead())
			{
				if(!indivList.get(i).getIsDead())
				{
					indivList.get(i).setDead();
				}
			}
		}
	}
	
	public void addIndividualGUI(ElementGUI e) {
		indivList.add(e);
	}
	
	/* This function returns the ElementGUI that corresponds to an individual */
	
	public synchronized ElementGUI getCorrespondingElementGUI (Individual i) {
		for (ElementGUI e : indivList) {
			if(e.getElem() == i) return e;
		}
		
		return null;
	}

	/* This function is the one that is adding the resource into the game when the player clicks on the map */
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	/* This function looks at the coordinates (in pixel) of the mouse on the map, and returns the case in the board where the resource
	 * has to be
	 */
	
	public Case computePosition(int x,int y)
	{
		int goodX, goodY;
		goodX = x/caseSize;
		goodY = y/caseSize;
		
		return swGUI.getSw().getBoard().get(goodX, goodY);
	}
	
	/* The following functions are useless, but here, because we implement the MouseListener class */
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		Case c = computePosition(e.getX(),e.getY());
		
		if(swGUI.getLeftBar().getAddRes().isSelected())
		{
			/*0 : rock, 1 : wood, 2 : metal, 3 : food, 4 : plutonium*/
			switch(swGUI.getLeftBar().getResourcesComboBox().getSelectedIndex()) {
				case 0:
					Rock r = new Rock(c.getPosition(),"");
					c.add(r);
					resList.add(new ElementGUI(r));
					break;
				case 1:
					Wood w = new Wood(c.getPosition(),"");
					c.add(w);
					resList.add(new ElementGUI(w));
					break;
				case 2:
					Metal m = new Metal(c.getPosition(),"");
					c.add(m);
					resList.add(new ElementGUI(m));
					break;
				case 3:
					Food f = new Food(c.getPosition(),"");
					c.add(f);
					resList.add(new ElementGUI(f));
					break;
				case 4:
					Plutonium p = new Plutonium(c.getPosition(),"");
					c.add(p);
					resList.add(new ElementGUI(p));
					break;
			}
			
			repaint ();
		}
	}
	
	/* This method increments the "deadcount" of every individual every turn. Once at 30, we stop printing this individual */
	
	public synchronized void deadCounter(){
			for(ElementGUI e : indivList){
			if(e.getIsDead() == true) e.incrementDeadCount();
		}
	}
	
	/* This method removes the dead individuals of the indivList */
	
}
