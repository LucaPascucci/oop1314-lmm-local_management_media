package lmm.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

import lmm.controller.DetailsFilmController;
import lmm.model.IFilm;

/**
 * This class shows the details of the selected film.
 * @author Filippo Nicolini
 *
 */
public class DetailsFilmView extends AbstractView {

	private static final long serialVersionUID = 1;

	private static final Integer DOUBLE_VERTICAL_GAP = 20;
	private static final Integer SIMPLE_VERTICAL_GAP = 8;
	private static final Integer HORIZONTAL_GAP = 25;
	private static final Integer CENTER_ALIGNMENT = 75;
	private static final Integer COVER_SIZE = 300;
	private static final Integer BUTTON_GAP = 15;
	private static final Integer CHAR_SIZE = 16;

	private final JLabel emptyStar = new JLabel(new ImageIcon(this.getClass().getResource("/Empty_Star.png")));
	private final JLabel fullStar = new JLabel(new ImageIcon(this.getClass().getResource("/Full_Star.png")));
	private final JLabel downloadCovers = new JLabel(new ImageIcon(this.getClass().getResource("/Download_Icon.png")));

	private final JLabel lblCodFilm = new JLabel("Code Film:");
	private final JLabel lblTitle = new JLabel("Title:");
	private final JLabel lblGenre = new JLabel("Genre:");
	private final JLabel lblReleaseYear = new JLabel("Release Year:");
	private final JLabel lblPrice = new JLabel("Price:");
	private final JLabel lblPlot = new JLabel("Plot");
	private final JLabel codFilm = new JLabel();
	private final JLabel title = new JLabel();
	private final JLabel genre = new JLabel();
	private final JLabel releaseYear = new JLabel();
	private final JLabel price = new JLabel();
	private final JLabel cover = new JLabel();
	private final JTextArea plot = new JTextArea(12, 40); //JTextArea size: 12 width, 40 height.
	private final JScrollPane scrollPane = new JScrollPane(this.plot);
	private final JButton back = new JButton("Back");
	private final JButton buy = new JButton("Buy Film");
	private final JButton trailer = new JButton("View Trailer");

	private DetailsFilmController observer;

