package com.founder.drools.core.model;

import java.util.Map;
/**
 * ****************************************************************************
 * @Package:      [com.founder.drools.core.model.RuleBean.java]  
 * @ClassName:    [RuleBean]   
 * @Description:  [规则对象，保存规则和返回值]   
 * @Author:       [zhang.hai@founder.com.cn]  
 * @CreateDate:   [2015年11月6日 下午4:57:39]   
 * @UpdateUser:   [ZhangHai(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2015年11月6日 下午4:57:39，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
public class RuleBean {	
	
	private String ruleFileName;//规则文件名
	private String ruleName;//规则名
	private String serviceUrl;//服务地址
	private String serviceMethod;//服务方法
	private int resStatus=1;//0成功 1失败
	private Object response;//返回对象
	private Object paramObj;
	private Map globalParamMap;
	
	
	public String getServiceMethod() {
		return serviceMethod;
	}
	public void setServiceMethod(String serviceMethod) {
		this.serviceMethod = serviceMethod;
	}
	public String getServiceUrl() {
		return serviceUrl;
	}
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
	public String getRuleFileName() {
		return ruleFileName;
	}
	public void setRuleFileName(String ruleFileName) {
		this.ruleFileName = ruleFileName;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}	
	public int getResStatus() {
		return resStatus;
	}
	public void setResStatus(int resStatus) {
		this.resStatus = resStatus;
	}
	public Object getResponse() {
		return response;
	}
	public void setResponse(Object response) {
		this.response = response;
	}
	public Object getParamObj() {
		return paramObj;
	}
	public void setParamObj(Object paramObj) {
		this.paramObj = paramObj;
	}
	public Map getGlobalParamMap() {
		return globalParamMap;
	}
	public void setGlobalParamMap(Map globalParamMap) {
		this.globalParamMap = globalParamMap;
	}
	
	
}
