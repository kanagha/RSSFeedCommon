package com.rss.common.dataprovider;

import java.util.List;

import org.springframework.stereotype.Component;

import com.rss.common.Channel;

@Component
public interface IChannelDBProvider {

    public String addChannel(ChannelObjectMapper channel);

    public void addURL(String channelId, String url);

    public void deleteURL(String channelId, String url);

    public Channel getChannel(String channelId);

    public List<Channel> getChannels(String subscriberid);
}