	/**
	 * This constructor creates the view of DetailsFilmView.
	 */
	public DetailsFilmView() {

		super();

		this.setTitle("Info Film");
		this.setResizable(false);
		this.setSize(500, 700); //frame size: 500 width, 700 height.
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		final SpringLayout layout = new SpringLayout();
		this.setLayout(layout);

		this.lblCodFilm.setFont(new Font(null, Font.BOLD, CHAR_SIZE));
		this.lblTitle.setFont(new Font(null, Font.BOLD, CHAR_SIZE));
		this.lblGenre.setFont(new Font(null, Font.BOLD, CHAR_SIZE));
		this.lblReleaseYear.setFont(new Font(null, Font.BOLD, CHAR_SIZE));
		this.lblPrice.setFont(new Font(null, Font.BOLD, CHAR_SIZE));
		this.lblPlot.setFont(new Font(null, Font.BOLD, CHAR_SIZE));
		this.cover.setToolTipText("One Click to view other images");

		this.add(this.lblCodFilm);
		layout.putConstraint(SpringLayout.NORTH, this.lblCodFilm, DOUBLE_VERTICAL_GAP, SpringLayout.NORTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.lblCodFilm, HORIZONTAL_GAP, SpringLayout.WEST, this.getContentPane());

		this.add(this.codFilm);
		layout.putConstraint(SpringLayout.NORTH, this.codFilm, SIMPLE_VERTICAL_GAP, SpringLayout.SOUTH, this.lblCodFilm);
		layout.putConstraint(SpringLayout.WEST, this.codFilm, HORIZONTAL_GAP, SpringLayout.WEST, this.getContentPane());

		this.add(this.lblTitle);
		layout.putConstraint(SpringLayout.NORTH, this.lblTitle, DOUBLE_VERTICAL_GAP, SpringLayout.SOUTH, this.codFilm);
		layout.putConstraint(SpringLayout.WEST, this.lblTitle, HORIZONTAL_GAP, SpringLayout.WEST, this.getContentPane());

		this.add(this.title);
		layout.putConstraint(SpringLayout.NORTH, this.title, SIMPLE_VERTICAL_GAP, SpringLayout.SOUTH, this.lblTitle);
		layout.putConstraint(SpringLayout.WEST, this.title, HORIZONTAL_GAP, SpringLayout.WEST, this.getContentPane());

		this.add(this.lblGenre);
		layout.putConstraint(SpringLayout.NORTH, this.lblGenre, DOUBLE_VERTICAL_GAP, SpringLayout.SOUTH, this.title);
		layout.putConstraint(SpringLayout.WEST, this.lblGenre, HORIZONTAL_GAP, SpringLayout.WEST, this.getContentPane());

		this.add(this.genre);
		layout.putConstraint(SpringLayout.NORTH, this.genre, SIMPLE_VERTICAL_GAP, SpringLayout.SOUTH, this.lblGenre);
		layout.putConstraint(SpringLayout.WEST, this.genre, HORIZONTAL_GAP, SpringLayout.WEST, this.getContentPane());

		this.add(this.lblReleaseYear);
		layout.putConstraint(SpringLayout.NORTH, this.lblReleaseYear, DOUBLE_VERTICAL_GAP, SpringLayout.SOUTH, this.genre);
		layout.putConstraint(SpringLayout.WEST, this.lblReleaseYear, HORIZONTAL_GAP, SpringLayout.WEST, this.getContentPane());

		this.add(this.releaseYear);
		layout.putConstraint(SpringLayout.NORTH, this.releaseYear, SIMPLE_VERTICAL_GAP, SpringLayout.SOUTH, this.lblReleaseYear);
		layout.putConstraint(SpringLayout.WEST, this.releaseYear, HORIZONTAL_GAP, SpringLayout.WEST, this.getContentPane());

		this.add(this.lblPrice);
		layout.putConstraint(SpringLayout.NORTH, this.lblPrice, DOUBLE_VERTICAL_GAP, SpringLayout.SOUTH, this.releaseYear);
		layout.putConstraint(SpringLayout.WEST, this.lblPrice, HORIZONTAL_GAP, SpringLayout.WEST, this.getContentPane());

		this.add(this.price);
		layout.putConstraint(SpringLayout.NORTH, this.price, SIMPLE_VERTICAL_GAP, SpringLayout.SOUTH, this.lblPrice);
		layout.putConstraint(SpringLayout.WEST, this.price, HORIZONTAL_GAP, SpringLayout.WEST, this.getContentPane());

		this.add(this.trailer);
		layout.putConstraint(SpringLayout.NORTH, this.trailer, HORIZONTAL_GAP, SpringLayout.SOUTH, this.cover);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.trailer, -CENTER_ALIGNMENT, SpringLayout.HORIZONTAL_CENTER, this.cover);

