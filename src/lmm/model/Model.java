package lmm.model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import lmm.exception.DuplicateBoughtException;
import lmm.exception.DuplicateUsernameException;
import lmm.exception.InvalidContentException;
import lmm.exception.UserNotFoundException;

/**
 * This class is the model. Contains all the type of the data.
 * @author Marco Sperandeo
 *
 */
public class Model implements IModel, Serializable {

	private static final long serialVersionUID = 1;

	private static final Integer DEFAULT_VISIT = 0;
	private static final Integer DEFAULT_DOWNLOAD = 0;
	private static final String TOP_BOUGHT_FILM = "bought";
	private static final String TOP_VISITED_FILM = "visited";
	private static final String IMAGE_FORMAT = ".png";
	private static final String VIDEO_FORMAT = ".flv";
	private static final String DEFAULT_COVER = "Default_Cover.png";
	private static final String DEFAULT_IMAGE = "Default_User.png";
	private static final String DEFAULT_PATH = "res/";
	private static final Integer RECENT_FILM_SIZE = 20;

	private Admin admin;
	private IUser currentUser;
	private final Map<Integer, IFilm> films;
	private SortedSet<Integer> filmEliminated;
	private Integer indFilms;
	private final Set<User> users;
	private final Map<String, Set<Integer>> downloads;
	private final Map<Integer, Pair<Integer, Integer>> statics;
	private final ArrayDeque<Integer> recentFilms;

	/**
	 * Creates a new Model object.
	 */
	public Model() {

		this.currentUser = null;
		this.admin = new Admin("admin", "admin", "admin", "admin");
		this.films = new HashMap<>();
		this.filmEliminated = new TreeSet<>();
		this.indFilms = 1;
		this.users = new HashSet<>();
		this.downloads = new HashMap<>(); 
		this.statics = new HashMap<>();
		this.recentFilms = new ArrayDeque<>();
	}

	private void checkContentFilm(final String title, final Integer price, final String plot, final Integer date) throws InvalidContentException {
		if (title == null || price < 0 || plot.equals("") || date.toString().length() > 4 || date.toString().length() < 4) {
			throw new InvalidContentException();
		}
	}

	@Override
	public Set<Integer> getKeySetFilm() {
		return this.films.keySet();
	}

	@Override
	public IFilm getFilm(final Integer filmCode) {
		return this.films.get(filmCode);
	}

	@Override
	public Map<Integer, IFilm> getFilms() {
		return this.films;
	}

	@Override
	public ArrayDeque<Integer> getRecentFilms() {
		return this.recentFilms;
	}

	@Override
	public void addFilm(final String newTitle, final Integer newPrice, final String newPlot, final Integer newDate, final FilmType newGenre, final String newCover)throws InvalidContentException {
		this.checkContentFilm(newTitle, newPrice, newPlot, newDate);
		final IFilm newfilm = new Film(newTitle, newPlot, newPrice, newDate, newGenre, newCover);
		if (!this.filmEliminated.isEmpty()) {
			final Integer firstAvaiable = this.filmEliminated.first();
			this.films.put(firstAvaiable, newfilm);
			this.filmEliminated.remove(firstAvaiable);
			this.statics.put(firstAvaiable, new Pair<>(DEFAULT_VISIT, DEFAULT_DOWNLOAD));
			if (this.recentFilms.size() > RECENT_FILM_SIZE) {
				this.recentFilms.removeLast();
			}
			this.recentFilms.addFirst(firstAvaiable);
		} else {
			this.films.put(this.indFilms, newfilm);
			this.statics.put(this.indFilms, new Pair<>(DEFAULT_VISIT, DEFAULT_DOWNLOAD));
			if (this.recentFilms.size() > RECENT_FILM_SIZE) {
				this.recentFilms.removeLast();
			}
			this.recentFilms.addFirst(indFilms);
			this.indFilms++;
		}

	}

