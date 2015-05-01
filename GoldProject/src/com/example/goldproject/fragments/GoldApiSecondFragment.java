package com.example.goldproject.fragments;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goldproject.GoldFullScreenImage;
import com.example.goldproject.R;
import com.example.goldproject.jewellerymodels.GoldItems;
import com.example.goldproject.jewellerymodels.GoldProducts;
import com.example.goldproject.util.cache.ImageFetcher;
import dev.dworks.libs.astickyheader.SimpleSectionedGridAdapter;
import dev.dworks.libs.astickyheader.SimpleSectionedGridAdapter.Section;
import dev.dworks.libs.astickyheader.ui.SquareImageView;

public class GoldApiSecondFragment extends Fragment {

	private GoldAdapter gAdapter;

	private GridView goldGridView;

	public Activity mGoldApi2;

	public Context context;

	private LayoutInflater mInflater;

	// A view that displays one child at a time and lets the user pick among
	// them. The items in the Spinner come from the Adapter associated with this
	// view.
	private Spinner priceSpinner;

	private Spinner puritySpinner;

	private ArrayList<GoldItems> items;

	int pos;

	private	TextView imagePrice;

	private SearchView goldItemSearchView;

	ArrayList<String> priceRangesList = new ArrayList<String>();

	ArrayList<String> purityListItems = new ArrayList<String>();

	ArrayList<String> weightRangesList = new ArrayList<String>();

	// A simple subclass of {@link ImageResizer} that fetches and resizes images
	// fetched from a URL.
	private ImageFetcher imageFetcher;

	protected static final String TAG = "GoldApiFragment2";

	private SimpleSectionedGridAdapter mSectionedGridAdapter;

	//get list of goldproduct types
	private ArrayList<GoldProducts> mGoldProducts = new ArrayList<GoldProducts>();

	private BroadcastReceiver br;

	private IntentFilter mIntentFilter;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		System.out.println("goldAPIFragment onCreate().....");

