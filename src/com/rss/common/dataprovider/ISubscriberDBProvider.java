package com.rss.common.dataprovider;

import java.util.List;

import org.springframework.stereotype.Component;

import com.rss.common.Subscriber;

@Component
public interface ISubscriberDBProvider {

    public String addSubscriber(String username, String emailAddress);

    public Subscriber getSubscriber(String subscriberId);

    public List<Subscriber> getSubscribers();
}
