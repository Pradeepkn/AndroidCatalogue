package com.example.goldproject.fragments;

import java.util.ArrayList;

import com.example.goldproject.R;
import com.example.goldproject.jewellerymodels.GoldItems;
import com.example.goldproject.jewellerymodels.GoldProducts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class GoldApiFirstFragment extends Fragment {

	private Activity mGoldApi;

	private ArrayList<GoldItems> goldItemsCategory;

	Context context;

	//get list of goldproduct types
	private ArrayList<GoldProducts> fFragGoldProducts = new ArrayList<GoldProducts>();

//	private String url = "http://brinvents.com/jewellery/api/ListOfProducts/retrive.json?type=Gold";

	private ViewFlipper goldFlipper;

	private TextView womenTextViewRow;

	private TextView menTextViewRow;

	private ImageView womenItemsLeftFlip;

	private ImageView menItemsLeftFlip;

	private TextView womenGoldItemIdView0;

	private TextView womenGoldItemIdView1;

	private TextView womenGoldItemIdView2;

	private TextView womenGoldItemIdView3;

	private TextView womenGoldItemIdView4;

	private TextView womenGoldItemIdView5;

	private TextView womenGoldItemIdView6;

	private TextView womenGoldItemIdView7;

	private TextView womenGoldItemIdView8;

	private TextView womenGoldItemIdView9;

	private TextView womenGoldItemIdView10;

	private TextView womenGoldItemIdView11;

	private TextView menGoldItemIdView0;

	private TextView menGoldItemIdView1;

	private TextView menGoldItemIdView2;

	private ImageView golditemsCategoryBackImage;

	private TextView golditemsCategoryBackText;

	private ImageView goldItemsDetailsBackImage;

	private TextView goldItemsDetailsBackText;

	private Animation inFromRightAnimation() {

		Animation inFromRight = new TranslateAnimation(

				Animation.RELATIVE_TO_PARENT,  +1.0f, Animation.RELATIVE_TO_PARENT,  0.0f,

				Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
				);
		inFromRight.setDuration(500);

		inFromRight.setInterpolator(new AccelerateInterpolator());

		return inFromRight;
	}

	private Animation inFromLeftAnimation() {

		Animation inFromLeft = new TranslateAnimation(

				Animation.RELATIVE_TO_PARENT,  -1.0f, Animation.RELATIVE_TO_PARENT,  0.0f,

				Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
				);
		inFromLeft.setDuration(500);

		inFromLeft.setInterpolator(new AccelerateInterpolator());

		return inFromLeft;
	}

	@Override
	public void onAttach(Activity activity) {

		mGoldApi = activity;

		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		System.out.println("gold API on create() started.......................");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.gold_fragment_viewflipper, container, false);

		initSignUpComponenets(view);

		return view;
	}

	private void initSignUpComponenets(View view) {

		goldFlipper = (ViewFlipper) view.findViewById(R.id.goldViewFlipperFrag1);

		womenTextViewRow = (TextView) view.findViewById(R.id.GoldWomenItemsId);

		menTextViewRow = (TextView) view.findViewById(R.id.GoldMenId);

		womenItemsLeftFlip = (ImageView) view.findViewById(R.id.womenItemsLeftFlip);

		menItemsLeftFlip = (ImageView) view.findViewById(R.id.menItemsLeftFlip);

		womenGoldItemIdView0 = (TextView) view.findViewById(R.id.womenGoldItemId0);

		womenGoldItemIdView1 = (TextView) view.findViewById(R.id.womenGoldItemId1);

		womenGoldItemIdView2 = (TextView) view.findViewById(R.id.womenGoldItemId2);

		womenGoldItemIdView3 = (TextView) view.findViewById(R.id.womenGoldItemId3);

		womenGoldItemIdView4 = (TextView) view.findViewById(R.id.womenGoldItemId4);

		womenGoldItemIdView5 = (TextView) view.findViewById(R.id.womenGoldItemId5);

		womenGoldItemIdView6 = (TextView) view.findViewById(R.id.womenGoldItemId6);

		womenGoldItemIdView7 = (TextView) view.findViewById(R.id.womenGoldItemId7);

		womenGoldItemIdView8 = (TextView) view.findViewById(R.id.womenGoldItemId8);

		womenGoldItemIdView9 = (TextView) view.findViewById(R.id.womenGoldItemId9);

		womenGoldItemIdView10 = (TextView) view.findViewById(R.id.womenGoldItemId10);

		womenGoldItemIdView11 = (TextView) view.findViewById(R.id.womenGoldItemId11);

		menGoldItemIdView0 = (TextView) view.findViewById(R.id.menGoldItemId0);

		menGoldItemIdView1 = (TextView) view.findViewById(R.id.menGoldItemId1);

		menGoldItemIdView2 = (TextView) view.findViewById(R.id.menGoldItemId2);

		golditemsCategoryBackImage = (ImageView) view.findViewById(R.id.goldItemsCategoryBackImageView);

		golditemsCategoryBackText = (TextView) view.findViewById(R.id.goldItemsCategoryBackTextView);

		goldItemsDetailsBackImage = (ImageView) view.findViewById(R.id.goldItemsDetailsBackImageView);

		goldItemsDetailsBackText = (TextView) view.findViewById(R.id.goldItemsDetailsBackTextView);

		womenTextViewRow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// Send an Intent with an action named "my-event". 
				Intent intent = new Intent("my-event");

				intent.putExtra("womenFirstFrag", "Women");

				//Helper to register for and send broadcasts of Intents to local objects within your process.
				LocalBroadcastManager mgr = LocalBroadcastManager.getInstance(getActivity());

				mgr.sendBroadcast(intent);
			}
		});

		womenItemsLeftFlip.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				goldFlipper.setInAnimation(inFromRightAnimation());

				goldFlipper.showNext();
			}
		});

		menTextViewRow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// Send an Intent with an action named "my-event". 
				Intent intent = new Intent("my-event");

				intent.putExtra("menFirstFrag", "Men");

				//Helper to register for and send broadcasts of Intents to local objects within your process.
				LocalBroadcastManager mgr = LocalBroadcastManager.getInstance(getActivity());

				mgr.sendBroadcast(intent);
			}
		});

		menItemsLeftFlip.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				goldFlipper.setInAnimation(inFromRightAnimation());

				goldFlipper.showNext();
			}
		});

		womenGoldItemIdView0.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				/*goldFlipper.setInAnimation(inFromRightAnimation());

				goldFlipper.showNext();*/

				Intent intent = new Intent("my-event");

				intent.putExtra("womenGoldItems0", "Bangles");

				LocalBroadcastManager mgr = LocalBroadcastManager.getInstance(getActivity());

				mgr.sendBroadcast(intent);
			}
		});

		womenGoldItemIdView1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				/*goldFlipper.setInAnimation(inFromRightAnimation());

				goldFlipper.showNext();*/

				Intent intent = new Intent("my-event");

				intent.putExtra("womenGoldItems1", "Ear Ring");

				LocalBroadcastManager mgr = LocalBroadcastManager.getInstance(getActivity());

				mgr.sendBroadcast(intent);
			}
		});

		womenGoldItemIdView2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				/*goldFlipper.setInAnimation(inFromRightAnimation());

				goldFlipper.showNext();*/

				Intent intent = new Intent("my-event");

				intent.putExtra("womenGoldItems2", "Necklace");

				LocalBroadcastManager mgr = LocalBroadcastManager.getInstance(getActivity());

				mgr.sendBroadcast(intent);
			}
		});

		womenGoldItemIdView3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				/*goldFlipper.setInAnimation(inFromRightAnimation());

				goldFlipper.showNext();*/

				Intent intent = new Intent("my-event");

				intent.putExtra("womenGoldItems3", "Black Beeds Chain");

				LocalBroadcastManager mgr = LocalBroadcastManager.getInstance(getActivity());

				mgr.sendBroadcast(intent);
			}
		});

		womenGoldItemIdView4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				/*goldFlipper.setInAnimation(inFromRightAnimation());

				goldFlipper.showNext();*/

				Intent intent = new Intent("my-event");

				intent.putExtra("womenGoldItems4", "Tanmania");

				LocalBroadcastManager mgr = LocalBroadcastManager.getInstance(getActivity());

				mgr.sendBroadcast(intent);
			}
		});

		womenGoldItemIdView5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				/*goldFlipper.setInAnimation(inFromRightAnimation());

				goldFlipper.showNext();*/

				Intent intent = new Intent("my-event");

				intent.putExtra("womenGoldItems5", "Ring");

				LocalBroadcastManager mgr = LocalBroadcastManager.getInstance(getActivity());

				mgr.sendBroadcast(intent);
			}
		});

		womenGoldItemIdView6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				/*goldFlipper.setInAnimation(inFromRightAnimation());

				goldFlipper.showNext();*/

				Intent intent = new Intent("my-event");

				intent.putExtra("womenGoldItems6", "Pendant");

				LocalBroadcastManager mgr = LocalBroadcastManager.getInstance(getActivity());

				mgr.sendBroadcast(intent);
			}
		});

		womenGoldItemIdView7.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				/*goldFlipper.setInAnimation(inFromRightAnimation());

				goldFlipper.showNext();*/

				Intent intent = new Intent("my-event");

				intent.putExtra("womenGoldItems7", "Chain");

				LocalBroadcastManager mgr = LocalBroadcastManager.getInstance(getActivity());

				mgr.sendBroadcast(intent);
			}
		});

		womenGoldItemIdView8.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				/*goldFlipper.setInAnimation(inFromRightAnimation());

				goldFlipper.showNext();*/

				Intent intent = new Intent("my-event");

				intent.putExtra("womenGoldItems8", "Mangalasutra");

				LocalBroadcastManager mgr = LocalBroadcastManager.getInstance(getActivity());

				mgr.sendBroadcast(intent);
			}
		});

		womenGoldItemIdView9.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				/*goldFlipper.setInAnimation(inFromRightAnimation());

				goldFlipper.showNext();*/

				Intent intent = new Intent("my-event");

				intent.putExtra("womenGoldItems9", "Neckalce Set");

				LocalBroadcastManager mgr = LocalBroadcastManager.getInstance(getActivity());

				mgr.sendBroadcast(intent);
			}
		});

		womenGoldItemIdView10.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				/*goldFlipper.setInAnimation(inFromRightAnimation());

				goldFlipper.showNext();*/

				Intent intent = new Intent("my-event");

				intent.putExtra("womenGoldItems10", "Bracelet");

				LocalBroadcastManager mgr = LocalBroadcastManager.getInstance(getActivity());

				mgr.sendBroadcast(intent);
			}
		});

		womenGoldItemIdView11.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				/*goldFlipper.setInAnimation(inFromRightAnimation());

				goldFlipper.showNext();*/

				Intent intent = new Intent("my-event");

				intent.putExtra("womenGoldItems11", "Maang Tikka");

				LocalBroadcastManager mgr = LocalBroadcastManager.getInstance(getActivity());

				mgr.sendBroadcast(intent);
			}
		});

		menGoldItemIdView0.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				/*goldFlipper.setInAnimation(inFromRightAnimation());

				goldFlipper.showNext();*/

				Intent intent = new Intent("my-event");

				intent.putExtra("menGoldItems0", "Ring");

				LocalBroadcastManager mgr = LocalBroadcastManager.getInstance(getActivity());

				mgr.sendBroadcast(intent);
			}
		});

		menGoldItemIdView1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				/*goldFlipper.setInAnimation(inFromRightAnimation());

				goldFlipper.showNext();*/

				Intent intent = new Intent("my-event");

				intent.putExtra("menGoldItems1", "Chain");

				LocalBroadcastManager mgr = LocalBroadcastManager.getInstance(getActivity());

				mgr.sendBroadcast(intent);
			}
		});

		menGoldItemIdView2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				/*goldFlipper.setInAnimation(inFromRightAnimation());

				goldFlipper.showNext();*/

				Intent intent = new Intent("my-event");

				intent.putExtra("menGoldItems2", "Bracelet");

				LocalBroadcastManager mgr = LocalBroadcastManager.getInstance(getActivity());

				mgr.sendBroadcast(intent);
			}
		});

		golditemsCategoryBackImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				goldFlipper.setInAnimation(inFromLeftAnimation());

				goldFlipper.showPrevious();
			}
		});

		golditemsCategoryBackText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				goldFlipper.setInAnimation(inFromLeftAnimation());

				goldFlipper.showPrevious();
			}
		});

		goldItemsDetailsBackImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				goldFlipper.setInAnimation(inFromLeftAnimation());

				goldFlipper.showPrevious();
			}
		});

		goldItemsDetailsBackText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				goldFlipper.setInAnimation(inFromLeftAnimation());

				goldFlipper.showPrevious();
			}
		});
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);

		ArrayList<GoldProducts> gProducts = new ArrayList<GoldProducts>();

		for (int i = 0; i < fFragGoldProducts.size(); i++) {

			System.out.println("firstfrag goldproducts size--" +fFragGoldProducts.size());

			GoldProducts gProducts1 = fFragGoldProducts.get(i);

			ArrayList<GoldItems> gItems = gProducts1.items;

			if (gItems == null) {

				continue;
			}
			ArrayList<GoldItems> gItemsList = new ArrayList<GoldItems>();

			for (int j = 0; j < gItems.size(); j++) {

				System.out.println("gItems size--" +gItems.size());

				GoldItems gItems1 = gItems.get(j);

				String genderName = gItems1.getGender();

				System.out.println("gold each Items name--" +genderName);

				if (genderName.equals("Women") || genderName.equals("Men") || genderName.equals("Kids") || genderName.equals("Occasions")) {

					womenTextViewRow.setText(genderName);
					//map every goldItems in list
					gItemsList.add(gItems1);
				}else {
					menTextViewRow.setText(genderName);
				}
			}
			System.out.println("final gItemsList---" +gItemsList.size());

			if (gItemsList.size() > 0) {

				gProducts1.items = gItemsList;

				//map each goldProducts items in to a list
				gProducts.add(gProducts1);
			}
		}
		updateItemsView(gProducts);

		// Call Async task to get the gold items
