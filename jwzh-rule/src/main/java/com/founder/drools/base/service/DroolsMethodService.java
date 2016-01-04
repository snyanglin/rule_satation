package com.founder.drools.base.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.founder.drools.base.common.BaseModelUtils;
import com.founder.drools.base.dao.Drools_methodDao;
import com.founder.drools.base.model.Drools_method;
import com.founder.framework.utils.UUID;

/**
 * ****************************************************************************
 * @Package:      [com.founder.drools.base.service.DroolsMethodService.java]  
 * @ClassName:    [DroolsMethodService]   
 * @Description:  [方法管理服务]   
 * @Author:       [zhang.hai@founder.com.cn]  
 * @CreateDate:   [2015年12月2日 下午5:18:59]   
 * @UpdateUser:   [ZhangHai(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2015年12月2日 下午5:18:59，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
@Service
public class DroolsMethodService{
		
	@Resource(name = "drools_methodDao")
	private Drools_methodDao drools_methodDao;
	
	public Drools_method queryMethodById(String ID) {
		return drools_methodDao.queryById(ID);
	}
	
	public Drools_method queryMethodByEntity(Drools_method entity) {
		return drools_methodDao.queryByEntity(entity);
	}
	
	public List<Drools_method> queryMethodList(Drools_method entity) {	
		if(entity == null) entity= new Drools_method();		
		return drools_methodDao.queryListByEntity(entity);
	}
	
	public List<Drools_method> getMethodManagerList(Drools_method entity) {	
		if(entity == null) entity= new Drools_method();		
		return drools_methodDao.queryListByEntity(entity);
	}
	
	public void addMethod(Drools_method entity) {		
		BaseModelUtils.setSaveProperty(entity);			
		entity.setId(UUID.create());
		drools_methodDao.insert(entity);
	}
	
	public void updateMethod(Drools_method entity) {		
		entity.setUpdatetime(new Date());		
		drools_methodDao.update(entity);
	}
		
	public void deleteMethod(String id){
		drools_methodDao.delete(id);
	}		
	
	/**
	 * 
	 * @Title: countMethodNum
	 * @Description: TODO(统计方法数)
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throw
	 */
	public int countMethodNum() {		
		return drools_methodDao.countMethodNum();
	}

}
