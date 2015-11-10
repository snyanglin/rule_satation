package com.founder.drools.base.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.founder.drools.base.dao.Drools_ruleDao;
import com.founder.drools.base.model.Drools_rule;

@Service
public class DroolsRuleService{

	@Resource(name = "drools_ruleDao")
	private Drools_ruleDao drools_ruleDao;
	
	public Drools_rule queryRuleById(String ID) {
		return drools_ruleDao.queryById(ID);
	}
	
	public Drools_rule queryRuleByEntity(Drools_rule entity) {
		return drools_ruleDao.queryByEntity(entity);
	}
	
	public void updateRule(Drools_rule entity) {
		drools_ruleDao.update(entity);
	}
	
	public List<Drools_rule> queryRuleManagerList() {
		Drools_rule entity = new Drools_rule();
		entity.setRuletype(0);//查询规则显示列表
		return drools_ruleDao.queryListByEntity(entity);
	}
	
	public List<Drools_rule> queryRuleListByEntity(Drools_rule entity) {		 
		return drools_ruleDao.queryListByEntity(entity);
	}

}
