package com.founder.drools.base.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.founder.drools.base.common.BaseModelUtils;
import com.founder.drools.base.dao.Drools_urlDao;
import com.founder.drools.base.model.Drools_url;
import com.founder.drools.core.request.HttpRequestBean;
import com.founder.framework.utils.UUID;

/**
 * ****************************************************************************
 * @Package:      [com.founder.drools.base.service.DroolsUrlService.java]  
 * @ClassName:    [DroolsUrlService]   
 * @Description:  [远程应用系统地址管理服务]   
 * @Author:       [zhang.hai@founder.com.cn]  
 * @CreateDate:   [2015年12月2日 下午5:20:50]   
 * @UpdateUser:   [ZhangHai(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2015年12月2日 下午5:20:50，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
@Service
public class DroolsUrlService{
		
	@Resource(name = "drools_urlDao")
	private Drools_urlDao drools_urlDao;
	
	private HttpRequestBean httpRequestBean= new HttpRequestBean("init");
	
	
	public Drools_url queryUrlById(String ID) {
		return drools_urlDao.queryById(ID);
	}
	
	/**
	 * 
	 * @Title: queryUrlByEntity
	 * @Description: TODO(精确查询单条，如果查询出多条会出错)
	 * @param @param entity
	 * @param @return    设定文件
	 * @return Drools_url    返回类型
	 * @throw
	 */
	public Drools_url queryUrlByEntity(Drools_url entity) {
		return drools_urlDao.queryByEntity(entity);
	}
	
	/**
	 * 
	 * @Title: queryUrlList
	 * @Description: TODO(精确查询列表)
	 * @param @param entity
	 * @param @return    设定文件
	 * @return List<Drools_url>    返回类型
	 * @throw
	 */
	public List<Drools_url> queryUrlList(Drools_url entity) {	
		if(entity == null) entity= new Drools_url();		
		return drools_urlDao.queryListByEntity(entity);
	}
	
	/**
	 * 
	 * @Title: queryUrlListFuzzy
	 * @Description: TODO(urlname和url模糊查询列表)
	 * @param @param entity
	 * @param @return    设定文件
	 * @return List<Drools_url>    返回类型
	 * @throw
	 */
	public List<Drools_url> queryUrlListFuzzy(Drools_url entity) {	
		if(entity == null) entity= new Drools_url();		
		return drools_urlDao.queryListByEntityFuzzy(entity);
	}
	
	/**
	 * 
	 * @Title: addUrl
	 * @Description: TODO(新增地址)
	 * @param @param entity    设定文件
	 * @return void    返回类型
	 * @throw
	 */
	public void addUrl(Drools_url entity) {		
		BaseModelUtils.setSaveProperty(entity);		
		entity.setId(UUID.create());
		drools_urlDao.insert(entity);
	}
	
	public void updateUrl(Drools_url entity) {		
		entity.setUpdatetime(new Date());		
		drools_urlDao.update(entity);
	}
		
	public void deleteUrl(String id){
		drools_urlDao.delete(id);
	}

	/**
	 * 
	 * @Title: urlValidate
	 * @Description: TODO(验证地址是否可连接)
	 * @param @param url
	 * @param @throws Exception    设定文件
	 * @return void    返回类型
	 * @throw
	 */
	public void urlValidate(String url) throws Exception {
		httpRequestBean.setServiceUrl(url);
		httpRequestBean.doHttpGet(null);		
	}

	/**
	 * 
	 * @Title: countUrlNum
	 * @Description: TODO(统计地址数)
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throw
	 */
	public int countUrlNum() {		
		return drools_urlDao.countUrlNum();
	}
	
	/**
	 * 
	 * @Title: addUrlList
	 * @Description: TODO(批量添加)
	 * @param @param list    设定文件
	 * @return void    返回类型
	 * @throw
	 */
	public void addUrlList(List<Drools_url> list) {	
		for(Drools_url entity:list){
			BaseModelUtils.setSaveProperty(entity);				
			drools_urlDao.insert(entity);
		}
	}
	
}
