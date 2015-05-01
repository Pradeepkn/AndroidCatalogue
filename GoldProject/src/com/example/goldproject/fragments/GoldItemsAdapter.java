package com.example.goldproject.fragments;

import java.util.ArrayList;

import com.example.goldproject.R;
import com.example.goldproject.jewellerymodels.GoldItems;
import com.example.goldproject.jewellerymodels.GoldProducts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class GoldItemsAdapter extends ArrayAdapter<GoldItems> {

	ArrayList<GoldItems> goldItemsCategoryList;

	ArrayList<GoldProducts> goldItemsProductList;

	LayoutInflater vi;

	int Resource;

	ViewHolder holder;

	public GoldItemsAdapter(Context context, int resource, ArrayList<GoldItems> objects, ArrayList<GoldProducts> objects1) {

		super(context, resource, objects);

		vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		Resource = resource;

		goldItemsCategoryList = objects;

		goldItemsProductList = objects1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v =convertView;

		if (v == null) {

			holder = new ViewHolder();

			v = vi.inflate(Resource, null);

			holder.genderName = (TextView) v.findViewById(R.id.GoldWomenItemsId);

			holder.genderName1 = (TextView) v.findViewById(R.id.GoldMenId);

			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		//		String productName = goldItemsProductList.get(position).getProductName();

		if (goldItemsProductList.get(position).getProductName() == goldItemsCategoryList.get(position).getName()) {

			holder.genderName.setText(goldItemsCategoryList.get(position).getGender());
		}else{
			holder.genderName1.setText(goldItemsCategoryList.get(position).getGender());
		}
		return v;
	}

	static class ViewHolder {

		public TextView genderName;

		public TextView genderName1;
	}
}
