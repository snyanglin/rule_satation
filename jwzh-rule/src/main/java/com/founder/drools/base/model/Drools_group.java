package com.founder.drools.base.model;

import java.io.Serializable;
import java.util.List;

import com.founder.framework.annotation.DBInfoAnnotation;
import com.founder.framework.annotation.FieldDesc;


@DBInfoAnnotation(tableName = "DROOLS_GROUP", pk = "id")
public class Drools_group extends BaseModle implements Serializable {

	private static final long serialVersionUID = -5351327439931807100L;
	
	@FieldDesc("方法ID") private String id;
	@FieldDesc("分组名") private String groupname;	
	@FieldDesc("备注") private String bz;	
	
	private List ruleFileList;//规则文件列表
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
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public List getRuleFileList() {
		return ruleFileList;
	}
	public void setRuleFileList(List ruleFileList) {
		this.ruleFileList = ruleFileList;
	}
		
}
