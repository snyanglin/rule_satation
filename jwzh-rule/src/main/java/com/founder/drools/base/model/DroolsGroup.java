package com.founder.drools.base.model;

import java.io.Serializable;

import com.founder.framework.annotation.DBInfoAnnotation;
import com.founder.framework.annotation.FieldDesc;


@DBInfoAnnotation(tableName = "DROOLS_GROUP", pk = "id")
public class DroolsGroup extends BaseModle implements Serializable {

	private static final long serialVersionUID = -5351327439931807100L;
	
	@FieldDesc("规则ID")
	private String id;
	
	private String groupname;
	
	private String serviceid;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public String getServiceid() {
		return serviceid;
	}

	public void setServiceid(String serviceid) {
		this.serviceid = serviceid;
	}
	
}
