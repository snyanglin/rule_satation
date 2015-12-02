package com.founder.drools.base.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.founder.drools.base.model.Drools_group;
import com.founder.framework.base.dao.BaseDaoImpl;


@Repository("drools_groupDao")
public class Drools_groupDao extends BaseDaoImpl {
	
	
	public void insert(Drools_group entity) {
		trimEntity(entity);
		super.insert("Drools_group.save", entity);
	}

	public void update(Drools_group entity) {
		trimEntity(entity);
		super.update("Drools_group.update", entity);
	}
	
	public void delete(String id) {
		super.delete("Drools_group.delete", id);
	}

	/**
	 * 
	 * @Title: queryByEntity
	 * @Description: TODO(查询单个分组对象)
	 * @param @param entity
	 * @param @return    设定文件
	 * @return Drools_group    返回类型
	 * @throw
	 */
	public Drools_group queryByEntity(Drools_group entity) {
		trimEntity(entity);
		return (Drools_group)super.queryForObject("Drools_group.queryDroolsGroup", entity);
	}
	
	/**
	 * 
	 * @Title: queryListByEntity
	 * @Description: TODO(精确查询分组列表)
	 * @param @param entity
	 * @param @return    设定文件
	 * @return List<Drools_group>    返回类型
	 * @throw
	 */
	public List<Drools_group> queryListByEntity(Drools_group entity) {
		trimEntity(entity);
		return (List<Drools_group>)super.queryForList("Drools_group.queryDroolsGroup", entity);
	}

	/**
	 * 
	 * @Title: trimEntity
	 * @Description: TODO(清楚对象中的空格)
	 * @param @param entity    设定文件
	 * @return void    返回类型
	 * @throw
	 */
	private void trimEntity(Drools_group entity){
		if(entity.getGroupname()!=null)
			entity.setGroupname(entity.getGroupname().trim());
	}

	/**
	 * 
	 * @Title: queryListByEntityFuzzy
	 * @Description: TODO(模糊查询分组列表)
	 * @param @param entity
	 * @param @return    设定文件
	 * @return List<Drools_group>    返回类型
	 * @throw
	 */
	public List<Drools_group> queryListByEntityFuzzy(Drools_group entity) {
		trimEntity(entity);
		return (List<Drools_group>)super.queryForList("Drools_group.queryDroolsGroupFuzzy", entity);
	}
	
	public int countGroupNum() {
		return (int) super.queryForObject("Drools_group.countGroupNum",null);
	}
}
