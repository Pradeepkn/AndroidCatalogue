package com.example.jewelleryproject;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateOffersApi extends ActionBarActivity implements OnClickListener{

	private EditText jewelleryType, offerType, offerDiscount, makingChargeDiscount, wastageCharge, offerOnPurity, offerValidity, offerImage;
	private Button updateOffer;

	String responsetext = null;

	//progress dialog
	ProgressDialog pDialog;

	//JSON Parser class
	//HandleJSON handleJson = new HandleJSON();

	//url
	//private static final String url = "http://brinvents.com/jewel/Apis/post_offers.php";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		System.out.println("on create() started.......................");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_offers_api);
		jewelleryType = (EditText) findViewById(R.id.jewellerytypetextview);
		offerType = (EditText) findViewById(R.id.offertypetextview);
		offerDiscount = (EditText) findViewById(R.id.offerdiscounttextview);
		makingChargeDiscount = (EditText) findViewById(R.id.makingchargediscounttextview);
		wastageCharge = (EditText) findViewById(R.id.wastagechargetextview);
		offerOnPurity = (EditText) findViewById(R.id.offeronpuritytextview);
		offerValidity = (EditText) findViewById(R.id.offervaliditytextview);
		offerImage = (EditText) findViewById(R.id.offerimagetextview);

		updateOffer = (Button) findViewById(R.id.displayoffersbutton);
		updateOffer.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		System.out.println("when updatebutton is clicked....");
		new UpdateOffers().execute("http://brinvents.com/jewel/Apis/post_offers.php");
	}

	class UpdateOffers extends AsyncTask<String, String, String>{

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		boolean failure = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(UpdateOffersApi.this);
			pDialog.setMessage("Updating Offers...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... urls) {

			//getting values from edittext
			String jewellerytype = jewelleryType.getText().toString();
			String offertype = offerType.getText().toString();
			String offerdiscount = offerDiscount.getText().toString();
			String makingchargediscount = makingChargeDiscount.getText().toString();
			String wastagecharge = wastageCharge.getText().toString();
			String offeronpurity = offerOnPurity.getText().toString();
			String offervalidity = offerValidity.getText().toString();
			String offerimage = offerImage.getText().toString();

			try {
				//Building Parameters
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("jewellery_type", jewellerytype);
				jsonObj.put("offer_type", offertype);
				jsonObj.put("offer_discount", offerdiscount);
				jsonObj.put("making_charge_discount", makingchargediscount);
				jsonObj.put("wastage_charge", wastagecharge);
				jsonObj.put("offer_on_purity", offeronpurity);
				jsonObj.put("offer_validity", offervalidity);
				jsonObj.put("offer_image", offerimage);

				System.out.println(jsonObj+"===========");

				//Request method is POST
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPostReq = new HttpPost(urls[0]);

				//Create a String entity. String entity is appended to the url in a format that is required in HTTP POST.
				StringEntity se = new StringEntity(jsonObj.toString());

				se.setContentType("application/json;charset=UTF-8");
				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=UTF-8"));

				//Set entitiy in post request.
				httpPostReq.setEntity(se);

				//Execute POST request.
				HttpResponse httpResponse = httpClient.execute(httpPostReq);

				//To receive the response from the Server after HTTP POST execution. Write in trycatch block.

				try {
					responsetext = EntityUtils.toString(httpResponse.getEntity());
				}catch (ParseException e) {
					e.printStackTrace();
					Log.i("Parse Exception", e + "response got");
				}

				System.out.println(responsetext+"response from server-------------");

				Log.d("request!", "starting");

				//Get the response string into a new jSON object and get values from it.
				JSONObject json = new JSONObject(responsetext);
				System.out.println(json+"response from json object...............");

				JSONObject result = json.getJSONObject("Result");
				int ERRORCODE = result.getInt("errorCode");
				System.out.println("errorCode->"+ERRORCODE);
				String ERROR_MESSAGE = result.getString("errorMessage");
				System.out.println("errorMessage->"+ERROR_MESSAGE);
				int STATUS_CODE = result.getInt("statusCode");
				System.out.println("statusCode"+STATUS_CODE);

				//Success condition
				if(ERRORCODE == 0){
					System.out.println("updated sucuessfully..."+json.toString());
					Intent intent = new Intent(UpdateOffersApi.this, UpdateOffersSuccess.class);
					finish();
					startActivity(intent);
				}else{
					System.out.println("update unsucuessfull..."+json.getString(ERROR_MESSAGE));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product deleted
			pDialog.dismiss();
			if (file_url != null){
				Toast.makeText(UpdateOffersApi.this, file_url, Toast.LENGTH_LONG).show();
			}
		}
	} 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.update_offers_api, menu);
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
