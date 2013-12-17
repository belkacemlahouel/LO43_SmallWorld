package kernel;

import kernel.*;
import xml_parser.*;

import java.util.ArrayList;

public class Tribe {
	private ArrayList<Individual> population;
	private ArrayList<Resource> resources;
	private int tribeIndex;
	
	public int getTribeIndex() {
		return tribeIndex;
	}

	public void setTribeIndex(int tribeIndex) {
		this.tribeIndex = tribeIndex;
	}

	public Tribe(){
		population = new ArrayList<Individual>();
		resources = new ArrayList<Resource>();
	}
	
	public ArrayList<Individual> getPopulation() {
		return population;
	}
	public void setPopulation(ArrayList<Individual> population) {
		this.population = population;
	}
	public ArrayList<Resource> getResources() {
		return resources;
	}
	public void setResources(ArrayList<Resource> resources) {
		this.resources = resources;
	}
	
	
}
