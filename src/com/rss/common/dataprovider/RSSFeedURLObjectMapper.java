package com.rss.common.dataprovider;

import javax.xml.bind.annotation.XmlRootElement;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
Article RSSFeedURL:
URL <unique>
etag
 */

@XmlRootElement
@DynamoDBTable(tableName = "rssfeedurl")
public class RSSFeedURLObjectMapper {
    
    String url;
    String eTag;    
    
    @DynamoDBHashKey
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url){
        this.url = url;
    }

    @DynamoDBAttribute
    public String getETag() {
        return eTag;
    }

    public void setETag(String eTag) {
        this.eTag = eTag;
    }
}