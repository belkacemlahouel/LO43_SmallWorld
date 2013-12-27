package gui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class TribeEditor {
	private JLabel title;
	private JComboBox raceChoice;
	
	public TribeEditor()
	{
		title = new JLabel("Tribe");
		raceChoice = new JComboBox();
		raceChoice.addItem("Humain");
		raceChoice.addItem("Robot");
		raceChoice.addItem("Abeille Mutante");
	}

	public JLabel getTitle() {
		return title;
	}

	public void setTitle(JLabel title) {
		this.title = title;
	}

	public JComboBox getRaceChoice() {
		return raceChoice;
	}

	public void setRaceChoice(JComboBox raceChoice) {
		this.raceChoice = raceChoice;
	}
}
