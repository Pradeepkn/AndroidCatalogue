package com.example.jewelleryproject;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

public class DownloadAndReadImage {

	Context context;
	String strURL;
	Bitmap bitmap=null;

	// pass image url and Pos for example i:
	DownloadAndReadImage(String url)
	{
		this.strURL=url;
	}
	public Bitmap getBitmapImage()
	{
		downloadBitmapImage();
		return readBitmapImage();
	}
	void downloadBitmapImage()
	{
		InputStream input;
		try {
			URL url = new URL (strURL);
			input = url.openStream();
			byte[] buffer = new byte[1500];
			OutputStream output = new FileOutputStream ("Environment.getExternalStorageDirectory().getPath()");
			try
			{    
				int bytesRead = 0;
				while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0)
				{
					output.write(buffer, 0, bytesRead);
				}
			}
			finally
			{
				output.close();
				buffer=null;
			}
		}
		catch(Exception e)
		{
			Toast.makeText(context,e.toString(), Toast.LENGTH_LONG).show();
		}
	}

	Bitmap readBitmapImage()
	{
		String imageInSD = "Environment.getExternalStorageDirectory().getPath()"+strURL;
		BitmapFactory.Options bOptions = new BitmapFactory.Options();
		bOptions.inTempStorage = new byte[16*1024];

		bitmap = BitmapFactory.decodeFile(imageInSD,bOptions);

		return bitmap;
	}
}
