package com.mpowerpayments.mpower;

import org.json.simple.*;

//class for processing charge on direct card.
public class MPowerDirectCard extends MPowerCheckout {

  protected MPowerSetup setup;
  protected MPowerUtility utility;
  public String unityTransactionId;

  public MPowerDirectCard(MPowerSetup setup) {
    this.setup = setup;
    this.utility = new MPowerUtility(setup);
  }

  /**
   * Gets the information about the card and debit <code>amount</code> from the card.
   * @param  amount     Amount to debit
   * @param  cardName   The name of the card
   * @param  cardNumber The number of the card
   * @param  cardCVC    The Card Verification Code(CVC)
   * @param  expMonth   The monnth the card will expire
   * @param  expYear    The year the card will expire
   * @return            true if operation is successful and false if the operation is not successful
   */

  public boolean charge(double amount, String cardName, String cardNumber, String cardCVC, String expMonth, String expYear) {
    JSONObject payload = new JSONObject();
    payload.put("card_name",cardName);
    payload.put("card_number",cardNumber);
    payload.put("card_cvc",cardCVC);
    payload.put("exp_month",expMonth);
    payload.put("exp_year",expYear);
    payload.put("amount",amount);

    JSONObject result = utility.jsonRequest(setup.getDirectCreditcardChargeUrl() ,payload.toString());

    this.responseText = result.get("response_text").toString();
    this.responseCode = result.get("response_code").toString();

    if (this.responseCode.equals("00")) {
      this.transactionId = result.get("transaction_id").toString();
      this.unityTransactionId = result.get("unity_transaction_id").toString();
      this.description = result.get("description").toString();
      this.status = this.SUCCESS;
      return true;
    }else{
      this.status = this.FAIL;
      return false;
    }
  }

  public String getUnityTransactionId() {
    return this.unityTransactionId;
  }
}