package com.example.goldproject.jewellerymodels;

import java.util.ArrayList;

public class GoldProducts {

	public String productName;

	public ArrayList<GoldItems> items;

	public GoldProducts(){

	}

	public GoldProducts(String productName) {
		super();
		this.productName = productName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
}
