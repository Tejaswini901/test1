package com.inn.httpclient.service.impl;

import java.io.InputStreamReader;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.inn.exception.BusinessException;
import com.inn.httpclient.service.ISendGETRequestService;
import com.inn.httpclient.utils.ConfigUtils;

@Service("SendGETRequestServiceImpl")
public class SendGETRequestServiceImpl implements ISendGETRequestService {

    private final Logger logger = LoggerFactory.getLogger(SendGETRequestServiceImpl.class);

    private HttpResponse executeGetRequest(String url, Map<String, String> requestHeader) throws BusinessException{
    	try {
    		if(!ConfigUtils.isHostAvailable(url)) {
    			throw new BusinessException("Error occurs during the connection");
    		}
    		HttpClient client = HttpClientBuilder.create().build();
    		HttpGet request = new HttpGet(url);
    		if(requestHeader!=null && !requestHeader.isEmpty()) {
    			Set<String> keys = requestHeader.keySet();
    			for(String key:keys) {
    				request.addHeader(key, requestHeader.get(key));
    			}
    		}else {
    			request.addHeader("Content-Type", "application/json");
    		}
    		return client.execute(request);
    	}catch(Exception e) {
    		logger.error("Exception @executeGetRequest @SendPOSTRequest Cause: ",e);
    		throw new BusinessException(e);
    	}
    }
        
    @Override
    public JSONObject getJSONObject(String url) throws BusinessException {
    	try {
    		logger.info("inside @SendGETRequestServiceImpl @getJSONObject params: {}",url);
        	HttpResponse response = executeGetRequest(url, null);
        	logger.debug("debug @getJSONObject Response Code : {}" , response.getStatusLine().getStatusCode());
        	if(response.getEntity()!=null && response.getEntity().getContent()!=null) {
        		return ConfigUtils.getJSONObject(new InputStreamReader(response.getEntity().getContent()));
        	}
        	return ConfigUtils.getSuccessJsonObject();
    	}catch(Exception e) {
    		logger.error("Exception @SendGETRequestServiceImpl @getJSONObject Cause: ",e);
    		throw new BusinessException(e);
    	}
    }
    
    @Override
    public JSONArray getJSONArray(String url) throws BusinessException {
    	try {
    		logger.info("inside @SendGETRequestServiceImpl @getJSONArray params: {}",url);
        	HttpResponse response = executeGetRequest(url, null);
        	logger.debug("debug @getJSONArray Response Code : {}" , response.getStatusLine().getStatusCode());
        	if(response.getEntity()!=null && response.getEntity().getContent()!=null) {
        		return ConfigUtils.getJSONArray(new InputStreamReader(response.getEntity().getContent()));
        	}
        	return ConfigUtils.getSuccessJsonArray();
    	}catch(Exception e) {
    		logger.error("Exception @SendGETRequestServiceImpl @getJSONArray Cause: ",e);
    		throw new BusinessException(e);
    	}
    }
    
    

}
