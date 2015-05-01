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

import com.example.goldproject.DiamondFullScreenImage;
import com.example.goldproject.R;
import com.example.goldproject.jewellerymodels.DiamondItems;
import com.example.goldproject.jewellerymodels.DiamondProducts;
import com.example.goldproject.util.cache.ImageFetcher;
import dev.dworks.libs.astickyheader.SimpleSectionedGridAdapter;
import dev.dworks.libs.astickyheader.SimpleSectionedGridAdapter.Section;
import dev.dworks.libs.astickyheader.ui.SquareImageView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DiamondApiSecondFragment extends Fragment {

	private DiamondAdapter dAdapter;

	private GridView diamondGridView;

	private Activity mDiamondApi2;

	public Context context;

	private LayoutInflater mInflater;

	private ImageFetcher imageFetcher;

	// A view that displays one child at a time and lets the user pick among
	// them. The items in the Spinner come from the Adapter associated with this
	// view.
	private Spinner diamondPriceSpinner;

	private Spinner diamondPuritySpinner;

	private ArrayList<DiamondItems> items;

	private	TextView imagePrice;

	private SearchView DiamondItemSearchView;

	protected static final String TAG = "DiamondApiFragment2";

	private SimpleSectionedGridAdapter mSectionedGridAdapter;

	ArrayList<String> diamondPriceRangesList = new ArrayList<String>();

	ArrayList<String> diamondPurityListItems = new ArrayList<String>();

	ArrayList<String> diamondWeightRangesList = new ArrayList<String>();

	//get list of diamondproduct types
	private ArrayList<DiamondProducts> mDiamondProducts = new ArrayList<DiamondProducts>();

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		System.out.println("DiamondAPISecondFragment onCreate().....");
	}

	@Override
	public void onAttach(Activity activity) {

		super.onAttach(activity);

		mDiamondApi2 = activity;

		imageFetcher = new ImageFetcher(activity, 50);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.diamond_row1, container, false);

		return view;
	}

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

	private String getDiamondPurity(int position) {

		switch (position) {
		case 1:
			return "18 KT";

		case 2:
			return "22 KT(916)";
		}
		return null;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);

		DiamondItemSearchView = (SearchView) getActivity().findViewById(R.id.diamondSearchView);

		DiamondItemSearchView.setQueryHint("Enter items to search");

		// Declaring and typecasting a Spinner
		diamondPriceSpinner = (Spinner) getActivity().findViewById(R.id.diamondPriceSpinner1);

		// Declaring and typecasting a Spinner
		diamondPuritySpinner = (Spinner) getActivity().findViewById(R.id.diamondPuritySpinner1);

		// Declaring and typecasting a Spinner
		Spinner weightSpinner = (Spinner) getActivity().findViewById(R.id.diamondWeightSpinner1);

		//*** setOnQueryTextFocusChangeListener ***
		DiamondItemSearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {

				Toast.makeText(getActivity().getApplicationContext(), String.valueOf(hasFocus), Toast.LENGTH_SHORT).show();
			}
		});

		//*** setOnQueryTextListener ***
		DiamondItemSearchView.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {

				ArrayList<DiamondProducts> dProducts = new ArrayList<DiamondProducts>();

				for (int i = 0; i < mDiamondProducts.size(); i++) {

					System.out.println("mDiamondProducts size--" +mDiamondProducts.size());

					DiamondProducts dProducts1 = mDiamondProducts.get(i);

					ArrayList<DiamondItems> dItems = dProducts1.items;

					if (dItems == null) {

						continue;
					}
					ArrayList<DiamondItems> dItemsList = new ArrayList<DiamondItems>();

					for (int j = 0; j < dItems.size(); j++) {

						System.out.println("gItems size--" +dItems.size());

						DiamondItems dItems1 = dItems.get(j);

						String itemName = dItems1.getName();

						System.out.println("gold each Items name--" +itemName);

						if (itemName.equals(query)) {

							//map every diamondItems in list
							dItemsList.add(dItems1);
						}
					}
					System.out.println("final gItemsList---" +dItemsList.size());

					if (dItemsList.size() > 0) {

						dProducts1.items = dItemsList;

						//map each diamondproducts items in to a list
						dProducts.add(dProducts1);
					}
				}
				updateDiamondSearchViewAdapter(dProducts);

				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {

				return false;
			}
		});

		//display price list of ranges in each spinner items
		diamondPriceRangesList.add("Price");

		diamondPriceRangesList.add("1,000 - 10,000");

		diamondPriceRangesList.add("20,000 - 30,000");

		diamondPriceRangesList.add("40,000 - 50,000");

		diamondPriceRangesList.add("60,000 - 70,000");

		diamondPriceRangesList.add("80,000 - 90,000");

		// Setting a Custom Adapter to the Spinner
		diamondPriceSpinner.setAdapter(new PriceSpinnerAdapter(getActivity().getApplicationContext(), R.layout.diamond_custom_price_range, diamondPriceRangesList));

		diamondPriceSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

				//now changed
				if(position > 0) {
					// Get the price range using position
					final int startRange = getStartPriceRange(position);

					final int endRange = getEndPriceRange(position);

					//list of diamondproducts to get products type items
					ArrayList<DiamondProducts> tempList = new ArrayList<DiamondProducts>();

					System.out.println("DiamondProducts size---" +mDiamondProducts.size());

					for(int i = 0; i < mDiamondProducts.size(); i++) {

						DiamondProducts diamondProducts = mDiamondProducts.get(i);

						ArrayList<DiamondItems> diamondItems = diamondProducts.items;

						if(diamondItems == null) {

							continue;
						}
						final ArrayList<DiamondItems> tempDiamondItemList = new ArrayList<DiamondItems>();

						for(int j = 0; j < diamondItems.size(); j++) {

							DiamondItems items = diamondItems.get(j);

							final double price = items.getPrice();

							if(price >= startRange && (price <= endRange)) {

								tempDiamondItemList.add(items);
							} 
						}
						System.out.println("tempDiamondItemList.size()----"+tempDiamondItemList.size());

						if(tempDiamondItemList.size() > 0) {

							diamondProducts.items = tempDiamondItemList;

							tempList.add(diamondProducts);
						}
					}
					updateDiamondPriceAdapter(tempList);
				} else {
					updateDiamondPriceAdapter(mDiamondProducts);
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		diamondPurityListItems.add("Purity");

		diamondPurityListItems.add("18 KT");

		diamondPurityListItems.add("22 KT(916)");

		// Setting a Custom Adapter to the Spinner
		diamondPuritySpinner.setAdapter(new PuritySpinnerAdapter(getActivity().getApplicationContext(), R.layout.diamond_custom_purity_range, diamondPurityListItems));

		diamondPuritySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

				if (position > 0) {

					//get gold purity list 
					final String diamondPurityList = getDiamondPurity(position);

					System.out.println("goldPurityList---"+diamondPurityList);

					ArrayList<DiamondProducts> tempList1 = new ArrayList<DiamondProducts>();

					System.out.println("DiamondProducts size---" +mDiamondProducts.size());

					for(int i = 0; i < mDiamondProducts.size(); i++) {

						DiamondProducts diamondProducts = mDiamondProducts.get(i);

						ArrayList<DiamondItems> diamondItems = diamondProducts.items;

						if(diamondItems == null) {

							continue;
						}
						final ArrayList<DiamondItems> tempGoldItemList = new ArrayList<DiamondItems>();

						for(int j = 0; j < diamondItems.size(); j++) {

							DiamondItems items = diamondItems.get(j);

							final String purity = items.getPurity();

							System.out.println("purity acc to position---" +purity);

							//compares object address returns true
							if(purity.equals(diamondPurityList)) { 

								tempGoldItemList.add(items);
							}
						}
						System.out.println("tempGoldItemList.size()----"+tempGoldItemList.size());

						if(tempGoldItemList.size() > 0) {

							diamondProducts.items = tempGoldItemList;

							tempList1.add(diamondProducts);
						}
					}
					updateDiamondPurityAdapter(tempList1);
				} else {
					updateDiamondPurityAdapter(mDiamondProducts);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		// Setting a Custom Adapter to the Spinner
		weightSpinner.setAdapter(new WeightSpinnerAdapter(getActivity().getApplicationContext(), R.layout.custom_weight_range, diamondWeightRangesList));

		// call AsyncTask to parse the given url
		new JSONAsyncDiamondTask().execute("http://brinvents.com/jewellery/api/ListOfProducts/retrive.json?type=Diamond");

		diamondGridView =  (GridView) getActivity().findViewById(R.id.stickyGridHeadersDiamondGridView1);

		dAdapter = new DiamondAdapter(mDiamondApi2);

		mSectionedGridAdapter = new SimpleSectionedGridAdapter(mDiamondApi2, dAdapter, R.layout.grid_item_header, R.id.header_layout, R.id.header);

		mSectionedGridAdapter.setGridView(diamondGridView);

		diamondGridView.setAdapter(mSectionedGridAdapter);
	}

	// Creating an Adapter Class
	public class PriceSpinnerAdapter extends ArrayAdapter<String> {

		public PriceSpinnerAdapter(Context context, int textViewResourceId, ArrayList<String> objects) {

			super(context, textViewResourceId, objects);

			mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public View getCustomView(int position, View convertView, ViewGroup parent) {

			// Inflating the layout for the custom Spinner
			View layout = mInflater.inflate(R.layout.diamond_custom_price_range, parent, false);

			// Declaring and Typecasting the textview in the inflated layout
			TextView tvPriceRange = (TextView) layout.findViewById(R.id.diamondPriceRange);

			// Setting the text using the array
			tvPriceRange.setText(diamondPriceRangesList.get(position));

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
			View layout = mInflater.inflate(R.layout.diamond_custom_purity_range, parent, false);

			// Declaring and Typecasting the textview in the inflated layout
			TextView tvLanguage = (TextView) layout.findViewById(R.id.diamondPurityRange);

			// Setting the text using the array
			tvLanguage.setText(diamondPurityListItems.get(position));

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
			View layout = mInflater.inflate(R.layout.diamond_custom_weight_range, parent, false);

			// Declaring and Typecasting the textview in the inflated layout
			TextView tvLanguage = (TextView) layout.findViewById(R.id.diamondWeightRange);

			// Setting the text using the array
			tvLanguage.setText(diamondWeightRangesList.get(position));

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

	class JSONAsyncDiamondTask extends AsyncTask<String, Void, ArrayList<DiamondProducts>> {

		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {

			super.onPreExecute();

			dialog = new ProgressDialog(mDiamondApi2);

			dialog.setMessage("LoadingImages, please wait");

			dialog.setTitle("Connecting server");

			dialog.show();

			dialog.setCancelable(false);
		}

		@Override
		protected ArrayList<DiamondProducts> doInBackground(String... urls) {

			try {
				HttpGet httpGet = new HttpGet(urls[0]);

				HttpClient httpclient = new DefaultHttpClient();

				HttpResponse response = httpclient.execute(httpGet);

				int status = response.getStatusLine().getStatusCode();

				if (status == 200) {

					HttpEntity entity = response.getEntity();

					String data = EntityUtils.toString(entity);

					JSONObject json = new JSONObject(data);

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

					ArrayList<DiamondProducts> productList = new ArrayList<DiamondProducts>();

					for (int i = 0; i < jarray.length(); i++) {

						JSONObject jsonObj = jarray.getJSONObject(i);

						//jsonarray parse for products
						JSONArray jarray1 = jsonObj.getJSONArray("products");

						for (int j = 0; j < jarray1.length(); j++) {

							JSONObject products = jarray1.optJSONObject(j);

							if(products == null) {

								continue;
							}
							//jsonarray parse for items
							JSONArray itemJsonArray = products.getJSONArray("items");

							items = new ArrayList<DiamondItems>();

							for (int k = 0; k < itemJsonArray.length(); k++) {

								final JSONObject itemObject = itemJsonArray.getJSONObject(k);

								if(itemObject == null) {

									continue;
								}
								DiamondItems item = new DiamondItems();

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

									System.out.println("price string to number--"+number);

									//Returns this object's value as a double. Might involve rounding.
									item.price = number.doubleValue();

								} catch (java.text.ParseException e) {

									e.printStackTrace();
								}
								items.add(item);
							}
							String productName = products.optString("PT");

							if((TextUtils.isEmpty(productName) != true) && (items.size() > 0)) {

								DiamondProducts product = new DiamondProducts();

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
		protected void onPostExecute(ArrayList<DiamondProducts> result) {

			dialog.dismiss();

			if(result == null) {

				return;
			}
			mDiamondProducts.clear();

			mDiamondProducts.addAll(result);

			updateDiamondPriceAdapter(mDiamondProducts);

			updateDiamondPurityAdapter(mDiamondProducts);
		}
	} 

	private void updateDiamondSearchViewAdapter(ArrayList<DiamondProducts> result) {

		final ArrayList<Section> sections = new ArrayList<Section>();

		final ArrayList<DiamondItems> items = new ArrayList<DiamondItems>();

		for (int i = 0; i < result.size(); i++) {

			System.out.println("DiamondProducts size--" + result.size());

			DiamondProducts product = result.get(i);

			if (product == null || product.items == null) {

				continue;
			}
			sections.add(new Section(items.size(), product.productName));

			items.addAll(product.items);
		}
		mSectionedGridAdapter.setSections(sections.toArray(new Section[0]));

		dAdapter.setItems(items);
	}

	private void updateDiamondPriceAdapter(ArrayList<DiamondProducts> result) {

		final ArrayList<Section> sections = new ArrayList<Section>();

		final ArrayList<DiamondItems> items = new ArrayList<DiamondItems>();

		for (int i = 0; i < result.size(); i++) {

			System.out.println("DiamondProducts size--" + result.size());

			DiamondProducts product = result.get(i);

			if (product == null || product.items == null) {

				continue;
			}
			sections.add(new Section(items.size(), product.productName));

			items.addAll(product.items);
		}

		mSectionedGridAdapter.setSections(sections.toArray(new Section[0]));

		dAdapter.setItems(items);
	}

	public void updateDiamondPurityAdapter(ArrayList<DiamondProducts> result) {

		final ArrayList<Section> sections = new ArrayList<Section>();

		final ArrayList<DiamondItems> items = new ArrayList<DiamondItems>();

		for (int i = 0; i < result.size(); i++) {

			System.out.println("DiamondProducts size--" + result.size());

			DiamondProducts product = result.get(i);

			//not getting
			if (product == null || product.items == null) {

				continue;
			}
			sections.add(new Section(items.size(), product.productName));

			items.addAll(product.items);
		}
		mSectionedGridAdapter.setSections(sections.toArray(new Section[0]));

		dAdapter.setItems(items);
	}

	private class DiamondAdapter extends BaseAdapter {

		private ArrayList<DiamondItems> mDiamondItems = new ArrayList<DiamondItems>();

		public DiamondAdapter(Context context) {

			mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public void setItems(ArrayList<DiamondItems> items) {

			mDiamondItems.clear();

			if(items != null) {

				mDiamondItems.addAll(items);
			}
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {

			return mDiamondItems.size();
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

			final DiamondItems items = mDiamondItems.get(position);

			final SquareImageView image;

			if (convertView == null) {

				convertView = mInflater.inflate(R.layout.diamond_row, parent, false);
			}

			image = ViewHolder.get(convertView, R.id.diamondImageLogo1);

			imagePrice = ViewHolder.get(convertView, R.id.diamondImagePriceId);

			//Sets a drawable as the content of this ImageView. 
			image.setImageResource(R.drawable.ic_launcher);

			System.out.println("position----"+position + " URL " + items.url);

			//Returns the string representation of the given int.
			final String filename=String.valueOf(items.url.hashCode());

			System.out.println("filename hashcode value---"+filename);

			// storing price value to string from items model object.
			String txt = items.price + "";

			//removing special characters using regExp
			final String price = txt.replaceAll("[^0-9.]", "");

			System.out.println("price in string value---" + price);

			final Double priceRange = Double.parseDouble(price);

			System.out.println("price is----" + priceRange);

			// get the images from url and store it in sdcard device memory
			updateImageView(image, filename, items.url, items.price + "");

			ArrayList<String> priceDiamondRangeList = new ArrayList<String>();

			priceDiamondRangeList.add(price);

			// sorting array of price in ascending or descending order .
			Collections.sort(priceDiamondRangeList);

			System.out.println("array of price value..." + diamondPriceRangesList);

			image.setOnClickListener(new OnDiamondImageClickListener(filename));

			return convertView;
		}

		class OnDiamondImageClickListener implements OnClickListener {

			String diamondImgPostion;

			// constructor
			public OnDiamondImageClickListener(String position) {

				this.diamondImgPostion = position;
			}

			@Override
			public void onClick(View v) {
				// on selecting grid view image
				// launch full screen activity
				Intent i = new Intent(getActivity().getApplicationContext(), DiamondFullScreenImage.class);

				i.putExtra("diamondimgposition", diamondImgPostion);

				i.putExtra("diamonditems", mDiamondItems);

				mDiamondApi2.startActivity(i);
			}
		}
	}

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

	public void updateImageView(ImageView bmImage, String filename, String url, String price) {

		try {
			File SDCardRoot = Environment.getExternalStorageDirectory().getAbsoluteFile();
			//create a new file, specifying the path, and the filename
			//which we want to save the file as.
			//      String filename="downloadedFile.png";  
			File file = new File(SDCardRoot,filename);

			if(file.exists()) {

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

				byte[] buffer = new byte[10 * 1024];//10MB

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

			imagePrice.setText(mPrice);
		}
	}
}
