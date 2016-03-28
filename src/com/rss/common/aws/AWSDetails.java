package com.rss.common.aws;
/*
 * Copyright 2012-2013 Amazon Technologies, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *    http://aws.amazon.com/apache2.0
 *
 * This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and
 * limitations under the License.
 */
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.util.Tables;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.util.StringUtils;

/**
 * Configuration for AWS resources. Run this class to create your resources
 */
public class AWSDetails {

    public static final String SQS_ADDJOB_GETFEEDS_QUEUE = "rss-addjob-getfeeds-queue";
    public static final String SQS_GETFEEDS_QUEUE = "rss-getfeeds-queue";
    public static final String SQS_WEBSERVICE_REQUEST_QUEUE = "web-service-request";
    
    public static final String JOBREQUEST_TABLE_NAME = "jobrequest";
    public static final String FEEDURL_REQUEST_TABLE_NAME = "feedurl-request";
    public static final String SUBSCRIBER_TABLE_NAME = "subscriber";
    public static final String CHANNEL_TABLE_NAME = "channel";
    public static final String FEED_URL_TABLE_NAME = "rssfeedurl";
    public static final String ARTICLE_TABLE_NAME = "article";
    public static final String WEBSOCKET_CONFIG_TABLE_NAME = "websocketEndPointConfig";

    /*
     * The SDK provides several easy to use credentials providers.
     * Here we're loading our AWS security credentials from a properties
     * file on our classpath.
     */
    public static final AWSCredentialsProvider CREDENTIALS_PROVIDER =
            new ClasspathPropertiesFileCredentialsProvider();

    /*
     * This controls the AWS region used for created resources. You can easily
     * deploy applications in any or all of the AWS regions around the world,
     * allowing you to provide a lower latency and better experience for your
     * customers.
     */
    public static final Region REGION = Region.getRegion(Regions.US_WEST_2);

    /*
     * We construct our clients to access AWS here, so that we can share them
     * easily throughout our application.
     */
    public static final AmazonS3Client S3 = new AmazonS3Client(CREDENTIALS_PROVIDER);
    public static final AmazonSQSClient SQS = new AmazonSQSClient(CREDENTIALS_PROVIDER);
    public static final AmazonDynamoDBClient DYNAMODB = new AmazonDynamoDBClient(CREDENTIALS_PROVIDER);
    public static final DynamoDBMapper DYNAMODB_MAPPER = new DynamoDBMapper(DYNAMODB, CREDENTIALS_PROVIDER);


    static {
        /*
         * Set any other client options that you need here. For example, if you
         * connect to the internet through a proxy, then call setConfiguration
         * and pass in a ClientConfiguration object with your proxy settings.
         *
         * Here we set our region, so that we can keep our data located in the
         * same region.
         */
        DYNAMODB.setRegion(REGION);
        SQS.setRegion(REGION);
    }


