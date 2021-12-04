package lmm.controller.interfaces;
/**
 * Interface that define {@link lmm.controller.user.FavoriteFilmController}.
 * @author Roberto Reibaldi
 *
 */
public interface IFavoriteFilmController {
	/**
	 * Method that create the table of the favorite films of the current user.
	 */
	void generateTable();
	/**
	 * Method that return the view of the info of the selected film.
	 * @param filmCode parameter that pass the code of the film.
	 */
	void showDetailsFilm(final Integer filmCode);
	/**
	 * Return a boolean that say if the selected film is not anymore one of the favorite films of the current User.
	 * @param filmCode parameter that pass the code of the film.
	 * @return boolean
	 */
	boolean deleteFavorite(final Integer filmCode);
	/**
	 * Method that confirm that the current user bought the selected film. 
	 * @param filmCode parameter that pass the code of the film.
	 */
	void doBuy(final Integer filmCode);
	/**
	 * Method that show a preview of the info of the selected film.
	 * @param filmCode parameter that pass the code of the film.
	 */
	void showPreview(final Integer filmCode);

}
