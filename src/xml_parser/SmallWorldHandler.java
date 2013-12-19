package xml_parser;

import kernel.*;
import gui.*;

/*
 * LE MORVAN VALENTIN - INFO 01
 * */

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;


public class SmallWorldHandler extends DefaultHandler  {

	private SmallWorld smallworld = new SmallWorld();
	private Case ca;
	
	public SmallWorldHandler (){
		super();
	}
	
	public SmallWorld getSW() {
		return smallworld;
	}
	@Override
	public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endElement(String arg0, String arg1, String arg2)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endPrefixMapping(String arg0) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ignorableWhitespace(char[] arg0, int arg1, int arg2)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processingInstruction(String arg0, String arg1)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDocumentLocator(Locator arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void skippedEntity(String arg0) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startElement(String nameSpaceURI, String LocalName, String rawName, 
			Attributes attributs) throws SAXException {
		
			if (rawName.equals("board"))
			{ 
				int l =0 , w=0;
				for (int index = 0; index < attributs.getLength(); index++){
					if (attributs.getQName(index).equals("l")){
						l = Integer.parseInt(attributs.getValue(index));
					}
					else if (attributs.getQName(index).equals("w")){
						w = Integer.parseInt(attributs.getValue(index));
					}
				}
				Board b = new Board(l,w);
				smallworld.setBoard(b);
				smallworld.setGui(new SmallWorldGUI(smallworld));
			}
			else if (rawName.equals("case"))
			{
				int x = 1, y = 1;
				for (int index = 0; index < attributs.getLength(); index++){
					if (attributs.getQName(index).equals("x")){
						x = Integer.parseInt(attributs.getValue(index));
					}
					else if (attributs.getQName(index).equals("y")){
						y = Integer.parseInt(attributs.getValue(index));
					}
				ca = smallworld.getBoard().get(x,y);
				}
			}
			else if (rawName.equals("ressource")){
				String name ="";
				int  l = 0;
				for (int index = 0; index < attributs.getLength(); index++){
					if (attributs.getQName(index).equals("name")){
						name = attributs.getValue(index);
					}
					
					else if (attributs.getQName(index).equals("life")) {
						l = Integer.parseInt(attributs.getValue(index));
					}
				}
				Resource r = new Food(ca.getPosition(), name);
				r.setLife(l);
				ca.add(r);
				smallworld.addres(r);
			}
			else if (rawName.equals("human")){
				String name ="";
				int  l = 0, team = 0;
				for (int index = 0; index < attributs.getLength(); index++){
					if (attributs.getQName(index).equals("name")){
						name = attributs.getValue(index);
					}
					
					else if (attributs.getQName(index).equals("life")) {
						l = Integer.parseInt(attributs.getValue(index));
					}
					else if (attributs.getQName(index).equals("team")) {
						team = Integer.parseInt(attributs.getValue(index));
					}
				}
				Human h = new Human(ca.getPosition(), name);
				smallworld.addIndividual(h,team);
				h.setLife(l);
				ca.add(h);
			}
			else if (rawName.equals("robot")){
				String name ="";
				int  l = 0, team = 0;
				for (int index = 0; index < attributs.getLength(); index++){
					if (attributs.getQName(index).equals("name")){
						name = attributs.getValue(index);
					}
					
					else if (attributs.getQName(index).equals("life")) {
						l = Integer.parseInt(attributs.getValue(index));
					}
					else if (attributs.getQName(index).equals("team")) {
						team = Integer.parseInt(attributs.getValue(index));
					}
				}
				Robot r = new Robot(ca.getPosition(),"Trevor");
				smallworld.addIndividual(r,team);
				r.setLife(l);
				ca.add(r);
			}
		}
		


	@Override
	public void startPrefixMapping(String arg0, String arg1)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

}
