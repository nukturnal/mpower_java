package com.mpowerpayments.mpower;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.*;

import org.json.simple.*;
import org.json.simple.parser.*;

public class Utility {
	Setup setup;

	public Utility(Setup setup) {
		this.setup = setup;
	}

	public JSONObject jsonRequest(String url,String payload) {
		String output = "";
		String temp_output;
		try {

	    URL parseUrl = new URL(url);
	    HttpURLConnection conn = (HttpURLConnection) parseUrl.openConnection();
	    conn.setDoOutput(true);
	    conn.setRequestMethod("POST");
	    conn.setRequestProperty("Content-Type", "application/json");
     	conn.setRequestProperty("Content-Length", Integer.toString(payload.length()));
	    conn.setRequestProperty("User-Agent", "MPower Checkout API Java client v1 aka Don Nigalon");
			conn.setRequestProperty("mp-master-key", setup.getMasterKey());
			conn.setRequestProperty("mp-private-key", setup.getPrivateKey());
			conn.setRequestProperty("mp-public-key", setup.getPublicKey());
			conn.setRequestProperty("mp-token", setup.getToken());
			conn.setRequestProperty("mp-mode", setup.getMode());
 
	    OutputStream os = conn.getOutputStream();
	    os.write(payload.getBytes("UTF-8"));
	    os.flush();

 			int status = conn.getResponseCode();

    if (status != HttpURLConnection.HTTP_CREATED || status != HttpURLConnection.HTTP_OK) {
      throw new RuntimeException("Failed : HTTP error code : "
        + conn.getResponseCode());
    }
 
    BufferedReader br = new BufferedReader(new InputStreamReader(
      (conn.getInputStream())));
 
		while ((temp_output = br.readLine()) != null) { output += temp_output; }
    conn.disconnect();
    } catch (MalformedURLException e) {
 
    e.printStackTrace();
 
    } catch (IOException e) {
 
    e.printStackTrace();
 
   }

		return this.jsonParse(output);
	}

	public JSONObject getRequest(String url) {
		String output = "";
		String temp_output;
		try {
 
			URL parseUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) parseUrl.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/json");
	    conn.setRequestProperty("User-Agent", "MPower Checkout API Java client v1 aka Don Nigalon");
			conn.setRequestProperty("mp-master-key", setup.getMasterKey());
			conn.setRequestProperty("mp-private-key", setup.getPrivateKey());
			conn.setRequestProperty("mp-public-key", setup.getPublicKey());
			conn.setRequestProperty("mp-token", setup.getToken());
			conn.setRequestProperty("mp-mode", setup.getMode());
	 
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
	 
			BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));
	 
			while ((temp_output = br.readLine()) != null) { output += temp_output; }
	 
			conn.disconnect();
 
	  } catch (MalformedURLException e) { 
			e.printStackTrace();
	  } catch (IOException e) {
			e.printStackTrace();
	  }
	  return jsonParse(output);
	}

	static public JSONObject jsonParse(String s) {
		Object value = null;
		try{
			JSONParser parser = new JSONParser();
			value = parser.parse(s);
		} catch(ParseException e) {
			e.printStackTrace();
		}
		return (JSONObject)value;
	}

	public JSONObject pushJSON(String jsonText) {
		return pushJSONObject(jsonText);
	}

	public JSONObject pushJSON(Object jsonText) {
		String str;
		try{
			str = jsonText.toString();
		}catch(NullPointerException exp) {
			str = "";
		} 
		return pushJSONObject(str);
	}

	public JSONObject pushJSONObject(String jsonText) {
		JSONObject data = new JSONObject();
		JSONParser parser = new JSONParser();
  	ContainerFactory containerFactory = new ContainerFactory(){

	    public List creatArrayContainer() {
	      return new LinkedList();
	    }

	    public Map createObjectContainer() {
	      return new LinkedHashMap();
	    }                    
  };
      
  try{
    Map json = (Map)parser.parse(jsonText, containerFactory);
    Iterator iter = json.entrySet().iterator();

    while(iter.hasNext()){
      Map.Entry entry = (Map.Entry)iter.next();
      data.put(entry.getKey(),entry.getValue());
    }
  }
  catch(ParseException e) {}
  catch(NullPointerException exp) {}
  return data;
	}
}