	@Override
	public void editFilm(final Integer newFilmCode, final String newTitle, final Integer newPrice, final String newPlot, final Integer newDate, final FilmType newGenre, final Vector<String> newCover, final boolean newTrailer) throws InvalidContentException {
		if (newFilmCode == null) {
			throw new InvalidContentException();
		}
		final IFilm newfilm = new Film(newTitle, newPlot, newPrice, newDate, newGenre, newCover, newTrailer);
		this.films.put(newFilmCode, newfilm);

	}

	@Override
	public void deleteFilm(final Integer newFilmCode) throws InvalidContentException {
		if (!this.films.containsKey(newFilmCode) || newFilmCode == null) {
			throw new InvalidContentException();
		}

		String current = null;
		final Vector<String> covers = this.getFilm(newFilmCode).getImages();
		if (!covers.contains(DEFAULT_COVER)) {
			final Iterator<String> delete = covers.iterator();
			while (delete.hasNext()) {
				final File file = new File(DEFAULT_PATH + delete.next());
				file.delete();
			}
		}

		if (this.getFilm(newFilmCode).getTrailer()) {
			final File file = new File(DEFAULT_PATH + newFilmCode + VIDEO_FORMAT);
			file.delete();
		}

		this.films.remove(newFilmCode);
		this.filmEliminated.add(newFilmCode);
		this.statics.remove(newFilmCode);
		if (this.recentFilms.contains(newFilmCode)) {
			this.recentFilms.remove(newFilmCode);
		}
		final Iterator<String> it = this.downloads.keySet().iterator();
		while (it.hasNext()) {
			current = it.next();
			this.deleteFilmUser(current, newFilmCode);
		}
	}

	@Override
	public void addFilmTrailer(final Integer newFilmCode) throws InvalidContentException {
		if (!this.films.containsKey(newFilmCode) || newFilmCode == null) {
			throw new InvalidContentException();
		}
		final IFilm film = this.getFilm(newFilmCode);
		this.editFilm(newFilmCode, film.getTitle(), film.getPrice(), film.getPlot(), film.getDate(), film.getGenre(), film.getImages(), true);
	}

	@Override
	public boolean checkTrailer(final Integer newFilmCode) throws InvalidContentException {
		if (!this.films.containsKey(newFilmCode) || newFilmCode == null) {
			throw new InvalidContentException();
		}
		return this.getFilm(newFilmCode).getTrailer();
	}

	@Override
	public void addFilmCover(final Integer newFilmCode, final Integer newCover) throws InvalidContentException {
		if (!this.films.containsKey(newFilmCode) || newFilmCode == null) {
			throw new InvalidContentException();
		}
		final IFilm film = this.getFilm(newFilmCode);
		final Vector<String> covers = film.getImages();
		if (covers.contains(DEFAULT_COVER)) {
			covers.set(0, newFilmCode + "." + newCover + IMAGE_FORMAT);
		} else {
			covers.add(newFilmCode + "." + newCover + IMAGE_FORMAT);
		}
		this.editFilm(newFilmCode, film.getTitle(), film.getPrice(), film.getPlot(), film.getDate(), film.getGenre(), covers, film.getTrailer());

	}

	@Override
	public Integer nextFilmCover(final Integer newFilmCode) throws InvalidContentException {
		if (!this.films.containsKey(newFilmCode) || newFilmCode == null) {
			throw new InvalidContentException();
		}
		final IFilm film = this.getFilm(newFilmCode);
		final Vector<String> covers = film.getImages();
		if (covers.contains(DEFAULT_COVER)) {
			return 0;
		} else {
			return covers.size();
		}
	}

	@Override
	public Set<Integer> getFilmBought(final String userId) {
		return this.downloads.get(userId);
	}

