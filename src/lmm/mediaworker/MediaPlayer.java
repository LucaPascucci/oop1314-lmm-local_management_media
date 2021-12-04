package lmm.mediaworker;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import com.xuggle.xuggler.IAudioSamples;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IPacket;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;
import com.xuggle.xuggler.ICodec;

import java.awt.image.BufferedImage;

import com.xuggle.xuggler.Global;
import com.xuggle.xuggler.IPixelFormat;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.IVideoResampler;
import com.xuggle.xuggler.Utils;

/**
 * Class implementing the media player using Xuggler Library to encoding video file
 * and a JFrame {@link lmm.mediaworker.VideoFrame} to display the video file.
 * @author Luca Pascucci
 *
 */
public class MediaPlayer {

	private static final Integer BUFFER_SIZE = 1024;

	private SourceDataLine mLine;
	private VideoFrame mScreen;
	private IContainer container;
	private IStreamCoder audioCoder;
	private IStreamCoder videoCoder;
	private Integer videoStreamId;
	private Integer audioStreamId;
	private IVideoResampler resampler;
	private final String path;

	/**
	 * Constructs a new MediaPlayer given the path of a video file provided in input.
	 * @param pathVideo Path of a video file
	 */
	@SuppressWarnings("deprecation")
	public MediaPlayer(final String pathVideo) {
		this.path = pathVideo;

		this.container = IContainer.make();

		if (this.container.open(this.path, IContainer.Type.READ, null) < 0) {
			throw new IllegalArgumentException("could not open file: " + this.path);
		}

		// query for the number of stream
		final int numStreams = this.container.getNumStreams();

		this.videoStreamId = -1;
		this.videoCoder = null;
		this.audioStreamId = -1;
		this.audioCoder = null;
		for (int i = 0; i < numStreams; i++) {

			// Find the stream objectS
			final IStream stream = this.container.getStream(i);
			// Get the pre-configured decoder that can decode this stream;
			final IStreamCoder coder = stream.getStreamCoder();

			if (coder.getCodecType() == ICodec.Type.CODEC_TYPE_VIDEO) {
				this.videoStreamId = i;
				this.videoCoder = coder;

			}
			if (coder.getCodecType() == ICodec.Type.CODEC_TYPE_AUDIO) {
				this.audioStreamId = i;
				this.audioCoder = coder;

			}

		}
		if (this.videoStreamId == -1) {
			throw new RuntimeException("could not find video stream in container: " + path);
		}

		if (this.audioStreamId == -1) {
			throw new RuntimeException("could not find audio stream in container: " + path);
		}

		if (this.videoCoder.open() < 0) {
			throw new RuntimeException("could not open video decoder for container: " + path);
		}

		if (this.audioCoder.open() < 0) {
			throw new RuntimeException("could not open audio decoder for container: " + path);
		}


		this.resampler = null;
		if (this.videoCoder.getPixelType() != IPixelFormat.Type.BGR24) {
			// if this stream is not in BGR24, we're going to need to
			// convert it. The VideoResampler does that for us.
			this.resampler = IVideoResampler.make(this.videoCoder.getWidth(), this.videoCoder.getHeight(), IPixelFormat.Type.BGR24, this.videoCoder.getWidth(), this.videoCoder.getHeight(), this.videoCoder.getPixelType());
			if (this.resampler == null) {
				throw new RuntimeException("could not create color space resampler for: " + this.path);
			}
		}
	}

