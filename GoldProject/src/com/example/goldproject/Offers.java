package com.example.goldproject;

public class Offers {


	private String jewellery_type;

	private String offer_type;

	private String offer_discount;

	private String making_charge_discount;

	private String wastage_charge;

	private String offer_on_purity;

	private String offer_validity;

	private String offer_image;

	public Offers() {
	}

	public Offers(String jewellery_type, String offer_type, String offer_discount, String making_charge_discount, String wastage_charge, String offer_on_purity, String offer_validity, String offer_image) {

		super();

		this.jewellery_type = jewellery_type;

		this.offer_type = offer_type;

		this.offer_discount = offer_discount;

		this.making_charge_discount = making_charge_discount;

		this.wastage_charge = wastage_charge;

		this.offer_on_purity = offer_on_purity;

		this.offer_validity = offer_validity;

		this.offer_image = offer_image;
	}

	public String getJewellery_type() {
		return jewellery_type;
	}

	public void setJewellery_type(String jewellery_type) {
		this.jewellery_type = jewellery_type;
	}

	public String getOffer_type() {
		return offer_type;
	}

	public void setOffer_type(String offer_type) {
		this.offer_type = offer_type;
	}

	public String getOffer_discount() {
		return offer_discount;
	}

	public void setOffer_discount(String offer_discount) {
		this.offer_discount = offer_discount;
	}

	public String getMaking_charge_discount() {
		return making_charge_discount;
	}

	public void setMaking_charge_discount(String making_charge_discount) {
		this.making_charge_discount = making_charge_discount;
	}

	public String getWastage_charge() {
		return wastage_charge;
	}

	public void setWastage_charge(String wastage_charge) {
		this.wastage_charge = wastage_charge;
	}

	public String getOffer_on_purity() {
		return offer_on_purity;
	}

	public void setOffer_on_purity(String offer_on_purity) {
		this.offer_on_purity = offer_on_purity;
	}

	public String getOffer_validity() {
		return offer_validity;
	}

	public void setOffer_validity(String offer_validity) {
		this.offer_validity = offer_validity;
	}

	public String getOffer_image() {
		return offer_image;
	}

	public void setOffer_image(String offer_image) {
		this.offer_image = offer_image;
	}

}
