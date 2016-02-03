package com.founder.drools.base.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.founder.drools.base.model.Drools_group;
import com.founder.drools.base.service.RuleExOrImService;
import com.founder.drools.core.request.RuleBean;
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
	
	@Resource(name="ruleExOrImService")
	private RuleExOrImService ruleExOrImService;
	
	/**
	 * 
	 * @Title: ruleExportPre
	 * @Description: TODO(规则导出页面)
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throw
	 */
	@RequestMapping(value = "/ruleExportPre", method = {RequestMethod.GET})
	public ModelAndView ruleExportPre(){
		ModelAndView mv = new ModelAndView("exorim/export/ruleExportPre");	
		
		List<Drools_group> groupList=ruleExOrImService.queryExportList();
		mv.addObject("groupList",groupList);		
		
		return mv;
	}
	
	/**
	 * 
	 * @Title: testRule
	 * @Description: TODO(规则日志导出准备)
	 * @param @return    设定文件
	 * @return RuleBean    返回类型
	 * @throw
	 */
	@RequestMapping(value = "/logPrepare", method = {RequestMethod.GET})
	public @ResponseBody String logPrepare(){
		String result = "true";
		return result;
	}
	
	/**
	 * 
	 * @Title: ruleExportPre
	 * @Description: TODO(日志导出页面)
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throw
	 */
	@RequestMapping(value = "/logExportPre", method = {RequestMethod.GET})
	public ModelAndView logExportPre(){
		ModelAndView mv = new ModelAndView("exorim/export/logExportPre");
		mv.addObject("name", "准备下载");
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
	@RequestMapping(value = "/ruleExport", method = {RequestMethod.POST})
	public @ResponseBody Map<String, String> ruleExport(String groupid,String fileStr,String timeStr){
		Map<String, String> map = new HashMap<String, String>();
		map.put("resStatus", "0");//成功
		
		try{
			ruleExOrImService.exportRule(groupid, fileStr,timeStr);
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
			ruleExOrImService.exportSys(timeStr);			
			
		}catch(Exception e){
			logger.error(e.getLocalizedMessage(), e);
			map.put("resStatus", "1");//失败
			map.put("errorMsg", e.toString());//失败
		}
		
		return map;
	}
	
	/**
	 * 
	 * @Title: ruleExportZip
	 * @Description: TODO(生成压缩包)
	 * @param @param timeStr
	 * @param @param request
	 * @param @return    设定文件
	 * @return Map<String,String>    返回类型
	 * @throw
	 */
	@RequestMapping(value = "/ruleExportZip", method = {RequestMethod.POST})
	public @ResponseBody Map<String, String> ruleExportZip(String timeStr,HttpServletRequest request){
		Map<String, String> map = new HashMap<String, String>();
		map.put("resStatus", "0");//成功
		
		try{
			String basePath=request.getSession().getServletContext().getRealPath("/");
			ruleExOrImService.exportZip(timeStr,basePath);
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
			List<Drools_group> groupList=ruleExOrImService.importZip(zipFile.getBytes());
			mv.addObject("GroupList",groupList);
		}
		
		return mv;
	}
	
	@RequestMapping(value = "/ruleImport", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Map<String, String> ruleImport(String groupname,String groupbz,String ruleFileName,String content){
		Map<String, String> map = new HashMap<String, String>();
		map.put("resStatus", "0");//成功
		
		try{
			ruleExOrImService.ruleImport(groupname, groupbz,ruleFileName, content);
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
			ruleExOrImService.clrearRule();
		}catch(Exception e){
			logger.error(e.getLocalizedMessage(), e);
			map.put("resStatus", "1");//失败
			map.put("errorMsg", e.toString());//失败
		}
		
		return map;
	}
	
	
	
}
