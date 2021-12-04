package lmm.view;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

/**
 * Interface that extends interface of the listener for the views.
 * @author Filippo Nicolini
 *
 */
public interface IAbstractView extends ActionListener, MouseListener {
	/**
	 * Set the icon of the program.
	 */
	void setIcon();
	/**
	 * Set the position of the frame into the screen.
	 */
	void setLocation();
	
}
