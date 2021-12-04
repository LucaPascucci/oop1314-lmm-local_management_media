package lmm.model;

import java.io.Serializable;

/**
 * Models a generic admin.
 * @author Marco Sperandeo
 *
 */
public class Admin extends AbstractPerson implements Serializable {

	private static final long serialVersionUID = 1;

	/**
	 * This extends the abstract class, and it creates the admin with the parameters provided in input.
	 * @param userID string username
	 * @param password string password
	 * @param name string name
	 * @param surname string surname
	 */
	public Admin(final String userID, final String password, final String name, final String surname) {
		super(userID, password, name, surname);
	}

}