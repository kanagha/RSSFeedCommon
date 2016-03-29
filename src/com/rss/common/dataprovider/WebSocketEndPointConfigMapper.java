package com.rss.common.dataprovider;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 *  WebSocketEndPoint table:
 *  id (auto-generated)
 *  channelId
 *  stomp endpoint
 */

@XmlRootElement
@DynamoDBTable(tableName = "websocketEndPointConfig")
@Configuration
public class WebSocketEndPointConfigMapper {

	private String id;
	private String channelId;
	private String stompEndPoint;

	public WebSocketEndPointConfigMapper() {
	}
	
	@DynamoDBHashKey
	@DynamoDBAutoGeneratedKey
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@DynamoDBAttribute
	public String getChannelId() {
		return channelId;
	}
	
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	@DynamoDBAttribute
	public String getStompEndPoint() {
		return stompEndPoint;
	}
	
	public void setStompEndPoint(String stompEndPoint) {
		this.stompEndPoint = stompEndPoint;
	}
}
