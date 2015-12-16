package com.founder.drools.base.service;

import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.founder.drools.base.common.BaseModelUtils;
import com.founder.drools.base.dao.Drools_ruleDao;
import com.founder.drools.base.model.Drools_rule;
import com.founder.drools.base.model.Drools_ruleHis;
import com.founder.drools.core.inteface.RuleService;
import com.founder.drools.core.request.RuleBean;
import com.founder.framework.config.SystemConfig;

/**
 * ****************************************************************************
 * @Package:      [com.founder.drools.base.service.DroolsRuleService.java]  
 * @ClassName:    [DroolsRuleService]   
 * @Description:  [规则服务]   
 * @Author:       [zhang.hai@founder.com.cn]  
 * @CreateDate:   [2015年12月2日 下午5:20:24]   
 * @UpdateUser:   [ZhangHai(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2015年12月2日 下午5:20:24，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
@Service
public class DroolsRuleService{
	private String filePath=null;		

	@Resource(name = "drools_ruleDao")
	private Drools_ruleDao drools_ruleDao;
	
	@Autowired
	private RuleService ruleService;
	
	@Autowired
	private DroolsRuleHisService droolsRuleHisService;
	
	public Drools_rule queryRuleById(String ID) {
		return drools_ruleDao.queryById(ID);
	}
	
	public Drools_rule queryRuleByEntity(Drools_rule entity) {
		return drools_ruleDao.queryByEntity(entity);
	}
	
	public void addRule(Drools_rule entity) {
		entity.setStatus("1");
		BaseModelUtils.setSaveProperty(entity);				
		entity.setId(BaseModelUtils.getTimeString());
		if("undefined".equals(entity.getParamstr())){
			entity.setParamstr(null);
		}
		drools_ruleDao.insert(entity);
	}
	
	public void updateRule(Drools_rule entity) {		
		entity.setUpdatetime(new Date());		
		drools_ruleDao.update(entity);
	}
	
	public List<Drools_rule> queryRuleManagerList(Drools_rule entity) {	
		if(entity == null) entity= new Drools_rule();
		entity.setRuletype("0");//查询规则显示列表
		return drools_ruleDao.queryListByEntityFuzzy(entity);
	}
	
	public List<Drools_rule> queryRuleListByEntity(Drools_rule entity) {		 
		return drools_ruleDao.queryListByEntity(entity);
	}

	public void deleteRule(String id){
		drools_ruleDao.delete(id);
	}

	/**
	 * 
	 * @Title: ruleRelease
	 * @Description: TODO(发布规则)
	 * @param @param rulefilename 规则文件名
	 * @return void    返回类型
	 * @throw
	 */
	public String ruleRelease(String rulefilename) {
		Drools_rule entity = this.getRule(rulefilename, null);
		
		//验证语法是否正确
		RuleBean ruleBean = new RuleBean();
		ruleBean.setRuleFileName(entity.getRulefilename());
        ruleBean.setRuleName("validateRuleFile");
        
        try{
        	this.ruleTestRelease(rulefilename,"validateRuleFile");
        	ruleService.validateRule(ruleBean);
        	if(ruleBean.getResStatus()!=0){
        		return ruleBean.getResponse().toString();
        	}
        }catch(Exception e){
        	e.printStackTrace();
        	return e.toString();        	
        }	
       
		//正式发布
		this.writeDrl(entity.getRulefilename(), entity.getContent(),false);
		try{
			ruleService.reLoadOne(entity.getRulefilename());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//记录版本
		Drools_rule ruleHead = new Drools_rule();
		ruleHead.setRulefilename(rulefilename);
		ruleHead.setVersion(BaseModelUtils.getTimeString());
		ruleHead.setStatus("0");
		ruleHead.setUpdatetime(new Date());	
		drools_ruleDao.updateByRuleFileName(ruleHead);
		
		//保存历史版本
		this.writeRuleHistory(entity.getRulefilename());
		
		return null;
	}
	
	/**
	 * 
	 * @Title: ruleTestRelease
	 * @Description: TODO(发布测试验证的临时文件)
	 * @param @param rulefilename
	 * @param @param rulename    
	 * @return void    返回类型
	 * @throw
	 */
	public void ruleTestRelease(String rulefilename,String rulename) {
		Drools_rule entity;
		if("validateRuleFile".equals(rulename)){//验证整个文件
			entity = this.getRule(rulefilename, null);
		}else{//测试某一个规则
			entity = this.getRule(rulefilename, rulename);
		}
		
		String testFileName=entity.getRulefilename()+"_"+rulename+"_TEST";
		this.writeDrl(testFileName, entity.getContent(),true);		
	}
	
	private void  writeDrl(String fileName,String content,boolean isTest){		
		if(filePath==null || filePath.length()==0)
			filePath = SystemConfig.getString("DrlFilePath");
		if(filePath==null || filePath.length()==0)
			throw new RuntimeException("can not find \"DrlFilePath\" in systemconfig");
		//创建文件夹
		String testDir="";
		if(isTest){
			testDir="/test";
		}
		File dir = new File(filePath+testDir);
		if(!dir.exists()){
			dir.mkdir();
		}
		
		if(!dir.exists()){
			throw new RuntimeException("can not create path:"+filePath+testDir);
		}
		try{			
			File file=new File(filePath+testDir+"/"+fileName+".drl");
			if(!file.exists()){
				file.createNewFile();
			}
			FileWriter fileWriter =new FileWriter(file);
            fileWriter.write(content);
            fileWriter.flush();
            fileWriter.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void deleteDrl(String fileName){
		if(filePath==null || filePath.length()==0)
			filePath = SystemConfig.getString("DrlFilePath");
		if(filePath==null || filePath.length()==0)
			throw new RuntimeException("can not find \"DrlFilePath\" in systemconfig");
		
		File file=new File(filePath+"/"+fileName+".drl");
		
		file.deleteOnExit();
		
	}
	
	/**
	 * 
	 * @Title: getRule
	 * @Description: TODO(获取完整的规则文件对象)
	 * @param @param ruleFileName 规则文件
	 * @param @param rulename 指定的规则名，获取全部的时候传 null
	 * @param @return    设定文件
	 * @return Drools_rule    返回类型
	 * @throw
	 */
	public Drools_rule getRule(String ruleFileName,String rulename){
		//查询规则头
		Drools_rule entity=new Drools_rule();
		entity.setRulefilename(ruleFileName);
		entity.setRuletype("0");
		Drools_rule ruleHead = drools_ruleDao.queryByEntity(entity);
		
		//查询规则体
		entity.setRuletype("1");
		entity.setRulename(rulename);
		List list = drools_ruleDao.queryListByEntity(entity);
		StringBuffer content=new StringBuffer();
		if(ruleHead.getContent()!=null)
			content.append("/*RULE HEAD START*/\r\n").append(ruleHead.getContent()).append("\r\n/*RULE HEAD END*/\r\n\r\n");
		
		content.append("/*RULE BODY START*/\r\n\r\n");
		for(int i=0;i<list.size();i++){
			entity =((Drools_rule)list.get(i));
			content.append("/*RULE START*/\r\n");
			content.append("rule \""+entity.getRulename()+"\"\r\n\r\n");
			if(entity.getContent()!=null)
				content.append(entity.getContent());
			content.append("\r\n\r\nend\r\n");
			content.append("/*RULE START*/\r\n\r\n");
		}
		content.append("/*RULE BODY END*/");
		ruleHead.setContent(content.toString());
		
		return ruleHead;
	}
	
	/**
	 * 
	 * @Title: ruleFile
	 * @Description: TODO(规则归档)
	 * @param @param ruleFileName    设定文件
	 * @return void    返回类型
	 * @throw
	 */
	public void ruleFile(String ruleFileName){
		//记录版本
		Drools_rule ruleHead = new Drools_rule();
		ruleHead.setRulefilename(ruleFileName);
		ruleHead.setVersion(BaseModelUtils.getTimeString());
		ruleHead.setStatus("0");
		BaseModelUtils.setUpdateProperty(ruleHead);		
		drools_ruleDao.updateByRuleFileName(ruleHead);
		
		this.writeRuleHistory(ruleFileName);//记录历史
		
		drools_ruleDao.deleteByRuleFileName(ruleFileName);//删除正在使用的规则
		
		deleteDrl(ruleFileName);//删除文件
	}
	
	
	/**
	 * 
	 * @Title: writeRuleHistory
	 * @Description: TODO(记录规则历史表)
	 * @param @param ruleFileName    设定文件
	 * @return void    返回类型
	 * @throw
	 */
	public void writeRuleHistory(String ruleFileName){
		Drools_rule drools_rule = this.getRule(ruleFileName, null);
		
		String version = drools_rule.getVersion();
		if(version == null || version.length()==0)
			version = BaseModelUtils.getTimeString();
		
		Drools_ruleHis ruleHis=new Drools_ruleHis();
		ruleHis.setVersion(version);
		ruleHis.setContent(drools_rule.getContent());		
		ruleHis.setRulefilename(drools_rule.getRulefilename());
		ruleHis.setRuleid(drools_rule.getId());
		
		ruleHis.setGroupid(drools_rule.getGroupid());		
		
		ruleHis.setGroupname(drools_rule.getGroupname());
		ruleHis.setBz(drools_rule.getBz());
		
		droolsRuleHisService.insert(ruleHis);
	}
	
	/**
	 * 
	 * @Title: countRuleNum
	 * @Description: TODO(统计现有的规则数)
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throw
	 */
	public int countRuleNum(){
		return drools_ruleDao.countRuleNum();
	}

}
