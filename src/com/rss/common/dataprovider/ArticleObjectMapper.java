package com.rss.common.dataprovider;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@XmlRootElement
@DynamoDBTable(tableName = "article")
public class ArticleObjectMapper {

	private String guid;
	private String title;
	private String link;
	private String description;
	private String publishedDate;
	// This URL will be mapping to FeedURL table
	private String feedUrl;
	
	// this is basically a local secondary index
	@DynamoDBIndexRangeKey(localSecondaryIndexName = "guid-index")
	public String getGuid() {
		return guid;
	}
	
	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	@DynamoDBAttribute
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@DynamoDBAttribute
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}

	@DynamoDBAttribute
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	// this is the range for the primary key
	@DynamoDBRangeKey
	public String getPublishedDate() {
		return publishedDate;
	}
	
	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}
	
	//@DynamoDBIndexRangeKey(globalSecondaryIndexName = "FeedURL")
	// this is the primary key
	@DynamoDBHashKey
	public String getFeedUrl() {
		return feedUrl;
	}
	
	public void setFeedUrl(String url) {
		feedUrl = url;
	}
}