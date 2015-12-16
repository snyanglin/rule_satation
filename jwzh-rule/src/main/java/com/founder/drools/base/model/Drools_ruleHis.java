package com.founder.drools.base.model;

import java.io.Serializable;

import com.founder.framework.annotation.DBInfoAnnotation;
import com.founder.framework.annotation.FieldDesc;


@DBInfoAnnotation(tableName = "DROOLS_RULEHIS", pk = "version")
public class Drools_ruleHis extends BaseModle implements Serializable {

	private static final long serialVersionUID = 1L;

	@FieldDesc("版本，归档时间") private String version;	
	@FieldDesc("分组ID") private String groupid;
	@FieldDesc("分组名") private String groupname;
	@FieldDesc("规则文件名") private String rulefilename;
	@FieldDesc("规则内容") private String content;
	@FieldDesc("备注") private String bz;
	@FieldDesc("使用时的规则ID，区分是否是同一个规则") private String ruleid;
	
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
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
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getRuleid() {
		return ruleid;
	}
	public void setRuleid(String ruleid) {
		this.ruleid = ruleid;
	}

	
}
