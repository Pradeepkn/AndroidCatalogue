package com.example.goldproject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.example.goldproject.jewellerymodels.DiamondItems;
import com.example.goldproject.util.cache.ImageFetcher;
import dev.dworks.libs.astickyheader.ui.SquareImageView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class DiamondAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	
	private Context context;

	private ArrayList<DiamondItems> mItems = new ArrayList<DiamondItems>();

	private ImageFetcher imageFetcher;
	
	private Activity dActivity;

	public DiamondAdapter(Context context) {

		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void setItems(ArrayList<DiamondItems> items) {

		mItems.clear();

		if(items != null) {

			mItems.addAll(items);
		}

		notifyDataSetChanged();
	}

	@Override
	public int getCount() {

		return mItems.size();
	}

	@Override
	public Object getItem(int position) {

		return position;
	}

	@Override
	public long getItemId(int position) {

		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final DiamondItems items = mItems.get(position);

		SquareImageView image;

		//View v = convertView;

		if (convertView == null) {

			convertView = mInflater.inflate(R.layout.diamond_row, parent, false);
		}

		image = ViewHolder.get(convertView, R.id.diamondImageLogo1);

		image.setImageResource(R.drawable.ic_launcher);
		//holder.imageview.setImageResource(R.drawable.ic_launcher);
		
		//image.setOnClickListener(new OnDiamondImageClickListner(position));

		System.out.println("position----"+position + " URL " + items.url);

		updateImageView(image, "goldfileName_" + position, items.url);

		return convertView;
	}
	
	/*public class OnDiamondImageClickListner implements OnClickListener {

		int _position;

		// constructor
		public OnDiamondImageClickListner(int position) {

			this._position = position;
		}

		@Override
		public void onClick(View v) {
			// on selecting grid view image
			// launch full screen activity
			Intent i = new Intent(context, SingleClickImage.class);

			i.putExtra("position",_position);

			dActivity.startActivity(i);
		}
	}*/

	public static  class ViewHolder {
		@SuppressWarnings("unchecked")
		public static <T extends View> T get(View view, int id) {
			SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
			if (viewHolder == null) {
				viewHolder = new SparseArray<View>();
				view.setTag(viewHolder);
			}
			View childView = viewHolder.get(id);
			if (childView == null) {
				childView = view.findViewById(id);
				viewHolder.put(id, childView);
			}
			return (T) childView;
		}
	}

	public void updateImageView(ImageView bmImage, String filename, String url) {

		try {
			File SDCardRoot = Environment.getExternalStorageDirectory().getAbsoluteFile();
			//create a new file, specifying the path, and the filename
			//which we want to save the file as.
			//      String filename="downloadedFile.png";  
			File file = new File(SDCardRoot,filename);

			if(file.exists()) {

				imageFetcher.loadImage(file.getAbsoluteFile(), bmImage);

			} else {

				new DownloadGoldImageTask1(bmImage, filename).execute(url);
			}
		} catch (Exception e) {

			new DownloadGoldImageTask1(bmImage, filename).execute(url);
		}
	}

	public class DownloadGoldImageTask1 extends AsyncTask<String, Void, String> {

		ImageView bmImage;

		private String mFileName;

		public DownloadGoldImageTask1(ImageView bmImage, String filename) {

			this.bmImage = bmImage;

			this.mFileName = filename;
		}

		@Override
		protected String doInBackground(String... urls) {

			String urldisplay = urls[0];

			Bitmap bitmap = null;

			File SDCardRoot = Environment.getExternalStorageDirectory().getAbsoluteFile();

			System.out.println("SDCardRoot : " + SDCardRoot + " mFileName : " + mFileName);

			File file = new File(SDCardRoot, mFileName);

			try {

				file.createNewFile();

			} catch (IOException e1) {

				e1.printStackTrace();
			}

			try {

				//set the download URL, a url that points to a file on the internet
				//this is the file to be downloaded
				URL url = new URL(urls[0]);

				System.out.println("url..."+url);

				//create the new connection
				HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

				//set up some things on the connection
				urlConnection.setRequestMethod("GET");

				urlConnection.setDoOutput(true);

				//and connect!
				urlConnection.connect();

				InputStream in = new java.net.URL(urldisplay).openStream();

				FileOutputStream os = new FileOutputStream(file);

				int len;

				byte[] buffer = new byte[4 * 1024];

				while ((len = in.read(buffer)) != -1) {

					os.write(buffer, 0, len);
				}

				os.flush();

				try {
					in.close();

					os.close();
				} catch (IOException ex) {
					// ex.printStackTrace();
				}

				bitmap = BitmapFactory.decodeStream(in);
				//catch some possible errors...

			} catch (MalformedURLException e) {

				e.printStackTrace();

			} catch (IOException e) {

				e.printStackTrace();
			}
			return file.getAbsolutePath();
		}
		// Sets the Bitmap returned by doInBackground
		@Override
		protected void onPostExecute(String result) {

			imageFetcher.loadImage(result, bmImage);
		}
	}
}
