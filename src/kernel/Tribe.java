package kernel;

import java.util.ArrayList;

public class Tribe {
	
	private ArrayList<Individual> population;
	private ArrayList<Resource> resources;
	private Position base;
	
	public Tribe () {
		population = new ArrayList<Individual> (0);
		resources = new ArrayList<Resource> (0);
	}
	
	/*
	 * getters and setters for Tribe
	 */
	public void						addResource (Resource r)		{resources.add(r);}
	public void						addIndividual (Individual i)	{population.add (i);}
	public ArrayList<Individual>	getPopulation ()				{return population;}
	public ArrayList<Resource>		getResources ()					{return resources;}
	public Position					getBase ()						{return base;}
}
