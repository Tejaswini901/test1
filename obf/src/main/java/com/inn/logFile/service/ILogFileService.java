package com.inn.logFile.service;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

import com.inn.logFile.model.LogFile;

public interface ILogFileService {

	List<LogFile> getAllFoldersAndFiles(String folderType, String date, Integer enodebId, Integer logFileType);

	ResponseEntity<InputStreamResource> downloadFile(String folderType, Integer enodebId, Integer logFileTypeId, String fileName) throws FileNotFoundException;

}
