package com.mpowerpayments.mpower;
import org.json.simple.*;

//class for setting up online store information
public class MPowerCheckoutStore {
  private String name = "Untitled Store";
  private String tagline;  
  private String postalAddress;  
  private String phoneNumber;  
  private String logoUrl;
  private String websiteUrl;
  private String returnUrl; //the URL the user will be redirected to when payment is successful
  private String cancelUrl; //the URL the user will be redicted to when checkout is cancelled

	public MPowerCheckoutStore(String name, String tagline){
		setName(name);
		setTagline(tagline);
	}

	public MPowerCheckoutStore(String name) {
		setName(name);
	}

	public MPowerCheckoutStore() {

	}

	public void setName(String name){
		this.name = name;
	}

	public void setTagline(String tagline){
		this.tagline = tagline;
	}

	public void setPostalAddress(String postalAddress){
		this.postalAddress = postalAddress;
	}

	public void setPhoneNumber(String phoneNumber){
		this.phoneNumber = phoneNumber;
	}

	public void setWebsiteUrl(String websiteUrl){
		this.websiteUrl = websiteUrl;
	}

	public void setLogoUrl(String logoUrl){
		this.logoUrl = logoUrl;
	}

	public void setCancelUrl(String cancelUrl){
		this.cancelUrl = cancelUrl;
	}

	public void setReturnUrl(String returnUrl){
		this.returnUrl = returnUrl;
	}

	public String getName(){
		return this.name;
	}

	public String getTagline(){
		return this.tagline;
	}

	public String getPostalAddress(){
		return this.postalAddress;
	}

	public String getPhoneNumber(){
		return this.phoneNumber;
	}

	public String getWebsiteUrl(){
		return this.websiteUrl;
	}

	public String getLogoUrl(){
		return this.logoUrl;
	}

	public String getCancelUrl(){
		return this.cancelUrl;
	}

	public String getReturnUrl(){
		return this.returnUrl;
	}
	
	/**
	 * Returns {@link JSONObject } of the settings data.
	 * @return settings information
	 */
	public JSONObject getSettings() {
		JSONObject settings = new JSONObject();
		settings.put("name",getName());
		settings.put("tagline",getTagline());
		settings.put("postal_address",getPostalAddress());
		settings.put("phone",getPhoneNumber());
		settings.put("logo_url",getLogoUrl());
		settings.put("website_url",getWebsiteUrl());
		return settings;
	}
}