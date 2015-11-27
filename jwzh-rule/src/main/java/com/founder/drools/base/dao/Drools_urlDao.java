package com.founder.drools.base.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.founder.drools.base.model.Drools_url;
import com.founder.framework.base.dao.BaseDaoImpl;


@Repository("drools_urlDao")
public class Drools_urlDao extends BaseDaoImpl {
	
	public void insert(Drools_url entity) {
		trimEntity(entity);
		super.insert("Drools_url.saveUrl", entity);
	}

	
	public void update(Drools_url entity) {
		trimEntity(entity);
		super.update("Drools_url.updateUrl", entity);
	}
	
	public void delete(String id) {
		super.delete("Drools_url.deleteUrl", id);
	}

	public Drools_url queryById(String entityId) {
		Drools_url entity=new Drools_url();
		entity.setId(entityId);
		return (Drools_url)super.queryForObject("Drools_url.queryDroolsUrl", entity);		
	}
	
	public Drools_url queryByEntity(Drools_url entity) {		
		trimEntity(entity);
		return (Drools_url)super.queryForObject("Drools_url.queryDroolsUrl", entity);		
	}
	
	public List<Drools_url> queryListByEntity(Drools_url entity) {
		trimEntity(entity);
		return (List<Drools_url>)super.queryForList("Drools_url.queryDroolsUrl", entity);
	}

	private void trimEntity(Drools_url entity){
		if(entity.getUrlname()!=null)
			entity.setUrlname(entity.getUrlname().trim());
	}
}
