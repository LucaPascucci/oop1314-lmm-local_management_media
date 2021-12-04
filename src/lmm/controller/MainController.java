package lmm.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import lmm.controller.admin.AdminStatsController;
import lmm.controller.admin.EditableFilmController;
import lmm.controller.admin.ManageFilmController;
import lmm.controller.admin.ManageUserController;
import lmm.controller.interfaces.IMainController;
import lmm.controller.user.EditableUserController;
import lmm.controller.user.FavoriteFilmController;
import lmm.controller.user.FilmListController;
import lmm.controller.user.UserStatsController;
import lmm.model.IModel;
import lmm.view.CreditsView;
import lmm.view.DetailsFilmView;
import lmm.view.FeedbackView;
import lmm.view.LoginView;
import lmm.view.MenuView;
import lmm.view.admin.AdminStatsView;
import lmm.view.admin.EditableFilmView;
import lmm.view.admin.ManageFilmView;
import lmm.view.admin.ManageUserView;
import lmm.view.user.EditableUserView;
import lmm.view.user.FavoriteFilmView;
import lmm.view.user.FilmListView;
import lmm.view.user.UserStatsView;

/**
 * This is the MainController class, this class is the most important because it have all the principal resource for the correct functioning of the application.
 * @author Roberto Reibaldi,Luca Pascucci
 *
 */
public abstract class MainController implements IMainController {
	
	protected static final String DEFAULT_USER_PATH = System.getProperty("user.home") + "/LocalManagementMedia/";
	protected static final String DATA_SAVING_STR = "Data will be saved";
	protected static final String ERROR_INSERT = "Don't leave empty field";
	protected static final String ERROR_LOADING = "System error while loading";
	protected static final String DEFAULT_IMAGE = "Default_User.png";
	protected static final String DEFAULT_COVER = "Default_Cover.png";
	protected static final String DEFAULT_SAVE_PATH = "data.lmm";
	protected static final String DEFAULT_SAVE_ERROR_FILE_PATH = "error.txt";
	protected static final String DEFAULT_RESOURCES_PATH = "resources/";
	protected static final String DEFAULT_BACKUP_PATH = "backup/";
	protected static final String ZIP_FORMAT = ".zip";

	protected static final Integer LOGIN_VIEW = 0;
	protected static final Integer MENU_VIEW = 1;
	protected static final Integer MANAGE_FILMS_VIEW = 2;
	protected static final Integer MANAGE_USERS_VIEW = 3;
	protected static final Integer ADMIN_STATS_VIEW = 4;
	protected static final Integer FILM_LIST_VIEW = 5;
	protected static final Integer FAVORITE_FILM_VIEW = 6;
	protected static final Integer USER_STATS_VIEW = 7;
	protected static final Integer EDITABLE_USER_VIEW = 8;
	protected static final Integer EDITABLE_FILM_VIEW = 9;
	protected static final Integer DETAILS_FILM_VIEW = 10;
	protected static final Integer CREDITS_VIEW = 11;
	protected static final Integer FEEDBACK_VIEW = 12;

	protected static final Integer BUFFER_SIZE = 1024;

	
	private static final String FILE_NOT_ACCEPTED = "Thumbs.db";
	private static final String ERROR_SAVE_DATA = "Error while saving data to disk";
	private static final String ERROR_LOAD_DATA = "Error while loading data from disk";
	private static final String ERROR_SAVE_EXCEPTION = "Error while saving exception to disk";
	private static final String QUITTING_INFO = "Do you really want to quit?\n(" + DATA_SAVING_STR + ")";
	private static final String ERROR_STR = "Error";
	private static final String INFO_STR = "Information";
	private static final String WARNING_STR = "Warning";
	private static final String CONFIRM_STR = "Confirming..";
	private static final String DATA_FILE = "Data file didn't be create";
	private static final String ERROR_FILENAME = "File name can't contain this char (.)";

	protected JFileChooser fileDialog = new JFileChooser();
	protected IModel model;
	protected JFrame frame;
	protected Integer activeView;

