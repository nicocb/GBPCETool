/*
 * Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gracie.barra.util;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.ServletException;

import org.apache.commons.io.output.ByteArrayOutputStream;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.jpeg.JpegDirectory;
import com.drew.metadata.png.PngDirectory;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

// [START example]
public class CloudStorageHelper {

	private static Storage storage = null;
	private static final Logger log = Logger.getLogger(CloudStorageHelper.class.getName());

	static {
		storage = StorageOptions.getDefaultInstance().getService();
	}

	/**
	 * Uploads a file to Google Cloud Storage to the bucket specified in the
	 * BUCKET_NAME environment variable, appending a timestamp to end of the
	 * uploaded filename.
	 * 
	 * @param extension
	 * 
	 * @throws ImageProcessingException
	 * @throws MetadataException
	 */
	public String uploadFile(byte[] bytes, final String bucketName, String id, String extension)
			throws IOException, ServletException, MetadataException, ImageProcessingException {

		final String fileName = id + ".jpg";

		// the inputstream is closed by default, so we don't need to close it
		// here
		BlobInfo blobInfo;
		BlobId bid = BlobId.of(bucketName, fileName);
		log.info("Checking existence of " + fileName);
		if (storage.get(bid) != null) {
			log.info("Deleting " + fileName);
			storage.delete(BlobId.of(bucketName, fileName));
		}
		log.info("Storing " + fileName);
		blobInfo = storage.create(
				BlobInfo.newBuilder(bucketName, fileName)
						// Modify access list to allow all users with link
						// to
						// read file
						.setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER)))).build(),
				imageIOResized(bytes, extension));
		log.info("Stored " + fileName);
		return blobInfo.getMediaLink();
	}

	/**
	 * Checks that the file extension is supported.
	 */
	public static String checkFileExtension(String fileName) throws ServletException {
		if (fileName != null && !fileName.isEmpty() && fileName.contains(".")) {
			String[] allowedExt = { "jpg", "jpeg", "png" };
			for (String ext : allowedExt) {
				if (fileName.endsWith("." + ext)) {
					return ext.equals("jpeg") ? "jpg" : ext;
				}
			}
		}
		throw new ServletException("file must be an image : jpg or png");
	}

	private byte[] imageIOResized(byte[] imgBytes, String extension)
			throws IOException, MetadataException, ImageProcessingException {

		ByteArrayInputStream imgStream = new ByteArrayInputStream(imgBytes);

		BufferedImage image = ImageIO.read(imgStream);

		ImageInformation info = readImageInformation(new ByteArrayInputStream(imgBytes), extension);
		if (info.orientation != 1 || info.ratio != 1.0) {
			image = transformImage(image, getExifTransformation(info));
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageOutputStream imageOutput = ImageIO.createImageOutputStream(baos);

		final ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
		writer.setOutput(imageOutput);

		// You may want also to alter jpeg quality
		ImageWriteParam iwParam = writer.getDefaultWriteParam();
		iwParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		iwParam.setCompressionQuality(.5f);

		// writes the file with given compression level
		// from your JPEGImageWriteParam instance
		writer.write(null, new IIOImage(image, null, null), iwParam);
		writer.dispose();

		ImageIO.write(image, "jpg", imageOutput);

		return baos.toByteArray();

	}

	public BufferedImage transformImage(BufferedImage image, AffineTransform transform) {

		AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

		return op.filter(image, null);
	}

	public AffineTransform getExifTransformation(ImageInformation info) {

		AffineTransform t = new AffineTransform();

		switch (info.orientation) {
		case 1:
			t.scale(1.0 * info.ratio, 1.0 * info.ratio);
			break;
		case 2: // Flip X
			t.scale(-1.0 * info.ratio, 1.0 * info.ratio);
			t.translate(-info.width, 0);
			break;
		case 3: // PI rotation
			t.scale(1.0 * info.ratio, 1.0 * info.ratio);
			t.translate(info.width, info.height);
			t.rotate(Math.PI);
			break;
		case 4: // Flip Y
			t.scale(1.0 * info.ratio, -1.0 * info.ratio);
			t.translate(0, -info.height);
			break;
		case 5: // - PI/2 and Flip X
			t.scale(1.0 * info.ratio, 1.0 * info.ratio);
			t.rotate(-Math.PI / 2);
			t.scale(-1.0 * info.ratio, 1.0 * info.ratio);
			break;
		case 6: // -PI/2 and -width
			t.scale(1.0 * info.ratio, 1.0 * info.ratio);
			t.translate(info.height, 0);
			t.rotate(Math.PI / 2);
			break;
		case 7: // PI/2 and Flip
			t.scale(-1.0 * info.ratio, 1.0 * info.ratio);
			t.translate(-info.height, 0);
			t.translate(0, info.width);
			t.rotate(3 * Math.PI / 2);
			break;
		case 8: // PI / 2
			t.scale(1.0 * info.ratio, 1.0 * info.ratio);
			t.translate(0, info.width);
			t.rotate(3 * Math.PI / 2);
			break;
		}

		return t;
	}

	// Inner class containing image information
	public class ImageInformation {
		public final int orientation;
		public final int width;
		public final int height;
		public final float ratio;

		public ImageInformation(int orientation, int width, int height) {
			this.orientation = orientation;
			this.width = width;
			this.height = height;
			this.ratio = Math.min(1f, 1000f / Math.max((float) width, (float) height));
		}

		@Override
		public String toString() {
			return String.format("%dx%d,%d", this.width, this.height, this.orientation);
		}
	}

	public ImageInformation readImageInformation(ByteArrayInputStream imgStream, String extension)
			throws IOException, MetadataException, ImageProcessingException {
		Metadata metadata = ImageMetadataReader.readMetadata(imgStream);
		Directory directory = metadata.getDirectory(ExifIFD0Directory.class);

		int orientation = 1;
		if (directory != null) {
			try {
				orientation = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
			} catch (MetadataException me) {
			}
		}
		int width = 0;
		int height = 0;

		if (extension.equals("jpg")) {
			JpegDirectory jpegDirectory = metadata.getDirectory(JpegDirectory.class);
			width = jpegDirectory.getImageWidth();
			height = jpegDirectory.getImageHeight();

		}
		if (extension.equals("png")) {
			PngDirectory pngDirectory = metadata.getDirectory(PngDirectory.class);
			width = pngDirectory.getInt(PngDirectory.TAG_IMAGE_WIDTH);
			height = pngDirectory.getInt(PngDirectory.TAG_IMAGE_HEIGHT);

		}

		return new ImageInformation(orientation, width, height);
	}
}
