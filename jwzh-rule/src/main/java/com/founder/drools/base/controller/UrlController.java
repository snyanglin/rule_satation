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

import com.founder.drools.base.model.Drools_url;
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
	@Autowired
	private DroolsUrlService droolsUrlService;
	
	@RequestMapping(value = "/urlManager", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView urlManager(){
		ModelAndView mv = new ModelAndView("system/url/urlManager");		        
		return mv;
	
	}
	
	@RequestMapping(value = "/getUrlManagerList", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView getUrlManagerList(Drools_url entity,Paginator paginator){
		ModelAndView mv = new ModelAndView("system/url/urlManagerList");			
		List<Drools_url> list = droolsUrlService.queryUrlList(entity);
		paginator.setList(list);
		mv.addObject("Paginator",paginator);	
		mv.addObject("Entity",entity);
		
		return mv;
	}
	
	@RequestMapping(value = "/urlAddPre", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView urlAddPre(){
		ModelAndView mv = new ModelAndView("system/url/urlAdd");		        
		return mv;
	
	}
	
	@RequestMapping(value = "/urlAdd", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Map<String, String> urlAdd(Drools_url entity){
		Map<String, String> map = new HashMap<String, String>();
		map.put("resStatus", "0");//成功
		try{
			Drools_url queryEntity = new Drools_url();
			queryEntity.setUrlname(entity.getUrlname());
			List<Drools_url> list = droolsUrlService.queryUrlList(entity);
			if(list!=null && list.size()>0){
				map.put("resStatus", "1");//失败
				map.put("errorMsg", "地址名称已存在，请重新输入");//失败
				return map;
			}
			droolsUrlService.addUrl(entity);
			
		}catch(Exception e){
			e.printStackTrace();
			map.put("resStatus", "1");//失败
			map.put("errorMsg", e.toString());//失败
		}
		return map;				
	}
	
	@RequestMapping(value = "/urlEditPre", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView urlEditPre(String id){
		ModelAndView mv = new ModelAndView("system/url/urlEdit");	
		Drools_url entity= droolsUrlService.queryUrlById(id);
		mv.addObject("entity",entity);		
		return mv;
	}	
	
	@RequestMapping(value = "/urlEdit", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Map<String, String> urlEdit(Drools_url entity){
		Map<String, String> map = new HashMap<String, String>();
		map.put("resStatus", "0");//成功
		try{
			Drools_url queryEntity = new Drools_url();
			queryEntity.setUrlname(entity.getUrlname());
			List<Drools_url> list = droolsUrlService.queryUrlList(entity);
			if(list!=null && list.size()>0){
				map.put("resStatus", "1");//失败
				map.put("errorMsg", "地址名称已存在，请重新输入");//失败
				return map;
			}
			
			droolsUrlService.updateUrl(entity);
			
		}catch(Exception e){
			e.printStackTrace();
			map.put("resStatus", "1");//失败
			map.put("errorMsg", e.toString());//失败
		}
		return map;
	}
	
	@RequestMapping(value = "/urlDelete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Map<String, String> urlDelete(String id){
		Map<String, String> map = new HashMap<String, String>();
		map.put("resStatus", "0");//成功
		try{			
			droolsUrlService.deleteUrl(id);
			
		}catch(Exception e){
			e.printStackTrace();
			map.put("resStatus", "1");//失败
			map.put("errorMsg", e.toString());//失败
		}
		return map;
	}
	
	@RequestMapping(value = "/urlValidate", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Map<String, String> urlValidate(String url){
		Map<String, String> map = new HashMap<String, String>();
		map.put("resStatus", "0");//成功
		try{
			droolsUrlService.urlValidate(url);
		}catch(Exception e){
			e.printStackTrace();
			map.put("resStatus", "1");//失败
			map.put("errorMsg", "连接失败");//失败
		}
		return map;
	}
	
}