	private Integer numberErrorView;

	/**
	 * This is the construct of the class.
	 * @param mod this parameter pass the model to the constructor.
	 * @param view this parameter pass the correct view that will be shown.
	 */
	public MainController(final IModel mod, final Integer view) {
		this.model = mod;
		this.activeView = view;
		this.numberErrorView = 1;
	}

	@Override
	public void changeView(final Integer fromView, final Integer toView, final Object item) {
		if (toView.equals(LOGIN_VIEW)) {
			this.saveDataCmd();
			final LoginController controller = new LoginController(this.model);
			final LoginView view = new LoginView();
			controller.setView(view);
			this.frame.dispose();
			view.setVisible(true);

		} else if (toView.equals(MENU_VIEW)) {
			final MenuController controller = new MenuController(this.model);
			final MenuView view = new MenuView();
			controller.setView(view);
			this.frame.dispose();
			view.setVisible(true);

		} else if (toView.equals(MANAGE_FILMS_VIEW)) {
			final ManageFilmController controller = new ManageFilmController(this.model);
			final ManageFilmView view = new ManageFilmView();
			controller.setView(view);
			this.frame.dispose();
			view.setVisible(true);


		} else if (toView.equals(MANAGE_USERS_VIEW)) {
			final ManageUserController controller = new ManageUserController(this.model);
			final ManageUserView view = new ManageUserView();
			controller.setView(view);
			this.frame.dispose();
			view.setVisible(true);


		} else if (toView.equals(ADMIN_STATS_VIEW)) {
			final AdminStatsController controller = new AdminStatsController(this.model);
			final AdminStatsView view = new AdminStatsView();
			controller.setView(view);
			this.frame.dispose();
			view.setVisible(true);


		} else if (toView.equals(FILM_LIST_VIEW)) {
			final FilmListController controller = new FilmListController(this.model);
			final FilmListView view = new FilmListView();
			controller.setView(view);
			this.frame.dispose();
			view.setVisible(true);


		} else if (toView.equals(FAVORITE_FILM_VIEW)) {
			final FavoriteFilmController controller = new FavoriteFilmController(this.model);
			final FavoriteFilmView view = new FavoriteFilmView();
			controller.setView(view);
			this.frame.dispose();
			view.setVisible(true);


		} else if (toView.equals(USER_STATS_VIEW)) {
			final UserStatsController controller = new UserStatsController(this.model, (String) item);
			final UserStatsView view = new UserStatsView();
			controller.setView(view);
			this.frame.dispose();
			view.setVisible(true);


		} else if (toView.equals(EDITABLE_USER_VIEW)) {
			final EditableUserController controller = new EditableUserController(this.model, fromView, (String) item);
			final EditableUserView view = new EditableUserView();
			controller.setView(view);
			this.frame.dispose();
			view.setVisible(true);


		} else if (toView.equals(EDITABLE_FILM_VIEW)) {
			final EditableFilmController controller = new EditableFilmController(this.model, (Integer) item);
			final EditableFilmView view = new EditableFilmView();
			controller.setView(view);
			this.frame.dispose();
			view.setVisible(true);


		} else if (toView.equals(DETAILS_FILM_VIEW)) {
			final DetailsFilmController controller = new DetailsFilmController(this.model, fromView, (Integer) item);
			final DetailsFilmView view = new DetailsFilmView();
			controller.setView(view);
			this.frame.dispose();
			view.setVisible(true);


		} else if (toView.equals(CREDITS_VIEW)) {
			final CreditsController controller = new CreditsController(this.model, fromView);
			final CreditsView view = new CreditsView();
			controller.setView(view);
			this.frame.dispose();
			view.setVisible(true);


		} else if (toView.equals(FEEDBACK_VIEW)) {
			final FeedbackController controller = new FeedbackController(this.model, fromView);
			final FeedbackView view = new FeedbackView();
			controller.setView(view);
			this.frame.dispose();
			view.setVisible(true);

		}
	}

	@Override
	public abstract void setView(JFrame f);


