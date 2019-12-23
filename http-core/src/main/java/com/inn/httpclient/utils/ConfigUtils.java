package com.inn.httpclient.utils;

import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.nio.channels.IllegalBlockingModeException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.inn.exception.BusinessException;

public class ConfigUtils {

	public static JSONObject getSuccessJsonObject() {
		JSONObject result = new JSONObject();
		result.put("message", "success");
		return result;
	}
	public static JSONArray getSuccessJsonArray() {
		JSONObject result = getSuccessJsonObject();
		JSONArray resultArray = new JSONArray();
		resultArray.add(result);
		return resultArray;
	}
	public static JSONObject getJSONObject(InputStreamReader inputStreamReader) throws BusinessException {
		try{
			JSONParser parser = new JSONParser();
			Object json = parser.parse(inputStreamReader);
			if (json instanceof JSONObject) {
				return (JSONObject)json;
			}
			return getSuccessJsonObject();
		}catch(Exception e) {
			throw new BusinessException(e);
		}
	}
	
	public static JSONArray getJSONArray(InputStreamReader inputStreamReader) throws BusinessException {
		try {
			JSONParser parser = new JSONParser();
			Object json = parser.parse(inputStreamReader);
	    	if (json instanceof JSONArray) {
				return (JSONArray) json;
			}
	    	return getSuccessJsonArray();
		}catch(Exception e) {
			throw new BusinessException(e);
		}
	}
	
	public static boolean isHostAvailable(String strUrl) throws BusinessException {
		try {
			URL url = new URL(strUrl);
			String host = url.getHost();
			int port = url.getPort();
			return pingHost(host, port);
		}catch(Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}
    
    private static boolean pingHost(String host, int port) throws BusinessException {
        try(Socket socket = new Socket()){
            socket.connect(new InetSocketAddress(host, port), 2000);
            return true;
        } catch (IllegalBlockingModeException e) {
            throw new BusinessException("This socket has an associated channel, and the channel is in non-blocking mode");
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Endpoint is null or is a SocketAddress subclass not supported by this socket");
        } catch (Exception e) {
            throw new BusinessException("Error occurs during the connection");
        }
    }
}
