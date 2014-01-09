package gui;

import kernel.resources.Resource;
import kernel.individuals.Individual;
import kernel.*;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/* An Element GUI is the graphical version of the elements */

public class ElementGUI {
	private Image imgElementUp,imgElementDown,imgElementLeft,imgElementRight,imgElementDead;
	private Element elem;
	private Position prec_position; // The precedent position in the movement 
	private boolean endMove;
	private boolean isDead;
	private int deadCount;
	
	
	public ElementGUI(Individual i)
	{
		/*
		 * Implementation of the images for Super individuals
		 */
		String capa = "";
		// if (i instanceof SuperBee || i instanceof SuperHuman || i instanceof SuperRobot) {
		if (i.isSuper()) {
			capa = "super";
		}
		
		try{
			imgElementDown = ImageIO.read(new File("data\\" + capa + i.getTypeName () + "down.png")); // The file of the element is referred as it's name + the .png file extension
		  } catch (IOException e) {
			e.printStackTrace();
		}
		try{
			imgElementUp = ImageIO.read(new File("data\\" + capa + i.getTypeName () + "up.png")); // The file of the element is referred as it's name + the .png file extension
		  } catch (IOException e) {
			e.printStackTrace();
		}
		try{
			imgElementRight = ImageIO.read(new File("data\\" + capa + i.getTypeName () + "Right.png")); // The file of the element is referred as it's name + the .png file extension
		  } catch (IOException e) {
			e.printStackTrace();
		}
		try{
			imgElementLeft = ImageIO.read(new File("data\\" + capa + i.getTypeName () + "Left.png")); // The file of the element is referred as it's name + the .png file extension
		  } catch (IOException e) {
			e.printStackTrace();
		}
		try{
			imgElementDead = ImageIO.read(new File("data\\"+ capa + i.getTypeName() + "dead.png")); // The file of the element is referred as it's name + the .png file extension
		  } catch (IOException e) {
			e.printStackTrace();
		}
		deadCount = 0;

		elem = i;
		prec_position = i.getPosition();
	}
	
	public ElementGUI(Resource r)
	{
		try{
			imgElementUp = ImageIO.read(new File("data//" + r.getTypeName() + ".png")); // For a resource, we simply use the image "up" because there is only one
	      } catch (IOException e) {
	        e.printStackTrace();
	    } 
		
		elem = r;
	}
	

	public Element getElem() {
		return elem;
	}

	public void setElem(Element elem) {
		this.elem = elem;
	}

	public Position getPos() {
		return elem.getPosition();
	}

	/*public void setPosition(Position pos) {
		elem.setPosition(pos);
	}*/
	
	public void setPosition (int x, int y) {
		elem.setPosition(x, y);
	}
	
	public Image getImgElementUp() {
		return imgElementUp;
	}

	public Image getImgElementDown() {
		return imgElementDown;
	}

	public Image getImgElementLeft() {
		return imgElementLeft;
	}

	public Image getImgElementRight() {
		return imgElementRight;
	}

	public Image getImgElementDead() {
		return imgElementDead;
	}

	public boolean getIsDead()
	{
		return isDead;
	}
	
	public void setDead()
	{
		isDead = true;
	}

	public Position getPrec_position() {
		return prec_position;
	}

	public void setPrec_position(Position prec_position) {
		this.prec_position = prec_position;
	}

	/* This method returns the right image to put : left, down, up, right, dead */
	
	public Image getCorrespondingImg() {
		if(isDead)
		{
			return imgElementDead;
		}
		else if(prec_position.getX() < elem.getPosition().getX())
		{
			return imgElementRight;
		}
		else if(prec_position.getX() > elem.getPosition().getX())
		{
			return imgElementLeft;
		}
		else if(prec_position.getY() < elem.getPosition().getY())
		{
			return imgElementDown;
		}
		else if(prec_position.getY() > elem.getPosition().getY())
		{
			return imgElementUp;
		}
		else
		{
			return imgElementUp;
		}
	}

	public void incrementDeadCount() {
		++deadCount;
	}

	public int getDeadCount() {
		return deadCount;
	}
	
}