//		new JsonGoldItemsTask().execute();
	}

	/*public class JsonGoldItemsTask extends AsyncTask <Void, Void, ArrayList<GoldProducts>> {

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
		}

		@Override
		protected ArrayList<GoldProducts> doInBackground(Void... args) {

			System.out.println("background process strated");

			ServiceHandler serviceClient = new ServiceHandler();

			String jsonResp = serviceClient.makeServiceCall(url, ServiceHandler.GET);

			System.out.println("response from url--" + jsonResp);

			if(jsonResp != null){

				try {
					JSONObject json = new JSONObject(jsonResp);

					System.out.println("response from the server-----------"+json);

					JSONObject result = json.getJSONObject("Result");

					int errorCode = result.getInt("errorCode");

					System.out.println("ERROR_CODE->"+errorCode);

					String errorMessage = result.getString("errorMessage");

					System.out.println("ERROR_MESSAGE->"+errorMessage);

					int statusCode = result.getInt("statusCode");

					System.out.println("STATUS_CODE->"+statusCode);

					//jsonarray parse for listOfItems
					JSONArray jarray = result.getJSONArray("listOfItems");

					// create an array list for Gold product array
					ArrayList<GoldProducts> productList = new ArrayList<GoldProducts>();

					for (int i = 0; i < jarray.length(); i++) {

						JSONObject jsonObj = jarray.getJSONObject(i);

						//jsonarray parse for products
						JSONArray jarray1 = jsonObj.getJSONArray("products");

						for (int j = 0; j < jarray1.length(); j++) {

							JSONObject products  = jarray1.optJSONObject(j);

							if (products == null) {

								continue;
							}
							//jsonarray parse for items
							JSONArray itemJsonArray  = products.getJSONArray("items");

							goldItemsCategory = new ArrayList<GoldItems>();

							for (int k = 0; k < itemJsonArray.length(); k++) {

								final JSONObject itemObject = itemJsonArray.optJSONObject(k);

								if (itemObject == null) {

									continue;
								}
								GoldItems gItems = new GoldItems();

								gItems.gender = itemObject.optString("gender_name");

								goldItemsCategory.add(gItems);

								String productName = products.optString("PT");

								if ((TextUtils.isEmpty(productName) != true) && (goldItemsCategory.size() > 0)) {

									GoldProducts product = new GoldProducts();

									product.productName = productName;

									product.items = goldItemsCategory;

									productList.add(product);
								}
							}
						}
						return productList;
					}
				}catch (JSONException e) {

					e.printStackTrace();
				} 
			}else {
				Log.e("JSON data", "didn't receive the response from JSON url");
			}
			return null;
		}

		@Override
		protected void onPostExecute(ArrayList<GoldProducts> result) {

			super.onPostExecute(result);

			if (result == null) {

				return;
			}
		}
	}*/
	private void updateItemsView(ArrayList<GoldProducts> result) {

		ArrayList<GoldItems> items = new ArrayList<GoldItems>();

		for (int i = 0; i < result.size(); i++) {

			System.out.println("GoldProducts size--" + result.size());

			GoldProducts product = result.get(i);

			items = product.items;

			for (int j = 0; j < items.size(); j++) {

				GoldItems gItems2 = items.get(j);

				String genderName = gItems2.getGender();

				if (genderName.equals("Women")) {

					womenTextViewRow.setText(genderName);
				} else {
					menTextViewRow.setText(genderName);
				}
			}
		}
	}
}
