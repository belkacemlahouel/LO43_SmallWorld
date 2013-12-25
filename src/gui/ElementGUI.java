package gui;

import kernel.*;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/* An Element GUI is the graphical version of the elements */

public class ElementGUI {
	private Image imgElement;
	private Element elem;
	private boolean endMove;
	private boolean isDead;
	
	
	public ElementGUI(Individual i)
	{
		try{
			imgElement = ImageIO.read(new File("data//" + i.getTypeName () + ".png")); // The file of the element is referred as it's name + the .png file extension
	      } catch (IOException e) {
	        e.printStackTrace();
	    }
				
		elem = i;
	}
	
	public ElementGUI(Resource r)
	{
		try{
			imgElement = ImageIO.read(new File("data//" + r.getTypeName() + ".png")); // The file of the element is referred as it's name + the .png file extension
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

	public Image getImgElement() {
		return imgElement;
	}

	public void setImgElement(Image imgElement) {
		this.imgElement = imgElement;
	}
	
	public boolean getIsDead()
	{
		return isDead;
	}
	
	public void setDead()
	{
		isDead = true;
		if(this.elem instanceof Individual)
		{
			try{
				imgElement = ImageIO.read(new File("data//"+((Individual)elem).getTypeName() + "dead.png")); // The file of the element is referred as it's name + the .png file extension
		      } catch (IOException e) {
		        e.printStackTrace();
		    }
		}

	}
}
