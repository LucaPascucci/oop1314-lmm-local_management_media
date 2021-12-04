package lmm.view.user;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import lmm.controller.user.FavoriteFilmController;
import lmm.model.IFilm;
import lmm.view.AbstractView;
/**
 * Class that show the films that user has selected as a favorite.
 * @author Filippo Nicolini, Marco Sperandeo
 *
 */
public class FavoriteFilmView extends AbstractView {

	private static final long serialVersionUID = 1;

	private static final String[] COLUMN_NAME_FILM = new String[]{"Cod", "Title", "Genre", "Year", "Price"};

	private static final String SELECT_FILM = "Select a film!";

	private static final Integer MENU_IDX_CONTACT_US = 0;
	private static final Integer MENU_IDX_ABOUT = 1;

	private static final Integer PANEL_MINIMUM_GAP = 5;
	private static final Integer MEDIUM_GAP = 15;
	private static final Integer BUTTON_BORDER = 50;

	private final JMenuItem fileContainedMenu;
	private final JMenuItem[] helpMenuItem;

	private final JButton back = new JButton("Back");
	private final JButton buy = new JButton("Buy Now");
	private final JButton delete = new JButton("Delete Favorite");
	private final JButton details = new JButton("Details Film");
	private final JTable table;

	private final Object[][] tableData = new Object[][]{};

	private final JPanel previewPanel = new JPanel();
	private final JLabel lblCover = new JLabel();
	private final JLabel lblTitle = new JLabel();
	private final JLabel lblGenre = new JLabel();
	private final JLabel lblReleaseYear = new JLabel();
	private final JLabel lblPrice = new JLabel();

	private FavoriteFilmController observer;
	/**
	 * Create a new FavoriteFilmView.
	 */
	public FavoriteFilmView() {

		super();

		this.setTitle("Favorite Films");
		this.setResizable(false);
		this.setSize(850, 500); //frame size: 850 width, 500 height;
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		final SpringLayout layout = new SpringLayout();
		this.setLayout(layout);

		JMenuBar bar;
		JMenu fileMenu;
		JMenu helpMenu;
		JScrollPane scroll;

		bar = new JMenuBar();
		fileMenu = new JMenu("File");
		helpMenu = new JMenu("Help");
		this.fileContainedMenu = new JMenuItem("Exit");
		fileMenu.add(this.fileContainedMenu);
		this.fileContainedMenu.addActionListener(this);
		this.helpMenuItem = new JMenuItem[2];
		this.helpMenuItem[MENU_IDX_CONTACT_US] = new JMenuItem("Contact Us");
		this.helpMenuItem[MENU_IDX_ABOUT] = new JMenuItem("About");
		for (int i = 0; i < 2; i++) {
			helpMenu.add(this.helpMenuItem[i]);
			this.helpMenuItem[i].addActionListener(this);	
		}

		bar.add(fileMenu);
		bar.add(helpMenu);
		setJMenuBar(bar);

		this.table = new JTable(new DefaultTableModel(tableData, COLUMN_NAME_FILM) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(final int row, final int column) {
				return false;
			}

		});
		this.table.getColumn("Cod").setPreferredWidth(40); //set the column size to 40
		this.table.getColumn("Title").setPreferredWidth(315); //set the column size to 315
		this.table.getColumn("Genre").setPreferredWidth(125); //set the column size to 125
		this.table.getColumn("Year").setPreferredWidth(50); //set the column size to 50
		this.table.getColumn("Price").setPreferredWidth(52); //set the column size to 52
		this.table.getTableHeader().setReorderingAllowed(false);
		this.table.getTableHeader().setResizingAllowed(false);
		this.table.setFillsViewportHeight(true);
		this.table.setSelectionMode(0);
		this.table.setToolTipText("Double Click to see details");
		final JLabel label = (JLabel) this.table.getDefaultRenderer(Object.class);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		scroll = new JScrollPane(this.table);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		this.add(scroll);
		layout.putConstraint(SpringLayout.WEST, scroll, 0, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, scroll, 0, SpringLayout.NORTH, this.getContentPane());
		layout.putConstraint(SpringLayout.SOUTH, scroll, 0, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, scroll, 600, SpringLayout.WEST, this.getContentPane());
		//600 is the width of the scroll

