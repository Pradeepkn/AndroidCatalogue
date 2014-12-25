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

public class HomeScreenAdapter extends ArrayAdapter<HomeScreen>{

	ArrayList<HomeScreen> homeList;
	LayoutInflater inflater;
	int Resource;
	ViewHolder holder;

	public HomeScreenAdapter(Context context, int resource, ArrayList<HomeScreen> objects){
		super(context, resource, objects);
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Resource = resource;
		homeList = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;
		if(v == null){
			holder = new ViewHolder();
			v = inflater.inflate(Resource, null);
			holder.imageView1 = (ImageView) v.findViewById(R.id.ivImageLogo1);
			v.setTag(holder);
		}else{
			holder = (ViewHolder) v.getTag();
		}
		holder.imageView1.setImageResource(R.drawable.ic_launcher);
		new DownloadImageTask1(holder.imageView1).execute(homeList.get(position).getUri());
		return v;
	}

	static class ViewHolder{

		public ImageView imageView1;
	}

	private class DownloadImageTask1 extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public DownloadImageTask1(ImageView bmImage) {
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
