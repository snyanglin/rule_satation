package com.founder.drools.base.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.founder.drools.base.model.Drools_service;
import com.founder.drools.base.model.Drools_url;
import com.founder.drools.base.service.DroolsServiceService;
import com.founder.drools.base.service.DroolsUrlService;
import com.founder.drools.core.model.Paginator;
import com.founder.framework.base.controller.BaseController;

/**
 * ****************************************************************************
 * @Package:      [com.founder.drools.base.controller.urlController.java]  
 * @ClassName:    [urlController]   
 * @Description:  [地址管理 控制器]   
 * @Author:       [zhang.hai@founder.com.cn]  
 * @CreateDate:   [2015年11月27日 上午10:04:11]   
 * @UpdateUser:   [ZhangHai(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2015年11月27日 上午10:04:11，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
@Controller
@RequestMapping("urlManager")
public class UrlController extends BaseController {
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private DroolsUrlService droolsUrlService;
	
	@Autowired
	private DroolsServiceService droolsServiceService;
	
	/**
	 * 
	 * @Title: urlManager
	 * @Description: TODO(地址管理页面)
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throw
	 */
	@RequestMapping(value = "/urlManager", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView urlManager(){
		ModelAndView mv = new ModelAndView("system/url/urlManager");		        
		return mv;
	}
	
	/**
	 * 
	 * @Title: getUrlManagerList
	 * @Description: TODO(模糊查询地址列表)
	 * @param @param entity
	 * @param @param paginator
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throw
	 */
	@RequestMapping(value = "/getUrlManagerList", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView getUrlManagerList(Drools_url entity,Paginator paginator){
		ModelAndView mv = new ModelAndView("system/url/urlManagerList");			
		List<Drools_url> list = droolsUrlService.queryUrlListFuzzy(entity);//模糊查询
		paginator.setList(list);
		mv.addObject("Paginator",paginator);	
		mv.addObject("Entity",entity);
		return mv;
	}
	
	/**
	 * 
	 * @Title: urlAddPre
	 * @Description: TODO(地址添加页面)
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throw
	 */
	@RequestMapping(value = "/urlAddPre", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView urlAddPre(){
		ModelAndView mv = new ModelAndView("system/url/urlAdd");		        
		return mv;
	
	}
	
	/**
	 * 
	 * @Title: urlAdd
	 * @Description: TODO(地址添加)
	 * @param @param entity
	 * @param @return    设定文件
	 * @return Map<String,String>    返回类型
	 * @throw
	 */
	@RequestMapping(value = "/urlAdd", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Map<String, String> urlAdd(Drools_url entity){
		Map<String, String> map = new HashMap<String, String>();
		map.put("resStatus", "0");//成功
		try{
			Drools_url queryEntity = new Drools_url();
			queryEntity.setUrlname(entity.getUrlname());
			List<Drools_url> list = droolsUrlService.queryUrlList(queryEntity);//精确查询地址名称
			if(list!=null && list.size()>0){
				map.put("resStatus", "1");//失败
				map.put("errorMsg", "地址名称已存在，请重新输入");//失败
				return map;
			}
			droolsUrlService.addUrl(entity);
			
		}catch(Exception e){
			logger.error(e.getLocalizedMessage(), e);
			map.put("resStatus", "1");//失败
			map.put("errorMsg", e.toString());//失败
		}
		return map;				
	}
	
	/**
	 * 
	 * @Title: urlEditPre
	 * @Description: TODO(地址编辑页面)
	 * @param @param id
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throw
	 */
	@RequestMapping(value = "/urlEditPre", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView urlEditPre(String id){
		ModelAndView mv = new ModelAndView("system/url/urlEdit");	
		Drools_url entity= droolsUrlService.queryUrlById(id);
		mv.addObject("entity",entity);		
		return mv;
	}	
	
	/**
	 * 
	 * @Title: urlEdit
	 * @Description: TODO(地址编辑)
	 * @param @param entity
	 * @param @return    设定文件
	 * @return Map<String,String>    返回类型
	 * @throw
	 */
	@RequestMapping(value = "/urlEdit", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Map<String, String> urlEdit(Drools_url entity){
		Map<String, String> map = new HashMap<String, String>();
		map.put("resStatus", "0");//成功
		try{
			Drools_url queryEntity = new Drools_url();
			queryEntity.setUrlname(entity.getUrlname());
			List<Drools_url> list = droolsUrlService.queryUrlList(queryEntity);
			if(list!=null && list.size()>0){
				if(!list.get(0).getId().equals(entity.getId())){
					map.put("resStatus", "1");//失败
					map.put("errorMsg", "地址名称已存在，请重新输入");//失败
					return map;
				}
			}
			
			droolsUrlService.updateUrl(entity);
			
		}catch(Exception e){
			logger.error(e.getLocalizedMessage(), e);
			map.put("resStatus", "1");//失败
			map.put("errorMsg", e.toString());//失败
		}
		return map;
	}
	
	/**
	 * 
	 * @Title: urlDelete
	 * @Description: TODO(地址删除)
	 * @param @param id
	 * @param @return    设定文件
	 * @return Map<String,String>    返回类型
	 * @throw
	 */
	@RequestMapping(value = "/urlDelete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Map<String, String> urlDelete(String id){
		Map<String, String> map = new HashMap<String, String>();
		map.put("resStatus", "0");//成功
		try{		
			Drools_service queryEntity=new Drools_service();
			queryEntity.setUrlid(id);
			List<Drools_service> list = droolsServiceService.queryServiceList(queryEntity);
			if(list!=null && list.size()>0){
				map.put("resStatus", "1");//失败
				map.put("errorMsg", "该地址已被服务占用，如果要删除该地址，请先删除占用的服务！");
				return map;
			}
			
			droolsUrlService.deleteUrl(id);			
		}catch(Exception e){
			logger.error(e.getLocalizedMessage(), e);
			map.put("resStatus", "1");//失败
			map.put("errorMsg", e.toString());//失败
			return map;
		}
		return map;
	}
	
	/**
	 * 
	 * @Title: urlValidate
	 * @Description: TODO(地址验证)
	 * @param @param url
	 * @param @return    设定文件
	 * @return Map<String,String>    返回类型
	 * @throw
	 */
	@RequestMapping(value = "/urlValidate", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Map<String, String> urlValidate(String url){
		Map<String, String> map = new HashMap<String, String>();
		map.put("resStatus", "0");//成功
		try{
			droolsUrlService.urlValidate(url);
		}catch(Exception e){
			logger.error(e.getLocalizedMessage(), e);
			map.put("resStatus", "1");//失败
			map.put("errorMsg", "连接失败");//失败
		}
		return map;
	}
	
}