    public static void main(String[] args) {
        String queueUrl = SQS.createQueue(new CreateQueueRequest(SQS_ADDJOB_GETFEEDS_QUEUE)).getQueueUrl();
        System.out.println("Using Amazon SQS Queue: " + queueUrl);
        
        String publisherQueueUrl = SQS.createQueue(new CreateQueueRequest(SQS_GETFEEDS_QUEUE)).getQueueUrl();
        System.out.println("Using Amazon SQS Queue: " + publisherQueueUrl);
        
        String webserviceRequestQueueUrl = SQS.createQueue(new CreateQueueRequest(SQS_WEBSERVICE_REQUEST_QUEUE)).getQueueUrl();
        System.out.println("Using Amazon SQS Queue: " + webserviceRequestQueueUrl);

        if ( !Tables.doesTableExist(DYNAMODB, JOBREQUEST_TABLE_NAME) ) {
            System.out.println("Creating job request table...");
            DYNAMODB.createTable(new CreateTableRequest()
                    .withTableName(JOBREQUEST_TABLE_NAME)
                    .withKeySchema(new KeySchemaElement("id", KeyType.HASH))
                    .withAttributeDefinitions(new AttributeDefinition("id", ScalarAttributeType.S))
                    .withProvisionedThroughput(new ProvisionedThroughput(50l, 50l)));
        }
        if ( !Tables.doesTableExist(DYNAMODB, FEEDURL_REQUEST_TABLE_NAME) ) {
            System.out.println("Creating feed url request table...");
            DYNAMODB.createTable(new CreateTableRequest()
                    .withTableName(FEEDURL_REQUEST_TABLE_NAME)
                    .withKeySchema(new KeySchemaElement("id", KeyType.HASH))
                    .withAttributeDefinitions(new AttributeDefinition("id", ScalarAttributeType.S))
                    .withProvisionedThroughput(new ProvisionedThroughput(50l, 50l)));
        }
        if ( !Tables.doesTableExist(DYNAMODB, SUBSCRIBER_TABLE_NAME) ) {
            System.out.println("Creating subscriber table...");
            DYNAMODB.createTable(new CreateTableRequest()
                    .withTableName(SUBSCRIBER_TABLE_NAME)
                    .withKeySchema(new KeySchemaElement("id", KeyType.HASH))
                    .withAttributeDefinitions(new AttributeDefinition("id", ScalarAttributeType.S))
                    .withProvisionedThroughput(new ProvisionedThroughput(50l, 50l)));
        }
        if ( !Tables.doesTableExist(DYNAMODB, CHANNEL_TABLE_NAME) ) {
            System.out.println("Creating channel table....");
            DYNAMODB.createTable(new CreateTableRequest()
                    .withTableName(CHANNEL_TABLE_NAME)
                    .withKeySchema(new KeySchemaElement("id", KeyType.HASH))
                    .withAttributeDefinitions(new AttributeDefinition("id", ScalarAttributeType.S))
                    .withProvisionedThroughput(new ProvisionedThroughput(50l, 50l)));
        }
        if ( !Tables.doesTableExist(DYNAMODB, FEED_URL_TABLE_NAME) ) {
            System.out.println("Creating feedurl table....");
            DYNAMODB.createTable(new CreateTableRequest()
                    .withTableName(FEED_URL_TABLE_NAME)
                    .withKeySchema(new KeySchemaElement("url", KeyType.HASH))
                    .withAttributeDefinitions(new AttributeDefinition("url", ScalarAttributeType.S))
                    .withProvisionedThroughput(new ProvisionedThroughput(50l, 50l)));
        }
        List<KeySchemaElement> keySchemaElements = new ArrayList<KeySchemaElement>();
        keySchemaElements.add(new KeySchemaElement("feedUrl", KeyType.HASH));
        keySchemaElements.add(new KeySchemaElement("publishedDate", KeyType.RANGE));
        if ( !Tables.doesTableExist(DYNAMODB, ARTICLE_TABLE_NAME) ) {
            System.out.println("Creating article table....");
            DYNAMODB.createTable(new CreateTableRequest()
                    .withTableName(ARTICLE_TABLE_NAME)
                    .withKeySchema(keySchemaElements)
                    //.withKeySchema(new KeySchemaElement("guid", KeyType.HASH))
                    .withAttributeDefinitions(new AttributeDefinition("feedUrl", ScalarAttributeType.S))
                    .withAttributeDefinitions(new AttributeDefinition("publishedDate", ScalarAttributeType.S))
                    .withProvisionedThroughput(new ProvisionedThroughput(50l, 50l)));
        }
        
        if ( !Tables.doesTableExist(DYNAMODB, WEBSOCKET_CONFIG_TABLE_NAME) ) {
            System.out.println("Creating websocket endpoint config table....");
            DYNAMODB.createTable(new CreateTableRequest()
                    .withTableName(WEBSOCKET_CONFIG_TABLE_NAME)
                    .withKeySchema(keySchemaElements)
                    .withAttributeDefinitions(new AttributeDefinition("id", ScalarAttributeType.S))
                    .withProvisionedThroughput(new ProvisionedThroughput(50l, 50l)));
        }
        
        Tables.waitForTableToBecomeActive(DYNAMODB, JOBREQUEST_TABLE_NAME);
        Tables.waitForTableToBecomeActive(DYNAMODB, FEEDURL_REQUEST_TABLE_NAME);
        Tables.waitForTableToBecomeActive(DYNAMODB, SUBSCRIBER_TABLE_NAME);
        Tables.waitForTableToBecomeActive(DYNAMODB, CHANNEL_TABLE_NAME);
        Tables.waitForTableToBecomeActive(DYNAMODB, FEED_URL_TABLE_NAME);
        Tables.waitForTableToBecomeActive(DYNAMODB, ARTICLE_TABLE_NAME);
        Tables.waitForTableToBecomeActive(DYNAMODB, WEBSOCKET_CONFIG_TABLE_NAME);
        System.out.println("Using AWS DynamoDB Table: " + StringUtils.join(",", JOBREQUEST_TABLE_NAME, FEEDURL_REQUEST_TABLE_NAME, SUBSCRIBER_TABLE_NAME,
        		CHANNEL_TABLE_NAME, FEED_URL_TABLE_NAME, ARTICLE_TABLE_NAME, WEBSOCKET_CONFIG_TABLE_NAME));
    }	
}
