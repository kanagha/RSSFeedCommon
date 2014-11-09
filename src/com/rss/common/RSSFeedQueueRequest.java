package com.rss.common;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RSSFeedQueueRequest {
	List<String> URLlist;
	int subscriberId;
	
	public RSSFeedQueueRequest() {
		URLlist = new LinkedList<String>();
	}
	
	public RSSFeedQueueRequest(List<String> urlList, int id){
		URLlist = urlList;
		subscriberId = id;
	}	
	
	public RSSFeedQueueRequest(String jsonString) throws IOException, ParseException {
		//JsonFactory factory = new JsonFactory();
		//JsonParser parser = factory.createJsonParser(jsonString);
		JSONParser parser =  new JSONParser();
		//JSONObject obj1 = new JSONObject(jsonString);
		Object object = parser.parse(jsonString);
		JSONObject jsonObject =(JSONObject)object;
		Long obj1 = (Long) jsonObject.get("Id");
		subscriberId = obj1.intValue();
		List<String> list = (List<String>) jsonObject.get("URLlist");
		URLlist = list;
	}
	
	public List<String> getURLList() {
		return URLlist;
	}
	
	public int getSubscriberId() {
		return subscriberId;
	}
	
	public String serializeToJSON() {
		List<String> list = new LinkedList<String>();		
		
		for(String url:  URLlist){
			list.add(url);
		}		
		JSONObject object = new JSONObject();
		object.put("Id", subscriberId);
		object.put("URLlist", list);
		String jsonString =  object.toString();
		// format the string by escaping the 
		return jsonString;
	}	
}

