package lmm.view.user;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import com.toedter.calendar.JDateChooser;

import lmm.controller.user.EditableUserController;
import lmm.model.User;
import lmm.view.AbstractView;

/**
 * Class used to create a new user or to edit a registered user .
 * @author Filippo Nicolini
 *
 */
public class EditableUserView extends AbstractView {

	private static final long serialVersionUID = 1;

	private static final Integer VERTICAL_GAP = 20;
	private static final Integer MINIMUM_GAP = 5;
	private static final Integer HORIZONTAL_FIRST_TABULATION = 25;
	private static final Integer HORIZONTAL_SECOND_TABULATION = 100;
	private static final Integer TEXTFIELD_SIZE = 15;

	private final JLabel lblName = new JLabel("Name");
	private final JLabel lblSurname = new JLabel("Surname");
	private final JLabel lblPassword = new JLabel("Password");
	private final JLabel lblUsername = new JLabel("Username");
	private final JLabel lblData = new JLabel("Birth Date");
	private final JTextField txtName = new JTextField(TEXTFIELD_SIZE);
	private final JTextField txtsurname = new JTextField(TEXTFIELD_SIZE);
	private final JPasswordField txtpassword = new JPasswordField(TEXTFIELD_SIZE);
	private final JTextField txtusername = new JTextField(TEXTFIELD_SIZE);
	private final JCheckBox chk = new JCheckBox();
	private final JLabel passwd = new JLabel("Mostra Password");
	private final JDateChooser myDateChooser = new JDateChooser();

	private final JButton register = new JButton("");
	private final JButton cancel = new JButton("Cancel");

	private final char defaultChar = this.txtpassword.getEchoChar();
	private EditableUserController observer;

