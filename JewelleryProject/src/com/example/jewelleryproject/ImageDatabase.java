package com.example.jewelleryproject;

import java.io.ByteArrayOutputStream;

import android.animation.ArgbEvaluator;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageDatabase extends Activity implements OnClickListener{

	private ImageView imageview=null;
	private Button btninsert=null;
	private Button btnretrive=null;
	private MyDataBase mdb=null;
	private SQLiteDatabase db=null;
	private Cursor c=null;
	private byte[] img=null;
	private static final String DATABASE_NAME = "ImageDb.db";
	public static final int DATABASE_VERSION = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_database);

		btninsert = (Button) findViewById(R.id.button1);
		btnretrive = (Button) findViewById(R.id.button2);
		imageview = (ImageView) findViewById(R.id.imageView1);
		btninsert.setOnClickListener(this);
		btnretrive.setOnClickListener(this);
		mdb = new MyDataBase(getApplicationContext(), DATABASE_NAME, null, DATABASE_VERSION);

		//Creates Bitmap objects from various sources, including files, streams, and byte-arrays.
		Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		b.compress(CompressFormat.PNG, 100, bos);
		img = bos.toByteArray();
		db = mdb.getWritableDatabase();
	}

	@Override
	public void onClick(View arg0) {

		if (btninsert==arg0) {
			ContentValues cv = new ContentValues();
			cv.put("image", img);
			db.insert("tableimage", null, cv);
			Toast.makeText(this, "inserted successfully", Toast.LENGTH_LONG).show();
		}
		else if(btnretrive==arg0)
		{
			String[] col={"image"};
			c=db.query("tableimage", col, null, null, null, null, null);

			if(c!=null){
				c.moveToFirst();
				do{
					img=c.getBlob(c.getColumnIndex("image"));
				}while(c.moveToNext());
			}
			Bitmap b1=BitmapFactory.decodeByteArray(img, 0, img.length);

			imageview.setImageBitmap(b1);
			Toast.makeText(this, "Retrive successfully", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_database, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
