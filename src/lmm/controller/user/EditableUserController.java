package lmm.controller.user;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;

import lmm.controller.MainController;
import lmm.controller.interfaces.IEditableUserController;
import lmm.exception.DuplicateUsernameException;
import lmm.exception.UserNotFoundException;
import lmm.view.user.EditableUserView;
import lmm.model.IModel;
import lmm.model.User;
/**
 * This class check the insert of the parameter of a new user.
 * @author Roberto Reibaldi
 *
 */
public class EditableUserController extends MainController implements IEditableUserController {

	private static final String ERROR_DOUBLE = "This username already exist";
	private static final String ERROR_USER = "This username is incorret";
	private static final String ERROR_DATE = "Invalid date selection";

	private static final List<String> USERNAME_NOT_ALLOWED = Arrays.asList("admin", "default_user");

	private String profileImage;
	private Set<Integer> favoritesFilm;

	private final String editableUserId;
	private final Integer returnView;

	/**
	 * This is the constructor of the EditableUserController.
	 * @param model this parameter pass the model to the constructor.
	 * @param fromGUI this paramater pass the correct view.
	 * @param item this parameter pass the id of the user.
	 */
	public EditableUserController(final IModel model, final Integer fromGUI, final String item) {
		super(model, EDITABLE_USER_VIEW);
		this.returnView = fromGUI;
		this.editableUserId = item;
	}

	@Override
	public void setView(final JFrame f) {
		this.frame = f;
		((EditableUserView) this.frame).attachObserver(this);

	}

	@Override
	public User getEditableUser() {
		try {
			if (this.editableUserId != null) {
				final User editableUser = this.model.getUser(editableUserId);
				this.profileImage = editableUser.getProfileImage();
				this.favoritesFilm = editableUser.getFavorites();
				return editableUser;
			}
		} catch (UserNotFoundException exc) {
			this.saveError(exc);
			this.showErrorDialog(ERROR_LOADING);
		}
		return null;
	}

	@Override
	public void doBack() {
		this.changeView(this.activeView, this.returnView, null);
	}

	@Override
	public void doRegister(final String name, final String surname, final String userId, final String password, final Calendar date) {
		if (USERNAME_NOT_ALLOWED.contains(userId)) {
			((EditableUserView) this.frame).resetUserIDField();
			this.showErrorDialog(ERROR_USER);
		} else if (userId.length() != 0 && name.length() != 0 && surname.length() != 0 && password.length() != 0 && !(date == null || date.after(Calendar.getInstance()))) {
			if (this.editableUserId != null) {
				final User curr = new User(userId, password, name, surname,  date, this.profileImage, this.favoritesFilm);
				this.model.editUser(curr, null);
				this.saveDataCmd();
				this.changeView(this.activeView, this.returnView, null);
			} else {
				final User currUser = new User(userId, password, name, surname, date, DEFAULT_IMAGE);
				try {
					this.model.addUser(currUser);
					this.changeView(this.activeView, this.returnView, null);
				} catch (DuplicateUsernameException exc) {
					this.saveError(exc);
					this.showErrorDialog(ERROR_DOUBLE);
					((EditableUserView) this.frame).resetUserIDField();
				}
			}
		} else {
			this.showErrorDialog(ERROR_INSERT + " or \n" + ERROR_DATE);
		}
	}

}
