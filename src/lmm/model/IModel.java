package lmm.model;

import java.util.ArrayDeque;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.Vector;

import lmm.exception.DuplicateBoughtException;
import lmm.exception.DuplicateUsernameException;
import lmm.exception.InvalidContentException;
import lmm.exception.UserNotFoundException;

/**
 * Defines the model of the LMM application.
 * @author Marco Sperandeo
 *
 */
public interface IModel {
	/**
	 * Return the set of the film Id that is used as key.
	 * @return Set<Integer>
	 */
	Set<Integer> getKeySetFilm();

	/**
	 * Returns the film with id=filmId.
	 * @param filmCode code of the film
	 * @return {@link lmm.model.IFilm}
	 */
	IFilm getFilm(Integer filmCode);

	/**
	 * Returns the existing films.
	 * @return Map<Integer, IFilm>
	 */
	Map<Integer, IFilm> getFilms();

	/**
	 * Add a film to the model.
	 * @param newTitle string title
	 * @param newPrice price of the film
	 * @param newPlot plot of the film
	 * @param newDate release date 
	 * @param newGenre genre of the film
	 * @param newCover default cover
	 * @throws InvalidContentException combination of data entered incorrect
	 */
	void addFilm(String newTitle, Integer newPrice, String newPlot, Integer newDate, FilmType newGenre, String newCover) throws InvalidContentException;

	/**
	 * Edits an existing film.
	 * @param newFilmCode code of the film
	 * @param newTitle string title
	 * @param newPrice price of the film
	 * @param newPlot plot of the film
	 * @param newDate release date
	 * @param newGenre genre of the film
	 * @param newCover default cover
	 * @param newTrailer boolean trailer
	 * @throws InvalidContentException combination of data entered incorrect
	 */
	void editFilm(Integer newFilmCode, String newTitle, Integer newPrice, String newPlot, Integer newDate, FilmType newGenre, Vector<String> newCover, boolean newTrailer) throws InvalidContentException;

	/**
	 * Deletes a film with the id=filmId.
	 * @param newFilmCode code of the film
	 * @throws InvalidContentException caused when there isn't input
	 */
	void deleteFilm(Integer newFilmCode) throws InvalidContentException;

	/**
	 * Add a trailer to the film that have id=filmId.
	 * @param newFilmCode code of the film
	 * @throws InvalidContentException combination of data entered incorrect
	 */
	void addFilmTrailer(Integer newFilmCode) throws InvalidContentException;

	/**
	 * Return the recent films.
	 * @return ArrayDeque<Integer>
	 */
	ArrayDeque<Integer> getRecentFilms();

	/**
	 * Check that a film has trailer with the id=filmId.
	 * @param newFilmCode code of the film
	 * @return boolean
	 * @throws InvalidContentException combination of data entered incorrect
	 */
	boolean checkTrailer(Integer newFilmCode) throws InvalidContentException;

	/**
	 * Add the film cover with the id=filmId.
	 * @param newFilmCode code of the film
	 * @param newCover index of the cover
	 * @throws InvalidContentException combination of data entered incorrect
	 */
	void addFilmCover(Integer newFilmCode, Integer newCover) throws InvalidContentException;

	/**
	 * Allows the vision of next cover with the id=filmId.
	 * @param newFilmCode code of the film
	 * @return Integer
	 * @throws InvalidContentException combination of data entered incorrect
	 */
	Integer nextFilmCover(Integer newFilmCode) throws InvalidContentException;

	/**
	 * Returns the films bought by a user with the id=userId.
	 * @param userId string userId
	 * @return Integer
	 */
	Set<Integer> getFilmBought(String userId);

	/**
	 * Add to an user the film bought with the id=filmId.
	 * @param newFilmCode code of the film
	 * @throws DuplicateBoughtException film already bought
	 */
	void addBoughtFilms(Integer newFilmCode) throws DuplicateBoughtException;

	/**
	 * Returns the total of user's purchases with the id=userId.
	 * @param userId string userId
	 * @return Integer
	 */
	Integer getTotPurchasedUser(String userId);

	/**
	 * Returns the incomes of all the users.
	 * @return Integer
	 */
	Integer getTotCash();

	/**
	 * Returns the user's favorites films with the id=userId.
	 * @param userId string userId
	 * @return Set<Integer>
	 * @throws UserNotFoundException data inserted invalid
	 */
	Set<Integer> getFavoritesFilms(String userId) throws UserNotFoundException;

	/**
	 * Add to the current user a film as favorite with the id=filmId.
	 * @param newFilmCode code of the film
	 * @throws UserNotFoundException data inserted invalid
	 */
	void addFavoritesFilms(Integer newFilmCode) throws UserNotFoundException;

	/**
	 * Returns the statistic of the film with the id=filmId.
	 * @param filmId code of the film
	 * @return Pair<Integer, Integer>
	 */
	Pair<Integer, Integer> getStatic(Integer filmId);

	/**
	 * When a film is visited, the first integer increase by 1, with the id=filmId.
	 * @param newFilmCode code of the film
	 */
	void visitedFilm(Integer newFilmCode);

	/**
	 * When a film is bought, the second integer increase by 1, with the id=filmId.
	 * @param newFilmCode code of the film
	 */
	void boughtFilm(Integer newFilmCode);

	/**
	 * Add an user to the model.
	 * @param newUser 
	 * @throws DuplicateUsernameException username already existing
	 */
	void addUser(User newUser) throws DuplicateUsernameException;

	/**
	 * Returns the list of users.
	 * @return Set<User>
	 */
	Set<User> getUsers();

	/**
	 * Returns the users connect.
	 * @return {@link lmm.model.IUser}
	 */
	IUser getCurrentUser();

	/**
	 * Set the current user working with the application.
	 * @param user current user
	 */
	void setCurrentUser(IUser user);

	/**
	 * Return the user associated with the userId passed as parameter.
	 * @param userId string user
	 * @return {@link lmm.model.User}
	 * @throws UserNotFoundException data inserted invalid
	 */
	User getUser(String userId) throws UserNotFoundException;

	/**
	 * Edits an existing user.
	 * @param modifiedUser 
	 * @param newImage string image
	 */
	void editUser(User modifiedUser, String newImage);

	/**
	 * Delete an user with id=userId.
	 * @param newUserId string userId
	 */
	void deleteUser(String newUserId);

	/**
	 * Delete an user's film with id=userId and id=filmID.
	 * @param newUserId string userId
	 * @param newFilmCode code of the film
	 */
	void deleteFilmUser(String newUserId, Integer newFilmCode);

	/**
	 * Changes the admin's password.
	 * @param newPassword string password
	 */
	void changeAdminPassword(String newPassword);

	/**
	 * Returns the admin.
	 * @return {@link lmm.model.Admin}
	 */
	Admin getAdmin();

	/**
	 * Returns the film's list ordered based on the parameter inserted.
	 * @param order type of sorting
	 * @return ArrayDeque<Integer>
	 */
	ArrayDeque<Integer> getOrderFilms(String order);

	/**
	 * Returns the film's list ordered in the basis of id's film.
	 * @return SortedSet<Integer>
	 */
	SortedSet<Integer> getOrderFilm();

	/**
	 * Removes the film witch code is passed as parameter.
	 * @param newFilmCode code of the film
	 * @throws UserNotFoundException data inserted invalid
	 */
	void removeFavoriteFilm(Integer newFilmCode) throws UserNotFoundException;
}