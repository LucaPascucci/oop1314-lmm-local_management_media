package lmm.controller;

import javax.swing.JFrame;

import lmm.controller.interfaces.IMenuController;
import lmm.model.IModel;
import lmm.view.MenuView;
/**
 * This class is one of the principal view of the application.
 * By the functions of this class the current user can travel in the application.
 * @author Roberto Reibaldi
 *
 */
public class MenuController extends MainController implements IMenuController {

	private boolean userControll;
	/**
	 * This is the constructor of the class.
	 * @param mod this parameter pass the model to the constructor.
	 */
	public MenuController(final IModel mod) {
		super(mod, MENU_VIEW);

		if (this.model.getCurrentUser().getUserID().equals(this.model.getAdmin().getUserID())) {
			this.userControll = true;
		} else {
			this.userControll = false;
		}
	}

	@Override
	public void setView(final JFrame f) {
		this.frame = f;
		((MenuView) this.frame).attachObserver(this);

	}
	@Override
	public void backToHome() {
		this.changeView(this.activeView, LOGIN_VIEW, null);
	}

	@Override
	public boolean getUserControll() {
		return this.userControll;
	}

	@Override
	public void doFirst() {
		if (this.userControll) {
			this.changeView(MENU_VIEW, MANAGE_FILMS_VIEW, null);
		} else {
			this.changeView(MENU_VIEW, FILM_LIST_VIEW, null);
		}
	}

	@Override
	public void doSecond() {
		if (this.userControll) {
			this.changeView(MENU_VIEW, MANAGE_USERS_VIEW, null);
		} else {
			this.changeView(MENU_VIEW, FAVORITE_FILM_VIEW, null);
		}
	}

	@Override
	public void doThird() {
		if (this.userControll) {
			this.changeView(MENU_VIEW, ADMIN_STATS_VIEW, null);
		} else {
			this.changeView(MENU_VIEW, USER_STATS_VIEW, null);
		}
	}

	@Override
	public void doBack() {
		this.backToHome();
	}

}
