package lmm.view.user;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import lmm.controller.user.FilmListController;
import lmm.model.FilmType;
import lmm.view.AbstractView;
/**
 * User view that has a list of films, showed with different type of filter and the chance to set a proper filter.
 * @author Filippo Nicolini
 *
 */
public class FilmListView extends AbstractView {

	private static final long serialVersionUID = 1;

	private static final String[] COLUMN_NAME_FILM = new String[]{"Cod", "Title", "Genre", "Year", "Price"};
	private static final Color ORANGE_COLOR = new Color(248, 192, 61);

	private static final String SELECT_FILM = "Select a film!";

	private static final Integer HORIZONTAL_GAP = 10;
	private static final Integer PANEL_GAP = 5;
	private static final Integer BUTTON_GAP_TO_FRAME = 40;
	private static final Integer VERTICAL_GAP = 20;
	private static final Integer CHAR_FONT = 14;
	private static final Integer DOUBLE_PANEL_GAP = 10;

	private static final Integer MENU_IDX_CONTACT_US = 0;
	private static final Integer MENU_IDX_ABOUT = 1;

	private static final String[] ALPHABETH = { "Select", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
	private static final String[] PRICES = { "Select", "0-10", "10-15", "15-20", "20-25", "25 +"};
	private static final FilmType[] FILM_TYPES = {FilmType.Select , FilmType.ACTION , FilmType.ADVENTURE, FilmType.ANIMATIONS, FilmType.BIOGRAPHICAL, FilmType.COMIC, FilmType.DOCUMENTARY, FilmType.DRAMATIC, FilmType.EROTIC, FilmType.FANTASY, FilmType.HORROR, FilmType.MUSICAL, FilmType.POLICE, FilmType.SENTIMENTAL, FilmType.THRILLER, FilmType.WESTERN};

	private static final Integer CMB_ROW_COUNT = 5;

	private final JMenuItem fileContainedMenu;

	private final JMenuItem[] helpMenuItem;

	private final JComboBox<FilmType> cmbGenre = new JComboBox<FilmType>(FILM_TYPES);
	private final JComboBox<String> cmbPrice = new JComboBox<String>(PRICES);
	private final JComboBox<String> cmbLetters = new JComboBox<String>(ALPHABETH);
	private final JSpinner spinnerYears = new JSpinner();

	private final JLabel lblTitle = new JLabel("Title");
	private final JLabel lblGenre = new JLabel("Genre");
	private final JLabel lblLetters = new JLabel("Letters");
	private final JLabel lblPrice = new JLabel("Price");
	private final JLabel lblReleaseYear = new JLabel("Release Year");
	private final JLabel lblFilmList = new JLabel("Film List");
	private final JLabel lblTopView = new JLabel("Top View");
	private final JLabel lblTopBought = new JLabel("Top Bought");
	private final JLabel lblRecent = new JLabel("Recent");
	private final JTextField txtTitle = new JTextField(15); //TextField size: 15.
	private final JButton search = new JButton("Search");
	private final JButton back = new JButton("Back");
	private final JButton details = new JButton("Details Film");

	private final JPanel panelFilter = new JPanel();

	private final Object[][] tableData = new Object[][]{};
	private final JTable table;

	private Object[] filtering = new Object[] {null, null, null, null, null};

	private FilmListController observer;

	/**
	 * Create a new FilmListView.
	 */
	public FilmListView() {

		super();

		this.setTitle("Film List");
		this.setResizable(false);
		this.setSize(810, 500); //frame size: 810 width, 500 height.
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		JMenuBar bar;
		JMenu fileMenu;
		JMenu helpMenu;
		JScrollPane scroll;

		final SpringLayout layout = new SpringLayout();
		this.setLayout(layout);

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
		this.setJMenuBar(bar);

		this.lblFilmList.setFont(new Font(null, Font.BOLD, CHAR_FONT));
		this.lblTopView.setFont(new Font(null, Font.BOLD, CHAR_FONT));
		this.lblTopBought.setFont(new Font(null, Font.BOLD, CHAR_FONT));
		this.lblRecent.setFont(new Font(null, Font.BOLD, CHAR_FONT));
		this.lblFilmList.setBackground(ORANGE_COLOR);
		this.lblFilmList.setOpaque(true);
		this.lblTopBought.setOpaque(true);
		this.lblTopView.setOpaque(true);
		this.lblRecent.setOpaque(true);

		this.cmbLetters.setSelectedIndex(0);
		this.cmbLetters.setMaximumRowCount(CMB_ROW_COUNT);
		this.cmbLetters.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(final ItemEvent e) {
				if (cmbLetters.getSelectedItem().equals("Select")) {
					filtering[0] = null;
				} else {
					filtering[0] = (String) cmbLetters.getSelectedItem();
				}
			}
		});

