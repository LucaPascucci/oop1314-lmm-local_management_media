package lmm.controller.admin;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import lmm.controller.MainController;
import lmm.controller.interfaces.IManageFilmController;
import lmm.exception.InvalidContentException;
import lmm.exception.NoVideoIconException;
import lmm.exception.UnsupportedCodecException;
import lmm.mediaworker.VideoFilter;
import lmm.mediaworker.VideoResizer;
import lmm.model.IFilm;
import lmm.model.IModel;
import lmm.view.admin.ManageFilmView;
/**
 * This class manage the editing of the data of a film.
 * @author Roberto Reibaldi, Luca Pascucci
 *
 */
public class ManageFilmController extends MainController implements IManageFilmController {

	private static final String VIDEO_FORMAT = ".flv";
	private static final String BASE_PATH = DEFAULT_USER_PATH + DEFAULT_RESOURCES_PATH + "films/";
	private static final String TEMPORARY_PATH = DEFAULT_USER_PATH + DEFAULT_RESOURCES_PATH + "tmp";
	private static final String DEFAULT_FORMAT = "png";
	private static final String[] IMAGE_FORMAT = new String[]{"jpg", "bmp", "jpeg", "wbmp", "png", "gif"};

	private static final String DELETE_FILM = "Do you really delete this film?";
	private static final String TRAILER_INFO = "This film have already a trailer!\nContinue? ( Previous will be eliminated! )";
	private static final String SELECT_IMAGE = "Select a image file";
	private static final String COVER_ADDED = "Cover added succesfully!";
	private static final String TRAILER_ADDED = "Trailer added succesfully!";
	private static final String DEFAULT_FILTER = "Supported Format";
	private static final String UNSUPPORTED_CODEC = "Video Resizer doesn't support an audio or video codec!";
	private static final String ERROR_VIDEO_ICON = "Cannot complete adding video icon";

	private Thread secondaryThread = new Thread();
	private VideoResizer videoResizer;

	/**
	 * This is the constructor of the class.
	 * @param mod this parameter pass the model to the constructor.
	 */
	public ManageFilmController(final IModel mod) {
		super(mod, MANAGE_FILMS_VIEW);
	}

	@Override
	public void setView(final JFrame f) {
		this.frame = f;
		((ManageFilmView) this.frame).attachObserver(this);

	}

	@Override
	public void doBack() {
		this.changeView(this.activeView, MENU_VIEW, null);

	}

	@Override
	public void doAddCover(final Integer filmCode) {
		try {
			final Integer nextImage = this.model.nextFilmCover(filmCode);
			this.fileDialog.setAcceptAllFileFilterUsed(false);
			this.fileDialog.setFileFilter(new FileNameExtensionFilter("Supported Images", IMAGE_FORMAT));
			if (this.fileDialog.showOpenDialog(this.frame) == JFileChooser.APPROVE_OPTION) {
				final BufferedImage img = ImageIO.read(new File(this.fileDialog.getSelectedFile().getPath()));
				final BufferedImage scaledImage = this.getScaledImage(img);

				final File saveFile = new File(BASE_PATH + filmCode + "." + nextImage + "." + DEFAULT_FORMAT);

				ImageIO.write(scaledImage, DEFAULT_FORMAT, saveFile);
				this.model.addFilmCover(filmCode, nextImage);
				this.saveDataCmd();
				this.showInfoDialog(COVER_ADDED);
			}
		} catch (IOException exc) {
			this.saveError(exc);
			this.showInfoDialog(SELECT_IMAGE);
		} catch (InvalidContentException exc) {
			this.saveError(exc);
		}

	}

	@Override
	public void doAddMovie() {
		this.changeView(this.activeView, EDITABLE_FILM_VIEW, null);
	}

