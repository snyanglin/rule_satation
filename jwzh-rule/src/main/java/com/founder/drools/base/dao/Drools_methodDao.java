package com.founder.drools.base.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.founder.drools.base.model.Drools_method;
import com.founder.framework.base.dao.BaseDaoImpl;


@Repository("drools_methodDao")
public class Drools_methodDao extends BaseDaoImpl {
	
	public void insert(Drools_method entity) {
		trimEntity(entity);
		super.insert("Drools_method.save", entity);
	}

	
	public void update(Drools_method entity) {
		trimEntity(entity);
		super.update("Drools_method.update", entity);
	}
	
	public void delete(String id) {
		super.delete("Drools_method.delete", id);
	}

	public Drools_method queryById(String entityId) {
		Drools_method entity=new Drools_method();
		entity.setId(entityId);
		return (Drools_method)super.queryForObject("Drools_method.queryDroolsMethod", entity);		
	}
	
	public Drools_method queryByEntity(Drools_method entity) {		
		trimEntity(entity);
		return (Drools_method)super.queryForObject("Drools_method.queryDroolsMethod", entity);		
	}
	
	public List<Drools_method> queryListByEntity(Drools_method entity) {
		trimEntity(entity);
		return (List<Drools_method>)super.queryForList("Drools_method.queryDroolsMethod", entity);
	}

	private void trimEntity(Drools_method entity){
		if(entity.getMethodname()!=null)
			entity.setMethodname(entity.getMethodname().trim());
	}

}
