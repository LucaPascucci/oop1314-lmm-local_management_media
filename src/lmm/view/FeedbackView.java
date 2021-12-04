package lmm.view;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import lmm.controller.FeedbackController;
/**
 * This class creates a view to send a message if there are problem with the program.
 * @author Filippo Nicolini
 *
 */
public class FeedbackView extends AbstractView {

	private static final long serialVersionUID = 1;

	private static final Integer VERTICAL_GAP = 20;
	private static final Integer HALF_VERTICAL_GAP = 10;
	private static final Integer HORIZONTAL_GAP = 20;

	private final JLabel loadingGif = new JLabel(new ImageIcon(this.getClass().getResource("/Loading.gif")));

	private final JLabel lblText = new JLabel("Text to send");
	private final JLabel lblObject = new JLabel("Object");
	private final JLabel message = new JLabel("Data will be enclosed automatically.");
	private final JTextField object = new JTextField(21); //JTextField size: 21.
	private final JTextArea text = new JTextArea(10, 30); //JTextArea size: 10 width, 30 height.
	private final JScrollPane scroll = new JScrollPane(this.text);
	private final JButton send = new JButton("Send");
	private final JButton back = new JButton("Back");

	private FeedbackController observer;

	/**
	 * This constructor creates a new FeedbackView.
	 */
	public FeedbackView() {

		super();

		this.setTitle("Contact Us");
		this.setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setSize(420, 360); //frame size: 420 width, 360 height.

		final SpringLayout layout = new SpringLayout();
		this.setLayout(layout);

		this.add(this.lblObject);
		layout.putConstraint(SpringLayout.NORTH, this.lblObject, VERTICAL_GAP, SpringLayout.NORTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.lblObject, HORIZONTAL_GAP, SpringLayout.WEST, this.getContentPane());

		this.add(this.object);
		layout.putConstraint(SpringLayout.NORTH, this.object, VERTICAL_GAP, SpringLayout.NORTH, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.object, -HORIZONTAL_GAP, SpringLayout.EAST, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.object, 120, SpringLayout.WEST, this.getContentPane());
		//120 is the tabulation border for this JTextField

		this.add(this.lblText);
		layout.putConstraint(SpringLayout.NORTH, this.lblText, VERTICAL_GAP, SpringLayout.SOUTH, this.lblObject);
		layout.putConstraint(SpringLayout.WEST, this.lblText, HORIZONTAL_GAP, SpringLayout.WEST, this.getContentPane());

		this.add(this.scroll);
		layout.putConstraint(SpringLayout.NORTH, this.scroll, HALF_VERTICAL_GAP, SpringLayout.SOUTH, this.lblText);
		layout.putConstraint(SpringLayout.WEST, this.scroll, HORIZONTAL_GAP, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.scroll, -HORIZONTAL_GAP, SpringLayout.EAST, this.getContentPane());

		this.add(this.message);
		layout.putConstraint(SpringLayout.NORTH, this.message, HALF_VERTICAL_GAP, SpringLayout.SOUTH, this.scroll);
		layout.putConstraint(SpringLayout.WEST, this.message, HORIZONTAL_GAP, SpringLayout.WEST, this.getContentPane());

		this.add(this.back);
		layout.putConstraint(SpringLayout.SOUTH, this.back, -VERTICAL_GAP, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.back, -HORIZONTAL_GAP, SpringLayout.EAST, this.getContentPane());

		this.add(this.send);
		layout.putConstraint(SpringLayout.SOUTH, this.send, -VERTICAL_GAP, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.send, -HORIZONTAL_GAP, SpringLayout.WEST, this.back);

		this.add(this.loadingGif, 1, 0); //this method put the loadingGif on the top of the others component.
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.loadingGif, 0, SpringLayout.HORIZONTAL_CENTER, this.getContentPane());
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, this.loadingGif, 0, SpringLayout.VERTICAL_CENTER, this.getContentPane());
		this.loadingGif.setVisible(false);

		this.back.addActionListener(this);
		this.send.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(final WindowEvent e) {
				if (!loadingGif.isVisible()) {
					observer.doBack();
				}
			}

		});
		this.setLocation();
		this.setIcon();
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		final Object selection = e.getSource();
		if (!this.loadingGif.isVisible()) {
			if (selection.equals(this.back)) {
				this.observer.doBack();
			} else if (selection.equals(this.send)) {
				this.observer.doSend(this.object.getText(), this.text.getText());
			}
		}

	}

	@Override
	public void mouseReleased(final MouseEvent e) {	}
	/**
	 * Attach the observer of the controller to the view.
	 * @param feedbackController controller for this view
	 */
	public void attachObserver(final FeedbackController feedbackController) {
		this.observer = feedbackController;
	}
	/**
	 * Set fields visibility.
	 */
	public void setUnsetLoadingVisible() {
		if (this.loadingGif.isVisible()) {
			this.loadingGif.setVisible(false);
			this.object.setEditable(true);
			this.text.setEditable(true);
		} else {
			this.loadingGif.setVisible(true);
			this.object.setEditable(false);
			this.text.setEditable(false);
		}
	}

}
