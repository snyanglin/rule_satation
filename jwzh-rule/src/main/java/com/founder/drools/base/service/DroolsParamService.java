package com.founder.drools.base.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.founder.drools.base.dao.Drools_method_parameterDao;
import com.founder.drools.base.model.Drools_method_parameter;

@Service
public class DroolsParamService{
		
	@Resource(name = "drools_method_parameterDao")
	private Drools_method_parameterDao drools_method_parameterDao;
	
	public List<Drools_method_parameter> getParamList(String methodid){
		Drools_method_parameter entity= new Drools_method_parameter();
		entity.setMethodid(methodid);
		return drools_method_parameterDao.queryListByEntity(entity);
	}
	
	public void addParam(List<Drools_method_parameter> list,String methodid){
		drools_method_parameterDao.deleteByMethodId(methodid);
		Drools_method_parameter entity;
		if(list!=null)
			for(int i=0;i<list.size();i++){
				entity = list.get(i);
				entity.setId(this.getTimeString());
				entity.setMethodid(methodid);
				entity.setCreatetime(new Date());
				drools_method_parameterDao.insert(entity);
			}
	}
	
	public List<Drools_method_parameter> getParamList(String paramname,String paramclass,String parambz){
		if(paramname!=null && paramname.length()>0){
			try{
				String[] paramnameAry=paramname.split(">>");
				String[] paramclassAry=paramclass.split(">>");
				String[] parambzAry=parambz.split(">>");
				if(paramnameAry.length==0) return null;
				
				List<Drools_method_parameter> list=new ArrayList<Drools_method_parameter>();
				for(int i=0;i<paramnameAry.length;i++){
					Drools_method_parameter entity=new Drools_method_parameter();
					entity.setParamname(paramnameAry[i]);
					entity.setParamclass(paramclassAry[i]);
					entity.setBz(parambzAry[i]);
					list.add(entity);
				}
				
				return list;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	public String getTimeString(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date());
	}

}
