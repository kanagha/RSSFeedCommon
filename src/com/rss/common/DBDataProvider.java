package com.rss.common;

import static com.rss.common.aws.AWSDetails.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.rss.common.dataprovider.ArticleObjectMapper;
import com.rss.common.dataprovider.RSSFeedURLObjectMapper;
import com.rss.common.dataprovider.WebSocketEndPointConfigMapper;

public class DBDataProvider {
	
	static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	
	public static void addWebsocketEndPoint(String stompEndPoint, String channelId) {
		WebSocketEndPointConfigMapper mapper = new WebSocketEndPointConfigMapper();
		mapper.setChannelId(channelId);
		mapper.setStompEndPoint(stompEndPoint);
		DYNAMODB_MAPPER.save(mapper);
	}
	
	public static void deleteWebsocketEndPoint(String webSocketEndPointConfigId) {
		WebSocketEndPointConfigMapper endPointConfig = DYNAMODB_MAPPER.load(WebSocketEndPointConfigMapper.class, webSocketEndPointConfigId);
		if (endPointConfig != null) {
			DYNAMODB_MAPPER.delete(endPointConfig);
		}
	}
	
	//TODO: Fetch stomp endpoint for the given channel
	public static String getStompEndPoint(String channelId) {
		return null;
	}

	public static void addArticles(Article article, String rssFeedURL) {
		ArticleObjectMapper articleObj = new ArticleObjectMapper();
		articleObj.setGuid(article.guid);
		articleObj.setTitle(article.title);
		articleObj.setLink(article.link);
		articleObj.setPublishedDate(article.publishedDate);
		articleObj.setDescription(article.description);
		articleObj.setFeedUrl(rssFeedURL);
		DYNAMODB_MAPPER.save(articleObj);
	}
	
	public static List<ArticleObjectMapper> getArticles(String rssFeedURL) {
		// need to write it with Condition and DynamoDBQueryExpression
		// Refer http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/JavaQueryScanORMModelExample.html
		/*DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		Map<String, Condition> filter = new HashMap<String, Condition>();
		filter.put("feedUrl", new Condition().withComparisonOperator(ComparisonOperator.EQ)).withAttributeValueList(new AttributeValue().withS(rssFeedURL));
		scanExpression.setScanFilter(filter);
		return DYNAMODB_MAPPER.scan(ArticleObjectMapper.class, scanExpression);*/

		long oneDayAgoMilli = (new Date()).getTime() - (1L*24L*60L*60L*1000L);
        Date oneDayAgo = new Date();
        oneDayAgo.setTime(oneDayAgoMilli);
        dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        String oneDayAgoStr = dateFormatter.format(oneDayAgo);

		Condition rangeKeyCondition = new Condition().withComparisonOperator(ComparisonOperator.GT.toString())
				.withAttributeValueList(new AttributeValue().withS(oneDayAgoStr.toString()));

		ArticleObjectMapper objectMapper = new ArticleObjectMapper();
		objectMapper.setFeedUrl(rssFeedURL);
		DynamoDBQueryExpression<ArticleObjectMapper> queryExpression = new DynamoDBQueryExpression<ArticleObjectMapper>()
	            .withHashKeyValues(objectMapper)
	            .withRangeKeyCondition("publishedDate", rangeKeyCondition);
		QueryResultPage<ArticleObjectMapper> resultPages = DYNAMODB_MAPPER.queryPage(ArticleObjectMapper.class, queryExpression);
		Map<String, AttributeValue> map = resultPages.getLastEvaluatedKey();
		if (map != null) {
			for(String key : map.keySet()) {
				System.out.println("Key : " + key + " value : " + map.get(key));
			}
		}		
		return resultPages.getResults();
	}
	
	public static void addFeedURL(String rssFeedURL, String eTag) {
		RSSFeedURLObjectMapper rssFeedObj = new RSSFeedURLObjectMapper();
		rssFeedObj.setETag(eTag);
		rssFeedObj.setUrl(rssFeedURL);
		DYNAMODB_MAPPER.save(rssFeedObj);
	}
	
	public static String getETagForFeedURL(String rssFeedURL) {
		RSSFeedURLObjectMapper rssFeedObj = DYNAMODB_MAPPER.load(RSSFeedURLObjectMapper.class, rssFeedURL);
		if (rssFeedObj != null) {
			return rssFeedObj.getETag();
		}
		return null;		
	}
}