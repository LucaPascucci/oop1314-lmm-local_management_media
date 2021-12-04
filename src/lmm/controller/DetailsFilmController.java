package lmm.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import lmm.view.DetailsFilmView;
import lmm.controller.interfaces.IDetailsFilmController;
import lmm.exception.DuplicateBoughtException;
import lmm.exception.UserNotFoundException;
import lmm.mediaworker.MediaPlayer;
import lmm.model.IFilm;
import lmm.model.IModel;
/**
 * This class manage the functions of the details of a film.
 * @author Roberto Reibaldi,Luca Pascucci
 *
 */
public class DetailsFilmController extends MainController implements IDetailsFilmController {

	private static final String DUPLICATE_BUY = "This film is already buyed!";
	private static final String SUCCESS_BUY = "Movie purchased!";
	private static final String SUCCESS_ZIP = "Zip file created!";
	private static final String ERROR_ZIP = "Error during creation Zip file";
	private static final String ERROR_FILENAME = "File name can't contain this char (.)";
	private static final String INFO_COVER = "This film contain only default cover";
	private static final String NO_TRAILER = "This film don't have a trailer";

	private static final String DEFAULT_PATH = DEFAULT_USER_PATH + DEFAULT_RESOURCES_PATH + "films/";
	private static final String VIDEO_FORMAT = ".flv";

	private Thread secondaryThread = new Thread();
	private MediaPlayer viewTrailer;
	private int indexCover = -1;
	private Vector<String> covers;

	private final Integer filmCode;
	private final Integer returnView;
	private IFilm currentFilm;
	private boolean userControll;

	/**
	 * This is the constructor of the class.
	 * @param mod this parameter pass the model to the constructor.
	 * @param view this parameter pass the correct view that will be shown.
	 * @param film this parameter pass the code of the film that will be shown.
	 */
	public DetailsFilmController(final IModel mod, final Integer view, final Integer film) {
		super(mod, DETAILS_FILM_VIEW);
		this.filmCode = film;
		this.returnView = view;

		if (this.model.getCurrentUser().getUserID().equals(this.model.getAdmin().getUserID())) {
			this.userControll = true;
		} else {
			this.userControll = false;
		}
	}

	@Override
	public void setView(final JFrame f) {
		this.frame = f;
		((DetailsFilmView) this.frame).attachObserver(this);
	}

	@Override
	public void doBack() {
		if (!this.controllThread()) {
			this.changeView(this.activeView, this.returnView, null);
		}
	}

	@Override
	public void doDownloadCover() {
		if (!this.covers.contains(DEFAULT_COVER)) {
			if (this.fileDialog.showSaveDialog(this.frame) == JFileChooser.APPROVE_OPTION) {
				this.downloadCovers(this.fileDialog.getSelectedFile().getPath(), this.fileDialog.getSelectedFile());
				this.showInfoDialog(SUCCESS_ZIP);
			}
		} else {
			this.showInfoDialog(INFO_COVER);
		}
	}

	@Override
	public void doTrailerView() {
		if (this.model.getFilm(filmCode).getTrailer()) {
			this.viewTrailer = new MediaPlayer(DEFAULT_PATH + filmCode + VIDEO_FORMAT);
			if (!this.secondaryThread.isAlive()) {
				this.secondaryThread = new Thread() {
					public void run() {
						viewTrailer.startTrailer(currentFilm.getTitle());
					}
				};
				this.secondaryThread.start();
			}
		} else {
			this.showInfoDialog(NO_TRAILER);
		}

	}
	
	@Override
	public boolean controllThread() {
		return this.secondaryThread.isAlive();
	}

	@Override
	public void doBuy() {
		try {
			this.model.addBoughtFilms(filmCode);
			this.model.boughtFilm(filmCode);
			this.saveDataCmd();
			this.showInfoDialog(SUCCESS_BUY);
		} catch (DuplicateBoughtException exc) {
			this.saveError(exc);
			this.showWarningDialog(DUPLICATE_BUY);
		}
	}

	@Override
	public boolean doFavorite() {
		try {
			this.model.addFavoritesFilms(filmCode);
			return true;
		} catch (UserNotFoundException exc) {
			this.saveError(exc);
			this.showErrorDialog(ERROR_LOADING);
		}
		return false;
	}

	@Override
	public IFilm getFilm() {
		currentFilm = this.model.getFilm(filmCode);
		this.covers =  currentFilm.getImages();
		return currentFilm;
	}

	@Override
	public boolean getUserControll() {
		return this.userControll;
	}

	@Override
	public void doVisit() {
		this.model.visitedFilm(this.filmCode);
		this.saveDataCmd();
	}

	@Override
	public Icon getCover() {
		if (this.covers.contains(DEFAULT_COVER)) {
			return new ImageIcon(this.getClass().getResource("/" + this.covers.firstElement())); 
		} else {
			if (this.indexCover == -1) {
				this.indexCover = 0;
				return new ImageIcon(DEFAULT_PATH + this.covers.firstElement());
			} else if (this.covers.size() > this.indexCover + 1) {
				this.indexCover++;
				return new ImageIcon(DEFAULT_PATH + this.covers.get(this.indexCover));
			} else {
				this.indexCover = 0;
				return new ImageIcon(DEFAULT_PATH + this.covers.get(this.indexCover)); 
			}
		}
	}

	@Override
	public String getFIlmID() {
		return this.filmCode.toString();
	}

	@Override
	public boolean isFavorite() {
		try {
			if (this.model.getFavoritesFilms(this.model.getCurrentUser().getUserID()).contains(this.filmCode)) {
				return true;
			}
		} catch (UserNotFoundException exc) {
			this.saveError(exc);
		}
		return false;
	}

	@Override
	public boolean deleteFavorite() {
		try {
			this.model.removeFavoriteFilm(this.filmCode);
			this.saveDataCmd();
			return false;
		} catch (UserNotFoundException exc) {
			this.saveError(exc);
		}
		return true;
	}
	
	/**
	 * This Method downloads the covers of a film selected by the parameter.
	 * @param directory this parameter pass the path where will be found the file. 
	 * @param filePath this parameter identify the file in the computer.
	 */
	private void downloadCovers(final String directory, final File filePath) {
		try {
			final String fileName = filePath.getName();
			if (!fileName.contains(".")) {
				// definiamo l'output previsto che sara'  un file in formato zip
				final ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(directory + ZIP_FORMAT)));

				// definiamo il buffer per lo stream di bytes
				final byte[] data = new byte[BUFFER_SIZE];
				for (final String currFile : this.covers) {
					final BufferedInputStream in = new BufferedInputStream(new FileInputStream(DEFAULT_PATH + currFile));
					int count;

					// processo di compressione
					out.putNextEntry(new ZipEntry(currFile));
					while ((count = in.read(data)) > 0) {
						out.write(data, 0, count);
					}
					in.close();
				}
				out.flush();
				out.close();
			} else {
				this.showErrorDialog(ERROR_FILENAME);
			}
		} catch (FileNotFoundException exc) {
			this.saveError(exc);
			this.showErrorDialog(ERROR_ZIP);
		} catch (IOException exc) {
			this.saveError(exc);
			this.showErrorDialog(ERROR_ZIP);
		}

	}

}
