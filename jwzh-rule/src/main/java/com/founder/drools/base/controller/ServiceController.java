package com.founder.drools.base.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * @Package:      [com.founder.drools.base.controller.ServiceController.java]  
 * @ClassName:    [ServiceController]   
 * @Description:  [服务管理 控制器]   
 * @Author:       [zhang.hai@founder.com.cn]  
 * @CreateDate:   [2015年11月27日 上午10:07:13]   
 * @UpdateUser:   [ZhangHai(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2015年11月27日 上午10:07:13，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
@Controller
@RequestMapping("serviceManager")
public class ServiceController extends BaseController {
	@Autowired
	private DroolsServiceService droolsServiceService;
	
	@Autowired
	private DroolsUrlService droolsUrlService;
	
	@RequestMapping(value = "/serviceManager", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView serviceManager(){
		ModelAndView mv = new ModelAndView("system/service/serviceManager");	
		List<Drools_url> list= droolsUrlService.queryUrlList(null);
		mv.addObject("UrlList",list);
		return mv;
	
	}
	
	@RequestMapping(value = "/getServiceManagerList", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView getServiceManagerList(Drools_service entity,Paginator paginator){
		ModelAndView mv = new ModelAndView("system/service/serviceManagerList");			
		List<Drools_service> list = droolsServiceService.queryServiceList(entity);
		paginator.setList(list);
		mv.addObject("Paginator",paginator);	
		mv.addObject("Entity",entity);
		
		return mv;
	}
	
	@RequestMapping(value = "/getServiceSelectList", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView getServiceSelectList(Drools_service entity,String queryType){
		ModelAndView mv = new ModelAndView("system/service/serviceSelectList");			
		List<Drools_service> list = droolsServiceService.queryServiceList(entity);	
		if("0".equals(queryType)){//添加查询全部的选择
			Drools_service newEntity=new Drools_service();
			newEntity.setServicename("*全部*");
			newEntity.setId("ALL");
			list.add(0, newEntity);
		}
		mv.addObject("List",list);
		return mv;
	}
	
	@RequestMapping(value = "/serviceAddPre", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView serviceAddPre(){
		ModelAndView mv = new ModelAndView("system/service/serviceAdd");	
		List<Drools_url> list= droolsUrlService.queryUrlList(null);
		mv.addObject("UrlList",list);
		return mv;
	
	}
	
	@RequestMapping(value = "/serviceAdd", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Map<String, String> urlAdd(Drools_service entity){
		Map<String, String> map = new HashMap<String, String>();
		map.put("resStatus", "0");//成功
		try{
			Drools_service queryEntity = new Drools_service();
			queryEntity.setServicename(entity.getServicename());
			queryEntity.setUrlid(entity.getUrlid());
			List<Drools_service> list = droolsServiceService.queryServiceList(entity);
			if(list!=null && list.size()>0){
				map.put("resStatus", "1");//失败
				map.put("errorMsg", "服务已存在，请重新输入");//失败
				return map;
			}
			droolsServiceService.addService(entity);
			
		}catch(Exception e){
			e.printStackTrace();
			map.put("resStatus", "1");//失败
			map.put("errorMsg", e.toString());//失败
		}
		return map;				
	}
	
	@RequestMapping(value = "/serviceEditPre", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView serviceEditPre(String id){
		ModelAndView mv = new ModelAndView("system/service/serviceEdit");	
		Drools_service entity= droolsServiceService.queryServiceById(id);
		Drools_url urlEntity = droolsUrlService.queryUrlById(entity.getUrlid());
		mv.addObject("entity",entity);
		mv.addObject("urlEntity",urlEntity);
		return mv;
	}	
	
	@RequestMapping(value = "/serviceEdit", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Map<String, String> serviceEdit(Drools_service entity){
		Map<String, String> map = new HashMap<String, String>();
		map.put("resStatus", "0");//成功
		try{
			Drools_service queryEntity = new Drools_service();
			queryEntity.setServicename(entity.getServicename());
			queryEntity.setUrlid(entity.getUrlid());
			List<Drools_service> list = droolsServiceService.queryServiceList(entity);
			if(list!=null && list.size()>0){
				map.put("resStatus", "1");//失败
				map.put("errorMsg", "服务已存在，请重新输入");//失败
				return map;
			}
			
			droolsServiceService.updateService(entity);
			
		}catch(Exception e){
			e.printStackTrace();
			map.put("resStatus", "1");//失败
			map.put("errorMsg", e.toString());//失败
		}
		return map;
	}
	
	@RequestMapping(value = "/serviceDelete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Map<String, String> serviceDelete(String id){
		Map<String, String> map = new HashMap<String, String>();
		map.put("resStatus", "0");//成功
		try{			
			droolsServiceService.deleteService(id);
			
		}catch(Exception e){
			e.printStackTrace();
			map.put("resStatus", "1");//失败
			map.put("errorMsg", e.toString());//失败
		}
		return map;
	}
	
}
