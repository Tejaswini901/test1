package com.inn.logFile.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inn.logFile.dao.ILogFileTypeDao;
import com.inn.logFile.model.LogFileType;
import com.inn.logFile.service.ILogFileTypeService;

@Service("LogFileTypeServiceImpl")
public class LogFileTypeServiceImpl implements ILogFileTypeService {

    private final Logger logger = LoggerFactory.getLogger(LogFileTypeServiceImpl.class);

    @Autowired
    private ILogFileTypeDao logFileTypeDao;

	@Override
	public List<LogFileType> getLogFileTypes() {
		return logFileTypeDao.getLogFileTypes();
	}

	@Override
	public LogFileType findfileTypeById(Integer logFileTypeId) {
		return logFileTypeDao.findfileTypeById(logFileTypeId);
	}

    
}
