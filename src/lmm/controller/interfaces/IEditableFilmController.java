package lmm.controller.interfaces;

import lmm.model.FilmType;
import lmm.model.IFilm;
/**
 * Interface that define {@link lmm.controller.admin.EditableFilmController}.
 * @author Roberto Reibaldi
 *
 */
public interface IEditableFilmController {
	/**
	 * Return the view of the info of the current film.
	 * @return {@link lmm.model.IFilm}
	 */
	IFilm getEditableFilm();
	/**
	 * Method that checks that the new parameters are correct.
	 * @param title parameter that pass the name of the film.
	 * @param price parameter that pass the cost of the film.
	 * @param plot parameter that pass the description of the film.
	 * @param year parameter that pass the release date of the film.
	 * @param currType parameter that pass the gender of the film.
	 */
	void doConfirm(final String title, final String price, final String plot, final Integer year, final FilmType currType);

}
