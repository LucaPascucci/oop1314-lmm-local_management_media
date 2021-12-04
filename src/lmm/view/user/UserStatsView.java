package lmm.view.user;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;

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

import lmm.controller.user.UserStatsController;
import lmm.model.User;
import lmm.view.AbstractView;
/**
 * User view that show the information of the user and his films.
 * @author Filippo Nicolini
 *
 */
public class UserStatsView extends AbstractView {

	private static final long serialVersionUID = 1;

	private static final String[] COLUMN_NAME_FILM = new String[]{"Cod", "Title", "Genre", "Year", "Price"};

	private static final String SELECT_FILM = "Select a film!";

	private static final Integer MENU_IDX_CONTACT_US = 0;
	private static final Integer MENU_IDX_ABOUT = 1;

	private static final Integer VERTICAL_GAP = 20;
	private static final Integer HORIZONTAL_FIRST_TABULATION = 25;
	private static final Integer HORIZONTAL_SECOND_TABULATION = 15;
	private static final Integer BUTTON_BORDER = 50;
	private static final Integer IMAGE_SIZE = 100;

	private final JMenuItem fileContainedMenu;
	private final JMenuItem[] helpMenuItem;

	private final Object[][] tableData = new Object[][]{};

	private final JLabel lblName = new JLabel("Name:");
	private final JLabel name = new JLabel();
	private final JLabel lblSurname = new JLabel("Surname:");
	private final JLabel surname = new JLabel();
	private final JLabel lblUsername = new JLabel("Username:");
	private final JLabel username = new JLabel();
	private final JLabel lblDate = new JLabel("Birth date: ");
	private final JLabel date = new JLabel();
	private final JLabel lblTotFilm = new JLabel("Purchased film:");
	private final JLabel totFilm = new JLabel();
	private final JLabel lblTotPurchased = new JLabel("Total purchase:");
	private final JLabel totPurchased = new JLabel();
	private final JButton delete = new JButton("Delete Film");
	private final JButton back = new JButton("Back");
	private final JButton edit = new JButton("Edit User");
	private final JButton details = new JButton("Details Film");
	private final JLabel lblUserImage = new JLabel();

	private final JTable table;

