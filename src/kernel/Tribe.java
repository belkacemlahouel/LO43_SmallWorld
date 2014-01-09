package kernel;

import kernel.individuals.Individual;
import kernel.individuals.Robot;
import kernel.individuals.Human;
import kernel.individuals.Bee;
import java.util.ArrayList;
import java.util.HashMap;
import kernel.individuals.FighterBee;
import kernel.individuals.FighterHuman;
import kernel.individuals.FighterRobot;
import kernel.individuals.PickerBee;
import kernel.individuals.PickerHuman;
import kernel.individuals.PickerRobot;
import kernel.individuals.SuperBee;
import kernel.individuals.SuperHuman;
import kernel.individuals.SuperRobot;

/***************************************************************
 *  @author Belkacem Lahouel - UTBM - A2013
 *  Project: LO43, Small World
 ***************************************************************
 *  This class manages the tribes
 *  The biggest method allows the user to add an individual (no parameters) to this tribe
 *  The individual is created here
 *  This method should be used more often (the others are deprecated)
 *  The individual added is with the same type as the other individuals
 *  The method to add an individual (in parameter) should be used for e.g.
 *		adding an Individual to a tribe which is not its type
 */

public class Tribe {
	
	private ArrayList<Individual> population;
	private Position base_position;
	/*
	 * @author Belkacem @date @02/01/14
	 * The resources are modelised by an HashMap, refering a quantity per resource
	 * Individual type is stocked here, to avoid problems, if there are no instances of any Individual yet
	 */
	private HashMap<String, Integer> resources;
	private String individual_type;
	
	/*
	 * By default, there is all the set of possible ressources
	 * The individuals may or may not pick them up, though
	 */
	public Tribe (String type, Position base) {
		
		individual_type = type;
		base_position = base;
		
		population = new ArrayList<Individual> (0);
		resources = new HashMap<String, Integer> (0);
		
		resources.put ("Wood", 0);
		resources.put ("Rock", 0);
		resources.put ("Food", 0);
		resources.put ("Plutonium", 0);
		resources.put ("Metal", 0);
		
		if (individual_type == null) System.err.println ("- Error, Individual Type in Tribe null");
	}
	
	/*
	 * @author Belkacem @date 02/01/14
	 * Implementation of the HashMap of resources, more efficient
	 * Hence, two getters/setters are changed
	 * 
	 * for the addResource method, we add a quantity to the specified resource
	 * 
	 * Implementation of the creation of a new Individual
	 * Only when the resources are enough to fill one Individual's life completely
	 * 
	 * Two types of resources: wood/rock, for everybody, to upgrade civilizations
	 *							and the resource to heal yourself (food, plutonium, ...)
	 * The second type of resources change following the type of the Individual
	 * 
	 * @author Belkacem @date 07/01/14
	 * Implementation of pickers/fighters/supers/normal with probabilities: 0.2, 0.2, 0.1, 0.5
	 */
	public Individual addIndividual () {
		Individual tmp = null;
		int capa_proba = Tools.rand(10, 1);
		
		/*
		 * In the order of probabilities, for more performance
		*/
		if ((capa_proba >= 6)) {																						// normal: 0.5
			if		(individual_type.equalsIgnoreCase ("Human"))	tmp = new Human (base_position, "");
			else if (individual_type.equalsIgnoreCase ("Bee"))		tmp = new Bee (base_position, "");
			else if (individual_type.equalsIgnoreCase ("Robot"))	tmp = new Robot (base_position, "");
			else System.err.println ("- Error, type not found");
		} else if ((capa_proba == 4 || capa_proba == 5)) {																// fighter: 0.2
			if		(individual_type.equalsIgnoreCase ("Human"))	tmp = new FighterHuman (base_position, "");
			else if (individual_type.equalsIgnoreCase ("Bee"))		tmp = new FighterBee (base_position, "");
			else if (individual_type.equalsIgnoreCase ("Robot"))	tmp = new FighterRobot (base_position, "");
			else System.err.println ("- Error, type not found");
		} else if ((capa_proba == 3 || capa_proba == 2)) {																// picker: 0.2
			if		(individual_type.equalsIgnoreCase ("Human"))	tmp = new PickerHuman (base_position, "");
			else if (individual_type.equalsIgnoreCase ("Bee"))		tmp = new PickerBee (base_position, "");
			else if (individual_type.equalsIgnoreCase ("Robot"))	tmp = new PickerRobot (base_position, "");
			else System.err.println ("- Error, type not found");
		} else {																										// super: 0.1
			if		(individual_type.equalsIgnoreCase ("Human"))	tmp = new SuperHuman (base_position, "");
			else if (individual_type.equalsIgnoreCase ("Bee"))		tmp = new SuperBee (base_position, "");
			else if (individual_type.equalsIgnoreCase ("Robot"))	tmp = new SuperRobot (base_position, "");
			else System.err.println ("- Error, type not found");
		}
		
		if (tmp != null) {population.add (tmp); tmp.setTribe(this);}
		else System.err.println ("- Problem, individual type not found");
		
		return tmp;
	}
	
	/*
	 * if a qty is added, normally this means that there is at least one person left...
	 */
	public void	addResource (String type, int qty) {
		resources.put(type, resources.get(type) + qty);
		if (!population.isEmpty() && type.equals(population.get(0).getVitalResource()) && resources.get(type) > population.get(0).getMaxLife()) {
			addIndividual ();
			resources.put (type, resources.get(type) - population.get(0).getMaxLife());
		}
	}
	
	/*
	 * @author Belkacem @date 03/01/14
	 * Classic toString override
	 */
	public String toString () {
		String tmp = "";
		tmp += "Tribe: base: " + base_position + ", " + population.size() + " " + individual_type + "\n\tlifes:";
		for (Individual e : population) {
			tmp += " " + e.getLife() + ",";
		}
		tmp += "\n\tResources:";
		for (String e : resources.keySet()) {
			tmp += "\n\t\t" + e + ": " + resources.get(e);
		}
		return tmp + "\n";
	}
	
	/*
	 * classic getters and setters for tribes
	 * less classic add methods
	 */
	public ArrayList<Individual>	getPopulation ()							{return population;}
	public HashMap<String, Integer>	getResources ()								{return resources;}
	public Position					getBasePosition ()							{return base_position;}
	public String					getIndividualType ()						{return individual_type;}
	
	public void						setPopulation (ArrayList<Individual> tmp)	{population = tmp;}
	
	public void						addIndividual (Individual i)				{population.add (i);}
}
