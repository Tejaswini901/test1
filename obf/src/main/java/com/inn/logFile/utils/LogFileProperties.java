package com.inn.logFile.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@ConfigurationProperties(prefix = "logfile")
public class LogFileProperties {

    private String basePath;
    
    private String enodeBIds;
    
    private Integer noOfDays;
    
    public String elasticHost;
    
    public int elasticPort;	

	public String getEnodeBIds() {
		return enodeBIds;
	}

	public void setEnodeBIds(String enodeBIds) {
		this.enodeBIds = enodeBIds;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public Integer getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(Integer noOfDays) {
		this.noOfDays = noOfDays;
	}

	public int getElasticPort() {
		return elasticPort;
	}

	public void setElasticPort(int elasticPort) {
		this.elasticPort = elasticPort;
	}
    
	
}
