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

public class OffersAdapter extends ArrayAdapter<Offers>{

	ArrayList<Offers> offerList;
	LayoutInflater vi;
	int Resource;
	ViewHolder holder;

	public OffersAdapter(Context context, int resource, ArrayList<Offers> objects) {
		super(context, resource, objects);
		vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Resource = resource;
		offerList = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// convert view = design
		View v = convertView;
		if (v == null) {
			holder = new ViewHolder();
			v = vi.inflate(Resource, null);
			holder.imageview = (ImageView) v.findViewById(R.id.ivImageLogo);
			holder.offerJewelleryType = (TextView) v.findViewById(R.id.jewellery_type);
			holder.offerDisscount = (TextView) v.findViewById(R.id.offer_discount);
			holder.offerMakingChargeDisccount = (TextView) v.findViewById(R.id.making_charge_discount);
			holder.offerOnPurity = (TextView) v.findViewById(R.id.offer_on_purity);
			holder.offerType = (TextView) v.findViewById(R.id.offer_type);
			holder.offerValidity = (TextView) v.findViewById(R.id.offer_validity);
			holder.offerWastageCharge = (TextView) v.findViewById(R.id.wastage_charge);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		holder.imageview.setImageResource(R.drawable.ic_launcher);
		new DownloadImageTask(holder.imageview).execute(offerList.get(position).getOffer_image());
		holder.offerJewelleryType.setText(offerList.get(position).getJewellery_type());
		holder.offerType.setText("Offertype:"+offerList.get(position).getOffer_type());
		holder.offerDisscount.setText("Offerdiscount: " + offerList.get(position).getOffer_discount());
		holder.offerMakingChargeDisccount.setText("Makingchargediscount:"+offerList.get(position).getMaking_charge_discount());
		holder.offerWastageCharge.setText("Wastagecharge: " + offerList.get(position).getWastage_charge());
		holder.offerOnPurity.setText("Offeronpurity: " + offerList.get(position).getOffer_on_purity());
		holder.offerValidity.setText("Offervalidity: " + offerList.get(position).getOffer_validity());
		return v;
	}

	static class ViewHolder {
		public ImageView imageview;
		public TextView offerJewelleryType;
		public TextView offerType;
		public TextView offerDisscount;
		public TextView offerMakingChargeDisccount;
		public TextView offerWastageCharge;
		public TextView offerOnPurity;
		public TextView offerValidity;
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