	@Override
	public void addBoughtFilms(final Integer newFilmCode) throws DuplicateBoughtException {
		if (this.downloads.containsKey(currentUser.getUserID())) {
			if (this.downloads.get(currentUser.getUserID()) == null) {
				final Set<Integer> filmBuy = new HashSet<>();
				filmBuy.add(newFilmCode);
				this.downloads.put(this.currentUser.getUserID(), filmBuy);
			} else {
				final Set<Integer> filmBought = this.downloads.get(currentUser.getUserID());
				if (filmBought.contains(newFilmCode)) {
					throw new DuplicateBoughtException();
				}
				filmBought.add(newFilmCode);
				this.downloads.put(this.currentUser.getUserID(), filmBought);
			}
		}
	}

	@Override
	public Integer getTotPurchasedUser(final String newUserId) {
		final Iterator<Integer> it = this.getFilmBought(newUserId).iterator();
		Integer sum = 0;
		Integer current;
		while (it.hasNext()) {
			current = it.next();
			sum += this.getFilm(current).getPrice();
		}
		return sum;
	}

	@Override
	public Integer getTotCash() {
		final Iterator<User> it = this.getUsers().iterator();
		Integer sum = 0;
		IUser current;
		while (it.hasNext()) {
			current = it.next();
			sum += this.getTotPurchasedUser(current.getUserID());
		}
		return sum;
	}

	@Override
	public Set<Integer> getFavoritesFilms(final String userId) throws UserNotFoundException {
		return this.getUser(userId).getFavorites();
	}

	@Override
	public void addFavoritesFilms(final Integer newFilmCode) throws UserNotFoundException {
		final User currUser = this.getUser(this.currentUser.getUserID());
		final Set<Integer> userFavorites = currUser.getFavorites();
		if (!userFavorites.contains(newFilmCode)) {
			User curr;
			final Iterator<User> it = this.users.iterator();
			while (it.hasNext()) {
				curr = it.next();
				if (curr.getUserID().equals(currUser.getUserID())) {
					it.remove();
				}
			}
			userFavorites.add(newFilmCode);
			final User newUser = new User(currUser.getUserID(), currUser.getPassword(), currUser.getName(), currUser.getSurname(), currUser.getDate(), currUser.getProfileImage(), userFavorites);
			this.users.add(newUser);	
		}
	}

	@Override
	public Pair<Integer, Integer> getStatic(final Integer filmId) {
		return this.statics.get(filmId);
	}

	@Override
	public void visitedFilm(final Integer newFilmCode) {
		final Pair<Integer, Integer> stat = this.statics.get(newFilmCode);
		stat.setVisited(stat.getVisited() + 1);
		this.statics.put(newFilmCode, stat);
	}

	@Override
	public void boughtFilm(final Integer newFilmCode) {
		final Pair<Integer, Integer> stat = this.statics.get(newFilmCode);
		stat.setBought(stat.getBought() + 1);
		this.statics.put(newFilmCode, stat);
	}

	@Override
	public void addUser(final User newUser) throws DuplicateUsernameException {
		final Iterator<User> it = this.users.iterator();
		User current;
		while (it.hasNext()) {
			current = it.next();
			if (current.getUserID().equals(newUser.getUserID())) {
				throw new DuplicateUsernameException();
			}
		}
		this.users.add(newUser);
		this.downloads.put(newUser.getUserID(), new HashSet<Integer>());
	}

	@Override
	public Set<User> getUsers() {
		return this.users;
	}

	@Override
	public IUser getCurrentUser() {
		return this.currentUser;
	}

	@Override
	public void setCurrentUser(final IUser user) {
		this.currentUser = user;
	}

	@Override
	public User getUser(final String userID) throws UserNotFoundException {
		final Iterator<User> it = this.users.iterator();
		User current = null;
		while (it.hasNext()) {
			current = it.next();
			if (current.getUserID().equals(userID)) {
				return current;
			}
		}
		throw new UserNotFoundException();
	}

