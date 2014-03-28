package com.rgtsoft.mrlapp.cardui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fima.cardsui.objects.RecyclableCard;
import com.rgtsoft.mrlapp.R;

public class MyImageCard extends RecyclableCard {
	

	public MyImageCard(String title,String desc, int image){
		super(title, desc, image);
	}

	@Override
	protected int getCardLayoutId() {
		return R.layout.card_picture;
	}

	@Override
	protected void applyTo(View convertView) {
		((TextView) convertView.findViewById(R.id.title_img)).setText(title);
		((TextView) convertView.findViewById(R.id.description_img))
		.setText(desc);
		((ImageView) convertView.findViewById(R.id.imageView1)).setImageResource(image);
	}

}
