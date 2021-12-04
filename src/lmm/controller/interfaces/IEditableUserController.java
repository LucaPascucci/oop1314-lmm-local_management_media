package lmm.controller.interfaces;

import java.util.Calendar;

import lmm.model.User;
/**
 * Interface that define {@link lmm.controller.user.EditableUserController}.
 * @author Roberto Reibaldi
 *
 */
public interface IEditableUserController {
	/**
	 * Return the view of the info of the current User.
	 * @return {@link lmm.model.User}
	 */
	User getEditableUser();
	/**
	 * Method that manages and controls the registration of a new generic user.	
	 * @param name parameter that pass the name of the user.
	 * @param surname parameter that pass the surname of the user.
	 * @param userId parameter that pass the identification code of the user.
	 * @param password parameter that pass the password chosen from the user. 
	 * @param date parameter that pass the birth date of the user.
	 */
	void doRegister(final String name, final String surname, final String userId, final String password, final Calendar date);

}
