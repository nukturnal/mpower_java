mpower-java
===========

MPower Payments Java Client Library

## Installation

Add MPowerPaments jar file to your classpath

    mpowerpayments-version.jar

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
Please note that MPowerCheckoutInvoice Class requires two parameters which should be instances of MPowerSetup & MPowerStore respectively

    MPowerCheckoutInvoice co = new MPowerCheckoutInvoice(apiSetup, storeSetup);

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

    if(co->create()) {
       System.out.println("Invoice URL: "+co.getInvoiceUrl());
    }else{
      System.out.println("Error Occured: "+ co.getResponseCode());
    }

## Contributing

1. Fork it
2. Create your feature branch (`git checkout -b my-new-feature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin my-new-feature`)
5. Create new Pull Request