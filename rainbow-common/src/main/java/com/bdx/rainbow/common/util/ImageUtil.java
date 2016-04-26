package com.bdx.rainbow.common.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 图片工具类，主要针对图片水印处理
 * 
 * @author  mler
 */
@SuppressWarnings("restriction")
public class ImageUtil {

	// 项目根目录路径
	public static final String path ="";
	
	/**
	 * 生成缩略图 <br/>
	 * 保存:ImageIO.write(BufferedImage, imgType[jpg/png/...], File);
	 * 
	 * @param source
	 *            原图片
	 * @param width
	 *            缩略图宽
	 * @param height
	 *            缩略图高
	 * @param b
	 *            是否等比缩放
	 * */
	public static BufferedImage thumb(BufferedImage source, int width,int height, boolean b) {
		// targetW，targetH分别表示目标长和宽
		int type = source.getType();
		BufferedImage target = null;
		double sx = (double) width / source.getWidth();
		double sy = (double) height / source.getHeight();

		if (b) {
			if (sx > sy) {
				sx = sy;
				width = (int) (sx * source.getWidth());
			} else {
				sy = sx;
				height = (int) (sy * source.getHeight());
			}
		}

		if (type == BufferedImage.TYPE_CUSTOM) { // handmade
			ColorModel cm = source.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(width,height);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			target = new BufferedImage(cm, raster, alphaPremultiplied, null);
		} else
			target = new BufferedImage(width, height, type);
		
		Graphics2D g = target.createGraphics();
		// smoother than exlax:
		g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
		g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
		g.dispose();
		return target;
	}

