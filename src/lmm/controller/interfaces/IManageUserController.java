package lmm.controller.interfaces;

/**
 * Interface that define the {@link lmm.controller.admin.ManageUserController}.
 * @author Roberto Reibaldi
 *
 */
public interface IManageUserController {
	/**
	 * Method that allows to the admin to modify the parameter of the user.
	 * @param username parameter that pass the username of the user.
	 */
	void doEditUser(final String username);
	/**
	 * Method that allows to the admin to delete an user selected by userId.	
	 * @param username parameter that pass the username of the user
	 * @return boolean
	 */
	boolean doDeleteUser(final String username);
	/**
	 * Method that allows to the admin to see the view of UserStats.
	 * @param username parameter that pass the username of the user
	 */
	void doUserStats(final String username);
	/**
	 * Method that generates an User list.	
	 */
	void generateTable();
	/**
	 * Method that allows the admin to add a new user.
	 */
	void addUser();
	/**
	 * Method that load file.
	 */
	void doLoading();
	/**
	 * Method that save file.	
	 */
	void doSaving();

}
