package com.founder.drools.base.dao;

import org.springframework.stereotype.Repository;

import com.founder.drools.core.request.RuleLogBean;
import com.founder.framework.base.dao.BaseDaoImpl;

/**
 * 
 * @author Administrator
 *
 */
@Repository("drools_logDao")
public class Drools_logDao extends BaseDaoImpl {
	
	public void saveLog(RuleLogBean ruleLog){
		super.insert("Drools_log.save", ruleLog);
	}
	
}
