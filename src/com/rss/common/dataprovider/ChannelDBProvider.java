package com.rss.common.dataprovider;

import static com.rss.common.aws.AWSDetails.DYNAMODB_MAPPER;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.rss.common.Channel;

@Component
@Scope("prototype")
public class ChannelDBProvider implements IChannelDBProvider {

    @Override
    public String addChannel(ChannelObjectMapper channel) {
        DYNAMODB_MAPPER.save(channel);
        return channel.getId();
    }

    @Override
    public void addURL(String channelId, String url) {
        ChannelObjectMapper channel = DYNAMODB_MAPPER.load(ChannelObjectMapper.class, channelId);
        channel.getUrlSet().add(url);
        DYNAMODB_MAPPER.save(channel);
    }

    @Override
    public void deleteURL(String channelId, String url) {
        ChannelObjectMapper channel = DYNAMODB_MAPPER.load(ChannelObjectMapper.class, channelId);
        channel.getUrlSet().remove(url);
        DYNAMODB_MAPPER.save(channel);
    }

    @Override
    public Channel getChannel(String channelId) {
        ChannelObjectMapper mapper =  DYNAMODB_MAPPER.load(ChannelObjectMapper.class, channelId);
        return new Channel(mapper);
    }

    @Override
    public List<Channel> getChannels(String subscriberid) {
        List<Channel> channelList = new ArrayList<Channel>();
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        Map<String, Condition> filter = new HashMap<String, Condition>();
        filter.put("userId", new Condition().withComparisonOperator(ComparisonOperator.NOT_NULL));
        scanExpression.setScanFilter(filter);
        List<ChannelObjectMapper> channelMapperList = DYNAMODB_MAPPER.scan(ChannelObjectMapper.class, scanExpression);
        for (ChannelObjectMapper mapper : channelMapperList) {
            channelList.add(new Channel(mapper));
        }
        return channelList;
    }

}
