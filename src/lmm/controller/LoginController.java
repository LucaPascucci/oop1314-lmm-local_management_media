package lmm.controller;

import java.io.File;

import javax.swing.JFrame;

import lmm.controller.interfaces.ILoginController;
import lmm.exception.UserNotFoundException;
import lmm.model.IModel;
import lmm.model.IUser;
import lmm.view.LoginView;
/**
 * This class manage the functions of the login.
 * @author Roberto Reibaldi
 *
 */
public class LoginController extends MainController implements ILoginController {

	private static final String ERROR_LOGIN = "No user found with the provided credentials";
	private static final String ERROR_PSWD = "Incorrect password";
	/**
	 * This is the construct of the class. 	
	 * @param mod this parameter pass the model to the constructor.
	 */
	public LoginController(final IModel mod) {
		super(mod, LOGIN_VIEW);
		this.model.setCurrentUser(null);
	}

	@Override
	public void setView(final JFrame f) {
		this.frame = f;
		((LoginView) this.frame).attachObserver(this);

	}
	@Override
	public void doLogin(final String username, final String password) {
		if (password.length() == 0 || username.length() == 0) {
			this.showErrorDialog(ERROR_INSERT);
		} else {
			if (username.equals(this.model.getAdmin().getUserID())) {
				if (password.equals(this.model.getAdmin().getPassword())) {
					this.model.setCurrentUser(this.model.getAdmin());
					this.saveDataCmd();
					this.changeView(LOGIN_VIEW, MENU_VIEW, null);
				} else {
					this.showErrorDialog(ERROR_PSWD);
					((LoginView) this.frame).resetField();
				}
			} else {
				try {
					final IUser current = this.model.getUser(username);
					if (current.getPassword().equals(password)) {
						this.model.setCurrentUser(current);
						this.saveDataCmd();
						this.changeView(LOGIN_VIEW, MENU_VIEW, null);
					} else {
						this.showErrorDialog(ERROR_PSWD);
						((LoginView) this.frame).resetField();
					}
				} catch (UserNotFoundException e1) {
					this.saveError(e1);
					this.showErrorDialog(ERROR_LOGIN);
					((LoginView) this.frame).resetField();
				}
			}
		}	
	}

	@Override
	public void doNewUser() {
		this.changeView(LOGIN_VIEW, EDITABLE_USER_VIEW, null);
	}

	@Override
	public void doBack() {
		this.doExit();
	}

	@Override
	public void createWorkspace() {
		
		if (!this.resourcesFolderExist(DEFAULT_USER_PATH)) {
			new File(DEFAULT_USER_PATH).mkdir();
		}
		if (!this.resourcesFolderExist(DEFAULT_USER_PATH + DEFAULT_BACKUP_PATH)) {
			new File(DEFAULT_USER_PATH + DEFAULT_BACKUP_PATH).mkdir();
		}
		if (!this.resourcesFolderExist(DEFAULT_USER_PATH + DEFAULT_RESOURCES_PATH)) {
			new File(DEFAULT_USER_PATH + DEFAULT_RESOURCES_PATH).mkdir();
		}
		if (!this.resourcesFolderExist(DEFAULT_USER_PATH + DEFAULT_RESOURCES_PATH + "films/")) {
			new File(DEFAULT_USER_PATH + DEFAULT_RESOURCES_PATH + "films/").mkdir();
		}
		if (!this.resourcesFolderExist(DEFAULT_USER_PATH + DEFAULT_RESOURCES_PATH + "users/")) {
			new File(DEFAULT_USER_PATH + DEFAULT_RESOURCES_PATH + "users/").mkdir();
		}
	}

}
