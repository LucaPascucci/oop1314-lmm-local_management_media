package lmm.view.admin;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import lmm.controller.admin.ManageFilmController;
import lmm.view.AbstractView;

/**
 * Class that allows the admin to handle the films list in the program.
 * @author Filippo Nicolini
 *
 */
public class ManageFilmView extends AbstractView {

	private static final long serialVersionUID = 1;

	private static final String[] COLUMN_NAME_FILM = new String[]{"Cod", "Title", "Genre", "Year", "Price"};

	private static final String SELECT_FILM = "Select a film!";

	private static final Integer LEFT_BORDER_BUTTON = 40;
	private static final Integer RIGHT_BORDER_BUTTON = 160;
	private static final Integer NORTH_GAP = 20;

	private static final int MENU_IDX_SAVE_FROM_FILE = 0;
	private static final int MENU_IDX_LOAD_FROM_FILE = 1;
	private static final int MENU_IDX_EXIT = 2;
	private static final Integer MENU_IDX_CONTACT_US = 0;
	private static final Integer MENU_IDX_ABOUT = 1;

	private final JLabel loadingGif = new JLabel(new ImageIcon(this.getClass().getResource("/Loading.gif")));

	private final JMenuItem[] fileContainedMenu;
	private final JMenuItem[] helpMenuItem;

	private final Object[][] tabelData = new Object[][]{};

	private final JButton editFilm = new JButton("Edit Film");
	private final JButton addFilm = new JButton("Add Film");
	private final JButton addTrailer = new JButton("Add Trailer");
	private final JButton addCover = new JButton("Add Cover");
	private final JButton deleteFilm = new JButton("Delete Film");
	private final JButton details = new JButton("Details Film");
	private final JButton back = new JButton("Back");

	private final JTable table;

	private ManageFilmController observer;


