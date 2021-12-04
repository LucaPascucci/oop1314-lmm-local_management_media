package lmm.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * Models a generic user.
 * @author Marco Sperandeo
 *
 */
public class User extends AbstractPerson implements Serializable {

	private static final long serialVersionUID = 1;

	private final Calendar userBirthdate;
	private final String profileImage;
	private final Set<Integer> favorites;

	/**
	 * Creats a new User with the parameters provided in input.
	 * @param userID string username
	 * @param password string password
	 * @param name string name
	 * @param surname string surname
	 * @param date	user birthday
	 * @param image user image
	 */
	public User(final String userID, final String password, final String name, final String surname, final Calendar date, final String image) {
		super(userID, password, name, surname);
		this.userBirthdate = date;
		this.profileImage = image;
		this.favorites = new HashSet<>();
	}

	/**
	 * Creats a new User with the parameters provided in input that overwrite the previous.
	 * @param userID string username
	 * @param password string password
	 * @param name string name
	 * @param surname string surname
	 * @param date user birthdate
	 * @param image user image
	 * @param fav user's favorites films
	 */
	public User(final String userID, final String password, final String name, final String surname, final Calendar date, final String image, final Set<Integer> fav) {
		super(userID, password, name, surname);
		this.userBirthdate = date;
		this.profileImage = image;
		this.favorites = fav;
	}

	/**
	 * Return person birthdate.
	 * @return Calendar
	 */
	public Calendar getDate() {
		return this.userBirthdate;
	}

	/**
	 * Return person profile image.
	 * @return String
	 */
	public String getProfileImage() {
		return this.profileImage;
	}

	/**
	 * Return the set of the favorites films.
	 * @return Set<Integer>
	 */
	public Set<Integer> getFavorites() {
		return favorites;
	}
}
