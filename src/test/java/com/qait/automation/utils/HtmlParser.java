package com.qait.automation.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlParser {
	
	public static Map<String, HashMap<String, String>> taskURLs = new HashMap<String, HashMap<String, String> >();
	static String filePath="src" + File.separator + "test" + File.separator + "resources" + File.separator + "testdata" + File.separator + "task_urls.html";
	
	public static void populateTaskURLs()
	{
		File file=new File(filePath);
		try
		{
			Document doc=Jsoup.parse(file, "UTF-8");
			Elements elements=doc.getElementsByTag("tr");
			Map<String, String> examURLs=new HashMap<String, String>();
			int flag=0;
			for(Element el : elements)
			{
				if(flag++==0)
					continue;
				examURLs=getExamURLs(el);
				taskURLs.put(el.child(1).text(),  (HashMap<String, String>) examURLs);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private static Map<String, String> getExamURLs(Element el) {
		Map<String, String> urls=new HashMap<String, String>();
		urls.put("exam1", el.child(2).child(0).attr("href").toString());
		urls.put("exam2", el.child(3).child(0).attr("href").toString());
		urls.put("training", el.child(4).child(0).attr("href").toString());
		return urls;
	}
	
}
