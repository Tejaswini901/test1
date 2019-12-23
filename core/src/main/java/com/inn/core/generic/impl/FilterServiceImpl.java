package com.inn.core.generic.impl;


import com.github.tennaito.rsql.jpa.JpaPredicateVisitor;
import com.inn.core.generic.IFilterSercvice;
import com.inn.core.utils.ConstantValueUtil.ResponseKey;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
class FilterServiceImpl<EntityModel> implements IFilterSercvice<EntityModel> {

    final private Logger log = LoggerFactory.getLogger(FilterServiceImpl.class);
    @PersistenceContext
    private EntityManager em;

    @Override
    public JSONObject searchByFilter(Class entity, String search, String orderBy, String orderType, Integer page, Integer size) throws Exception {
        final String searchParameter = "entity :: " + entity.getName() + " search :: " + search + " orderBy :: " + orderBy + " orderType :: " + orderType + " page :: " + page + " size ::" + size;
        log.info("Inside @class FilterServiceImpl @method searchByFilter @params " + searchParameter);
        JSONObject queryObject = new JSONObject();
        try {
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery criteria = builder.createQuery(entity);
            From root = criteria.from(entity);
            RSQLVisitor<Predicate, EntityManager> visitor = new JpaPredicateVisitor<EntityModel>().defineRoot(root);

            Node rootNode;
            if (search != null && !search.isEmpty()) {
                rootNode = new RSQLParser().parse(search);
                // Visit the node to retrieve CriteriaQuery
                Predicate predicate = rootNode.accept(visitor, em);

                // Use generated predicate as you like
                criteria.where(predicate);
            }

            orderType = orderType != null ? orderType.toLowerCase() : "desc";
            List<Order> orderList = new ArrayList<Order>();

            List<String> orderByLists = new ArrayList<>(orderBy != null ? Arrays.asList(orderBy.split(",")) : Collections.singletonList("modifiedTime"));
            orderByLists.add("modifiedTime");

            for (String item : orderByLists) {
                if (orderType.equals("desc")) {
                    orderList.add(builder.desc(root.get(item)));
                }
                else
                {
                    orderList.add(builder.asc(root.get(item)));
                }
            }
            criteria.orderBy(orderList);

            Query query = em.createQuery(criteria);
            queryObject.put(ResponseKey.count, query.getResultList().size());
            if (page != null && size != null) {
                query.setFirstResult((size * page)).setMaxResults((size * (page + 1)) - ((size * page)));
            }

            queryObject.put(ResponseKey.data, query.getResultList());

            return queryObject;
        } catch (Exception e) {
            log.error("Exception @class FilterServiceImpl @method searchByFilter @message :: " + e.getMessage());
            throw e;
        }
    }
}
