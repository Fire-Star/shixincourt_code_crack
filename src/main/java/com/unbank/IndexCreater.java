package com.unbank;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;

import net.semanticmetadata.lire.builders.GlobalDocumentBuilder;
import net.semanticmetadata.lire.imageanalysis.features.GlobalFeature;
import net.semanticmetadata.lire.imageanalysis.features.global.AutoColorCorrelogram;
import net.semanticmetadata.lire.imageanalysis.features.global.BinaryPatternsPyramid;
import net.semanticmetadata.lire.imageanalysis.features.global.CEDD;
import net.semanticmetadata.lire.imageanalysis.features.global.ColorLayout;
import net.semanticmetadata.lire.imageanalysis.features.global.EdgeHistogram;
import net.semanticmetadata.lire.imageanalysis.features.global.FCTH;
import net.semanticmetadata.lire.imageanalysis.features.global.FuzzyColorHistogram;
import net.semanticmetadata.lire.imageanalysis.features.global.FuzzyOpponentHistogram;
import net.semanticmetadata.lire.imageanalysis.features.global.Gabor;
import net.semanticmetadata.lire.imageanalysis.features.global.JCD;
import net.semanticmetadata.lire.imageanalysis.features.global.JpegCoefficientHistogram;
import net.semanticmetadata.lire.utils.FileUtils;
import net.semanticmetadata.lire.utils.LuceneUtils;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;

import com.unbank.utils.ImageUtils;

public class IndexCreater {
	private static final String indexPath = "test-index";
	private static final String indexSource = "num";
//	private static Class<? extends GlobalFeature> globalFeatureClass = ColorLayout.class;

	public static void main(String[] args) {
		
		createIndexing();
	}
	public static void createIndexing() {
		try {
			IndexWriter iw = LuceneUtils.createIndexWriter(indexPath, true);
			// 得到本地图片
			ArrayList<String> images = FileUtils.getAllImages(new File(
					indexSource), true);
			GlobalDocumentBuilder globalDocumentBuilder = new GlobalDocumentBuilder();
			globalDocumentBuilder.addExtractor(EdgeHistogram.class);
			globalDocumentBuilder.addExtractor(FCTH.class);
			globalDocumentBuilder.addExtractor(CEDD.class);
			globalDocumentBuilder.addExtractor(ColorLayout.class);
			globalDocumentBuilder.addExtractor(FuzzyOpponentHistogram.class);
			globalDocumentBuilder.addExtractor(AutoColorCorrelogram.class);
			globalDocumentBuilder.addExtractor(BinaryPatternsPyramid.class);
			globalDocumentBuilder.addExtractor(FuzzyColorHistogram.class);
			globalDocumentBuilder.addExtractor(Gabor.class);
			globalDocumentBuilder.addExtractor(JCD.class);
			globalDocumentBuilder.addExtractor(JpegCoefficientHistogram.class);

			for (String identifier : images) {
				String[] testFilesPathTemp = identifier.split("\\\\");
				Document doc;
				try {
					doc = globalDocumentBuilder.createDocument(ImageUtils
							.toBufferImage(identifier),
							testFilesPathTemp[testFilesPathTemp.length - 1]
									.split("\\.")[0]);
					iw.addDocument(doc);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}

			}
			LuceneUtils.commitWriter(iw);
			LuceneUtils.closeWriter(iw);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void makeCat(String imgUrl) {
		BufferedImage bufferedImage = ImageUtils.toBufferImage(imgUrl);
		bufferedImage = ImageUtils.thresholdImage(bufferedImage, 150);
		int y = 0;
		int wight = 11;
		int hight = bufferedImage.getHeight();
		try {
			ImageIO.write(
					ImageUtils.catImage(bufferedImage, 7, y, wight, hight),
					"jpg", new File(7 + "_" + new Date().getTime() + ".jpg"));
			ImageIO.write(
					ImageUtils.catImage(bufferedImage, 19, y, wight, hight),
					"jpg", new File(19 + "_" + new Date().getTime() + ".jpg"));
			ImageIO.write(
					ImageUtils.catImage(bufferedImage, 32, y, wight, hight),
					"jpg", new File(32 + "_" + new Date().getTime() + ".jpg"));
			ImageIO.write(
					ImageUtils.catImage(bufferedImage, 45, y, wight, hight),
					"jpg", new File(45 + "_" + new Date().getTime() + ".jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}



}
