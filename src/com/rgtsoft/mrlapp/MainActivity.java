package com.rgtsoft.mrlapp;



import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.rgtsoft.mrlapp.module.LightHomeActivity;
import com.rgtsoft.mrlapp.module.MemberListActivity;
import com.rgtsoft.mrlapp.query.GridMenuAdapter;
import com.rgtsoft.mrlapp.query.RGTImageLoader;
import com.rgtsoft.mrlapp.query.RGTHttpPoster;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity{
	
	public static final int[] NO_PROFILE_ARRAY = {
		R.drawable.noprofile_img,
		R.drawable.noprofile_img,
		R.drawable.noprofile_img,
		R.drawable.noprofile_img,
		R.drawable.noprofile_img,
		R.drawable.noprofile_img,
		R.drawable.noprofile_img,
		R.drawable.noprofile_img,
		R.drawable.noprofile_img,
	};
	
	private TextView mrl_cscore;
	private TextView mrl_fullname;
	private TextView mrl_id;
	private TextView mrl_position;
	private TextView mrl_ascore;
	private ImageView mrl_pic;
	
	private Context context;
	private GridView menu_grid;
	private ArrayList<Drawable> icons;
	private ArrayList<String> icons_name;
	
	private String picfin;
	private String position_mrl;
	private String full_name;
	private String ascore;
	private String cscore;
	private String pmrl_id;
	private String pmrl_id_show;
	private String person_id;
	
	private Resources res;
	private String[] mrl_pos;
	
	private RGTHttpPoster postButton;
	private String lighthomeResponse;
	private String memberlistResponse;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		Bundle bundle = getIntent().getExtras();
		String info = bundle.getString("data_senduser");
		int mrl_lvl = 0;
		
		// put StringArray from string.xml
		res = getResources();
		mrl_pos = res.getStringArray(R.array.mrl_level_name);
		
		try {
			
			JSONObject jsonObject = (JSONObject) new JSONTokener(info).nextValue();
			JSONObject jPic = (JSONObject) jsonObject.get("user_picture");
			picfin = jPic.getString("picture");
			JSONObject data_info = (JSONObject) jsonObject.get("user_info");
			String papoint = data_info.getString("pmrl_accumulated_score");
			String m_pos = data_info.getString("pmrl_level_id");
			String lname = data_info.getString("person_surname");
			String p_id = data_info.getString("person_id");
			String fname = data_info.getString("person_name");
			String pcpoint = data_info.getString("pmrl_current_score");
			String id = data_info.getString("pmrl_code");

			
			full_name = fname +" "+ lname;
			pmrl_id_show = "เลขที่ผู้นำฟื้นฟู: "+id;
			ascore = "คะแนนรวมทั้งหมด: "+papoint;
			cscore = "คะแนนปัจจุบัน: "+pcpoint;
			person_id = p_id;
			pmrl_id = id;
			
			mrl_lvl = Integer.parseInt(m_pos);
			position_mrl = "ตำแหน่ง: "+mrl_pos[mrl_lvl];
			
			
		} catch (JSONException e) {
			e.printStackTrace();
			Toast.makeText(context, "NOT BREAK JSON", Toast.LENGTH_SHORT).show();
		}
		
		//------------------------------ Profile Code----------------------------------//
		
		//view matching
		mrl_fullname = (TextView) findViewById(R.id.mrl_name);
		mrl_id = (TextView) findViewById(R.id.mrl_id);
		mrl_position = (TextView) findViewById(R.id.mrl_position);
		mrl_ascore = (TextView) findViewById(R.id.mrl_ascore);
		mrl_cscore = (TextView) findViewById(R.id.mrl_cscore);
		mrl_pic = (ImageView) findViewById(R.id.pro_img);
		
		RGTImageLoader imgLoad = new RGTImageLoader(picfin, mrl_pic);
		
		mrl_id.setText(pmrl_id_show);
		mrl_fullname.setText(full_name);
		mrl_position.setText(position_mrl);
		mrl_ascore.setText(ascore);
		mrl_cscore.setText(cscore);
		
		
		
		//------------------------------ Main Menu Code----------------------------------//
		
		menu_grid = (GridView) findViewById(R.id.main_menu);
		
		//Icon Menu Preparation
		icons = new ArrayList<Drawable>();
		icons_name = new ArrayList<String>();
		context = MainActivity.this;
		
		//icon array
		icons.add(getResources().getDrawable(R.drawable.menu_point));
		icons.add(getResources().getDrawable(R.drawable.menu_orgchart));
		icons.add(getResources().getDrawable(R.drawable.menu_news));
		icons.add(getResources().getDrawable(R.drawable.menu_lhome));
		//icon name array
		icons_name.add("เช็คคะแนน");
		icons_name.add("ดูโครงสร้าง");
		icons_name.add("ข้อมูลข่าวสาร");
		icons_name.add("บ้านแสงสว่าง");
		
		// Set Adapter for GridView
		menu_grid.setAdapter(new GridMenuAdapter(context, icons, icons_name));
		
		// Set Event On Item Click
		menu_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				Toast.makeText(context, "Position ="+position , Toast.LENGTH_SHORT).show();
				
				switch(position) {
				
					case 1: 
						//Create MemberListActivity
						postButton = new RGTHttpPoster("http://new.rgtcenter.com/android/mrlmemberlist.php");
						
						ArrayList<NameValuePair> pid_set = new ArrayList<NameValuePair>();
						
						pid_set.add(new BasicNameValuePair("person_id", person_id));
						
						postButton.doPost(pid_set, new Handler(){
							
							public void handleMessage(android.os.Message msg) {
								
								switch (msg.what) {
								
									case RGTHttpPoster.HTTP_SEND_OK: 
									
									memberlistResponse = (String) msg.obj;
									
									break;
								
								case RGTHttpPoster.HTTP_SEND_ERROR: 
									
									Toast.makeText(context, "Button Break!", Toast.LENGTH_SHORT).show();
									
									break;
								
								}
								
							};
							
						});
						
						
						Intent memberlist = new Intent(MainActivity.this, MemberListActivity.class);
						memberlist.putExtra("memberList", memberlistResponse);
						startActivity(memberlist);
						
						break;
				
					case 3: 
						//Create LightHomeActivity
						postButton = new RGTHttpPoster("http://new.rgtcenter.com/android/lighthomecenter.php");
						
						ArrayList<NameValuePair> id_set = new ArrayList<NameValuePair>();
						
						id_set.add(new BasicNameValuePair("mrl_id", pmrl_id));
						
						postButton.doPost(id_set, new Handler(){
							
							public void handleMessage(android.os.Message msg) {
								
								switch (msg.what) {
								
								case RGTHttpPoster.HTTP_SEND_OK: 
									
									lighthomeResponse = (String) msg.obj;
									
									break;
								
								case RGTHttpPoster.HTTP_SEND_ERROR: 
									
									Toast.makeText(context, "Button Break!", Toast.LENGTH_SHORT).show();
									
									break;
								
								
								}
								
							};
							
						});
						
						Intent lightHome = new Intent(MainActivity.this, LightHomeActivity.class);
						lightHome.putExtra("lighthomeList", lighthomeResponse);
						startActivity(lightHome);
						break;
				
				}
				
			}
		});
		
		
		
		
		
	}

}
