package lmm.controller.admin;

import java.util.ArrayDeque;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import lmm.view.admin.AdminStatsView;
import lmm.controller.MainController;
import lmm.controller.interfaces.IAdminStatsController;
import lmm.model.IFilm;
import lmm.model.IModel;
/**
 * This class manage the functions of the statistics of the admin.
 * @author Roberto Reibaldi
 *
 */
public class AdminStatsController extends MainController implements IAdminStatsController {

	private static final String TOP_VISITED_FILM = "visited";
	private static final String TOP_BOUGHT_FILM = "bought";

	private static final String CHANGED_PASSWORD = "Password changed successfully";
	private Thread secondaryThread = new Thread();

	/**
	 * This is the constructor of the class.
	 * @param mod this parameter pass the model to the constructor.
	 */
	public AdminStatsController(final IModel mod) {
		super(mod, ADMIN_STATS_VIEW);
	}

	@Override
	public void setView(final JFrame f) {
		this.frame = f;
		((AdminStatsView) this.frame).attachObserver(this);

	}

	@Override
	public void doBack() {
		this.changeView(this.activeView, MENU_VIEW, null);
	}

	@Override
	public void generateTableRecent() {
		final Iterator<Integer> deck = this.model.getRecentFilms().iterator();
		Integer curr;
		while (deck.hasNext()) {
			curr = deck.next();
			final IFilm film = this.model.getFilm(curr);
			final Object[] obj = new Object[]{curr, film.getTitle(), film.getGenre(), film.getDate(), film.getPrice()};
			((AdminStatsView) this.frame).newRow(obj);
		}

	}

	@Override
	public void generateTableFilmList() {
		final Iterator<Integer> it = this.model.getOrderFilm().iterator();
		Integer curr;
		while (it.hasNext()) {
			curr = it.next();
			final IFilm film = this.model.getFilm(curr);
			final Object[] obj = new Object[] {curr, film.getTitle(), film.getGenre(), film.getDate(), film.getPrice()};
			((AdminStatsView) this.frame).newRow(obj);
		}
	}

	@Override
	public void generateTableTopVisited() {
		final ArrayDeque<Integer> deck = this.model.getOrderFilms(TOP_VISITED_FILM);
		Integer curr;
		while (0 < deck.size()) {
			curr = deck.remove();
			final IFilm film = this.model.getFilm(curr);
			final Object[] obj = new Object[] {curr, film.getTitle(), film.getGenre(), film.getDate(), film.getPrice()};
			((AdminStatsView) this.frame).newRow(obj);
		}
	}

	@Override
	public void generateTableTopBought() {
		final ArrayDeque<Integer> deck = this.model.getOrderFilms(TOP_BOUGHT_FILM);
		Integer curr;
		while (0 < deck.size()) {
			curr = deck.remove();
			final IFilm film = this.model.getFilm(curr);
			final Object[] obj = new Object[] {curr, film.getTitle(), film.getGenre(), film.getDate(), film.getPrice()};
			((AdminStatsView) this.frame).newRow(obj);
		}
	}

	@Override
	public void showDetailsFilm(final Integer filmCode) {
		this.changeView(this.activeView, DETAILS_FILM_VIEW, filmCode);
	}

	@Override
	public void doChangePassword() {
		final JPasswordField passwordField = new JPasswordField();
		final Object[] obj = {"Please enter the new password:\n", passwordField};
		final Object[] stringArray = {"OK", "Cancel"};
		if (JOptionPane.showOptionDialog(this.frame, obj, "Change password", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, stringArray, obj) == JOptionPane.YES_OPTION && new String(passwordField.getPassword()).length() != 0) {
			this.model.changeAdminPassword(new String(passwordField.getPassword()));
			this.saveDataCmd();
			this.showInfoDialog(CHANGED_PASSWORD);

		}

	}

	@Override
	public String getTotalFilms() {
		final Integer films = this.model.getKeySetFilm().size();
		return films.toString();
	}

	@Override
	public String getTotalUsers() {
		final Integer users = this.model.getUsers().size();
		return users.toString();
	}

	@Override
	public String getTotalIncomes() {
		return this.model.getTotCash().toString();
	}

	@Override
	public void doLoading() {
		this.secondaryThread = new Thread() {
			public void run() {
				doLoadMenu();
			}
		};
		this.secondaryThread.start();
	}

	@Override
	public void doSaving() {
		this.secondaryThread = new Thread() {
			public void run() {
				doSaveMenu();
			}
		};
		this.secondaryThread.start();
	}



}