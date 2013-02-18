package com.mpowerpayments.mpower;

public class MPowerCheckout {
	public String responseText;
	public String responseCode;
	public String status;
  public String token;
  public String transactionId;
  public String description;

  public static String SUCCESS = "success";
  public static String FAIL = "fail";
  public static String COMPLETED = "completed";
  public static String PENDING = "pending";

  public String getStatus() {
    return status;
  }

  public String getResponseText() {
    return responseText;
  }

  public String getResponseCode() {
    return responseCode;
  }

  public String getToken() {
    return this.token;
  }

  public String getTransactionId() {
    return this.transactionId;
  }

  public String getDescription() {
    return this.description;
  }
}