	/**
	 * Create a new EditableUserView.
	 * 
	 */
	public EditableUserView() {

		super();

		this.setSize(420, 350); //frame size: 420 width, 350 height.
		this.setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		final SpringLayout layout = new SpringLayout();
		this.setLayout(layout);

		this.add(this.lblName);
		layout.putConstraint(SpringLayout.WEST, this.lblName, HORIZONTAL_FIRST_TABULATION, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.lblName, VERTICAL_GAP, SpringLayout.NORTH, this.getContentPane());

		this.add(this.txtName);
		layout.putConstraint(SpringLayout.NORTH, this.txtName, VERTICAL_GAP, SpringLayout.NORTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.txtName, HORIZONTAL_SECOND_TABULATION, SpringLayout.WEST, this.getContentPane());

		this.add(this.lblSurname);
		layout.putConstraint(SpringLayout.WEST, this.lblSurname, HORIZONTAL_FIRST_TABULATION, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.lblSurname, VERTICAL_GAP, SpringLayout.SOUTH, this.txtName);

		this.add(this.txtsurname);
		layout.putConstraint(SpringLayout.NORTH, this.txtsurname, VERTICAL_GAP, SpringLayout.SOUTH, this.txtName);
		layout.putConstraint(SpringLayout.WEST, this.txtsurname, HORIZONTAL_SECOND_TABULATION, SpringLayout.WEST, this.getContentPane());

		this.add(this.lblUsername);
		layout.putConstraint(SpringLayout.WEST, this.lblUsername, HORIZONTAL_FIRST_TABULATION, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.lblUsername, VERTICAL_GAP, SpringLayout.SOUTH, this.txtsurname);

		this.add(this.txtusername);
		layout.putConstraint(SpringLayout.NORTH, this.txtusername, VERTICAL_GAP , SpringLayout.SOUTH, this.txtsurname);
		layout.putConstraint(SpringLayout.WEST, this.txtusername, HORIZONTAL_SECOND_TABULATION, SpringLayout.WEST, this.getContentPane());

		this.add(this.lblPassword);
		layout.putConstraint(SpringLayout.WEST, this.lblPassword, HORIZONTAL_FIRST_TABULATION, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.lblPassword, VERTICAL_GAP, SpringLayout.SOUTH, this.txtusername);

		this.add(this.txtpassword);
		layout.putConstraint(SpringLayout.NORTH, this.txtpassword, VERTICAL_GAP, SpringLayout.SOUTH, this.txtusername);
		layout.putConstraint(SpringLayout.WEST, this.txtpassword, HORIZONTAL_SECOND_TABULATION, SpringLayout.WEST, this.getContentPane());

		this.add(this.chk);
		layout.putConstraint(SpringLayout.NORTH, this.chk, MINIMUM_GAP, SpringLayout.SOUTH, this.txtpassword);
		layout.putConstraint(SpringLayout.WEST, this.chk, HORIZONTAL_SECOND_TABULATION, SpringLayout.WEST, this.getContentPane());

		this.add(this.passwd);
		layout.putConstraint(SpringLayout.NORTH, this.passwd, MINIMUM_GAP, SpringLayout.SOUTH, this.txtpassword);
		layout.putConstraint(SpringLayout.WEST, this.passwd, MINIMUM_GAP, SpringLayout.EAST, this.chk);

		this.add(this.lblData);
		layout.putConstraint(SpringLayout.WEST, this.lblData, HORIZONTAL_FIRST_TABULATION, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.lblData, VERTICAL_GAP, SpringLayout.SOUTH, this.chk);

		this.add(this.myDateChooser);
		layout.putConstraint(SpringLayout.NORTH, this.myDateChooser, VERTICAL_GAP, SpringLayout.SOUTH, this.chk);
		layout.putConstraint(SpringLayout.WEST, this.myDateChooser, HORIZONTAL_SECOND_TABULATION, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.myDateChooser, 125, SpringLayout.WEST, this.myDateChooser);
		//125 is the width of the myDataChooser

		this.add(this.register);
		layout.putConstraint(SpringLayout.SOUTH, this.register, -VERTICAL_GAP, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.register, -VERTICAL_GAP, SpringLayout.EAST, this.getContentPane());

		this.add(this.cancel);
		layout.putConstraint(SpringLayout.SOUTH, this.cancel, -VERTICAL_GAP, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.cancel, -VERTICAL_GAP, SpringLayout.WEST, this.register);

		this.chk.addActionListener(this);
		this.register.addActionListener(this);
		this.cancel.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(final WindowEvent e) {
				final User editableUser = observer.getEditableUser();
				if (editableUser != null) {
					txtName.setText(editableUser.getName());
					txtsurname.setText(editableUser.getSurname());
					txtpassword.setText(editableUser.getPassword());
					txtusername.setText(editableUser.getUserID());
					final Calendar userDate = editableUser.getDate();
					myDateChooser.setCalendar(userDate);
					txtusername.setEditable(false);
					register.setText("Save");
					setTitle("Edit User");
				} else {
					register.setText("Register");
					setTitle("New User");
				}
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
		final Object selection = e.getSource();
		if (selection.equals(this.cancel)) {
			this.observer.doBack();
		} else if (selection.equals(this.chk)) {
			if (this.chk.isSelected()) {
				this.txtpassword.setEchoChar('\0');
			} else {
				this.txtpassword.setEchoChar(this.defaultChar);
			}
		} else if (selection.equals(this.register)) {
			this.observer.doRegister(this.txtName.getText(), this.txtsurname.getText(), this.txtusername.getText(), new String(this.txtpassword.getPassword()), this.myDateChooser.getCalendar());
		}

	}

	@Override
	public void mouseReleased(final MouseEvent e) { }
	
	/**
	 * Attach the observer of the controller to the view.
	 * @param editableUserController controller for this view
	 */
	public void attachObserver(final EditableUserController editableUserController) {
		this.observer = editableUserController;
	}
	
	/**
	 * Set to null the JTextField indicated.
	 */
	public void resetUserIDField() {
		this.txtusername.setText("");
	}

}