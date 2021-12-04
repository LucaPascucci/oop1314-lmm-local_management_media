package lmm.controller;

import javax.swing.JFrame;

import lmm.view.CreditsView;
import lmm.model.IModel;
/**
 * This class manage the functions of the credits.
 * @author Roberto Reibaldi
 *
 */
public class CreditsController extends MainController {

	private final Integer returnView;
	/**
	 * This is the constructor of the class.
	 * @param mod this parameter pass the model to the constructor.
	 * @param view this parameter pass the correct view that will be shown.
	 */
	public CreditsController(final IModel mod, final Integer view) {
		super(mod, DETAILS_FILM_VIEW);
		this.returnView = view;
	}

	@Override
	public void setView(final JFrame f) {
		this.frame = f;
		((CreditsView) this.frame).attachObserver(this);

	}

	@Override
	public void doBack() {
		this.changeView(this.activeView, this.returnView, null);
	}

}
