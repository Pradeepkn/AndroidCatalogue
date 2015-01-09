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

public class collectionsAdapter extends ArrayAdapter<Collections>{

	ArrayList<Collections> collectionsList;

	LayoutInflater inflater;

	ViewHolder viewHolder;

	int Resource;

	public collectionsAdapter(Context context, int resource, ArrayList<Collections> object) {

		super(context, resource, object);

		Resource = resource;

		collectionsList = object;

		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View rowView = convertView;

		if(rowView == null){

			viewHolder = new ViewHolder();

			rowView = inflater.inflate(Resource, null);

			viewHolder.imageView = (ImageView) rowView.findViewById(R.id.collLogo);

			viewHolder.item_type = (TextView) rowView.findViewById(R.id.item_type);

			viewHolder.purity = (TextView) rowView.findViewById(R.id.purity);

			viewHolder.price = (TextView) rowView.findViewById(R.id.price);

			viewHolder.occassion = (TextView) rowView.findViewById(R.id.occassion);

			viewHolder.weight = (TextView) rowView.findViewById(R.id.weight);

			viewHolder.making_charge = (TextView) rowView.findViewById(R.id.making_charge);

			viewHolder.wastage = (TextView) rowView.findViewById(R.id.wastage);

			viewHolder.model = (TextView) rowView.findViewById(R.id.model);

			rowView.setTag(viewHolder);
		} else{
			viewHolder = (ViewHolder) rowView.getTag();
		}

		viewHolder.imageView.setImageResource(R.drawable.ic_launcher);

		new DownloadCollImageTask(viewHolder.imageView).execute(collectionsList.get(position).getImage_path());

		viewHolder.item_type.setText("Itemtype"+collectionsList.get(position).getItem_type());

		viewHolder.purity.setText("Purity"+collectionsList.get(position).getPurity());

		viewHolder.price.setText("Price"+collectionsList.get(position).getPrice());

		viewHolder.occassion.setText("Occassion"+collectionsList.get(position).getOccassion());

		viewHolder.weight.setText("Weight"+collectionsList.get(position).getWeight());

		viewHolder.making_charge.setText("Making_charge"+collectionsList.get(position).getMaking_charge());

		viewHolder.wastage.setText("Wastage"+collectionsList.get(position).getWastage());

		viewHolder.model.setText("Model"+collectionsList.get(position).getModel());

		return rowView;
	}

	static class ViewHolder{

		public ImageView imageView;

		public TextView item_type;

		public TextView purity;

		public TextView price;

		public TextView occassion;

		public TextView weight;

		public TextView making_charge;

		public TextView wastage;

		public TextView model;
	}

	private class DownloadCollImageTask extends AsyncTask<String, Void, Bitmap>{

		ImageView bmImage;

		public DownloadCollImageTask(ImageView bmImage){

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
