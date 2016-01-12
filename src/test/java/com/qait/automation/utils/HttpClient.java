/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qait.automation.utils;

import com.qait.automation.coach.behavedemo.getstory.Constants;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.api.json.JSONConfiguration;

/**
 * 
 * @author prashantshukla
 */
public class HttpClient {

	public HttpClient() {

	}

	public ClientResponse getHttpResponse(String resourceURL) {
		Client client = getJiraClient();

		WebResource webResourceGet = client.resource(resourceURL);
		ClientResponse response = webResourceGet.get(ClientResponse.class);
		return response;
	}

	public ClientResponse postHttpResponse(String resourceURL, Object postBody) {
		ClientResponse response = null;

		Client client = getJiraClient();

		WebResource webResourcePost = client.resource(resourceURL);

		response = webResourcePost.type("application/json").post(
				ClientResponse.class, postBody);

		return response;
	}

	public ClientResponse putHttpResponse(String resourceURL, Object postBody) {

		Client client = getJiraClient();

		WebResource webResourcePut = client.resource(resourceURL);

		ClientResponse response = webResourcePut.type("application/json").put(
				ClientResponse.class, postBody);
		return response;
	}

	private Client getJiraClient() {
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING,
				Boolean.TRUE);

		Client client = Client.create(clientConfig);

		client.addFilter(new HTTPBasicAuthFilter(Constants.JIRA_USERNAME,
				Constants.JIRA_PASSWORD));
		return client;
	}
}
