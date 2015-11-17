package com.founder.drools.base.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.founder.drools.base.service.DroolsRuleService;
import com.founder.drools.core.inteface.RuleService;
import com.founder.drools.core.model.RuleBean;
import com.founder.framework.base.controller.BaseController;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

@Controller
@RequestMapping("founderRule")
public class FounderRuleController extends BaseController {					
	
	@Autowired
	private RuleService ruleService;
	
	@Autowired
	private DroolsRuleService droolsRuleService;
	
	@RequestMapping(value = "/executeRule", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String executeRule(String ruleFileName,String ruleName,String paramStr){
		XStream xStream = new XStream(new DomDriver());
		RuleBean ruleBean = (RuleBean) xStream.fromXML(paramStr);
//		RuleBean ruleBean = new RuleBean();
//        ruleBean.setRuleFileName(ruleFileName);
//        ruleBean.setRuleName(ruleName);
        
        Map map=droolsRuleService.queryService(ruleBean.getRuleFileName());
        ruleBean.setServiceUrl((String)map.get("SERVICEURL"));
        ruleBean.setServiceMethod((String)map.get("SERVICEMETHOD"));
        
        //执行规则
        try{
        	ruleService.executeRule(ruleBean);
        }catch(Exception e){
        	e.printStackTrace();
        	ruleBean.setResponse(e.toString());        	
        }
		
        Map resMap=new HashMap();
        resMap.put("ruleStatus", ruleBean.getResStatus());
        resMap.put("ruleResponse", ruleBean.getResponse());
		return xStream.toXML(resMap);
	}
	
//	@RequestMapping(value = "/executeRule", method = {RequestMethod.GET,RequestMethod.POST})
//	public @ResponseBody Object executeRule(RuleBean ruleBean){
//			
//        Map map=droolsRuleService.queryService(ruleBean.getRuleFileName());
//        ruleBean.setServiceUrl((String)map.get("SERVICEURL"));
//        ruleBean.setServiceMethod((String)map.get("SERVICEMETHOD"));
//        
//        //执行规则
//        try{
//        	//ruleService.executeRule(ruleBean,ruleService.Str2Map(paramStr),null);
//        }catch(Exception e){
//        	e.printStackTrace();
//        	ruleBean.setResponse(e.toString());        	
//        }
//		
//        Map resMap=new HashMap();
//        resMap.put("ruleStatus", ruleBean.getResStatus());
//        resMap.put("ruleResponse", ruleBean.getResponse());
//		return resMap;
//	}	
}
