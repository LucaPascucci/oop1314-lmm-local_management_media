package lmm.view.admin;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;

import lmm.controller.admin.EditableFilmController;
import lmm.model.FilmType;
import lmm.model.IFilm;
import lmm.view.AbstractView;

/**
 * This class is used to create or edit a film.
 * @author Filippo Nicolini
 *
 */
public class EditableFilmView extends AbstractView {

	private static final long serialVersionUID = 1;

	private static final Integer HORIZONTAL_FIRST_TABULATION = 20;
	private static final Integer HORIZONTAL_SECOND_TABULATION = 110;
	private static final Integer VERTICAL_GAP = 15;
	private static final int CMB_ROW_COUNT = 5;

	private static final FilmType[] FILM_TYPES = {FilmType.Select , FilmType.ACTION , FilmType.ADVENTURE, FilmType.ANIMATIONS, FilmType.BIOGRAPHICAL, FilmType.COMIC, FilmType.DOCUMENTARY, FilmType.DRAMATIC, FilmType.EROTIC, FilmType.FANTASY, FilmType.HORROR, FilmType.MUSICAL, FilmType.POLICE, FilmType.SENTIMENTAL, FilmType.THRILLER, FilmType.WESTERN};

	private final JLabel lblTitle = new JLabel("Title");
	private final JLabel lblGenre = new JLabel("Genre");
	private final JLabel lblPrice = new JLabel("Price");
	private final JLabel lblPlot = new JLabel("Plot");
	private final JLabel lblReleaseYear = new JLabel("Release Year");
	private final JTextField txtTitle = new JTextField(17); //TextField size: 17.
	private final JTextField txtPrice = new JTextField(6); //TextField size: 6.
	private final JComboBox<FilmType> cmbGenre = new JComboBox<FilmType>(FILM_TYPES);
	private final JTextArea txtPlot = new JTextArea(16, 25); //TextArea size: 16 width, 25 height.
	private final JSpinner spinnerYears = new JSpinner();
	private final JButton confirm = new JButton("");
	private final JButton cancel = new JButton("Cancel");

	private FilmType currType;
	private EditableFilmController observer;

