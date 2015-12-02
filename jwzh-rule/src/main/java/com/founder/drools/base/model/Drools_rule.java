package com.founder.drools.base.model;

import java.io.Serializable;

import com.founder.framework.annotation.DBInfoAnnotation;
import com.founder.framework.annotation.FieldDesc;


@DBInfoAnnotation(tableName = "DROOLS_RULE", pk = "id")
public class Drools_rule extends BaseModle implements Serializable {

	private static final long serialVersionUID = 1L;

	@FieldDesc("规则ID") private String id;
	@FieldDesc("规则文件名称") private String rulefilename;
	@FieldDesc("规则名称") private String rulename;
	@FieldDesc("分组ID") private String groupid;
	@FieldDesc("规则内容") private String content;
	@FieldDesc("返回内容") private String paramstr;
	@FieldDesc("状态：0正常，1未验证，3未发布，9历史版本") private String status;
	@FieldDesc("版本：新版本发布，老版本状态变成9的时间") private String version;
	@FieldDesc("备注") private String bz;
	@FieldDesc("规则类型：0规则头，1规则体") private String ruletype;
	
	@FieldDesc("分组名") private String groupname;
		
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}	
	public String getRulefilename() {
		return rulefilename;
	}
	public void setRulefilename(String rulefilename) {
		this.rulefilename = rulefilename;
	}
	public String getRulename() {
		return rulename;
	}
	public void setRulename(String rulename) {
		this.rulename = rulename;
	}
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getParamstr() {
		return paramstr;
	}
	public void setParamstr(String paramstr) {
		this.paramstr = paramstr;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getRuletype() {
		return ruletype;
	}
	public void setRuletype(String ruletype) {
		this.ruletype = ruletype;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	
}
