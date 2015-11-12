package com.founder.drools.base.model;

import java.io.Serializable;

import com.founder.framework.annotation.DBInfoAnnotation;
import com.founder.framework.annotation.FieldDesc;
import com.founder.framework.base.entity.BaseEntity;


@DBInfoAnnotation(tableName = "DROOLS_RULEHIS", pk = "version")
public class Drools_ruleHis extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@FieldDesc("版本，归档时间") private String version;
	@FieldDesc("服务ID") private String serviceid;
	@FieldDesc("服务名") private String servicename;
	@FieldDesc("服务URL") private String serviceurl;
	@FieldDesc("服务方法") private String servicemethod;
	@FieldDesc("分组ID") private String groupid;
	@FieldDesc("分组名") private String groupname;
	@FieldDesc("规则文件名") private String rulefilename;
	@FieldDesc("规则内容") private String content;
	@FieldDesc("使用时的规则ID，区分是否是同一个规则") private String ruleid;
	
	
	public String getServicemethod() {
		return servicemethod;
	}
	public void setServicemethod(String servicemethod) {
		this.servicemethod = servicemethod;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getServiceid() {
		return serviceid;
	}
	public void setServiceid(String serviceid) {
		this.serviceid = serviceid;
	}
	public String getServicename() {
		return servicename;
	}
	public void setServicename(String servicename) {
		this.servicename = servicename;
	}
	public String getServiceurl() {
		return serviceurl;
	}
	public void setServiceurl(String serviceurl) {
		this.serviceurl = serviceurl;
	}
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public String getRulefilename() {
		return rulefilename;
	}
	public void setRulefilename(String rulefilename) {
		this.rulefilename = rulefilename;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRuleid() {
		return ruleid;
	}
	public void setRuleid(String ruleid) {
		this.ruleid = ruleid;
	}

	
}
