package gui;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import kernel.Bee;
import kernel.Board;
import kernel.Food;
import kernel.Human;
import kernel.Metal;
import kernel.Plutonium;
import kernel.Position;
import kernel.Robot;
import kernel.Rock;
import kernel.SmallWorld;
import kernel.Wood;
import xml_parser.SmallWorldParser;

public class EditorPanel extends JPanel{
	private JLabel title1,mapSizeText,resSizeText,textNbTribes;
	private JComboBox mapSize,resSize,nbTribes;
	private JButton addTribe, exit,start,add,validate;
	private ArrayList<TribeEditor> tribeEditorList;
	private GridBagConstraints gbc1;
	
	
	public EditorPanel(final SmallWorldGUI swGUI)
	{
		super();
		
		tribeEditorList = new ArrayList<TribeEditor>();
		add = new JButton("Valider");
		title1 = new JLabel("Tribus");
		mapSizeText = new JLabel("Taille de la map : ");
		resSizeText = new JLabel("Abondance des ressources : ");
		textNbTribes = new JLabel("Nombre de tribus : ");
		validate = new JButton("C'est parti !");
		
		mapSize = new JComboBox();
		mapSize.addItem("Petite");
		mapSize.addItem("Moyenne");
		mapSize.addItem("Grande");
		
		resSize = new JComboBox();
		resSize.addItem("Faible");
		resSize.addItem("Moyenne");
		resSize.addItem("Forte");
		
		nbTribes = new JComboBox();
		nbTribes.addItem("1");
		nbTribes.addItem("2");
		nbTribes.addItem("3");
		nbTribes.addItem("4");
		nbTribes.addItem("5");
		nbTribes.addItem("6");
		
		this.setLayout(new GridBagLayout());
		gbc1 = new GridBagConstraints();
		gbc1.fill = GridBagConstraints.HORIZONTAL;
		gbc1.insets = new Insets(10,10,10,10);
		gbc1.ipadx = 100;
		gbc1.ipady = 20;
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		this.add(mapSizeText,gbc1);
		gbc1.gridx = 1;
		gbc1.gridy = 1;
		this.add(mapSize,gbc1);
		gbc1.gridx = 0;
		gbc1.gridy = 2;
		this.add(resSizeText,gbc1);
		gbc1.gridx = 1;
		gbc1.gridy = 2;
		this.add(resSize,gbc1);
		gbc1.gridx = 0;
		gbc1.gridy = 3;
		this.add(textNbTribes,gbc1);
		gbc1.gridx = 1;
		gbc1.gridy = 3;
		this.add(nbTribes,gbc1);
		gbc1.gridx = 2;
		gbc1.gridy = 3;
		this.add(add,gbc1);
		gbc1.gridx = 0;
		gbc1.gridy = 4;
		
		add.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent arg0){
	    		updateTribeEditorList(nbTribes.getSelectedIndex()+1);
				
				/*
				 * @author Belkacem @date 02/01/14
				 * The user can't change its map configuration
				 */
				add.setEnabled(false);
				mapSize.setEnabled(false);
				resSize.setEnabled(false);
				nbTribes.setEnabled(false);
				validate.setVisible(true);
	    	}
		});
		
		validate.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent arg0){
	    		SmallWorld sw = new SmallWorld(nbTribes.getSelectedIndex()+1);
	    		sw.setGui(swGUI);
	    		swGUI.setSw(sw);
	    		generateBoard(sw);
	    		generateTribes(sw);
	    		generateResources(sw);
	    		swGUI.startGame();
				
				/*
				 * @author Belkacem @date 02/01/14
				 * Re-enabling the buttons so that the user can change its options if he comes back to this menu
				 * Putting back the default values
				 * Hiding again these buttons
				 */
				add.setEnabled(true);
				mapSize.setEnabled(true);
				resSize.setEnabled(true);
				nbTribes.setEnabled(true);
				mapSize.setSelectedIndex(0);
				nbTribes.setSelectedIndex(0);
				resSize.setSelectedIndex(0);
				validate.setVisible(false);
				updateTribeEditorList(0);
	    	}
		});
	}
	
	public void paintComponent(Graphics g)
	{
		try {
	        Image img = ImageIO.read(new File("data//background1.png"));

		    g.drawImage(img,0,0, this);

	      } catch (IOException e) {
	        e.printStackTrace();
	      }
		
	}
	
	private void updateTribeEditorList(int choosedNbTribes) {
		
		gbc1.gridy = 4;
		
		for(int i=0;i<tribeEditorList.size();i++)
		{
			this.remove(tribeEditorList.get(i).getTitle());
			this.remove(tribeEditorList.get(i).getRaceChoice());
		}
		
		this.remove(validate);
		
		this.repaint();
		
		tribeEditorList.removeAll(tribeEditorList);
		
		for(int i=0;i<choosedNbTribes;i++)
		{
			gbc1.gridx = 0;
			this.tribeEditorList.add(new TribeEditor());
			tribeEditorList.get(i).getTitle().setText("Tribe " + (i+1) + " :");
			this.add(tribeEditorList.get(i).getTitle(),gbc1);
			gbc1.gridx ++;
			this.add(tribeEditorList.get(i).getRaceChoice(),gbc1);
			gbc1.gridy++;
		}
		
		this.add(validate,gbc1);
		
		this.revalidate();
	}
	
	public void generateBoard(SmallWorld sw)
	{
		/* 0 = small board, 1 = mean board, 2 = big board */
		if(mapSize.getSelectedIndex() == 0)
		{
			Board b = new Board(50,30);
			sw.setBoard(b);
		}
		else if(mapSize.getSelectedIndex() == 1)
		{
			Board b = new Board(70,50);
			sw.setBoard(b);
		}
		else if(mapSize.getSelectedIndex() == 2)
		{
			Board b = new Board(110,72);
			sw.setBoard(b);
		}
	}
	
	/* This method creates 5 individuals in every choosen tribe, at totally random positions */
	
	public void generateTribes(SmallWorld sw)
	{
		
		for(int i=0; i<tribeEditorList.size();i++)
		{
			/* 0 = Human, 1 = Robot, 2 = Bee*/
			if(tribeEditorList.get(i).getRaceChoice().getSelectedIndex() == 0)
			{
				for(int j=0;j<5;j++)
				{
					Position randPosition = sw.getBoard().randPosition();
					Human h = new Human(randPosition,"");
					sw.getTribe_list().get(i).addIndividual(h);
					sw.getBoard().get(randPosition).add(h);
				}
			}
			else if(tribeEditorList.get(i).getRaceChoice().getSelectedIndex() == 1)
			{
				for(int j=0;j<5;j++)
				{
					Position randPosition = sw.getBoard().randPosition();
					Robot h = new Robot(randPosition,"");
					sw.getTribe_list().get(i).addIndividual(h);
					sw.getBoard().get(randPosition).add(h);
				}
			}
			else if(tribeEditorList.get(i).getRaceChoice().getSelectedIndex() == 2)
			{
				for(int j=0;j<5;j++)
				{
					Position randPosition = sw.getBoard().randPosition();
					Bee h = new Bee(randPosition,"");
					sw.getTribe_list().get(i).addIndividual(h);	
					sw.getBoard().get(randPosition).add(h);
				}
			}
		}
	}
	
	public void generateResources(SmallWorld sw)
	{
		int nbEach = 0;
		
		/* 0 = a few, 1 = a bit more, 2 = a lot */
		if(resSize.getSelectedIndex() == 0)
		{
			nbEach = 3;
		}
		else if(resSize.getSelectedIndex() == 1)
		{
			nbEach = 7;
		}
		else if(resSize.getSelectedIndex() == 2)
		{
			nbEach = 13;
		}
		
		for(int i=0;i<nbEach;i++)
		{
			Position randPosition = sw.getBoard().randPosition();
			Wood r = new Wood(randPosition,"");
			sw.addResource(r);	
			sw.getBoard().get(randPosition).add(r);
		}
		
		for(int i=0;i<nbEach;i++)
		{
			Position randPosition = sw.getBoard().randPosition();
			Plutonium r = new Plutonium(randPosition,"");
			sw.addResource(r);	
			sw.getBoard().get(randPosition).add(r);
		}
		
		for(int i=0;i<nbEach;i++)
		{
			Position randPosition = sw.getBoard().randPosition();
			Rock r = new Rock(randPosition,"");
			sw.addResource(r);	
			sw.getBoard().get(randPosition).add(r);
		}
		
		for(int i=0;i<nbEach;i++)
		{
			Position randPosition = sw.getBoard().randPosition();
			Metal r = new Metal(randPosition,"");
			sw.addResource(r);	
			sw.getBoard().get(randPosition).add(r);
		}
		
		for(int i=0;i<nbEach;i++)
		{
			Position randPosition = sw.getBoard().randPosition();
			Food r = new Food(randPosition,"");
			sw.addResource(r);	
			sw.getBoard().get(randPosition).add(r);
		}
	}
	
	
	
}
