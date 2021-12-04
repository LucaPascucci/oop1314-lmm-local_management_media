package lmm.controller.interfaces;

/**
 * Interface that define the {@link lmm.controller.admin.AdminStatsController}.  
 * @author Roberto Reibaldi
 *
 */
public interface IAdminStatsController {

	/**
	 * This method manages the creations of the list of the film contained in the application.
	 */
	void generateTableFilmList();
	/**
	 * This method changes the list of films with a new list ordered by the variable TOP_VISITED_FILM.
	 */
	void generateTableTopVisited();
	/**
	 * This method changes the list of films with a new list ordered by the variable TOP_BOUGHT_FILM.
	 */
	void generateTableTopBought();
	/**
	 * This method return the details of the film insert by the variable filmCode.
	 * @param filmCode this parameter pass the film.
	 */
	void showDetailsFilm(final Integer filmCode);
	/**
	 * This method allows to change the password.
	 */
	void doChangePassword();
	/**
	 * Return the total number of Films contained in the application.
	 * @return String
	 */
	String getTotalFilms();
	/**
	 * Return the total number of User contained in the application. 
	 * @return String
	 */
	String getTotalUsers();
	/**
	 * Return the total income up to now.
	 * @return String
	 */
	String getTotalIncomes();
	/**
	 * This method generate a film list ordered by the year of release of the films.
	 */
	void generateTableRecent();
	/**
	 * This method allows to load the files of the application.
	 */
	void doLoading();
	/**
	 * This method allows to save the changes.	
	 */
	void doSaving();
}
