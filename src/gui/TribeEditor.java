package gui;

import javax.swing.JComboBox;
import javax.swing.JLabel;

public class TribeEditor {
	private JLabel title;
	private JComboBox raceChoice;
	
	public TribeEditor () {
		title = new JLabel("Tribu");
		raceChoice = new JComboBox();
		raceChoice.addItem("Humain");
		raceChoice.addItem("Robot");
		raceChoice.addItem("Abeille Mutante");
	}

	public JLabel getTitle() {
		return title;
	}

	public JComboBox getRaceChoice() {
		return raceChoice;
	}
	
	public String getSelectedType () {
		// return (String) raceChoice.getSelectedItem();
		/*
		 * @author Belkacem @date 03/01/14
		 * This type does not match types in kernel
		 */
		if		(raceChoice.getSelectedItem().equals("Humain"))				return "human";
		else if (raceChoice.getSelectedItem().equals("Abeille Mutante"))	return "bee";
		else if (raceChoice.getSelectedItem().equals("Robot"))				return "robot";
		else {
			System.err.println ("- Error: Wrong type selected");
			return null;
		}
	}
}
