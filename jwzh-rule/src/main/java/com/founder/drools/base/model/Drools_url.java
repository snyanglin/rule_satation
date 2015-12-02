package com.founder.drools.base.model;

import java.io.Serializable;
import java.util.Date;

import com.founder.framework.annotation.DBInfoAnnotation;
import com.founder.framework.annotation.FieldDesc;
import com.founder.framework.base.entity.BaseEntity;


@DBInfoAnnotation(tableName = "DROOLS_URL", pk = "id")
public class Drools_url extends BaseModle implements Serializable {

	private static final long serialVersionUID = 1L;

	@FieldDesc("地址ID") private String id;
	@FieldDesc("地址名称") private String urlname;
	@FieldDesc("地址URL") private String url;
	@FieldDesc("备注") private String bz;	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrlname() {
		return urlname;
	}
	public void setUrlname(String urlname) {
		this.urlname = urlname;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	
}
