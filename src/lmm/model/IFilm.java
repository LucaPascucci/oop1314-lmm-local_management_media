package lmm.model;

import java.util.Vector;

/**
 * Defines a film.
 * @author Marco Sperandeo
 *
 */
public interface IFilm {

	/**
	 * Returns the film's title.
	 * @return String
	 */
	String getTitle();

	/**
	 * Returns the film's plot.
	 * @return String
	 */
	String getPlot();

	/**
	 * Returns the film' price.
	 * @return Integer
	 */
	Integer getPrice();

	/**
	 * Returns the fim's date.
	 * @return Integer
	 */
	Integer getDate();

	/**
	 * Returns the genre of the film.
	 * @return {@link lmm.model.FilmType}
	 */
	FilmType getGenre();

	/**
	 * Returns the images of the film.
	 * @return Vector<String>
	 */
	Vector<String> getImages();

	/**
	 * Returns the trailer of the film.
	 * @return boolean
	 */
	boolean getTrailer();

}
