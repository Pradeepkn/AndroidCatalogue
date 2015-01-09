package com.example.jewelleryproject;

import java.io.InputStream;
import java.util.ArrayList;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DiamondAdapter extends ArrayAdapter<Diamond>{

	ArrayList<Diamond> dimondList;
	
	LayoutInflater inflater;
	
	int Resource;
	
	ViewHolder holder;

	public DiamondAdapter(Context context, int resource, ArrayList<Diamond> objects) {
		
		super(context, resource, objects);
		
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		Resource = resource;
		
		dimondList = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// convert view = design
		View v = convertView;
		
		if (v == null) {
			
			holder = new ViewHolder();
			
			v = inflater.inflate(Resource, null);
			
			holder.diamondCT = (TextView) v.findViewById(R.id.CT);
			
			holder.diamondPT = (TextView) v.findViewById(R.id.PT);
			
			holder.imageview = (ImageView) v.findViewById(R.id.diamondlogo);
			
			holder.jewelleryTypeName = (TextView) v.findViewById(R.id.jewellery_type_name);
			
			holder.diamondName = (TextView) v.findViewById(R.id.name);
			
			holder.genderName = (TextView) v.findViewById(R.id.gender_name);
			
			holder.wearingStyleName = (TextView) v.findViewById(R.id.wearing_style_name);
			
			holder.designTypeName = (TextView) v.findViewById(R.id.design_type_name);
			
			holder.colorName = (TextView) v.findViewById(R.id.color_name);
			
			holder.clarityName = (TextView) v.findViewById(R.id.clarity_name);
			
			holder.ringSizeName = (TextView) v.findViewById(R.id.ring_size_name);
			
			holder.price = (TextView) v.findViewById(R.id.price);

			v.setTag(holder);
		} else {
			
			holder = (ViewHolder) v.getTag();
		}
		holder.imageview.setImageResource(R.drawable.ic_launcher);
		
		new DownloadImageTask(holder.imageview).execute(dimondList.get(position).getUri());
		
		holder.diamondCT.setText(dimondList.get(position).getCT());
		
		holder.diamondPT.setText("PT:"+dimondList.get(position).getPT());
		
		holder.diamondName.setText("name: " + dimondList.get(position).getName());
		
		holder.jewelleryTypeName.setText("jewellerytypename:"+dimondList.get(position).getJewellery_type_name());
		
		holder.genderName.setText("gendername: " + dimondList.get(position).getGender_name());
		
		holder.wearingStyleName.setText("wearingstylename: " + dimondList.get(position).getWearing_style_name());
		
		holder.designTypeName.setText("designtypename: " + dimondList.get(position).getDesign_type_name());
		
		holder.clarityName.setText("clarityname: " + dimondList.get(position).getClarity_name());
		
		holder.colorName.setText("colorname: " + dimondList.get(position).getColor_name());
		
		holder.ringSizeName.setText("ringsizename: " + dimondList.get(position).getRing_size_name());
		
		holder.price.setText("price: " + dimondList.get(position).getPrice());

		return v;
	}

	static class ViewHolder {
		
		public ImageView imageview;
		
		public TextView diamondCT;
		
		public TextView diamondPT;
		
		public TextView diamondName;
		
		public TextView jewelleryTypeName;
		
		public TextView genderName;
		
		public TextView wearingStyleName;
		
		public TextView designTypeName;
		
		public TextView clarityName;
		
		public TextView colorName;
		
		public TextView ringSizeName;
		
		public TextView price;
	}

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		
		ImageView bmImage;

		public DownloadImageTask(ImageView bmImage) {
			
			this.bmImage = bmImage;
		}

		@Override
		protected Bitmap doInBackground(String... urls) {

			String urldisplay = urls[0];
			
			Bitmap bitmap = null;
			
			try {
				
				InputStream in = new java.net.URL(urldisplay).openStream();
				
				bitmap = BitmapFactory.decodeStream(in);
				
			} catch (Exception e) {
				
				Log.e("Error", e.getMessage());
				
				e.printStackTrace();
			}
			return bitmap;
		}
		protected void onPostExecute(Bitmap result) {
			
			holder.imageview.setImageBitmap(result);
		}
	}
}
