package lmm.controller.user;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import lmm.view.user.FavoriteFilmView;
import lmm.controller.MainController;
import lmm.controller.interfaces.IFavoriteFilmController;
import lmm.exception.DuplicateBoughtException;
import lmm.exception.UserNotFoundException;
import lmm.model.IFilm;
import lmm.model.IModel;
/**
 * This class manage the favorite films of an user.
 * @author Roberto Reibaldi, Luca Pascucci
 *
 */
public class FavoriteFilmController extends MainController implements IFavoriteFilmController {

	private static final String FILM_BOUGHT = "Film successfully purchased!";
	private static final String FILM_ALREADY_BOUGHT = "Film already bought!";

	private Integer codeFilmPreview;

	/**
	 * This is the constructor of the class.
	 * @param mod this parameter pass the model to the constructor.
	 */
	public FavoriteFilmController(final IModel mod) {
		super(mod, FAVORITE_FILM_VIEW);
	}

	@Override
	public void setView(final JFrame f) {
		this.frame = f;
		((FavoriteFilmView) this.frame).attachObserver(this);

	}

	@Override
	public void doBack() {
		this.changeView(this.activeView, MENU_VIEW, null);
	}

	@Override
	public void generateTable() {
		try {
			final Iterator<Integer> it = this.model.getFavoritesFilms(this.model.getCurrentUser().getUserID()).iterator();
			Integer curr;
			while (it.hasNext()) {
				curr = it.next();
				final IFilm film = this.model.getFilm(curr);
				final Object[] obj = new Object[] {curr, film.getTitle(), film.getGenre(), film.getDate(), film.getPrice()};
				((FavoriteFilmView) this.frame).newRow(obj);
			}
		} catch (UserNotFoundException exc) {
			this.saveError(exc);
		}
	}

	@Override
	public void showDetailsFilm(final Integer filmCode) {
		this.changeView(FAVORITE_FILM_VIEW, DETAILS_FILM_VIEW, filmCode);
	}

	@Override
	public void doBuy(final Integer filmCode) {
		if (!this.model.getFilmBought(this.model.getCurrentUser().getUserID()).contains(filmCode)) {
			try {
				this.model.addBoughtFilms(filmCode);
				this.model.boughtFilm(filmCode);
				this.saveDataCmd();
				this.showInfoDialog(FILM_BOUGHT);
			} catch (DuplicateBoughtException exc) {
				this.saveError(exc);
			}
		} else {
			this.showWarningDialog(FILM_ALREADY_BOUGHT);
		}

	}

	@Override
	public boolean deleteFavorite(final Integer filmCode) {
		try {
			if (this.codeFilmPreview.equals(filmCode)) {
				((FavoriteFilmView) this.frame).unsetPreview();
			}
			this.model.removeFavoriteFilm(filmCode);
			return true;
		} catch (UserNotFoundException exc) {
			this.saveError(exc);
		}
		return false;

	}

	@Override
	public void showPreview(final Integer filmCode) {
		this.codeFilmPreview = filmCode;
		final IFilm film = this.model.getFilm(filmCode);
		((FavoriteFilmView) this.frame).setPreview(film, this.getCoverImage(film.getImages().firstElement()));

	}

	private ImageIcon getCoverImage(final String cover) {
		try {
			if (cover.equals(DEFAULT_COVER)) {
				final BufferedImage img = ImageIO.read(new File(this.getClass().getResource("/" + cover).toURI()));
				final BufferedImage scaledImage = this.getScaledImage(img);
				return new ImageIcon(scaledImage);
			} else {
				final BufferedImage img = ImageIO.read(new File(DEFAULT_USER_PATH + DEFAULT_RESOURCES_PATH + "films/" + cover));
				final BufferedImage scaledImage = this.getScaledImage(img);
				return new ImageIcon(scaledImage);
			}
		} catch (IOException exc) {
			this.saveError(exc);
		} catch (URISyntaxException exc) {
			this.saveError(exc);
			this.showErrorDialog(ERROR_LOADING);
		}
		return null;
	}

	/**
	 * This method returns a resized BufferedImage
	 * @param originalImage
	 * @return BufferedImage
	 */
	private BufferedImage getScaledImage(final BufferedImage originalImage) {

		final int maxWidth = 100;
		final int maxHeight = 100;

		int width = originalImage.getWidth();
		int height = originalImage.getHeight();

		final Dimension originalDimension = new Dimension(originalImage.getWidth(), originalImage.getHeight());
		final Dimension boundaryDimension = new Dimension(maxWidth, maxHeight);
		final Dimension scalingDimension = getScaledDimension(originalDimension, boundaryDimension);

		width = (int) scalingDimension.getWidth();
		height = (int) scalingDimension.getHeight();

		final BufferedImage resizedImage = new BufferedImage(width, height, originalImage.getType());
		final Graphics2D g = resizedImage.createGraphics();

		g.drawImage(originalImage, 0, 0, width, height, null);

		return resizedImage;

	}

	/**
	 * This method returns the new dimension for ScaledImage
	 * @param imgSize
	 * @param boundary
	 * @return Dimension
	 */
	private Dimension getScaledDimension(final Dimension imgSize, final Dimension boundary) {

		final int originalWidth = imgSize.width;
		final int originalHeight = imgSize.height;
		final int boundWidth = boundary.width;
		final int boundHeight = boundary.height;
		int newWidth = originalWidth;
		int newHeight = originalHeight;

		// check if need to perform the scaling of witdh
		if (originalWidth > boundWidth) {

			newWidth = boundWidth;
			// scaling of height
			newHeight = (newWidth * originalHeight) / originalWidth;
		}

		// check if need to perform the scaling of height 
		if (newHeight > boundHeight) {

			newHeight = boundHeight;
			// scaling of witdth
			newWidth = (newHeight * originalWidth) / originalHeight;
		}

		return new Dimension(newWidth, newHeight);
	}

}
