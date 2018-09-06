package com.jian.ssm.util;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import net.coobird.thumbnailator.Thumbnails;


/**
 * 
 * @ClassName:  CutImageUtil   
 * @Description:TODO   
 * @author: jianlinwei
 * @date:   2018年5月7日 下午2:02:08   
 *
 */
public class CutImageUtil {
   
	   public   byte[]   cutimg(byte[]  img, int let  ,int top){
		InputStream  is  = new ByteArrayInputStream(img);
		try {
			ImageInputStream  iis  = ImageIO.createImageInputStream(is);
			ImageReader  ir  = ImageIO.getImageReadersBySuffix("jpg").next();
			ir.setInput(iis, true);
			ImageReadParam  irp  = ir.getDefaultReadParam();
			
			Rectangle   rec   = new Rectangle(let-50, top, 210, 210);
			irp.setSourceRegion(rec);
			BufferedImage  bf =ir.read(0, irp);
			ByteArrayOutputStream  bytearray  = new ByteArrayOutputStream();
			ImageIO.write(bf, "jpg", bytearray);
			return bytearray.toByteArray(); 
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	   }
	  
	   /**
		   * 
		   * @Title: compressImg   
		   * @Description: TODO   
		   * @param: @return 
		   * @author: JianLinWei     
		   * @return: byte[]      
		   * @throws
		   */
		   public  byte[]  compressImg(byte[]  oldImg) {
			   byte[]  newImg = null;
			   ByteArrayInputStream intputStream = new ByteArrayInputStream(oldImg);
	           ByteArrayOutputStream baos = new ByteArrayOutputStream();
	           try {
				Thumbnails.of(intputStream).scale(0.5f).outputQuality(0.8f).toOutputStream(baos);
			} catch (IOException e) {
				e.printStackTrace();
			}
	           newImg = baos.toByteArray();
	           return newImg;
		   }
}
