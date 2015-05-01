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

import com.example.goldproject.PlatinumFullScreenImage;
import com.example.goldproject.R;
import com.example.goldproject.jewellerymodels.PlatinumItems;
import com.example.goldproject.jewellerymodels.PlatinumProducts;
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

public class PlatinumApiSecondFragment extends Fragment {

	private PlatinumAdapter pAdapter;

	private GridView platinumGridView;

	private Activity mPlatinumApi2;

	public Context context;

	private LayoutInflater mInflater;

	// A view that displays one child at a time and lets the user pick among
	// them. The items in the Spinner come from the Adapter associated with this
	// view.
	private Spinner platinumPriceSpinner;

	private Spinner platinumPuritySpinner;

	private ArrayList<PlatinumItems> items;

	private	TextView imagePrice;

	private SearchView platinumItemSearchView;

	protected static final String TAG = "platinumApiFragment2";

	private ImageFetcher imageFetcher;

	private SimpleSectionedGridAdapter mSectionedGridAdapter;

	ArrayList<String> platinumPriceRangesList = new ArrayList<String>();

	ArrayList<String> platinumPurityListItems = new ArrayList<String>();

	ArrayList<String> platinumWeightRangesList = new ArrayList<String>();

	//get list of diamondproduct types
	private ArrayList<PlatinumProducts> mPlatinumProducts = new ArrayList<PlatinumProducts>();

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		System.out.println("PlatinumAPISecondFragment onCreate().....");
	}

	@Override
	public void onAttach(Activity activity) {

		super.onAttach(activity);

		mPlatinumApi2 = activity;

		imageFetcher = new ImageFetcher(activity, 50);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.platinum_row1, container, false);

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

	private String getPlatinumPurity(int position) {

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

		platinumItemSearchView = (SearchView) getActivity().findViewById(R.id.platinumSearchView);

		platinumItemSearchView.setQueryHint("Enter items to search");

		// Declaring and typecasting a Spinner
		platinumPriceSpinner = (Spinner) getActivity().findViewById(R.id.platinumPriceSpinner1);

		// Declaring and typecasting a Spinner
		platinumPuritySpinner = (Spinner) getActivity().findViewById(R.id.platinumPuritySpinner1);

		// Declaring and typecasting a Spinner
		Spinner weightSpinner = (Spinner) getActivity().findViewById(R.id.platinumWeightSpinner1);

		//*** setOnQueryTextFocusChangeListener ***
		platinumItemSearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {

				Toast.makeText(getActivity().getApplicationContext(), String.valueOf(hasFocus), Toast.LENGTH_SHORT).show();
			}
		});

		//*** setOnQueryTextListener ***
		platinumItemSearchView.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {

				ArrayList<PlatinumProducts> pProducts = new ArrayList<PlatinumProducts>();

				for (int i = 0; i < mPlatinumProducts.size(); i++) {

					System.out.println("mPlatinumProducts size--" +mPlatinumProducts.size());

					PlatinumProducts pProducts1 = mPlatinumProducts.get(i);

					ArrayList<PlatinumItems> pItems = pProducts1.items;

					if (pItems == null) {

						continue;
					}
					ArrayList<PlatinumItems> pItemsList = new ArrayList<PlatinumItems>();

					for (int j = 0; j < pItems.size(); j++) {

						System.out.println("gItems size--" +pItems.size());

						PlatinumItems pItems1 = pItems.get(j);

						String itemName = pItems1.getName();

						System.out.println("platinum each Items name--" +itemName);

						if (itemName.equals(query)) {

							//map every diamondItems in list
							pItemsList.add(pItems1);
						}
					}
					System.out.println("final gItemsList---" +pItemsList.size());

					if (pItemsList.size() > 0) {

						pProducts1.items = pItemsList;

						//map each diamondproducts items in to a list
						pProducts.add(pProducts1);
					}
				}
				updatePlatinumSearchViewAdapter(pProducts);

				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {

				return false;
			}
		});

		//display price list of ranges in each spinner items
		platinumPriceRangesList.add("Price");

		platinumPriceRangesList.add("1,000 - 10,000");

		platinumPriceRangesList.add("20,000 - 30,000");

		platinumPriceRangesList.add("40,000 - 50,000");

		platinumPriceRangesList.add("60,000 - 70,000");

		platinumPriceRangesList.add("80,000 - 90,000");

		// Setting a Custom Adapter to the Spinner
		platinumPriceSpinner.setAdapter(new PriceSpinnerAdapter(getActivity().getApplicationContext(), R.layout.custom_price_range, platinumPriceRangesList));

		platinumPriceSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

				//now changed
				if(position > 0) {
					// Get the price range using position
					final int startRange = getStartPriceRange(position);

					final int endRange = getEndPriceRange(position);

					//list of platinumproducts to get products type items
					ArrayList<PlatinumProducts> tempList = new ArrayList<PlatinumProducts>();

					System.out.println("PlatinumProducts size---" +mPlatinumProducts.size());

					for(int i = 0; i < mPlatinumProducts.size(); i++) {

						PlatinumProducts platinumProducts = mPlatinumProducts.get(i);

						ArrayList<PlatinumItems> platinumItems = platinumProducts.items;

						if(platinumItems == null) {

							continue;
						}
						final ArrayList<PlatinumItems> tempPlatinumItemList = new ArrayList<PlatinumItems>();

						for(int j = 0; j < platinumItems.size(); j++) {

							PlatinumItems items = platinumItems.get(j);

							final double price = items.getPrice();

							if(price >= startRange && (price <= endRange)) {

								tempPlatinumItemList.add(items);
							} 
						}
						System.out.println("tempDiamondItemList.size()----"+tempPlatinumItemList.size());

						if(tempPlatinumItemList.size() > 0) {

							platinumProducts.items = tempPlatinumItemList;

							tempList.add(platinumProducts);
						}
					}
					updatePlatinumPriceAdapter(tempList);
				} else {
					updatePlatinumPriceAdapter(mPlatinumProducts);
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		platinumPurityListItems.add("Purity");

		platinumPurityListItems.add("18 KT");

		platinumPurityListItems.add("22 KT(916)");

		// Setting a Custom Adapter to the Spinner
		platinumPuritySpinner.setAdapter(new PuritySpinnerAdapter(getActivity().getApplicationContext(), R.layout.custom_purity_range, platinumPurityListItems));

		platinumPuritySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

				if (position > 0) {

					//get gold purity list 
					final String platinumPurityList = getPlatinumPurity(position);

					System.out.println("platinumPurityList---" +platinumPurityList);

					ArrayList<PlatinumProducts> tempList1 = new ArrayList<PlatinumProducts>();

					System.out.println("platinumProducts size---" +mPlatinumProducts.size());

					for(int i = 0; i < mPlatinumProducts.size(); i++) {

						PlatinumProducts platinumProducts = mPlatinumProducts.get(i);

						ArrayList<PlatinumItems> platinumItems = platinumProducts.items;

						if(platinumItems == null) {

							continue;
						}
						final ArrayList<PlatinumItems> tempPlatinumItemList = new ArrayList<PlatinumItems>();

						for(int j = 0; j < platinumItems.size(); j++) {

							PlatinumItems items = platinumItems.get(j);

							final String purity = items.getPurity();

							System.out.println("purity acc to position---" +purity);

							//compares object address returns true
							if(purity.equals(platinumPurityList)) { 

								tempPlatinumItemList.add(items);
							}
						}
						System.out.println("tempPlatinumItemList.size()----" +tempPlatinumItemList.size());

						if(tempPlatinumItemList.size() > 0) {

							platinumProducts.items = tempPlatinumItemList;

							tempList1.add(platinumProducts);
						}
					}
					updatePlatinumPurityAdapter(tempList1);
				} else {
					updatePlatinumPurityAdapter(mPlatinumProducts);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		// Setting a Custom Adapter to the Spinner
		weightSpinner.setAdapter(new WeightSpinnerAdapter(getActivity().getApplicationContext(), R.layout.custom_weight_range, platinumWeightRangesList));

		new JSONAsyncPlatinumTask().execute("http://brinvents.com/jewellery/api/ListOfProducts/retrive.json?type=Platinum");

		platinumGridView = (GridView) getActivity().findViewById(R.id.stickyGridHeadersPlatinumGridView1);

		pAdapter = new PlatinumAdapter(mPlatinumApi2);

		mSectionedGridAdapter = new SimpleSectionedGridAdapter(mPlatinumApi2, pAdapter, R.layout.grid_item_header, R.id.header_layout, R.id.header);

		mSectionedGridAdapter.setGridView(platinumGridView);

		platinumGridView.setAdapter(mSectionedGridAdapter);
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
			tvPriceRange.setText(platinumPriceRangesList.get(position));

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
			tvLanguage.setText(platinumPurityListItems.get(position));

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
			tvLanguage.setText(platinumWeightRangesList.get(position));

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

	class JSONAsyncPlatinumTask extends AsyncTask<String, Void, ArrayList<PlatinumProducts>>{

		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {

			super.onPreExecute();

			dialog = new ProgressDialog(mPlatinumApi2);

			dialog.setMessage("LoadingImages, please wait");

			dialog.setTitle("Connecting server");

			dialog.show();

			dialog.setCancelable(false);
		}

		@Override
		protected ArrayList<PlatinumProducts> doInBackground(String... urls) {

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

					ArrayList<PlatinumProducts> productList = new ArrayList<PlatinumProducts>();

					for (int i = 0; i < jarray.length(); i++) {

						JSONObject jsonObj = jarray.getJSONObject(i);

						//jsonarray parse for products
						JSONArray jarray1 = jsonObj.getJSONArray("products");

						for (int j = 0; j < jarray1.length(); j++) {

							JSONObject products = jarray1.getJSONObject(j);

							if(products == null) {

								continue;
							}
							//jsonarray parse for items
							JSONArray itemJsonArray = products.getJSONArray("items");

							items = new ArrayList<PlatinumItems>();

							for (int k = 0; k < itemJsonArray.length(); k++) {

								final JSONObject itemObject = itemJsonArray.getJSONObject(k);

								if(itemObject == null) {

									continue;
								}

								PlatinumItems item = new PlatinumItems();

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

								PlatinumProducts product = new PlatinumProducts();

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

		protected void onPostExecute(ArrayList<PlatinumProducts> result) {

			dialog.dismiss();

			if(result == null) {

				return;
			}
			mPlatinumProducts.clear();

			mPlatinumProducts.addAll(result);

			updatePlatinumPriceAdapter(mPlatinumProducts);

			updatePlatinumPurityAdapter(mPlatinumProducts);
		}
	} 
	private void updatePlatinumSearchViewAdapter(ArrayList<PlatinumProducts> result) {

		final ArrayList<Section> sections = new ArrayList<Section>();

		final ArrayList<PlatinumItems> items = new ArrayList<PlatinumItems>();

		for (int i = 0; i < result.size(); i++) {

			System.out.println("platinumProducts size--" + result.size());

			PlatinumProducts product = result.get(i);

			if (product == null || product.items == null) {

				continue;
			}
			sections.add(new Section(items.size(), product.productName));

			items.addAll(product.items);
		}
		mSectionedGridAdapter.setSections(sections.toArray(new Section[0]));

		pAdapter.setItems(items);
	}

	private void updatePlatinumPriceAdapter(ArrayList<PlatinumProducts> result) {

		final ArrayList<Section> sections = new ArrayList<Section>();

		final ArrayList<PlatinumItems> items = new ArrayList<PlatinumItems>();

		for (int i = 0; i < result.size(); i++) {

			System.out.println("platinumProducts size--" + result.size());

			PlatinumProducts product = result.get(i);

			if (product == null || product.items == null) {

				continue;
			}
			sections.add(new Section(items.size(), product.productName));

			items.addAll(product.items);
		}

		mSectionedGridAdapter.setSections(sections.toArray(new Section[0]));

		pAdapter.setItems(items);
	}

	public void updatePlatinumPurityAdapter(ArrayList<PlatinumProducts> result) {

		final ArrayList<Section> sections = new ArrayList<Section>();

		final ArrayList<PlatinumItems> items = new ArrayList<PlatinumItems>();

		for (int i = 0; i < result.size(); i++) {

			System.out.println("platinumProducts size--" + result.size());

			PlatinumProducts product = result.get(i);

			//not getting
			if (product == null || product.items == null) {

				continue;
			}
			sections.add(new Section(items.size(), product.productName));

			items.addAll(product.items);
		}
		mSectionedGridAdapter.setSections(sections.toArray(new Section[0]));

		pAdapter.setItems(items);
	}

	private class PlatinumAdapter extends BaseAdapter {

		private LayoutInflater mInflater;

		private ArrayList<PlatinumItems> mPlatinumItems = new ArrayList<PlatinumItems>();

		public PlatinumAdapter(Context context) {

			mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public void setItems(ArrayList<PlatinumItems> items) {

			mPlatinumItems.clear();

			if(items != null) {

				mPlatinumItems.addAll(items);
			}

			notifyDataSetChanged();
		}

		@Override
		public int getCount() {

			return mPlatinumItems.size();
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

			final PlatinumItems items = mPlatinumItems.get(position);

			SquareImageView image;

			if (convertView == null) {

				convertView = mInflater.inflate(R.layout.platinum_row, parent, false);
			}

			image = ViewHolder.get(convertView, R.id.platinumImageLogo1);

			imagePrice = ViewHolder.get(convertView, R.id.platinumImagePriceId);

			image.setImageResource(R.drawable.ic_launcher);
			//holder.imageview.setImageResource(R.drawable.ic_launcher);

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

			ArrayList<String> pricePlatinumRangeList = new ArrayList<String>();

			pricePlatinumRangeList.add(price);

			// sorting array of price in ascending or descending order .
			Collections.sort(pricePlatinumRangeList);

			System.out.println("array of price value..." + pricePlatinumRangeList);

			image.setOnClickListener(new OnPlatinumImageClickListener(filename));

			return convertView;
		}

		class OnPlatinumImageClickListener implements OnClickListener {

			String platinumImgPostion;

			public OnPlatinumImageClickListener(String position) {

				this.platinumImgPostion = position;
			}

			@Override
			public void onClick(View v) {

				Intent i = new Intent(getActivity().getApplicationContext(), PlatinumFullScreenImage.class);

				i.putExtra("platinumimgposition", platinumImgPostion);

				i.putExtra("platinumitems", mPlatinumItems);

				mPlatinumApi2.startActivity(i);
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

				imagePrice.setText(price);

			} else {

				new DownloadPlatinumImageTask1(bmImage, filename, price).execute(url);
			}
		} catch (Exception e) {

			new DownloadPlatinumImageTask1(bmImage, filename, price).execute(url);
		}
	}

	public class DownloadPlatinumImageTask1 extends AsyncTask<String, Void, String> {

		ImageView bmImage;

		private String mFileName;

		private String mPrice;

		public DownloadPlatinumImageTask1(ImageView bmImage, String filename, String price) {

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
