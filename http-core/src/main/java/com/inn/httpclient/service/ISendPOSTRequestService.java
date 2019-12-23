package com.inn.httpclient.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.inn.exception.BusinessException;

public interface ISendPOSTRequestService {
	
	public JSONObject getJSONObject(String url) throws BusinessException;
	public JSONObject getJSONObject(String url, JSONObject jsonObject) throws BusinessException;
	public JSONObject getJSONObject(String url, JSONArray jsonArray) throws BusinessException;
	
	public JSONArray getJSONArray(String url) throws BusinessException;
	public JSONArray getJSONArray(String url, JSONObject jsonObject) throws BusinessException;
	public JSONArray getJSONArray(String url, JSONArray jsonArray) throws BusinessException;

}
