package com.founder.drools.core.request;

import java.util.Date;

public class RuleLogBean {
	private String id;
	private String modeName;
	private String funcName;
	private String ruleFileName;//规则文件名
	private String ruleName;//规则名
	private Date updateTime;
	private String paramStr;
	private String exMessage;
	public String getId() {
		return id;
	}
	public String getModeName() {
		return modeName;
	}
	public String getFuncName() {
		return funcName;
	}
	public String getRuleFileName() {
		return ruleFileName;
	}
	public String getRuleName() {
		return ruleName;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public String getParamStr() {
		return paramStr;
	}
	public String getExMessage() {
		return exMessage;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setModeName(String modeName) {
		this.modeName = modeName;
	}
	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}
	public void setRuleFileName(String ruleFileName) {
		this.ruleFileName = ruleFileName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public void setParamStr(String paramStr) {
		this.paramStr = paramStr;
	}
	public void setExMessage(String exMessage) {
		this.exMessage = exMessage;
	}

	
}
