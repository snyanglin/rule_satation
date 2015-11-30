package com.founder.drools.base.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.founder.drools.base.model.Drools_rule;
import com.founder.framework.base.dao.BaseDaoImpl;


@Repository("drools_ruleDao")
public class Drools_ruleDao extends BaseDaoImpl {
	
	public void insert(Drools_rule entity) {
		trimEntity(entity);
		super.insert("Drools_rule.saveRule", entity);
	}

	
	public void update(Drools_rule entity) {
		trimEntity(entity);
		super.update("Drools_rule.updateRule", entity);
	}

	public void updateByRuleFileName(Drools_rule entity) {
		trimEntity(entity);
		super.update("Drools_rule.updateRuleByRuleFileName", entity);
	}
	
	public void delete(String id) {
		super.delete("Drools_rule.deleteRule", id);
	}

	public void deleteByRuleFileName(String rulefilename) {
		super.delete("Drools_rule.deleteRuleByFileName", rulefilename.trim());
	}
	
	public Drools_rule queryById(String entityId) {
		Drools_rule entity=new Drools_rule();
		entity.setId(entityId);
		return (Drools_rule)super.queryForObject("Drools_rule.queryDroolsRule", entity);		
	}
	
	public Drools_rule queryByEntity(Drools_rule entity) {		
		trimEntity(entity);
		return (Drools_rule)super.queryForObject("Drools_rule.queryDroolsRule", entity);		
	}
	
	/**
	 * 
	 * @Title: queryListByEntity
	 * @Description: TODO(精确查询List)
	 * @param @param entity
	 * @param @return    设定文件
	 * @return List<Drools_rule>    返回类型
	 * @throw
	 */
	public List<Drools_rule> queryListByEntity(Drools_rule entity) {
		trimEntity(entity);
		return (List<Drools_rule>)super.queryForList("Drools_rule.queryDroolsRule", entity);
	}
	
	/**
	 * 
	 * @Title: queryListByEntityFuzzy
	 * @Description: TODO(模糊查询list)
	 * @param @param entity
	 * @param @return    设定文件
	 * @return List<Drools_rule>    返回类型
	 * @throw
	 */
	public List<Drools_rule> queryListByEntityFuzzy(Drools_rule entity) {
		trimEntity(entity);
		return (List<Drools_rule>)super.queryForList("Drools_rule.queryDroolsRuleFuzzy", entity);
	}

	private void trimEntity(Drools_rule entity){
		if(entity.getRulefilename()!=null)
			entity.setRulefilename(entity.getRulefilename().trim());
		
		if(entity.getRulename()!=null)
			entity.setRulename(entity.getRulename().trim());
	}

}
