package lmm.controller.interfaces;

/**
 * Interface that define the {@link lmm.controller.admin.ManageFilmController}.
 * @author Roberto Reibaldi
 *
 */
public interface IManageFilmController {
	/**
	 * Method that manage the insert of a Cover in the selected Film by codeFilm.
	 * @param filmCode parameter that pass the code of the film
	 */
	void doAddCover(final Integer filmCode);
	/**
	 * Method that manage the insert of a new film.
	 */
	void doAddMovie();
	/**
	 * Method that manage the insert of a new Trailer.
	 * @param filmCode parameter that pass the code of the film
	 */
	void doAddTrailer(final Integer filmCode);
	/**
	 * Method that manage by a variable boolean the elimination of a film by the codeFilm.
	 * @param filmCode parameter that pass the code of the film
	 * @return boolean
	 */
	boolean doDeleteFilm(final Integer filmCode);
	/**
	 * Method that manage the editing of a Film.
	 * @param filmCode parameter that pass the code of the film
	 */
	void doEditMovie(final Integer filmCode);
	/**
	 * This Method return the details of the film selected by codefilm.	
	 * @param filmCode parameter that pass the code of the film.
	 */
	void showDetailsFilm(final Integer filmCode);
	/**
	 * Method that create the film list.
	 */
	void generateTable();
	/**
	 * Method that check if a trailer is already insert or not.	
	 * @param check parameter of type boolean that determinate if the trailer is already insert.
	 * @param codeFilm parameter that pass the code of the film.
	 */
	void returnEncoding(final boolean check, final Integer codeFilm);
	/**
	 * Method that load files.
	 */
	void doLoading();
	/**
	 * Method that save files.	
	 */
	void doSaving();
}
