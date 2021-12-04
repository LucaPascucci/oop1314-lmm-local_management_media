package lmm.model;

import java.io.Serializable;
import java.util.Vector;

/**
 * Models a generic film.
 * @author Marco Sperandeo
 *
 */
public class Film implements IFilm, Serializable {

	private static final long serialVersionUID = 1;

	private final String title;
	private final String plot;
	private final Integer price;
	private final Integer date;
	private final FilmType genre;
	private final boolean trailer;
	private final Vector<String> images;

	/**
	 * Creats a new film with the parameters provided in input.
	 * @param newTitle string title
	 * @param newPlot string plot
	 * @param newPrice integer price
	 * @param newDate integer date
	 * @param newGenre filmType genre
	 * @param newCover string cover
	 */
	public Film(final String newTitle, final String newPlot, final Integer newPrice, final Integer newDate, final FilmType newGenre, final String newCover) {
		this.date = newDate;
		this.plot = newPlot;
		this.price = newPrice;
		this.title = newTitle;
		this.genre = newGenre;
		this.trailer = false;
		this.images = new Vector<>();
		this.images.add(newCover);
	}

	/**
	 * Creats a new film with the parameters provided in input that overwrite the previous.
	 * @param newTitle string title
	 * @param newPlot string plot
	 * @param newPrice integer price
	 * @param newDate integer date
	 * @param newGenre filmType genre
	 * @param newCover string cover
	 * @param newTrailer boolean trailer
	 */
	public Film(final String newTitle, final String newPlot, final Integer newPrice, final Integer newDate, final FilmType newGenre, final Vector<String> newCover, final boolean newTrailer) {
		this.date = newDate;
		this.plot = newPlot;
		this.price = newPrice;
		this.title = newTitle;
		this.genre = newGenre;
		this.trailer = newTrailer;
		this.images = newCover;
	}

	@Override
	public String getTitle() {
		return this.title;
	}
	@Override
	public String getPlot() {
		return this.plot;
	}
	@Override
	public Integer getPrice() {
		return this.price;
	}
	@Override
	public Integer getDate() {
		return this.date;
	}
	@Override
	public FilmType getGenre() {
		return genre;
	}
	@Override
	public Vector<String> getImages() {
		return this.images;
	}

	@Override
	public boolean getTrailer() {
		return this.trailer;
	}

}
