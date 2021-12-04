package lmm.controller.user;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import lmm.controller.MainController;
import lmm.controller.interfaces.IUserStatsController;
import lmm.exception.UserNotFoundException;
import lmm.model.IFilm;
import lmm.model.IModel;
import lmm.model.User;
import lmm.view.user.UserStatsView;
/**
 * This class manage the statistics of the user.
 * @author Roberto Reibaldi, Luca Pascucci
 *
 */
public class UserStatsController extends MainController implements IUserStatsController {

	private static final String DELETE_FILM = "Do you really delete this film?";
	private static final String[] IMAGE_FORMAT = new String[]{"jpg", "bmp", "jpeg", "wbmp", "png", "gif"};

	private final String userId;
	private User currUser;
	/**
	 * This is the constructor of the class.
	 * @param mod this parameter pass the model to the constructor.
	 * @param username this parameter pass the user by the userId.
	 */
	public UserStatsController(final IModel mod, final String username) {
		super(mod, USER_STATS_VIEW);
		this.userId = username;
	}

	@Override
	public void setView(final JFrame f) {
		this.frame = f;
		((UserStatsView) this.frame).attachObserver(this);

	}
	/**
	 * This method set the current user.
	 * @return {@link lmm.model.User}
	 */
	public User getUser() {
		try {
			if (this.userId != null) {
				currUser = this.model.getUser(userId);
				((UserStatsView) this.frame).setEditableVisible();
				return currUser;
			} else {
				currUser = this.model.getUser(this.model.getCurrentUser().getUserID());
				return currUser;
			}
		} catch (UserNotFoundException exc) {
			this.saveError(exc);
			this.showErrorDialog(ERROR_LOADING);
		}
		return null;

	}

	@Override
	public String setNumFilm() {
		final Integer val = this.model.getFilmBought(this.currUser.getUserID()).size();
		return val.toString();

	}

	@Override
	public String setPurchasedFilm() {
		return this.model.getTotPurchasedUser(this.currUser.getUserID()).toString();
	}

	@Override
	public void showDetailsFilm(final Integer filmCode) {
		this.changeView(this.activeView, DETAILS_FILM_VIEW, filmCode);
	}

	@Override
	public void doBack() {
		if (!this.checkUser()) {
			this.changeView(this.activeView, MANAGE_USERS_VIEW, null);
		} else {
			this.changeView(this.activeView, MENU_VIEW , null);
		}
	}

	@Override
	public boolean doDelete(final Integer filmCode) {
		final Integer answer = this.showQuestionDialog(DELETE_FILM);
		if (answer == JOptionPane.YES_OPTION) {
			this.model.deleteFilmUser(this.currUser.getUserID(), filmCode);
			((UserStatsView) this.frame).setFilmVal();
			((UserStatsView) this.frame).setPurchasedVal();
			this.saveDataCmd();
			return true;
		}
		return false;
	}

	@Override
	public void doEditUser() {
		this.changeView(this.activeView, EDITABLE_USER_VIEW, this.currUser.getUserID());
	}

	@Override
	public void generateTable() {
		final Iterator<Integer> it = this.model.getFilmBought(this.currUser.getUserID()).iterator();
		Integer curr;
		while (it.hasNext()) {
			curr = it.next();
			final IFilm film = this.model.getFilm(curr);
			final Object[] obj = new Object[] {curr, film.getTitle(), film.getGenre(), film.getDate(), film.getPrice()};
			((UserStatsView) this.frame).newRow(obj);
		}

	}
	
	@Override
	public boolean checkUser() {
		return this.userId == null ? true : false;
	}

	@Override
	public boolean checkProfileImage() {
		return this.currUser.getProfileImage() == DEFAULT_IMAGE;
	}

	@Override
	public ImageIcon getProfileImage() {
		if (this.checkProfileImage()) {
			return new ImageIcon(this.getClass().getResource("/" + currUser.getProfileImage()));
		} else {
			return new ImageIcon(DEFAULT_USER_PATH + DEFAULT_RESOURCES_PATH + "users/" + currUser.getProfileImage());
		}
	}

	@Override
	public void backToHome() {
		if (!this.checkUser()) {
			this.changeView(this.activeView, MANAGE_USERS_VIEW, null);
		} else {
			this.showInfoDialog(DATA_SAVING_STR);
			this.changeView(this.activeView, LOGIN_VIEW, null);
		}
	}

	@Override
	public boolean changeProfileImage() {
		try {
			this.fileDialog.setAcceptAllFileFilterUsed(false);
			this.fileDialog.setFileFilter(new FileNameExtensionFilter("Supported Images", IMAGE_FORMAT));
			if (this.fileDialog.showOpenDialog(this.frame) == JFileChooser.APPROVE_OPTION) {
				final BufferedImage img = ImageIO.read(new File(this.fileDialog.getSelectedFile().getPath()));
				final BufferedImage scaledImage = this.getScaledImage(img);

				final File saveFile = new File(DEFAULT_USER_PATH + DEFAULT_RESOURCES_PATH + "users/" + currUser.getUserID() + "." + "png");
				ImageIO.write(scaledImage, "png", saveFile);
				this.model.editUser(currUser, this.currUser.getUserID());
				this.getUser();
				this.saveDataCmd();
				return true;
			}
		} catch (IOException exc) {
			this.saveError(exc);
		}
		return false;

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
