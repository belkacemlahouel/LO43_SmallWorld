package xml_parser;

import kernel.resources.Plutonium;
import kernel.resources.Rock;
import kernel.resources.Food;
import kernel.resources.Metal;
import kernel.resources.Resource;
import kernel.resources.Wood;
import kernel.individuals.Individual;
import kernel.individuals.Robot;
import kernel.individuals.Human;
import kernel.individuals.Bee;
import java.io.*;

import org.jdom2.*;
import org.jdom2.output.*;

import kernel.*;
/*
 * Le Morvan Valentin - Info 01
 * Saving the ongoing game into an XML
 * The created XML contain every information on the map (size, resources, individuals)
 */
public class JDOMSave {

	static org.jdom2.Element racine;
	static org.jdom2.Document document;
	
	public JDOMSave()
	{
		DocType dtd = new DocType("SmallWorld", "structure.dtd");
		racine = new org.jdom2.Element("SmallWorld");
		document = new Document(racine, dtd);
	}
	
	 public void SavetoXML(SmallWorld sw,String fileName){
		 Case[][] map = sw.getBoard().getBoard();
		 int length = map.length;
		 int width = map[0].length;
		 
		 /*Creation of the <board> tag with its attributes*/
		 org.jdom2.Element board = new org.jdom2.Element("board");
		 racine.addContent(board);
		 Attribute l = new Attribute("l", Integer.toString(length));
		 board.setAttribute(l);
		 Attribute w = new Attribute("w", Integer.toString(width));
		 board.setAttribute(w);
		 //creation of the different tags <tribe>
		 for (int i = 0; i < sw.getTribeList().size(); i++){
			 org.jdom2.Element tribe = new org.jdom2.Element("tribe");
			 Attribute x = new Attribute("x", Integer.toString(sw.getTribeAt(i).getBasePosition().getX()));
			 tribe.setAttribute(x);
			 Attribute y = new Attribute("y", Integer.toString(sw.getTribeAt(i).getBasePosition().getY()));
			 tribe.setAttribute(y);
			 Attribute type = new Attribute("type", sw.getTribeAt(i).getIndividualType());
			 tribe.setAttribute(type);
			 board.addContent(tribe);
		 }
		 
		 //Creation of the different tag <case>
		 for(int i = 0; i < length; i++){
			 for(int j = 0; j < width; j++){
				 if(!map[i][j].getElementsList().isEmpty()){
					 
					 //a <case> tag is created if it contains at least one element
					 org.jdom2.Element ca = new org.jdom2.Element("case");
					 Attribute x = new Attribute("x",Integer.toString(map[i][j].getPosition().getX()));
					 Attribute y = new Attribute("y",Integer.toString(map[i][j].getPosition().getY()));
					 board.addContent(ca);
					 ca.setAttribute(x);
					 ca.setAttribute(y);
					 
					 for( int k = 0; k < map[i][j].getElementsList().size(); k++){
						 
						 kernel.Element elem = map[i][j].getElementsList().get(k);
						 if(elem instanceof Resource){
							 //Case where the element is a resource:
							 org.jdom2.Element re = new org.jdom2.Element("ressource");
							 Attribute life = new Attribute("life", Integer.toString(elem.getLife()));
							 if(elem instanceof Food){//The "name" attribute has to be set 
								 Attribute name = new Attribute("type", "food");
								 re.setAttribute(name);
							 }
							 else if(elem instanceof Metal){
								 Attribute name = new Attribute("type", "metal");
								 re.setAttribute(name);
							 }
							 else if(elem instanceof Plutonium){
								 Attribute name = new Attribute("type", "plutonium");
								 re.setAttribute(name);
							 }
							 else if(elem instanceof Rock){
								 Attribute name = new Attribute("type", "rock");
								 re.setAttribute(name);
							 }
							 else if(elem instanceof Wood){
								 Attribute name = new Attribute("type", "wood");
								 re.setAttribute(name);
							 }
							 re.setAttribute(life);
							 ca.addContent(re);
						 }
						 //Case where the element is an individual:
						 else if (elem instanceof Individual){
							 org.jdom2.Element ind = new org.jdom2.Element("individual");
							 if(elem instanceof Human){//The "type" attribute has to be set 
								 Attribute type = new Attribute("type", "human");
								 ind.setAttribute(type);
							 }
							 else if(elem instanceof Robot){
								 Attribute type = new Attribute("type", "robot");
								 ind.setAttribute(type);
							 }
							 else if(elem instanceof Bee){
								 Attribute type = new Attribute("type", "bee");
								 ind.setAttribute(type);
							 }
							 
							 /*
							  * @author Belkacem Lahouel
							  * Implementation of life care on Individuals when continuing a game
							  */
							 Attribute life = new Attribute("life", Integer.toString(elem.getLife()));
							 ind.setAttribute (life);
							 
							 // To set the "team" attribute we have to look for the individual in each team.
							 for (int t = 0; t < sw.getTribeList().size(); t++){
								 if (sw.getTribeList().get(t).getPopulation().contains(elem)){
									 Attribute team = new Attribute("team",Integer.toString(t+1));
									 ind.setAttribute(team);
								 }
							 }
							 ca.addContent(ind);
						 }
					 }
				 }
			 }
		 }
		 XMLOutputter xmlOutput = new XMLOutputter();
		 xmlOutput.setFormat(Format.getPrettyFormat());
		 try {
			xmlOutput.output(document, new FileWriter("data//save//"+fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
}
