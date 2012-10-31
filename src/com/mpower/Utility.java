package com.mpower;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import org.apache.http.*;
import org.apache.http.client.methods.*;
import org.apache.http.entity.*;
import org.apache.http.impl.client.*;

public class Utility {
	Setup setup;

	public Utility(Setup setup) {
		this.setup = setup;
	}

	public String postJson(String url,String payload) {
		String output = null;
		try	{
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost request = new HttpPost(url);
			request.setHeader("User-Agent", "MPower Checkout API Java client v1 aka Don Nigalon");
			request.setHeader("mp-master-key", setup.getMasterKey());
			request.setHeader("mp-private-key", setup.getPrivateKey());
			request.setHeader("mp-public-key", setup.getPublicKey());
			request.setHeader("mp-token", setup.getToken());
			request.setHeader("mp-mode", setup.getMode());

			StringEntity input = new StringEntity(payload);
			input.setContentType("application/json");
			request.setEntity(input);

			HttpResponse response = httpClient.execute(request);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(response.getEntity().getContent())));

			
			String temp_output = null;
			while ((temp_output = br.readLine()) != null) { output += temp_output; }

			httpClient.getConnectionManager().shutdown();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return output;
	}
}