	@Override
	public void doAddTrailer(final Integer filmCode) {
		try {
			if (this.model.checkTrailer(filmCode)) {
				final Integer answer = this.showQuestionDialog(TRAILER_INFO);
				if (answer == JOptionPane.YES_OPTION) {
					final VideoFilter filter = new VideoFilter(DEFAULT_FILTER);
					this.fileDialog.setAcceptAllFileFilterUsed(false);
					this.fileDialog.setFileFilter(filter);
					if (this.fileDialog.showOpenDialog(this.frame) == JFileChooser.APPROVE_OPTION) {
						((ManageFilmView) this.frame).setUnsetLoadingVisible();
						this.videoResizer = new VideoResizer(fileDialog.getSelectedFile().getPath(), TEMPORARY_PATH + VIDEO_FORMAT, this);
						this.secondaryThread = new Thread() {
							public void run() {
								try {
									videoResizer.startEncoding(BASE_PATH + filmCode + VIDEO_FORMAT, filmCode);
								} catch (UnsupportedCodecException exc) {
									saveError(exc);
									showErrorDialog(UNSUPPORTED_CODEC);
									secondaryThread.interrupt();
								} catch (NoVideoIconException exc) {
									saveError(exc);
									showErrorDialog(ERROR_VIDEO_ICON);
									secondaryThread.interrupt();
								}
							}
						};
						this.secondaryThread.start();
					}

				}
			} else {
				final VideoFilter filter = new VideoFilter(DEFAULT_FILTER);
				this.fileDialog.setAcceptAllFileFilterUsed(false);
				this.fileDialog.setFileFilter(filter);
				if (this.fileDialog.showOpenDialog(this.frame) == JFileChooser.APPROVE_OPTION) {
					((ManageFilmView) this.frame).setUnsetLoadingVisible();
					this.videoResizer = new VideoResizer(this.fileDialog.getSelectedFile().getPath(), TEMPORARY_PATH + VIDEO_FORMAT, this);
					this.secondaryThread = new Thread() {
						public void run() {
							try {
								videoResizer.startEncoding(BASE_PATH + filmCode + VIDEO_FORMAT, filmCode);
							} catch (UnsupportedCodecException exc) {
								saveError(exc);
								showErrorDialog(UNSUPPORTED_CODEC);
								secondaryThread.interrupt();
							} catch (NoVideoIconException exc) {
								saveError(exc);
								showErrorDialog(ERROR_VIDEO_ICON);
								secondaryThread.interrupt();
							}
						}
					};
					this.secondaryThread.start();
				}

			}
		} catch (InvalidContentException exc) {
			this.saveError(exc);
		}
	}

	@Override
	public boolean doDeleteFilm(final Integer filmCode) {
		try {
			final Integer answer = this.showQuestionDialog(DELETE_FILM);
			if (answer == JOptionPane.YES_OPTION) {
				this.model.deleteFilm(filmCode);
				this.saveDataCmd();
			}
			return true;
		} catch (InvalidContentException exc) {
			this.saveError(exc);
		}
		return false;
	}

	@Override
	public void doEditMovie(final Integer filmCode) {
		this.changeView(this.activeView, EDITABLE_FILM_VIEW, filmCode);

	}

	@Override
	public void showDetailsFilm(final Integer filmCode) {
		this.changeView(this.activeView, DETAILS_FILM_VIEW, filmCode);

	}

	@Override
	public void generateTable() {
		final Iterator<Integer> it = this.model.getOrderFilm().iterator();
		Integer curr;
		while (it.hasNext()) {
			curr = it.next();
			final IFilm film = this.model.getFilm(curr);
			final Object[] obj = new Object[] {curr, film.getTitle(), film.getGenre(), film.getDate(), film.getPrice()};
			((ManageFilmView) this.frame).newRow(obj);
		}

	}

	@Override
	public void returnEncoding(final boolean check, final Integer filmCode) {
		try {
			Toolkit.getDefaultToolkit().beep();
			if (check) {
				this.showInfoDialog(TRAILER_ADDED);
				this.secondaryThread.interrupt();
				this.model.addFilmTrailer(filmCode);
				this.saveDataCmd();
			}
			((ManageFilmView) this.frame).setUnsetLoadingVisible();
		} catch (InvalidContentException exc) {
			this.saveError(exc);
		}

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
	
	/**
	 * This method returns a resized BufferedImage
	 * @param originalImage
	 * @return BufferedImage
	 */
	private BufferedImage getScaledImage(final BufferedImage originalImage) {

		final int maxWidth = 300;
		final int maxHeight = 300;

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