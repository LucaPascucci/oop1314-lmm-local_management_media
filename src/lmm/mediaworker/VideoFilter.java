package lmm.mediaworker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.filechooser.FileFilter;

import com.xuggle.xuggler.IContainerFormat;

/**
 * Class implementing different FileFilter for JFileChooser.
 * @author Luca Pascucci
 *
 */
public class VideoFilter extends FileFilter {

	private static final String PROBLEM_CODEC_1 = "matroska,webm";
	private static final String PROBLEM_CODEC_2 = "mov,mp4,m4a,3gp,3g2,mj2";
	private static final String PROBLEM_CODEC_3 = "hls,applehttp";

	// Description of this filter.
	private final String description;
	// Known extensions.
	private final List<String> extensions;
	// Cached ext
	private final List<String> lowerCaseExtensions;

	/**
	 * Constructs a new VideoFilter given the description of the filter
	 * this constructor set the default available video format from Xuggle Library.
	 * @param newDescription Description of VideoFilter
	 */
	public VideoFilter(final String newDescription) {
		super();
		if (newDescription == null || newDescription.length() == 0) {
			throw new IllegalArgumentException("Extensions must be non-null and not empty");
		}
		this.description = newDescription;
		this.extensions = new ArrayList<>();
		this.lowerCaseExtensions = new ArrayList<>();
		for (final IContainerFormat currentFormat: IContainerFormat.getInstalledInputFormats()) {
			//Some formats are in the string so i need to divide them
			if (currentFormat.getInputFormatShortName().equals(PROBLEM_CODEC_1)) {
				this.extensions.add("matroska");
				this.extensions.add("webm");

				this.lowerCaseExtensions.add("matroska".toLowerCase(Locale.ENGLISH));
				this.lowerCaseExtensions.add("webm".toLowerCase(Locale.ENGLISH));

			} else if (currentFormat.getInputFormatShortName().equals(PROBLEM_CODEC_2)) {

				this.extensions.add("mov");
				this.extensions.add("mp4");
				this.extensions.add("m4a");
				this.extensions.add("3gp");
				this.extensions.add("3g2");
				this.extensions.add("mj2");

				this.lowerCaseExtensions.add("mov".toLowerCase(Locale.ENGLISH));
				this.lowerCaseExtensions.add("mp4".toLowerCase(Locale.ENGLISH));
				this.lowerCaseExtensions.add("m4a".toLowerCase(Locale.ENGLISH));
				this.lowerCaseExtensions.add("3gp".toLowerCase(Locale.ENGLISH));
				this.lowerCaseExtensions.add("3g2".toLowerCase(Locale.ENGLISH));
				this.lowerCaseExtensions.add("mj2".toLowerCase(Locale.ENGLISH));

			} else if (currentFormat.getInputFormatShortName().equals(
					PROBLEM_CODEC_3)) {
				this.extensions.add("hls");
				this.extensions.add("applehttp");

				this.lowerCaseExtensions.add("hls".toLowerCase(Locale.ENGLISH));
				this.lowerCaseExtensions.add("applehttp"
						.toLowerCase(Locale.ENGLISH));

			} else {
				this.extensions.add(currentFormat.getInputFormatShortName());
				this.lowerCaseExtensions.add(currentFormat.getInputFormatShortName().toLowerCase(Locale.ENGLISH));
			}
		}

	}

	/**
	 * Constructs a new VideoFilter given the description of the filter 
	 * and the list of supported format.
	 * @param newDescription Description of VideoFilter
	 * @param newExtensions  List of the visible extensions
	 */
	public VideoFilter(final String newDescription, final List<String> newExtensions) {
		super();
		if (newExtensions == null || newExtensions.isEmpty()) {
			throw new IllegalArgumentException(
					"Extensions must be non-null and not empty");
		}
		this.description = newDescription;
		this.extensions = new ArrayList<>();
		this.lowerCaseExtensions = new ArrayList<>();
		for (final String currentExtension : newExtensions) {
			if (currentExtension == null || currentExtension.length() == 0) {
				throw new IllegalArgumentException(
						"Each extension must be non-null and not empty");
			}
			this.extensions.add(currentExtension);
			this.lowerCaseExtensions.add(currentExtension.toLowerCase(Locale.ENGLISH));
		}
	}

	@Override
	public boolean accept(final File f) {
		if (f != null) {
			if (f.isDirectory()) {
				return true;
			}
			final String fileName = f.getName();
			final int i = fileName.lastIndexOf('.');
			if (i > 0 && i < fileName.length() - 1) {
				final  String desiredExtension = fileName.substring(i + 1).toLowerCase(Locale.ENGLISH);
				for (final String extension : this.lowerCaseExtensions) {
					if (desiredExtension.equals(extension)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	/**
	 * returns a List of extensions in the VideoFilter.
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getExtensions() {
		return new ArrayList<String>(this.extensions);
	}

	@Override
	public String toString() {
		return super.toString() + "[description=" + getDescription() + " extensions=" + getExtensions() + "]";
	}

}