	/**
	 * 图片水印
	 * 
	 * @param imgPath
	 *            待处理图片
	 * @param markPath
	 *            水印图片
	 * @param x
	 *            水印位于图片左上角的 x 坐标值
	 * @param y
	 *            水印位于图片左上角的 y 坐标值
	 * @param alpha
	 *            水印透明度 0.1f ~ 1.0f
	 * */
	public static void waterMark(String imgPath, String markPath, int x, int y,
			float alpha) {
		try {
			// 加载待处理图片文件
			Image img = ImageIO.read(new File(imgPath));

			BufferedImage image = new BufferedImage(img.getWidth(null),
					img.getHeight(null), BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(img, 0, 0, null);

			// 加载水印图片文件
			Image src_biao = ImageIO.read(new File(markPath));
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));
			g.drawImage(src_biao, x, y, null);
			g.dispose();

			// 保存处理后的文件
			FileOutputStream out = new FileOutputStream(imgPath);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 文字水印
	 * 
	 * @param imgPath
	 *            待处理图片
	 * @param text
	 *            水印文字
	 * @param font
	 *            水印字体信息
	 * @param color
	 *            水印字体颜色
	 * @param x
	 *            水印位于图片左上角的 x 坐标值
	 * @param y
	 *            水印位于图片左上角的 y 坐标值
	 * @param alpha
	 *            水印透明度 0.1f ~ 1.0f
	 */

	public static void textMark(String imgPath, String text, Font font,
			Color color, int x, int y, float alpha) {
		try {
			Font Dfont = (font == null) ? new Font("宋体", 20, 13) : font;

			Image img = ImageIO.read(new File(imgPath));

			BufferedImage image = new BufferedImage(img.getWidth(null),
					img.getHeight(null), BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();

			g.drawImage(img, 0, 0, null);
			g.setColor(color);
			g.setFont(Dfont);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));
			g.drawString(text, x, y);
			g.dispose();
			FileOutputStream out = new FileOutputStream(imgPath);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image);
			out.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * 读取JPEG图片
	 * @param filename 文件名
	 * @return BufferedImage 图片对象
	 */
	public static BufferedImage readJPEGImage(String filename)
	{
		try {
			InputStream imageIn = new FileInputStream(new File(filename));
			// 得到输入的编码器，将文件流进行jpg格式编码
			JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(imageIn);
			// 得到编码后的图片对象
			BufferedImage sourceImage = decoder.decodeAsBufferedImage();
			
			return sourceImage;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ImageFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 读取JPEG图片
	 * @param filename 文件名
	 * @return BufferedImage 图片对象
	 */
	public static BufferedImage readPNGImage(String filename)
	{
   
		try {
 			File inputFile = new File(filename);  
 	        BufferedImage sourceImage = ImageIO.read(inputFile);
			return sourceImage;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ImageFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 灰度值计算
	 * @param pixels 像素
	 * @return int 灰度值
	 */
	public static int rgbToGray(int pixels) {
		// int _alpha = (pixels >> 24) & 0xFF;
		int _red = (pixels >> 16) & 0xFF;
		int _green = (pixels >> 8) & 0xFF;
		int _blue = (pixels) & 0xFF;
		return (int) (0.3 * _red + 0.59 * _green + 0.11 * _blue);
	}
	
	/**
	 * 计算数组的平均值
	 * @param pixels 数组
	 * @return int 平均值
	 */
	public static int average(int[] pixels) {
		float m = 0;
		for (int i = 0; i < pixels.length; ++i) {
			m += pixels[i];
		}
		m = m / pixels.length;
		return (int) m;
	}
	
	
	/**
	 * @Description: 将srcImageFile裁剪后生成destImageFile
	 * @param srcImageFile
	 *            原始图
	 * @param destImageFile
	 *            目标图
	 * @param width
	 *            原始图预处理后width
	 * @param height
	 *            原始图预处理后height
	 * @param rect
	 *            目标图输出的格式(rect.x, rect.y, rect.width, rect.height)
	 * @throws IOException
	 */
	public static void cut(String srcImageFile, String destImageFile,int width, int height, Rectangle rect) throws IOException 
	{
		Image image = ImageIO.read(new File(srcImageFile));
		BufferedImage bImage = makeThumbnail(image, width, height);

//		// 把原始图片输出
//		ImageIO.write(bImage, "jpg", new File("img/src2.jpg"));

		saveSubImage(bImage, rect, new File(destImageFile));
	}

	/**
	 * @Description: 将srcImageFile裁剪后生成destImageFile
	 * @param srcImageFile
	 *            原始图
	 * @param destImageFile
	 *            目标图
	 * @param width
	 *            原始图预处理后width
	 * @param height
	 *            原始图预处理后height
	 * @param rect
	 *            目标图输出的格式(rect.x, rect.y, rect.width, rect.height)
	 * @throws IOException
	 */
	public static void cut(File srcImageFile, File destImageFile, int width,int height, Rectangle rect) throws IOException 
	{
		Image image = ImageIO.read(srcImageFile);
		BufferedImage bImage = makeThumbnail(image, width, height);

		saveSubImage(bImage, rect, destImageFile);
	}

	/**
	 * @Description: 对原始图片根据(x, y, width, height) = (0, 0, width,
	 *               height)进行缩放，生成BufferImage
	 * @param img
	 * @param width
	 *            预处理后图片的宽度
	 * @param height
	 *            预处理后图片高度
	 * @return
	 * @throws IOException
	 */
	private static BufferedImage makeThumbnail(Image img, int width, int height)
			throws IOException {
		BufferedImage tag = new BufferedImage(width, height, 1);
		Graphics g = tag.getGraphics();
		g.drawImage(img.getScaledInstance(width, height, 4), 0, 0, null);

		g.dispose();
		return tag;
	}

	/**
	 * @Description: 对BufferImage按照(x, y, width, height) = (subImageBounds.x,
	 *               subImageBounds.y, subImageBounds.width,
	 *               subImageBounds.height)进行裁剪
	 *               如果subImageBounds范围过大，则用空白填充周围的区域。
	 * 
	 * @param image
	 * @param subImageBounds
	 * @param destImageFile
	 * @throws IOException
	 */
	private static void saveSubImage(BufferedImage image,Rectangle subImageBounds, File destImageFile) throws IOException {
		String fileName = destImageFile.getName();
		String formatName = fileName.substring(fileName.lastIndexOf('.') + 1);
		BufferedImage subImage = new BufferedImage(subImageBounds.width,subImageBounds.height, 1);
		Graphics g = subImage.getGraphics();

		if ((subImageBounds.width > image.getWidth()) || (subImageBounds.height > image.getHeight())) 
		{
			int left = subImageBounds.x;
			int top = subImageBounds.y;
			
			//如果截取的宽度大于已有图片宽度，去差值的一半，为了将图画在中间
			if (image.getWidth() < subImageBounds.width)
				left = (subImageBounds.width - image.getWidth()) / 2;
			//如果截取的高度大于已有图片宽度，去差值的一半，为了将图画在中间
			if (image.getHeight() < subImageBounds.height)
				top = (subImageBounds.height - image.getHeight()) / 2;
			
			g.setColor(Color.white);
			g.fillRect(0, 0, subImageBounds.width, subImageBounds.height);
			g.drawImage(image, left, top, null);
//			ImageIO.write(image, formatName, destImageFile);
			
		} else {
//			System.out.println("left:"+subImageBounds.x+",top:"+subImageBounds.y);
			g.drawImage(image.getSubimage(subImageBounds.x, subImageBounds.y,
					subImageBounds.width, subImageBounds.height), 0, 0, null);
		}
		g.dispose();
		ImageIO.write(subImage, formatName, destImageFile);
	}
	
	public static BufferedImage cutImage(BufferedImage image,int x,int y,int width,int height) throws IOException {
		
		Rectangle subImageBounds = new Rectangle(x, y, width, height);
		
		BufferedImage subImage = new BufferedImage(subImageBounds.width,subImageBounds.height, 1);
		Graphics g = subImage.getGraphics();

		if ((subImageBounds.width > image.getWidth()) || (subImageBounds.height > image.getHeight())) 
		{
			int left = subImageBounds.x;
			int top = subImageBounds.y;
			
			//如果截取的宽度大于已有图片宽度，去差值的一半，为了将图画在中间
			if (image.getWidth() < subImageBounds.width)
				left = (subImageBounds.width - image.getWidth()) / 2;
			//如果截取的高度大于已有图片宽度，去差值的一半，为了将图画在中间
			if (image.getHeight() < subImageBounds.height)
				top = (subImageBounds.height - image.getHeight()) / 2;
			
			g.setColor(Color.white);
			g.fillRect(0, 0, subImageBounds.width, subImageBounds.height);
			g.drawImage(image, left, top, null);
//			ImageIO.write(image, formatName, destImageFile);
			
		} else {
//			System.out.println("left:"+subImageBounds.x+",top:"+subImageBounds.y);
			g.drawImage(image.getSubimage(subImageBounds.x, subImageBounds.y,
					subImageBounds.width, subImageBounds.height), 0, 0, null);
		}
		g.dispose();
		
		return subImage;
	}
	
	
	/**
	 * 图片旋转功能
	 * @param src 图片
	 * @param angel 旋转角度(旋转方向：向右)
	 * @return
	 */
	public static BufferedImage rotate(Image src, int angel) {
		int src_width = src.getWidth(null);
		int src_height = src.getHeight(null);
		// calculate the new image size 计算旋转后新图片的高度和宽度
		Rectangle rect_des = calcRotatedSize(new Rectangle(new Dimension(src_width, src_height)), angel);

		BufferedImage res = null;
		res = new BufferedImage(rect_des.width, rect_des.height,BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = res.createGraphics();
		g2.setColor(Color.WHITE);
		// transform
		g2.translate((rect_des.width - src_width) / 2,(rect_des.height - src_height) / 2);
		g2.rotate(Math.toRadians(angel), src_width / 2, src_height / 2);
		g2.drawImage(src, null, null);
		
		return res;
	}

	public static Rectangle calcRotatedSize(Rectangle src, int angel) {
		// if angel is greater than 90 degree, we need to do some conversion
		if (angel >= 90) {
			if(angel / 90 % 2 == 1){
				int temp = src.height;
				src.height = src.width;
				src.width = temp;
			}
			angel = angel % 90;
		}

		double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;
		double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
		double angel_alpha = (Math.PI - Math.toRadians(angel)) / 2;
		double angel_dalta_width = Math.atan((double) src.height / src.width);
		double angel_dalta_height = Math.atan((double) src.width / src.height);

		int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha
				- angel_dalta_width));
		int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha
				- angel_dalta_height));
		int des_width = src.width + len_dalta_width * 2;
		int des_height = src.height + len_dalta_height * 2;
		return new java.awt.Rectangle(new Dimension(des_width, des_height));
	}
	
	
	public static final void main(String[] args) throws IOException {
		// //2448 × 3264
		// File dest = new File("/Users/mler/Desktop/test333.jpg");
		// dest.deleteOnExit();
		//
		// Rectangle rect = new Rectangle(0,0, 1000, 1000);
		//
		// File srcImg = new File("/Users/mler/Desktop/img/source.jpg");
		// cut(srcImg, new File("/Users/mler/Desktop/test7.jpg"), 3000,3000,
		// rect);
		// System.out.println("ok");

		BufferedImage src = ImageIO.read(new File("/Users/mler/Desktop/img/source.jpg"));
		BufferedImage des = rotate(src, 30);
		ImageIO.write(des, "jpg", new File("/Users/mler/Desktop/444.jpg"));

		// bigger angel
		des = rotate(src, 150);
		ImageIO.write(des, "jpg", new File("/Users/mler/Desktop/555.jpg"));

		// bigger angel
		des = rotate(src, 270);
//		Assert.assertNotNull(des);
		ImageIO.write(des, "jpg", new File("/Users/mler/Desktop/666.jpg"));
		System.out.println("ok");
	}
	
	

		
	
	
}
