package com.founder.drools.base.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.founder.drools.base.model.Drools_rule;
import com.founder.drools.base.service.DroolsRuleService;
import com.founder.drools.core.inteface.RuleService;
import com.founder.framework.base.controller.BaseController;
/**
 * ****************************************************************************
 * @Package:      [com.founder.zdrygl.base.controller.RuleTestController.java]  
 * @ClassName:    [RuleTestController]   
 * @Description:  [规则引擎测试控制器]   
 * @Author:       [zhang.hai@founder.com.cn]  
 * @CreateDate:   [2015年10月14日 下午2:34:49]   
 * @UpdateUser:   [ZhangHai(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2015年10月14日 下午2:34:49，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
@Controller
@RequestMapping("ruleManager")
public class RuleController extends BaseController {					
	
	@Autowired
	private RuleService ruleService;
	
	@Autowired
	private DroolsRuleService droolsRuleService;
	
	
	@RequestMapping(value = "/ruleManager", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView ruleManager(){
		ModelAndView mv = new ModelAndView("drools/edit/ruleManager");	
		
		List<Drools_rule> list = droolsRuleService.queryRuleManagerList();
		mv.addObject("List",list);
		
		return mv;
	}		
	
	@RequestMapping(value = "/ruleEditPre", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView ruleEditPre(String ruleName){
		ModelAndView mv = new ModelAndView("drools/edit/ruleEdit");	
		Drools_rule entity= new Drools_rule();
		entity.setRulename(ruleName);
		//entity.setStatus(0);
		List<Drools_rule> list = droolsRuleService.queryRuleListByEntity(entity);
		mv.addObject("List",list);
		return mv;
	}	
	
	@RequestMapping(value = "/ruleEdit", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView ruleEdit(Drools_rule entity){
		ModelAndView mv = new ModelAndView("drools/edit/ruleManager");	
		droolsRuleService.updateRule(entity);	
		List<Drools_rule> list = droolsRuleService.queryRuleManagerList();
		mv.addObject("List",list);
		
		return mv;		
	}	
}
