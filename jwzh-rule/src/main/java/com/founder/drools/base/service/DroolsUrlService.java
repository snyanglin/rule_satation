package com.founder.drools.base.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.founder.drools.base.dao.Drools_urlDao;
import com.founder.drools.base.model.Drools_url;
import com.founder.drools.core.request.HttpRequestBean;

@Service
public class DroolsUrlService{
		
	@Resource(name = "drools_urlDao")
	private Drools_urlDao drools_urlDao;
	
	private HttpRequestBean httpRequestBean= new HttpRequestBean("init");
	
	
	public Drools_url queryUrlById(String ID) {
		return drools_urlDao.queryById(ID);
	}
	
	public Drools_url queryUrlByEntity(Drools_url entity) {
		return drools_urlDao.queryByEntity(entity);
	}
	
	public List<Drools_url> queryUrlList(Drools_url entity) {	
		if(entity == null) entity= new Drools_url();		
		return drools_urlDao.queryListByEntity(entity);
	}
	
	public void addUrl(Drools_url entity) {		
		entity.setCreatetime(new Date());				
		entity.setId(getTimeString());
		drools_urlDao.insert(entity);
	}
	
	public void updateUrl(Drools_url entity) {		
		entity.setUpdatetime(new Date());		
		drools_urlDao.update(entity);
	}
		

	public String getTimeString(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date());
	}
	
	public void deleteUrl(String id){
		drools_urlDao.delete(id);
	}

	public void urlValidate(String url) throws Exception {
		httpRequestBean.setServiceUrl(url);
		httpRequestBean.doHttpGet(null);		
	}

	
}
