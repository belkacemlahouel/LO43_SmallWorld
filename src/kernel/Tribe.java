package kernel;

import java.util.ArrayList;

public class Tribe {
	
	private ArrayList<Individual> population;
	private ArrayList<Resource> resources;
	
	// TODO Should we create the lists here?
	public Tribe () {
		population = new ArrayList<Individual> ();
		resources = new ArrayList<Resource> ();
	}
	
	public void addResource (Resource r) {
		resources.add(r);
	}
	
	public void addIndividual (Individual i) {
		population.add (i);
	}
	
	public ArrayList<Individual> getPopulation () {
		return population;
	}
	
	public ArrayList<Resource> getResources () {
		return resources;
	}
}
