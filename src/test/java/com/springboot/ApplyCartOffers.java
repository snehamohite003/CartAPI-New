package com.springboot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.controller.ApplyOfferRequest;
import com.springboot.controller.ApplyOfferResponse;

public class ApplyCartOffers {

	HttpURLConnection con;

	public ApplyOfferRequest test(int cartValue, int restID, int usrID) {
		ApplyOfferRequest applyOfferRequest = new ApplyOfferRequest();
		applyOfferRequest.setCart_value(cartValue);
		applyOfferRequest.setRestaurant_id(restID);
		applyOfferRequest.setUser_id(usrID);
		return applyOfferRequest;
	}

	@Test
	public void checkFlatXForOneSegment() throws Exception {
		//consider user is come under segment p1 then according to the class CartOfferApplicationTests following offer will get apply
		ApplyOfferRequest applyOfferRequest = test(200, 1, 1);
		int cartValue = -1;
        HttpURLConnection httpURLConnection = aplyOfferOnCart(applyOfferRequest);
		if (httpURLConnection.getResponseCode() == 200) {
			cartValue = getCartValue(httpURLConnection);
			Assert.assertEquals(cartValue, 190);
		}
		else {
			 System.out.println("API responce is:" +httpURLConnection.getResponseCode());
			
		}
	}
	@Test
	public void checkFlatXForTwoSegment() throws Exception {
		//consider user is come under segment p2 then according to the class CartOfferApplicationTests following offer will get apply
		ApplyOfferRequest applyOfferRequest = test(200, 1, 2);
		int cartValue = -1;
        HttpURLConnection httpURLConnection = aplyOfferOnCart(applyOfferRequest);
		if (httpURLConnection.getResponseCode() == 200) {
			cartValue = getCartValue(httpURLConnection);
			Assert.assertEquals(cartValue,170 );
		}
		else {
			 System.out.println("API responce is:" +httpURLConnection.getResponseCode());
			
		}
	}
	@Test
	public void checkFlatXForThreeSegment() throws Exception {
		//consider user is come under segment p3 then according to the class CartOfferApplicationTests following offer will get apply
		ApplyOfferRequest applyOfferRequest = test(200, 1, 3);
		int cartValue = -1;
        HttpURLConnection httpURLConnection = aplyOfferOnCart(applyOfferRequest);
		if (httpURLConnection.getResponseCode() == 200) {
			cartValue = getCartValue(httpURLConnection);
			Assert.assertEquals(cartValue,0 );
		}
		else {
			 System.out.println("API responce is:" +httpURLConnection.getResponseCode());
			
		}
	}
	@Test
	public void checkResponceWhenRestIDIsNotExist() throws Exception {
		//suppose res id 200 we have not added in the system (350 res is not exist)
		ApplyOfferRequest applyOfferRequest = test(200, 350, 1);
        HttpURLConnection httpURLConnection = aplyOfferOnCart(applyOfferRequest);
        Assert.assertEquals(httpURLConnection.getResponseCode(),404 );
	}
	@Test
	public void checkResponceWhenUserIsNotExist() throws Exception {
		//suppose user id 2000 we have not added in the system
		ApplyOfferRequest applyOfferRequest = test(200, 1, 2000);
        HttpURLConnection httpURLConnection = aplyOfferOnCart(applyOfferRequest);
        Assert.assertEquals(httpURLConnection.getResponseCode(),404 );
	}
	@Test
	public void checkResponceWhenUserPassedEverythingZero() throws Exception {
		//Consider 0 as invalid input
		ApplyOfferRequest applyOfferRequest = test(0, 0, 0);
        HttpURLConnection httpURLConnection = aplyOfferOnCart(applyOfferRequest);
        Assert.assertEquals(httpURLConnection.getResponseCode(),400 );
	}
	@Test
	public void checkResponceWhenUserPassedcartValueNegative() throws Exception {
		//consider user is come under segment p1 then according to the class CartOfferApplicationTests following offer will get apply
		//suppose user id 2000 we have not added in the system
		ApplyOfferRequest applyOfferRequest = test(-100, 1, 1);
        HttpURLConnection httpURLConnection = aplyOfferOnCart(applyOfferRequest);
        Assert.assertEquals(httpURLConnection.getResponseCode(),400 );
	}
	@Test
	public void checkResponceWhenUserPassedUserIDNegative() throws Exception {
		//consider user is come under segment p1 then according to the class CartOfferApplicationTests following offer will get apply
		ApplyOfferRequest applyOfferRequest = test(100, 1, -1);
        HttpURLConnection httpURLConnection = aplyOfferOnCart(applyOfferRequest);
        Assert.assertEquals(httpURLConnection.getResponseCode(),400 );
	}
	@Test
	public void checkResponceWhenUserPassedRestIDNegative() throws Exception {
		//consider user is come under segment p1 then according to the class CartOfferApplicationTests following offer will get apply
		ApplyOfferRequest applyOfferRequest = test(100, -1, 1);
        HttpURLConnection httpURLConnection = aplyOfferOnCart(applyOfferRequest);
        Assert.assertEquals(httpURLConnection.getResponseCode(),400 );
	}
	
	
	
	
	
	
	public void checkResponceWhenUserPassedCartValueOne() throws Exception {
		//consider user is come under segment p2 then according to the class CartOfferApplicationTests following offer will get apply
		ApplyOfferRequest applyOfferRequest = test(1, 1, 2);
		double cartValue;
        HttpURLConnection httpURLConnection = aplyOfferOnCart(applyOfferRequest);
    	if (httpURLConnection.getResponseCode() == 200) {
			cartValue = getCartValue(httpURLConnection);
			Assert.assertEquals(cartValue, 0.15, 0.001);
		}
		else {
			 System.out.println("API responce is:" +httpURLConnection.getResponseCode());
			
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	public HttpURLConnection aplyOfferOnCart(ApplyOfferRequest applyOfferRequest) throws Exception {
		HttpURLConnection con;
		String urlString = "/api/v1/cart/apply_offer";
		URL url = new URL(urlString);
		con = (HttpURLConnection) url.openConnection();
		con.setDoOutput(true);
		con.setRequestProperty("Content-Type", "application/json");

		ObjectMapper mapper = new ObjectMapper();

		String POST_PARAMS = mapper.writeValueAsString(applyOfferRequest);
		OutputStream os = con.getOutputStream();
		os.write(POST_PARAMS.getBytes());
		os.flush();
		os.close();
		return con;

	}

	public int getCartValue(HttpURLConnection httpURLConnection) throws IOException {

		int cartVal=-1;
		if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(response.toString());
			// Retrieve the "cart_value" field from the JSON
			if (jsonNode.has("cart_value")) {
				cartVal = jsonNode.get("cart_value").asInt();
			}
			in.close();
		}
		return cartVal;
	}

}
