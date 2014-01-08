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
import kernel.*;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

/*
 * Le Morvan Valentin - Info 01
 * Handler implementation for the XML file
 * The XML file can either describe just a map (loading map) or an entire game in pause (continue game)
 */

public class SmallWorldHandler extends DefaultHandler  {

	private SmallWorld smallworld;
	private Case ca;
	/*
	 * @author Belkacem Lahouel
	 * variable for the management of lifes during backup
	 */
	private boolean new_game;
	
	public SmallWorldHandler () {
		super();
		smallworld = new SmallWorld ();
	}

	/*
	 * Main parser function
	 * It reads the XML file and parameters the SmallWorld instance with it
	 */
	@Override
	public void startElement(String nameSpaceURI, String LocalName, String rawName, Attributes attributs) throws SAXException {
		
		if (rawName.equalsIgnoreCase ("tribe")) {
			/*
			 * @author Belkacem @date 02/01/14
			 * Implementation of the resources in common, for a tribe
			 * how to make the tribes being created first? I cannot add individuals if they are not created
			 */
			int x = 0, y = 0;
			String type = "";
			for (int i=0 ; i<attributs.getLength() ; ++i) {
				if		(attributs.getQName(i).equalsIgnoreCase ("x"))			x = Integer.parseInt(attributs.getValue(i));
				else if (attributs.getQName(i).equalsIgnoreCase ("y"))			y = Integer.parseInt(attributs.getValue(i));
				else if (attributs.getQName(i).equalsIgnoreCase ("type"))		type = attributs.getValue(i);
			}
			smallworld.addTribe (type, smallworld.getBoard().get(x, y).getPosition());
		} else if (rawName.equalsIgnoreCase ("case")) {
			int x = 1, y = 1;
			for (int index = 0; index<attributs.getLength(); ++index) {
				if		(attributs.getQName(index).equalsIgnoreCase ("x")) x = Integer.parseInt(attributs.getValue (index));
				else if (attributs.getQName(index).equalsIgnoreCase ("y")) y = Integer.parseInt(attributs.getValue (index));
				ca = smallworld.getBoard().get (x, y);
			}
		} else if (rawName.equalsIgnoreCase ("ressource")) { // TODO: divide this in all the resources (rock, food, metal, etc)
			String type = "";
			Resource r = null;
			int life = 0;
			
			for (int index=0 ; index<attributs.getLength() ; ++index){
				if		(attributs.getQName(index).equalsIgnoreCase ("type"))	type = attributs.getValue(index);
				else if (attributs.getQName(index).equalsIgnoreCase ("life")) life = Integer.parseInt(attributs.getValue(index));
			}
			
			if		(type.equalsIgnoreCase ("rock"))		r = new Rock		(ca.getPosition(), "");
			else if (type.equalsIgnoreCase ("metal"))		r = new Metal		(ca.getPosition(), "");
			else if (type.equalsIgnoreCase ("food"))		r = new Food		(ca.getPosition(), "");
			else if (type.equalsIgnoreCase ("plutonium"))	r = new Plutonium	(ca.getPosition(), "");
			else if (type.equalsIgnoreCase ("wood"))		r = new Wood		(ca.getPosition(), "");
			else System.err.println ("- Error, " + type + " as a Resource not found");
			
			if (r != null) {
				if (!new_game) r.setLife (life);
				smallworld.addResource (r);
				ca.add (r);
			}
		} else if (rawName.equalsIgnoreCase ("individual")) {
			/*
			 * @author Belkacem Lahouel
			 * adding the life backup when a game is continued
			 */
			int life = 100, tribe = 0;
			String type = "";
			Individual i = null;
			
			// récupération des valeurs des attributs:
			for (int index=0 ; index<attributs.getLength() ; ++index) {
				if		(attributs.getQName(index).equalsIgnoreCase ("type"))  type = attributs.getValue(index);
				else if (attributs.getQName(index).equalsIgnoreCase ("team")) tribe = Integer.parseInt(attributs.getValue(index)) -1;// The value of this variable represent the index in the list, 1 less than the number of the tribe.
				/*
				 * @author Belkacem Lahouel
				 * adding the implementation of the life when a backup is restored...
				 */
				else if (attributs.getQName(index).equalsIgnoreCase ("life")) life = Integer.parseInt(attributs.getValue(index));
			}
			
			// création de l'objet dans la team donnée:
			if		(type.equalsIgnoreCase ("human"))	i = new Human	(ca.getPosition(), "");
			else if (type.equalsIgnoreCase ("robot"))	i = new Robot	(ca.getPosition(), "");
			else if (type.equalsIgnoreCase ("bee"))		i = new Bee		(ca.getPosition(), "");
			
			/*
			 * @author Belkacem Lahouel
			 * Management of the life while backing up games
			 */
			if (i != null) {
				if (!new_game) i.setLife (life);
				if (smallworld.getTribeList() != null && smallworld.getTribeList().size() > tribe && type.equals (smallworld.getTribeAt(tribe).getIndividualType().toLowerCase())) {
					smallworld.addIndividual (i, tribe);
					i.setTribe(smallworld.getTribeAt(tribe));
				}
				else System.err.println ("- Error in the XML file, wrong type association between individual and tribe");
				ca.add (i);
			}
		}
		else if (rawName.equalsIgnoreCase ("board")) {
			int l = 1, w = 1;
			for (int index=0 ; index<attributs.getLength() ; ++index){
				if		(attributs.getQName(index).equalsIgnoreCase ("l")) l = Integer.parseInt(attributs.getValue (index));
				else if (attributs.getQName(index).equalsIgnoreCase ("w")) w = Integer.parseInt(attributs.getValue (index));
			}
			smallworld.setBoard (new Board(l,w));
		} 
	}
	
	/*
	 * Classic getters and setters
	 */
	public void reload ()		{new_game = false;}
	public void reset ()		{new_game = true;}
	public SmallWorld getSW ()	{return smallworld;}
	
	/*
	 * Unnecessary overrides for the parser
	 */
	@Override
	public void startPrefixMapping(String arg0, String arg1)		throws SAXException {}
	@Override
	public void characters(char[] arg0, int arg1, int arg2)			throws SAXException {}
	@Override
	public void endDocument()										throws SAXException {}
	@Override
	public void endElement(String arg0, String arg1, String arg2)	throws SAXException {}
	@Override
	public void endPrefixMapping(String arg0)						throws SAXException {}
	@Override
	public void ignorableWhitespace(char[] arg0, int arg1, int arg2)throws SAXException {}
	@Override
	public void processingInstruction(String arg0, String arg1)		throws SAXException {}
	@Override
	public void setDocumentLocator(Locator arg0) {}
	@Override
	public void skippedEntity(String arg0)							throws SAXException {}
	@Override
	public void startDocument()										throws SAXException {}

}
