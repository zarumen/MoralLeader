package com.rgtsoft.mrlapp.query;

import java.util.ArrayList;

import com.rgtsoft.mrlapp.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridMenuAdapter extends BaseAdapter{
	
	//Attribute
	
	private Context context;
	private ArrayList<Drawable> icons;
	private GridViewHolder gridViewHolder;
	private ArrayList<String> texts;
	
	
	public GridMenuAdapter (Context context, ArrayList<Drawable> icons, ArrayList<String> texts) {
		
		this.context = context;
		this.icons = icons;
		this.texts = texts;
		
	}

	@Override
	public int getCount() {
		
		return this.icons.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		//Create
		if ( convertView == null) {
			
			
			LayoutInflater mInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.layout_gridview_item, parent, false);
			
			gridViewHolder = new GridViewHolder();
			gridViewHolder.image = (ImageView) convertView.findViewById(R.id.icon_image);
			gridViewHolder.text = (TextView) convertView.findViewById(R.id.icon_text);
			convertView.setTag(gridViewHolder);
			
			
		} else {
			
			gridViewHolder = (GridViewHolder) convertView.getTag();
			
		}
		//Assign
		
		gridViewHolder.image.setImageDrawable(icons.get(position));
		gridViewHolder.text.setText(texts.get(position));
		
		return convertView;
	}
	
	private class GridViewHolder {
		
		public ImageView image;
		public TextView text;
		
	}

}