		this.add(this.downloadCovers);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, this.downloadCovers, 0, SpringLayout.VERTICAL_CENTER, this.trailer);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.downloadCovers, CENTER_ALIGNMENT, SpringLayout.HORIZONTAL_CENTER, this.cover);

		this.add(this.emptyStar);
		layout.putConstraint(SpringLayout.WEST, this.emptyStar, HORIZONTAL_GAP, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, this.emptyStar, 0, SpringLayout.VERTICAL_CENTER, this.trailer);

		this.add(this.fullStar);
		layout.putConstraint(SpringLayout.WEST, this.fullStar, HORIZONTAL_GAP, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, this.fullStar, 0, SpringLayout.VERTICAL_CENTER, this.trailer);
		this.fullStar.setVisible(false);

		this.add(this.lblPlot);
		layout.putConstraint(SpringLayout.NORTH, this.lblPlot, DOUBLE_VERTICAL_GAP, SpringLayout.SOUTH, this.trailer);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.lblPlot, 0, SpringLayout.HORIZONTAL_CENTER, this.getContentPane());
		this.plot.setEditable(false);
		this.plot.setLineWrap(true);

		this.add(this.scrollPane);
		layout.putConstraint(SpringLayout.NORTH, this.scrollPane, SIMPLE_VERTICAL_GAP, SpringLayout.SOUTH, this.lblPlot);
		layout.putConstraint(SpringLayout.WEST, this.scrollPane, HORIZONTAL_GAP, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.scrollPane, -HORIZONTAL_GAP, SpringLayout.EAST, this.getContentPane());
		layout.putConstraint(SpringLayout.SOUTH, this.scrollPane, 200, SpringLayout.NORTH, this.scrollPane);
		//200 is the vertical size of the scrollPane

		this.add(this.cover);
		layout.putConstraint(SpringLayout.NORTH, this.cover, HORIZONTAL_GAP, SpringLayout.NORTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.cover, -COVER_SIZE, SpringLayout.EAST, this.cover);
		layout.putConstraint(SpringLayout.EAST, this.cover, -HORIZONTAL_GAP, SpringLayout.EAST, this.getContentPane());
		layout.putConstraint(SpringLayout.SOUTH, this.cover, COVER_SIZE, SpringLayout.NORTH, this.cover);

		this.add(this.back);
		layout.putConstraint(SpringLayout.SOUTH, this.back, -BUTTON_GAP, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.back, -HORIZONTAL_GAP, SpringLayout.EAST, this.getContentPane());

		this.add(this.buy);
		layout.putConstraint(SpringLayout.SOUTH, this.buy, -BUTTON_GAP, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.buy, -HORIZONTAL_GAP, SpringLayout.WEST, this.back);

		this.back.addActionListener(this);
		this.buy.addActionListener(this);
		this.trailer.addActionListener(this);
		this.cover.addMouseListener(this);
		this.emptyStar.addMouseListener(this);
		this.fullStar.addMouseListener(this);
		this.downloadCovers.addMouseListener(this);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(final WindowEvent e) {
				if (observer.getUserControll()) {
					buy.setVisible(false);
					emptyStar.setVisible(false);
				} else {
					observer.doVisit();
					if (observer.isFavorite()) {
						emptyStar.setVisible(false);
						fullStar.setVisible(true);
					}
				}
				final IFilm current = observer.getFilm();
				codFilm.setText(observer.getFIlmID());
				title.setText(current.getTitle());
				releaseYear.setText(current.getDate().toString());
				genre.setText(current.getGenre().toString());
				plot.setText(current.getPlot());
				price.setText(current.getPrice().toString());
				cover.setIcon(observer.getCover());
			}

			@Override
			public void windowClosing(final WindowEvent e) {
				observer.doBack();

			}
		});
		this.setLocation();
		this.setIcon();
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		if (!this.observer.controllThread()) {
			final Object selection = e.getSource();
			if (selection.equals(this.back)) {
				this.observer.doBack();
			} else if (selection.equals(this.buy)) {
				this.observer.doBuy();
			} else if (selection.equals(this.trailer)) {
				this.observer.doTrailerView();
			}
		}

	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		if (!this.observer.controllThread()) {
			if (e.getClickCount() == 1 && !e.isConsumed() && e.getComponent().equals(this.cover)) {
				e.consume();
				this.cover.setIcon(this.observer.getCover());
			} else if (e.getClickCount() == 1 && !e.isConsumed() && e.getComponent().equals(this.emptyStar)) {
				e.consume();
				this.changeStar(this.observer.doFavorite());
			} else if (e.getClickCount() == 1 && !e.isConsumed() && e.getComponent().equals(this.fullStar)) {
				e.consume();
				this.changeStar(this.observer.deleteFavorite());	
			} else if (e.getClickCount() == 1 && !e.isConsumed() && e.getComponent().equals(this.downloadCovers)) {
				this.observer.doDownloadCover();
			}
		}

	}
	/**
	 * Attach the observer of the controller to the view.
	 * @param detailsFilmController controller for this view
	 */
	public void attachObserver(final DetailsFilmController detailsFilmController) {
		this.observer = detailsFilmController;
	}

	/**
	 * Set the star compared to the status of favorite of the film
	 * @param star
	 */
	private void changeStar(final boolean star) {
		if (star) {
			this.emptyStar.setVisible(false);
			this.fullStar.setVisible(true);
		} else {
			this.emptyStar.setVisible(true);
			this.fullStar.setVisible(false);
		}

	}

}
