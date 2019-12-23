package com.inn.core.utils;

public class ConstantValueUtil {

    public static final String CONSUMER_KEY = "CONSUMER_KEY";
    public static final String CONSUMER_SECRET = "CONSUMER_SECRET";
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String ACCESS_TOKEN_SECRET = "ACCESS_TOKEN_SECRET";
    public static final String SOMETHING_WENT_WRONG = "Something Went Wrong!";


    public enum ResponseStatus {SUCCESS, INTERNAL_SERVER_ERROR, NO_CONTENT, INVALID_PATH_SEQUENCE, FILE_NOT_FOUND, NOT_CREATE_DIRECTORY}

    public enum ResponseKey {data, status, statusInfo, count, message, mediaType}


}
