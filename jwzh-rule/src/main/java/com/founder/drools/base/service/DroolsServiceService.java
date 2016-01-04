package com.founder.drools.base.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.founder.drools.base.common.BaseModelUtils;
import com.founder.drools.base.dao.Drools_serviceDao;
import com.founder.drools.base.model.Drools_service;
import com.founder.framework.utils.UUID;

/**
 * ****************************************************************************
 * @Package:      [com.founder.drools.base.service.DroolsServiceService.java]  
 * @ClassName:    [DroolsServiceService]   
 * @Description:  [远程应用系统服务管理服务类]   
 * @Author:       [zhang.hai@founder.com.cn]  
 * @CreateDate:   [2015年12月2日 下午5:20:33]   
 * @UpdateUser:   [ZhangHai(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2015年12月2日 下午5:20:33，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
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
	
	/**
	 * 
	 * @Title: addService
	 * @Description: TODO(服务添加，新增ID)
	 * @param @param entity    设定文件
	 * @return void    返回类型
	 * @throw
	 */
	public void addService(Drools_service entity) {		
		BaseModelUtils.setSaveProperty(entity);	
		entity.setId(UUID.create());
		drools_serviceDao.insert(entity);
	}
	
	/**
	 * 
	 * @Title: addServiceList
	 * @Description: TODO(批量添加)
	 * @param @param list    设定文件
	 * @return void    返回类型
	 * @throw
	 */
	public void addServiceList(List<Drools_service>	list) {		
		for(Drools_service entity:list){
			BaseModelUtils.setSaveProperty(entity);
			drools_serviceDao.insert(entity);
		}
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
