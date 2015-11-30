package com.founder.drools.base.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.founder.drools.base.model.Drools_method_parameter;
import com.founder.framework.base.dao.BaseDaoImpl;


@Repository("drools_method_parameterDao")
public class Drools_method_parameterDao extends BaseDaoImpl {
	
	public void insert(Drools_method_parameter entity) {
		trimEntity(entity);
		super.insert("Drools_method_parameter.save", entity);
	}
	
	public void update(Drools_method_parameter entity) {
		trimEntity(entity);
		super.update("Drools_method_parameter.update", entity);
	}
	
	public void delete(String id) {
		super.delete("Drools_method_parameter.delete", id);
	}
	
	public void deleteByMethodId(String methodId) {
		super.delete("Drools_method_parameter.deleteByMethodId", methodId);
	}

	public Drools_method_parameter queryById(String entityId) {
		Drools_method_parameter entity=new Drools_method_parameter();
		entity.setId(entityId);
		return (Drools_method_parameter)super.queryForObject("Drools_method_parameter.queryDroolsParameter", entity);		
	}
	
	public Drools_method_parameter queryByEntity(Drools_method_parameter entity) {		
		trimEntity(entity);
		return (Drools_method_parameter)super.queryForObject("Drools_method_parameter.queryDroolsParameter", entity);		
	}
	
	public List<Drools_method_parameter> queryListByEntity(Drools_method_parameter entity) {
		trimEntity(entity);
		return (List<Drools_method_parameter>)super.queryForList("Drools_method_parameter.queryDroolsParameter", entity);
	}

	private void trimEntity(Drools_method_parameter entity){
		if(entity.getParamname()!=null)
			entity.setParamname(entity.getParamname().trim());
		if(entity.getParamclass()!=null)
			entity.setParamclass(entity.getParamclass().trim());
	}
}
