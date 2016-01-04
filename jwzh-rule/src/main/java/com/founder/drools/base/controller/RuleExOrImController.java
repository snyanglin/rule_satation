package com.founder.drools.base.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.founder.drools.base.model.Drools_group;
import com.founder.drools.base.model.Drools_method;
import com.founder.drools.base.model.Drools_method_parameter;
import com.founder.drools.base.model.Drools_rule;
import com.founder.drools.base.model.Drools_ruleHis;
import com.founder.drools.base.model.Drools_service;
import com.founder.drools.base.model.Drools_url;
import com.founder.drools.base.service.DroolsGroupService;
import com.founder.drools.base.service.DroolsMethodService;
import com.founder.drools.base.service.DroolsParamService;
import com.founder.drools.base.service.DroolsRuleHisService;
import com.founder.drools.base.service.DroolsRuleService;
import com.founder.drools.base.service.DroolsServiceService;
import com.founder.drools.base.service.DroolsUrlService;
import com.founder.framework.base.controller.BaseController;
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
	
	@Autowired
	private DroolsRuleService droolsRuleService;
	@Autowired
	private DroolsMethodService droolsMethodService;
	@Autowired
	private DroolsServiceService droolsServiceService;
	@Autowired
	private DroolsUrlService droolsUrlService;
	@Autowired
	private DroolsParamService droolsParamService;
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
			logger.error(e.getLocalizedMessage(), e);
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
			logger.error(e.getLocalizedMessage(), e);
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
	@RequestMapping(value = "/ruleImportPre", method = {RequestMethod.GET})
	public ModelAndView ruleImportPre(){
		ModelAndView mv = new ModelAndView("exorim/import/ruleImportPre");	
		return mv;
	}
	
	@RequestMapping(value = "/ruleImportView", method = {RequestMethod.POST})
	public ModelAndView ruleImportView(@RequestParam(value="zipFile") CommonsMultipartFile zipFile){
		ModelAndView mv = new ModelAndView("exorim/import/ruleImport");	
		if(!zipFile.isEmpty()){
			
			if(zipFile.getSize()>1024*1024)//文件不能超过1M
				throw new RuntimeException("File is too large");
			
			FileItem fileItem = zipFile.getFileItem();
			String zipFileName = fileItem.getName();
			if (zipFileName.indexOf("\\") != -1) { // 去除完整路径
				zipFileName = zipFileName.substring(zipFileName.lastIndexOf("\\") + 1);
			}
			
			String fileType = "";//文件类型
			int atI = zipFileName.lastIndexOf(".");
			if (atI != -1) {
				fileType = zipFileName.substring(atI + 1);
				fileType = fileType.toLowerCase();
			}
			if(!"zip".equals(fileType)){
				throw new RuntimeException("File type can only be 'zip'!");
			}
			
			//解压ZIP,获取分组
			List<Drools_group> groupList=droolsRuleHisService.importZip(zipFile.getBytes());
			mv.addObject("GroupList",groupList);
		}
		
		return mv;
	}
	
	@RequestMapping(value = "/ruleImport", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Map<String, String> ruleImport(String groupname,String ruleFileName,String filePath){
		Map<String, String> map = new HashMap<String, String>();
		map.put("resStatus", "0");//成功
		
		try{
			if("sysconfig".equals(groupname)){//服务方法配置文件
				List list=droolsRuleHisService.importSys(filePath);
				if("Drools_url".equals(ruleFileName)){
						droolsUrlService.addUrlList(list);
				}else if("Drools_service".equals(ruleFileName)){
					droolsServiceService.addServiceList(list);
				}else if("Drools_method".equals(ruleFileName)){
					droolsMethodService.addMethodList(list);
				}else if("Drools_method_parameter".equals(ruleFileName)){
					droolsParamService.addParam(list);
				}
				
			}else{
				//验证规则文件是否存在
				Drools_rule queryEntity=new Drools_rule();
				queryEntity.setRulefilename(ruleFileName.trim());
				List<Drools_rule> resList=droolsRuleService.queryRuleListByEntity(queryEntity);
				if(resList.size()>0)
					throw new RuntimeException("Rule file \""+ruleFileName+"\" is exits!");
				
				//验证分组是否存在，不存在则创建
				Drools_group groupEntity= droolsGroupService.validateAndAdd(groupname);
				
				List<Drools_rule> list=droolsRuleHisService.importRule(groupEntity.getId(), ruleFileName, filePath);
				if(list.size()==0){
					map.put("resStatus", "1");//失败
					map.put("errorMsg", "规则文件解析出错！");//失败
				}
				 
				for(int i=0;i<list.size();i++){
					 droolsRuleService.addRule(list.get(i));
				}
			}
		}catch(Exception e){
			logger.error(e.getLocalizedMessage(), e);
			map.put("resStatus", "1");//失败
			map.put("errorMsg", e.toString());//失败
		}
		
		return map;
	}
	
	/**
	 * 
	 * @Title: clrearRule
	 * @Description: TODO(清空规则)
	 * @param @return    设定文件
	 * @return Map<String,String>    返回类型
	 * @throw
	 */
	@RequestMapping(value = "/clrearRule", method = {RequestMethod.POST})
	public @ResponseBody Map<String, String> clrearRule(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("resStatus", "0");//成功
		
		try{
			droolsRuleService.clearRule();
			droolsGroupService.clearGroup();
		}catch(Exception e){
			logger.error(e.getLocalizedMessage(), e);
			map.put("resStatus", "1");//失败
			map.put("errorMsg", e.toString());//失败
		}
		
		return map;
	}
	
	/**
	 * 
	 * @Title: exportSys
	 * @Description: TODO(导出服务方法配置)
	 * @param @return    设定文件
	 * @return Map<String,String>    返回类型
	 * @throw
	 */
	@RequestMapping(value = "/exportSys", method = {RequestMethod.POST})
	public @ResponseBody Map<String, String> exportSys(String timeStr){
		Map<String, String> map = new HashMap<String, String>();
		map.put("resStatus", "0");//成功
		
		try{
			List<Drools_url> urlList = droolsUrlService.queryUrlListFuzzy(null);
			droolsRuleHisService.exportSys(timeStr, "Drools_url", urlList);
			
			List<Drools_service> serviceList= droolsServiceService.queryServiceList(null);
			droolsRuleHisService.exportSys(timeStr, "Drools_service", serviceList);
			
			List<Drools_method> methodList = droolsMethodService.queryMethodList(null);
			droolsRuleHisService.exportSys(timeStr, "Drools_method", methodList);
			
			List<Drools_method_parameter> parameterList = droolsParamService.queryParamList();
			droolsRuleHisService.exportSys(timeStr, "Drools_method_parameter", parameterList);
			
		}catch(Exception e){
			logger.error(e.getLocalizedMessage(), e);
			map.put("resStatus", "1");//失败
			map.put("errorMsg", e.toString());//失败
		}
		
		return map;
	}
	
}
