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

public class PlatinumAdapter extends ArrayAdapter<Platinum>{

	ArrayList<Platinum> platinumList;
	LayoutInflater inflater;
	int Resource;
	ViewHolder holder;

	public PlatinumAdapter(Context context, int resource, ArrayList<Platinum> objects) {
		super(context, resource, objects);
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Resource = resource;
		platinumList = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;
		if (v == null) {
			holder = new ViewHolder();
			v = inflater.inflate(Resource, null);
			holder.platinumCT = (TextView) v.findViewById(R.id.CT);
			holder.platinumCT = (TextView) v.findViewById(R.id.PT);
			holder.imageview = (ImageView) v.findViewById(R.id.platinumlogo);
			holder.jewelleryTypeName = (TextView) v.findViewById(R.id.jewellery_type_name);
			holder.platinumName = (TextView) v.findViewById(R.id.name);
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
		new DownloadPlatinumImageTask(holder.imageview).execute(platinumList.get(position).getUri());
		holder.platinumCT.setText(platinumList.get(position).getCT());
		holder.platinumCT.setText("PT:"+platinumList.get(position).getPT());
		holder.platinumName.setText("name: " + platinumList.get(position).getName());
		holder.jewelleryTypeName.setText("jewellerytypename:"+platinumList.get(position).getJewellery_type_name());
		holder.genderName.setText("gendername: " + platinumList.get(position).getGender_name());
		holder.wearingStyleName.setText("wearingstylename: " + platinumList.get(position).getWearing_style_name());
		holder.designTypeName.setText("designtypename: " + platinumList.get(position).getDesign_type_name());
		holder.clarityName.setText("clarityname: " + platinumList.get(position).getClarity_name());
		holder.colorName.setText("colorname: " + platinumList.get(position).getColor_name());
		holder.ringSizeName.setText("ringsizename: " + platinumList.get(position).getRing_size_name());
		holder.price.setText("price: " + platinumList.get(position).getPrice());

		return v;
	}

	static class ViewHolder {
		public ImageView imageview;
		public TextView platinumCT;
		public TextView platinumPT;
		public TextView platinumName;
		public TextView jewelleryTypeName;
		public TextView genderName;
		public TextView wearingStyleName;
		public TextView designTypeName;
		public TextView clarityName;
		public TextView colorName;
		public TextView ringSizeName;
		public TextView price;
	}

	private class DownloadPlatinumImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public DownloadPlatinumImageTask(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		@Override
		protected Bitmap doInBackground(String... urls) {

			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			bmImage.setImageBitmap(result);
		}
	}
}
