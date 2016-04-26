package com.bdx.rainbow.service.imgscan;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.bdx.rainbow.common.util.ImageUtil;

public class ImgScanService {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
//		// TODO Auto-generated method stub
//		Map<Integer,String> hashCodes = new HashMap<Integer,String>();
//
//	    String hashCode = null;
//
//		String fileName = "/Users/mler/Desktop/test/";
//		
////				BufferedImage image = ImageUtil.cut(srcImageFile, destImageFile, width, height, rect);
//		
////		Map<Integer,String> touchIds = getSourceTouchId(ImageIO.read(new File(fileName + "source.jpg")));
////		for(Integer key : touchIds.keySet())
////		{
////			System.out.println(key+":"+touchIds.get(key));
////		}
//
//		String code1 = getImageHashCode(fileName+"3.pic_hda.jpg");
//		String code2 = getImageHashCode(fileName+"4.pic_hda.jpg");
//		
//		  System.out.println(fileName+"1.pic_hda.jpg : " +getImageHashCode(fileName+"1.pic_hda.jpg"));
//		  System.out.println(fileName+"2.pic_hda.jpg : " +getImageHashCode(fileName+"2.pic_hda.jpg"));
//		
//		  int difference = hammingDistance(code1, code2);
//		    System.out.print("汉明距离:"+difference+"     ");
//		    if(difference==0){
//		    	System.out.println("一样");
//		    }else if(difference<=5){
//		    	System.out.println("非常相似");
//		    }else if(difference<=10){
//		    	System.out.println("有点相似");
//		    }else if(difference>10){
//		    	System.out.println("完全不一样");
//		    }
		
		
		// TODO Auto-generated method stub
		Map<String,String> hashCodes = new HashMap<String,String>();

	    String hashCode = null;

		String fileName = "/Users/mler/Desktop/test1/";
		
//		BufferedImage image = ImageUtil.cut(srcImageFile, destImageFile, width, height, rect);
		
		System.out.println("===================原图的HashCode========================");
		
		Map<Integer,String> touchIds = getSourceTouchId(ImageIO.read(new File(fileName + "source.jpg")));
		for(Integer key : touchIds.keySet())
		{
			System.out.println(key+":"+touchIds.get(key));
		}
		System.out.println("=======================================================");
		
		
		for(int i = 315;i<=373;i++){
			System.out.println(fileName+"" +i+".pic.jpg");
		    hashCode = getImageHashCode(fileName+"" +i+".pic.jpg");
		    hashCodes.put(i+"",hashCode);
		}
		System.out.println("===================待比对的HashCode========================");
		
		for (String key:hashCodes.keySet())
        {
			System.out.println(key+":"+hashCodes.get(key));
		}
		
		System.out.println("=======================================================");
		for (String key:hashCodes.keySet())
        {
		    int difference = hammingDistance(touchIds.values(), hashCodes.get(key));
		    System.out.print("汉明距离:"+difference+"     ");
		    if(difference==0){
		    	System.out.println("source.jpg图片跟"+(key)+".pic.jpg一样");
		    }else if(difference<=5){
		    	System.out.println("source.jpg图片跟"+(key)+".pic.jpg非常相似");
		    }else if(difference<=10){
		    	System.out.println("source.jpg图片跟"+(key)+".pic.jpg有点相似");
		    }else if(difference>10){
		    	System.out.println("source.jpg图片跟"+(key)+".pic.jpg完全不一样");
		    }
        }

	}
	
	
	/**
	 * 输入原图，生成各个角度的图片指纹
	 * @param source
	 * @return
	 * @throws IOException 
	 */
	public static Map<Integer,String> getSourceTouchId(BufferedImage img) throws IOException{
		
		Map<Integer,String> hashCodeMap = new HashMap<Integer, String>();
		
		for(int angel=0;angel<=360;angel=angel+30)
		{
			BufferedImage temp_image = img;
			if(angel > 0){
				temp_image = ImageUtil.rotate(img, angel);
				ImageIO.write(temp_image, "jpg", new File("/Users/mler/Desktop/test1/source_"+angel+".jpg"));
			}
			
			String hashCode = getImageHashCode(temp_image);
			
			hashCodeMap.put(angel, hashCode);
		}
		
		return hashCodeMap;
	}
	
	/**
	 * 根据图片名称获取图片hashcode
	 * @param String fileName
	 * @retuen String hashCode
	 */
	public static String getImageHashCode(BufferedImage source)
	{
		StringBuffer hashCode = new StringBuffer();
		
//		BufferedImage source = ImageUtil.readPNGImage(fileName);// 读取文件

//		System.out.println("source==>" + source.toString());

		int width = 8;
		int height = 8;

		// 第一步，缩小尺寸。
		// 将图片缩小到8x8的尺寸，总共64个像素。这一步的作用是去除图片的细节，只保留结构、明暗等基本信息，摒弃不同尺寸、比例带来的图片差异。
		BufferedImage thumb = ImageUtil.thumb(source, width, height, false);

		// 第二步，简化色彩。
		// 将缩小后的图片，转为64级灰度。也就是说，所有像素点总共只有64种颜色。
		int[] pixels = new int[width * height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				pixels[i * height + j] = ImageUtil.rgbToGray(thumb.getRGB(i,
						j));
			}
		}
		
		// 第三步，计算平均值。
		// 计算所有64个像素的灰度平均值。
		int avgPixel = ImageUtil.average(pixels);

		// 第四步，比较像素的灰度。
		// 将每个像素的灰度，与平均值进行比较。大于或等于平均值，记为1；小于平均值，记为0。
		int[] comps = new int[width * height];
		for (int i = 0; i < comps.length; i++) {
			if (pixels[i] >= avgPixel) {
				comps[i] = 1;
			} else {
				comps[i] = 0;
			}
		}

		// 第五步，计算哈希值。
		// 将上一步的比较结果，组合在一起，就构成了一个64位的整数，这就是这张图片的指纹。组合的次序并不重要，只要保证所有图片都采用同样次序就行了。
		for (int i = 0; i < comps.length; i += 4) {
			int result = comps[i] * (int) Math.pow(2, 3) + comps[i + 1]
					* (int) Math.pow(2, 2) + comps[i + 2]
					* (int) Math.pow(2, 1) + comps[i + 2];
			hashCode.append(binaryToHex(result));
		}

		// 得到指纹以后，就可以对比不同的图片，看看64位中有多少位是不一样的。
