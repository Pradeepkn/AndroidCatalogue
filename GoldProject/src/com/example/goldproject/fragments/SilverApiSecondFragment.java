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

import com.example.goldproject.R;
import com.example.goldproject.SilverFullScreenImage;
import com.example.goldproject.jewellerymodels.SilverItems;
import com.example.goldproject.jewellerymodels.SilverProducts;
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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.SearchView.OnQueryTextListener;

public class SilverApiSecondFragment extends Fragment {

	private SilverAdapter sAdapter;

	private GridView silverGridView;

	public Activity mSilverApi2;

	public Context context;

	private LayoutInflater mInflater;

	private ImageFetcher imageFetcher;

	// A view that displays one child at a time and lets the user pick among
	// them. The items in the Spinner come from the Adapter associated with this
	// view.
	private Spinner silverPriceSpinner;

	private Spinner silverPuritySpinner;

	private ArrayList<SilverItems> items;

	private	TextView silverImagePrice;

	private SearchView silverItemSearchView;

	protected static final String TAG = "SilverApiFragment2";

	private SimpleSectionedGridAdapter mSectionedGridAdapter;

	ArrayList<String> silverPriceRangesList = new ArrayList<String>();

	ArrayList<String> silverPurityListItems = new ArrayList<String>();

	ArrayList<String> silverWeightRangesList = new ArrayList<String>();

