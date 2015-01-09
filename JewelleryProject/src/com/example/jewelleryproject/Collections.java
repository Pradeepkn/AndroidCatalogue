package com.example.jewelleryproject;

public class Collections {

	private String item_type;

	private String purity;

	private String image_path;

	private String price;

	private String occassion;

	private String weight;

	private String making_charge;

	private String wastage;

	private String model;

	public Collections(){

	}

	public Collections(String item_type, String purity, String image_path, String price, String occassion, String weight, String making_charge, String wastage, String model) {

		super();

		this.item_type = item_type;

		this.purity = purity;

		this.image_path = image_path;

		this.price = price;

		this.occassion = occassion;

		this.weight = weight;

		this.making_charge = making_charge;

		this.wastage = wastage;

		this.model = model;
	}

	public String getItem_type() {

		return item_type;
	}

	public void setItem_type(String item_type) {

		this.item_type = item_type;
	}

	public String getPurity() {

		return purity;
	}

	public void setPurity(String purity) {

		this.purity = purity;
	}

	public String getImage_path() {

		return image_path;
	}

	public void setImage_path(String image_path) {

		this.image_path = image_path;
	}

	public String getPrice() {

		return price;
	}

	public void setPrice(String price) {

		this.price = price;
	}

	public String getOccassion() {

		return occassion;
	}

	public void setOccassion(String occassion) {

		this.occassion = occassion;
	}

	public String getWeight() {

		return weight;
	}

	public void setWeight(String weight) {

		this.weight = weight;
	}

	public String getMaking_charge() {

		return making_charge;
	}

	public void setMaking_charge(String making_charge) {

		this.making_charge = making_charge;
	}

	public String getWastage() {

		return wastage;
	}

	public void setWastage(String wastage) {

		this.wastage = wastage;
	}

	public String getModel() {

		return model;
	}

	public void setModel(String model) {

		this.model = model;
	}
}
