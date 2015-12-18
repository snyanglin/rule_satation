package com.founder.drools.base.service;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.founder.drools.base.dao.Drools_ruleHisDao;
import com.founder.drools.base.model.Drools_group;
import com.founder.drools.base.model.Drools_rule;
import com.founder.drools.base.model.Drools_ruleHis;
import com.founder.drools.core.model.RuleFileUtil;
import com.founder.drools.core.model.ZipUtils;
import com.founder.framework.config.SystemConfig;

/**
 * ****************************************************************************
 * @Package:      [com.founder.drools.base.service.DroolsRuleHisService.java]  
 * @ClassName:    [DroolsRuleHisService]   
 * @Description:  [规则历史服务]   
 * @Author:       [zhang.hai@founder.com.cn]  
 * @CreateDate:   [2015年12月2日 下午5:20:08]   
 * @UpdateUser:   [ZhangHai(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2015年12月2日 下午5:20:08，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
@Service
public class DroolsRuleHisService{
	private String filePath=null;

	@Resource(name = "drools_ruleHisDao")
	private Drools_ruleHisDao drools_ruleHisDao;
	
	public void insert(Drools_ruleHis entity){		
		drools_ruleHisDao.insert(entity);
	}
	
	public Drools_ruleHis queryRuleHis(Drools_ruleHis entity){
		return drools_ruleHisDao.queryRuleHis(entity);
	}

	public List<Drools_ruleHis> queryRuleHisList(Drools_ruleHis entity) {
		return drools_ruleHisDao.queryRuleHisList(entity);
	}
	
	/**
	 * 
	 * @Title: queryRuleHisManagerList
	 * @Description: TODO(查询规则文件列表,取最大版本号)
	 * @param @param entity
	 * @param @return    设定文件
	 * @return List<Drools_ruleHis>    返回类型
	 * @throw
	 */
	public List<Drools_ruleHis> queryRuleHisManagerList(Drools_ruleHis entity) {
		return drools_ruleHisDao.queryDroolsRuleHisManagerList(entity);
	}
	
	/**
	 * 
	 * @Title: queryRuleHisGroup
	 * @Description: TODO(查询分组列表)
	 * @param @return    设定文件
	 * @return List<Drools_ruleHis>    返回类型
	 * @throw
	 */
	public List<Drools_ruleHis> queryRuleHisGroup(){
		return drools_ruleHisDao.queryRuleHisGroup();
	}
	
	/**
	 * 
	 * @Title: exportRule
	 * @Description: TODO(导出规则)
	 * @param @param groupname
	 * @param @param fileStr
	 * @param @param timeStr    设定文件
	 * @return void    返回类型
	 * @throw
	 */
	public void exportRule(String groupname,String fileStr,String timeStr){
		String fileAry[]=fileStr.split(",");
		Drools_ruleHis queryEntity = new Drools_ruleHis();
		Drools_ruleHis resEntity;
		
		String exportDir=clearGroup(groupname,timeStr);//清空的目录
		for(int i =0;i<fileAry.length;i++){
			queryEntity.setVersion(fileAry[i]);			
			resEntity = this.queryRuleHis(queryEntity);//规则文件
			this.writeDrl(exportDir, resEntity);//生成文件
		}
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
	 * @Title: clearGroup
	 * @Description: TODO(清理导出的目录，删除以前的，建立新的)
	 * @param @param groupName
	 * @param @param timeStr
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throw
	 */
	private String clearGroup(String groupName,String timeStr){
		if(filePath==null || filePath.length()==0)
			filePath = SystemConfig.getString("DrlFilePath");
		if(filePath==null || filePath.length()==0)
			throw new RuntimeException("can not find \"DrlFilePath\" in systemconfig");
		
		//创建文件夹
		String exportDir=filePath+"/export";
		File dir = new File(exportDir);
		/*导出的文件所在的目录*/
		if(!dir.exists()){
			dir.mkdirs();
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
		
		/*分组文件夹*/
		exportDir+="/"+timeStr+"/"+groupName;
		dir = new File(exportDir);
		if(!dir.exists()){
			dir.mkdirs();
		}else{//清空文件夹
			File[] files = dir.listFiles();
			for(int i=0;i<files.length;i++){
				files[i].delete();
			}
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
	 * @Title: writeDrl
	 * @Description: TODO(生成规则文件)
	 * @param @param groupName
	 * @param @param entity    设定文件
	 * @return void    返回类型
	 * @throw
	 */
	private void  writeDrl(String exportDir,Drools_ruleHis entity){		
		if(entity==null || entity.getRulefilename()==null || entity.getContent()==null || entity.getRulefilename().length()==0 || entity.getContent().length()==0)
			return;
		
		try{			
			File file=new File(exportDir+"/"+entity.getRulefilename()+".drl");
			
			file.createNewFile();//目录是新建的，所以肯定没有文件
			
			FileWriter fileWriter =new FileWriter(file);
            fileWriter.write(entity.getContent());
            fileWriter.flush();
            fileWriter.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @Title: queryExportList
	 * @Description: TODO(查询导出规则列表)
	 * @param @param groupid
	 * @param @return    设定文件
	 * @return List<Drools_ruleHis>    返回类型
	 * @throw
	 */
	public List<Drools_ruleHis> queryExportList(String groupid){
		return drools_ruleHisDao.queryExportList(groupid);
	}

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
		Map<String,Drools_group> groupMap=new HashMap<String,Drools_group>();
		RuleFileUtil.readRuleFromDir(baseFdir, groupMap);
		Object keyAry[] = groupMap.keySet().toArray();
		List<Drools_group> groupList=new LinkedList<Drools_group>();
		for(int i=0;i<keyAry.length;i++){
			String groupName=keyAry[i].toString();
			Drools_group drools_group = groupMap.get(groupName);
			groupList.add(drools_group);
		}
		return groupList;
	}
	
	public List<Drools_rule> importRule(String groupId,String ruleFileName,String filePath){
		return RuleFileUtil.readRuleFromFile(groupId,ruleFileName,filePath);
	}

}
