package lmm.controller.interfaces;

import javax.swing.Icon;

import lmm.model.IFilm;

/**
 * Interface that define the DetailsFilmController{@link lmm.controller.DetailsFilmController}.
 * @author Roberto Reibaldi
 *
 */
public interface IDetailsFilmController {

	/**
	 * Method that download all the file, relative to the cover, to the current computer.
	 */
	void doDownloadCover();
	/**
	 * Method that runs the video Trailer of the selected film.	
	 */
	void doTrailerView();
	/**
	 * Method that confirm that a generic user bought a generic film. 	
	 */
	void doBuy();
	/**
	 * Return if the selected film	is the favorite one of the user or not.
	 * @return boolean
	 */
	boolean doFavorite();
	/**
	 * Return the object Film.	
	 * @return {@link lmm.model.IFilm}
	 */
	IFilm getFilm();
	/**
	 * Return the type of current user.
	 * @return boolean
	 */
	boolean getUserControll();
	/**
	 * Method that confirm that the visit took place.	
	 */
	void doVisit();
	/**
	 *  Get the current object cover.
	 *  @return Icon
	 */
	Icon getCover();
	/**
	 * Return the code of the Film to String.
	 * @return String
	 */
	String getFIlmID();
	/**
	 * Return a boolean that say if the selected film is one of the favorite films of a generic User.
	 * @return boolean
	 */
	boolean isFavorite();
	/**
	 * Return a boolean that say if a generic film is not anymore one of the favorite films of a generic User.
	 * @return boolean
	 */
	boolean deleteFavorite();
	
	/**
	 * This method check if the secondaryThread is running.
	 * @return boolean
	 */
	boolean controllThread();
}
