package com.founder.drools.base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
@RequestMapping("ruleSys")
public class SysController extends BaseController {					
	
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
