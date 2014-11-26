package com.example.signupapi;

import java.util.ArrayList;
import java.util.HashMap;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

public class MySimpleAdapter extends SimpleAdapter{

	private Context mContext;
	public LayoutInflater inflater = null;

	public MySimpleAdapter(Context context, ArrayList<? extends HashMap<String, ?>> arraylist, int resource, String[] from, int[] to) {
		super(context, arraylist, resource, from, to);
		mContext = context;
		inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.row, null);

		HashMap<String, Object> map = (HashMap<String, Object>) getItem(position);

		new DownloadTask((ImageView) vi.findViewById(R.id.ivImage),(ImageView) vi.findViewById(R.id.ivImage2),(ImageView)vi.findViewById(R.id.ivImage3)).execute((String) map.get("TAG_URI"),(String)map.get("TAG_URI1"), (String)map.get("TAG_URI2"));
		return vi;
	}
}


