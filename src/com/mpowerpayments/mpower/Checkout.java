package com.mpowerpayments.mpower;

public class Checkout {
	public String responseText;
	public String responseCode;
	public String status;

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
}