package lmm.controller.admin;

import java.io.File;
import java.util.Calendar;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import lmm.controller.MainController;
import lmm.controller.interfaces.IManageUserController;
import lmm.exception.UserNotFoundException;
import lmm.model.IModel;
import lmm.model.User;
import lmm.view.admin.ManageUserView;
/**
 * This class manage the editing of the data of an user. 
 * @author Roberto Reibaldi
 *
 */
public class ManageUserController extends MainController implements IManageUserController {

	private static final String DELETE_USER = "Do you really delete this user?";
	private Thread secondaryThread;
	/**
	 * This is the constructor of the class.	
	 * @param mod this parameter pass the model to the constructor.
	 */
	public ManageUserController(final IModel mod) {
		super(mod, MANAGE_USERS_VIEW);
	}

	@Override
	public void setView(final JFrame f) {
		this.frame = f;
		((ManageUserView) this.frame).attachObserver(this);

	}

	@Override
	public void doEditUser(final String username) {
		this.changeView(this.activeView, EDITABLE_USER_VIEW, username);
	}

	@Override
	public boolean doDeleteUser(final String username) {
		try {
			final Integer answer = this.showQuestionDialog(DELETE_USER);
			if (answer == JOptionPane.YES_OPTION) {
				final String nameImageUser = this.model.getUser(username).getProfileImage();
				if (!nameImageUser.equals(DEFAULT_IMAGE)) {
					final File deleteFile = new File(DEFAULT_USER_PATH + DEFAULT_RESOURCES_PATH + "users/" + nameImageUser);
					deleteFile.delete();
				}
				this.model.deleteUser(username);
				return true;
			}
		} catch (UserNotFoundException exc) {
			this.saveError(exc);
			this.showErrorDialog(ERROR_LOADING);
		}
		return false;
	}

	@Override
	public void doUserStats(final String username) {
		this.changeView(this.activeView, USER_STATS_VIEW, username);

	}

	@Override
	public void doBack() {
		this.changeView(this.activeView, MENU_VIEW, null);
	}

	@Override
	public void generateTable() {
		final Iterator<User> it = this.model.getUsers().iterator();
		User curr;
		while (it.hasNext()) {
			curr = it.next();
			final Calendar cal = curr.getDate();
			final String date = cal.get(Calendar.DATE) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR);
			final Object[] obj = new Object[]{curr.getUserID(), curr.getPassword(), curr.getName(), curr.getSurname(), date};
			((ManageUserView) this.frame).newRow(obj);
		}
	}

	@Override
	public void addUser() {
		this.changeView(MANAGE_USERS_VIEW, EDITABLE_USER_VIEW, null);

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