		this.add(this.back);
		layout.putConstraint(SpringLayout.SOUTH, this.back, -30, SpringLayout.SOUTH, this.getContentPane());
		//30 is the gap from the south of the frame to the south of the back button
		layout.putConstraint(SpringLayout.WEST, this.back, BUTTON_BORDER, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.back, -BUTTON_BORDER, SpringLayout.EAST, this.getContentPane());

		this.add(this.delete);
		layout.putConstraint(SpringLayout.SOUTH, this.delete, -MEDIUM_GAP, SpringLayout.NORTH, this.back);
		layout.putConstraint(SpringLayout.WEST, this.delete, BUTTON_BORDER, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.delete, -BUTTON_BORDER, SpringLayout.EAST, this.getContentPane());

		this.add(this.details);
		layout.putConstraint(SpringLayout.SOUTH, this.details, -MEDIUM_GAP, SpringLayout.NORTH, this.delete);
		layout.putConstraint(SpringLayout.WEST, this.details, BUTTON_BORDER, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.details, -BUTTON_BORDER, SpringLayout.EAST, this.getContentPane());

		final SpringLayout panelLayout = new SpringLayout();
		this.previewPanel.setLayout(panelLayout);

		final Border blackBorder = BorderFactory.createLineBorder(Color.BLACK);
		this.previewPanel.setBorder(BorderFactory.createTitledBorder(blackBorder, "Preview"));
		this.add(this.previewPanel);
		layout.putConstraint(SpringLayout.WEST, this.previewPanel, MEDIUM_GAP, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.NORTH, this.previewPanel, 0, SpringLayout.NORTH, bar);
		layout.putConstraint(SpringLayout.EAST, this.previewPanel, -MEDIUM_GAP, SpringLayout.EAST, this.getContentPane());
		layout.putConstraint(SpringLayout.SOUTH, this.previewPanel, -MEDIUM_GAP, SpringLayout.NORTH, this.details);

