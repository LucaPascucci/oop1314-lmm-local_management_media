package lmm.model;

import java.io.Serializable;

/**
 * Models a generic pair.
 * @author Marco Sperandeo
 *
 * @param <X>
 * @param <Y>
 */
public class Pair<X, Y> implements Serializable {

	private static final long serialVersionUID = 1;

	private X visited;
	private Y bought;

	/**
	 * Creates a pair with the parameters provided in input.
	 * @param newVisited 
	 * @param newBought 
	 */
	public Pair(final X newVisited, final Y newBought) {
		this.visited = newVisited;
		this.bought = newBought;
	}

	/**
	 * Returns how many times the film has visited.
	 * @return X
	 */
	public X getVisited() {
		return this.visited;
	}

	/**
	 * Returns how many times the film has bought.
	 * @return Y
	 */
	public Y getBought() {
		return this.bought;
	}
	/**
	 * Set the value of visit of a film.
	 * @param newVisited new X value
	 */
	public void setVisited(final X newVisited) {
		this.visited = newVisited;
	}
	/**
	 * Set the value of times that a film has been bought.
	 * @param newBought new Y value
	 */
	public void setBought(final Y newBought) {
		this.bought = newBought;
	}
} 