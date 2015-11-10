package com.founder.drools.base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.founder.drools.core.inteface.RuleService;
import com.founder.drools.core.model.RuleBean;
import com.founder.framework.annotation.RestfulAnnotation;
import com.founder.framework.base.controller.BaseController;
import com.founder.framework.base.entity.SessionBean;
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
@RequestMapping("ruleSys")
public class SysController extends BaseController {					
	
	@Autowired
	private RuleService ruleService;
	
	@RequestMapping(value = "/test", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView test(){
		ModelAndView mv = new ModelAndView("drools/test");
		
        RuleBean ruleBean = new RuleBean();
        ruleBean.setRuleServerName("MESSAGE_ZDRYGL");
        ruleBean.setRuleName("LGSQ");
        //执行规则
		ruleService.executeRule(ruleBean,null,null);
		
		System.out.println(ruleBean.getResponse());
		return mv;
	
	}
	
	@RequestMapping(value = "/serviceManager", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView serviceManager(){
		ModelAndView mv = new ModelAndView("system/serviceManager");		        
		return mv;
	
	}
	
	@RequestMapping(value = "/groupManager", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView groupManager(){
		ModelAndView mv = new ModelAndView("system/groupManager");		        
		return mv;
	
	}	
	
	
}
