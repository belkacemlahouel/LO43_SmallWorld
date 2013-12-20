package gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener implements KeyListener {
	private SmallWorldGUI swGUI;
	
	
	public KeyboardListener(SmallWorldGUI swGUI_)
	{
		this.swGUI = swGUI_;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		swGUI.getGlassPane().setVisible(true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		swGUI.getGlassPane().setVisible(true);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		swGUI.getGlassPane().setVisible(true);		
	}
	
	

}
