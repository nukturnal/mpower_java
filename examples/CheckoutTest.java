import com.mpowerpayments.mpower.*;

public class CheckoutTest {
	public static void main(String args[]) throws Exception {

		// Setup your API keys and mode
		MPowerSetup apiSetup = new MPowerSetup();
		apiSetup.setMasterKey("dd6f2c90-f075-012f-5b69-00155d866600");
		apiSetup.setPrivateKey("test_private_oDLVld1eNyh0IsetdhdJvcl0ygA");
		apiSetup.setPublicKey("test_public_zzF3ywvX9DE-dSDNhUqKoaTI4wc");
		apiSetup.setToken("ca03737cf94wcf644f36"); 
		apiSetup.setMode("test");

		// Setup your store information
		MPowerCheckoutStore storeSetup = new MPowerCheckoutStore();
		storeSetup.setName("My Awesome Online storeSetup");
		storeSetup.setTagline("This is an awesome Java storeSetup.");
		storeSetup.setPhoneNumber("024000001");
		storeSetup.setPostalAddress("606 Memorylane Chokor no.1 Road.");
		storeSetup.setWebsiteUrl("http://my-awesome-long-website-url.com/");

		// Start creating an MPower Checkout
		MPowerCheckoutInvoice co = new MPowerCheckoutInvoice(apiSetup, storeSetup);
		// Add invoice items
		co.addItem("Crate of Apeteshi",2,10.00,20.00);
		co.addItem("50kg Bag of Sultana Rice",1,78.50,78.50);
		co.addItem("Book - Marriage of Anansewaa",1,10.00,10.00);

		// You will need to calculated the total amout yourself.
		co.setTotalAmount(108.50);

		// Create the invoice
		if (co.create()) {
			System.out.println("Invoice URL: "+co.getInvoiceUrl());
		}else{
			System.out.println("Error Occured: "+ co.getResponseCode());
		}
	}
}