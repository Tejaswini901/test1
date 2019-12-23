package com.inn.logFile.service;

import java.util.List;

import com.inn.logFile.model.LogFileType;

public interface ILogFileTypeService {

	List<LogFileType> getLogFileTypes();

	LogFileType findfileTypeById(Integer logFileTypeId);

}