//		System.out.println(hashCode.toString() + "=====>hashCode");
		
		return  hashCode.toString();
	
	}
	
	public static String getImageHashCode(String fileName){
		
		StringBuffer hashCode = new StringBuffer();
		
//		BufferedImage source = ImageUtil.readPNGImage(fileName);// 读取文件

//		System.out.println("source==>" + source.toString());

		int width = 8;
		int height = 8;

		// 第一步，缩小尺寸。
		// 将图片缩小到8x8的尺寸，总共64个像素。这一步的作用是去除图片的细节，只保留结构、明暗等基本信息，摒弃不同尺寸、比例带来的图片差异。
//		BufferedImage thumb = ImageUtil.thumb(source, width, height, false);

		// 第二步，简化色彩。
		// 将缩小后的图片，转为64级灰度。也就是说，所有像素点总共只有64种颜色。
		int[] pixels = new int[width * height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
//				pixels[i * height + j] = ImageUtil.rgbToGray(thumb.getRGB(i,j));
			}
		}

		// 第三步，计算平均值。
		// 计算所有64个像素的灰度平均值。
		int avgPixel = ImageUtil.average(pixels);

		// 第四步，比较像素的灰度。
		// 将每个像素的灰度，与平均值进行比较。大于或等于平均值，记为1；小于平均值，记为0。
		int[] comps = new int[width * height];
		for (int i = 0; i < comps.length; i++) {
			if (pixels[i] >= avgPixel) {
				comps[i] = 1;
			} else {
				comps[i] = 0;
			}
		}

		// 第五步，计算哈希值。
		// 将上一步的比较结果，组合在一起，就构成了一个64位的整数，这就是这张图片的指纹。组合的次序并不重要，只要保证所有图片都采用同样次序就行了。
		for (int i = 0; i < comps.length; i += 4) {
			int result = comps[i] * (int) Math.pow(2, 3) + comps[i + 1]
					* (int) Math.pow(2, 2) + comps[i + 2]
					* (int) Math.pow(2, 1) + comps[i + 2];
			hashCode.append(binaryToHex(result));
		}

		// 得到指纹以后，就可以对比不同的图片，看看64位中有多少位是不一样的。
//		System.out.println(hashCode.toString() + "=====>hashCode");
		
		return  hashCode.toString();
	}
	
	
	/**
	 * 二进制转为十六进制
	 * 
	 * @param int binary
	 * @return char hex
	 */
	private static char binaryToHex(int binary) {
		char ch = ' ';
		switch (binary) {
		case 0:
			ch = '0';
			break;
		case 1:
			ch = '1';
			break;
		case 2:
			ch = '2';
			break;
		case 3:
			ch = '3';
			break;
		case 4:
			ch = '4';
			break;
		case 5:
			ch = '5';
			break;
		case 6:
			ch = '6';
			break;
		case 7:
			ch = '7';
			break;
		case 8:
			ch = '8';
			break;
		case 9:
			ch = '9';
			break;
		case 10:
			ch = 'a';
			break;
		case 11:
			ch = 'b';
			break;
		case 12:
			ch = 'c';
			break;
		case 13:
			ch = 'd';
			break;
		case 14:
			ch = 'e';
			break;
		case 15:
			ch = 'f';
			break;
		default:
			ch = ' ';
		}
		return ch;
	}
	

	/**
	 * 计算"汉明距离"（Hamming distance）。
	 * 如果不相同的数据位不超过5，就说明两张图片很相似；如果大于10，就说明这是两张不同的图片。
	 * @param sourceHashCode 源hashCode
	 * @param hashCode 与之比较的hashCode
	 */
	public static int hammingDistance(String sourceHashCode, String hashCode) {
		int difference = 0;
		int len = sourceHashCode.length();
		
		for (int i = 0; i < len; i++) {
			if (sourceHashCode.charAt(i) != hashCode.charAt(i)) {
				difference ++;
			} 
		}
		
		return difference;
	}
	
	public static int hammingDistance(Collection<String> sourceHashCodes, String hashCode) {
		int difference = 16;
		
		for (String sourceHashCode : sourceHashCodes) {
			 int temp_diff = hammingDistance(sourceHashCode, hashCode);
			 if(temp_diff<difference)
				 difference = temp_diff;
		}
		
		return difference;
	}
	
	public static int hammingDistance( String sourceHashCode ,Collection<String> destHashCodes) {
		int difference = 16;
		
		for (String destcode : destHashCodes) {
			 int temp_diff = hammingDistance(sourceHashCode, destcode);
			 if(temp_diff<difference)
				 difference = temp_diff;
		}
		
		return difference;
	}

}