		this.previewPanel.add(this.lblCover);
		panelLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.lblCover, 0, SpringLayout.HORIZONTAL_CENTER, this.previewPanel);
		panelLayout.putConstraint(SpringLayout.NORTH, this.lblCover, PANEL_MINIMUM_GAP, SpringLayout.NORTH, this.previewPanel);
		panelLayout.putConstraint(SpringLayout.SOUTH, this.lblCover, 125, SpringLayout.NORTH, this.lblCover);
		//125 is the height of the lblCover

		this.previewPanel.add(this.lblTitle);
		panelLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.lblTitle, 0, SpringLayout.HORIZONTAL_CENTER, this.previewPanel);
		panelLayout.putConstraint(SpringLayout.NORTH, this.lblTitle, PANEL_MINIMUM_GAP, SpringLayout.SOUTH, this.lblCover);

		this.previewPanel.add(this.lblGenre);
		panelLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.lblGenre, 0, SpringLayout.HORIZONTAL_CENTER, this.previewPanel);
		panelLayout.putConstraint(SpringLayout.NORTH, this.lblGenre, PANEL_MINIMUM_GAP, SpringLayout.SOUTH, this.lblTitle);

		this.previewPanel.add(this.lblReleaseYear);
		panelLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.lblReleaseYear, 0, SpringLayout.HORIZONTAL_CENTER, this.previewPanel);
		panelLayout.putConstraint(SpringLayout.NORTH, this.lblReleaseYear, PANEL_MINIMUM_GAP, SpringLayout.SOUTH, this.lblGenre);

		this.previewPanel.add(this.lblPrice);
		panelLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.lblPrice, 0, SpringLayout.HORIZONTAL_CENTER, this.previewPanel);
		panelLayout.putConstraint(SpringLayout.NORTH, this.lblPrice, PANEL_MINIMUM_GAP, SpringLayout.SOUTH, this.lblReleaseYear);

		this.previewPanel.add(this.buy);
		panelLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.buy, 0, SpringLayout.HORIZONTAL_CENTER, this.previewPanel);
		panelLayout.putConstraint(SpringLayout.NORTH, this.buy, PANEL_MINIMUM_GAP, SpringLayout.SOUTH, this.lblPrice);
		this.buy.setVisible(false);

		this.back.addActionListener(this);
		this.delete.addActionListener(this);
		this.details.addActionListener(this);
		this.buy.addActionListener(this);
		this.table.addMouseListener(this);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(final WindowEvent e) {
				observer.generateTable();
			}

			@Override
			public void windowClosing(final WindowEvent e) {
				observer.backToHome();		
			}

		});
		this.setLocation();
		this.setIcon();
	}
	@Override
	public void actionPerformed(final ActionEvent e) {
		final Object selection = e.getSource();
		if (selection.equals(this.back)) {
			this.observer.doBack();
		} else if (selection.equals(this.delete)) {
			try {
				final Integer codFilm = (Integer) this.table.getValueAt(this.table.getSelectedRow(), 0);
				if (this.observer.deleteFavorite(codFilm)) {
					((DefaultTableModel) this.table.getModel()).removeRow(this.table.getSelectedRow());
				}
			}	catch (ArrayIndexOutOfBoundsException exc) {
				this.observer.saveError(exc);
				this.observer.showWarningDialog(SELECT_FILM);
			}
		} else if (selection.equals(this.details)) {
			try {
				final Integer idFilm = (Integer) this.table.getValueAt(this.table.getSelectedRow(), 0);
				this.observer.showDetailsFilm(idFilm);
			} catch (ArrayIndexOutOfBoundsException exc) {
				this.observer.saveError(exc);
				this.observer.showWarningDialog(SELECT_FILM);
			}
		} else if (selection.equals(this.buy)) {
			try {
				final Integer codFilm = (Integer) this.table.getValueAt(this.table.getSelectedRow(), 0);
				this.observer.doBuy(codFilm);
			}	catch (ArrayIndexOutOfBoundsException exc) {
				this.observer.saveError(exc);
				this.observer.showWarningDialog(SELECT_FILM);
			}
		} else if (selection.equals(this.fileContainedMenu)) {
			this.observer.doExit();
		} else if (selection.equals(this.helpMenuItem[MENU_IDX_ABOUT])) {
			this.observer.showCreditsView();
		} else if (selection.equals(this.helpMenuItem[MENU_IDX_CONTACT_US])) {
			this.observer.showFeedbackView();
		}
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		if (e.getClickCount() == 2 && !e.isConsumed() && e.getComponent().equals(this.table)) {
			try {
				e.consume();
				final Integer codFilm = (Integer) this.table.getValueAt(this.table.getSelectedRow(), 0);
				this.observer.showDetailsFilm(codFilm);
			}	catch (ArrayIndexOutOfBoundsException exc) {
				this.observer.saveError(exc);
				this.observer.showWarningDialog(SELECT_FILM);
			}
		} else if (e.getClickCount() == 1 && !e.isConsumed() && e.getComponent().equals(this.table)) {
			try {
				e.consume();
				final Integer codFilm = (Integer) this.table.getValueAt(this.table.getSelectedRow(), 0);
				this.observer.showPreview(codFilm);
			}	catch (ArrayIndexOutOfBoundsException exc) {
				this.observer.saveError(exc);
				this.observer.showWarningDialog(SELECT_FILM);
			}
		}
	}
	/**
	 * Attach the observer of the controller to the view.
	 * @param favoriteFilmController controller for this view
	 */
	public void attachObserver(final FavoriteFilmController favoriteFilmController) {
		this.observer = favoriteFilmController;
	}
	/**
	 * Add to the table the raw passed as parameter.
	 * @param obj new row to add to the table
	 */
	public void newRow(final Object[] obj) {
		((DefaultTableModel) this.table.getModel()).addRow(obj);
	}
	/**
	 * Set the fields to show the preview of a film.
	 * @param film	IFilm used to set fields for the preview
	 * @param cover ImageIcon to set to label for the preview
	 */
	public void setPreview(final IFilm film, final ImageIcon cover) {
		this.lblTitle.setText(film.getTitle());
		this.lblCover.setIcon(cover);
		this.lblGenre.setText(film.getGenre().toString());
		this.lblReleaseYear.setText(film.getDate().toString());
		this.lblPrice.setText(film.getPrice().toString());
		this.buy.setVisible(true);
	}
	/**
	 * Set to void the fields to show the preview of a film.
	 */
	public void unsetPreview() {
		this.lblTitle.setText("");
		this.lblCover.setIcon(null);
		this.lblGenre.setText("");
		this.lblReleaseYear.setText("");
		this.lblPrice.setText("");
		this.buy.setVisible(false);

	}

}
