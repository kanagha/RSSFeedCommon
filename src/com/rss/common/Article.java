package com.rss.common;

import java.io.Serializable;

public class Article implements Serializable {
	public String title;
	public String link;
	public String description;
	public String  publishedDate;
	
	public boolean equals(Object arg){
		if (!(arg instanceof Article)) {
			return false;
		}
		Article article = (Article)arg;
		if (title.compareToIgnoreCase(article.title) == 0 && 
				link.compareTo(article.link)== 0 &&
				((description != null) ? (description.compareToIgnoreCase(article.description) == 0) : true) &&
				publishedDate.compareTo(article.publishedDate) == 0) {
					return true;
				}
		return false;
	}
	
	public int hashCode() {
		int retValue = (description != null ? description.toLowerCase().hashCode() : 1);
		return (13 * title.toLowerCase().hashCode() + 13 * link.hashCode() + 13
				* retValue + 13 * publishedDate
				.hashCode());
	}
	
	public String toString() {
		StringBuffer retValue = new StringBuffer();
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