		mIntentFilter = new IntentFilter("action_name");
	}

	@Override
	public void onAttach(Activity activity) {

		super.onAttach(activity);

		mGoldApi2 = activity;

		/**
		 * Initialize providing a single target image size (used for both width
		 * and height);
		 * 
		 * @param context
		 * @param imageSize
		 */
		imageFetcher = new ImageFetcher(activity, 50);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.gold_row1, container, false);

		return view;
	}

	//now changed
	private int getStartPriceRange(int position) {

		switch (position) {

		case 1:
			return 1000;
		case 2:
			return 20000;
		case 3:
			return 40000;
		case 4:
			return 60000;
		case 5:
			return 80000;
		}
		return 0;
	}

	private int getEndPriceRange(int position) {

		switch (position) {

		case 1:
			return 10000;
		case 2:
			return 30000;
		case 3:
			return 50000;
		case 4:
			return 70000;
		case 5:
			return 90000;
		}
		return 0;
	}

	private String getGoldPurityList(int position) {

		switch (position) {

		case 1:
			return "18 KT";

		case 2:
			return "22 KT(916)";
		}
		return null;
	}

	@Override
	public void onResume() {

		super.onResume();

		// Register BroadcastReceiver br to receive messages.
		LocalBroadcastManager.getInstance(getActivity()).registerReceiver(br, new IntentFilter("my-event"));
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);

		goldItemSearchView = (SearchView) getActivity().findViewById(R.id.goldSearchView);

		goldItemSearchView.setQueryHint("Enter Gold items to search");

		// Declaring and typecasting a Spinner
		priceSpinner = (Spinner) getActivity().findViewById(R.id.goldPriceSpinner1);

		// Declaring and typecasting a Spinner
		puritySpinner = (Spinner) getActivity().findViewById(R.id.goldPuritySpinner1);

		// Declaring and typecasting a Spinner
		Spinner weightSpinner = (Spinner) getActivity().findViewById(R.id.goldWeightSpinner1);

		//*** setOnQueryTextFocusChangeListener ***
		goldItemSearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {

				Toast.makeText(getActivity().getApplicationContext(), String.valueOf(hasFocus), Toast.LENGTH_SHORT).show();
			}
		});

		//*** setOnQueryTextListener ***
		goldItemSearchView.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {

				ArrayList<GoldProducts> gProducts = new ArrayList<GoldProducts>();

				for (int i = 0; i < mGoldProducts.size(); i++) {

					System.out.println("mGoldProducts size--" +mGoldProducts.size());

					GoldProducts gProducts1 = mGoldProducts.get(i);

					ArrayList<GoldItems> gItems = gProducts1.items;

					if (gItems == null) {

						continue;
					}
					ArrayList<GoldItems> gItemsList = new ArrayList<GoldItems>();

					for (int j = 0; j < gItems.size(); j++) {

						System.out.println("gItems size--" +gItems.size());

						GoldItems gItems1 = gItems.get(j);

						String itemName = gItems1.getName();

						String genderName = gItems1.getGender();

						System.out.println("gold each Items name--" +itemName);

						System.out.println("gold each Gender name---" +genderName);

						if (itemName.equals(query) || genderName.equals(query)) {

							//map every goldItems in list
							gItemsList.add(gItems1);
						} 
					}
					System.out.println("final gItemsList---" +gItemsList.size());

					if (gItemsList.size() > 0) {

						gProducts1.items = gItemsList;

						//map each goldProducts items in to a list
						gProducts.add(gProducts1);
					}
				}
				updateSearchViewAdapter(gProducts);

				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {

				return false;
			}
		});

		//display price list of ranges in each spinner items
		priceRangesList.add("Price");

		priceRangesList.add("1,000 - 10,000");

		priceRangesList.add("20,000 - 30,000");

		priceRangesList.add("40,000 - 50,000");

		priceRangesList.add("60,000 - 70,000");

		priceRangesList.add("80,000 - 90,000");

		// Setting a Custom Adapter to the Spinner
		priceSpinner.setAdapter(new PriceSpinnerAdapter(getActivity().getApplicationContext(), R.layout.custom_price_range, priceRangesList));

		priceSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

				//now changed
				if(position > 0) {
					// Get the price range using position
					final int startRange = getStartPriceRange(position);

					final int endRange = getEndPriceRange(position);

					//list of goldproducts to get products type items
					ArrayList<GoldProducts> tempList = new ArrayList<GoldProducts>();

					System.out.println("GoldProducts size---" +mGoldProducts.size());

					for(int i = 0; i < mGoldProducts.size(); i++) {

						GoldProducts goldProducts = mGoldProducts.get(i);

						ArrayList<GoldItems> goldItems = goldProducts.items;

						if(goldItems == null) {

							continue;
						}
						final ArrayList<GoldItems> tempGoldItemList = new ArrayList<GoldItems>();

						for(int j = 0; j < goldItems.size(); j++) {

							GoldItems items = goldItems.get(j);

							final double price = items.getPrice();

							if(price >= startRange && (price <= endRange)) {

								tempGoldItemList.add(items);
							} 
						}
						System.out.println("tempGoldItemList.size()----"+tempGoldItemList.size());

						if(tempGoldItemList.size() > 0) {

							goldProducts.items = tempGoldItemList;

							tempList.add(goldProducts);
						}
					}
					updatePriceAdapter(tempList);
				} else {
					updatePriceAdapter(mGoldProducts);
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		purityListItems.add("Purity");

		purityListItems.add("18 KT");

		purityListItems.add("22 KT(916)");

		// Setting a Custom Adapter to the Spinner
		puritySpinner.setAdapter(new PuritySpinnerAdapter(getActivity().getApplicationContext(), R.layout.custom_purity_range, purityListItems));

		puritySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

				if (position > 0) {

					//get gold purity list 
					final String goldPurityList = getGoldPurityList(position);

					System.out.println("goldPurityList---"+goldPurityList);

					ArrayList<GoldProducts> tempList1 = new ArrayList<GoldProducts>();

					System.out.println("GoldProducts size---" +mGoldProducts.size());

					for(int i = 0; i < mGoldProducts.size(); i++) {

						GoldProducts goldProducts = mGoldProducts.get(i);

						ArrayList<GoldItems> goldItems = goldProducts.items;

						if(goldItems == null) {

							continue;
						}
						final ArrayList<GoldItems> tempGoldItemList = new ArrayList<GoldItems>();

						for(int j = 0; j < goldItems.size(); j++) {

							GoldItems items = goldItems.get(j);

							final String purity = items.getPurity();

							System.out.println("purity acc to position---" +purity);

							//compares object address returns true
							if(purity.equals(goldPurityList)) { 

								tempGoldItemList.add(items);
							}
						}
						System.out.println("tempGoldItemList.size()----"+tempGoldItemList.size());

						if(tempGoldItemList.size() > 0) {

							goldProducts.items = tempGoldItemList;

							tempList1.add(goldProducts);
						}
					}
					updatePurityAdapter(tempList1);
				} else {
					updatePurityAdapter(mGoldProducts);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		// Setting a Custom Adapter to the Spinner
		weightSpinner.setAdapter(new WeightSpinnerAdapter(getActivity().getApplicationContext(), R.layout.custom_weight_range, weightRangesList));

		//receiving broadcast
		br = new BroadcastReceiver() {

			//This method is called when the BroadcastReceiver is receiving an Intent broadcast.
			@Override
			public void onReceive(Context context, Intent intent) {

				String womenItems = intent.getStringExtra("womenFirstFrag");

				String menItems = intent.getStringExtra("menFirstFrag");

				String womenBangleItems = intent.getStringExtra("womenGoldItems0");

				String womenEarRingItems = intent.getStringExtra("womenGoldItems1");

				String womenNecklaceItems = intent.getStringExtra("womenGoldItems2");

				String womenBlackBeedsChainItems = intent.getStringExtra("womenGoldItems3");

				String womenTanmaniaItems = intent.getStringExtra("womenGoldItems4");

				String womenRingItems = intent.getStringExtra("womenGoldItems5");

				String womenPendantItems = intent.getStringExtra("womenGoldItems6");

				String womenChainItems = intent.getStringExtra("womenGoldItems7");

				String womenMangalasutraItems = intent.getStringExtra("womenGoldItems8");

				String womenNeckalceSetItems = intent.getStringExtra("womenGoldItems9");

				String womenBraceletItems = intent.getStringExtra("womenGoldItems10");

				String womenMaangTikkaItems = intent.getStringExtra("womenGoldItems11");

				System.out.println("womenFirstFrag---" +womenItems);

				System.out.println("menFirstFrag---" +menItems);

				Log.d("womenitemsreceiver", "Got message: " + womenItems);

				Log.d("womenitemsreceiver", "Got msg: " +womenItems);

				ArrayList<GoldProducts> gItemProduct = new ArrayList<GoldProducts>();

				for (int i = 0; i < mGoldProducts.size(); i++) {

					GoldProducts gProd = mGoldProducts.get(i);

					ArrayList<GoldItems> gGoldItems = gProd.items;

					if(gGoldItems == null) {

						continue;
					}
					final ArrayList<GoldItems> mGoldItems = new ArrayList<GoldItems>();

					for (int k = 0; k < gGoldItems.size(); k++) {

						GoldItems gItems = gGoldItems.get(k);

						String genderName = gItems.getGender();

						String itemsName = gItems.getName();

						System.out.println("genderName----" +genderName);

						System.out.println("itemsName-----" +itemsName);

						if (genderName.equals(womenItems) || genderName.equals(menItems) || itemsName.equals(womenBangleItems) || itemsName.equals(womenEarRingItems) || itemsName.equals(womenNecklaceItems) || itemsName.equals(womenBlackBeedsChainItems) || itemsName.equals(womenTanmaniaItems) || itemsName.equals(womenRingItems) || itemsName.equals(womenPendantItems) || itemsName.equals(womenChainItems) || itemsName.equals(womenMangalasutraItems) || itemsName.equals(womenNeckalceSetItems) || itemsName.equals(womenBraceletItems) || itemsName.equals(womenMaangTikkaItems)) {

							mGoldItems.add(gItems);
						}/*else if (genderName.equals("Men")) {

							mGoldItems.add(gItems);
						}*/
					}
					if(mGoldItems.size() > 0) {

						gProd.items = mGoldItems;

						gItemProduct.add(gProd);
					}
				}
				updateWomenGoldItems(gItemProduct);
			}
		};

		// call AsyncTask to parse the given url
		new JSONAsyncGoldTask().execute("http://brinvents.com/jewellery/api/ListOfProducts/retrive.json?type=Gold");

		goldGridView = (GridView) getActivity().findViewById(R.id.stickyGridHeadersGoldGridView1);

		gAdapter = new GoldAdapter(mGoldApi2);

		mSectionedGridAdapter = new SimpleSectionedGridAdapter(mGoldApi2, gAdapter, R.layout.grid_item_header, R.id.header_layout, R.id.header);

		mSectionedGridAdapter.setGridView(goldGridView);

		goldGridView.setAdapter(mSectionedGridAdapter);
	}

	@Override
	public void onPause() {

		// Unregister since the activity is not visible
		LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(br);

		super.onPause();
	} 

	// Creating an Adapter Class
	public class PriceSpinnerAdapter extends ArrayAdapter<String> {

		public PriceSpinnerAdapter(Context context, int textViewResourceId, ArrayList<String> objects) {

			super(context, textViewResourceId, objects);

			mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public View getCustomView(int position, View convertView, ViewGroup parent) {

			// Inflating the layout for the custom Spinner
			View layout = mInflater.inflate(R.layout.custom_price_range, parent, false);

			// Declaring and Typecasting the textview in the inflated layout
			TextView tvPriceRange = (TextView) layout.findViewById(R.id.priceRange);

			// Setting the text using the array
			tvPriceRange.setText(priceRangesList.get(position));

			// Setting the color of the text
			tvPriceRange.setTextColor(Color.rgb(75, 180, 225));

			// Setting Special atrributes for 1st element
			if (position == 0) {
				// Setting the size of the text
				tvPriceRange.setTextSize(20f);
				// Setting the text Color
				tvPriceRange.setTextColor(Color.BLACK);
			}
			return layout;
		}

		// It gets a View that displays in the drop down popup the data at the
		// specified position
		@Override
		public View getDropDownView(int position, View convertView, ViewGroup parent) {

			return getCustomView(position, convertView, parent);
		}

		// It gets a View that displays the data at the specified position
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			return getCustomView(position, convertView, parent);
		}
	}

	// Creating an Adapter Class
	public class PuritySpinnerAdapter extends ArrayAdapter<String> {

		public PuritySpinnerAdapter(Context context, int textViewResourceId, ArrayList<String> objects) {

			super(context, textViewResourceId, objects);

			mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public View getCustomView(int position, View convertView, ViewGroup parent) {

			// Inflating the layout for the custom Spinner
			View layout = mInflater.inflate(R.layout.custom_purity_range, parent, false);

			// Declaring and Typecasting the textview in the inflated layout
			TextView tvLanguage = (TextView) layout.findViewById(R.id.purityRange);

			// Setting the text using the array
			tvLanguage.setText(purityListItems.get(position));

			// Setting the color of the text
			tvLanguage.setTextColor(Color.rgb(75, 180, 225));

			// Setting Special atrributes for 1st element
			if (position == 0) {
				// Setting the size of the text
				tvLanguage.setTextSize(20f);
				// Setting the text Color
				tvLanguage.setTextColor(Color.BLACK);
			}
			return layout;
		}

		// It gets a View that displays in the drop down popup the data at the
		// specified position
		@Override
		public View getDropDownView(int position, View convertView, ViewGroup parent) {

			return getCustomView(position, convertView, parent);
		}

		// It gets a View that displays the data at the specified position
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			return getCustomView(position, convertView, parent);
		}
	}

	// Creating an Adapter Class
	public class WeightSpinnerAdapter extends ArrayAdapter<String> {

		public WeightSpinnerAdapter(Context context, int textViewResourceId, ArrayList<String> objects) {

			super(context, textViewResourceId, objects);

			mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public View getCustomView(int position, View convertView, ViewGroup parent) {

			// Inflating the layout for the custom Spinner
			View layout = mInflater.inflate(R.layout.custom_weight_range, parent, false);

			// Declaring and Typecasting the textview in the inflated layout
			TextView tvLanguage = (TextView) layout.findViewById(R.id.weightRange);

			// Setting the text using the array
			tvLanguage.setText(weightRangesList.get(position));

			// Setting the color of the text
			tvLanguage.setTextColor(Color.rgb(75, 180, 225));

			// Setting Special atrributes for 1st element
			if (position == 0) {
				// Setting the size of the text
				tvLanguage.setTextSize(20f);
				// Setting the text Color
				tvLanguage.setTextColor(Color.BLACK);
			}
			return layout;
		}

		// It gets a View that displays in the drop down popup the data at the
		// specified position
		@Override
		public View getDropDownView(int position, View convertView, ViewGroup parent) {

			return getCustomView(position, convertView, parent);
		}

		// It gets a View that displays the data at the specified position
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			return getCustomView(position, convertView, parent);
		}
	}

	class JSONAsyncGoldTask extends AsyncTask<String, Void, ArrayList<GoldProducts>> {

		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {

			super.onPreExecute();

			dialog = new ProgressDialog(mGoldApi2);

			dialog.setMessage("LoadingImages, please wait");

			dialog.setTitle("Connecting server");

			dialog.show();

			dialog.setCancelable(false);
		}

		@Override
		protected ArrayList<GoldProducts> doInBackground(String... urls) {

			try {
				HttpGet httpGet = new HttpGet(urls[0]);

				HttpClient httpclient = new DefaultHttpClient();

				HttpResponse response = httpclient.execute(httpGet);

				int status = response.getStatusLine().getStatusCode();

				if (status == 200) {

					HttpEntity entity = response.getEntity();

					String data = EntityUtils.toString(entity);

					JSONObject json = new JSONObject(data);

					System.out.println("response from the server-----------" + json);

					JSONObject result = json.getJSONObject("Result");

					int errorCode = result.getInt("errorCode");

					System.out.println("ERROR_CODE->" + errorCode);

					String errorMessage = result.getString("errorMessage");

					System.out.println("ERROR_MESSAGE->" + errorMessage);

					int statusCode = result.getInt("statusCode");

					System.out.println("STATUS_CODE->" + statusCode);

					// jsonarray parse for listOfItems
					JSONArray jarray = result.getJSONArray("listOfItems");

					// create an array list for Gold product array
					ArrayList<GoldProducts> productList = new ArrayList<GoldProducts>();

					for (int i = 0; i < jarray.length(); i++) {

						JSONObject jsonObj = jarray.getJSONObject(i);

						// jsonarray parse for products
						JSONArray jarray1 = jsonObj.getJSONArray("products");

						for (int j = 0; j < jarray1.length(); j++) {

							JSONObject products = jarray1.optJSONObject(j);

							if (products == null) {

								continue;
							}
							// jsonarray parse for items
							JSONArray itemJsonArray = products.getJSONArray("items");

							items = new ArrayList<GoldItems>();

							for (int k = 0; k < itemJsonArray.length(); k++) {

								final JSONObject itemObject = itemJsonArray.optJSONObject(k);

								if (itemObject == null) {

									continue;
								}
								// creating model with object to store items
								GoldItems item = new GoldItems();

								item.name = itemObject.optString("name");

								item.jewelleryType = itemObject.optString("jewellery_type_name");

								item.gender = itemObject.optString("gender_name");

								item.style = itemObject.optString("wearing_style_name");

								item.designType = itemObject.optString("design_type_name");

								item.clarity = itemObject.optString("clarity_name");

								item.color = itemObject.optString("color_name");

								item.size = itemObject.optString("ring_size_name");

								item.url = itemObject.optString("uri");

								item.purity = itemObject.optString("purity_name");

								//This class provides the interface for formatting and parsing numbers.
								NumberFormat format = NumberFormat.getInstance(Locale.getDefault());

								Number number;

								try {
									//Parses a Number from the specified string using the rules of this number format.
									number = format.parse(itemObject.optString("price"));

									System.out.println("price string to number--" +number);

									//Returns this object's value as a double. Might involve rounding.
									item.price = number.doubleValue();

								} catch (java.text.ParseException e) {

									e.printStackTrace();
								}

								// adding model object to array or dictionary
								items.add(item);

								// Populate spinner with priceranges names
								// priceRangesList.add(itemObject.optString("price"));
							}
							String productName = products.optString("PT");

							if ((TextUtils.isEmpty(productName) != true) && (items.size() > 0)) {

								GoldProducts product = new GoldProducts();

								product.productName = productName;

								product.items = items;

								productList.add(product);
							}
						}
					}
					return productList;
				}
			} catch (ParseException e1) {

				e1.printStackTrace();

			} catch (IOException e) {

				e.printStackTrace();

			} catch (JSONException e) {

				e.printStackTrace();
			}
			return null;
		}

		// Runs on the UI thread after doInBackground
		protected void onPostExecute(ArrayList<GoldProducts> result) {

			dialog.dismiss();

			if (result == null) {

				return;
			}
			mGoldProducts.clear();

			mGoldProducts.addAll(result);

			updateSearchViewAdapter(mGoldProducts);

			updatePriceAdapter(mGoldProducts);

			updatePurityAdapter(mGoldProducts);
		}
	}

	private void updateSearchViewAdapter(ArrayList<GoldProducts> result) {

		final ArrayList<Section> sections = new ArrayList<Section>();

		final ArrayList<GoldItems> items = new ArrayList<GoldItems>();

		for (int i = 0; i < result.size(); i++) {

			System.out.println("GoldProducts size--" + result.size());

			GoldProducts product = result.get(i);

			if (product == null || product.items == null) {

				continue;
			}
			sections.add(new Section(items.size(), product.productName));

			items.addAll(product.items);
		}
		mSectionedGridAdapter.setSections(sections.toArray(new Section[0]));

		gAdapter.setItems(items);
	}

	private void updatePriceAdapter(ArrayList<GoldProducts> result) {

		final ArrayList<Section> sections = new ArrayList<Section>();

		final ArrayList<GoldItems> items = new ArrayList<GoldItems>();

		for (int i = 0; i < result.size(); i++) {

			System.out.println("GoldProducts size--" + result.size());

			GoldProducts product = result.get(i);

			if (product == null || product.items == null) {

				continue;
			}
			sections.add(new Section(items.size(), product.productName));

			items.addAll(product.items);
		}
		mSectionedGridAdapter.setSections(sections.toArray(new Section[0]));

		gAdapter.setItems(items);
	}

	public void updatePurityAdapter(ArrayList<GoldProducts> result) {

		final ArrayList<Section> sections = new ArrayList<Section>();

		final ArrayList<GoldItems> items = new ArrayList<GoldItems>();

		for (int i = 0; i < result.size(); i++) {

			System.out.println("GoldProducts size--" + result.size());

			GoldProducts product = result.get(i);

			//not getting
			if (product == null || product.items == null) {

				continue;
			}
			sections.add(new Section(items.size(), product.productName));

			items.addAll(product.items);
		}
		mSectionedGridAdapter.setSections(sections.toArray(new Section[0]));

		gAdapter.setItems(items);
	}

	public void updateWomenGoldItems(ArrayList<GoldProducts> result) {

		final ArrayList<Section> sections = new ArrayList<Section>();

		final ArrayList<GoldItems> items = new ArrayList<GoldItems>();

		for (int i = 0; i < result.size(); i++) {

			System.out.println("GoldProducts size--" + result.size());

			GoldProducts product = result.get(i);

			//not getting
			if (product == null || product.items == null) {

				continue;
			}
			sections.add(new Section(items.size(), product.productName));

			items.addAll(product.items);
		}
		mSectionedGridAdapter.setSections(sections.toArray(new Section[0]));

		gAdapter.setItems(items);
	}

	private class GoldAdapter extends BaseAdapter {

		private ArrayList<GoldItems> mGoldItems = new ArrayList<GoldItems>();

		public GoldAdapter(Context context) {

			mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public void setItems(ArrayList<GoldItems> items) {

			mGoldItems.clear();

			if (items != null) {

				mGoldItems.addAll(items);
			}
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {

			return mGoldItems.size();
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

			final GoldItems items = mGoldItems.get(position);

			final SquareImageView image;

			if (convertView == null) {

				convertView = mInflater.inflate(R.layout.gold_row, parent, false);
			}

			image = ViewHolder.get(convertView, R.id.GoldImageLogo1);

			imagePrice = ViewHolder.get(convertView, R.id.goldImagePriceId);

			//Sets a drawable as the content of this ImageView. 
			image.setImageResource(R.drawable.ic_launcher);

			System.out.println("position----" + position + " URL " + items.url);

			// Returns the string representation of the given int.
			final String filename = String.valueOf(items.url.hashCode());

			System.out.println("actuall url hashcode position---" + filename);

			// storing price value to string from items model object.
			String txt = items.price + "";

			//removing special characters using regExp
			final String price = txt.replaceAll("[^0-9.]", "");

			System.out.println("price in string value---" + price);

			final Double priceRange = Double.parseDouble(price);

			System.out.println("price is----" + priceRange);

			// get the images from url and store it in sdcard device memory
			updateImageView(image, filename, items.url, items.price + "");

			ArrayList<String> priceRangeList = new ArrayList<String>();

			priceRangeList.add(price);

			// sorting array of price in ascending or descending order .
			Collections.sort(priceRangeList);

			System.out.println("array of price value..." + priceRangesList);

			image.setOnClickListener(new OnGoldImageClickListener(filename));

			return convertView;
		}

		class OnGoldImageClickListener implements OnClickListener {

			String goldImgPostion;

			// constructor
			public OnGoldImageClickListener(String position) {

				this.goldImgPostion = position;
			}

			@Override
			public void onClick(View v) {
				// on selecting grid view image
				// launch full screen activity
				Intent i = new Intent(getActivity().getApplicationContext(), GoldFullScreenImage.class);

				i.putExtra("goldimgposition", goldImgPostion);

				i.putExtra("golditems", mGoldItems);

				mGoldApi2.startActivity(i);

				/*
				 * flipper.setInAnimation(inFromRightAnimation());
				 * 
				 * flipper.showNext();
				 */
			}
		}
	}

	public static class ViewHolder {

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

	public void updateImageView(ImageView bmImage, String filename, String url, String price) {

		try {
			File SDCardRoot = Environment.getExternalStorageDirectory().getAbsoluteFile();

			// create a new file, specifying the path, and the filename
			// which we want to save the file as.
			File file = new File(SDCardRoot, filename);

			if (file.exists()) {

				imageFetcher.loadImage(file.getAbsoluteFile(), bmImage);

				imagePrice.setText(price);

			} else {

				new DownloadGoldImageTask1(bmImage, filename, price).execute(url);
			}
		} catch (Exception e) {

			new DownloadGoldImageTask1(bmImage, filename, price).execute(url);
		}
	}

	public class DownloadGoldImageTask1 extends AsyncTask<String, Void, String> {

		ImageView bmImage;

		private String mFileName;

		private String mPrice;

		public DownloadGoldImageTask1(ImageView bmImage, String filename, String price) {

			this.bmImage = bmImage;

			this.mFileName = filename;

			this.mPrice = price;
		}

		@Override
		protected String doInBackground(String... urls) {

			String urldisplay = urls[0];

			Bitmap bitmap = null;

			File SDCardRoot = Environment.getExternalStorageDirectory().getAbsoluteFile();

			// /mnt/sdcard ->SDCardRoot
			System.out.println("SDCardRoot : " + SDCardRoot + " mFileName : " + mFileName);

			File file = new File(SDCardRoot, mFileName);

			try {

				file.createNewFile();

			} catch (IOException e1) {

				e1.printStackTrace();
			}

			try {
				// set the download URL, a url that points to a file on the
				// internet
				// this is the file to be downloaded
				URL url = new URL(urls[0]);

				System.out.println("url..." + url);

				// create the new connection
				HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

				// set up some things on the connection
				urlConnection.setRequestMethod("GET");

				urlConnection.setDoOutput(true);

				// and connect!
				urlConnection.connect();

				InputStream in = new java.net.URL(urldisplay).openStream();

				FileOutputStream os = new FileOutputStream(file);

				int len;

				byte[] buffer = new byte[10 * 1024]; // 10MB

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

				// Decode an input stream into a bitmap.
				// stream closed (java.io.IOException)
				// Creates Bitmap objects from various sources, including files,
				// streams, and byte-arrays.
				bitmap = BitmapFactory.decodeStream(in);
				// catch some possible errors...

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

			imagePrice.setText(mPrice);
		}
	}
}
