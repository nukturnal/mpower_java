package com.mpowerpayments.mpower;

import org.json.simple.*;

public class MPowerDirectPay extends MPowerCheckout {

  protected MPowerSetup setup;
  protected MPowerUtility utility;

  public MPowerDirectPay(MPowerSetup setup) {
    this.setup = setup;
    this.utility = new MPowerUtility(setup);
  }

  public boolean creditAccount(String payeeAccount, double amount) {
    JSONObject payload = new JSONObject();
    payload.put("account_alias",payeeAccount);
    payload.put("amount",amount);

    JSONObject result = utility.jsonRequest(setup.getDirectPayCreditUrl() ,payload.toString());

    this.responseText = result.get("response_text").toString();
    this.responseCode = result.get("response_code").toString();

    if (this.responseCode.equals("00")) {
      this.transactionId = result.get("transaction_id").toString();
      this.description = result.get("description").toString();
      this.status = this.SUCCESS;
      return true;
    }else{
      this.status = this.FAIL;
      return false;
    }
  }
}