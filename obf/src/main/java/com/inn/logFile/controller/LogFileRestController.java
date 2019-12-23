package com.inn.logFile.controller;

import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inn.logFile.model.LogFile;
import com.inn.logFile.service.ILogFileService;

@RestController
@RequestMapping("logFile")
@CrossOrigin(origins = "*")
public class LogFileRestController {

	private final Logger logger = LoggerFactory.getLogger(LogFileRestController.class);
	
	@Autowired
	private ILogFileService logFileService;
	
	@GetMapping("/getMessage")
	public Date getMessage() {
		return new Date();
		
	}
	
	@GetMapping("/getAllFoldersAndFiles")
	public List<LogFile> getAllFoldersAndFiles(@RequestParam(value = "folderType", required = false) String folderType,
			@RequestParam(value = "enodebId", required = false) Integer enodebId,
			@RequestParam(value = "date", required = false) String date,
			@RequestParam(value = "logFileTypeId", required = false) Integer logFileTypeId) {
		return logFileService.getAllFoldersAndFiles(folderType, date, enodebId, logFileTypeId);
	}

    @GetMapping(value="/downloadFile")
    public ResponseEntity<InputStreamResource>  downloadFile(@RequestParam(value = "enodebId", required = false) Integer enodebId,
			@RequestParam(value = "date", required = false) String date,
			@RequestParam(value = "logFileTypeId", required = false) Integer logFileTypeId,
			@RequestParam(value = "fileName", required = false) String fileName) throws FileNotFoundException {
		return logFileService.downloadFile(date, enodebId, logFileTypeId, fileName);
	}
}