	/**
	 * Implements a new EditableFilmView.
	 */
	public EditableFilmView() {

		super();

		this.setTitle("Film");
		this.setSize(330, 580); //frame size: 330 width, 580 height.
		this.setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		JScrollPane scrollPlot;

		final SpringLayout layout = new SpringLayout();
		this.setLayout(layout);

		this.cmbGenre.setSelectedIndex(0);
		this.cmbGenre.setMaximumRowCount(CMB_ROW_COUNT);
		this.cmbGenre.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(final ItemEvent e) {
				if (cmbGenre.getSelectedItem().equals(FilmType.Select)) {
					currType = null;
				} else {
					currType = (FilmType) cmbGenre.getSelectedItem();
				}
			}
		});

		this.txtPlot.setLineWrap(true);
		scrollPlot = new JScrollPane(this.txtPlot);
		scrollPlot.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		this.add(this.lblTitle);
		layout.putConstraint(SpringLayout.WEST, this.lblTitle, HORIZONTAL_FIRST_TABULATION, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.lblTitle, VERTICAL_GAP, SpringLayout.NORTH, this.getContentPane());


		this.add(this.txtTitle);
		layout.putConstraint(SpringLayout.NORTH, this.txtTitle, VERTICAL_GAP, SpringLayout.NORTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.txtTitle, HORIZONTAL_SECOND_TABULATION, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.txtTitle, -HORIZONTAL_FIRST_TABULATION, SpringLayout.EAST, this.getContentPane());

		this.add(this.lblGenre);
		layout.putConstraint(SpringLayout.WEST, this.lblGenre, HORIZONTAL_FIRST_TABULATION, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.lblGenre, VERTICAL_GAP, SpringLayout.SOUTH, this.txtTitle);

		this.add(this.cmbGenre);
		layout.putConstraint(SpringLayout.NORTH, this.cmbGenre, VERTICAL_GAP, SpringLayout.SOUTH, this.txtTitle);
		layout.putConstraint(SpringLayout.WEST, this.cmbGenre, HORIZONTAL_SECOND_TABULATION, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.cmbGenre, 300, SpringLayout.WEST, this.getContentPane());
		//300 is the width of the cmbGenre.

		this.add(this.lblPrice);
		layout.putConstraint(SpringLayout.WEST, this.lblPrice, HORIZONTAL_FIRST_TABULATION, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.lblPrice, VERTICAL_GAP, SpringLayout.SOUTH, this.cmbGenre);

		this.add(this.txtPrice);
		layout.putConstraint(SpringLayout.NORTH, this.txtPrice, VERTICAL_GAP, SpringLayout.SOUTH, this.cmbGenre);
		layout.putConstraint(SpringLayout.WEST, this.txtPrice, HORIZONTAL_SECOND_TABULATION, SpringLayout.WEST, this.getContentPane());

		this.add(this.lblReleaseYear);
		layout.putConstraint(SpringLayout.WEST, this.lblReleaseYear, HORIZONTAL_FIRST_TABULATION, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.lblReleaseYear, VERTICAL_GAP, SpringLayout.SOUTH, this.txtPrice);

		this.add(this.spinnerYears);
		layout.putConstraint(SpringLayout.NORTH, this.spinnerYears, VERTICAL_GAP, SpringLayout.SOUTH, this.txtPrice);
		layout.putConstraint(SpringLayout.WEST, this.spinnerYears, HORIZONTAL_SECOND_TABULATION, SpringLayout.WEST, this.getContentPane());
		this.spinnerYears.setModel(new SpinnerNumberModel(2014, 1852, 2020, 1)); //set the values of the spinnerYears

		this.add(this.lblPlot);
		layout.putConstraint(SpringLayout.WEST, this.lblPlot, HORIZONTAL_FIRST_TABULATION, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.lblPlot, VERTICAL_GAP, SpringLayout.SOUTH, this.spinnerYears);

		this.add(scrollPlot);
		layout.putConstraint(SpringLayout.NORTH, scrollPlot, VERTICAL_GAP, SpringLayout.SOUTH, this.lblPlot);
		layout.putConstraint(SpringLayout.WEST, scrollPlot, HORIZONTAL_FIRST_TABULATION, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, scrollPlot, -HORIZONTAL_FIRST_TABULATION, SpringLayout.EAST, this.getContentPane());

		this.add(this.confirm);
		layout.putConstraint(SpringLayout.SOUTH, this.confirm, -VERTICAL_GAP, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.confirm, -HORIZONTAL_FIRST_TABULATION, SpringLayout.EAST, this.getContentPane());

		this.add(this.cancel);
		layout.putConstraint(SpringLayout.SOUTH, this.cancel, -VERTICAL_GAP, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.cancel, -HORIZONTAL_FIRST_TABULATION, SpringLayout.WEST, this.confirm);

		this.confirm.addActionListener(this);
		this.cancel.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(final WindowEvent e) {
				final IFilm film = observer.getEditableFilm();
				if (film != null) {
					confirm.setText("Save");
					cmbGenre.setSelectedItem(film.getGenre());
					currType = (FilmType) cmbGenre.getSelectedItem();
					txtTitle.setText(film.getTitle());
					txtPrice.setText(film.getPrice().toString());
					txtPlot.setText(film.getPlot());
					spinnerYears.setValue(film.getDate());
				} else {
					confirm.setText("Add Film");
				}

			}

			@Override
			public void windowClosing(final WindowEvent e) {
				observer.doBack();
			}

		});
		this.setLocation();
		this.setIcon();
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		final Object selection = e.getSource();
		if (selection.equals(this.cancel)) {
			this.observer.doBack();
		} else if (selection.equals(this.confirm)) {
			this.observer.doConfirm(this.txtTitle.getText(), this.txtPrice.getText(), this.txtPlot.getText(), (Integer) this.spinnerYears.getValue(), this.currType);
		}

	}

	@Override
	public void mouseReleased(final MouseEvent e) {	}
	/**
	 * Attach the observer of the controller to the view.
	 * @param editableFilmController controller for this view
	 */
	public void attachObserver(final EditableFilmController editableFilmController) {
		this.observer = editableFilmController;		
	}

	/**
	 * Set indicated fields to void.
	 */
	public void resetNumberField() {
		this.txtPrice.setText("");

	}

}
