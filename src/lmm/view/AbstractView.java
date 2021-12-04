package lmm.view;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * Abstract class that implements the interface {@link lmm.view.IAbstractView} and the common methods of the view.
 * @author Filippo Nicolini
 *
 */
public abstract class AbstractView extends JFrame implements IAbstractView {

	private static final long serialVersionUID = 1;

	private final ImageIcon icon = new ImageIcon(this.getClass().getResource("/View_Icon.png"));
	private static final String OS_WITHOUT_ICON = "Mac";
	private static final String COMMAND_FOR_OS_NAME = "os.name";

	@Override
	public void setLocation() {
		this.setLocationRelativeTo(null);
	}

	@Override
	public void setIcon() {
		if (!System.getProperty(COMMAND_FOR_OS_NAME).startsWith(OS_WITHOUT_ICON)) {
			this.setIconImage(this.icon.getImage());
		}
	}

	@Override
	public abstract void actionPerformed(ActionEvent e);

	@Override
	public abstract void mouseReleased(MouseEvent e);

	@Override
	public void mouseClicked(final MouseEvent e) {
		//this method is unused in all views
	}

	@Override
	public void mousePressed(final MouseEvent e) {
		//this method is unused in all views
	}

	@Override
	public void mouseEntered(final MouseEvent e) {
		//this method is unused in all views
	}

	@Override
	public void mouseExited(final MouseEvent e) {
		//this method is unused in all views
	}

}
