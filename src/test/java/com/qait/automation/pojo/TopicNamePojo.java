package com.qait.automation.pojo;

public class TopicNamePojo {

	private static String topicName;
    private static String publishedArticleName;
	public  String getPublishedArticleName() {
		return publishedArticleName;
	}

	public  void setPublishedArticleName(String publishedArticleName) {
		TopicNamePojo.publishedArticleName = publishedArticleName;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicNamev) {
		topicName = topicNamev;
	}
	
	
}
