package com.founder.drools.base.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.founder.drools.base.model.Drools_ruleHis;
import com.founder.framework.base.dao.BaseDaoImpl;


@Repository("drools_ruleHisDao")
public class Drools_ruleHisDao extends BaseDaoImpl {
	
	
	public void insert(Drools_ruleHis entity) {
		super.insert("Drools_ruleHis.insert", entity);
	}
	
	public Drools_ruleHis queryRuleHis(Drools_ruleHis entity){
		return (Drools_ruleHis) super.queryForObject("Drools_ruleHis.queryRuleHis", entity);
	}

	public List<Drools_ruleHis> queryRuleHisList(Drools_ruleHis entity){
		return super.queryForList("Drools_ruleHis.queryRuleHis",entity);
	}
	
	public List<Drools_ruleHis> queryDroolsRuleHisManagerList(Drools_ruleHis entity){
		return super.queryForList("Drools_ruleHis.queryRuleHisManagerList",entity);
	}
}
