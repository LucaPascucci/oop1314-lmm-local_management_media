package lmm.view.admin;

import java.awt.Color;
import java.awt.Font;
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

import lmm.controller.admin.AdminStatsController;
import lmm.view.AbstractView;

/**
 * Class that shows to the admin the list of the films with different type of filter and the statistics of the film and money incomes.
 * @author Filippo Nicolini
 *
 */
public class AdminStatsView extends AbstractView {

	private static final long serialVersionUID = 1;

	private static final Integer HORIZONTAL_LBL_TABULATION = 20;
	private static final Integer VERTICAL_GAP = 30;
	private static final Integer MINIMUM_GAP = 5;
	private static final Integer BUTTON_GAP_TO_FRAME = 50;
	private static final Integer LABEL_GAP = 15;
	private static final Integer CHAR_FONT = 14;

	private final JLabel loadingGif = new JLabel(new ImageIcon(this.getClass().getResource("/Loading.gif")));

	private static final int MENU_IDX_SAVE_FROM_FILE = 0;
	private static final int MENU_IDX_LOAD_FROM_FILE = 1;
	private static final int MENU_IDX_EXIT = 2;
	private static final Integer MENU_IDX_CONTACT_US = 0;
	private static final Integer MENU_IDX_ABOUT = 1;

	private static final String[] COLUMN_NAME_FILM = new String[]{"Cod", "Title", "Genre", "Year", "Price"};
	private static final Color ORANGE_COLOR = new Color(248, 192, 61);

	private static final String SELECT_FILM = "Select a film!";

	private final JMenuItem[] fileContainedMenu;
	private final JMenuItem[] helpMenuItem;

	private final Object[][] tableData = new Object[][]{};

	private final JLabel lblFilmPresents = new JLabel();
	private final JLabel lblRegisteredUsers = new JLabel();
	private final JLabel lblTotalIncomes = new JLabel();
	private final JLabel lblFilmList = new JLabel("Film List");
	private final JLabel lblTopView = new JLabel("Top View");
	private final JLabel lblTopBought = new JLabel("Top Bought");
	private final JLabel lblRecent = new JLabel("Recent");
	private final JButton details = new JButton("Details Film");
	private final JButton back = new JButton("Back");
	private final JButton changePassword = new JButton("Change Password");

	private final JTable table;

	private AdminStatsController observer;

