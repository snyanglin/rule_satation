package com.founder.drools.base.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.founder.drools.base.model.Drools_service;
import com.founder.framework.base.dao.BaseDaoImpl;


@Repository("drools_serviceDao")
public class Drools_serviceDao extends BaseDaoImpl {
	
	public void insert(Drools_service entity) {
		trimEntity(entity);
		super.insert("Drools_service.save", entity);
	}

	
	public void update(Drools_service entity) {
		trimEntity(entity);
		super.update("Drools_service.update", entity);
	}
	
	public void delete(String id) {
		super.delete("Drools_service.delete", id);
	}

	public Drools_service queryById(String entityId) {
		Drools_service entity=new Drools_service();
		entity.setId(entityId);
		return (Drools_service)super.queryForObject("Drools_service.queryDroolsService", entity);		
	}
	
	public Drools_service queryByEntity(Drools_service entity) {		
		trimEntity(entity);
		return (Drools_service)super.queryForObject("Drools_service.queryDroolsService", entity);		
	}
	
	public List<Drools_service> queryListByEntity(Drools_service entity) {
		trimEntity(entity);
		return (List<Drools_service>)super.queryForList("Drools_service.queryDroolsService", entity);
	}

	private void trimEntity(Drools_service entity){
		if(entity.getServicename()!=null)
			entity.setServicename(entity.getServicename().trim());
	}
}
