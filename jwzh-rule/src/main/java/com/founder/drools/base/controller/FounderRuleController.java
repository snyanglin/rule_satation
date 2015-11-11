package com.founder.drools.base.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
	public @ResponseBody Object executeRule(String ruleFileName,String ruleName,String paramStr){
			
        RuleBean ruleBean = new RuleBean();
        ruleBean.setRuleFileName(ruleFileName);
        ruleBean.setRuleName(ruleName);
        //执行规则
		ruleService.executeRule(ruleBean,Str2Map(paramStr),null);
		return ruleBean.getResponse();
	}
	
	
	@RequestMapping(value = "/test", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView test(){
		ModelAndView mv = new ModelAndView("drools/test");		     
		return mv;
	
	}
	
	private Map Str2Map(String paramStr){
		//String paramStr="{p3=p3, p2=p2, p1=p1}";
		paramStr=paramStr.replace("{", "");
		paramStr=paramStr.replace("}", "");
		Map map=new HashMap();
		String keyAndValueAry[]=paramStr.split(",");
		for(int i=0;i<keyAndValueAry.length;i++){
			String keyAdnValue[] = keyAndValueAry[i].split("=");
			if(keyAdnValue.length==2){
				map.put(keyAdnValue[0].trim(), keyAdnValue[1].trim());
			}
		}
		
		return map;
	}
}