	private UserStatsController observer;
	/**
	 * Create a new UserStatsView.
	 */
	public UserStatsView() {

		super();

		this.setTitle("User Stats");
		this.setSize(900, 500); //frame size: 900 width, 500 height.
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

			private static final long serialVersionUID = 1;

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
		layout.putConstraint(SpringLayout.NORTH, scroll, 0, SpringLayout.NORTH, bar);
		layout.putConstraint(SpringLayout.SOUTH, scroll, 0, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, scroll, 600, SpringLayout.WEST, this.getContentPane());
		//600 is the width of the scroll

		this.add(this.lblName);
		layout.putConstraint(SpringLayout.NORTH, this.lblName, VERTICAL_GAP, SpringLayout.NORTH, bar);
		layout.putConstraint(SpringLayout.WEST, this.lblName, HORIZONTAL_FIRST_TABULATION, SpringLayout.EAST, scroll);

		this.add(this.name);
		layout.putConstraint(SpringLayout.NORTH, this.name, VERTICAL_GAP, SpringLayout.NORTH, bar);
		layout.putConstraint(SpringLayout.WEST, this.name, HORIZONTAL_SECOND_TABULATION, SpringLayout.EAST, this.lblName);

		this.add(this.lblSurname);
		layout.putConstraint(SpringLayout.NORTH, this.lblSurname, VERTICAL_GAP, SpringLayout.SOUTH, this.lblName);
		layout.putConstraint(SpringLayout.WEST, this.lblSurname, HORIZONTAL_FIRST_TABULATION, SpringLayout.EAST, scroll);

		this.add(this.surname);		
		layout.putConstraint(SpringLayout.NORTH, this.surname, VERTICAL_GAP, SpringLayout.SOUTH, this.lblName);
		layout.putConstraint(SpringLayout.WEST, this.surname, HORIZONTAL_SECOND_TABULATION, SpringLayout.EAST, this.lblSurname);

		this.add(this.lblUsername);
		layout.putConstraint(SpringLayout.NORTH, this.lblUsername, VERTICAL_GAP, SpringLayout.SOUTH, this.lblSurname);
		layout.putConstraint(SpringLayout.WEST, this.lblUsername, HORIZONTAL_FIRST_TABULATION, SpringLayout.EAST, scroll);

		this.add(this.username);
		layout.putConstraint(SpringLayout.NORTH, this.username, VERTICAL_GAP, SpringLayout.SOUTH, this.lblSurname);
		layout.putConstraint(SpringLayout.WEST, this.username, HORIZONTAL_SECOND_TABULATION, SpringLayout.EAST, this.lblUsername);

		this.add(this.lblDate);
		layout.putConstraint(SpringLayout.NORTH, this.lblDate, VERTICAL_GAP, SpringLayout.SOUTH, this.lblUsername);
		layout.putConstraint(SpringLayout.WEST, this.lblDate, HORIZONTAL_FIRST_TABULATION, SpringLayout.EAST, scroll);

		this.add(this.date);
		layout.putConstraint(SpringLayout.NORTH, this.date, VERTICAL_GAP, SpringLayout.SOUTH, this.lblUsername);
		layout.putConstraint(SpringLayout.WEST, this.date, HORIZONTAL_SECOND_TABULATION, SpringLayout.EAST, this.lblDate);

		this.add(this.lblTotFilm);
		layout.putConstraint(SpringLayout.NORTH, this.lblTotFilm, VERTICAL_GAP, SpringLayout.SOUTH, this.lblDate);
		layout.putConstraint(SpringLayout.WEST, this.lblTotFilm, HORIZONTAL_FIRST_TABULATION, SpringLayout.EAST, scroll);

		this.add(this.totFilm);
		layout.putConstraint(SpringLayout.NORTH, this.totFilm, VERTICAL_GAP, SpringLayout.SOUTH, this.lblDate);
		layout.putConstraint(SpringLayout.WEST, this.totFilm, HORIZONTAL_SECOND_TABULATION, SpringLayout.EAST, this.lblTotFilm);

		this.add(this.lblTotPurchased);
		layout.putConstraint(SpringLayout.NORTH, this.lblTotPurchased, VERTICAL_GAP, SpringLayout.SOUTH, this.lblTotFilm);
		layout.putConstraint(SpringLayout.WEST, this.lblTotPurchased, HORIZONTAL_FIRST_TABULATION, SpringLayout.EAST, scroll);

		this.add(this.totPurchased);
		layout.putConstraint(SpringLayout.NORTH, this.totPurchased, VERTICAL_GAP, SpringLayout.SOUTH, this.lblTotFilm);
		layout.putConstraint(SpringLayout.WEST, this.totPurchased, HORIZONTAL_SECOND_TABULATION, SpringLayout.EAST, this.lblTotFilm);

		this.add(this.back);
		layout.putConstraint(SpringLayout.SOUTH, this.back, -VERTICAL_GAP, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.back, BUTTON_BORDER, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.back, -BUTTON_BORDER, SpringLayout.EAST, this.getContentPane());

		this.add(this.delete);
		layout.putConstraint(SpringLayout.SOUTH, this.delete, -VERTICAL_GAP, SpringLayout.NORTH, this.back);
		layout.putConstraint(SpringLayout.WEST, this.delete, BUTTON_BORDER, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.delete, -BUTTON_BORDER, SpringLayout.EAST, this.getContentPane());

		this.add(this.edit);
		layout.putConstraint(SpringLayout.SOUTH, this.edit, -VERTICAL_GAP, SpringLayout.NORTH, this.delete);
		layout.putConstraint(SpringLayout.WEST, this.edit, BUTTON_BORDER, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.edit, -BUTTON_BORDER, SpringLayout.EAST, this.getContentPane());

		this.add(this.details);
		layout.putConstraint(SpringLayout.SOUTH, this.details, -VERTICAL_GAP, SpringLayout.NORTH, this.edit);
		layout.putConstraint(SpringLayout.WEST, this.details, BUTTON_BORDER, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.details, -BUTTON_BORDER, SpringLayout.EAST, this.getContentPane());

		this.add(this.lblUserImage);
		layout.putConstraint(SpringLayout.EAST, this.lblUserImage, -VERTICAL_GAP, SpringLayout.EAST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.lblUserImage, VERTICAL_GAP, SpringLayout.NORTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.lblUserImage, -IMAGE_SIZE, SpringLayout.EAST, this.lblUserImage);
		layout.putConstraint(SpringLayout.SOUTH, this.lblUserImage, IMAGE_SIZE, SpringLayout.NORTH, this.lblUserImage);
		this.lblUserImage.setToolTipText("Double Click to change profile image");

		this.delete.addActionListener(this);
		this.edit.addActionListener(this);
		this.back.addActionListener(this);
		this.table.addMouseListener(this);
		this.details.addActionListener(this);
		this.lblUserImage.addMouseListener(this);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(final WindowEvent e) {
				final User user = observer.getUser();
				observer.generateTable();
				name.setText(user.getName());
				surname.setText(user.getSurname());
				username.setText(user.getUserID());
				final Calendar calendar = user.getDate();
				date.setText(calendar.get(Calendar.DATE) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR));
				totFilm.setText(observer.setNumFilm());
				totPurchased.setText(observer.setPurchasedFilm());
				lblUserImage.setIcon(observer.getProfileImage());
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
		} else if (selection.equals(this.helpMenuItem[MENU_IDX_ABOUT])) {
			if (this.observer.checkUser()) {
				this.observer.showCreditsView();
			}
		} else if (selection.equals(this.helpMenuItem[MENU_IDX_CONTACT_US])) {
			if (this.observer.checkUser()) {
				this.observer.showFeedbackView();
			}
		} else if (selection.equals(this.fileContainedMenu)) {
			if (this.observer.checkUser()) {
				this.observer.doExit();
			}

		} else if (selection.equals(this.details)) {
			if (this.observer.checkUser()) {
				try {
					final Integer idFilm = (Integer) this.table.getValueAt(this.table.getSelectedRow(), 0);
					this.observer.showDetailsFilm(idFilm);
				} catch (ArrayIndexOutOfBoundsException exc) {
					this.observer.saveError(exc);
					this.observer.showWarningDialog(SELECT_FILM);
				}
			}
		} else if (selection.equals(this.edit)) {
			this.observer.doEditUser();
		} else if (selection.equals(this.delete)) {
			try {
				final Integer value = (Integer) this.table.getValueAt(this.table.getSelectedRow(), 0);
				if (this.observer.doDelete(value)) {
					((DefaultTableModel) this.table.getModel()).removeRow(this.table.getSelectedRow());
				}
			} catch (ArrayIndexOutOfBoundsException exc) {
				this.observer.saveError(exc);
				this.observer.showWarningDialog(SELECT_FILM);
			}
		}
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		if (this.observer.checkUser()) {
			if (e.getClickCount() == 2 && !e.isConsumed() && e.getComponent().equals(this.table)) {
				e.consume();
				try {
					final Integer codFilm = (Integer) this.table.getValueAt(this.table.getSelectedRow(), 0);
					this.observer.showDetailsFilm(codFilm);
				} catch (ArrayIndexOutOfBoundsException exc) {
					this.observer.saveError(exc);
					this.observer.showWarningDialog(SELECT_FILM);
				}
			}
			if (e.getClickCount() == 2 && !e.isConsumed() && e.getComponent().equals(this.lblUserImage)) {
				e.consume();
				if (this.observer.changeProfileImage()) {
					final ImageIcon newImage = this.observer.getProfileImage();
					newImage.getImage().flush();
					this.lblUserImage.setIcon(newImage);
				}

			}

		}

	}
	/**
	 * Attach the observer of the controller to the view.
	 * @param userStatsController controller for this view
	 */
	public void attachObserver(final UserStatsController userStatsController) {
		this.observer = userStatsController;

	}
	/**
	 * Set button visibility.
	 */
	public void setEditableVisible() {
		this.edit.setVisible(false);
	}
	/**
	 * Set the value of this field.
	 */
	public void setFilmVal() {
		this.totFilm.setText(this.observer.setNumFilm());
	}
	/**
	 * Set the value of this field.
	 */
	public void setPurchasedVal() {
		this.totPurchased.setText(this.observer.setPurchasedFilm());
	}
	/**
	 * Add to the table the raw passed as parameter.
	 * @param obj new row to add to the table
	 */
	public void newRow(final Object[] obj) {
		((DefaultTableModel) this.table.getModel()).addRow(obj);		
	}

}