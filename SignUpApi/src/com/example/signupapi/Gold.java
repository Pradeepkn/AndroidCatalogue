package com.example.signupapi;

public class Gold {

	private String CT;
	private String PT;
	private String name;
	private String jewellery_type_name;
	private String gender_name;
	private String wearing_style_name;
	private String design_type_name;
	private String clarity_name;
	private String color_name;
	private String ring_size_name;
	private String uri;
	private String price;

	public Gold(){

	}

	public Gold(String CT, String PT, String name, String jewellery_type_name,
			String gender_name, String wearing_style_name,
			String design_type_name, String clarity_name, String color_name,
			String ring_size_name, String uri, String price) {
		super();
		this.CT = CT;
		this.PT = PT;
		this.name = name;
		this.jewellery_type_name = jewellery_type_name;
		this.gender_name = gender_name;
		this.wearing_style_name = wearing_style_name;
		this.design_type_name = design_type_name;
		this.clarity_name = clarity_name;
		this.color_name = color_name;
		this.ring_size_name = ring_size_name;
		this.uri = uri;
		this.price = price;
	}

	public String getCT() {
		return CT;
	}

	public void setCT(String cT) {
		CT = cT;
	}

	public String getPT() {
		return PT;
	}

	public void setPT(String pT) {
		PT = pT;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJewellery_type_name() {
		return jewellery_type_name;
	}

	public void setJewellery_type_name(String jewellery_type_name) {
		this.jewellery_type_name = jewellery_type_name;
	}

	public String getGender_name() {
		return gender_name;
	}

	public void setGender_name(String gender_name) {
		this.gender_name = gender_name;
	}

	public String getWearing_style_name() {
		return wearing_style_name;
	}

	public void setWearing_style_name(String wearing_style_name) {
		this.wearing_style_name = wearing_style_name;
	}

	public String getDesign_type_name() {
		return design_type_name;
	}

	public void setDesign_type_name(String design_type_name) {
		this.design_type_name = design_type_name;
	}

	public String getClarity_name() {
		return clarity_name;
	}

	public void setClarity_name(String clarity_name) {
		this.clarity_name = clarity_name;
	}

	public String getColor_name() {
		return color_name;
	}

	public void setColor_name(String color_name) {
		this.color_name = color_name;
	}

	public String getRing_size_name() {
		return ring_size_name;
	}

	public void setRing_size_name(String ring_size_name) {
		this.ring_size_name = ring_size_name;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
}
