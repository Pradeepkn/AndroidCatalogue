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

public class SilverAdapter extends ArrayAdapter<Silver>{

	ArrayList<Silver> silverList;
	int Resource;
	LayoutInflater lInflater;
	ViewHolder viewHolder;

	public SilverAdapter(Context context, int resource, ArrayList<Silver> objects){

		super(context, resource, objects);
		lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Resource = resource;
		silverList = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View rowView = convertView;
		if (rowView == null) {
			viewHolder = new ViewHolder();

			rowView = lInflater.inflate(Resource, null);

			//obtaining generated silver object
			viewHolder.silverCT = (TextView) rowView.findViewById(R.id.CT);
			viewHolder.silverPT = (TextView) rowView.findViewById(R.id.PT);
			viewHolder.imageview = (ImageView) rowView.findViewById(R.id.silverlogo);
			viewHolder.jewelleryTypeName = (TextView) rowView.findViewById(R.id.jewellery_type_name);
			viewHolder.silverName = (TextView) rowView.findViewById(R.id.name);
			viewHolder.genderName = (TextView) rowView.findViewById(R.id.gender_name);
			viewHolder.wearingStyleName = (TextView) rowView.findViewById(R.id.wearing_style_name);
			viewHolder.designTypeName = (TextView) rowView.findViewById(R.id.design_type_name);
			viewHolder.colorName = (TextView) rowView.findViewById(R.id.color_name);
			viewHolder.clarityName = (TextView) rowView.findViewById(R.id.clarity_name);
			viewHolder.ringSizeName = (TextView) rowView.findViewById(R.id.ring_size_name);
			viewHolder.price = (TextView) rowView.findViewById(R.id.price);

			rowView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) rowView.getTag();
		}
		viewHolder.imageview.setImageResource(R.drawable.ic_launcher);
		new DownloadSilverImageTask(viewHolder.imageview).execute(silverList.get(position).getUri());
		viewHolder.silverCT.setText(silverList.get(position).getCT());
		viewHolder.silverPT.setText("PT:"+silverList.get(position).getPT());
		viewHolder.silverName.setText("name: " + silverList.get(position).getName());
		viewHolder.jewelleryTypeName.setText("jewellerytypename:"+silverList.get(position).getJewellery_type_name());
		viewHolder.genderName.setText("gendername: " + silverList.get(position).getGender_name());
		viewHolder.wearingStyleName.setText("wearingstylename: " + silverList.get(position).getWearing_style_name());
		viewHolder.designTypeName.setText("designtypename: " + silverList.get(position).getDesign_type_name());
		viewHolder.clarityName.setText("clarityname: " + silverList.get(position).getClarity_name());
		viewHolder.colorName.setText("colorname: " + silverList.get(position).getColor_name());
		viewHolder.ringSizeName.setText("ringsizename: " + silverList.get(position).getRing_size_name());
		viewHolder.price.setText("price: " + silverList.get(position).getPrice());
		return rowView;
	}

	static class ViewHolder {
		public ImageView imageview;
		public TextView silverCT;
		public TextView silverPT;
		public TextView silverName;
		public TextView jewelleryTypeName;
		public TextView genderName;
		public TextView wearingStyleName;
		public TextView designTypeName;
		public TextView clarityName;
		public TextView colorName;
		public TextView ringSizeName;
		public TextView price;
	}

	private class DownloadSilverImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public DownloadSilverImageTask(ImageView bmImage) {
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
			viewHolder.imageview.setImageBitmap(result);
		}
	}
}
