package com.rss.common.dataprovider;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@XmlRootElement
@DynamoDBTable(tableName = "jobrequest")
public class JobRequestMapper {
    
    // For now, it includes all the urls in a given job
    private String jobId;
    private Date requestTimestamp;
    private String channelId;
    private String quartzJobId;
    
    @DynamoDBAutoGeneratedKey
    public String getJobId(){
        return jobId;
    }
    
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }    

    @DynamoDBAttribute
    public Date getRequestTimestamp() {
        return requestTimestamp;
    }

    public void setRequestTimestamp(Date requestTimestamp) {
        this.requestTimestamp = requestTimestamp;
    }    
    
    @DynamoDBHashKey
    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    @DynamoDBAttribute
    public void setQuartzJobId(String quartzJobId) {
        this.quartzJobId = quartzJobId;
    }
    
    public String getQuartzJobId() {
        return quartzJobId;
    }
}