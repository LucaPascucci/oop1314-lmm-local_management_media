package lmm.controller.user;

import java.util.ArrayDeque;
import java.util.Iterator;

import javax.swing.JFrame;

import lmm.controller.MainController;
import lmm.controller.interfaces.IFilmListController;
import lmm.model.FilmType;
import lmm.model.IFilm;
import lmm.model.IModel;
import lmm.view.user.FilmListView;
/**
 * This class manage the films list of the application.
 * @author Roberto Reibaldi
 *
 */
public class FilmListController extends MainController implements IFilmListController {

	private static final String TOP_VISITED_FILM = "visited";
	private static final String TOP_BOUGHT_FILM = "bought";

	/**
	 * This is the constructor of the class.
	 * @param mod this parameter pass the model to the constructor.
	 */
	public FilmListController(final IModel mod) {
		super(mod, FILM_LIST_VIEW);
	}

	@Override
	public void setView(final JFrame f) {
		this.frame = f;
		((FilmListView) this.frame).attachObserver(this);
	}

	@Override
	public void doBack() {
		this.changeView(this.activeView, MENU_VIEW, null);

	}

	@Override
	public void showDetailsFilm(final Integer filmCode) {
		this.changeView(this.activeView, DETAILS_FILM_VIEW, filmCode);

	}

	@Override
	public void generateTableRecent(final Object[] filter) {
		final Iterator<Integer> deck = this.model.getRecentFilms().iterator();
		Integer curr;
		if (filter == null) {
			while (deck.hasNext()) {
				curr = deck.next();
				final IFilm film = this.model.getFilm(curr);
				final Object[] obj = new Object[]{curr, film.getTitle(), film.getGenre(), film.getDate(), film.getPrice()};
				((FilmListView) this.frame).newRow(obj);
			}
		} else {
			while (deck.hasNext()) {
				curr = deck.next();
				final IFilm film = this.model.getFilm(curr);
				if (this.checkFilterFilm(film, filter)) {
					final Object[] obj = new Object[] {curr, film.getTitle(), film.getGenre(), film.getDate(), film.getPrice()};
					((FilmListView) this.frame).newRow(obj);
				}
			}
		}

	}

	@Override
	public void generateTableTopVisited(final Object[] filter) {
		final ArrayDeque<Integer> deck = this.model.getOrderFilms(TOP_VISITED_FILM);
		Integer curr;
		if (filter == null) {
			while (0 < deck.size()) {
				curr = deck.remove();
				final IFilm film = this.model.getFilm(curr);
				final Object[] obj = new Object[] {curr, film.getTitle(), film.getGenre(), film.getDate(), film.getPrice()};
				((FilmListView) this.frame).newRow(obj);
			}
		} else {
			while (0 < deck.size()) {
				curr = deck.remove();
				final IFilm film = this.model.getFilm(curr);
				if (this.checkFilterFilm(film, filter)) {
					final Object[] obj = new Object[] {curr, film.getTitle(), film.getGenre(), film.getDate(), film.getPrice()};
					((FilmListView) this.frame).newRow(obj);
				}
			}
		}

	}

	@Override
	public void generateTableTopBought(final Object[] filter) {
		final ArrayDeque<Integer> deck = this.model.getOrderFilms(TOP_BOUGHT_FILM);
		Integer curr;
		if (filter == null) {
			while (0 < deck.size()) {
				curr = deck.remove();
				final IFilm film = this.model.getFilm(curr);
				final Object[] obj = new Object[] {curr, film.getTitle(), film.getGenre(), film.getDate(), film.getPrice()};
				((FilmListView) this.frame).newRow(obj);
			}
		} else {
			while (0 < deck.size()) {
				curr = deck.remove();
				final IFilm film = this.model.getFilm(curr);
				if (this.checkFilterFilm(film, filter)) {
					final Object[] obj = new Object[] {curr, film.getTitle(), film.getGenre(), film.getDate(), film.getPrice()};
					((FilmListView) this.frame).newRow(obj);
				}
			}
		}

	}

	@Override
	public void generateTableFilmList(final Object[] filter) {
		final Iterator<Integer> it = this.model.getOrderFilm().iterator();
		Integer curr;
		if (filter == null) {
			while (it.hasNext()) {
				curr = it.next();
				final IFilm film = this.model.getFilm(curr);
				final Object[] obj = new Object[] {curr, film.getTitle(), film.getGenre(), film.getDate(), film.getPrice()};
				((FilmListView) this.frame).newRow(obj);
			}
		} else {
			while (it.hasNext()) {
				curr = it.next();
				final IFilm film = this.model.getFilm(curr);
				if (this.checkFilterFilm(film, filter)) {
					final Object[] obj = new Object[] {curr, film.getTitle(), film.getGenre(), film.getDate(), film.getPrice()};
					((FilmListView) this.frame).newRow(obj);
				}
			}
		}
	}
	/**
	 * This private method have the function to check the filter that will be used to manage the film list.
	 * @param film this parameter pass the film.	
	 * @param filter this parameter pass all the filter chosen.
	 * @return boolean
	 */
	private boolean checkFilterFilm(final IFilm film, final Object[] filter) {
		boolean checkTitle = false;
		boolean checkGenre = false;
		boolean checkPrice = false;
		boolean checkYear = false;
		final String titleFilm = film.getTitle();
		if (filter[0] == null) {
			checkTitle = true;
		} else if (film.getTitle().startsWith((String) filter[0])) {
			checkTitle = true;
		} else if (((String) filter[0]).length() == 1 && (titleFilm.startsWith(((String) filter[0]).toUpperCase()) || titleFilm.startsWith(((String) filter[0]).toLowerCase()))) {
			checkTitle = true;
		}
		if (filter[1] == null || film.getGenre().equals((FilmType) filter[1])) {
			checkGenre = true;
		}
		if (filter[2] == null && filter[3] == null || film.getPrice() >= (Integer) filter[2] && film.getPrice() < (Integer) filter[3]) {
			checkPrice = true;
		}
		if (filter[4] == null || film.getDate().equals((Integer) filter[4])) {
			checkYear = true;
		}
		if (checkTitle && checkGenre && checkPrice && checkYear) {
			return true;

		}
		return false;
	}

}
