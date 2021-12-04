package lmm.controller.interfaces;

/**
 * Interface that define the {@link lmm.controller.LoginController}.
 * @author Roberto Reibaldi, Luca Pascucci
 * 
 */
public interface ILoginController {
	/**
	 * Method that manage the login by the insert of an userID and a password.
	 * @param username this parameter pass the username.
	 * @param password this parameter pass the password chosen by the user.
	 */
	void doLogin(final String username, final String password);
	/**
	 * Method that allows the registration of a new user.
	 */
	void doNewUser();
	
	/**
	 * Method that creates folders used to save data.
	 */
	void createWorkspace();
}
