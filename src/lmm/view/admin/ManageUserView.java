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

import lmm.controller.admin.ManageUserController;
import lmm.view.AbstractView;

/**
 * Class that allows to the admin to operate with the users.
 * @author Filippo Nicolini
 *
 */
public class ManageUserView extends AbstractView {

	private static final long serialVersionUID = 1;

	private final Object[][] tableData = new Object[][]{};

	private static final String[] COLUMN_NAME_USER = new String[]{"Username", "Password", "Name", "Surname", "Date"};

	private static final String SELECT_USER = "Select an user!";

	private final JLabel loadingGif = new JLabel(new ImageIcon(this.getClass().getResource("/Loading.gif")));

	private static final Integer LEFT_BORDER_BUTTON = 40;
	private static final Integer RIGHT_BORDER_BUTTON = 160;
	private static final Integer VERTICAL_GAP = 20;

	private static final int MENU_IDX_SAVE_FROM_FILE = 0;
	private static final int MENU_IDX_LOAD_FROM_FILE = 1;
	private static final int MENU_IDX_EXIT = 2;
	private static final Integer MENU_IDX_CONTACT_US = 0;
	private static final Integer MENU_IDX_ABOUT = 1;

	private final JMenuItem[] fileContainedMenu;
	private final JMenuItem[] helpMenuItem;

	private final JButton delete = new JButton("Delete User");
	private final JButton edit = new JButton("Edit User");
	private final JButton stats = new JButton("User Stats");
	private final JButton back = new JButton("Back");
	private final JButton newUser = new JButton("New User");

	private final JTable table;

	private ManageUserController observer;
	/**
	 * Create a new ManageUserView.
	 */
	public ManageUserView() {

		super();

		this.setTitle("Manage User");
		this.setSize(800, 450); //frame size: 800 width, 450 height.
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

		this.table = new JTable(new DefaultTableModel(tableData, COLUMN_NAME_USER) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(final int row, final int column) {
				return false;
			}
		});
		this.table.getColumn("Username").setPreferredWidth(125); //set the column size to 125
		this.table.getColumn("Password").setPreferredWidth(125); //set the column size to 125
		this.table.getColumn("Name").setPreferredWidth(125); //set the column size to 125
		this.table.getColumn("Surname").setPreferredWidth(125); //set the column size to 125
		this.table.getColumn("Date").setPreferredWidth(82); //set the column size to 82
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
		layout.putConstraint(SpringLayout.NORTH, scroll, 0, SpringLayout.NORTH, bar);
		layout.putConstraint(SpringLayout.SOUTH, scroll, 0, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, scroll, 600, SpringLayout.WEST, this.getContentPane());
		//600 is the width of the scroll.
		this.add(this.edit);
		layout.putConstraint(SpringLayout.NORTH, this.edit, VERTICAL_GAP, SpringLayout.SOUTH, bar);
		layout.putConstraint(SpringLayout.WEST, this.edit, LEFT_BORDER_BUTTON, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.edit, RIGHT_BORDER_BUTTON, SpringLayout.EAST, scroll);

		this.add(this.delete);
		layout.putConstraint(SpringLayout.NORTH, this.delete, VERTICAL_GAP, SpringLayout.SOUTH, this.edit);
		layout.putConstraint(SpringLayout.WEST, this.delete, LEFT_BORDER_BUTTON, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.delete, RIGHT_BORDER_BUTTON, SpringLayout.EAST, scroll);

		this.add(this.stats);
		layout.putConstraint(SpringLayout.NORTH, this.stats, VERTICAL_GAP, SpringLayout.SOUTH, this.delete);
		layout.putConstraint(SpringLayout.WEST, this.stats, LEFT_BORDER_BUTTON, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.stats, RIGHT_BORDER_BUTTON, SpringLayout.EAST, scroll);

		this.add(this.newUser);
		layout.putConstraint(SpringLayout.NORTH, this.newUser, VERTICAL_GAP, SpringLayout.SOUTH, this.stats);
		layout.putConstraint(SpringLayout.WEST, this.newUser, LEFT_BORDER_BUTTON, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.newUser, RIGHT_BORDER_BUTTON, SpringLayout.EAST, scroll);

		this.add(this.back);
		layout.putConstraint(SpringLayout.SOUTH, this.back, -VERTICAL_GAP, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.back, LEFT_BORDER_BUTTON, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.back, RIGHT_BORDER_BUTTON, SpringLayout.EAST, scroll);

		this.add(this.loadingGif, 1, 0);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.loadingGif, 0, SpringLayout.HORIZONTAL_CENTER, this.getContentPane());
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, this.loadingGif, 0, SpringLayout.VERTICAL_CENTER, this.getContentPane());
		this.loadingGif.setVisible(false);

		this.edit.addActionListener(this);
		this.delete.addActionListener(this);
		this.stats.addActionListener(this);
		this.back.addActionListener(this);
		this.newUser.addActionListener(this);
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
		if (selection.equals(this.edit)) {
			try {
				final String userId = (String) this.table.getValueAt(this.table.getSelectedRow(), 0);
				this.observer.doEditUser(userId);
			} catch (ArrayIndexOutOfBoundsException exc) {
				this.observer.saveError(exc);
				this.observer.showWarningDialog(SELECT_USER);
			}

		} else if (selection.equals(delete)) {
			try {
				final String userId = (String) this.table.getValueAt(this.table.getSelectedRow(), 0);
				if (this.observer.doDeleteUser(userId)) {
					((DefaultTableModel) this.table.getModel()).removeRow(this.table.getSelectedRow());
				}
			} catch (ArrayIndexOutOfBoundsException exc) {
				this.observer.saveError(exc);
				this.observer.showWarningDialog(SELECT_USER);
			}

		} else if (selection.equals(this.stats)) {
			try {
				final String userId = (String) this.table.getValueAt(this.table.getSelectedRow(), 0);
				this.observer.doUserStats(userId);
			} catch (ArrayIndexOutOfBoundsException exc) {
				this.observer.saveError(exc);
				this.observer.showWarningDialog(SELECT_USER);
			}
		} else if (selection.equals(this.newUser)) {
			this.observer.addUser();

		} else if (selection.equals(this.back)) {
			this.observer.doBack();

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

	@Override
	public void mouseReleased(final MouseEvent e) {
		if (e.getClickCount() == 2 && !e.isConsumed()) {
			e.consume();
			try {
				final String selectedUser = (String) this.table.getValueAt(this.table.getSelectedRow(), 0);
				this.observer.doUserStats(selectedUser);
			}	catch (ArrayIndexOutOfBoundsException exc) {
				this.observer.saveError(exc);
				this.observer.showWarningDialog(SELECT_USER);
			}
		}

	}
	/**
	 * Attach the observer of the controller to the view.
	 * @param manageUserController controller for this view
	 */
	public void attachObserver(final ManageUserController manageUserController) {
		this.observer = manageUserController;		
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
