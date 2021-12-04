package lmm.mediaworker;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.ImageIcon;

import lmm.exception.NoVideoIconException;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.IMediaTool;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.MediaToolAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IVideoPictureEvent;

/**
 * Class used to add an ImageIcon to a file video.
 * @author Luca Pascucci
 *
 */
public class VideoIcon {

	private final String path;
	private final IMediaReader mediaReader;
	private final ImageIcon videoIcon = new ImageIcon(this.getClass().getResource("/Video_Icon.png"));

	/**
	 * Constructs a new VideoIcon given two paths.
	 * @param temporaryFile	Path of the temporary file video create
	 * @param finalPath		Path of the final file video that will be use to display to users
	 * @throws NoVideoIconException 
	 */
	public VideoIcon(final String temporaryFile, final String finalPath) throws NoVideoIconException {

		this.path = temporaryFile;

		// create a media reader
		this.mediaReader = ToolFactory.makeReader(path);

		// configure it to generate BufferImages
		this.mediaReader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);

		final IMediaWriter mediaWriter = ToolFactory.makeWriter(finalPath, mediaReader);

		final IMediaTool imageMediaTool = new StaticImageMediaTool(videoIcon);  

		// create a tool chain:
		// reader -> addStaticImage -> writer
		this.mediaReader.addListener(imageMediaTool);

		imageMediaTool.addListener(mediaWriter);

	}

	/**
	 * This method start the encoding of the temporary file video.
	 */
	public void startEncoding() {

		while (this.mediaReader.readPacket() == null) {
			// continue coding
		}
		//when it finish the encoding, temporary file video will be eliminated
		final File deleteTmp = new File(this.path);
		deleteTmp.delete();
	}

	/**
	 * Private class that insert ImageIcon in all the video frames
	 * @author Luca Pascucci
	 *
	 */
	private static class StaticImageMediaTool extends MediaToolAdapter {

		private final BufferedImage logoImage;

		public StaticImageMediaTool(final ImageIcon imageIcon) {
			super();
			logoImage = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
			final Graphics g = logoImage.createGraphics();
			// paint the Icon to the BufferedImage.
			imageIcon.paintIcon(null, g, 0, 0);
			g.dispose();
		}

		@Override
		public void onVideoPicture(final IVideoPictureEvent event) {

			final BufferedImage image = event.getImage();

			// get the graphics for the image
			final Graphics2D g = image.createGraphics();

			final Rectangle2D bounds = new Rectangle2D.Float(0, 0, logoImage.getWidth(), logoImage.getHeight());

			// compute the amount to inset the time stamp and 
			// translate the image to that position
			final double inset = bounds.getHeight();
			g.translate(inset, event.getImage().getHeight() - inset);

			g.fill(bounds);
			g.drawImage(logoImage, 0, 0, null);

			// call parent which will pass the video onto next tool in chain
			super.onVideoPicture(event);

		}

	}
}