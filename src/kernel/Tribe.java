package kernel;

import java.util.ArrayList;
import java.util.HashMap;

public class Tribe {
	
	private ArrayList<Individual> population;
	private Position base_position;
	
	private HashMap<String, Integer> resources;
	private String individual_type;
	/*
	 * @author Belkacem @date @02/01/14
	 * The resources are modelised by an HashMap, refering a quantity per resource
	 * Individual type is stocked here, to avoid problems, if there are no instances of any Individual yet
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
		
		/*for (String key : resources.keySet()) {
			System.out.println("\t\t" + key);
		}*/
		
		if (individual_type == null) System.err.println ("- Error, Individual Type in Tribe null");
		
		/*if (individual_type != null) {
			switch (individual_type) {
				case "Human":	resources.put ("Food", 0);		break;
				case "Bee":		resources.put ("Plutonium", 0); break;
				case "Robot":	resources.put ("Metal", 0);		break;
				default:		System.err.println ("- Error, Individual type in Tribe not recognized");
			}
		}
		else System.err.println ("- Error, Individual Type in Tribe null");*/
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
	 */
	public Individual addIndividual () {
		Individual tmp = null;
		
		if		(individual_type.equalsIgnoreCase ("Human"))	tmp = new Human (base_position, "");
		else if (individual_type.equalsIgnoreCase ("Bee"))		tmp = new Bee (base_position, "");
		else if (individual_type.equalsIgnoreCase ("Robot"))	tmp = new Robot (base_position, "");
		else System.err.println ("- Error, type not found");
		
		if (tmp != null) {population.add (tmp); tmp.setTribe(this);}
		else System.err.println ("- Problem, individual type not found");
		
		return tmp;
	}
	
	/*
	 * if a qty is added, normally this means that there is at least one person left
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
	 */
	public void						addIndividual (Individual i)				{population.add (i);}
	public ArrayList<Individual>	getPopulation ()							{return population;}
	public HashMap<String, Integer>	getResources ()								{return resources;}
	public Position					getBasePosition ()							{return base_position;}
	public String					getIndividualType ()						{return individual_type;}
	public void						setPopulation (ArrayList<Individual> tmp)	{population = tmp;}
}
