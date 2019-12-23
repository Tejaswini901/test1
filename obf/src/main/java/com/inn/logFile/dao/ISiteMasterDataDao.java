package com.inn.logFile.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import com.inn.logFile.model.SiteMasterData;

@Component("EnodeBDaoRepo")
public interface ISiteMasterDataDao extends JpaRepository<SiteMasterData, Long>, JpaSpecificationExecutor<SiteMasterData> {

	List<SiteMasterData> getEnodebDetail();

	SiteMasterData findEnodebById(@Param("enodebId") Integer enodebId);

}
