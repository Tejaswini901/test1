package com.inn.logFile.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries(value = {
	@NamedQuery(name = "LogFileType.getLogFileTypes", query = "SELECT e FROM LogFileType e"),
	@NamedQuery(name = "LogFileType.findfileTypeById", query = "SELECT e FROM LogFileType e where e.id=:fileTypeId")
})

@Entity
@Table(name = "logfile_type")
public class LogFileType {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
 
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
