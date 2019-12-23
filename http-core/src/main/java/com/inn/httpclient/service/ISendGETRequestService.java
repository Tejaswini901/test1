package com.inn.httpclient.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.inn.exception.BusinessException;

public interface ISendGETRequestService {
	
	public JSONObject getJSONObject(String url) throws BusinessException;
	public JSONArray getJSONArray(String url) throws BusinessException;

}
