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
		entity.setStatus("1");//未验证
		entity.setId(getTimeString());
		drools_ruleDao.insert(entity);
	}
	
	public void updateRule(Drools_rule entity) {
		
		entity.setUpdatetime(new Date());
		entity.setStatus("1");//未验证
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

	public void ruleRelease(String rulename) {
		Drools_rule entity=new Drools_rule();
		entity.setRulename(rulename);
		entity.setRuletype("0");
		Drools_rule ruleHead = drools_ruleDao.queryByEntity(entity);
		
		entity.setRuletype("1");
		List list = drools_ruleDao.queryListByEntity(entity);//查询所有未发布的rulename规则
		StringBuffer content=new StringBuffer();
		content.append(ruleHead.getContent()).append("\r\n\r\n");
		for(int i=0;i<list.size();i++){
			content.append(((Drools_rule)list.get(i)).getContent()).append("\r\n\r\n");
		}
		this.writeDrl(ruleHead.getRulename(), content.toString());
		ruleService.reLoadOne(ruleHead.getRulename());
	}
	
	private void  writeDrl(String fileName,String content){		
		if(filePath==null || filePath.length()==0)
			filePath = SystemConfig.getString("DrlFilePath");
		if(filePath==null || filePath.length()==0)
			throw new RuntimeException("can not find \"DrlFilePath\" in systemconfig");
		//创建文件夹
		File dir = new File(filePath);
		if(!dir.exists()){
			dir.mkdir();
		}
		
		if(!dir.exists()){
			throw new RuntimeException("can not create path:"+filePath);
		}
		try{			
			File file=new File(filePath+"/"+fileName+".drl");
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
