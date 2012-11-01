package com.mpowerpayments.mpower;

public class MPowerSetup {
	private String privateKey;
	private String publicKey;
	private String masterKey;
	private String token;
	private String mode;

	final String LIVE_CHECKOUT_INVOICE_BASE_URL = "https://app.mpowerpayments.com/api/v1/checkout-invoice/create";
  final String TEST_CHECKOUT_INVOICE_BASE_URL = "https://app.mpowerpayments.com/sandbox-api/v1/checkout-invoice/create";
  final String LIVE_CHECKOUT_CONFIRM_BASE_URL = "https://app.mpowerpayments.com/api/v1/checkout-invoice/confirm/";
  final String TEST_CHECKOUT_CONFIRM_BASE_URL = "https://app.mpowerpayments.com/sandbox-api/v1/checkout-invoice/confirm/";

	public MPowerSetup(String masterKey, String privateKey, String publicKey, String token, String mode) {
		this.masterKey = masterKey;
		this.privateKey = privateKey;
		this.publicKey = publicKey;
		this.token = token;
		this.mode = mode;
	}

	public MPowerSetup(){

	}

	public void setMasterKey(String str){
		this.masterKey = str;
	}

	public void setPrivateKey(String str){
		this.privateKey = str;
	}

	public void setPublicKey(String str){
		this.publicKey = str;
	}

	public void setToken(String str){
		this.token = str;
	}

	public void setMode(String str){
		this.mode = str;
	}

	public String getMasterKey(){
		return this.masterKey;
	}

	public String getPrivateKey(){
		return this.privateKey;
	}

	public String getPublicKey(){
		return this.publicKey;
	}

	public String getToken(){
		return this.token;
	}

	public String getMode(){
		return this.mode;
	}

	public String getCheckoutInvoiceUrl() {
		if (this.mode == "live") {
			return this.LIVE_CHECKOUT_INVOICE_BASE_URL;
		}else{
			return this.TEST_CHECKOUT_INVOICE_BASE_URL;
		}
	}

	public String getCheckoutConfirmUrl() {
		if (this.mode == "live") {
			return this.LIVE_CHECKOUT_CONFIRM_BASE_URL;
		}else{
			return this.TEST_CHECKOUT_CONFIRM_BASE_URL;
		}
	}
}