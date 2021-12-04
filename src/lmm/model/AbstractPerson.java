package lmm.model;

import java.io.Serializable;

/**
 * This class modelling a generic Person.
 * @author Marco Sperandeo
 *
 */
public abstract class AbstractPerson implements IUser, Serializable {

	private static final long serialVersionUID = 1;
	private final String name;
	private final String surname;
	private final String userID;
	private final String password;

	/**
	 * This constructor creates a new person with the parameters provided in input.
	 * @param newUserID string username
	 * @param newPassword string password
	 * @param newName string name
	 * @param newSurname string surname
	 */
	public AbstractPerson(final String newUserID, final String newPassword, final String newName, final String newSurname) {
		this.name = newName;
		this.surname = newSurname;
		this.userID = newUserID;
		this.password = newPassword;
	}

	/**
	 * Return the person name.
	 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Return the person surname.
	 * @return String
	 */
	public String getSurname() {
		return this.surname;
	}	
	/**
	 * Return the user ID.
	 * @return String
	 */
	public String getUserID() {
		return this.userID;
	}
	/**
	 * Return the password.
	 * @return String
	 */
	public String getPassword() {
		return this.password;
	}
}
