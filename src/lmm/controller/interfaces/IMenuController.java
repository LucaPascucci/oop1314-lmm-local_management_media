package lmm.controller.interfaces;

/**
 * Interface that define the {@link lmm.controller.MenuController}.
 * @author Roberto Reibaldi
 *
 */
public interface IMenuController {
	/**
	 * Method that return the type of current user, define if it is an normal user or an admin.
	 * @return boolean
	 */
	boolean getUserControll();
	/**
	 * Method that by UserControll, allow the current user to see a determinate set of view.
	 */
	void doFirst();
	/**
	 * Method that by UserControll, allow the current user to see a determinate set of view.	
	 */
	void doSecond();
	/**
	 * Method that by UserControll, allow the current user to see a determinate set of view.	
	 */
	void doThird();
}
