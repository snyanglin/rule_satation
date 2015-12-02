package com.founder.drools.base.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.founder.drools.base.model.Drools_group;
import com.founder.drools.base.model.Drools_rule;
import com.founder.drools.base.service.DroolsGroupService;
import com.founder.drools.base.service.DroolsRuleService;
import com.founder.drools.core.model.Paginator;
import com.founder.framework.base.controller.BaseController;
/**
 * ****************************************************************************
 * @Package:      [com.founder.zdrygl.base.controller.RuleTestController.java]  
 * @ClassName:    [RuleTestController]   
 * @Description:  [规则引擎分组管理控制器]   
 * @Author:       [zhang.hai@founder.com.cn]  
 * @CreateDate:   [2015年10月14日 下午2:34:49]   
 * @UpdateUser:   [ZhangHai(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2015年10月14日 下午2:34:49，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
@Controller
@RequestMapping("groupManager")
public class GroupController extends BaseController {					
	
	@Resource(name="droolsGroupService")
	private DroolsGroupService droolsGroupService;
	
	@Autowired
	private DroolsRuleService droolsRuleService;
	
	/**
	 * 
	 * @Title: groupManager
	 * @Description: TODO(分组管理页面)
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throw
	 */
	@RequestMapping(value = "/groupManager", method = {RequestMethod.GET})
	public ModelAndView groupManager(){
		ModelAndView mv = new ModelAndView("system/group/groupManager");		        
		return mv;
	}
	
	/**
	 * 
	 * @Title: getGroupManagerList
	 * @Description: TODO(分组列表页面)
	 * @param @param entity
	 * @param @param paginator
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throw
	 */
	@RequestMapping(value = "/getGroupManagerList", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView getGroupManagerList(Drools_group entity,Paginator paginator){
		ModelAndView mv = new ModelAndView("system/group/groupManagerList");			
		List<Drools_group> list = droolsGroupService.queryListByEntityFuzzy(entity);
		paginator.setList(list);
		mv.addObject("Paginator",paginator);	
		mv.addObject("Entity",entity);
		
		return mv;		
	}	
	
	/**
	 * 
	 * @Title: groupAddPre
	 * @Description: TODO(分组添加页面)
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throw
	 */
	@RequestMapping(value = "groupAddPre", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView groupAddPre(){
		ModelAndView mv = new ModelAndView("system/group/groupAdd");	
		return mv;
	}
	
	/**
	 * 
	 * @Title: groupAdd
	 * @Description: TODO(ajax分组添加请求)
	 * @param @param entity
	 * @param @return    设定文件
	 * @return Map<String,String>    返回类型
	 * @throw
	 */
	@RequestMapping(value = "/groupAdd", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Map<String, String> groupAdd(Drools_group entity){
		Map<String, String> map = new HashMap<String, String>();
		map.put("resStatus", "0");//成功
		try{
			Drools_group queryEntity = new Drools_group();
			queryEntity.setGroupname(entity.getGroupname());			
			List<Drools_group> list = droolsGroupService.queryListByEntity(queryEntity);
			if(list!=null && list.size()>0){
				map.put("resStatus", "1");//失败
				map.put("errorMsg", "分组已存在，请重新输入");//失败
				return map;
			}
			
			droolsGroupService.save(entity);
			
		}catch(Exception e){
			e.printStackTrace();
			map.put("resStatus", "1");//失败
			map.put("errorMsg", e.toString());//失败
		}
		return map;				
	}
	
	/**
	 * 
	 * @Title: groupEditPre
	 * @Description: TODO(分组修改页面)
	 * @param @param id
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throw
	 */
	@RequestMapping(value = "/groupEditPre", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView groupEditPre(String id){
		ModelAndView mv = new ModelAndView("system/group/groupEdit");	
		Drools_group entity= droolsGroupService.queryById(id);
		mv.addObject("entity",entity);	
		return mv;
	}	
	
	/**
	 * 
	 * @Title: groupEdit
	 * @Description: TODO(分组修改ajax请求)
	 * @param @param entity
	 * @param @return    设定文件
	 * @return Map<String,String>    返回类型
	 * @throw
	 */
	@RequestMapping(value = "/groupEdit", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Map<String, String> groupEdit(Drools_group entity){
		Map<String, String> map = new HashMap<String, String>();
		map.put("resStatus", "0");//成功
		try{
			Drools_group queryEntity = new Drools_group();
			queryEntity.setGroupname(entity.getGroupname());			
			List<Drools_group> list = droolsGroupService.queryListByEntity(queryEntity);
			if(list!=null && list.size()>0){
				if(!list.get(0).getId().equals(entity.getId())){
					map.put("resStatus", "1");//失败
					map.put("errorMsg", "分组已存在，请重新输入");//失败
					return map;
				}
			}
			droolsGroupService.update(entity);
		}catch(Exception e){
			e.printStackTrace();
			map.put("resStatus", "1");//失败
			map.put("errorMsg", e.toString());//失败
		}
		return map;
	}
	
	/**
	 * 
	 * @Title: groupDelete
	 * @Description: TODO(分组删除Ajax请求)
	 * @param @param id
	 * @param @return    设定文件
	 * @return Map<String,String>    返回类型
	 * @throw
	 */
	@RequestMapping(value = "/groupDelete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Map<String, String> groupDelete(String id){
		Map<String, String> map = new HashMap<String, String>();
		map.put("resStatus", "0");//成功
		try{		
			Drools_rule queryEntity= new Drools_rule();
			queryEntity.setGroupid(id);
			List<Drools_rule> list = droolsRuleService.queryRuleListByEntity(queryEntity);
			if(list!=null && list.size()>0){
				map.put("resStatus", "1");//失败
				map.put("errorMsg", "该分组中有规则，如果要删除该分组，请先删除分组下的所哟规则！");
				return map;
			}
			droolsGroupService.deleteGroup(id);			
		}catch(Exception e){
			e.printStackTrace();
			map.put("resStatus", "1");//失败
			map.put("errorMsg", e.toString());//失败
		}
		return map;
	}
}