	/**
	 * Create a new ManageFilmView.
	 */
	public ManageFilmView() {

		super();

		this.setTitle("Manage Film");
		this.setSize(800, 450); //frame size: 800 width, 450 height.
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setResizable(false);

		JMenuBar bar;
		JMenu fileMenu;
		JMenu helpMenu;
		JScrollPane scroll;

		final SpringLayout layout = new SpringLayout();
		this.setLayout(layout);

		bar = new JMenuBar();
		fileMenu = new JMenu("File");
		helpMenu = new JMenu("Help");
		this.fileContainedMenu = new JMenuItem[3];
		this.fileContainedMenu[MENU_IDX_SAVE_FROM_FILE] = new JMenuItem("Save Data");
		this.fileContainedMenu[MENU_IDX_LOAD_FROM_FILE] = new JMenuItem("Load Data");
		this.fileContainedMenu[MENU_IDX_EXIT] = new JMenuItem("Exit");
		for (int i = 0; i < 3; i++) {
			fileMenu.add(this.fileContainedMenu[i]);
			this.fileContainedMenu[i].addActionListener(this);
		}
		this.helpMenuItem = new JMenuItem[2];
		this.helpMenuItem[MENU_IDX_CONTACT_US] = new JMenuItem("Contact Us");
		this.helpMenuItem[MENU_IDX_ABOUT] = new JMenuItem("About");
		for (int i = 0; i < 2; i++) {
			helpMenu.add(this.helpMenuItem[i]);
			this.helpMenuItem[i].addActionListener(this);	
		}

		bar.add(fileMenu);
		bar.add(helpMenu);
		this.setJMenuBar(bar);

		this.table = new JTable(new DefaultTableModel(tabelData, COLUMN_NAME_FILM) {
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

		this.add(this.loadingGif, 1, 0);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.loadingGif, 0, SpringLayout.HORIZONTAL_CENTER, this.getContentPane());
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, this.loadingGif, 0, SpringLayout.VERTICAL_CENTER, this.getContentPane());
		this.loadingGif.setVisible(false);

		this.add(scroll);
		layout.putConstraint(SpringLayout.WEST, scroll, 0, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, scroll, 0, SpringLayout.NORTH, bar);
		layout.putConstraint(SpringLayout.SOUTH, scroll, 0, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, scroll, 600, SpringLayout.WEST, this.getContentPane());
		//600 is the width of the scroll

		this.add(this.details);
		layout.putConstraint(SpringLayout.NORTH, this.details, NORTH_GAP, SpringLayout.SOUTH, bar);
		layout.putConstraint(SpringLayout.WEST, this.details, LEFT_BORDER_BUTTON, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.details, RIGHT_BORDER_BUTTON, SpringLayout.EAST, scroll);

		this.add(this.addFilm);
		layout.putConstraint(SpringLayout.NORTH, this.addFilm, NORTH_GAP, SpringLayout.SOUTH, this.details);
		layout.putConstraint(SpringLayout.WEST, this.addFilm, LEFT_BORDER_BUTTON, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.addFilm, RIGHT_BORDER_BUTTON, SpringLayout.EAST, scroll);

		this.add(this.addCover);
		layout.putConstraint(SpringLayout.NORTH, this.addCover, NORTH_GAP, SpringLayout.SOUTH, this.addFilm);
		layout.putConstraint(SpringLayout.WEST, this.addCover, LEFT_BORDER_BUTTON, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.addCover, RIGHT_BORDER_BUTTON, SpringLayout.EAST, scroll);

		this.add(this.addTrailer);
		layout.putConstraint(SpringLayout.NORTH, this.addTrailer, NORTH_GAP, SpringLayout.SOUTH, this.addCover);
		layout.putConstraint(SpringLayout.WEST, this.addTrailer, LEFT_BORDER_BUTTON, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.addTrailer, RIGHT_BORDER_BUTTON, SpringLayout.EAST, scroll);

		this.add(this.editFilm);
		layout.putConstraint(SpringLayout.NORTH, this.editFilm, NORTH_GAP, SpringLayout.SOUTH, this.addTrailer);
		layout.putConstraint(SpringLayout.WEST, this.editFilm, LEFT_BORDER_BUTTON, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.editFilm, RIGHT_BORDER_BUTTON, SpringLayout.EAST, scroll);

		this.add(this.deleteFilm);
		layout.putConstraint(SpringLayout.NORTH, this.deleteFilm, NORTH_GAP, SpringLayout.SOUTH, this.editFilm);
		layout.putConstraint(SpringLayout.WEST, this.deleteFilm, LEFT_BORDER_BUTTON, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.deleteFilm, RIGHT_BORDER_BUTTON, SpringLayout.EAST, scroll);

		this.add(this.back);
		layout.putConstraint(SpringLayout.SOUTH, this.back, -NORTH_GAP, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.back, LEFT_BORDER_BUTTON, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.back, RIGHT_BORDER_BUTTON, SpringLayout.EAST, scroll);

		this.addCover.addActionListener(this);
		this.addFilm.addActionListener(this);
		this.addTrailer.addActionListener(this);
		this.back.addActionListener(this);
		this.deleteFilm.addActionListener(this);
		this.editFilm.addActionListener(this);
		this.details.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(final WindowEvent e) {
				observer.generateTable();
			}

			@Override
			public void windowClosing(final WindowEvent e) {
				if (!loadingGif.isVisible()) {
					observer.backToHome();
				}
			}
		});
		this.table.addMouseListener(this);
		this.setLocation();
		this.setIcon();
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		final Object selection = e.getSource();
		if (!this.loadingGif.isVisible()) {
			if (selection.equals(this.back)) {
				this.observer.doBack();
			} else if (selection.equals(this.addCover)) {
				try {
					final Integer value = (Integer) this.table.getValueAt(this.table.getSelectedRow(), 0);
					this.observer.doAddCover(value);
				} catch (ArrayIndexOutOfBoundsException exc) {
					this.observer.saveError(exc);
					this.observer.showWarningDialog(SELECT_FILM);
				}
			} else if (selection.equals(this.addFilm)) {
				this.observer.doAddMovie();
			} else if (selection.equals(this.addTrailer)) {
				try {
					final Integer value = (Integer) this.table.getValueAt(this.table.getSelectedRow(), 0);
					this.observer.doAddTrailer(value);
				} catch (ArrayIndexOutOfBoundsException exc) {
					this.observer.saveError(exc);
					this.observer.showWarningDialog(SELECT_FILM);
				}
			} else if (selection.equals(this.deleteFilm)) {
				try {
					final Integer value = (Integer) this.table.getValueAt(this.table.getSelectedRow(), 0);
					if (this.observer.doDeleteFilm(value)) {
						((DefaultTableModel) this.table.getModel()).removeRow(this.table.getSelectedRow());
					}
				} catch (ArrayIndexOutOfBoundsException exc) {
					this.observer.saveError(exc);
					this.observer.showWarningDialog(SELECT_FILM);
				}
			} else if (selection.equals(this.editFilm)) {
				try {
					final Integer value = (Integer) this.table.getValueAt(this.table.getSelectedRow(), 0);
					this.observer.doEditMovie(value);
				} catch (ArrayIndexOutOfBoundsException exc) {
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
			} else if (selection == this.fileContainedMenu[MENU_IDX_SAVE_FROM_FILE]) {
				this.setUnsetLoadingVisible();
				this.observer.doSaving();
			} else if (selection == this.fileContainedMenu[MENU_IDX_LOAD_FROM_FILE]) {
				this.setUnsetLoadingVisible();
				this.observer.doLoading();
				this.refreshTable();
			} else if (selection == this.fileContainedMenu[MENU_IDX_EXIT]) {
				this.observer.doExit();
			} else if (selection.equals(this.helpMenuItem[MENU_IDX_ABOUT])) {
				this.observer.showCreditsView();
			} else if (selection.equals(this.helpMenuItem[MENU_IDX_CONTACT_US])) {
				this.observer.showFeedbackView();
			}
		}

	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		if (e.getClickCount() == 2 && !e.isConsumed()  && e.getComponent().equals(this.table) && !this.loadingGif.isVisible()) {
			try {
				e.consume();
				final Integer idFilm = (Integer) this.table.getValueAt(this.table.getSelectedRow(), 0);
				this.observer.showDetailsFilm(idFilm);
			} catch (ArrayIndexOutOfBoundsException exc) {
				this.observer.saveError(exc);
				this.observer.showWarningDialog(SELECT_FILM);
			}

		}
	}
	/**
	 * Attach the observer of the controller to the view.
	 * @param manageFilmController controller for this view
	 */
	public void attachObserver(final ManageFilmController manageFilmController) {
		this.observer = manageFilmController;

	}
	/**
	 * Add to the table the raw passed as parameter.
	 * @param obj new row to add to the table
	 */
	public void newRow(final Object[] obj) {
		((DefaultTableModel) this.table.getModel()).addRow(obj);

	}
	/**
	 * Clean the table before add new row.
	 */
	public void refreshTable() {
		((DefaultTableModel) this.table.getModel()).getDataVector().clear();
		((DefaultTableModel) this.table.getModel()).fireTableDataChanged();
	}
	/**
	 * Set JLable visibility.
	 */
	public void setUnsetLoadingVisible() {
		if (this.loadingGif.isVisible()) {
			this.loadingGif.setVisible(false);
		} else {
			this.loadingGif.setVisible(true);
		}
	}

}