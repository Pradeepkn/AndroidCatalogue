package com.example.goldproject.jewellerymodels;

import java.io.Serializable;

public class PlatinumItems implements Serializable {

	private static final long serialVersionUID = 1L;
	public String name;
	public String jewelleryType;
	public String gender;
	public String style;
	public String designType;
	public String clarity;
	public String color;
	public String size;
	public String url;
	public double price;
	public String purity;

	public PlatinumItems(){

	}

	public PlatinumItems(String name, String jewelleryType, String gender,
			String style, String designType, String clarity, String color,
			String size, String url, double price, String purity) {
		super();
		this.name = name;
		this.jewelleryType = jewelleryType;
		this.gender = gender;
		this.style = style;
		this.designType = designType;
		this.clarity = clarity;
		this.color = color;
		this.size = size;
		this.url = url;
		this.price = price;
		this.purity = purity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJewelleryType() {
		return jewelleryType;
	}

	public void setJewelleryType(String jewelleryType) {
		this.jewelleryType = jewelleryType;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getDesignType() {
		return designType;
	}

	public void setDesignType(String designType) {
		this.designType = designType;
	}

	public String getClarity() {
		return clarity;
	}

	public void setClarity(String clarity) {
		this.clarity = clarity;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getPurity() {
		return purity;
	}

	public void setPurity(String purity) {
		this.purity = purity;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
