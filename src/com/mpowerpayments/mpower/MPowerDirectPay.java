package com.mpowerpayments.mpower;

import org.json.simple.*;

//class for processing direct payment to MPower accounts 
public class MPowerDirectPay extends MPowerCheckout {

  protected MPowerSetup setup;
  protected MPowerUtility utility;

  /**
   * Constructor that accepts an instance of <code>MPowerSetup</code>
   * @param  setup an instance of <code>MpowerSetup</code>
   */
  public MPowerDirectPay(MPowerSetup setup) {
    this.setup = setup;
    this.utility = new MPowerUtility(setup);
  }

  /**
   * Adds a specified amount to an MPower account
   * @param  payeeAccount The account alias of the person who the amount is being payed
   * @param  amount       The amount being paid
   * @return              true if transaction is successful and false when the transaction fails.
   */

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