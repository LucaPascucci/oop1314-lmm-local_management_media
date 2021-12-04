package lmm.view;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SpringLayout;

import lmm.controller.CreditsController;

/**
 * Class that shows the credits of the program.
 * @author Filippo Nicolini
 */
public class CreditsView extends AbstractView {

	private static final long serialVersionUID = 1;

	private static final Integer HORIZONTAL_GAP = 20;
	private static final Integer LABEL_GAP = 10;

	private final JLabel creditsIcon = new JLabel(new ImageIcon(this.getClass().getResource("/Credits_Icon.png")));

	private final JLabel developers = new JLabel("Sviluppata da:");
	private final JLabel pascucci = new JLabel("Luca Pascucci");
	private final JLabel nicolini = new JLabel("Filippo Nicolini");
	private final JLabel sperandeo = new JLabel("Marco Sperandeo");
	private final JLabel reibaldi = new JLabel("Roberto Reibaldi");
	private final JLabel version = new JLabel("Version 1.0");
	private final JLabel distribution = new JLabel("Software distribuited under GNU General Public License.");
	private final JLabel lblCode = new JLabel("Code is available at bitbucket link:");
	private final JLabel link = new JLabel("https://bitbucket.org/LucaPascucci/oop1314-lmm-local_management_media");

	private CreditsController observer;

	/**
	 * This constructor creates a new CreditsView.
	 */
	public CreditsView() {

		super();

		this.setSize(525, 500); //frame size: 525 width, 500 height.
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setTitle("Creators of the application");
		this.setResizable(false);

		final SpringLayout layout = new SpringLayout();
		this.setLayout(layout);

		this.add(this.creditsIcon);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.creditsIcon, 0, SpringLayout.HORIZONTAL_CENTER, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.creditsIcon, LABEL_GAP, SpringLayout.NORTH, this.getContentPane());

		this.add(this.version);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.version, 0, SpringLayout.HORIZONTAL_CENTER, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.version, 0, SpringLayout.SOUTH, this.creditsIcon);

		this.add(this.developers);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.developers, 0, SpringLayout.HORIZONTAL_CENTER, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.developers, LABEL_GAP, SpringLayout.SOUTH, this.version);

		this.add(this.pascucci);
		layout.putConstraint(SpringLayout.EAST, this.pascucci, -HORIZONTAL_GAP, SpringLayout.WEST, this.nicolini);
		layout.putConstraint(SpringLayout.NORTH, this.pascucci, LABEL_GAP, SpringLayout.SOUTH, this.developers);
		this.pascucci.setIcon(new ImageIcon(this.getClass().getResource("/Luca_Pascucci.png")));
		this.pascucci.setHorizontalTextPosition(JLabel.CENTER);
		this.pascucci.setVerticalTextPosition(JLabel.NORTH);

		this.add(this.nicolini);
		layout.putConstraint(SpringLayout.EAST, this.nicolini, -LABEL_GAP, SpringLayout.HORIZONTAL_CENTER, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.nicolini, LABEL_GAP, SpringLayout.SOUTH, this.developers);
		this.nicolini.setIcon(new ImageIcon(this.getClass().getResource("/Filippo_Nicolini.png")));
		this.nicolini.setHorizontalTextPosition(JLabel.CENTER);
		this.nicolini.setVerticalTextPosition(JLabel.NORTH);

		this.add(this.sperandeo);
		layout.putConstraint(SpringLayout.WEST, this.sperandeo, LABEL_GAP, SpringLayout.HORIZONTAL_CENTER, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.sperandeo, LABEL_GAP, SpringLayout.SOUTH, this.developers);
		this.sperandeo.setIcon(new ImageIcon(this.getClass().getResource("/Marco_Sperandeo.png")));
		this.sperandeo.setHorizontalTextPosition(JLabel.CENTER);
		this.sperandeo.setVerticalTextPosition(JLabel.NORTH);

		this.add(this.reibaldi);
		layout.putConstraint(SpringLayout.WEST, this.reibaldi, HORIZONTAL_GAP, SpringLayout.EAST, this.sperandeo);
		layout.putConstraint(SpringLayout.NORTH, this.reibaldi, LABEL_GAP, SpringLayout.SOUTH, this.developers);
		this.reibaldi.setIcon(new ImageIcon(this.getClass().getResource("/Roberto_Reibaldi.png")));
		this.reibaldi.setHorizontalTextPosition(JLabel.CENTER);
		this.reibaldi.setVerticalTextPosition(JLabel.NORTH);


		this.add(this.distribution);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.distribution, 0, SpringLayout.HORIZONTAL_CENTER, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.distribution, LABEL_GAP, SpringLayout.SOUTH, this.nicolini);
		this.add(this.lblCode);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.lblCode, 0, SpringLayout.HORIZONTAL_CENTER, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.lblCode, 0, SpringLayout.SOUTH, this.distribution);
		this.add(this.link);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.link, 0, SpringLayout.HORIZONTAL_CENTER, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.link, 0, SpringLayout.SOUTH, this.lblCode);

		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(final WindowEvent e) {
				observer.doBack();
			}

		});
		this.setLocation();
		this.setIcon();

	}
	@Override
	public void mouseReleased(final MouseEvent e) {	}
	/**
	 * Attach the observer of the controller to the view.
	 * @param creditsController controller for this view
	 */
	public void attachObserver(final CreditsController creditsController) {
		this.observer = creditsController;
	}

	@Override
	public void actionPerformed(final ActionEvent e) { }

}
