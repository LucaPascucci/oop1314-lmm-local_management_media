package lmm.main;

import java.util.logging.Logger;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import lmm.controller.LoginController;
import lmm.model.IModel;
import lmm.model.Model;
import lmm.view.LoginView;

/**
 * <h1>Local Management Media</h1>
 * Local Management Media [LMM] is an application developed
 * for the course of Object Oriented Programming of Computer 
 * Science and Engineering that models a management of a video library.
 * 
 * @author Luca Pascucci, Filippo Nicolini, Marco Sperandeo, Roberto Reibaldi
 * @version 1.0
 * @since   2014-05-21 
 *
 */
public final class Main {

	private static final String GRAPHITE_LOOK_AND_FELL = "com.jtattoo.plaf.graphite.GraphiteLookAndFeel";

	/**
	 * Constructor should never be called.
	 */
	private Main() { }

	/**
	 * @param args The command line parameters.
	 */
	public static void main(final String[] args) {

		setGUI();

		final IModel model = new Model();
		final LoginController controller = new LoginController(model);
		final LoginView view = new LoginView();
		controller.createWorkspace();
		controller.loadDataCmd();
		controller.setView(view);
		view.setVisible(true);
		
	}

	/**
	 * This method modify the look of the swing setting UIManager with a theme present in the JTattoo library
	 */
	private static void setGUI() {
		try {
			UIManager.setLookAndFeel(GRAPHITE_LOOK_AND_FELL);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException exc) {
			Logger.getLogger("Unable to load the GUI");
		}
	}

}
