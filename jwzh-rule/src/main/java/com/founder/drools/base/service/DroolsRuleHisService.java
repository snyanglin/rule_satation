package com.founder.drools.base.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.founder.drools.base.dao.Drools_ruleHisDao;
import com.founder.drools.base.model.Drools_ruleHis;

@Service
public class DroolsRuleHisService{

	@Resource(name = "drools_ruleHisDao")
	private Drools_ruleHisDao drools_ruleHisDao;
	
	public void insert(Drools_ruleHis entity){		
		
		//查询分组名
		
		//查询服务信息
		
		drools_ruleHisDao.insert(entity);
	}
	
	public Drools_ruleHis queryRuleHis(Drools_ruleHis entity){
		return drools_ruleHisDao.queryRuleHis(entity);
	}

	public List<Drools_ruleHis> queryRuleHisList(Drools_ruleHis entity) {
		return drools_ruleHisDao.queryRuleHisList(entity);
	}
	
	public List<Drools_ruleHis> queryRuleHisManagerList(Drools_ruleHis entity) {
		return drools_ruleHisDao.queryDroolsRuleHisManagerList(entity);
	}
}
