package com.inn.logFile.model;

public class LogFile {
	
	private String name;
	
	private String extension;
	
	private String type;
	
	private Integer enodebId;
	
	private Integer logFileTypeId;
	
	private String folderDate;
	
	private Long size;
	
	public LogFile() {
	
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getEnodebId() {
		return enodebId;
	}

	public void setEnodebId(Integer enodebId) {
		this.enodebId = enodebId;
	}

	public Integer getLogFileTypeId() {
		return logFileTypeId;
	}

	public void setLogFileTypeId(Integer logFileTypeId) {
		this.logFileTypeId = logFileTypeId;
	}

	public String getFolderDate() {
		return folderDate;
	}

	public void setFolderDate(String folderDate) {
		this.folderDate = folderDate;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

}
