package com.example.signupapi;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class DownloadTask extends AsyncTask<String, Void, Boolean>{

	ImageView imageview1;
	ImageView imageview2;
	ImageView imageview3;


	String url;
	Bitmap bm;

	public DownloadTask(ImageView imageview1, ImageView imageview2, ImageView imageview3) {
		this.imageview1 = imageview1;
		this.imageview2 = imageview2;
		this.imageview3 = imageview3;
	}

	@Override
	protected Boolean doInBackground(String... params) {
		url = params[0];
		bm = loadBitmap(url);
		return true;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		imageview1.setImageBitmap(bm);
		imageview2.setImageBitmap(bm);
		imageview3.setImageBitmap(bm);
	}

	public static Bitmap loadBitmap(String url) {
		try {
			URL newurl = new URL(url);
			Bitmap b = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
			return b;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}


