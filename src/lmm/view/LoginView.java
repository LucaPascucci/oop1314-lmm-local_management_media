package lmm.view;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import lmm.controller.LoginController;

/**
 * This class creates the start view of the program.
 * @author Filippo Nicolini, Marco Sperandeo
 *
 */
public class LoginView extends AbstractView {

	private static final long serialVersionUID = 1;
	
	private static final Integer VERTICAL_LBL_GAP = 20;
	private static final Integer VERTICAL_TXT_GAP = 15;
	private static final Integer BUTTON_SOUTH_GAP = 20;
	private static final Integer BUTTON_GAP_TO_FRAME = 125;
	private static final Integer HORIZONTAL_FIRST_TABULATION = 25;
	private static final Integer HORIZONTAL_SECOND_TABULATION = 100;
	private static final Integer TEXTFIELD_SIZE = 15;

	private static final Integer MENU_IDX_CONTACT_US = 0;
	private static final Integer MENU_IDX_ABOUT = 1;

	private final JLabel lblUser = new JLabel("User");
	private final JLabel lblPassword = new JLabel("Password");
	private final JButton login = new JButton("Login");
	private final JButton newUser = new JButton("New User");
	private final JTextField txtUserID = new JTextField(TEXTFIELD_SIZE);
	private final JPasswordField txtPassword = new JPasswordField(TEXTFIELD_SIZE);

	private final JMenuItem exitItem;
	private final JMenuItem[] helpMenuItem;

	private LoginController observer;

	/**
	 * This constructor creates a new LoginView.
	 */
	public LoginView() {

		super();
		this.setTitle("Login");
		this.setSize(300, 200); //frame size: 300 width, 200 height.
		this.setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		JMenuBar bar;
		JMenu fileMenu;
		JMenu helpMenu;

		bar = new JMenuBar();
		fileMenu = new JMenu("File");
		helpMenu = new JMenu("Help");
		this.exitItem = new JMenuItem("Exit");
		fileMenu.add(this.exitItem);
		this.exitItem.addActionListener(this);
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

		final SpringLayout layout = new SpringLayout();
		this.setLayout(layout);

		this.add(this.lblUser);
		layout.putConstraint(SpringLayout.NORTH, this.lblUser, VERTICAL_LBL_GAP, SpringLayout.NORTH, bar);
		layout.putConstraint(SpringLayout.WEST, this.lblUser, HORIZONTAL_FIRST_TABULATION, SpringLayout.WEST, this.getContentPane());

		this.add(this.txtUserID);
		this.txtUserID.setToolTipText("Insert your username");
		layout.putConstraint(SpringLayout.NORTH, this.txtUserID, VERTICAL_TXT_GAP, SpringLayout.NORTH, bar);
		layout.putConstraint(SpringLayout.WEST, this.txtUserID, HORIZONTAL_SECOND_TABULATION, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.txtUserID, -HORIZONTAL_FIRST_TABULATION, SpringLayout.EAST, this.getContentPane());

		this.add(this.lblPassword);
		layout.putConstraint(SpringLayout.NORTH, this.lblPassword, VERTICAL_LBL_GAP, SpringLayout.SOUTH, this.txtUserID);
		layout.putConstraint(SpringLayout.WEST, this.lblPassword, HORIZONTAL_FIRST_TABULATION, SpringLayout.WEST, this.getContentPane());

		this.add(this.txtPassword);
		this.txtPassword.setToolTipText("Insert your password");
		layout.putConstraint(SpringLayout.NORTH, this.txtPassword, VERTICAL_TXT_GAP, SpringLayout.SOUTH, this.txtUserID);
		layout.putConstraint(SpringLayout.WEST, this.txtPassword, HORIZONTAL_SECOND_TABULATION, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.txtPassword, -HORIZONTAL_FIRST_TABULATION, SpringLayout.EAST, this.getContentPane());

		this.add(this.newUser);
		layout.putConstraint(SpringLayout.SOUTH, this.newUser, -BUTTON_SOUTH_GAP, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.newUser, HORIZONTAL_FIRST_TABULATION, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.newUser, BUTTON_GAP_TO_FRAME, SpringLayout.WEST, this.getContentPane());

		this.add(this.login);
		layout.putConstraint(SpringLayout.SOUTH, this.login, -BUTTON_SOUTH_GAP, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.login, -HORIZONTAL_FIRST_TABULATION, SpringLayout.EAST, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.login, -BUTTON_GAP_TO_FRAME, SpringLayout.EAST, this.getContentPane());

		this.newUser.addActionListener(this);
		this.login.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(final WindowEvent e) {
				observer.doExit();
			}

		});
		this.setLocation();
		this.setIcon();
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		final Object selection = e.getSource();
		if (selection.equals(this.login)) {
			this.observer.doLogin(this.txtUserID.getText(), new String(this.txtPassword.getPassword()));
		}
		if (selection.equals(this.newUser)) {
			this.observer.doNewUser();
		}
		if (selection.equals(this.exitItem)) {
			this.observer.doBack();
		}
		if (selection.equals(this.helpMenuItem[MENU_IDX_ABOUT])) {
			this.observer.showCreditsView();
		}
		if (selection.equals(this.helpMenuItem[MENU_IDX_CONTACT_US])) {
			this.observer.showFeedbackView();
		}

	}

	@Override
	public void mouseReleased(final MouseEvent e) {	}
	/**
	 * Attach the observer of the controller to the view.
	 * @param loginController controller for this view
	 */
	public void attachObserver(final LoginController loginController) {
		this.observer = loginController;
	}
	/**
	 * Set indicated fields to void.
	 */
	public void resetField() {
		this.txtUserID.setText("");
		this.txtPassword.setText("");
	}

}
