package com.rss.common;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.rss.common.dataprovider.ArticleObjectMapper;

@Component
@Scope("prototype")
public class Article implements Serializable {
    // this is supposed to be the global unique identifier
    public String guid;
    public String title;
    public String link;
    public String description;
    public String publishedDate;

    public Article() {

    }

    public Article(ArticleObjectMapper articleObjectMapper) {
        guid = articleObjectMapper.getGuid();
        title = articleObjectMapper.getTitle();
        link = articleObjectMapper.getLink();
        description = articleObjectMapper.getDescription();
        publishedDate = articleObjectMapper.getPublishedDate();
    }

    public Article(String articleJsonString) {
        Gson gson = new Gson();
        Article article = gson.fromJson(articleJsonString, Article.class);
        this.guid = article.guid;
        this.title = article.title;
        this.link = article.link;
        this.description = article.description;
        this.publishedDate = article.publishedDate;
    }

    public Article(Article article) {
        this.guid = article.guid;
        this.title = article.title;
        this.link = article.link;
        this.description = article.description;
        this.publishedDate = article.publishedDate;
    }

    // change this. because guid is supposed to be unique. just check the guid
    public boolean equals(Object arg) {
        if (!(arg instanceof Article)) {
            return false;
        }
        Article article = (Article) arg;
        if (guid.compareToIgnoreCase(article.guid) == 0) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (13 * guid.toLowerCase().hashCode());
    }

    public String toString() {
        StringBuffer retValue = new StringBuffer();

        if (guid != null) {
            retValue.append(guid).append(",");
        }

        if (title != null) {
            retValue.append(title).append(",");
        }
        if (link != null) {
            retValue.append(link).append(",");
        }
        if (description != null) {
            retValue.append(description).append(",");
        }
        if (publishedDate != null) {
            retValue.append(publishedDate).append(",");
        }
        return retValue.toString();
    }

    public String serializeToJSON() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}