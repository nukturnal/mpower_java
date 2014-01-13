package com.mpowerpayments.mpower;



//Class for setting up Mpower API Keys and getting the various URLS
public class MPowerSetup {
	private String privateKey;
	private String publicKey;
	private String masterKey;
	private String token;
	private Mode mode;

	final String ROOT_URL_BASE = "https://app.mpowerpayments.com";

	final String LIVE_CHECKOUT_INVOICE_BASE_URL = "/api/v1/checkout-invoice/create";
  final String TEST_CHECKOUT_INVOICE_BASE_URL = "/sandbox-api/v1/checkout-invoice/create";

  final String LIVE_CHECKOUT_CONFIRM_BASE_URL = "/api/v1/checkout-invoice/confirm/";
  final String TEST_CHECKOUT_CONFIRM_BASE_URL = "/sandbox-api/v1/checkout-invoice/confirm/";

	final String LIVE_OPR_BASE_URL = "/api/v1/opr/create";
	final String TEST_OPR_BASE_URL = "/sandbox-api/v1/opr/create";

	final String LIVE_OPR_CHARGE_BASE_URL = "/api/v1/opr/charge";
	final String TEST_OPR_CHARGE_BASE_URL = "/sandbox-api/v1/opr/charge";

	final String LIVE_DIRECT_PAY_CREDIT_URL = "/api/v1/direct-pay/credit-account";
	final String TEST_DIRECT_PAY_CREDIT_URL = "/sandbox-api/v1/direct-pay/credit-account";

	final String LIVE_DIRECT_CREDITCARD_CHARGE_URL = "/api/v1/direct-card/processcard";
	final String TEST_DIRECT_CREDITCARD_CHARGE_URL = "/sandbox-api/v1/direct-card/processcard";

	/**
	 * Constructor that sets up your already generated MPower api keys 
	 * @param  masterKey  The Master key
	 * @param  privateKey The Private key
	 * @param  publicKey  The Public key
	 * @param  token      The Token
	 * @param  mode       Indicate whether the application is in live or test mode. 
	 *                    <p> Use <code>Mode.LIVE</code> when app is live or <code>Mode.TEST</code> when in test mode
	 */

	public MPowerSetup(String masterKey, String privateKey, String publicKey, String token, Mode mode) {
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

	public void setMode(Mode mode){
		this.mode = mode;
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

	public Mode getMode(){
		return this.mode;
	}

	/**
	 * Returns the {@link URL} of the checkout invoice depending on whether the app is in live or test mode;
	 * @return Url of the checkout invoice
	 */
	public String getCheckoutInvoiceUrl() {
		if (this.mode == Mode.LIVE) {
			return ROOT_URL_BASE+LIVE_CHECKOUT_INVOICE_BASE_URL;
		}else{
			return ROOT_URL_BASE+TEST_CHECKOUT_INVOICE_BASE_URL;
		}
	}

	/**
	 * Checks whether the app is in live or test mode and Returns an appropriate {@link URL} to confirm the checkout.
	 * @return Url to confirm the checkout
	 */

	public String getCheckoutConfirmUrl() {
		if (this.mode == Mode.LIVE) {
			return ROOT_URL_BASE+LIVE_CHECKOUT_CONFIRM_BASE_URL;
		}else{
			return ROOT_URL_BASE+TEST_CHECKOUT_CONFIRM_BASE_URL;
		}
	}

	/**
	 * Checks whether the app is in live or test mode and Returns the appropriate {@link URL} to the Onsite Payment Request Invoice.
	 * @return URL to the Onsite Payment Request Invoice
	 */

	public String getOPRInvoiceUrl() {
		if (this.mode == Mode.LIVE) {
			return ROOT_URL_BASE+LIVE_OPR_BASE_URL;
		}else{
			return ROOT_URL_BASE+TEST_OPR_BASE_URL;
		}
	}

	/**
	 * Returns the Onsite Payment Request(OPR) Charge {@link URL} 
	 * @return OPR Charge URL
	 */
	public String getOPRChargeUrl() {
		if (this.mode == Mode.LIVE) {
			return ROOT_URL_BASE+LIVE_OPR_CHARGE_BASE_URL;
		}else{
			return ROOT_URL_BASE+TEST_OPR_CHARGE_BASE_URL;
		}
	}

	/**
	 * Returns the {@link URL} for the Direct Pay Credit
	 * @return URL for the Direct Pay Credit
	 */
	public String getDirectPayCreditUrl() {
		if (this.mode == Mode.LIVE) {
			return ROOT_URL_BASE+LIVE_DIRECT_PAY_CREDIT_URL;
		}else{
			return ROOT_URL_BASE+TEST_DIRECT_PAY_CREDIT_URL;
		}
	}

	/**
	 * Returns the {@link URL} for charging on users Credit Card
	 * @return URL for the Direct Credit Card Charge
	 */
	public String getDirectCreditcardChargeUrl() {
		if (this.mode == Mode.LIVE) {
			return ROOT_URL_BASE+LIVE_DIRECT_CREDITCARD_CHARGE_URL;
		}else{
			return ROOT_URL_BASE+TEST_DIRECT_CREDITCARD_CHARGE_URL;
		}
	}

	/**
	 * An Enum data type for the different modes, live mode and test mode
	 */

	public enum Mode {
		TEST, LIVE
	}
}