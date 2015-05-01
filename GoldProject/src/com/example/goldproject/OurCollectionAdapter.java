package com.example.goldproject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import at.technikum.mti.fancycoverflow.FancyCoverFlow;
import at.technikum.mti.fancycoverflow.FancyCoverFlowAdapter;

public class OurCollectionAdapter extends FancyCoverFlowAdapter {

	private ArrayList<String> mUsrls = new ArrayList<String>();

	private TextView bannerView;

	private Activity activity;

	//private String banner;

	public OurCollectionAdapter(Activity activity1) {

		super();

		activity = activity1;
	}

	@Override
	public int getCount() {

		return mUsrls.size();
	}

	public void setItems(ArrayList<String> usrls) {

		mUsrls.clear();

		mUsrls.addAll(usrls);

		notifyDataSetChanged();
	}

	@Override
	public Object getItem(int position) {

		return mUsrls.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getCoverFlowItem(int i, View reuseableView, ViewGroup viewGroup) {

		ImageView imageView = null;

		if (reuseableView != null) {

			imageView = (ImageView) reuseableView;

		} else {

			imageView = new ImageView(viewGroup.getContext());

			imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

			imageView.setLayoutParams(new FancyCoverFlow.LayoutParams(600, 200));
		}

		//        imageView.setImageResource(this.getItem(i));

		updateImageView(imageView, "banner " + i, mUsrls.get(i));

		/*imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				bannerView.setText(banner);
			}
		});
		 */
		return imageView;
	}

	private void updateImageView(ImageView bmImage, final String filename, String url) {

		File SDCardRoot = Environment.getExternalStorageDirectory().getAbsoluteFile();
		//create a new file, specifying the path, and the filename
		//which we want to save the file as.
		//      String filename="downloadedFile.png";  
		File file = new File(SDCardRoot,filename);

		if(file.exists()) {

			//Creates Bitmap objects from various sources, including files, streams, and byte-arrays.
//			BitmapFactory.Options options = new BitmapFactory.Options();
//
//			options.inPreferredConfig = Bitmap.Config.ARGB_8888;

			Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

			System.out.println("decoding file to bitmap image..." + myBitmap);

			bmImage.setImageBitmap(myBitmap);

			bannerView = (TextView) activity.findViewById(R.id.collTextView);

			bmImage.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					//Toast.makeText(activity, filename, Toast.LENGTH_LONG).show();

					bannerView.setText(filename);
				}
			});

		} else {

			new DownloadImageTask1(bmImage, filename).execute(url);

			/*bmImage.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					Toast.makeText(activity, filename, Toast.LENGTH_LONG).show();
				}
			});*/
		}
	}

	public class DownloadImageTask1 extends AsyncTask<String, Void, Bitmap> {

		ImageView bmImage;

		private String mFileName;

		public DownloadImageTask1(ImageView bmImage, String filename) {

			this.bmImage = bmImage;

			this.mFileName = filename;
		}

		@Override
		protected Bitmap doInBackground(String... urls) {

			String urldisplay = urls[0];

			Bitmap bitmap = null;

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

				//set the path where we want to save the file
				//in this case, going to save it on the root directory of the
				//sd card.
				//return the path to the root of the external storage(SDcard).
				File SDCardRoot = Environment.getExternalStorageDirectory().getAbsoluteFile();
				//create a new file, specifying the path, and the filename
				//which we want to save the file as.
				//              String filename="downloadedFile.png";  
				File file = new File(SDCardRoot,mFileName);

				if(file.createNewFile())
				{
					file.createNewFile();
				}
				SDCardRoot.mkdir();
				//this will be used to write the downloaded data into the file we created
				FileOutputStream fileOutput = new FileOutputStream(file);

				//this will be used in reading the data from the internet
				InputStream inputStream = urlConnection.getInputStream();

				//this is the total size of the file
				int totalSize = urlConnection.getContentLength();

				System.out.println("total size......"+totalSize);
				//variable to store total downloaded bytes
				int downloadedSize = 0;

				//create a buffer...
				byte[] buffer = new byte[1024];

				int bufferLength = 0; //used to store a temporary size of the buffer

				//now, read through the input buffer and write the contents to the file
				while ((bufferLength = inputStream.read(buffer)) > 0 ) {
					//add the data in the buffer to the file in the file output stream (the file on the sd card)
					fileOutput.write(buffer, 0, bufferLength);
					//add up the size so we know how much is downloaded
					downloadedSize += bufferLength;
					//                    Log.i("Progress:","downloadedSize:"+downloadedSize+"totalSize:"+ totalSize) ;
					//this is where you would do something to report the progress, like this maybe
					//updateProgress(downloadedSize, totalSize);
				}
				//close the output stream when done
				fileOutput.close();

				InputStream in = new java.net.URL(urldisplay).openStream();

				//------>error occured in executing doinbackground()<--------
				//Decode an input stream into a bitmap.
				bitmap = BitmapFactory.decodeStream(in);

				//catch some possible errors...
			} catch (MalformedURLException e) {

				e.printStackTrace();

			} catch (IOException e) {

				e.printStackTrace();
			}
			return bitmap;
		}
		// Sets the Bitmap returned by doInBackground
		@Override
		protected void onPostExecute(Bitmap result) {

			bmImage.setImageBitmap(result);
		}
	}
}