	/**
	 * Start the encoding and visualizing of the video file.
	 * @param filmTitle Will be use to create a new {@link lmm.mediaworker.VideoFrame}
	 */
	@SuppressWarnings("deprecation")
	public void startTrailer(final String filmTitle) {
		/*
		 * And once we have that, we draw a window on screen
		 */
		this.openJavaWindow(filmTitle);
		this.openJavaSound(this.audioCoder);

		final IPacket packet = IPacket.make();
		long firstTimestampInStream = Global.NO_PTS;
		long systemClockStartTime = 0;
		final long millisecondsTolerance = 50; //tolerance
		IVideoPicture picture;
		IVideoPicture newPic;
		IAudioSamples samples;

		// read all the packets in the container of the video file
		while (this.container.readNextPacket(packet) >= 0 && this.mScreen.isShowing()) {
			if (packet.getStreamIndex() == this.videoStreamId) {
				/*
				 * We allocate a new picture to get the data out of Xuggler
				 */
				picture = IVideoPicture.make(this.videoCoder.getPixelType(), this.videoCoder.getWidth(), this.videoCoder.getHeight());

				int offset = 0;
				while (offset < packet.getSize()) {
					/*
					 * Decode the video, checking for any errors.
					 */
					final int bytesDecoded = this.videoCoder.decodeVideo(picture, packet, offset);
					if (bytesDecoded < 0) {
						throw new RuntimeException("got error decoding video in: " + this.path);
					}
					offset += bytesDecoded;

					/*
					 * Some decoders will consume data in a packet, but will not
					 * be able to construct a full video picture yet. Therefore
					 * you should always check if you got a complete picture
					 * from the decoder
					 */
					if (picture.isComplete()) {
						newPic = picture;
						/*
						 * If the resampler is not null, that means we didn't
						 * get the video in BGR24 format and need to convert it
						 * into BGR24 format.
						 */
						if (this.resampler != null) {
							// we must resample
							newPic = IVideoPicture.make(this.resampler.getOutputPixelFormat(), picture.getWidth(), picture.getHeight());
							if (this.resampler.resample(newPic, picture) < 0) {
								throw new RuntimeException("could not resample video from: " + this.path);
							}
						}
						if (newPic.getPixelType() != IPixelFormat.Type.BGR24) {
							throw new RuntimeException("could not decode video as BGR 24 bit data in: " + this.path);
						}

						/*
						 * We could just display the images as quickly as we
						 * decode them, but it turns out we can decode a lot
						 * faster than you think.
						 * 
						 * All Xuggler IAudioSamples and
						 * IVideoPicture objects always give timestamps in
						 * Microseconds, relative to the first decoded item. If
						 * instead you used the packet timestamps, they can be
						 * in different units depending on your IContainer, and
						 * IStream and things can get hairy quickly.
						 */
						if (firstTimestampInStream == Global.NO_PTS) {
							// This is the first time through
							firstTimestampInStream = picture.getTimeStamp();
							// get the starting clock time so we can hold up frames until the right time
							systemClockStartTime = System.currentTimeMillis();
						} else {
							final long systemClockCurrentTime = System.currentTimeMillis();
							final long millisecondsClockTimeSinceStartofVideo = systemClockCurrentTime - systemClockStartTime;
							// compute how long for this frame since the first frame in the stream 
							final long millisecondsStreamTimeSinceStartOfVideo = (picture.getTimeStamp() - firstTimestampInStream) / 1000;

							final long millisecondsToSleep = millisecondsStreamTimeSinceStartOfVideo - millisecondsClockTimeSinceStartofVideo + millisecondsTolerance;
							if (millisecondsToSleep > 0) {
								try {
									Thread.sleep(millisecondsToSleep);
								} catch (InterruptedException exc) {
									//Might get this when the user closes the dialog box, so just return from the method
									return;
								}
							}
						}

						// And finally, convert the BGR24 to an Java buffered image
						final BufferedImage javaImage = Utils.videoPictureToImage(newPic);

						// and display it on the Java Swing window
						this.updateJavaWindow(javaImage);
					}
				}
			} else if (packet.getStreamIndex() == this.audioStreamId) {
				/*
				 * Allocate a set of samples with the same number of channels
				 * as the coder tells us is in this buffer.
				 * 
				 * Also pass in a buffer size (1024), although
				 * Xuggler will probably allocate more space than just the 1024
				 */
				samples = IAudioSamples.make(BUFFER_SIZE, this.audioCoder.getChannels());

				/*
				 * A packet can actually contain multiple sets of samples (or
				 * frames of samples in audio-decoding speak). So, may need
				 * to call decode audio multiple times at different offsets in
				 * the packet's data and capture that here.
				 */
				int offset = 0;

				/*
				 * Keep going until we've processed all data
				 */
				while (offset < packet.getSize()) {
					final int bytesDecoded = this.audioCoder.decodeAudio(samples, packet, offset);
					if (bytesDecoded < 0) {
						throw new RuntimeException("got error decoding audio in: " + path);
					}
					offset += bytesDecoded;

					if (samples.isComplete()) {
						this.playJavaSound(samples);
					}
				}

			} else {
				//This packet isn't part of video stream, so silently drop it.
				do {
				} while (false);
			}
		}
		this.closeAllComponent();
	}

	/**
	 * Create an instance of {@link lmm.mediaworker.VideoFrame}
	 * @param filmTitle 
	 */
	private void openJavaWindow(final String filmTitle) {

		this.mScreen = new VideoFrame(filmTitle);

	}

	private void openJavaSound(final IStreamCoder aAudioCoder) {
		final AudioFormat audioFormat = new AudioFormat(aAudioCoder.getSampleRate(), (int) IAudioSamples.findSampleBitDepth(aAudioCoder.getSampleFormat()), aAudioCoder.getChannels(), true, false);
		/* xuggler defaults to signed 16 bit samples */
		final DataLine.Info info = new DataLine.Info(SourceDataLine.class,
				audioFormat);
		try {
			this.mLine = (SourceDataLine) AudioSystem.getLine(info);
			/**
			 * if that succeeded, try opening the line.
			 */
			this.mLine.open(audioFormat);
			/**
			 * And if that succeed, start the line.
			 */
			this.mLine.start();
		} catch (LineUnavailableException exc) {
			throw new RuntimeException("could not open audio line");
		}

	}

	/**
	 * Play the decoded audioStream packet after completing
	 * @param aSamples
	 */

	private void playJavaSound(final IAudioSamples aSamples) {
		/**
		 * Going to dump all the samples into the line.
		 */
		final byte[] rawBytes = aSamples.getData().getByteArray(0, aSamples.getSize());
		this.mLine.write(rawBytes, 0, aSamples.getSize());
	}

	/**
	 * Update the new image to the JFrame {@link lmm.mediaworker.VideoFrame}
	 * @param javaImage The new image to display
	 */
	private void updateJavaWindow(final BufferedImage javaImage) {
		this.mScreen.setImage(javaImage);
	}

	/**
	 * Close all the component used to visualize the video file
	 */
	private void closeAllComponent() {
		if (this.videoCoder != null) {
			this.videoCoder.close();
			this.videoCoder = null;
		}
		if (this.container != null) {
			this.container.close();
			this.container = null;
		}
		if (this.audioCoder != null) {
			this.audioCoder.close();
			this.audioCoder = null;
		}
		this.mScreen.dispose();
		this.mLine.close();
		Thread.currentThread().interrupt();

	}
}