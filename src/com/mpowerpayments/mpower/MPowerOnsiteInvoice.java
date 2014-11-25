package com.mpowerpayments.mpower;

import org.json.simple.*;

public class MPowerOnsiteInvoice extends MPowerCheckoutInvoice {

	public String invoiceToken;
	public MPowerOnsiteInvoice(MPowerSetup setup, MPowerCheckoutStore store) {
		super(setup, store);
	}

	public boolean create(String accountAlias) {
		JSONObject invoice_data = new JSONObject();
		JSONObject opr_data = new JSONObject();
		JSONObject payload = new JSONObject();
		JSONObject invoice = new JSONObject();
		JSONObject actions = new JSONObject();

		invoice.put("items", this.items);
		invoice.put("taxes", this.taxes);
		invoice.put("total_amount", getTotalAmount());
		invoice.put("description", getDescription());
		invoice_data.put("invoice",invoice);
		invoice_data.put("store",store.getSettings());
		actions.put("cancel_url", getCancelUrl());
		actions.put("return_url", getReturnUrl());
		invoice_data.put("actions",actions);

		opr_data.put("account_alias",accountAlias);

		payload.put("invoice_data",invoice_data);
		payload.put("opr_data",opr_data);
		payload.put("custom_data",this.customData);

		JSONObject result = utility.jsonRequest(setup.getOPRInvoiceUrl()
			,payload.toString());

		this.responseText = result.get("response_text").toString();
		this.responseCode = result.get("response_code").toString();
		
		if (this.responseCode.equals("00")) {
			this.token = result.get("token").toString();
			this.invoiceToken = result.get("invoice_token").toString();
			this.responseText = result.get("description").toString();
			this.status = this.SUCCESS;
			return true;
		}else{
			this.status = this.FAIL;
			return false;
		}
	}

	public Boolean charge(String oprToken, String confirmToken) {
		JSONObject payload = new JSONObject();
		payload.put("token", oprToken);
		payload.put("confirm_token",confirmToken);

		JSONObject jsonResult = utility.jsonRequest(setup.getOPRChargeUrl() ,payload.toString());
		JSONObject invoice_data = new JSONObject();
		Boolean result = false;
		if (jsonResult.size() > 0) {
			if (jsonResult.get("response_code").equals("00")) {
				invoice_data = (JSONObject)jsonResult.get("invoice_data");
				invoice = (JSONObject)invoice_data.get("invoice");
				this.status = invoice_data.get("status").toString();
				this.setReceiptUrl(invoice_data.get("receipt_url").toString());
				this.customData = utility.pushJSON(invoice_data.get("custom_data"));
				this.customer = utility.pushJSON(invoice_data.get("customer"));
				this.taxes = utility.pushJSON(invoice.get("taxes"));
				this.items = utility.pushJSON(invoice.get("items"));
				this.setTotalAmount((Double)invoice.get("total_amount"));

				this.responseText = jsonResult.get("response_text").toString();
				this.responseCode = "00";
				result = true;
			}else{
				this.status = this.FAIL;
				this.responseText = jsonResult.get("response_text").toString();
				this.responseCode = jsonResult.get("response_code").toString();
			}
		}else{
			this.responseText = "An Unknown MPower Server Error Occured.";
			this.responseCode = "4002";
			this.status = this.FAIL;
		}
		return result;
	}
}