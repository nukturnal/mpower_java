MPower Java API Client
======================
MPower Payments Java Client Library

## Offical Documentation
http://mpowerpayments.com/developers/docs/java.html

## Installation

Add MPowerPaments jar file to your classpath

    mpower_latest.jar

## Setup your API Keys

    MPowerSetup apiSetup = new MPowerSetup();
    apiSetup.setMasterKey("dd6f2c90-f075-012f-5b69-00155d866600");
    apiSetup.setPrivateKey("test_private_oDLVld1eNyh0IsetdhdJvcl0ygA");
    apiSetup.setPublicKey("test_public_zzF3ywvX9DE-dSDNhUqKoaTI4wc");
    apiSetup.setToken("ca03737cf94wcf644f36"); 
    apiSetup.setMode("test|live");

## Setup your checkout store information

    MPowerCheckoutStore storeSetup = new MPowerCheckoutStore();
    storeSetup.setName("My Awesome Online storeSetup");
    storeSetup.setTagline("This is an awesome Java storeSetup.");
    storeSetup.setPhoneNumber("024000001");
    storeSetup.setPostalAddress("606 Memorylane Chokor no.1 Road.");
    storeSetup.setWebsiteUrl("http://my-awesome-long-website-url.com/");

Customer will be redirected back to this URL when he cancels the checkout on MPower website

    storeSetup.setCancelUrl("CHECKOUT_CANCEL_URL");

MPower will automatically redirect customer to this URL after successfull payment.
This setup is optional and highly recommended you dont set it, unless you want to customize the payment experience of your customers.
When a returnURL is not set, MPower will redirect the customer to the receipt page.

    storeSetup.setReturnUrl(CHECKOUT_RETURN_URL);

## Create your Checkout Invoice
Please note that `MPowerCheckoutInvoice` Class requires two parameters which should be instances of MPowerSetup & MPowerStore respectively

    MPowerCheckoutInvoice co = new MPowerCheckoutInvoice(apiSetup, storeSetup);

## Create your Onsite Payment Request Invoice
Please note that `MPowerOnsiteInvoice` Class requires two parameters which should be instances of MPowerSetup & MPowerStore respectively

    MPowerOnsiteInvoice co = new MPowerOnsiteInvoice(apiSetup, storeSetup);

Params for addItem function `addItem(name_of_item,quantity,unit_price,total_price)`

    co.addItem("Crate of Apeteshi",2,10.00,20.00);
    co.addItem("50kg Bag of Sultana Rice",1,78.50,78.50);
    co.addItem("Book - Marriage of Anansewaa",1,10.00,10.00);

## Set the total amount to be charged ! Important

    co.setTotalAmount(108.50);

## Setup Tax Information (Optional)

    co.addTax("VAT (15)",50);
    co.addTax("NHIL (10)",50);

## You can add custom data to your invoice which can be called back later

    co.setCustomData("Firstname","Alfred");
    co.setCustomData("Lastname","Rowe");
    co.setCustomData("CartId",929292872);

## Pushing invoice to MPower server and getting your URL

    if(co.create()) {
       System.out.println("Invoice URL: "+co.getInvoiceUrl());
    }else{
      System.out.println("Error Occured: "+ co.getResponseCode());
    }

## Onsite Payment Request(OPR) Charge
First step is to take the customers mpower account alias, this could be the phoneno, username or mpower account number.
pass this as a param for the `create` action of the `MPower::Onsite::Invoice` class instance. MPower will return an OPR TOKEN after the request is successfull. The customer will also receieve a confirmation TOKEN.
        
    if(co.create("CUSTOMER_MPOWER_USERNAME_OR_PHONE")) {
      System.out.println(co.responseText);
      System.out.println("OPR Token: "+co.token);
    }else{
      System.out.println("Error Message: "+co.responseText);
    }

Second step requires you to accept the confirmation TOKEN from the customer, add your OPR Token and issue the charge. Upon successfull charge you should be able to access the digital receipt URL and other objects outlined in the offical docs.

    if(co.charge("OPR_TOKEN","CUSTOMER_CONFIRM_TOKEN")) {
      System.out.println(co.responseText);
      System.out.println("Receipt URL: "+co.getReceiptUrl());
    }else{
      System.out.println("Error Message: "+co.responseText);
    }

## DirectPay Request
You can pay any MPower account directly via your third party apps. This is particularly excellent for implementing your own Adaptive payment solutions ontop of MPower. Please note that `MPowerDirectPay` Class expects one parameter which should be an instance of the MPowerSetup Class

    MPowerDirectPay direct_pay = new MPowerDirectPay(apiSetupInstance);
    if(direct_pay.creditAccount("MPOWER_CUSTOMER_USERNAME_OR_PHONENO",50)){
        System.out.println("Status: "+direct_pay.getStatus());
        System.out.println("Descripion: "+direct_pay.getDescription());
        System.out.println("Transaction ID: "+direct_pay.getTransactionId());
    }else{
        System.out.println("Status: "+direct_pay.getStatus());
        System.out.println("Response Message: "+direct_pay.getResponseText());
    }

## Download MPower Java Demo
https://github.com/nukturnal/MPower_Java_Example

## Contributing

1. Fork it
2. Create your feature branch (`git checkout -b my-new-feature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin my-new-feature`)
5. Create new Pull Request