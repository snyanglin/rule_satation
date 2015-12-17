package com.founder.drools.base.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.founder.drools.base.model.Drools_group;
import com.founder.drools.base.model.Drools_ruleHis;
import com.founder.drools.base.service.DroolsGroupService;
import com.founder.drools.base.service.DroolsRuleHisService;
import com.founder.framework.base.controller.BaseController;
import com.founder.framework.utils.StringUtils;
/**
 * ****************************************************************************
 * @Package:      [com.founder.drools.base.controller.RuleExOrImController.java]  
 * @ClassName:    [RuleExOrImController]   
 * @Description:  [导入导出]   
 * @Author:       [zhang.hai@founder.com.cn]  
 * @CreateDate:   [2015年12月15日 下午5:54:47]   
 * @UpdateUser:   [ZhangHai(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2015年12月15日 下午5:54:47，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
@Controller
@RequestMapping("ruleExOrIm")
public class RuleExOrImController extends BaseController {		
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Resource(name="droolsGroupService")
	private DroolsGroupService droolsGroupService;
	
	@Resource(name="droolsRuleHisService")
	private DroolsRuleHisService droolsRuleHisService;
	/**
	 * 
	 * @Title: ruleExportPre
	 * @Description: TODO(规则导出页面)
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throw
	 */
	@RequestMapping(value = "/ruleExportPre", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView ruleExportPre(){
		ModelAndView mv = new ModelAndView("exorim/export/ruleExportPre");	
		List<Drools_group> groupList=droolsGroupService.queryListByEntity(null);
		Drools_ruleHis entity=new Drools_ruleHis();
		
		for(int i=0;i<groupList.size();i++){
			List<Drools_ruleHis> hisList= droolsRuleHisService.queryExportList(groupList.get(i).getId());
			groupList.get(i).setRuleFileList(hisList);
		}
		
		mv.addObject("groupList",groupList);		
		
		return mv;
	}
	
	/**
	 * 
	 * @Title: ruleExport
	 * @Description: TODO(导出groupid分组下的fileStr规则文件)
	 * @param @param groupid 分组id
	 * @param @param fileStr 规则文件名，以“，”分隔
	 * @param @return    设定文件
	 * @return Map<String,String>    返回类型
	 * @throw
	 */
	@RequestMapping(value = "/ruleExport", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Map<String, String> ruleExport(String groupid,String fileStr,String timeStr){
		Map<String, String> map = new HashMap<String, String>();
		map.put("resStatus", "0");//成功
		
		try{
			Drools_group groupEntity = droolsGroupService.queryById(groupid);//规则分组
			droolsRuleHisService.exportRule(groupEntity.getGroupname(), fileStr,timeStr);
		}catch(Exception e){
			e.printStackTrace();
			map.put("resStatus", "1");//失败
			map.put("errorMsg", e.toString());//失败
		}
		
		return map;
	}
	
	@RequestMapping(value = "/ruleExportZip", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Map<String, String> ruleExportZip(String timeStr,HttpServletRequest request){
		Map<String, String> map = new HashMap<String, String>();
		map.put("resStatus", "0");//成功
		
		try{
			String basePath=request.getSession().getServletContext().getRealPath("/");
			droolsRuleHisService.exportZip(timeStr,basePath);
		}catch(Exception e){
			e.printStackTrace();
			map.put("resStatus", "1");//失败
			map.put("errorMsg", e.toString());//失败
		}
		
		return map;
	}
	
	/**
	 * 
	 * @Title: ruleImportPre
	 * @Description: TODO(规则导出页面)
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throw
	 */
	@RequestMapping(value = "/ruleImportPre", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView ruleImportPre(){
		ModelAndView mv = new ModelAndView("exorim/import/ruleImportPre");	
		
		
		return mv;
	}
	
	@RequestMapping(value = "/ruleImport", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView ruleImport(){
		ModelAndView mv = new ModelAndView("exorim/import/ruleImportPre");	
		
		
		return mv;
	}
	
}
