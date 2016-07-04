package com.unbank;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import net.semanticmetadata.lire.builders.DocumentBuilder;
import net.semanticmetadata.lire.imageanalysis.features.GlobalFeature;
import net.semanticmetadata.lire.imageanalysis.features.global.ColorLayout;
import net.semanticmetadata.lire.searchers.GenericFastImageSearcher;
import net.semanticmetadata.lire.searchers.ImageSearchHits;
import net.semanticmetadata.lire.utils.LuceneUtils;

import org.apache.lucene.index.IndexReader;

import com.unbank.utils.ImageUtils;

public class ImageAnalyzers {

	private static final String indexPath = "test-index";
	private static Class<? extends GlobalFeature> globalFeatureClass = ColorLayout.class;

	public String analyzer(String imgUrl) {
		BufferedImage bufferedImage = ImageUtils.toBufferImage(imgUrl);
		bufferedImage = ImageUtils.thresholdImage(bufferedImage, 150);
		List<BufferedImage> images = new ArrayList<BufferedImage>();
		int y = 0;
		int wight = 11;
		int hight = bufferedImage.getHeight();

		images.add(ImageUtils.catImage(bufferedImage, 7, y, wight, hight));
		images.add(ImageUtils.catImage(bufferedImage, 19, y, wight, hight));
		images.add(ImageUtils.catImage(bufferedImage, 32, y, wight, hight));
		images.add(ImageUtils.catImage(bufferedImage, 45, y, wight, hight));
		
		StringBuffer sb = new StringBuffer();
		for (BufferedImage bi : images) {
			sb.append(searchImg(bi));
		}
		return sb.toString();

	}

	public static String searchImg(BufferedImage image) {
		String fileName = null;
		try {
			IndexReader reader = LuceneUtils.openIndexReader(indexPath, true);
			GenericFastImageSearcher ceddSearcher = new GenericFastImageSearcher(
					1, globalFeatureClass, true, reader);
			ImageSearchHits ceddhits = ceddSearcher.search(image, reader);
			fileName = reader.document(ceddhits.documentID(0)).getValues(
					DocumentBuilder.FIELD_NAME_IDENTIFIER)[0];
			fileName =fileName.charAt(0)+"";
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileName;
	}
}
