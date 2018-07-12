package com.example;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

public class TranslationByNaver {

	public static void main(String[] args){
		String apiClientId;
		String apiSecret;
		String text; // text to be translated
		String sourceLang; // original language of text
		String targetLang; // target language for translation

		/* Method: POST
		 * Request URL: https://openapi.naver.com/v1/language/translate
		 * Return type: JSON
		 * Required Parameters (1) source
		 *             (2) target
		 *             (3) text
		 */
		apiClientId="xj5MOLl9ZGYczHjKHhZw";
		apiSecret="fqOz3LCvhk";
		text="¿À´ÃÀº ¹Ù»Ú³×¿ä";
		sourceLang="ko";
		targetLang="en";

		try{
			String url = "https://openapi.naver.com/v1/language/translate";

			HttpClient client = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(url);

			// Set headers for API key authorization 
			post.addHeader("X-Naver-Client-Id", apiClientId);
			post.addHeader("X-Naver-Client-Secret",apiSecret);

			// Add parameters for POST
			List<NameValuePair> urlParams = new ArrayList<NameValuePair>();
			urlParams.add(new BasicNameValuePair("source", sourceLang));
			urlParams.add(new BasicNameValuePair("target", targetLang));
			urlParams.add(new BasicNameValuePair("text", text));

			// ÇÑ±¹¾î ÀÏº»¾î Áß±¹¾î ±úÁü ¹æÁö!
			post.setEntity(new UrlEncodedFormEntity(urlParams, "UTF-8"));

			System.out.println(post.toString());
			
			// Response received after POST. The type of the output is JSON.
			HttpResponse response = client.execute(post);

			BufferedReader rd = new BufferedReader(
					new InputStreamReader(response.getEntity().getContent(), "UTF-8"));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

			// Using json-simple to manipulate the JSON response
			String resultText = result.toString();
			Charset.forName("UTF-8").encode(resultText);
			System.out.println(resultText);
			
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(resultText);
			JSONObject message = (JSONObject) obj.get("message");

			JSONObject res = (JSONObject) message.get("result");

			String translatedText = (String) res.get("translatedText");

			System.out.println(translatedText);

		}catch(Exception e){
			e.printStackTrace();
		}


	}






}
