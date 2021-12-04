package lmm.model;

/**
 * Defines an user.
 * @author Marco Sperandeo
 *
 */
public interface IUser {

	/**
	 * Returns the user's ID.
	 * @return String
	 */
	String getUserID();

	/**
	 * Returns the user's password.
	 * @return String
	 */
	String getPassword();

}