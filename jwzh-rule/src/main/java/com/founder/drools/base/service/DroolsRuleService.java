package com.founder.drools.base.service;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.founder.drools.base.dao.Drools_ruleDao;
import com.founder.drools.base.model.Drools_rule;
import com.founder.drools.core.inteface.RuleService;
import com.founder.framework.config.SystemConfig;

@Service
public class DroolsRuleService{
	private String filePath=null;		

	@Resource(name = "drools_ruleDao")
	private Drools_ruleDao drools_ruleDao;
	
	@Autowired
	private RuleService ruleService;
	
	public Drools_rule queryRuleById(String ID) {
		return drools_ruleDao.queryById(ID);
	}
	
	public Drools_rule queryRuleByEntity(Drools_rule entity) {
		return drools_ruleDao.queryByEntity(entity);
	}
	
	public void addRule(Drools_rule entity) {
		
		entity.setCreatetime(new Date());				
		entity.setId(getTimeString());
		drools_ruleDao.insert(entity);
	}
	
	public void updateRule(Drools_rule entity) {
		entity.setUpdatetime(new Date());		
		drools_ruleDao.update(entity);
	}
	
	public List<Drools_rule> queryRuleManagerList() {
		Drools_rule entity = new Drools_rule();
		entity.setRuletype("0");//查询规则显示列表
		return drools_ruleDao.queryListByEntity(entity);
	}
	
	public List<Drools_rule> queryRuleListByEntity(Drools_rule entity) {		 
		return drools_ruleDao.queryListByEntity(entity);
	}

	public String getTimeString(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date());
	}
	
	public void deleteRule(Drools_rule entity){
		drools_ruleDao.delete(entity);
	}

	public void ruleRelease(String rulefilename) {
		Drools_rule entity=new Drools_rule();
		entity.setRulefilename(rulefilename);
		entity.setRuletype("0");
		Drools_rule ruleHead = drools_ruleDao.queryByEntity(entity);
		
		entity.setRuletype("1");
		List list = drools_ruleDao.queryListByEntity(entity);//查询所有未归档的rulefilename的规则
		StringBuffer content=new StringBuffer();
		content.append(ruleHead.getContent()).append("\r\n\r\n");
		for(int i=0;i<list.size();i++){
			content.append("rule \""+((Drools_rule)list.get(i)).getRulename()+"\"\r\n\r\n").append(((Drools_rule)list.get(i)).getContent()).append("\r\n\r\nend\r\n\r\n");
		}
		this.writeDrl(ruleHead.getRulefilename(), content.toString(),false);
		ruleService.reLoadOne(ruleHead.getRulefilename());
		
		
		list.add(ruleHead);		
		
		//记录版本
		
		//修改状态，添加版本号
		String version=this.getTimeString();
		for(int i=0;i<list.size();i++){
			entity = (Drools_rule) list.get(i);
			entity.setStatus("0");
			entity.setVersion(version);
			this.updateRule(entity);
		}
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
		Drools_rule entity=new Drools_rule();
		entity.setRulefilename(rulefilename);
		entity.setRuletype("0");
		Drools_rule ruleHead = drools_ruleDao.queryByEntity(entity);
		
		entity.setRuletype("1");
		entity.setRulename(rulename);
		List list = drools_ruleDao.queryListByEntity(entity);//查询所有未归档的rulename规则
		StringBuffer content=new StringBuffer();
		content.append(ruleHead.getContent()).append("\r\n\r\n");
		for(int i=0;i<list.size();i++){
			content.append("rule \""+((Drools_rule)list.get(i)).getRulename()+"\"\r\n\r\n").append(((Drools_rule)list.get(i)).getContent()).append("\r\n\r\nend\r\n\r\n");
		}
		
		String testFileName=ruleHead.getRulefilename()+"_"+rulename+"_TEST";
		this.writeDrl(testFileName, content.toString(),true);		
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
}
