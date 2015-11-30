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

	public Drools_group queryByEntity(Drools_group entity) {
		trimEntity(entity);
		return (Drools_group)super.queryForObject("Drools_group.queryDroolsGroup", entity);
	}
	
	public List<Drools_group> queryListByEntity(Drools_group entity) {
		trimEntity(entity);
		return (List<Drools_group>)super.queryForList("Drools_group.queryDroolsGroup", entity);
	}

	private void trimEntity(Drools_group entity){
		if(entity.getGroupname()!=null)
			entity.setGroupname(entity.getGroupname().trim());
	}

	public List<Drools_group> queryListByEntityFuzzy(Drools_group entity) {
		trimEntity(entity);
		return (List<Drools_group>)super.queryForList("Drools_group.queryDroolsGroupFuzzy", entity);
	}
}
