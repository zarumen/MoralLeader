package com.rgtsoft.mrlapp.query;

import java.util.ArrayList;

import com.rgtsoft.mrlapp.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListMemberAdapter extends BaseAdapter{
	
	private ArrayList<String> name;
	private Context context;
	private ListViewHolder listViewHolder;
	
	public ListMemberAdapter(Context context, ArrayList<String> name) {
		
		this.context = context;
		this.name = name;
	}



	@Override
	public int getCount() {
		return this.name.size();
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
		
		if(convertView == null) {
		LayoutInflater mInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = mInflater.inflate(R.layout.layout_listview_item, parent, false);
		
		listViewHolder.name = (TextView) convertView.findViewById(R.id.mrl_name_listitem);
		convertView.setTag(listViewHolder);
		
		} else {
			
			listViewHolder = (ListViewHolder) convertView.getTag();
		}
		
		listViewHolder.name.setText(name.get(position));
		
		return convertView;
	}


	private class ListViewHolder {
		
		public TextView name;
		
	}
	
	

}
