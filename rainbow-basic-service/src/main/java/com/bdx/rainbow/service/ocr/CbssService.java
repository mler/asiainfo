package com.bdx.rainbow.service.ocr;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.imageio.ImageIO;

public class CbssService {
    
	/**
	 * 验证码图片处理
	 * @param imageFile
	 * @param destFile
	 * @param average
	 * @throws IOException
	 */
    public void grey(File imageFile,File destFile,int average) throws IOException
    { 
    	 BufferedImage img = ImageIO.read(imageFile);
    	 
         if (img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0) {
             return;
         }
    	
        int width = img.getWidth(null);
        int height = img.getHeight(null);
        int minY = img.getMinY();
        int minX = img.getMinX();
         
    	for (int y = minY; y < height; y++) {
    	    for (int x = minX; x < width; x++) {
    	        int rgb = img.getRGB(x, y);
    	        Color color = new Color(rgb); // 根据rgb的int值分别取得r,g,b颜色。
    	        int gray = (int) (0.3 * color.getRed() + 0.59 * color.getGreen() + 0.11 * color.getBlue());
    	        Color newColor = new Color(gray, gray, gray);
    	        img.setRGB(x, y, newColor.getRGB());
    	    }
    	}
    	
    	for (int y = minY; y < height; y++) {
    	    for (int x = minX; x < width; x++) {
    	        int rgb = img.getRGB(x, y);
    	        Color color = new Color(rgb); // 根据rgb的int值分别取得r,g,b颜色。
    	        Color newColor = new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue());
    	        img.setRGB(x, y, newColor.getRGB());
    	    }
    	}
    	
    	for (int y = minY; y < height; y++) {
    	    for (int x = minX; x < width; x++) {
    	        int rgb = img.getRGB(x, y);
    	        Color color = new Color(rgb); // 根据rgb的int值分别取得r,g,b颜色。
    	        
    	        int value = 255 - color.getBlue();
    	        if (value < average) {
    	            Color newColor = new Color(0, 0, 0);
    	            img.setRGB(x, y, newColor.getRGB());
    	        } else {
    	            Color newColor = new Color(255, 255, 255);
    	            img.setRGB(x, y, newColor.getRGB());
    	        }
    	    }
    	}

		ImageIO.write(img, "jpg", destFile);
    }
    
    public InputStream grey(InputStream inFile,int average) throws Exception
    { 
    	try{
			BufferedImage img = ImageIO.read(inFile);

			if (img == null || img.getWidth(null) <= 0
					|| img.getHeight(null) <= 0) {
				return null;
			}

			int width = img.getWidth(null);
			int height = img.getHeight(null);
			int minY = img.getMinY();
			int minX = img.getMinX();

			for (int y = minY; y < height; y++) {
				for (int x = minX; x < width; x++) {
					int rgb = img.getRGB(x, y);
					Color color = new Color(rgb); // 根据rgb的int值分别取得r,g,b颜色。
					int gray = (int) (0.3 * color.getRed() + 0.59
							* color.getGreen() + 0.11 * color.getBlue());
					Color newColor = new Color(gray, gray, gray);
					img.setRGB(x, y, newColor.getRGB());
				}
			}

			for (int y = minY; y < height; y++) {
				for (int x = minX; x < width; x++) {
					int rgb = img.getRGB(x, y);
					Color color = new Color(rgb); // 根据rgb的int值分别取得r,g,b颜色。
					Color newColor = new Color(255 - color.getRed(),
							255 - color.getGreen(), 255 - color.getBlue());
					img.setRGB(x, y, newColor.getRGB());
				}
			}

			for (int y = minY; y < height; y++) {
				for (int x = minX; x < width; x++) {
					int rgb = img.getRGB(x, y);
					Color color = new Color(rgb); // 根据rgb的int值分别取得r,g,b颜色。

					int value = 255 - color.getBlue();
					if (value < average) {
						Color newColor = new Color(0, 0, 0);
						img.setRGB(x, y, newColor.getRGB());
					} else {
						Color newColor = new Color(255, 255, 255);
						img.setRGB(x, y, newColor.getRGB());
					}
				}
			}

			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(img, "jpg", os);
			return new ByteArrayInputStream(os.toByteArray());
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		throw e;
    	}
    	finally
    	{
    		if(inFile != null)
    		inFile.close();
    	}
    }
   
    public void loadCert() {
		String path = this.getClass().getResource("/").getPath();
		try {
			System.out.println(URLDecoder.decode(path, "utf-8"));
			path = URLDecoder.decode(path, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		File f = new File(path + "/cbss.cer");
		System.setProperty("javax.net.ssl.trustStore", f.getPath());
		System.setProperty("javax.net.ssl.trustStorePassword", "cbsscbss");

	}
    
    
//    public String iyouyao(String url) throws Exception
//    {}
    
    
    
    
	public final static void main(String[] args) throws Exception
	{
		
//		String code = "6920312604458";
//		
//		System.out.println();
		
		CbssService imageService = new CbssService();
		
		File temp = new File("/Users/mler/Desktop/temp1.jpg");
		
//		String captchaImage_url = "http://tool.iyouyao.com/getcode?"+Math.random();
		
		
//		String captchaImage_url = "http://sp.drugadmin.com/showValidateCodeTwo?"+Math.random();
//		System.out.println("url:"+captchaImage_url);
//		Map<HttpContext, InputStream> image_in_map = HttpClientUtil.downloadInputStream(captchaImage_url, null, null, null);
//		Map.Entry<HttpContext, InputStream> entry = image_in_map.entrySet().iterator().next();
//		InputStream image_in = imageService.grey(entry.getValue(), 120);
//		ImageIO.write(ImageIO.read(image_in), "jpg", temp);
//		if(image_in != null)
//			image_in.close();
		
		EasyOCR ocr = new EasyOCR();
		
		String code = ocr.discern(temp);
		
		System.out.println(code);
		
		
//		String url = "http://tool.iyouyao.com/ajaxbarcode";
//		Map<String,String> param = new HashMap<String, String>(2);
////		$.ajax({
////			   type: "post",
////			   url: "/ajaxbarcode",
////			   data: {'code':code,'backno':backno},
////			   dataType:'json',
////			   beforeSend:function(xhr){
////					$('#search').css("display","block");
////				   $('#loading').css("display","block");
////		        },
////			   success: function(msg){
//		param.put("code", "81038990072650163045");
//		param.put("backno", "1111");
//		
//		Map<HttpContext,String> responseMap = HttpClientUtil.getHttpContextAndResponse(url, null, entry.getKey(), param, "utf-8");
//		
//		if(responseMap != null && responseMap.isEmpty() == false)
//		{
//			String html = responseMap.values().iterator().next();
//			
//			System.out.println("返回HTML："+html);
//			
//		}
		
		

		
		
		
		
		
//		String srcURL1 = "/Users/luyiq/Pictures/照片/101NCD90/DSC_0002.JPG";
//		String destURL1 = "/Users/luyiq/Pictures/test/test2.jpg";
//		String srcURL = "http://10.10.19.180:8080/mapp_product/download_10010/images/res_mall_10010_com_mall_res_uploader_temp_20120331005057815662736_310_310_jpg.jpg";
//		String srcURL = "/Users/mler/Desktop/1111.jpg";
//		String destURL = "/Users/mler/Desktop/test";
//		imageService.grey(new File(srcURL), new File(destURL),100);
		
	}
}
