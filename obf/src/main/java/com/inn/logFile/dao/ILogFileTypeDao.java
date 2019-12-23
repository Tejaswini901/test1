package com.inn.logFile.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import com.inn.logFile.model.LogFileType;

@Component("LogFileTypeDaoRepo")
public interface ILogFileTypeDao extends JpaRepository<LogFileType, Long>, JpaSpecificationExecutor<LogFileType> {

	List<LogFileType> getLogFileTypes();
	
	LogFileType findfileTypeById(@Param("fileTypeId") Integer fileTypeId);

}
