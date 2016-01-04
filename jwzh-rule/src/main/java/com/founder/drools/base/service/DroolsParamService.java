package com.founder.drools.base.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.founder.drools.base.common.BaseModelUtils;
import com.founder.drools.base.dao.Drools_method_parameterDao;
import com.founder.drools.base.model.Drools_method_parameter;
import com.founder.framework.utils.UUID;

/**
 * ****************************************************************************
 * @Package:      [com.founder.drools.base.service.DroolsParamService.java]  
 * @ClassName:    [DroolsParamService]   
 * @Description:  [方法的参数管理服务]   
 * @Author:       [zhang.hai@founder.com.cn]  
 * @CreateDate:   [2015年12月2日 下午5:19:11]   
 * @UpdateUser:   [ZhangHai(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2015年12月2日 下午5:19:11，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
@Service
public class DroolsParamService{
		
	@Resource(name = "drools_method_parameterDao")
	private Drools_method_parameterDao drools_method_parameterDao;
	
	public List<Drools_method_parameter> getParamList(String methodid){
		Drools_method_parameter entity= new Drools_method_parameter();
		entity.setMethodid(methodid);
		return drools_method_parameterDao.queryListByEntity(entity);
	}
	
	/**
	 * 
	 * @Title: queryParamList
	 * @Description: TODO(导出时使用，查询所有数据)
	 * @param @return    设定文件
	 * @return List<Drools_method_parameter>    返回类型
	 * @throw
	 */
	public List<Drools_method_parameter> queryParamList(){
		Drools_method_parameter entity= new Drools_method_parameter();
		return drools_method_parameterDao.queryListByEntity(entity);
	}
	
	/**
	 * 
	 * @Title: addParam
	 * @Description: TODO(添加方法参数)
	 * @param @param list
	 * @param @param methodid    设定文件
	 * @return void    返回类型
	 * @throw
	 */
	public void addParam(List<Drools_method_parameter> list,String methodid){
		drools_method_parameterDao.deleteByMethodId(methodid);//删除当前方法的所有参数，再添加新的参数
		Drools_method_parameter entity;
		if(list!=null)
			for(int i=0;i<list.size();i++){
				entity = list.get(i);
				BaseModelUtils.setSaveProperty(entity);
				entity.setId(UUID.create());
				entity.setMethodid(methodid);				
				drools_method_parameterDao.insert(entity);
			}
	}
	
	public void addParam(List<Drools_method_parameter> list){
		for(Drools_method_parameter entity:list){
			BaseModelUtils.setSaveProperty(entity);
			drools_method_parameterDao.insert(entity);
		}
	}
	
	/**
	 * 
	 * @Title: getParamList
	 * @Description: TODO(将String类型的参数集合转成List参数集合)
	 * @param @param paramname
	 * @param @param paramclass
	 * @param @param parambz
	 * @param @return    设定文件
	 * @return List<Drools_method_parameter>    返回类型
	 * @throw
	 */
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

}
