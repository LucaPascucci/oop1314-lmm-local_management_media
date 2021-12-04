package lmm.controller.interfaces;

/**
 * Interface that define the {@link lmm.controller.user.FilmListController}.
 * @author Roberto Reibaldi
 *
 */
public interface IFilmListController {
	/**
	 * This method return the details of the film insert by the variable codFilm.
	 * @param filmCode parameter that pass the code of the film.
	 */
	void showDetailsFilm(final Integer filmCode);
	/**
	 * This method changes the list of films with a new list ordered by the variable TOP_VISITED_FILM and filtered by the variable filter.
	 * @param filter this parameter pass all the filter chosen.
	 */
	void generateTableTopVisited(final Object[] filter);
	/**
	 * This method generate a film list ordered by the year of release of the films and filtered by the variable filter.
	 * @param filter this parameter pass all the filter chosen.
	 */
	void generateTableRecent(final Object[] filter);
	/**
	 * This method generate a film list filtered by the variable filter.
	 * @param filter this parameter pass all the filter chosen.
	 */
	void generateTableFilmList(final Object[] filter);
	/**
	 * This method changes the list of films with a new list ordered by the variable TOP_BUYED_FILM and filtered by the variable filter.
	 * @param filter this parameter pass all the filter chosen.
	 */ 
	void generateTableTopBought(final Object[] filter);

}
