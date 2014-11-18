package com.rss.common;

import java.io.Serializable;

public class Article implements Serializable {
	// this is supposed to be the global unique identifier
	public String guid;
	public String title;
	public String link;
	public String description;
	public String  publishedDate;
	
	// change this. because guid is supposed to be unique. just check the guid
	public boolean equals(Object arg){
		if (!(arg instanceof Article)) {
			return false;
		}
		Article article = (Article)arg;
		if (guid.compareToIgnoreCase(article.guid) == 0) 
	    {
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
}