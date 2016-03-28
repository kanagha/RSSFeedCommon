package com.rss.common;

import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

@Component
public class RSSFeedQueueRequest {
	public List<String> URLlist;
	public String channelId;

	public RSSFeedQueueRequest() {
		URLlist = new LinkedList<String>();
	}

	public RSSFeedQueueRequest(List<String> urlList, String channelId){
		URLlist = urlList;
		this.channelId = channelId;
	}	

	public RSSFeedQueueRequest(String jsonString) throws IOException, ParseException {
		URLlist = new LinkedList<String>();
		Gson gson = new Gson();
		JsonReader reader = new JsonReader(new StringReader(jsonString));
		reader.setLenient(true);
		RSSFeedQueueRequest request = gson.fromJson(reader, RSSFeedQueueRequest.class);
		URLlist.addAll(request.URLlist);
		this.channelId = request.channelId;
	}

	public List<String> getURLList() {
		return URLlist;
	}

	public String getChannelId() {
		return channelId;
	}

	public String serializeToJSON() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}	
}