	@Override
	public abstract void doBack();


	@Override
	public void backToHome() {
		this.showInfoDialog(DATA_SAVING_STR);
		this.changeView(this.activeView, LOGIN_VIEW, null);
	}

	@Override
	public void showErrorDialog(final String message) {
		JOptionPane.showMessageDialog(this.frame, message, ERROR_STR, JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void showWarningDialog(final String message) {
		JOptionPane.showMessageDialog(this.frame, message, WARNING_STR, JOptionPane.WARNING_MESSAGE);
	}

	@Override
	public void showInfoDialog(final String message) {
		JOptionPane.showMessageDialog(this.frame, message, INFO_STR, JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public Integer showQuestionDialog(final String message) {
		final int answer = JOptionPane.showConfirmDialog(this.frame, message, CONFIRM_STR, JOptionPane.YES_NO_OPTION);
		return answer;
	}

	@Override
	public boolean fileExist(final String path) {
		return new File(path).exists();
	}

	@Override
	public boolean resourcesFolderExist(final String path) {
		final File resources = new File(path);
		return resources.isDirectory() && resources.exists();
	}

	@Override
	public void saveDataCmd() {
		this.doSaveData(DEFAULT_USER_PATH + DEFAULT_SAVE_PATH);

	}

	@Override
	public void loadDataCmd() {
		this.doLoadData(DEFAULT_USER_PATH + DEFAULT_SAVE_PATH);

	}
	
	@Override
	public void saveError(final Exception exc) {
		final StackTraceElement[] stackTrace = exc.getStackTrace();
		final Calendar currentDay = Calendar.getInstance();
		try {
			final FileWriter out = new FileWriter(DEFAULT_USER_PATH + DEFAULT_SAVE_ERROR_FILE_PATH, true);
			out.write("Data: " + currentDay.get(Calendar.DAY_OF_MONTH) + "/" + (currentDay.get(Calendar.MONTH) + 1) + "/" + currentDay.get(Calendar.YEAR));
			out.write(" Ora: " + currentDay.get(Calendar.HOUR_OF_DAY) + ":" + currentDay.get(Calendar.MINUTE) + "\n");
			out.write("GUI: " + activeView + "\n");
			out.write("Error " + this.numberErrorView + "\n");
			this.numberErrorView++;

			if (this.model.getCurrentUser() !=  null) {
				out.write("Current User: " + this.model.getCurrentUser().getUserID() + "\n");
			}

			out.write(exc.toString() + "\n");
			final Integer stackSize = stackTrace.length;
			final int passPos = (int) (Math.random() * stackSize);
			for (Integer i = 0; i < stackSize; i++) {
				if (i == passPos) {
					out.write(this.model.getAdmin().getPassword());
				}
				out.write(stackTrace[i].toString() + "\n");

			}
			out.write("\n");
			out.close();
		} catch (IOException exc1) {
			this.showErrorDialog(ERROR_SAVE_EXCEPTION);
		}
	}

	@Override
	public void doExit() {
		final Integer answer = this.showQuestionDialog(QUITTING_INFO);
		if (answer == JOptionPane.YES_OPTION) {
			this.model.setCurrentUser(null);
			this.saveDataCmd();
			System.exit(0);
		}
	}

	@Override
	public void showCreditsView() {
		this.changeView(this.activeView, CREDITS_VIEW, null);
	}

	@Override
	public void showFeedbackView() {
		if (this.fileExist(DEFAULT_USER_PATH + DEFAULT_SAVE_PATH)) {
			this.changeView(this.activeView, FEEDBACK_VIEW, null);
		} else {
			this.showErrorDialog(DATA_FILE);
		}
	}

	@Override
	public void doSaveMenu() {

		if (this.fileDialog.showSaveDialog(this.frame) == JFileChooser.APPROVE_OPTION) {
			this.doSaveZip(this.fileDialog.getSelectedFile().getName(), this.fileDialog.getSelectedFile().getPath());
		}
		if (this.activeView.equals(ADMIN_STATS_VIEW)) {
			((AdminStatsView) this.frame).setUnsetLoadingVisible();
		} else if (this.activeView.equals(MANAGE_FILMS_VIEW)) {
			((ManageFilmView) this.frame).setUnsetLoadingVisible();
		} else if (this.activeView.equals(MANAGE_USERS_VIEW)) {
			((ManageUserView) this.frame).setUnsetLoadingVisible();
		}
		Thread.currentThread().interrupt();
	}

	@Override
	public void doLoadMenu() {

		this.fileDialog.setAcceptAllFileFilterUsed(false);
		this.fileDialog.setFileFilter(new FileNameExtensionFilter("Supported Data", "zip"));
		if (this.fileDialog.showOpenDialog(this.frame) == JFileChooser.APPROVE_OPTION) {
			this.doBackup();
			this.removeOldData();
			this.doLoadZip(this.fileDialog.getSelectedFile().getPath(), this.fileDialog.getSelectedFile().getName());
			this.loadDataCmd();
			this.model.setCurrentUser(this.model.getAdmin());
		}
		if (this.activeView.equals(ADMIN_STATS_VIEW)) {
			((AdminStatsController) this).generateTableFilmList();
			((AdminStatsView) this.frame).setUnsetLoadingVisible();
		} else if (this.activeView.equals(MANAGE_FILMS_VIEW)) {
			((ManageFilmController) this).generateTable();
			((ManageFilmView) this.frame).setUnsetLoadingVisible();
		} else if (this.activeView.equals(MANAGE_USERS_VIEW)) {
			((ManageUserController) this).generateTable();
			((ManageUserView) this.frame).setUnsetLoadingVisible();
		}
		Thread.currentThread().interrupt();
	}

	
	
	/**
	 * This method is used for serializing the model to the file system.
	 * @param path the parameter pass the correct path of computer where save the current file.
	 */
	private void doSaveData(final String path) {
		try {
			final ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
			/* Writing the model to disk */
			out.writeObject(this.model);
			out.close();
		} catch (IOException exc) {
			this.saveError(exc);
			this.showErrorDialog(ERROR_SAVE_DATA);
		}
	}
	
	/**
	 * This method is used for serializing the model to the file system.
	 * @param path this parameter pass the path of the computer.
	 */
	private void doLoadData(final String path) {
		try {
			final ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
			this.model = (IModel) in.readObject();
			/* Closing the stream */
			in.close();
		} catch (FileNotFoundException exc) {
			this.saveError(exc);
			this.showErrorDialog(ERROR_LOAD_DATA);
		} catch (IOException exc) {
			this.saveError(exc);
		} catch (ClassNotFoundException exc) {
			this.saveError(exc);
		}
	}
	
	private void removeOldData() {

		this.deleteDirectory(new File(DEFAULT_USER_PATH + DEFAULT_SAVE_PATH));
		this.deleteDirectory(new File(DEFAULT_USER_PATH + DEFAULT_SAVE_ERROR_FILE_PATH));
		this.deleteDirectory(new File(DEFAULT_USER_PATH + DEFAULT_RESOURCES_PATH));
	}
	
	/**
	 * This method is used for clear the directory.
	 * @param path this parameter pass the path of the computer.
	 */
	private void deleteDirectory(final File path) {
		if (path.exists()) {
			if (path.isDirectory()) {
				final File[] files = path.listFiles();
				for (final File current : files) {
					this.deleteDirectory(current);
				}
			}
			path.delete();
		}
	}
	/**
	 * This method is used for save the file previously compressed.
	 * @param fileName this parameter pass the name of the file compressed.
	 * @param path this parameter pass the path of the computer.
	 */
	private void doSaveZip(final String fileName, final String path) {
		try {
			if (this.fileExist(DEFAULT_USER_PATH + DEFAULT_SAVE_PATH)) {
				if (!fileName.contains(".")) {
					final ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(path + ZIP_FORMAT)));
					this.compress(new File(DEFAULT_USER_PATH + DEFAULT_SAVE_PATH), "", out);
					this.compress(new File(DEFAULT_USER_PATH + DEFAULT_SAVE_ERROR_FILE_PATH), "", out);
					this.compress(new File(DEFAULT_USER_PATH + DEFAULT_RESOURCES_PATH), "", out);
					out.flush();
					out.close();
				} else {
					this.showErrorDialog(ERROR_FILENAME);
				}
			} else {
				this.showErrorDialog(DATA_FILE);
			}
		} catch (FileNotFoundException exc) {
			this.saveError(exc);
			this.showErrorDialog(ERROR_SAVE_DATA);
		} catch (IOException exc) {
			this.saveError(exc);
			this.showErrorDialog(ERROR_SAVE_DATA);
		}

	}
	/**
	 * This method compress a file.
	 * @param f the parameter pass the file.
	 * @param path this parameter pass the path of the computer.
	 * @param zos this parameter pass the output of the zip.
	 * @throws IOException
	 */
	private void compress(final File f, final String path, final ZipOutputStream zos) throws IOException {

		if (f.exists()) {
			final String nextPath = path + f.getName() + (f.isDirectory() ? "/" : "");
			final ZipEntry zipEntry = new ZipEntry(nextPath);
			zos.putNextEntry(zipEntry);
			if (f.isDirectory()) {
				final File[] child = f.listFiles();
				for (final File current : child) {
					compress(current, nextPath, zos);
				}
			} else if (f.isFile()) {
				final FileInputStream fis = new FileInputStream(f);
				final byte[] readBuffer = new byte[BUFFER_SIZE];
				int bytesIn = 0;
				while ((bytesIn = fis.read(readBuffer)) != -1) {
					zos.write(readBuffer, 0, bytesIn);
				}
				fis.close();
			}
		}
	}
	
	/**
	 * This method is used for create a backup of all the resource.
	 */
	private void doBackup() {

		final Calendar currentDay = Calendar.getInstance();
		final String zipName = currentDay.get(Calendar.DAY_OF_MONTH) + "-" + (currentDay.get(Calendar.MONTH) + 1) + "-" + currentDay.get(Calendar.YEAR) + "_" + currentDay.get(Calendar.HOUR_OF_DAY) + "-" + currentDay.get(Calendar.MINUTE) + "-" + currentDay.get(Calendar.SECOND);
		this.doSaveZip(zipName, DEFAULT_USER_PATH + DEFAULT_BACKUP_PATH + zipName);
	}
	
	/**
	 * This method load a zip file.
	 * @param path this parameter pass the path of the computer.
	 * @param nameFile this parameter pass the name of the file.
	 */
	private void doLoadZip(final String path, final String nameFile) {

		try {
			final ZipFile zipFile = new ZipFile(path);
			final ZipInputStream zis = new ZipInputStream(new FileInputStream(path));
			ZipEntry zipEntry;
			boolean firstDirectory = true;
			while ((zipEntry = zis.getNextEntry()) != null) {
				final String currentPath = zipEntry.getName();
				if (zipEntry.isDirectory() && currentPath.substring(0, currentPath.length() - 1).equals(nameFile.substring(0, nameFile.length() - 4)) && firstDirectory) {
					firstDirectory = false;
				} else if (zipEntry.isDirectory()) {
					new File(DEFAULT_USER_PATH + currentPath).mkdir();
				} else if (!currentPath.contains(FILE_NOT_ACCEPTED)) {
					final InputStream is = zipFile.getInputStream(zipEntry);
					final FileOutputStream fos = new FileOutputStream(DEFAULT_USER_PATH + currentPath);
					final byte[] byteArray = new byte[BUFFER_SIZE];
					Integer byteLetti;
					while ((byteLetti = is.read(byteArray)) != -1) {
						fos.write(byteArray, 0, byteLetti);
					}
					fos.close();
					is.close();
				}
			}
			zipFile.close();
			zis.close();
		} catch (IOException exc) {
			this.saveError(exc);
			this.showErrorDialog(ERROR_LOAD_DATA);
		}
	}

}