	/**
	 * This constructor creates a new AdminStatsView.
	 */
	public AdminStatsView() {
		super();

		this.setTitle("Admin Stats");
		this.setSize(850, 450); //frame size: 850 width, 450 height.
		this.setResizable(false);
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
		this.table.setToolTipText("Double click to see details");
		final JLabel label = (JLabel) this.table.getDefaultRenderer(Object.class);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		scroll = new JScrollPane(this.table);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		this.add(scroll);
		layout.putConstraint(SpringLayout.WEST, scroll, 0, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, scroll, MINIMUM_GAP, SpringLayout.SOUTH, this.lblFilmList);
		layout.putConstraint(SpringLayout.SOUTH, scroll, 0, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, scroll, 600, SpringLayout.WEST, this.getContentPane());
		//600 is the width of the scroll

		this.add(this.lblRegisteredUsers);
		layout.putConstraint(SpringLayout.NORTH, this.lblRegisteredUsers, VERTICAL_GAP, SpringLayout.NORTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.lblRegisteredUsers, HORIZONTAL_LBL_TABULATION, SpringLayout.EAST, scroll);

		this.add(this.lblTotalIncomes);
		layout.putConstraint(SpringLayout.NORTH, this.lblTotalIncomes, VERTICAL_GAP, SpringLayout.SOUTH, this.lblRegisteredUsers);
		layout.putConstraint(SpringLayout.WEST, this.lblTotalIncomes, HORIZONTAL_LBL_TABULATION, SpringLayout.EAST, scroll);

		this.add(this.lblFilmPresents);
		layout.putConstraint(SpringLayout.NORTH, this.lblFilmPresents, VERTICAL_GAP, SpringLayout.SOUTH, this.lblTotalIncomes);
		layout.putConstraint(SpringLayout.WEST, this.lblFilmPresents, HORIZONTAL_LBL_TABULATION, SpringLayout.EAST, scroll);

		this.add(this.lblFilmList);
		layout.putConstraint(SpringLayout.WEST, this.lblFilmList, MINIMUM_GAP, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.lblFilmList, MINIMUM_GAP, SpringLayout.NORTH, this.getContentPane());
		this.lblFilmList.setFont(new Font(null, Font.BOLD, CHAR_FONT));
		this.lblFilmList.setBackground(ORANGE_COLOR);
		this.lblFilmList.setOpaque(true);

		this.add(this.lblTopView);
		layout.putConstraint(SpringLayout.WEST, this.lblTopView, LABEL_GAP, SpringLayout.EAST, this.lblFilmList);
		layout.putConstraint(SpringLayout.NORTH, this.lblTopView, MINIMUM_GAP, SpringLayout.NORTH, this.getContentPane());
		this.lblTopView.setFont(new Font(null, Font.BOLD, CHAR_FONT));
		this.lblTopView.setOpaque(true);

		this.add(this.lblTopBought);
		layout.putConstraint(SpringLayout.WEST, this.lblTopBought, LABEL_GAP, SpringLayout.EAST, this.lblTopView);
		layout.putConstraint(SpringLayout.NORTH, this.lblTopBought, MINIMUM_GAP, SpringLayout.NORTH, this.getContentPane());
		this.lblTopBought.setFont(new Font(null, Font.BOLD, CHAR_FONT));
		this.lblTopBought.setOpaque(true);

		this.add(this.lblRecent);
		layout.putConstraint(SpringLayout.WEST, this.lblRecent, LABEL_GAP, SpringLayout.EAST, this.lblTopBought);
		layout.putConstraint(SpringLayout.NORTH, this.lblRecent, MINIMUM_GAP, SpringLayout.NORTH, this.getContentPane());
		this.lblRecent.setFont(new Font(null, Font.BOLD, CHAR_FONT));
		this.lblRecent.setOpaque(true);

		this.add(this.details);
		layout.putConstraint(SpringLayout.SOUTH, this.details, -VERTICAL_GAP, SpringLayout.NORTH, this.changePassword);
		layout.putConstraint(SpringLayout.WEST, this.details, BUTTON_GAP_TO_FRAME, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.details, -BUTTON_GAP_TO_FRAME, SpringLayout.EAST, this.getContentPane());

		this.add(this.changePassword);
		layout.putConstraint(SpringLayout.SOUTH, this.changePassword, -VERTICAL_GAP, SpringLayout.NORTH, this.back);
		layout.putConstraint(SpringLayout.WEST, this.changePassword, BUTTON_GAP_TO_FRAME, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.changePassword, -BUTTON_GAP_TO_FRAME, SpringLayout.EAST, this.getContentPane());

		this.add(this.back);
		layout.putConstraint(SpringLayout.SOUTH, this.back, -VERTICAL_GAP, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.back, BUTTON_GAP_TO_FRAME, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.back, -BUTTON_GAP_TO_FRAME, SpringLayout.EAST, this.getContentPane());

		this.add(this.loadingGif, 1, 0); //this method put the loadingGif on the top of the others component.
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.loadingGif, 0, SpringLayout.HORIZONTAL_CENTER, this.getContentPane());
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, this.loadingGif, 0, SpringLayout.VERTICAL_CENTER, this.getContentPane());
		this.loadingGif.setVisible(false);

		this.lblFilmList.addMouseListener(this);
		this.lblTopView.addMouseListener(this);
		this.lblTopBought.addMouseListener(this);
		this.lblRecent.addMouseListener(this);
		this.back.addActionListener(this);
		this.changePassword.addActionListener(this);
		this.details.addActionListener(this);
		this.table.addMouseListener(this);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(final WindowEvent e) {
				observer.generateTableFilmList();
				lblRegisteredUsers.setText("Registered users:  " + observer.getTotalUsers());
				lblTotalIncomes.setText("Total incomes:  " + observer.getTotalIncomes());
				lblFilmPresents.setText("Available films:  " + observer.getTotalFilms());
			}

			@Override
			public void windowClosing(final WindowEvent e) {
				if (!loadingGif.isVisible()) {
					observer.backToHome();
				}
			}
		});
		this.setLocation();
		this.setIcon();
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		if (!this.loadingGif.isVisible()) {
			final Object selection = e.getSource();
			if (selection.equals(this.back)) {
				this.observer.doBack();
			} else if (selection == this.fileContainedMenu[MENU_IDX_SAVE_FROM_FILE]) {
				this.setUnsetLoadingVisible();
				this.observer.doSaving();
			} else if (selection == this.fileContainedMenu[MENU_IDX_LOAD_FROM_FILE]) {
				this.setUnsetLoadingVisible();
				this.observer.doLoading();
				this.refreshTable();
				this.lblFilmList.setBackground(ORANGE_COLOR);
				this.lblTopView.setBackground(null);
				this.lblTopBought.setBackground(null);
				this.lblRecent.setBackground(null);
			} else if (selection.equals(this.fileContainedMenu[MENU_IDX_EXIT])) {
				this.observer.doExit();
			} else if (selection.equals(this.helpMenuItem[MENU_IDX_ABOUT])) {
				this.observer.showCreditsView();
			} else if (selection.equals(this.helpMenuItem[MENU_IDX_CONTACT_US])) {
				this.observer.showFeedbackView();
			} else if (selection.equals(this.changePassword)) {
				this.observer.doChangePassword();
			} else if (selection.equals(this.details)) {				
				try {
					final Integer idFilm = (Integer) this.table.getValueAt(this.table.getSelectedRow(), 0);
					this.observer.showDetailsFilm(idFilm);
				} catch (ArrayIndexOutOfBoundsException exc) {
					this.observer.saveError(exc);
					this.observer.showWarningDialog(SELECT_FILM);
				}
			}
		}
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		if (!this.loadingGif.isVisible()) {
			if (e.getClickCount() == 2 && !e.isConsumed()  && e.getComponent().equals(this.table)) {
				try {
					e.consume();
					final Integer idFilm = (Integer) this.table.getValueAt(this.table.getSelectedRow(), 0);
					this.observer.showDetailsFilm(idFilm);
				} catch (ArrayIndexOutOfBoundsException exc) {
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
				this.observer.generateTableTopVisited();
			} else if (e.getClickCount() == 1 && !e.isConsumed() && e.getComponent().equals(this.lblTopBought) && !this.lblTopBought.getBackground().equals(ORANGE_COLOR)) {
				e.consume();
				this.lblTopView.setBackground(null);
				this.lblFilmList.setBackground(null);
				this.lblTopBought.setBackground(ORANGE_COLOR);
				this.lblRecent.setBackground(null);
				this.refreshTable();
				this.observer.generateTableTopBought();
			} else if (e.getClickCount() == 1 && !e.isConsumed() && e.getComponent().equals(this.lblFilmList) && !this.lblFilmList.getBackground().equals(ORANGE_COLOR)) {
				e.consume();
				this.lblTopView.setBackground(null);
				this.lblFilmList.setBackground(ORANGE_COLOR);
				this.lblTopBought.setBackground(null);
				this.lblRecent.setBackground(null);
				this.refreshTable();
				this.observer.generateTableFilmList();
			} else if (e.getClickCount() == 1 && !e.isConsumed() && e.getComponent().equals(this.lblRecent) && !this.lblRecent.getBackground().equals(ORANGE_COLOR)) {
				e.consume();
				this.lblTopView.setBackground(null);
				this.lblRecent.setBackground(ORANGE_COLOR);
				this.lblTopBought.setBackground(null);
				this.lblFilmList.setBackground(null);
				this.refreshTable();
				this.observer.generateTableRecent();
			}
		}
	}
	/**
	 * Attach the observer of the controller to the view.
	 * @param adminStatsController controller for this view
	 */
	public void attachObserver(final AdminStatsController adminStatsController) {
		this.observer = adminStatsController;
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
	/**
	 * Set visibility of the loadingGif.
	 */
	public void setUnsetLoadingVisible() {
		if (this.loadingGif.isVisible()) {
			this.loadingGif.setVisible(false);
		} else {
			this.loadingGif.setVisible(true);
		}
	}
}
