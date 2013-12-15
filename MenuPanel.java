import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class MenuPanel extends JPanel {
	JButton Start,Pause;
	
	public MenuPanel(final SmallWorld sw)
	{
		super();
		GridLayout menuGrid = new GridLayout(15,1);
	    menuGrid.setVgap(5);
	    this.setLayout(menuGrid);
	    this.setBackground(Color.WHITE);
	    this.setPreferredSize(new Dimension(176,720));
	    JLabel lab1 = new JLabel ("Menu");
	    JButton butt1 = new JButton("Pause");
	    JButton butt2 = new JButton("Play");
	    JButton butt3 = new JButton("Ajouter humain");
	    
	    butt3.addActionListener(new ActionListener(){
	    	
	    	public void actionPerformed(ActionEvent arg0){
	    		
	    		sw.addHuman();
	    
	    	}
		});
	    this.add(lab1);
	    this.add(butt1);
	    this.add(butt2);
	    this.add(butt3);
	    
	}
	
}
