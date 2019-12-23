
package com.inn.httpclient.service.impl;

import java.io.InputStreamReader;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.inn.exception.BusinessException;
import com.inn.httpclient.service.ISendPOSTRequestService;
import com.inn.httpclient.utils.ConfigUtils;

@Service("SendPOSTRequestServiceImpl")
public class SendPOSTRequestServiceImpl implements ISendPOSTRequestService {

    private final Logger logger = LoggerFactory.getLogger(SendPOSTRequestServiceImpl.class);

    private HttpResponse executePostRequest(String url, Map<String, String> requestHeader, Object requestBody) throws BusinessException{
    	try {
    		if(!ConfigUtils.isHostAvailable(url)) {
    			throw new BusinessException("Error occurs during the connection");
    		}
    		HttpClient client = HttpClientBuilder.create().build();
			HttpPost request = new HttpPost(url);
    		if(requestHeader!=null && !requestHeader.isEmpty()) {
    			Set<String> keys = requestHeader.keySet();
    			for(String key:keys) {
    				request.addHeader(key, requestHeader.get(key));
    			}
    		}else {
    			request.addHeader("Content-Type", "application/json");
    		}
    		if(requestBody!=null) {
    			StringEntity params = new StringEntity(requestBody.toString()) ;
    			request.setEntity(params);
    		}
			return client.execute(request);
    	}catch(Exception e) {
    		logger.error("Exception @SendPOSTRequestServiceImpl @executePostRequest Cause: ",e);
    		throw new BusinessException(e);
    	}
    }
    
    private JSONArray getResultArray(String url, Map<String, String> requestHeader, Object requestBody) throws BusinessException{
    	try {
    		logger.info("inside @SendPOSTRequestServiceImpl @getResultArray url: {} requestHeader: {} requestBody: {}",url,requestHeader,requestBody);
    		HttpResponse response = executePostRequest(url, requestHeader, requestBody);
			logger.debug("debug @getResultArray Response Code : {}" , response.getStatusLine().getStatusCode());
	    	if(response.getEntity()!=null && response.getEntity().getContent()!=null) {
	    		return ConfigUtils.getJSONArray(new InputStreamReader(response.getEntity().getContent()));
	    	}
	    	return ConfigUtils.getSuccessJsonArray();
    	}catch(Exception e) {
    		logger.error("Exception @SendPOSTRequestServiceImpl @getResultArray Cause: ",e);
    		throw new BusinessException(e);
    	}
    }
    
    private JSONObject getResultObject(String url, Map<String, String> requestHeader, Object requestBody) throws BusinessException{
    	try {
    		logger.info("inside @SendPOSTRequestServiceImpl @getResultObject url: {} requestHeader: {} requestBody: {}",url,requestHeader,requestBody);
    		HttpResponse response = executePostRequest(url, requestHeader, requestBody);
			logger.debug("debug @getResultObject Response Code : {}" , response.getStatusLine().getStatusCode());
			if(response.getEntity()!=null && response.getEntity().getContent()!=null) {
        		return ConfigUtils.getJSONObject(new InputStreamReader(response.getEntity().getContent()));
        	}
        	return ConfigUtils.getSuccessJsonObject();
    	}catch(Exception e) {
    		logger.error("Exception @SendPOSTRequestServiceImpl @getResultObject Cause: ",e);
    		throw new BusinessException(e);
    	}
    }
    
    @Override
    public JSONObject getJSONObject(String url) throws BusinessException {
    	try {
    		logger.info("inside @SendPOSTRequestServiceImpl @getJSONObject params: {}",url);
    		return getResultObject(url, null, null);
    	}catch(BusinessException e) {
    		logger.error("BusinessException @SendPOSTRequestServiceImpl @getJSONObject Cause: ",e);
    		throw new BusinessException(e);
    	}
    }
    
    @Override
    public JSONObject getJSONObject(String url, JSONObject jsonObject) throws BusinessException {
    	try {
    		logger.info("inside @SendPOSTRequestServiceImpl with jsonObject @getJSONObject params: {}",url);
    		return getResultObject(url, null, jsonObject);
    	}catch(BusinessException e) {
    		logger.error("BusinessException @SendPOSTRequestServiceImpl @getJSONObject with jsonObject Cause: ",e);
    		throw new BusinessException(e);
    	}
    }
    
    @Override
    public JSONObject getJSONObject(String url, JSONArray jsonArray) throws BusinessException {
    	try {
    		logger.info("inside @SendPOSTRequestServiceImpl with jsonArray @getJSONObject params: {}",url);
    		return getResultObject(url, null, jsonArray);
    	}catch(BusinessException e) {
    		logger.error("BusinessException @SendPOSTRequestServiceImpl @getJSONObject with jsonArray Cause: ",e);
    		throw new BusinessException(e);
    	}
    }
    
    @Override
    public JSONArray getJSONArray(String url) throws BusinessException {
    	try {
    		logger.info("inside @SendPOSTRequestServiceImpl @getJSONArray params: {}",url);
    		return getResultArray(url, null, null);
    	}catch(BusinessException e) {
    		logger.error("BusinessException @SendPOSTRequestServiceImpl @getJSONArray Cause: ",e);
    		throw new BusinessException(e);
    	}
    }
    
    @Override
    public JSONArray getJSONArray(String url, JSONObject jsonObject) throws BusinessException {
    	try {
    		logger.info("inside @SendPOSTRequestServiceImpl with jsonObject @getJSONArray params: {}",url);
    		return getResultArray(url, null, jsonObject);
    	}catch(BusinessException e) {
    		logger.error("BusinessException @SendPOSTRequestServiceImpl @getJSONArray with jsonObject Cause: ",e);
    		throw new BusinessException(e);
    	}
    }
    
    @Override
    public JSONArray getJSONArray(String url, JSONArray jsonArray) throws BusinessException {
    	try {
    		logger.info("inside @SendPOSTRequestServiceImpl with jsonArray  @getJSONArray params: {}",url);
    		return getResultArray(url, null, jsonArray);
    	}catch(BusinessException e) {
    		logger.error("BusinessException @SendPOSTRequestServiceImpl @getJSONArray with jsonArray Cause: ",e);
    		throw new BusinessException(e);
    	}
    }

    
}
