package com.founder.drools.base.model;

import java.io.Serializable;

import com.founder.framework.annotation.DBInfoAnnotation;
import com.founder.framework.annotation.FieldDesc;


@DBInfoAnnotation(tableName = "DROOLS_SERVICE", pk = "id")
public class Drools_service extends BaseModle implements Serializable {

	private static final long serialVersionUID = 1L;

	@FieldDesc("地址ID") private String urlid;
	@FieldDesc("服务ID") private String id;
	@FieldDesc("服务名称") private String servicename;
	@FieldDesc("备注") private String bz;
	public String getUrlid() {
		return urlid;
	}
	public void setUrlid(String urlid) {
		this.urlid = urlid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getServicename() {
		return servicename;
	}
	public void setServicename(String servicename) {
		this.servicename = servicename;
	}
	
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
}
