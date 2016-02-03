package com.founder.drools.core.inteface.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.founder.drools.base.dao.Drools_logDao;
import com.founder.drools.core.request.RuleLogBean;
import com.founder.framework.base.entity.SessionBean;
import com.founder.framework.entity.OperationLog;
import com.founder.framework.entity.ServiceRestLog;
import com.founder.framework.operationLog.service.OperationLogService;
import com.founder.framework.utils.UUID;

public class DroolsLogServiceImpl implements OperationLogService {
	@Resource(name = "drools_logDao")
	private Drools_logDao drools_logDao;
	@Override
	public void countByMod(List<Map<String, Object>> arg0, String arg1,
			String arg2, List<String> arg3, int arg4) {
		System.out.println("countByMod");

	}

	@Override
	public void countByOrg(List<Map<String, Object>> arg0, String arg1,
			String arg2, List<String> arg3, int arg4) {
		System.out.println("countByOrg");

	}

	@Override
	public void countByResult(List<Map<String, Object>> arg0, String arg1,
			String arg2, List<String> arg3) {
		System.out.println("countByResult");

	}

	@Override
	public void countByType(List<Map<String, Object>> arg0, String arg1,
			String arg2, List<String> arg3) {
		System.out.println("countByType");

	}

	@Override
	public void insertOperationLog(String arg0, String arg1, int arg2,
			String arg3, Boolean arg4, SessionBean arg5, String arg6,
			String arg7, String arg8, String arg9, String arg10, String arg11) {
			RuleLogBean ruleLog =new RuleLogBean();
			String id = UUID.create();
			String functionModel =arg0;
			String functionName =arg1;
			String paramStr =arg3;
			String parTem = paramStr.substring(paramStr.indexOf(":")+2,paramStr.indexOf("ruleFileName")-3);
			String fileRuleName = paramStr.substring(paramStr.indexOf("ruleFileName")+15, paramStr.indexOf("ruleName")-3);
			String ruleName= paramStr.substring(paramStr.indexOf("ruleName")+11, paramStr.indexOf("resStatus")-3);
			String exMessage  = arg6;
			ruleLog.setId(id);
			ruleLog.setExMessage(exMessage);
			ruleLog.setModeName(functionModel);
			ruleLog.setFuncName(functionName);
			ruleLog.setRuleFileName(fileRuleName);
			ruleLog.setUpdateTime(new Date());
			ruleLog.setRuleName(ruleName);
			ruleLog.setParamStr(parTem);
			drools_logDao.saveLog(ruleLog);

	}

	@Override
	public void insertServiceLog(ServiceRestLog arg0) {
		System.out.println("insertServiceLog");

	}

	@Override
	public Map<String, Object> queryObj(String arg0, String arg1, Object arg2) {
		System.out.println("queryObj");
		return null;
	}

	@Override
	public OperationLog queryOperationLogById(String arg0) {
		System.out.println("queryOperationLogById");
		return null;
	}

	@Override
	public List<OperationLog> queryOperationLogListByEntity(OperationLog arg0) {
		System.out.println("queryOperationLogListByEntity");
		return null;
	}

	@Override
	public void removeOperationLogById(String arg0) {
		System.out.println("removeOperationLogById");

	}

}
