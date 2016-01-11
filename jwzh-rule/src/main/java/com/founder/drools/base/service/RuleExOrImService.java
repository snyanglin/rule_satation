package com.founder.drools.base.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.binary.Base64;

import com.founder.drools.base.model.Drools_group;
import com.founder.drools.base.model.Drools_method;
import com.founder.drools.base.model.Drools_method_parameter;
import com.founder.drools.base.model.Drools_rule;
import com.founder.drools.base.model.Drools_service;
import com.founder.drools.base.model.Drools_url;
import com.founder.drools.core.model.RuleFileUtil;
import com.founder.drools.core.model.ZipUtils;
import com.founder.drools.core.request.DroolsRequest;
import com.founder.framework.config.SystemConfig;



/**
 * ****************************************************************************
 * @Package:      [com.founder.drools.base.service.RuleExOrImService.java]  
 * @ClassName:    [RuleExOrImService]   
 * @Description:  [导入导出服务]   
 * @Author:       [zhang.hai@founder.com.cn]  
 * @CreateDate:   [2016年1月8日 下午4:31:59]   
 * @UpdateUser:   [ZhangHai(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2016年1月8日 下午4:31:59，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
@Service
public class RuleExOrImService{
	private String filePath=null;
	
	@Autowired
	private DroolsGroupService droolsGroupService;
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
	 * @Title: queryExportList
	 * @Description: TODO(查询所有分组和分组下的所有规则文件)
	 * @param @return    设定文件
	 * @return List<Drools_group>    返回类型
	 * @throw
	 */
	public List<Drools_group> queryExportList(){
		List<Drools_group> groupList=droolsGroupService.queryListByEntity(null);//查询当前的所有分组
		
		Drools_rule ruleEntity=new Drools_rule();
		for(Drools_group groupEntity:groupList){
			ruleEntity.setGroupid(groupEntity.getId());
			List<Drools_rule> ruleList=droolsRuleService.queryRuleManagerList(ruleEntity);//分组下的所有规则文件			
			groupEntity.setRuleFileList(ruleList);
		}
		return groupList;
	}

	/**
	 * 
	 * @Title: exportRule
	 * @Description: TODO(导出分组)
	 * @param @param groupid 分组名
	 * @param @param fileStr 规则文件名，以“,”分隔
	 * @param @param timeStr    设定文件
	 * @return void    返回类型
	 * @throw
	 */
	public void exportRule(String groupid,String fileStr,String timeStr){
		Drools_group groupEntity = droolsGroupService.queryById(groupid);//规则分组
		
		groupEntity.setBz(ToBase64(groupEntity.getBz()));
		groupEntity.setGroupname(ToBase64(groupEntity.getGroupname()));
		
		String fileAry[]=fileStr.split(",");		
		
		List ruleFileList=new ArrayList();
		groupEntity.setRuleFileList(ruleFileList);
		
		Drools_rule queryEntity = new Drools_rule();
		queryEntity.setGroupid(groupid);
		
		String exportDir=clearGroup(timeStr);//重置导出目录
		for(int i =0;i<fileAry.length;i++){
			queryEntity.setRulefilename(fileAry[i]);	
			List<Drools_rule> ruleList=droolsRuleService.queryRuleListByEntity(queryEntity);//查询分组下的指定规则文件的规则列表
			for(Drools_rule ruleEntity:ruleList){
				ruleEntity.setBz(ToBase64(ruleEntity.getBz()));
				ruleEntity.setContent(ToBase64(ruleEntity.getContent()));
				ruleEntity.setRulefilename(ToBase64(ruleEntity.getRulefilename()));
				ruleEntity.setParamstr(ToBase64(ruleEntity.getParamstr()));
				ruleEntity.setCreatetime(null);
				ruleEntity.setGroupname(null);				
				ruleEntity.setUpdatetime(null);
				ruleEntity.setVersion(null);
			}
			ruleFileList.add(ruleList);			
			this.writeGroupFile(exportDir, groupEntity.getGroupname(),groupEntity);//生成文件
		}
	}
	
	/**
	 * 
	 * @Title: exportSys
	 * @Description: TODO(导出系统配置)
	 * @param @param timeStr    设定文件
	 * @return void    返回类型
	 * @throw
	 */
	public void exportSys(String timeStr){
		
		List<Drools_url> urlList = droolsUrlService.queryUrlListFuzzy(null);
		for(Drools_url urlEntity:urlList){
			urlEntity.setBz(ToBase64(urlEntity.getBz()));
			urlEntity.setCreatetime(null);
			urlEntity.setUpdatetime(null);
			urlEntity.setUrl(ToBase64(urlEntity.getUrl()));
			urlEntity.setUrlname(ToBase64(urlEntity.getUrlname()));
		}
				
		List<Drools_service> serviceList= droolsServiceService.queryServiceList(null);
		for(Drools_service serviceEntity:serviceList){
			serviceEntity.setBz(ToBase64(serviceEntity.getBz()));
			serviceEntity.setCreatetime(null);
			serviceEntity.setServicename(ToBase64(serviceEntity.getServicename()));
			serviceEntity.setUpdatetime(null);
		}
		
		List<Drools_method> methodList = droolsMethodService.queryMethodList(null);
		for(Drools_method methodEntity:methodList){
			methodEntity.setBz(ToBase64(methodEntity.getBz()));
			methodEntity.setCreatetime(null);
			methodEntity.setMethodname(ToBase64(methodEntity.getMethodname()));
			methodEntity.setMethodresponse(ToBase64(methodEntity.getMethodresponse()));
			methodEntity.setUpdatetime(null);
			methodEntity.setServicename(ToBase64(methodEntity.getServicename()));
			methodEntity.setUrlname(ToBase64(methodEntity.getUrlname()));
		}
		
		List<Drools_method_parameter> parameterList = droolsParamService.queryParamList();
		for(Drools_method_parameter pEntity:parameterList){
			pEntity.setBz(ToBase64(pEntity.getBz()));
			pEntity.setCreatetime(null);
			pEntity.setParamname(ToBase64(pEntity.getParamname()));
			pEntity.setUpdatetime(null);
		}
		
		List sysConfigList=new ArrayList();
		sysConfigList.add(urlList);
		sysConfigList.add(serviceList);
		sysConfigList.add(methodList);
		sysConfigList.add(parameterList);
		String exportDir=filePath+"/export/"+timeStr;
		this.writeGroupFile(exportDir,ToBase64("sysconfig"), sysConfigList);	
		
	}
	
	/**
	 * 
	 * @Title: exportZip
	 * @Description: TODO(打包ZIP)
	 * @param @param timeStr    设定文件
	 * @return void    返回类型
	 * @throw
	 */
	public void exportZip(String timeStr,String basePath){
		if(filePath==null || filePath.length()==0)
			filePath = SystemConfig.getString("DrlFilePath");
		if(filePath==null || filePath.length()==0)
			throw new RuntimeException("can not find \"DrlFilePath\" in systemconfig");
		
		String exportDir=filePath+"/export/"+timeStr;
		
		ZipUtils.zipFiles(exportDir, basePath+"/download/rules.zip");
	}
	
	/**
	 * 
	 * @Title: ToBase64
	 * @Description: TODO(转成Base64，避免乱码)
	 * @param @param str
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throw
	 */
	private String ToBase64(String str){
		try{
			if(str!=null && str.length()>0)
				return new String(Base64.encodeBase64(str.getBytes("UTF-8")),"UTF-8");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 
	 * @Title: Base64ToString
	 * @Description: TODO(Base64转成字符串)
	 * @param @param str
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throw
	 */
	private String Base64ToString(String str){
		try{
			if(str!=null && str.length()>0){				
				return new String(Base64.decodeBase64(str.getBytes("UTF-8")),"UTF-8");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 
	 * @Title: clearGroup
	 * @Description: TODO(清理导出的目录，删除以前的，建立新的)
	 * @param @param groupName
	 * @param @param timeStr
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throw
	 */
	private String clearGroup(String timeStr){
		if(filePath==null || filePath.length()==0)
			filePath = SystemConfig.getString("DrlFilePath");
		if(filePath==null || filePath.length()==0)
			throw new RuntimeException("can not find \"DrlFilePath\" in systemconfig");
		
		//创建文件夹
		String exportDir=filePath+"/export";
		File dir = new File(exportDir);
		/*导出的文件所在的目录*/
		if(!dir.exists()){
			dir.mkdirs();//如果不存在，创建新的目录
		}else{//已存在，删除以前的文件
			File[] files = dir.listFiles();
			for(int i=0;i<files.length;i++){
				if(files[i].isDirectory() && files[i].getName().equals(timeStr)) continue;//当前正在导出的文件夹不能删除
				deleteFiles(files[i]);//删除文件或者文件夹
			}
		}
				
		if(!dir.exists()){
			throw new RuntimeException("can not create path:"+exportDir);
		}
		
		/*创建导出的文件夹*/
		exportDir+="/"+timeStr;
		dir = new File(exportDir);
		if(!dir.exists()){
			dir.mkdirs();
		}
		
		if(!dir.exists()){
			throw new RuntimeException("can not create path:"+exportDir);
		}
		
		return exportDir;
	}
	
	/**
	 * 
	 * @Title: deleteFiles
	 * @Description: TODO(删除文件夹)
	 * @param @param file    设定文件
	 * @return void    返回类型
	 * @throw
	 */
	private void deleteFiles(File file){
		try{
			if(file.isDirectory()){
				File[] files = file.listFiles();
				for(int i=0;i<files.length;i++){
					deleteFiles(files[i]);
				}
				file.delete();
			}else{
				file.delete();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @Title: writeGroupFile
	 * @Description: TODO(将规则分组写在exportDir目录下)
	 * @param @param exportDir
	 * @param @param groupEntity    设定文件
	 * @return void    返回类型
	 * @throw
	 */
	private void writeGroupFile(String exportDir,String fileName, Object obj){
		if(obj==null)
			return;
		
		try{			
			File file=new File(exportDir+"/"+fileName+".xml");
			
			file.createNewFile();//目录是新建的，所以肯定没有文件
			FileOutputStream fileOut=new FileOutputStream(file);
			fileOut.write(DroolsRequest.Obj2XStream(obj).getBytes("UTF-8"));
			fileOut.flush();
			fileOut.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	
	
	/**
	 * 
	 * @Title: importZip
	 * @Description: TODO(解压ZIP文件，读取规则文件)
	 * @param @param bytes
	 * @param @return    设定文件
	 * @return List<Drools_group>    返回类型
	 * @throw
	 */
	public List<Drools_group> importZip(byte[] bytes) {
		if(filePath==null || filePath.length()==0)
			filePath = SystemConfig.getString("DrlFilePath");
		if(filePath==null || filePath.length()==0)
			throw new RuntimeException("can not find \"DrlFilePath\" in systemconfig");
		
		String importDir=filePath+"/import/";
		File baseFdir=new File(importDir);
		this.deleteFiles(baseFdir);//删除目录
		ZipUtils.unZipFile(bytes, importDir);//解压ZIP
		
		//读取并解析规则
		Map<String,Object> groupMap=new HashMap<String,Object>();
		RuleFileUtil.readImportListFromDir(baseFdir, groupMap);
		
		//页面显示用的列表，分组下的list存Drools_rule对象，也只用于显示，实际内容放Drools_rule的content中，导入的时候再解析
		List<Drools_group> groupList=new LinkedList<Drools_group>();
		
		Object keyAry[] = groupMap.keySet().toArray();
		for(int i=0;i<keyAry.length;i++){
			String groupName=keyAry[i].toString();
			Object groupObj = groupMap.get(groupName);
			
			groupName=Base64ToString(groupName);
			
			Drools_group entity=new Drools_group();
			entity.setGroupname(groupName);
			groupList.add(entity);
			
			if("sysconfig".equals(groupName)){//系统分组，groupObj是List
				entity.setBz("远程系统服务方法配置");
				
				List<List> sysconfigList=(List<List>)groupObj;
				List<Drools_rule> ruleFileList=new LinkedList<Drools_rule>();
				entity.setRuleFileList(ruleFileList);
				for(List list:sysconfigList){//每个配置list，内容可能是Drools_method_parameter、Drools_method等对象
					
					try {
						Drools_rule ruleEntity=new Drools_rule();
						ruleEntity.setGroupname(groupName);
						String ruleFileName=list.get(0).getClass().toString();//为空会抛异常
						ruleFileName=ruleFileName.substring(ruleFileName.lastIndexOf('.')+1);
						ruleEntity.setRulefilename(ruleFileName);
						ruleEntity.setContent(DroolsRequest.Obj2XStream(list));
						ruleEntity.setBz(ruleFileName);
						ruleFileList.add(ruleEntity);
					} catch (Exception e) {
					}
				}
			}else{//规则分组，groupObj是Drools_group
				Drools_group groupEntity=(Drools_group)groupObj;
				
				List<Drools_rule> ruleFileList=new LinkedList<Drools_rule>();
				entity.setRuleFileList(ruleFileList);
				entity.setBz(Base64ToString(groupEntity.getBz()));
				
				
				List<List> readRuleFileList = groupEntity.getRuleFileList();//读取的规则文件列表
				for(List<Drools_rule> list:readRuleFileList){
					try {
						Drools_rule ruleEntity=getRuleHead(list);						
						ruleEntity.setContent(DroolsRequest.Obj2XStream(list));		
						ruleEntity.setRulefilename(Base64ToString(ruleEntity.getRulefilename()));
						ruleEntity.setBz(Base64ToString(ruleEntity.getBz()));
						ruleFileList.add(getRuleHead(list));
					} catch (Exception e) {
					}
				}
			}
			
		}
		return groupList;
	}
	
	/**
	 * 
	 * @Title: getRuleHead
	 * @Description: TODO(获取规则头文件)
	 * @param @param list
	 * @param @return    设定文件
	 * @return Drools_rule    返回类型
	 * @throw
	 */
	private Drools_rule getRuleHead(List<Drools_rule> list){
		for(Drools_rule entity:list){
			if("0".equals(entity.getRuletype()))
					return entity;
		}		
		return null;
	}
	
	/**
	 * 
	 * @Title: ruleImport
	 * @Description: TODO(导入服务方法的List)
	 * @param @param ruleFileName
	 * @param @param content
	 * @param @return    设定文件
	 * @return List    返回类型
	 * @throw
	 */
	public void ruleImport(String groupname,String groupbz,String ruleFileName,String content){
		if("sysconfig".equals(groupname)){//服务方法配置文件
			try {
				List list=(List) DroolsRequest.XStream2Obj(content);
				if("Drools_url".equals(ruleFileName)){
					for(Drools_url urlEntity:(List<Drools_url>)list){
						urlEntity.setBz(Base64ToString(urlEntity.getBz()));
						urlEntity.setUrl(Base64ToString(urlEntity.getUrl()));
						urlEntity.setUrlname(Base64ToString(urlEntity.getUrlname()));
					}
					droolsUrlService.addUrlList(list);
				}else if("Drools_service".equals(ruleFileName)){
					for(Drools_service serviceEntity:(List<Drools_service>)list){
						serviceEntity.setBz(Base64ToString(serviceEntity.getBz()));
						serviceEntity.setServicename(Base64ToString(serviceEntity.getServicename()));
					}
					droolsServiceService.addServiceList(list);
				}else if("Drools_method".equals(ruleFileName)){
					for(Drools_method methodEntity:(List<Drools_method>)list){
						methodEntity.setBz(Base64ToString(methodEntity.getBz()));
						methodEntity.setMethodname(Base64ToString(methodEntity.getMethodname()));
						methodEntity.setMethodresponse(Base64ToString(methodEntity.getMethodresponse()));
						methodEntity.setServicename(Base64ToString(methodEntity.getServicename()));
						methodEntity.setUrlname(Base64ToString(methodEntity.getUrlname()));
					}
					droolsMethodService.addMethodList(list);
				}else if("Drools_method_parameter".equals(ruleFileName)){
					for(Drools_method_parameter pEntity:(List<Drools_method_parameter>)list){
						pEntity.setBz(Base64ToString(pEntity.getBz()));
						pEntity.setCreatetime(null);
						pEntity.setParamname(Base64ToString(pEntity.getParamname()));
						pEntity.setUpdatetime(null);
					}
					droolsParamService.addParam(list);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
			//验证规则文件是否存在
			Drools_rule queryEntity=new Drools_rule();
			queryEntity.setRulefilename(ruleFileName.trim());
			List<Drools_rule> resList=droolsRuleService.queryRuleListByEntity(queryEntity);
			if(resList.size()>0)
				throw new RuntimeException("Rule file \""+ruleFileName+"\" is exits!");
			
			//验证分组是否存在，不存在则创建
			Drools_group groupEntity= droolsGroupService.validateAndAdd(groupname,groupbz);
			
			try {
				List<Drools_rule> list = (List<Drools_rule>) DroolsRequest.XStream2Obj(content);
				if(list.size()==0){
					throw new RuntimeException("规则文件解析出错！");
				}
				for(Drools_rule entity:list){
					entity.setGroupid(groupEntity.getId());
					entity.setGroupname(groupname);
					entity.setBz(Base64ToString(entity.getBz()));
					entity.setContent(Base64ToString(entity.getContent()));
					entity.setRulefilename(Base64ToString(entity.getRulefilename()));
					entity.setParamstr(Base64ToString(entity.getParamstr()));
					
					droolsRuleService.addRule(entity);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			 
			
		}
				
	}
	
	/**
	 * 
	 * @Title: clrearRule
	 * @Description: TODO(清空规则)
	 * @param     设定文件
	 * @return void    返回类型
	 * @throw
	 */
	public void clrearRule(){
		droolsRuleService.clearRule();
		droolsGroupService.clearGroup();
		droolsParamService.clearParam();
		droolsMethodService.clearMethod();
		droolsServiceService.clearService();
		droolsUrlService.clearUrl();
	}
}
