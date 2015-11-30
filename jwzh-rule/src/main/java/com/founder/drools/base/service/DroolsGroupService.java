package com.founder.drools.base.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.founder.drools.base.dao.Drools_groupDao;
import com.founder.drools.base.model.Drools_group;

@Service
public class DroolsGroupService {
	@Resource(name = "drools_groupDao")
	private Drools_groupDao drools_groupDao;

	public void save(Drools_group entity){
		entity.setCreateTime(new Date());
		entity.setId(getTimeString());
		drools_groupDao.insert(entity);
	}
	
	public void update(Drools_group entity){
		entity.setUpdateTime(new Date());
		drools_groupDao.update(entity);
	}
	
	public List<Drools_group> queryListByEntity(Drools_group entity){
		if(entity==null) entity= new Drools_group();
		return drools_groupDao.queryListByEntity(entity);
	}
	
	public Drools_group queryById(String id){
		Drools_group entity = new Drools_group();
		entity.setId(id);
		return drools_groupDao.queryByEntity(entity);
	}
	
	public void deleteGroup(String id){
		drools_groupDao.delete(id);
	}
	
	public String getTimeString(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date());
	}
}
