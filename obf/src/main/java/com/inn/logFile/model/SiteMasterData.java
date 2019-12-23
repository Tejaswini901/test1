package com.inn.logFile.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries(value = {
	@NamedQuery(name = "SiteMasterData.getEnodebDetail", query = "SELECT e FROM SiteMasterData e"),
	@NamedQuery(name = "SiteMasterData.findEnodebById", query = "SELECT e FROM SiteMasterData e where e.enodebid=:enodebId")
})

@Entity
@Table(name = "SiteMasterData")
public class SiteMasterData {

	@Id
    private int enodebid;
 
	private String enbId;

	public int getEnodebid() {
		return enodebid;
	}

	public void setEnodebid(int enodebid) {
		this.enodebid = enodebid;
	}

	public String getEnbId() {
		return enbId;
	}

	public void setEnbId(String enbId) {
		this.enbId = enbId;
	}
}
