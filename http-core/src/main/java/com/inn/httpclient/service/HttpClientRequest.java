package com.inn.httpclient.service;

import org.springframework.beans.factory.annotation.Autowired;

public class HttpClientRequest {

	@Autowired
	protected ISendPOSTRequestService sendPOSTRequestService;
	
	@Autowired
	protected ISendGETRequestService sendGETRequestService;
	
}
