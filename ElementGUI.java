import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/* An Element GUI is the graphical version of the elements */


public class ElementGUI {
	private Image imgElement;
	private Element elem;
	boolean endMove;
	
	public ElementGUI(Human h)
	{
		try{
			imgElement = ImageIO.read(new File(h.name + ".png")); // The file of the element is referred as it's name + the .png file extension
	      } catch (IOException e) {
	        e.printStackTrace();
	    }
		
		elem = h;
	}
	
	public ElementGUI(Individual i)
	{
		try{
			imgElement = ImageIO.read(new File(i.name + ".png")); // The file of the element is referred as it's name + the .png file extension
	      } catch (IOException e) {
	        e.printStackTrace();
	    }
		
		elem = i;
	}
	
	public ElementGUI(Resource r)
	{
		try{
			imgElement = ImageIO.read(new File(r.name + ".png")); // The file of the element is referred as it's name + the .png file extension
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
		return elem.pos;
	}

	public void setPosition(Position pos) {
		elem.pos = pos;
	}
	
	public void setPosition (int x, int y) {
		elem.pos.setX(x);
		elem.pos.setY(y);
	}

	public Image getImgElement() {
		return imgElement;
	}

	public void setImgElement(Image imgElement) {
		this.imgElement = imgElement;
	}
}
