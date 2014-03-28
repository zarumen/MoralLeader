package com.rgtsoft.mrlapp.module;

import com.rgtsoft.mrlapp.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class LightHomeActivity extends Activity{
	
	private String lHres;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lighthome);
		
		TextView lighthomeResponse = (TextView) findViewById(R.id.memberlist_tv);
		
		Bundle bundle = getIntent().getExtras();
		lHres = bundle.getString("lighthomeList");
		
		lighthomeResponse.setText(lHres);
		
		
		
	}

}
