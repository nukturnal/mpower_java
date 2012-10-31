package com.mpower;
import org.json.*;

public class CheckoutInvoice extends Checkout {

	private JSONObject items = new JSONObject();
  private double totalAmount = 0.0;
  private JSONObject taxes = new JSONObject();
  private int itemsCount = 0;
  private int taxesCount = 0;
  private String description = null;
  private String currency = "ghs";
  private String cancelUrl = null;
  private String returnUrl = null;
  private String invoiceUrl = null;
  private JSONObject customData = new JSONObject();
  private String receiptUrl = null;
  private JSONObject customer = new JSONObject();
  private Setup setup;
  private CheckoutStore store;
  private Utility utility;
	
	public CheckoutInvoice(Setup setup, CheckoutStore store) {
		this.setup = setup;
		this.store = store;
		this.utility = new Utility(setup);
		this.cancelUrl = store.getCancelUrl();
		this.returnUrl = store.getReturnUrl();
	}

	public void setTotalAmount(double amount) {
		this.totalAmount = amount;
	}

	public double getTotalAmount(){
		return this.totalAmount;
	}

	public void setReturnUrl(String url) {
		this.returnUrl = url;
	}

	public String getReturnUrl() {
		return this.returnUrl;
	}

	public void setCancelUrl(String url) {
		this.cancelUrl = url;
	}

	public String getCancelUrl() {
		return this.cancelUrl;
	}

	public void setInvoiceUrl(String url) {
		this.invoiceUrl = url;
	}

	public String getInvoiceUrl() {
		return this.invoiceUrl;
	}

	public void setReceiptUrl(String url) {
		this.receiptUrl = url;
	}

	public String getReceiptUrl() {
		return this.receiptUrl;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public String getItems() throws JSONException {
		return this.items.toString(2);
	}

	public String getTaxes() throws JSONException {
		return this.taxes.toString(2);
	}

	public Object getCustomData(String key) throws JSONException {
		return this.customData.get(key);
	}

	public void addItem(String name, int quantity, double unitPrice, double totalPrice) throws JSONException {
		JSONObject item = new JSONObject();
		item.put("name",name);
		item.put("quantity",quantity);
		item.put("unit_price",unitPrice);
		item.put("total_price",totalPrice);
		this.items.put("item_"+this.itemsCount, item);
		this.itemsCount += 1;
	}

	public void addItem(String name, int quantity, double unitPrice, double totalPrice, String description) throws JSONException {
		JSONObject item = new JSONObject();
		item.put("name",name);
		item.put("quantity",quantity);
		item.put("unit_price",unitPrice);
		item.put("total_price",totalPrice);
		item.put("description",description);
		this.items.put("item_"+this.itemsCount, item);
		this.itemsCount += 1;
	}

	public void addTax(String name, double amount) throws JSONException {
		JSONObject tax = new JSONObject();
		tax.put("name",name);
		tax.put("quantity",amount);
		this.taxes.put("tax_"+this.taxesCount, tax);
		this.taxesCount += 1;
	}

	public void addCustomData(String key, Object info) throws JSONException {
		this.customData.put(key,info);
	}

	public String create() throws JSONException {
		JSONObject payload = new JSONObject();
		JSONObject invoice = new JSONObject();
		JSONObject actions = new JSONObject();

		invoice.put("items", this.items);
		invoice.put("taxes", this.taxes);
		invoice.put("total_amount", getTotalAmount());
		invoice.put("description", getDescription());
		payload.put("invoice",invoice);
		payload.put("store",store.getSettings());
		actions.put("cancel_url", getCancelUrl());
		actions.put("return_url", getReturnUrl());
		payload.put("actions",actions);

		return this.utility.postJson(setup.getCheckoutInvoiceUrl(),payload.toString());
	}
}