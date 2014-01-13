package com.mpowerpayments.mpower;

import org.json.simple.*;

//class for processing the Checkout Invoice
public class MPowerCheckoutInvoice extends MPowerCheckout {

	protected	JSONObject invoice = new JSONObject();
	protected	JSONObject actions = new JSONObject();
  protected JSONObject items = new JSONObject(); 
  protected double totalAmount = 0.0;
  protected JSONObject taxes = new JSONObject();
  protected int itemsCount = 0;
  protected int taxesCount = 0;
  protected String description = null;
  protected String currency = "ghs";
  protected String cancelUrl = null;
  protected String returnUrl = null;
  protected String invoiceUrl = null;
  protected JSONObject customData = new JSONObject();
  protected String receiptUrl = null;
  protected JSONObject customer = new JSONObject();
  protected MPowerSetup setup;
  protected MPowerCheckoutStore store;
  protected MPowerUtility utility;
	
	/**
	 * Constructor for creating an instance of the Checkout Invoice with a given <code>MPowerSetup</code> and <code>MPowerCheckoutStore</code>
	 * @param  setup  An instance of <code>MPowerSetup</code>
	 * @param  store An instance of <code>MPowerCheckoutStore</code>
	 */
	public MPowerCheckoutInvoice(MPowerSetup setup, MPowerCheckoutStore store) {
		this.setup = setup;
		this.store = store;
		this.utility = new MPowerUtility(setup);
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

	public String getItems() {
		return this.items.toString();
	}

	public String getTaxes() {
		return this.taxes.toString();
	}

	public Object getCustomData(String key) {
		return this.customData.get(key);
	}

	public Object getCustomerInfo(String key) {
		return this.customer.get(key);
	}

	/**
	 * Adds new item 
	 * <p>
	 * The method creates an item of type {@link JSONObject} and adds the details to the item.
	 * @param name       The name of the item
	 * @param quantity   The quantity of the item
	 * @param unitPrice  The Unit price of the item
	 * @param totalPrice The total price of the item
	 */
	public void addItem(String name, int quantity, double unitPrice, double totalPrice) {
		JSONObject item = new JSONObject();
		item.put("name",name);
		item.put("quantity",quantity);
		item.put("unit_price",unitPrice);
		item.put("total_price",totalPrice);
		this.items.put("item_"+this.itemsCount, item);
		this.itemsCount += 1;
	}

	/**
	 * An overloaded <code>addItem</code> method with description added
	 * @param name        The name of the Item
	 * @param quantity    The quantity of the item
	 * @param unitPrice   The Unit Price of the item
	 * @param totalPrice  The total price of the item
	 * @param description The description of the item
	 * @see addItem
	 */
	public void addItem(String name, int quantity, double unitPrice, double totalPrice, String description) {
		JSONObject item = new JSONObject();
		item.put("name",name);
		item.put("quantity",quantity);
		item.put("unit_price",unitPrice);
		item.put("total_price",totalPrice);
		item.put("description",description);
		this.items.put("item_"+this.itemsCount, item);
		this.itemsCount += 1;
	}

	/**
	 * Adds tax information to the item
	 * @param name   The name of the tax
	 * @param amount The cost of the tax
	 */
	public void addTax(String name, double amount) {
		JSONObject tax = new JSONObject();
		tax.put("name",name);
		tax.put("quantity",amount);
		this.taxes.put("tax_"+this.taxesCount, tax);
		this.taxesCount += 1;
	}

	/**
	 * For adding additional information 
	 * @param key  The key to associate with the data object
	 * @param info An object of Custom data
	 */
	public void addCustomData(String key, Object info) {
		this.customData.put(key,info);
	}

	/**
	 * Creates an invoice, Returns true or false based on whether the invoice was created successfully or not.
	 * @return true if invoice is created or false if the creation failed
	 */

	public boolean create() {
		JSONObject payload = new JSONObject();

		invoice.put("items", this.items);
		invoice.put("taxes", this.taxes);
		invoice.put("total_amount", getTotalAmount());
		invoice.put("description", getDescription());
		payload.put("invoice",invoice);
		payload.put("store",store.getSettings());
		actions.put("cancel_url", getCancelUrl());
		actions.put("return_url", getReturnUrl());
		payload.put("actions",actions);

		JSONObject result = utility.jsonRequest(setup.getCheckoutInvoiceUrl()
			,payload.toString());

		this.responseText = result.get("response_text").toString();
		this.responseCode = result.get("response_code").toString();
		
		if (this.responseCode.equals("00")) {
			this.token = result.get("token").toString();
			this.responseText = result.get("description").toString();
			this.setInvoiceUrl(result.get("response_text").toString());
			this.status = this.SUCCESS;
			return true;
		}else{
			this.status = this.FAIL;
			return false;
		}
	}

	/**
	 * Checks whether the invoice is valid using the token received. if the invoice is valid it checks whether it has been paid or not.
	 * @param  token the token received 
	 * @return       true if invoice is valid and paid else false
	 */
	public Boolean confirm(String token) {
		JSONObject jsonData = utility.getRequest(setup.getCheckoutConfirmUrl()+token);
		Boolean result = false;
		if (jsonData.size() > 0) {
			if (jsonData.get("status").toString().equals(this.COMPLETED)) {
				invoice = (JSONObject)jsonData.get("invoice");
				this.status = jsonData.get("status").toString();
				this.setReceiptUrl(jsonData.get("receipt_url").toString());
				this.customData = utility.pushJSON(jsonData.get("custom_data"));
				this.customer = utility.pushJSON(jsonData.get("customer"));
				this.taxes = utility.pushJSON(invoice.get("taxes"));
				this.items = utility.pushJSON(invoice.get("items"));
				this.setTotalAmount((Double)invoice.get("total_amount"));
				this.responseText = "Checkout Invoice has been paid";
				this.responseCode = "00";
				result = true;
			}else{
				invoice = (JSONObject)jsonData.get("invoice");
				this.status = jsonData.get("status").toString();
				this.setReceiptUrl(jsonData.get("receipt_url").toString());
				this.customData = utility.pushJSON(jsonData.get("custom_data"));
				this.customer = utility.pushJSON(jsonData.get("customer"));
				this.taxes = utility.pushJSON(invoice.get("taxes"));
				this.items = utility.pushJSON(invoice.get("items"));
				this.setTotalAmount((Double)invoice.get("total_amount"));
				this.responseText = "Checkout Invoice has not been paid";
				this.responseCode = "1003";
			}
		}else{
			this.responseText = "Invoice Not Found";
			this.responseCode = "1002";
			this.status = this.FAIL;
		}
		return result;
	}
}