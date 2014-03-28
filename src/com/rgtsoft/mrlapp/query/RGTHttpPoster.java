package com.rgtsoft.mrlapp.query;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class RGTHttpPoster {
	
	public static final int HTTP_SEND_OK = 1;
	public static final int HTTP_SEND_ERROR = 0;
	
	private HttpClient httpClient;
	private HttpPost httpPost;
	
	private HttpResponse httpResponse;
	private Thread httpThread;
	
	
	//constructor 
	public RGTHttpPoster(String link) {
		
		httpClient = new DefaultHttpClient();
		httpPost = new HttpPost(link);
		
	}
	
	public void doPost(final ArrayList<NameValuePair> data, final Handler handler) {
		
		httpThread = new Thread() {
			
			@Override
			public void run() {
				Message msg = new Message();
				
				try {
					//data preparation
					httpPost.setEntity(new UrlEncodedFormEntity(data, HTTP.UTF_8));
					//data execute
					httpResponse = httpClient.execute(httpPost);
					msg.what = HTTP_SEND_OK;
					msg.obj = EntityUtils.toString(httpResponse.getEntity(), HTTP.UTF_8);
					Log.d("HTTP Response OK", httpResponse.toString());
					
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					msg.what = HTTP_SEND_ERROR;
				} catch (ClientProtocolException e) {
					e.printStackTrace();
					msg.what = HTTP_SEND_ERROR;
				} catch (IOException e) {
					e.printStackTrace();
					msg.what = HTTP_SEND_ERROR;
				} finally {
					
					handler.sendMessage(msg);
				}
			}
			
		};
		
		httpThread.start();

	}
	

}
