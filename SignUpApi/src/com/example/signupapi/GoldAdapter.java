package com.example.signupapi;

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

public class GoldAdapter extends ArrayAdapter<Gold>{

	ArrayList<Gold> goldList;
	LayoutInflater inflater;
	int Resource;
	ViewHolder holder;

	public GoldAdapter(Context context, int resource, ArrayList<Gold> objects) {
		super(context, resource, objects);
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Resource = resource;
		goldList = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// convert view = design
		View v = convertView;
		if (v == null) {
			holder = new ViewHolder();
			v = inflater.inflate(Resource, null);
			holder.goldCT = (TextView) v.findViewById(R.id.CT);
			holder.goldPT = (TextView) v.findViewById(R.id.PT);
			holder.imageview = (ImageView) v.findViewById(R.id.ivImageLogo1);
			holder.jewelleryTypeName = (TextView) v.findViewById(R.id.jewellery_type_name);
			holder.goldName = (TextView) v.findViewById(R.id.name);
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
		new DownloadImageTask(holder.imageview).execute(goldList.get(position).getUri());
		holder.goldCT.setText(goldList.get(position).getCT());
		holder.goldPT.setText("PT:"+goldList.get(position).getPT());
		holder.goldName.setText("name: " + goldList.get(position).getName());
		holder.jewelleryTypeName.setText("jewellerytypename:"+goldList.get(position).getJewellery_type_name());
		holder.genderName.setText("gendername: " + goldList.get(position).getGender_name());
		holder.wearingStyleName.setText("wearingstylename: " + goldList.get(position).getWearing_style_name());
		holder.designTypeName.setText("designtypename: " + goldList.get(position).getDesign_type_name());
		holder.clarityName.setText("clarityname: " + goldList.get(position).getClarity_name());
		holder.colorName.setText("colorname: " + goldList.get(position).getColor_name());
		holder.ringSizeName.setText("ringsizename: " + goldList.get(position).getRing_size_name());
		holder.price.setText("price: " + goldList.get(position).getPrice());

		return v;
	}

	static class ViewHolder {
		public ImageView imageview;
		public TextView goldCT;
		public TextView goldPT;
		public TextView goldName;
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