	//get list of diamondproduct types
	private ArrayList<SilverProducts> mSilverProducts = new ArrayList<SilverProducts>();

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		System.out.println("silverAPISecondFragment onCreate().....");
	}

	@Override
	public void onAttach(Activity activity) {

		super.onAttach(activity);

		mSilverApi2 = activity;

		/**
		 * Initialize providing a single target image size (used for both width and height);
		 * 
		 * @param context
		 * @param imageSize
		 */
		imageFetcher = new ImageFetcher(activity, 50);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.silver_row1, container, false);

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

	private String getSilverPurity(int position) {

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

		silverItemSearchView = (SearchView) getActivity().findViewById(R.id.silverSearchView);

		silverItemSearchView.setQueryHint("Enter items to search");

		// Declaring and typecasting a Spinner
		silverPriceSpinner = (Spinner) getActivity().findViewById(R.id.silverPriceSpinner1);

		// Declaring and typecasting a Spinner
		silverPuritySpinner = (Spinner) getActivity().findViewById(R.id.silverPuritySpinner1);

		// Declaring and typecasting a Spinner
		Spinner weightSpinner = (Spinner) getActivity().findViewById(R.id.silverWeightSpinner1);

		//*** setOnQueryTextFocusChangeListener ***
		silverItemSearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {

				Toast.makeText(getActivity().getApplicationContext(), String.valueOf(hasFocus), Toast.LENGTH_SHORT).show();
			}
		});

		//*** setOnQueryTextListener ***
		silverItemSearchView.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {

				ArrayList<SilverProducts> sProducts = new ArrayList<SilverProducts>();

				for (int i = 0; i < mSilverProducts.size(); i++) {

					System.out.println("mSilverProducts size--" +mSilverProducts.size());

					SilverProducts sProducts1 = mSilverProducts.get(i);

					ArrayList<SilverItems> sItems = sProducts1.items;

					if (sItems == null) {

						continue;
					}
					ArrayList<SilverItems> sItemsList = new ArrayList<SilverItems>();

					for (int j = 0; j < sItems.size(); j++) {

						System.out.println("sItems size--" +sItems.size());

						SilverItems sItems1 = sItems.get(j);

						String itemName = sItems1.getName();

						System.out.println("silver each Items name--" +itemName);

						if (itemName.equals(query)) {

							//map every diamondItems in list
							sItemsList.add(sItems1);
						}
					}
					System.out.println("final sItemsList---" +sItemsList.size());

					if (sItemsList.size() > 0) {

						sProducts1.items = sItemsList;

						//map each diamondproducts items in to a list
						sProducts.add(sProducts1);
					}
				}
				updateSilverSearchViewAdapter(sProducts);

				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {

				return false;
			}
		});

		//display price list of ranges in each spinner items
		silverPriceRangesList.add("Price");

		silverPriceRangesList.add("1,000 - 10,000");

		silverPriceRangesList.add("20,000 - 30,000");

		silverPriceRangesList.add("40,000 - 50,000");

		silverPriceRangesList.add("60,000 - 70,000");

		silverPriceRangesList.add("80,000 - 90,000");

		// Setting a Custom Adapter to the Spinner
		silverPriceSpinner.setAdapter(new PriceSpinnerAdapter(getActivity().getApplicationContext(), R.layout.custom_price_range, silverPriceRangesList));

		silverPriceSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

				//now changed
				if(position > 0) {
					// Get the price range using position
					final int startRange = getStartPriceRange(position);

					final int endRange = getEndPriceRange(position);

					//list of platinumproducts to get products type items
					ArrayList<SilverProducts> tempList = new ArrayList<SilverProducts>();

					System.out.println("SilverProducts size---" +mSilverProducts.size());

					for(int i = 0; i < mSilverProducts.size(); i++) {

						SilverProducts silverProducts = mSilverProducts.get(i);

						ArrayList<SilverItems> silverItems = silverProducts.items;

						if(silverItems == null) {

							continue;
						}
						final ArrayList<SilverItems> tempSilverItemList = new ArrayList<SilverItems>();

						for(int j = 0; j < silverItems.size(); j++) {

							SilverItems items = silverItems.get(j);

							final double price = items.getPrice();

							if(price >= startRange && (price <= endRange)) {

								tempSilverItemList.add(items);
							} 
						}
						System.out.println("tempDiamondItemList.size()----"+tempSilverItemList.size());

						if(tempSilverItemList.size() > 0) {

							silverProducts.items = tempSilverItemList;

							tempList.add(silverProducts);
						}
					}
					updateSilverPriceAdapter(tempList);
				} else {
					updateSilverPriceAdapter(mSilverProducts);
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		silverPurityListItems.add("Purity");

		silverPurityListItems.add("18 KT");

		silverPurityListItems.add("22 KT(916)");

		// Setting a Custom Adapter to the Spinner
		silverPuritySpinner.setAdapter(new PuritySpinnerAdapter(getActivity().getApplicationContext(), R.layout.custom_purity_range, silverPurityListItems));

		silverPuritySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

				if (position > 0) {

					//get gold purity list 
					final String silverPurityList = getSilverPurity(position);

					System.out.println("silverPurityList---" +silverPurityList);

					ArrayList<SilverProducts> tempList1 = new ArrayList<SilverProducts>();

					System.out.println("silverProducts size---" +mSilverProducts.size());

					for(int i = 0; i < mSilverProducts.size(); i++) {

						SilverProducts silverProducts = mSilverProducts.get(i);

						ArrayList<SilverItems> silverItems = silverProducts.items;

						if(silverItems == null) {

							continue;
						}
						final ArrayList<SilverItems> tempSilverItemList = new ArrayList<SilverItems>();

						for(int j = 0; j < silverItems.size(); j++) {

							SilverItems items = silverItems.get(j);

							final String purity = items.getPurity();

							System.out.println("purity acc to position---" +purity);

							//compares object address returns true
							if(purity.equals(silverPurityList)) { 

								tempSilverItemList.add(items);
							}
						}
						System.out.println("tempSilverItemList.size()----" +tempSilverItemList.size());

						if(tempSilverItemList.size() > 0) {

							silverProducts.items = tempSilverItemList;

							tempList1.add(silverProducts);
						}
					}
					updateSilverPurityAdapter(tempList1);
				} else {
					updateSilverPurityAdapter(mSilverProducts);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		// Setting a Custom Adapter to the Spinner
		weightSpinner.setAdapter(new WeightSpinnerAdapter(getActivity().getApplicationContext(), R.layout.custom_weight_range, silverWeightRangesList));

		new JSONAsyncGoldTask().execute("http://brinvents.com/jewellery/api/ListOfProducts/retrive.json?type=Silver");

		silverGridView = (GridView) mSilverApi2.findViewById(R.id.stickyGridHeadersSilverGridView1);

		sAdapter = new SilverAdapter(mSilverApi2);

		mSectionedGridAdapter = new SimpleSectionedGridAdapter(mSilverApi2, sAdapter, R.layout.grid_item_header, R.id.header_layout, R.id.header);

		mSectionedGridAdapter.setGridView(silverGridView);

		silverGridView.setAdapter(mSectionedGridAdapter);
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
			tvPriceRange.setText(silverPriceRangesList.get(position));

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
			tvLanguage.setText(silverPurityListItems.get(position));

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
			tvLanguage.setText(silverWeightRangesList.get(position));

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

	class JSONAsyncGoldTask extends AsyncTask<String, Void, ArrayList<SilverProducts>> {

		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {

			super.onPreExecute();

			dialog = new ProgressDialog(mSilverApi2);

			dialog.setMessage("LoadingImages, please wait");

			dialog.setTitle("Connecting server");

			dialog.show();

			dialog.setCancelable(false);
		}

		@Override
		protected ArrayList<SilverProducts> doInBackground(String... urls) {

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

					//create an array list for Gold product array
					ArrayList<SilverProducts> productList = new ArrayList<SilverProducts>();

					for (int i = 0; i < jarray.length(); i++) {

						JSONObject jsonObj = jarray.getJSONObject(i);

						//jsonarray parse for products
						JSONArray jarray1 = jsonObj.getJSONArray("products");

						for (int j = 0; j < jarray1.length(); j++) {

							JSONObject products  = jarray1.optJSONObject(j);

							if(products == null) {

								continue;
							}

							//jsonarray parse for items
							JSONArray itemJsonArray  = products.getJSONArray("items");

							items = new ArrayList<SilverItems>();

							for (int k = 0; k < itemJsonArray.length(); k++) {

								final JSONObject itemObject = itemJsonArray.optJSONObject(k);

								if(itemObject == null) {

									continue;
								}

								SilverItems item = new SilverItems();

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

								SilverProducts product = new SilverProducts();

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

		protected void onPostExecute(ArrayList<SilverProducts> result) {

			dialog.dismiss();

			if(result == null) {

				return;
			}
			mSilverProducts.clear();

			mSilverProducts.addAll(result);

			updateSilverPriceAdapter(mSilverProducts);

			updateSilverPurityAdapter(mSilverProducts);
		}
	}
	private void updateSilverSearchViewAdapter(ArrayList<SilverProducts> result) {

		final ArrayList<Section> sections = new ArrayList<Section>();

		final ArrayList<SilverItems> items = new ArrayList<SilverItems>();

		for (int i = 0; i < result.size(); i++) {

			System.out.println("silverProducts size--" + result.size());

			SilverProducts product = result.get(i);

			if (product == null || product.items == null) {

				continue;
			}
			sections.add(new Section(items.size(), product.productName));

			items.addAll(product.items);
		}
		mSectionedGridAdapter.setSections(sections.toArray(new Section[0]));

		sAdapter.setItems(items);
	}

	private void updateSilverPriceAdapter(ArrayList<SilverProducts> result) {

		final ArrayList<Section> sections = new ArrayList<Section>();

		final ArrayList<SilverItems> items = new ArrayList<SilverItems>();

		for (int i = 0; i < result.size(); i++) {

			System.out.println("silverProducts size--" + result.size());

			SilverProducts product = result.get(i);

			if (product == null || product.items == null) {

				continue;
			}
			sections.add(new Section(items.size(), product.productName));

			items.addAll(product.items);
		}

		mSectionedGridAdapter.setSections(sections.toArray(new Section[0]));

		sAdapter.setItems(items);
	}

	public void updateSilverPurityAdapter(ArrayList<SilverProducts> result) {

		final ArrayList<Section> sections = new ArrayList<Section>();

		final ArrayList<SilverItems> items = new ArrayList<SilverItems>();

		for (int i = 0; i < result.size(); i++) {

			System.out.println("silverProducts size--" + result.size());

			SilverProducts product = result.get(i);

			//not getting
			if (product == null || product.items == null) {

				continue;
			}
			sections.add(new Section(items.size(), product.productName));

			items.addAll(product.items);
		}
		mSectionedGridAdapter.setSections(sections.toArray(new Section[0]));

		sAdapter.setItems(items);
	}

	private class SilverAdapter extends BaseAdapter {

		private LayoutInflater mInflater;

		private ArrayList<SilverItems> mSilverItems = new ArrayList<SilverItems>();

		public SilverAdapter(Context context) {

			mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public void setItems(ArrayList<SilverItems> items) {

			mSilverItems.clear();

			if(items != null) {

				mSilverItems.addAll(items);
			}

			notifyDataSetChanged();
		}

		@Override
		public int getCount() {

			//		return goldList.size();
			return mSilverItems.size();
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

			final SilverItems items = mSilverItems.get(position);

			SquareImageView image;

			if (convertView == null) {

				convertView = mInflater.inflate(R.layout.silver_row, parent, false);
			}

			image = ViewHolder.get(convertView, R.id.SilverImageLogo1);

			silverImagePrice = ViewHolder.get(convertView, R.id.silverImagePriceId);

			image.setImageResource(R.drawable.ic_launcher);

			System.out.println("position----"+position + " URL " + items.url);

			//Returns the string representation of the given int.
			final String filename = String.valueOf(items.url.hashCode());

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

			ArrayList<String> priceSilverRangeList = new ArrayList<String>();

			priceSilverRangeList.add(price);

			// sorting array of price in ascending or descending order .
			Collections.sort(priceSilverRangeList);

			System.out.println("array of price value..." + priceSilverRangeList);

			image.setOnClickListener(new OnSilverImageClickListener(filename));

			return convertView;
		}

		class OnSilverImageClickListener implements OnClickListener {

			String silverImgPostion;

			// constructor
			public OnSilverImageClickListener(String position) {

				this.silverImgPostion = position;
			}

			@Override
			public void onClick(View v) {
				// on selecting grid view image
				// launch full screen activity
				Intent i = new Intent(getActivity().getApplicationContext(), SilverFullScreenImage.class);

				i.putExtra("silverimgposition", silverImgPostion);

				i.putExtra("silveritems", mSilverItems);

				mSilverApi2.startActivity(i);
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
			File file = new File(SDCardRoot,filename);

			if(file.exists()) {

				imageFetcher.loadImage(file.getAbsoluteFile(), bmImage);

			} else {

				new DownloadSilverImageTask1(bmImage, filename, price).execute(url);
			}
		} catch (Exception e) {

			new DownloadSilverImageTask1(bmImage, filename, price).execute(url);
		}
	}

	public class DownloadSilverImageTask1 extends AsyncTask<String, Void, String> {

		ImageView bmImage;

		private String mFileName;

		private String mPrice;

		public DownloadSilverImageTask1(ImageView bmImage, String filename, String price) {

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

				byte[] buffer = new byte[10 * 1024]; //10MB

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

			silverImagePrice.setText(mPrice);
		}
	}
}
