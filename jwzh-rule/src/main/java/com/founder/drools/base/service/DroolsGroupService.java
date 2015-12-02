package com.founder.drools.base.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.founder.drools.base.common.BaseModelUtils;
import com.founder.drools.base.dao.Drools_groupDao;
import com.founder.drools.base.model.Drools_group;

/**
 * ****************************************************************************
 * @Package:      [com.founder.drools.base.service.DroolsGroupService.java]  
 * @ClassName:    [DroolsGroupService]   
 * @Description:  [分组管理服务]   
 * @Author:       [zhang.hai@founder.com.cn]  
 * @CreateDate:   [2015年12月2日 下午5:18:42]   
 * @UpdateUser:   [ZhangHai(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2015年12月2日 下午5:18:42，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
@Service
public class DroolsGroupService {
	@Resource(name = "drools_groupDao")
	private Drools_groupDao drools_groupDao;

	public void save(Drools_group entity){
		BaseModelUtils.setSaveProperty(entity);		
		entity.setId(BaseModelUtils.getTimeString());
		drools_groupDao.insert(entity);
	}
	
	public void update(Drools_group entity){
		BaseModelUtils.setUpdateProperty(entity);		
		drools_groupDao.update(entity);
	}
	
	/**
	 * 
	 * @Title: queryListByEntity
	 * @Description: TODO(精确查询List)
	 * @param @param entity
	 * @param @return    设定文件
	 * @return List<Drools_group>    返回类型
	 * @throw
	 */
	public List<Drools_group> queryListByEntity(Drools_group entity){
		if(entity==null) entity= new Drools_group();
		return drools_groupDao.queryListByEntity(entity);
	}
	
	/**
	 * 
	 * @Title: queryListByEntityFuzzy
	 * @Description: TODO(模糊查询List)
	 * @param @param entity
	 * @param @return    设定文件
	 * @return List<Drools_group>    返回类型
	 * @throw
	 */
	public List<Drools_group> queryListByEntityFuzzy(Drools_group entity){
		if(entity==null) entity= new Drools_group();
		return drools_groupDao.queryListByEntityFuzzy(entity);
	}
	
	public Drools_group queryById(String id){
		Drools_group entity = new Drools_group();
		entity.setId(id);
		return drools_groupDao.queryByEntity(entity);
	}
	
	public void deleteGroup(String id){
		drools_groupDao.delete(id);
	}
	
	/**
	 * 
	 * @Title: countGroupNum
	 * @Description: TODO(统计分组数)
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throw
	 */
	public int countGroupNum() {		
		return drools_groupDao.countGroupNum();
	}
}
