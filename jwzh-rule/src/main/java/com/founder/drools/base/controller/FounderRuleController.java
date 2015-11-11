package com.founder.drools.base.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.founder.drools.core.inteface.RuleService;
import com.founder.drools.core.model.RuleBean;
import com.founder.framework.base.controller.BaseController;

@Controller
@RequestMapping("founderRule")
public class FounderRuleController extends BaseController {					
	
	@Autowired
	private RuleService ruleService;
	
	@RequestMapping(value = "/executeRule", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView executeRule(String ruleFileName,String ruleName,Object paramObj, Map globalParamMap){
		ModelAndView mv = new ModelAndView("drools/test");
		
        RuleBean ruleBean = new RuleBean();
        ruleBean.setRuleFileName(ruleFileName);
        ruleBean.setRuleName(ruleName);
        //执行规则
		ruleService.executeRule(ruleBean,null,null);
		mv.addObject("ruleRes",ruleBean.getResponse());
		
		return mv;
	
	}
	
	
	@RequestMapping(value = "/test", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView test(){
		ModelAndView mv = new ModelAndView("drools/test");		     
		return mv;
	
	}
}
