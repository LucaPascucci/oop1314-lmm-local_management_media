package lmm.controller.interfaces;

import javax.swing.ImageIcon;

/**
 * Interface that define the {@link lmm.controller.user.UserStatsController}.
 * @author Roberto Reibaldi
 *
 */
public interface IUserStatsController {
	/**
	 * Method that return the list of film owned by the user.
	 * @return String
	 */
	String setNumFilm();
	/**
	 * Method that set the cost of the selected film.
	 * @return String
	 */
	String setPurchasedFilm();
	/**
	 * This method return the details of the film selected by the variable idFilm.
	 * @param filmCode parameter that pass the code of the film.
	 */
	void showDetailsFilm(final Integer filmCode);
	/**
	 * Method that allows to the current user to edit the info of the User.
	 */
	void doEditUser();
	/**
	 * Method that allows to the current user to delete the film selected by the variable film insert.
	 * @param filmCode parameter that determinate which film the user want to delete.
	 * @return boolean
	 */
	boolean doDelete(final Integer filmCode);
	/**
	 * Method that check the existence of the ProfileImage.	
	 * @return boolean
	 */
	boolean checkProfileImage();
	/**
	 * Method that return the ImageIcon.
	 * @return ImageIcon
	 */
	ImageIcon getProfileImage();
	/**
	 * Method that allows to the current user to change the ProfileImage.
	 * @return boolean
	 */
	boolean changeProfileImage();
	/**
	 * Method that generate the list of film bought by the current user.
	 */
	void generateTable();
	/**
	 * Method that check if the current user is the admin or a normal user. 
	 * @return boolean
	 */
	boolean checkUser();

}
