package com.founder.drools.base.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.founder.drools.base.common.BaseModelUtils;
import com.founder.drools.base.dao.Drools_serviceDao;
import com.founder.drools.base.model.Drools_service;

@Service
public class DroolsServiceService{
		
	@Resource(name = "drools_serviceDao")
	private Drools_serviceDao drools_serviceDao;
	
	public Drools_service queryServiceById(String ID) {
		return drools_serviceDao.queryById(ID);
	}
	
	public Drools_service queryServiceByEntity(Drools_service entity) {
		return drools_serviceDao.queryByEntity(entity);
	}
	
	public List<Drools_service> queryServiceList(Drools_service entity) {	
		if(entity == null) entity= new Drools_service();		
		return drools_serviceDao.queryListByEntity(entity);
	}
	
	public void addService(Drools_service entity) {		
		BaseModelUtils.setSaveProperty(entity);	
		entity.setId(BaseModelUtils.getTimeString());
		drools_serviceDao.insert(entity);
	}
	
	public void updateService(Drools_service entity) {		
		entity.setUpdatetime(new Date());		
		drools_serviceDao.update(entity);
	}
	
	public void deleteService(String id){
		drools_serviceDao.delete(id);
	}

	/**
	 * 
	 * @Title: countServiceNum
	 * @Description: TODO(统计服务数)
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throw
	 */
	public int countServiceNum() {		
		return drools_serviceDao.countServiceNum();
	}
}
