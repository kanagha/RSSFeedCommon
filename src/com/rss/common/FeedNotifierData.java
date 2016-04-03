package com.rss.common;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;

/**
 * Class containing data for latest feeds fetched for urls
 * This will be helpful in publishing the latest titles for the feeds
 * to the clients.
 * And if clients are interested in entire feeds data,
 * they can use REST endpoints.
 * @author Kanagha
 *
 */
public class FeedNotifierData implements Serializable {
    public String channelId;
    
    public List<String> articleTitles;

    public FeedNotifierData(String channelId, List<String> titles) {
        this.channelId = channelId;
        this.articleTitles = titles;
    }

    public FeedNotifierData(String gsonString) {
        Gson gson = new Gson();
        FeedNotifierData data = gson.fromJson(gsonString, FeedNotifierData.class);
        channelId = data.channelId;
        articleTitles = data.articleTitles;
    }

    public String serializeToJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}