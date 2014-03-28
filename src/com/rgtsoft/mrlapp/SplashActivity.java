package com.rgtsoft.mrlapp;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.widget.ImageView;

public class SplashActivity extends Activity {
	
	private ImageView splash_img;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		splash_img = (ImageView) findViewById(R.id.image_splash);
		
		new Thread() {
			
			
			@Override
			public void run() {
				
				try {
					sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					startActivity(new Intent(SplashActivity.this, LoginActivity.class));
					SplashActivity.this.finish();
				}
			}
			
			
		}.start();
		
		
	}


}
