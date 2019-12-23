package com.inn.logFile.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.inn.logFile.model.LogFile;
import com.inn.logFile.model.LogFileType;
import com.inn.logFile.model.SiteMasterData;
import com.inn.logFile.service.ILogFileService;
import com.inn.logFile.service.ILogFileTypeService;
import com.inn.logFile.service.ISiteMasterDataService;
import com.inn.logFile.utils.LogFileProperties;

@Service("LogFileServiceImpl")
public class LogFileServiceImpl implements ILogFileService {

    private final Logger logger = LoggerFactory.getLogger(LogFileServiceImpl.class);
    
    @Autowired
    private ISiteMasterDataService enodeBService;
    
    @Autowired
    private  ILogFileTypeService logFileTypeService;
    
    @Autowired
    private LogFileProperties properties;
    
    public List<LogFile> getAllFoldersAndFiles(String folderType,String date, Integer enodebId, Integer logFileTypeId){
    	List<LogFile> logFiles = new ArrayList<LogFile>();
    	LogFile logFile = null;
    	if(folderType != null && "root".equals(folderType)) {
    		DateTimeFormatter format = DateTimeFormatter
    		        .ofPattern("dd-MM-yyyy");
    		Integer noOfDays = properties.getNoOfDays();
    		logger.error("noOfDays-----------{}",noOfDays);
    		    LocalDateTime now = LocalDateTime.now();
    		    for (Integer i=0 ; i < noOfDays ; i++) {
    		    	logFile = new LogFile();
        			logFile.setName(now.minusDays(i).format(format));
        			logFile.setType("folder");
        			logFile.setFolderDate(now.minusDays(i).format(format));
        			logFiles.add(logFile);
				}
    	}else if(date == null) {
    		return logFiles;
    	}else if(date != null && enodebId == null) {
    		List<SiteMasterData> enodebDetail = enodeBService.getEnodebDetail();
        	for (SiteMasterData enodeB : enodebDetail) {
        		logFile = new LogFile();
    			logFile.setName(enodeB.getEnbId());
    			logFile.setEnodebId(enodeB.getEnodebid());
    			logFile.setFolderDate(date);
    			logFile.setType("folder");
    			logFiles.add(logFile);
    		}
    	}else if(date != null && enodebId != null && logFileTypeId == null) {
    		List<LogFileType> logFileTypeList = logFileTypeService.getLogFileTypes();
    		for (LogFileType lfType : logFileTypeList) {
    			logFile = new LogFile();
    			logFile.setName(lfType.getName());
    			logFile.setLogFileTypeId(lfType.getId());
    			logFile.setEnodebId(enodebId);
    			logFile.setFolderDate(date);
    			logFile.setType("folder");
    			logFiles.add(logFile);
			}
    	}else if(date != null && enodebId != null && logFileTypeId != null) {
    		SiteMasterData enodeB = enodeBService.findEnodebById(enodebId);
    		LogFileType fileType = logFileTypeService.findfileTypeById(logFileTypeId);
    		String basePath = properties.getBasePath();
    		basePath = basePath + date + "/" + enodeB.getEnbId() + "/" + fileType.getName();
    		
			File file = new File(basePath);
			try {
				Files.walkFileTree(file.toPath(), Collections.emptySet(), 1, new SimpleFileVisitor<Path>() {
				    @Override
				    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				        LogFile logFile = new LogFile();
				        logger.info(attrs.size()+"'");
				        logger.info(attrs.toString());
				        String fileName = file.getFileName().toString();
						logFile.setName(fileName);
						logFile.setType(Files.isDirectory(file) ? "folder" : "file");
						logFile.setEnodebId(enodebId);
						logFile.setFolderDate(date);
						logFile.setLogFileTypeId(logFileTypeId);
						logFile.setExtension(fileName.substring(
								fileName.lastIndexOf(".")+1));
						logFiles.add(logFile);
						logFile.setSize(attrs.size());
				        return FileVisitResult.CONTINUE;
				    }
				});
			} catch (Exception e) {
				logger.error("Exception in @class LogFileServiceImpl @method getAllFoldersAndFiles : {}",e.getMessage());
			}
    	}
			
    		
    	
    	/*if(folderType.equals("root")) {
    		LogFile logFile = null;
    		String[] enodeBList = properties.getEnodeBIds().split("\\|");
    		for (String enodeB : enodeBList) {
    			logFile = new LogFile();
    			logFile.setName(enodeB);
    			logFile.setType("folder");
    			logFiles.add(logFile);
			}
    	}else {
    		String basePath = properties.getBasePath();
    		try (Stream<Path> walk = Files.walk(Paths.get(basePath+folderType))) {
    			logFiles = walk.filter(Files::isRegularFile)
        				.map(logFile -> new LogFile(logFile.toString().substring(logFile.toString().lastIndexOf("/")+1),
        						logFile.toString(),
        						logFile.toString().substring(logFile.toString().lastIndexOf(".")+1)
        						,"file"))
        				.collect(Collectors.toList());
        				
        	} catch (Exception e) {
        		logger.error("Error in getAllFoldersAndFiles : {}",e.getMessage());
        	}
    	}*/
    	/*try {
			if(folderType != null) {
				String basePath = properties.getBasePath();
				if(!"root".equals(folderType)) {
					basePath = basePath+folderType;
				}
				File file = new File(basePath);
				Files.walkFileTree(file.toPath(), Collections.emptySet(), 1, new SimpleFileVisitor<Path>() {
				    @Override
				    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				        LogFile logFile = new LogFile();
		    			logFile.setName(file.getFileName().toString());
		    			logFile.setType(Files.isDirectory(file) ? "folder" : "file");
		    			if(Files.isRegularFile(file)) {
		    				logFile.setPath(file.toAbsolutePath().toString());
		    			}
		    			logFiles.add(logFile);
				        return FileVisitResult.CONTINUE;
				    }
				});
			}
		} catch (IOException e) {
		}*/
    	
    	return logFiles;

    }
    
    @Override
	public ResponseEntity<InputStreamResource> downloadFile(String date,Integer enodebId,Integer logFileTypeId
			, String fileName)
			throws FileNotFoundException {
	    	if(date != null && enodebId != null && logFileTypeId != null) {
	    		SiteMasterData enodeB = enodeBService.findEnodebById(enodebId);
	    		LogFileType fileType = logFileTypeService.findfileTypeById(logFileTypeId);
	    		String basePath = properties.getBasePath();
	    		basePath = basePath + date + "/" + enodeB.getEnbId() + "/" + fileType.getName() + "/" + fileName;
	    		File file = new File(basePath);
	    		logger.info("file path : {}",basePath);
	        	InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
	        		return ResponseEntity.ok()
	        				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
	        				//.contentType("")
	        				.contentLength(file.length()).body(resource);
	        }
	    	return null;
    	}
}
