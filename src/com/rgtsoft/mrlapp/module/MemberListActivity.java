package com.rgtsoft.mrlapp.module;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.fima.cardsui.objects.CardStack;
import com.fima.cardsui.views.CardUI;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.rgtsoft.mrlapp.R;
import com.rgtsoft.mrlapp.cardui.MyImageCard;
import com.rgtsoft.mrlapp.query.ListMemberAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class MemberListActivity extends Activity{
	
	private ArrayList<String> fullname_array;
	private ArrayList<String> detail_array;
	private ArrayList<String> picture_array;
	
	private Resources res;
	private String[] mrl_pos;
	
	private CardUI mCardView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_memberlist);
		
		//==============Universal Image Loader Configuration=====================//
		
				ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
													.memoryCacheExtraOptions(720, 1280)
													.build();
				DisplayImageOptions options = new DisplayImageOptions.Builder()
													.build();
				ImageLoader.getInstance().init(config);
				
		//========================================================================
		
		Bundle bundle = getIntent().getExtras();
		String mList = bundle.getString("memberList");
		
		
		fullname_array = new ArrayList<String>();
		detail_array = new ArrayList<String>();
		picture_array = new ArrayList<String>();
		res = getResources();
		mrl_pos = res.getStringArray(R.array.mrl_level_name);
		
		try {
			
			JSONObject mListJson = (JSONObject) new JSONObject(mList);
			JSONArray mListArray = mListJson.getJSONArray("data_memberlist");
			
			for (int i= 0; i < mListArray.length(); i++) {
			
				JSONObject member = mListArray.getJSONObject(i);
				//fullname
				String fname = member.getString("person_name");
				String lname = member.getString("person_surname");
				String fullname = fname+" "+lname;
				fullname_array.add(fullname);
				
				//details
				String pmrl_code = member.getString("pmrl_code");
				String pmrl_ascore = member.getString("pmrl_accumulated_score");
				String pmrl_lvl = member.getString("pmrl_level_id");
				int mrl_lvl = Integer.parseInt(pmrl_lvl);
				
				String details = "รหัสผู้นำฟื้นฟู : "+pmrl_code+"\n"
									+"ตำแหน่ง : "+mrl_pos[mrl_lvl]+"\n"
									+"คะแนนรวม : "+pmrl_ascore;
				
				detail_array.add(details);
				
				//picture 
				String person_pic = member.getString("picture");
				String picUri = "drawable://"+person_pic;
				picture_array.add(picUri);
				
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}

		// init CardView
		mCardView = (CardUI) findViewById(R.id.cardsview);
		mCardView.setSwipeable(false);
		
		CardStack stackMember = new CardStack();
		stackMember.setTitle("รายชื่อผู้นำฟื้นฟู ระดับติดตัว");
		mCardView.addStack(stackMember);
		
		for (int i = 0; i < fullname_array.size(); i++) {
			
			mCardView.addCard(new MyImageCard(fullname_array.get(i),detail_array.get(i), (R.drawable.noprofile_img)));
			
		}
		
	}

}
