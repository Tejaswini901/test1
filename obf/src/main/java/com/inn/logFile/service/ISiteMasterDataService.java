package com.inn.logFile.service;

import java.util.List;

import com.inn.logFile.model.SiteMasterData;

public interface ISiteMasterDataService {

	List<SiteMasterData> getEnodebDetail();

	SiteMasterData findEnodebById(Integer enodebId);

}
