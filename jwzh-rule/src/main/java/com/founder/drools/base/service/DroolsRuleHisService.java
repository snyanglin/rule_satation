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
	
	/**
	 * 
	 * @Title: queryRuleHisGroup
	 * @Description: TODO(查询分组列表)
	 * @param @return    设定文件
	 * @return List<Drools_ruleHis>    返回类型
	 * @throw
	 */
	public List<Drools_ruleHis> queryRuleHisGroup(){
		return drools_ruleHisDao.queryRuleHisGroup();
	}
}
