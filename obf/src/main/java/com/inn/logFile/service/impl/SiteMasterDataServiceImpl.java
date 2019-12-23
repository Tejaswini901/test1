package com.inn.logFile.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inn.logFile.dao.ISiteMasterDataDao;
import com.inn.logFile.model.SiteMasterData;
import com.inn.logFile.service.ISiteMasterDataService;

@Service("EnodeBServiceImpl")
public class SiteMasterDataServiceImpl implements ISiteMasterDataService {

    private final Logger logger = LoggerFactory.getLogger(SiteMasterDataServiceImpl.class);

    @Autowired
    private ISiteMasterDataDao enodeBDao;
    
	@Override
	public List<SiteMasterData> getEnodebDetail() {
		return enodeBDao.getEnodebDetail();
	}
	
	@Override
	public SiteMasterData findEnodebById(Integer enodebId) {
		return enodeBDao.findEnodebById(enodebId);
	}
   
}
