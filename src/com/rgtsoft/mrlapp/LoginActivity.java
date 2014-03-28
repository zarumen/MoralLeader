package com.rgtsoft.mrlapp;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.rgtsoft.mrlapp.query.RGTHttpPoster;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity{
	
	private EditText input_mrlid;
	private EditText input_perid;
	private Button submit_button;
	
	private Context context;
	private RGTHttpPoster poster;
	private String input_mrl_per_id;
	private String input_mrl_id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		//view matching
		input_mrlid = (EditText) findViewById(R.id.input_mrl_id);
		input_perid = (EditText) findViewById(R.id.input_mrl_person_id);
		submit_button= (Button) findViewById(R.id.submit_button);
		
		context = this;
		
		//add event button
		
		submit_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				//change input to String prepare to sending
				input_mrl_id = input_mrlid.getText().toString().trim();
				Long input_idcard = Long.parseLong(input_perid.getText().toString());
				input_mrl_per_id = input_idcard.toString();
				
				if(input_mrl_id.length() == 0) {
					
					Toast.makeText(context, "กรุณากรอกเลขผู้นำฟื้นฟูฯ", Toast.LENGTH_SHORT).show();
					
				} else if (input_mrl_per_id.length() == 0) {
					
					Toast.makeText(context, "กรุณากรอกเลขบัตรประจำตัวประชาชน", Toast.LENGTH_SHORT).show();
					
				} else {
					
					poster = new RGTHttpPoster("http://new.rgtcenter.com/android/login.php");
					
					ArrayList<NameValuePair> datalogin = new ArrayList<NameValuePair>();
					datalogin.add(new BasicNameValuePair("mrl_id", input_mrl_id));
					datalogin.add(new BasicNameValuePair("mrl_idcard", input_mrl_per_id));
					
					
					poster.doPost(datalogin, new Handler(){
						
						public void handleMessage(android.os.Message msg) {
							switch(msg.what) {
							
							case RGTHttpPoster.HTTP_SEND_OK: 
								
								String jsonResponse = (String) msg.obj;
								
								//convert JSON to datareceive
								
								try {
									
									JSONObject jsonObject = (JSONObject) new JSONTokener(jsonResponse).nextValue();
									JSONObject dataArray = (JSONObject) jsonObject.get("data_user");
									int login_check = (Integer) jsonObject.get("login_check");
									String data_user = dataArray.toString();
									
									if(login_check != 0) {
										
										Intent intent = new Intent(LoginActivity.this, MainActivity.class);
										intent.putExtra("data_senduser", data_user);
										startActivity(intent);
										
										finish();
										
									} else {
										 
										Toast.makeText(context, "ไม่มีผู้นำฟื้นฟูรหัสนี้ กรุณาติดต่อผู้ประสานงาน", Toast.LENGTH_SHORT).show();
									}
									
								} catch (JSONException e) {
									
									Toast.makeText(context, "Cannot Convert JSONobject!", Toast.LENGTH_SHORT).show();
									e.printStackTrace();
								}
								
								break;
							case RGTHttpPoster.HTTP_SEND_ERROR: 
								
								Toast.makeText(context, "Login ERROR!", Toast.LENGTH_SHORT).show();
								
								break;
							
							
							}
							
							
						};
						
					});
				}
 				
				
			}
		});
	}

}