		this.cmbPrice.setSelectedIndex(0);
		this.cmbPrice.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(final ItemEvent e) {
				if (cmbPrice.getSelectedItem().equals(cmbPrice.getItemAt(0))) {
					filtering[2] = null;
					filtering[3] = null;
				}
				if (cmbPrice.getSelectedItem().equals(cmbPrice.getItemAt(1))) {
					filtering[2] = 0;
					filtering[3] = 10; //0 - 10 is the price range.
				}
				if (cmbPrice.getSelectedItem().equals(cmbPrice.getItemAt(2))) {
					filtering[2] = 10;
					filtering[3] = 15; //10 - 15 is the price range.
				}
				if (cmbPrice.getSelectedItem().equals(cmbPrice.getItemAt(3))) {
					filtering[2] = 15;
					filtering[3] = 20; //15 - 20 is the price range.
				}
				if (cmbPrice.getSelectedItem().equals(cmbPrice.getItemAt(4))) {
					filtering[2] = 20;
					filtering[3] = 25; //20 - 25 is the price range.
				}
				if (cmbPrice.getSelectedItem().equals(cmbPrice.getItemAt(5))) {
					filtering[2] = 25;
					filtering[3] = Integer.MAX_VALUE; //25 - MAX_VALUE is the price range.

				}

			}
		});

		this.spinnerYears.setModel(new SpinnerNumberModel(1852, 1852, 2020, 1));
		//set the values of spinnerYears.
		this.spinnerYears.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent e) {
				filtering[4] = (Integer) spinnerYears.getValue();
			}
		});

		this.cmbGenre.setSelectedIndex(0);
		this.cmbGenre.setMaximumRowCount(CMB_ROW_COUNT);
		this.cmbGenre.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(final ItemEvent e) {
				if (cmbGenre.getSelectedItem().equals(FilmType.Select)) {
					filtering[1] = null;
				} else {
					filtering[1] = (FilmType) cmbGenre.getSelectedItem();
				}
			}
		});

		this.cmbGenre.setToolTipText("Select genre");
		this.txtTitle.setToolTipText("Insert title");
		this.cmbPrice.setToolTipText("Select price range");
		this.spinnerYears.setToolTipText("Select year");

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
		layout.putConstraint(SpringLayout.NORTH, scroll, PANEL_GAP, SpringLayout.SOUTH, this.lblFilmList);
		layout.putConstraint(SpringLayout.SOUTH, scroll, 0, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, scroll, 600, SpringLayout.WEST, this.getContentPane());
		//600 is the width of the scroll

		final SpringLayout panelLayout = new SpringLayout();
		this.panelFilter.setLayout(panelLayout);
		final Border blackBorder = BorderFactory.createLineBorder(Color.BLACK);
		this.panelFilter.setBorder(BorderFactory.createTitledBorder(blackBorder, "Filter"));
		this.add(this.panelFilter);
		layout.putConstraint(SpringLayout.WEST, this.panelFilter, HORIZONTAL_GAP, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.NORTH, this.panelFilter, PANEL_GAP, SpringLayout.NORTH, bar);
		layout.putConstraint(SpringLayout.EAST, this.panelFilter, -HORIZONTAL_GAP, SpringLayout.EAST, this.getContentPane());
		layout.putConstraint(SpringLayout.SOUTH, this.panelFilter, -VERTICAL_GAP, SpringLayout.NORTH, this.details);

		this.panelFilter.add(this.lblTitle);
		panelLayout.putConstraint(SpringLayout.WEST, this.lblTitle, PANEL_GAP, SpringLayout.WEST, this.panelFilter);
		panelLayout.putConstraint(SpringLayout.NORTH, this.lblTitle, PANEL_GAP, SpringLayout.NORTH, this.panelFilter);

		this.panelFilter.add(this.txtTitle);
		panelLayout.putConstraint(SpringLayout.WEST, this.txtTitle, PANEL_GAP, SpringLayout.WEST, this.panelFilter);
		panelLayout.putConstraint(SpringLayout.NORTH, this.txtTitle, PANEL_GAP, SpringLayout.SOUTH, this.lblTitle);
		panelLayout.putConstraint(SpringLayout.EAST, this.txtTitle, -PANEL_GAP, SpringLayout.EAST, this.panelFilter);

		this.panelFilter.add(this.lblLetters);
		panelLayout.putConstraint(SpringLayout.WEST, this.lblLetters, PANEL_GAP, SpringLayout.WEST, this.panelFilter);
		panelLayout.putConstraint(SpringLayout.NORTH, this.lblLetters, PANEL_GAP, SpringLayout.SOUTH, this.txtTitle);

		this.panelFilter.add(this.cmbLetters);
		panelLayout.putConstraint(SpringLayout.WEST, this.cmbLetters, PANEL_GAP, SpringLayout.WEST, this.panelFilter);
		panelLayout.putConstraint(SpringLayout.NORTH, this.cmbLetters, PANEL_GAP, SpringLayout.SOUTH, this.lblLetters);
		panelLayout.putConstraint(SpringLayout.EAST, this.cmbLetters, -PANEL_GAP, SpringLayout.EAST, this.panelFilter);

		this.panelFilter.add(this.lblGenre);
		panelLayout.putConstraint(SpringLayout.WEST, this.lblGenre, PANEL_GAP, SpringLayout.WEST, this.panelFilter);
		panelLayout.putConstraint(SpringLayout.NORTH, this.lblGenre, PANEL_GAP, SpringLayout.SOUTH, this.cmbLetters);

		this.panelFilter.add(this.cmbGenre);
		panelLayout.putConstraint(SpringLayout.WEST, this.cmbGenre, PANEL_GAP, SpringLayout.WEST, this.panelFilter);
		panelLayout.putConstraint(SpringLayout.NORTH, this.cmbGenre, PANEL_GAP, SpringLayout.SOUTH, this.lblGenre);
		panelLayout.putConstraint(SpringLayout.EAST, this.cmbGenre, -PANEL_GAP, SpringLayout.EAST, this.panelFilter);

		this.panelFilter.add(this.lblPrice);
		panelLayout.putConstraint(SpringLayout.WEST, this.lblPrice, PANEL_GAP, SpringLayout.WEST, this.panelFilter);
		panelLayout.putConstraint(SpringLayout.NORTH, this.lblPrice, PANEL_GAP, SpringLayout.SOUTH, this.cmbGenre);

		this.panelFilter.add(this.cmbPrice);
		panelLayout.putConstraint(SpringLayout.WEST, this.cmbPrice, PANEL_GAP, SpringLayout.WEST, this.panelFilter);
		panelLayout.putConstraint(SpringLayout.NORTH, this.cmbPrice, PANEL_GAP, SpringLayout.SOUTH, this.lblPrice);
		panelLayout.putConstraint(SpringLayout.EAST, this.cmbPrice, -PANEL_GAP, SpringLayout.EAST, this.panelFilter);

		this.panelFilter.add(this.lblReleaseYear);
		panelLayout.putConstraint(SpringLayout.WEST, this.lblReleaseYear, PANEL_GAP, SpringLayout.WEST, this.panelFilter);
		panelLayout.putConstraint(SpringLayout.NORTH, this.lblReleaseYear, PANEL_GAP, SpringLayout.SOUTH, this.cmbPrice);

		this.panelFilter.add(this.spinnerYears);
		panelLayout.putConstraint(SpringLayout.WEST, this.spinnerYears, PANEL_GAP, SpringLayout.WEST, this.panelFilter);
		panelLayout.putConstraint(SpringLayout.NORTH, this.spinnerYears, PANEL_GAP, SpringLayout.SOUTH, this.lblReleaseYear);
		panelLayout.putConstraint(SpringLayout.EAST, this.spinnerYears, -PANEL_GAP, SpringLayout.EAST, this.panelFilter);

		this.panelFilter.add(this.search);
		panelLayout.putConstraint(SpringLayout.NORTH, this.search, DOUBLE_PANEL_GAP, SpringLayout.SOUTH, this.spinnerYears);
		panelLayout.putConstraint(SpringLayout.EAST, this.search, -PANEL_GAP, SpringLayout.EAST, this.panelFilter);

		this.add(this.lblFilmList);
		layout.putConstraint(SpringLayout.WEST, this.lblFilmList, HORIZONTAL_GAP, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.lblFilmList, PANEL_GAP, SpringLayout.NORTH, this.getContentPane());

		this.add(this.lblTopView);
		layout.putConstraint(SpringLayout.WEST, this.lblTopView, HORIZONTAL_GAP, SpringLayout.EAST, this.lblFilmList);
		layout.putConstraint(SpringLayout.NORTH, this.lblTopView, PANEL_GAP, SpringLayout.NORTH, this.getContentPane());

		this.add(this.lblTopBought);
		layout.putConstraint(SpringLayout.WEST, this.lblTopBought, HORIZONTAL_GAP, SpringLayout.EAST, this.lblTopView);
		layout.putConstraint(SpringLayout.NORTH, this.lblTopBought, PANEL_GAP, SpringLayout.NORTH, this.getContentPane());

		this.add(this.lblRecent);
		layout.putConstraint(SpringLayout.WEST, this.lblRecent, HORIZONTAL_GAP, SpringLayout.EAST, this.lblTopBought);
		layout.putConstraint(SpringLayout.NORTH, this.lblRecent, PANEL_GAP, SpringLayout.NORTH, this.getContentPane());

		this.add(this.details);
		layout.putConstraint(SpringLayout.SOUTH, this.details, -VERTICAL_GAP, SpringLayout.NORTH, this.back);
		layout.putConstraint(SpringLayout.WEST, this.details, BUTTON_GAP_TO_FRAME, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.details, -BUTTON_GAP_TO_FRAME, SpringLayout.EAST, this.getContentPane());

		this.add(this.back);
		layout.putConstraint(SpringLayout.SOUTH, this.back, -VERTICAL_GAP, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.back, BUTTON_GAP_TO_FRAME, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.back, -BUTTON_GAP_TO_FRAME, SpringLayout.EAST, this.getContentPane());

		this.lblFilmList.addMouseListener(this);
		this.lblTopView.addMouseListener(this);
		this.lblTopBought.addMouseListener(this);
		this.lblRecent.addMouseListener(this);
		this.back.addActionListener(this);
		this.details.addActionListener(this);
		this.search.addActionListener(this);
		this.table.addMouseListener(this);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(final WindowEvent e) {
				observer.generateTableFilmList(null);
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
		} else if (selection.equals(this.fileContainedMenu)) {
			this.observer.doExit();
		} else if (selection.equals(this.helpMenuItem[MENU_IDX_ABOUT])) {
			this.observer.showCreditsView();
		} else if (selection.equals(this.helpMenuItem[MENU_IDX_CONTACT_US])) {
			this.observer.showFeedbackView();
		} else if (selection.equals(this.details)) {
			try {
				final Integer idFilm = (Integer) this.table.getValueAt(this.table.getSelectedRow(), 0);
				this.observer.showDetailsFilm(idFilm);
			} catch (ArrayIndexOutOfBoundsException exc) {
				this.observer.saveError(exc);
				this.observer.showWarningDialog(SELECT_FILM);
			}
		} else if (selection.equals(this.search)) {
			if (this.txtTitle.getText().length() != 0) {
				this.filtering[0] = this.txtTitle.getText();
			}
			this.refreshTable();
			if (this.lblFilmList.getBackground().equals(ORANGE_COLOR)) {
				this.observer.generateTableFilmList(this.filtering);
			} else if (this.lblRecent.getBackground().equals(ORANGE_COLOR)) {
				this.observer.generateTableRecent(this.filtering);
			} else if (this.lblTopBought.getBackground().equals(ORANGE_COLOR)) {
				this.observer.generateTableTopBought(this.filtering);
			} else if (this.lblTopView.getBackground().equals(ORANGE_COLOR)) {
				this.observer.generateTableTopVisited(this.filtering);
			}
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
		} else if (e.getClickCount() == 1 && !e.isConsumed() && e.getComponent().equals(this.lblTopView) && !this.lblTopView.getBackground().equals(ORANGE_COLOR)) {
			e.consume();
			this.lblTopView.setBackground(ORANGE_COLOR);
			this.lblFilmList.setBackground(null);
			this.lblTopBought.setBackground(null);
			this.lblRecent.setBackground(null);
			this.refreshTable();
			this.resetFilter();
			this.observer.generateTableTopVisited(null);
		} else if (e.getClickCount() == 1 && !e.isConsumed() && e.getComponent().equals(this.lblTopBought) && !this.lblTopBought.getBackground().equals(ORANGE_COLOR)) {
			e.consume();
			this.lblTopBought.setBackground(ORANGE_COLOR);
			this.lblFilmList.setBackground(null);
			this.lblTopView.setBackground(null);
			this.lblRecent.setBackground(null);
			this.refreshTable();
			this.resetFilter();
			this.observer.generateTableTopBought(null);
		} else if (e.getClickCount() == 1 && !e.isConsumed() && e.getComponent().equals(this.lblFilmList) && !this.lblFilmList.getBackground().equals(ORANGE_COLOR)) {
			e.consume();
			this.lblFilmList.setBackground(ORANGE_COLOR);
			this.lblTopView.setBackground(null);
			this.lblTopBought.setBackground(null);
			this.lblRecent.setBackground(null);
			this.refreshTable();
			this.resetFilter();
			this.observer.generateTableFilmList(null);
		} else if (e.getClickCount() == 1 && !e.isConsumed() && e.getComponent().equals(this.lblRecent) && !this.lblRecent.getBackground().equals(ORANGE_COLOR)) {
			e.consume();
			this.lblRecent.setBackground(ORANGE_COLOR);
			this.lblFilmList.setBackground(null);
			this.lblTopBought.setBackground(null);
			this.lblTopView.setBackground(null);
			this.refreshTable();
			this.resetFilter();
			this.observer.generateTableRecent(null);
		}

	}

	private void resetFilter() {

		this.cmbLetters.setSelectedIndex(0);
		this.cmbGenre.setSelectedIndex(0);
		this.cmbPrice.setSelectedIndex(0);
		this.txtTitle.setText(null);
		this.spinnerYears.setValue(1852); //is the basic value of spinnerYears.

		this.filtering[0] = null;
		this.filtering[1] = null;
		this.filtering[2] = null;
		this.filtering[3] = null;
		this.filtering[4] = null;

	}
	/**
	 * Attach the observer of the controller to the view.
	 * @param filmListController controller for this view
	 */
	public void attachObserver(final FilmListController filmListController) {
		this.observer = filmListController;
	}
	/**
	 * Add to the table the raw passed as parameter.
	 * @param obj new row to add to the table
	 */
	public void newRow(final Object[] obj) {
		((DefaultTableModel) this.table.getModel()).addRow(obj);
	}
	/**
	 * Clean the table.
	 */
	public void refreshTable() {
		((DefaultTableModel) this.table.getModel()).getDataVector().clear();
		((DefaultTableModel) this.table.getModel()).fireTableDataChanged();
	}

}
