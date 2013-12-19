package xml_parser;


import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import kernel.SmallWorld;

import org.xml.sax.SAXException;


public class SmallWorldParser {
	
	public SmallWorldParser(){
		
	}
	
	public SmallWorld createSW(String path){
		SAXParserFactory fabrique = SAXParserFactory.newInstance();
		javax.xml.parsers.SAXParser parseur = null;
		
		try {
			parseur = fabrique.newSAXParser();
		} catch (/*ParserConfigurationException |*/ SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		File fichier = new File(path);
		
		SmallWorldHandler gestionnaire = new SmallWorldHandler();
		try {
			parseur.parse(fichier, gestionnaire);
		} catch (/*SAXException |*/ IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return gestionnaire.getSW();
	}

}
