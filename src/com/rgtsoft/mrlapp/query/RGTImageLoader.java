package com.rgtsoft.mrlapp.query;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class RGTImageLoader {
	
	private Thread thread;
	private Handler handler;
	
	public RGTImageLoader(final String imgURL, final ImageView image) {
		
		handler = new Handler() {
			
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 0: 
					
					image.setImageBitmap(null);
					
					break;
				case 1: 
					
					image.setImageBitmap((Bitmap) msg.obj);
					
					break;
				
				}
				
			}
			
			
		};
		
		thread = new Thread() {
			
			@Override
			public void run() {
				
				Message message = new Message();
				
				try {
					
					URL url = new URL(imgURL);
					Bitmap bitmap = BitmapFactory.decodeStream(url.openStream());
					message.what = 1;
					message.obj = bitmap;
					handler.sendMessage(message);
				} catch (MalformedURLException e) {
					e.printStackTrace();
					message.what = 0;
					handler.sendMessage(message);
				} catch (IOException e) {
					e.printStackTrace();
					message.what = 0;
					handler.sendMessage(message);
				}
			
			}
			
		};
		thread.start();
	}

}
