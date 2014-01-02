package xml_parser;

import kernel.SmallWorld;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;


public class SmallWorldParser {
	
	/*
	 * @author Belkacem
	 * correction of the bug with lives, when it comes to continue a game (backed up)
	 */
	private boolean new_game = true;
	private SmallWorldHandler gestionnaire;
	
	public SmallWorldParser() {
		
	}
	
	public SmallWorld createSW(String path){
		SAXParserFactory fabrique = SAXParserFactory.newInstance();
		javax.xml.parsers.SAXParser parseur = null;
		
		try {
			parseur = fabrique.newSAXParser();
		} catch (SAXException e1) {
			e1.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		File fichier = new File(path);
		
		gestionnaire = new SmallWorldHandler ();
		try {
			parseur.parse (fichier, gestionnaire);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		
		return gestionnaire.getSW();
	}
	
	
	/*
	 * @author Belkacem
	 * says to the Parser that this is not a new game
	 * Then the lifes indicated inside the XML are taken into account
	 */
	public void reload () {new_game = false; gestionnaire.reload();}
	public void reset () {new_game = true; gestionnaire.reset();}
}