	@Override
	public void editUser(final User modifiedUser, final String newImage) {
		User changeImage = null;
		if (newImage != null) {
			changeImage = new User(modifiedUser.getUserID(), modifiedUser.getPassword(), modifiedUser.getName(), modifiedUser.getSurname(), modifiedUser.getDate(), newImage + IMAGE_FORMAT, modifiedUser.getFavorites());
		}
		final Iterator<User> it = this.users.iterator();
		User curr;
		while (it.hasNext()) {
			curr = it.next();
			if (curr.getUserID().equals(modifiedUser.getUserID())) {
				it.remove();
			}
		}
		if (changeImage != null) {
			this.users.add(changeImage);
		} else {
			this.users.add(modifiedUser);
		}
	}

	@Override
	public void deleteUser(final String newUserId) {
		final Iterator<User> it = this.users.iterator();
		User curr;
		while (it.hasNext()) {
			curr = it.next();
			if (curr.getUserID().equals(newUserId)) {
				this.downloads.remove(curr.getUserID());
				it.remove();
				if (!curr.getProfileImage().equals(DEFAULT_IMAGE)) {
					final File file = new File(DEFAULT_PATH + curr.getProfileImage());
					file.delete();
				}
			}
		}
	}

	@Override
	public void deleteFilmUser(final String newUserId, final Integer newFilmCode) {
		final Set<Integer> filmBought = this.downloads.get(newUserId);
		filmBought.remove(newFilmCode);
		this.downloads.put(newUserId, filmBought);
	}

	@Override
	public void changeAdminPassword(final String newPassword) {
		final Admin newAdmin = new Admin(this.admin.getUserID(), newPassword, this.admin.getName(), this.admin.getSurname());
		this.admin = newAdmin;
	}

	@Override
	public Admin getAdmin() {
		return this.admin;
	}

	@Override
	public ArrayDeque<Integer> getOrderFilms(final String order) {
		final ArrayDeque<Integer> deck = new ArrayDeque<>();
		final Set<Integer> list = this.getKeySetFilm();
		if (!list.isEmpty()) {
			boolean check = true;
			Integer maxStat = -1;
			Integer maxId = null;
			Iterator<Integer> it;
			if (list.size() == 1) {
				check = false;
				it = list.iterator();
				deck.offer(it.next());
			}
			while (check) {
				it = list.iterator();
				while (it.hasNext()) {
					final Integer currentID = it.next();
					if (!deck.contains(currentID)) {
						if (order.equals(TOP_BOUGHT_FILM) && this.statics.get(currentID).getBought() > maxStat) {
							maxStat = this.statics.get(currentID).getVisited();
							maxId = currentID;
						} else if (order.equals(TOP_VISITED_FILM) && this.statics.get(currentID).getVisited() > maxStat) {
							maxStat = this.statics.get(currentID).getVisited();
							maxId = currentID;

						}
					}
				}
				deck.offer(maxId);
				maxStat = -1;
				if (list.size() == deck.size()) {
					check = false;
				}
			}
		}
		return deck;
	}

	@Override
	public SortedSet<Integer> getOrderFilm() {
		final SortedSet<Integer> set = new TreeSet<Integer>();
		final Iterator<Integer> it = this.films.keySet().iterator();
		while (it.hasNext()) {
			set.add(it.next());
		}
		return set;
	}

	@Override
	public void removeFavoriteFilm(final Integer newFilmCode) throws UserNotFoundException {
		final User currUser = this.getUser(this.currentUser.getUserID());
		final Set<Integer> userFavorites = currUser.getFavorites();
		if (userFavorites.contains(newFilmCode)) {
			User curr;
			final Iterator<User> it = this.users.iterator();
			while (it.hasNext()) {
				curr = it.next();
				if (curr.getUserID().equals(currUser.getUserID())) {
					it.remove();
				}
			}
			userFavorites.remove(newFilmCode);
			final User newUser = new User(currUser.getUserID(), currUser.getPassword(), currUser.getName(), currUser.getSurname(), currUser.getDate(), currUser.getProfileImage(), userFavorites);
			this.users.add(newUser);
		}		
	}

}