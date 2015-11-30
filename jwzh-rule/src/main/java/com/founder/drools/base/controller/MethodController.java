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

import com.founder.drools.base.model.Drools_method;
import com.founder.drools.base.model.Drools_method_parameter;
import com.founder.drools.base.model.Drools_url;
import com.founder.drools.base.service.DroolsMethodService;
import com.founder.drools.base.service.DroolsParamService;
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
@RequestMapping("methodManager")
public class MethodController extends BaseController {
	@Autowired
	private DroolsMethodService droolsMethodService;
	
	@Autowired
	private DroolsParamService droolsParamService;
	
	@Autowired
	private DroolsUrlService droolsUrlService;
	
	@RequestMapping(value = "/methodManager", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView methodManager(){
		ModelAndView mv = new ModelAndView("system/method/methodManager");	
		List<Drools_url> list= droolsUrlService.queryUrlList(null);
		mv.addObject("UrlList",list);
		return mv;
	
	}
	
	@RequestMapping(value = "/getMethodManagerList", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView getMethodManagerList(Drools_method entity,Paginator paginator){
		ModelAndView mv = new ModelAndView("system/method/methodManagerList");			
		List<Drools_method> list = droolsMethodService.getMethodManagerList(entity);
		paginator.setList(list);
		mv.addObject("Paginator",paginator);	
		mv.addObject("Entity",entity);
		
		return mv;
	}
	
	@RequestMapping(value = "methodAddPre", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView methodAddPre(){
		ModelAndView mv = new ModelAndView("system/method/methodAdd");	
		List<Drools_url> list= droolsUrlService.queryUrlList(null);
		mv.addObject("UrlList",list);
		return mv;
	}
	
	@RequestMapping(value = "/methodAdd", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Map<String, String> methodAdd(Drools_method entity,String paramname,String paramclass,String parambz){
		Map<String, String> map = new HashMap<String, String>();
		map.put("resStatus", "0");//成功
		try{
			Drools_method queryEntity = new Drools_method();
			queryEntity.setMethodname(entity.getMethodname());
			queryEntity.setServiceid(entity.getServiceid());
			List<Drools_method> list = droolsMethodService.queryMethodList(entity);
			if(list!=null && list.size()>0){
				map.put("resStatus", "1");//失败
				map.put("errorMsg", "方法已存在，请重新输入");//失败
				return map;
			}
			
			List<Drools_method_parameter> paramList=droolsParamService.getParamList(paramname, paramclass, parambz);//生成参数列表			
			droolsMethodService.addMethod(entity);//保存方法
			droolsParamService.addParam(paramList,entity.getId());
			
		}catch(Exception e){
			e.printStackTrace();
			map.put("resStatus", "1");//失败
			map.put("errorMsg", e.toString());//失败
		}
		return map;				
	}
	
	@RequestMapping(value = "/methodEditPre", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView methodEditPre(String id){
		ModelAndView mv = new ModelAndView("system/method/methodEdit");	
		Drools_method entity= droolsMethodService.queryMethodById(id);
		List<Drools_method_parameter> list = droolsParamService.getParamList(id);
		mv.addObject("entity",entity);	
		mv.addObject("List",list);	
		return mv;
	}	
	
	@RequestMapping(value = "/methodEdit", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Map<String, String> methodEdit(Drools_method entity,String paramname,String paramclass,String parambz){
		Map<String, String> map = new HashMap<String, String>();
		map.put("resStatus", "0");//成功
		try{
			Drools_method queryEntity = new Drools_method();
			queryEntity.setMethodname(entity.getMethodname());
			queryEntity.setServiceid(entity.getServiceid());
			List<Drools_method> list = droolsMethodService.queryMethodList(entity);
			if(list!=null && list.size()>0){
				if(!list.get(0).getId().equals(entity.getId())){
					map.put("resStatus", "1");//失败
					map.put("errorMsg", "方法已存在，请重新输入");//失败
					return map;
				}
			}
			List<Drools_method_parameter> paramList=droolsParamService.getParamList(paramname, paramclass, parambz);//生成参数列表	
			droolsMethodService.updateMethod(entity);
			droolsParamService.addParam(paramList,entity.getId());
			
		}catch(Exception e){
			e.printStackTrace();
			map.put("resStatus", "1");//失败
			map.put("errorMsg", e.toString());//失败
		}
		return map;
	}
	
	@RequestMapping(value = "/methodDelete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Map<String, String> methodDelete(String id){
		Map<String, String> map = new HashMap<String, String>();
		map.put("resStatus", "0");//成功
		try{			
			droolsMethodService.deleteMethod(id);			
		}catch(Exception e){
			e.printStackTrace();
			map.put("resStatus", "1");//失败
			map.put("errorMsg", e.toString());//失败
		}
		return map;
	}
}
