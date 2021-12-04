package lmm.controller.admin;

import java.util.Vector;

import javax.swing.JFrame;

import lmm.controller.MainController;
import lmm.controller.interfaces.IEditableFilmController;
import lmm.exception.InvalidContentException;
import lmm.model.FilmType;
import lmm.model.IFilm;
import lmm.model.IModel;
import lmm.view.admin.EditableFilmView;

/**
 * This class manage the functions of EditableFilmController.
 * @author Roberto Reibaldi
 */
public class EditableFilmController extends MainController implements IEditableFilmController {

	private static final String INVALID_NUMBER = "Insert a number in Price and Release Year";
	private static final String SELECT_GENRE = "Select a Film Genre!";
	private static final String INSERT_TITLE = "Insert film title!";

	private final Integer editableFilm;

	private Vector<String> covers;
	private boolean trailer;
	/**
	 * This is the construct of EditableFilmController.
	 * @param mod this parameter pass the model to the constructor.
	 * @param idFilm this parameter pass the code of the film.
	 */
	public EditableFilmController(final IModel mod, final Integer idFilm) {
		super(mod, EDITABLE_FILM_VIEW);
		this.editableFilm = idFilm;
	}

	@Override
	public void setView(final JFrame f) {
		this.frame = f;
		((EditableFilmView) this.frame).attachObserver(this);
	}

	@Override
	public IFilm getEditableFilm() {
		if (this.editableFilm != null) {
			final IFilm film = this.model.getFilm(this.editableFilm);
			this.covers = film.getImages();
			this.trailer = film.getTrailer();
			return film;
		}
		return null;
	}

	@Override
	public void doBack() {
		this.changeView(this.activeView, MANAGE_FILMS_VIEW, null);
	}

	@Override
	public void doConfirm(final String title, final String price, final String plot, final Integer year, final FilmType currType) {
		try {
			final Integer value = Integer.parseInt(price);
			if (title.length() != 0) {
				if (currType != null) {
					if (this.editableFilm != null) {
						this.model.editFilm(this.editableFilm, title, value, plot, year, currType, this.covers, this.trailer);
					} else {
						this.model.addFilm(title, value, plot, year, currType, DEFAULT_COVER);
					}
					this.saveDataCmd();
					this.changeView(this.activeView, MANAGE_FILMS_VIEW, null);
				} else {
					this.showErrorDialog(SELECT_GENRE);
				}
			} else {
				this.showErrorDialog(INSERT_TITLE);
			}

		} catch (NumberFormatException exc) {
			this.saveError(exc);
			this.showErrorDialog(INVALID_NUMBER);
			((EditableFilmView) this.frame).resetNumberField();
		} catch (InvalidContentException exc) {
			this.saveError(exc);
			this.showErrorDialog(ERROR_INSERT);
		}
	}
}
