package com.inn.core.generic;

import org.json.simple.JSONObject;

public interface IFilterSercvice<EntityModel> {

    JSONObject searchByFilter(Class entity, String search, String orderBy, String OrderType, Integer page, Integer size) throws Exception;

}
