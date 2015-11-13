package com.founder.drools.base.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.founder.drools.base.common.BaseModelUtils;
import com.founder.drools.base.model.DroolsGroup;
import com.founder.drools.base.service.DroolsGroupService;
import com.founder.framework.base.controller.BaseController;
import com.founder.framework.utils.EasyUIPage;
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
@RequestMapping("groupManager")
public class GroupManagerController extends BaseController {					
	
	@Resource(name="droolsGroupService")
	private DroolsGroupService droolsGroupService;
	
	@RequestMapping(value = "/groupManager", method = {RequestMethod.GET})
	public ModelAndView groupManager(){
		ModelAndView mv = new ModelAndView("groupManager/groupList");		        
		return mv;
	}
	
	@RequestMapping(value = "/viewAdd", method = {RequestMethod.GET})
	public ModelAndView viewAdd(){
		ModelAndView mv = new ModelAndView("groupManager/groupAdd");		        
		return mv;
	}
	
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody EasyUIPage list(EasyUIPage page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(required = false) DroolsGroup entity){
		page.setPagePara(rows);
		
//		int totalCount = 5;
//		List<DroolsGroup> groups = new ArrayList<DroolsGroup>();
//		for(int i = 0 ; i<totalCount; i++){
//			DroolsGroup group = new DroolsGroup();
//			group.setId(i+"");
//			group.setGroupname("分组名称~"+i);
//			BaseModelUtils.setSaveProperty(group);
//			groups.add(group);
//		}
//		page.setRows(groups);
//		page.setTotal(totalCount);
		
		return this.droolsGroupService.queryPageList(page, entity);
	}	
	
	@RequestMapping(value = "/save", method = {RequestMethod.POST})
	public @ResponseBody DroolsGroup save(DroolsGroup entity){
		return this.droolsGroupService.save(entity);
	}
	
}
