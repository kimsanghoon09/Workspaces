package com.sh.network.image.download;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ImageDownloader {
	
	public static void main(String[] args) {
		new ImageDownloader().start();
	}
	
	public void start() {
		String imgUrl = "https://upaae.com/wp-content/uploads/2016/07/osi-layers.png";
		URL url = null;
		URLConnection conn = null;
		
		try {
			url = new URL(imgUrl);
			conn = url.openConnection();

			try(
				BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("osi-layers.png"));
			){
				
				int len = 0;
				byte[] data = new byte[8192];
				while((len = bis.read(data)) != -1) {
					bos.write(data, 0, len);
				}	
			}


		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
}
