package lmm.controller.interfaces;

import javax.swing.JFrame;

/**
 * Interface that define the {@link lmm.controller.MainController}.
 * @author Roberto Reibaldi, Luca Pascucci
 *
 */
public interface IMainController {

	/**
	 * This method check the existence of a file.
	 * @param path path of the file that will be controlled.
	 * @return boolean
	 */
	boolean fileExist(final String path);

	/**
	 * This method check the existence of the folder of the resources.
	 * @param path path of the folder that will be controlled.
	 * @return boolean
	 */
	boolean resourcesFolderExist(final String path);

	/**
	 * This method launch the function of save.
	 */
	void saveDataCmd();

	/**
	 * This method launch the function of load.
	 */
	void loadDataCmd();

	/**
	 * This method launch the exception that could occur in the save function.
	 * @param exc parameter that define the exception.
	 */
	void saveError(Exception exc);

	/**
	 * This method have the function to change the current view when requested.
	 * @param fromView this parameter define the current view.
	 * @param toView this parameter define the view that we want to go.
	 * @param item this parameter pass the items of the view.
	 */
	void changeView(final Integer fromView, final Integer toView,  final Object item);

	/**
	 * This method make visible the view.
	 * @param f parameter that pass the correct frame.
	 */
	void setView(final JFrame f);

	/**
	 * This method show an error message.
	 * @param message parameter that define the text of the message.
	 */
	void showErrorDialog(final String message);

	/**
	 * This method show a warning message.
	 * @param message parameter that define the text of the message.
	 */
	void showWarningDialog(final String message);

	/**
	 * This method show an info message.
	 * @param message parameter that define the text of the message.
	 */
	void showInfoDialog(final String message);

	/**
	 * This method show a question message.
	 * @param message parameter that define the text of the message.
	 * @return Integer
	 */
	Integer showQuestionDialog(final String message);

	/**
	 * This method launch the function of the menu bottom save.
	 */
	void doSaveMenu();

	/**
	 * This method launch the function of the menu bottom load.
	 */
	void doLoadMenu();

	/**
	 * This method show the {@link lmm.view.CreditsView}.
	 */
	void showCreditsView();

	/**
	 * This method show the {@link lmm.view.FeedbackView}.
	 */
	void showFeedbackView();

	/**
	 * This method allow the current user to exit the application.
	 */
	void doExit();

	/**
	 * This method allow the current user to return to the {@link lmm.view.LoginView}.
	 */
	void backToHome();

	/**
	 * This method allow the current User to return to the previous view.
	 */
	void doBack();


}
