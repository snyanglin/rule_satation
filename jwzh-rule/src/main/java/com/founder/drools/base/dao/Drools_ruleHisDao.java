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

	/**
	 * 
	 * @Title: queryRuleHisList
	 * @Description: TODO(查询列表)
	 * @param @param entity
	 * @param @return    设定文件
	 * @return List<Drools_ruleHis>    返回类型
	 * @throw
	 */
	public List<Drools_ruleHis> queryRuleHisList(Drools_ruleHis entity){
		return super.queryForList("Drools_ruleHis.queryRuleHis",entity);
	}
	
	/**
	 * 
	 * @Title: queryDroolsRuleHisManagerList
	 * @Description: TODO(查询规则文件列表,取最大版本号)
	 * @param @param entity
	 * @param @return    设定文件
	 * @return List<Drools_ruleHis>    返回类型
	 * @throw
	 */
	public List<Drools_ruleHis> queryDroolsRuleHisManagerList(Drools_ruleHis entity){
		return super.queryForList("Drools_ruleHis.queryRuleHisManagerList",entity);
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
		return super.queryForList("Drools_ruleHis.queryRuleHisGroup");
	}
	
	/**
	 * 
	 * @Title: queryExportList
	 * @Description: TODO(查询导出规则列表)
	 * @param @param groupid
	 * @param @return    设定文件
	 * @return List<Drools_ruleHis>    返回类型
	 * @throw
	 */
	public List<Drools_ruleHis> queryExportList(String groupid){
		return super.queryForList("Drools_ruleHis.queryExportList",groupid);
	}
}
