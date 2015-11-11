package com.founder.drools.base.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.founder.drools.base.model.Drools_rule;
import com.founder.drools.base.service.DroolsRuleService;
import com.founder.drools.core.inteface.RuleService;
import com.founder.drools.core.model.RuleBean;
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
	public ModelAndView ruleEditPre(String rulefilename){
		ModelAndView mv = new ModelAndView("drools/edit/ruleEdit");	
		Drools_rule entity= new Drools_rule();
		entity.setRulefilename(rulefilename);//规则文件名
		entity.setRuletype("0");//规则头
		Drools_rule ruleHead = droolsRuleService.queryRuleByEntity(entity);
		
		entity.setRuletype("1");//规则体		
		List<Drools_rule> list = droolsRuleService.queryRuleListByEntity(entity);
		
		mv.addObject("ruleHead",ruleHead);
		mv.addObject("List",list);
		return mv;
	}	
	
	@RequestMapping(value = "/ruleEdit", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView ruleEdit(Drools_rule entity){
		entity.setStatus("1");
		if("add".equals(entity.getId())){//新增规则体
			entity.setRuletype("1");//规则体，规则头在新增规则的时候就有了
			droolsRuleService.addRule(entity);
		}else{
			droolsRuleService.updateRule(entity);	
		}
		return this.ruleEditPre(entity.getRulefilename());
	}
	
	@RequestMapping(value = "/ruleDelete", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView ruleDelete(Drools_rule entity){		
		droolsRuleService.deleteRule(entity);
		return this.ruleEditPre(entity.getRulefilename());		
	}
	
	@RequestMapping(value = "/ruleRelease", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView ruleRelease(String rulefilename){		
		droolsRuleService.ruleRelease(rulefilename);
		return this.ruleEditPre(rulefilename);		
	}
	
	@RequestMapping(value = "/testRule", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody RuleBean testRule(String ruleFileName,String ruleName,String paramStr){
		RuleBean ruleBean = new RuleBean();
        ruleBean.setRuleFileName(ruleFileName);
        ruleBean.setRuleName(ruleName);
        try{
        	droolsRuleService.ruleTestRelease(ruleFileName,ruleName);
        }catch(Exception e){
        	e.printStackTrace();
        	ruleBean.setResponse(e.toString());  
        	return ruleBean;
        }
		
        try{
        	ruleService.testRule(ruleBean, paramStr);
        }catch(Exception e){
        	e.printStackTrace();
        	ruleBean.setResponse(e.toString());        	
        }
        
        if(0==ruleBean.getResStatus()){//验证成功
        	Drools_rule entity=new Drools_rule();
        	entity.setRulefilename(ruleFileName);
        	entity.setRulename(ruleName);
        	entity.setRuletype("1");
        	entity = droolsRuleService.queryRuleByEntity(entity);
        	if("1".equals(entity.getStatus())){//未验证        		
        		entity.setStatus("3");//未发布
        		droolsRuleService.updateRule(entity);
        	}
        	
        }
		return ruleBean;
	}
	
}
