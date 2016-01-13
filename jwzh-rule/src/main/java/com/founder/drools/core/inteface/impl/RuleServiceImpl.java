package com.founder.drools.core.inteface.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.drools.runtime.StatefulKnowledgeSession;
import org.springframework.stereotype.Service;

import com.founder.drools.core.inteface.RuleService;
import com.founder.drools.core.model.RuleConfig;
import com.founder.drools.core.request.RuleBean;
import com.founder.framework.config.SystemConfig;

/**
 * ****************************************************************************
 * @Package:      [com.founder.zdrygl.core.inteface.impl.RuleServiceImpl.java]  
 * @ClassName:    [RuleServiceImpl]   
 * @Description:  [规则服务实现]   
 * @Author:       [zhang.hai@founder.com.cn]  
 * @CreateDate:   [2015年10月16日 下午5:47:09]   
 * @UpdateUser:   [ZhangHai(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2015年10月16日 下午5:47:09，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
@Service
public class RuleServiceImpl implements RuleService {		
	private Logger logger = Logger.getLogger(this.getClass());
	private String drlFilePath=null;
	
	//规则对应关系Map
	private Map<String, RuleConfig> ruleConfigMap = new HashMap<String, RuleConfig>();
	
	@Override
	public boolean executeRule(RuleBean ruleBean) {
		try{	
			String ruleFileName=ruleBean.getRuleFileName();
			logger.info("execute rule:"+ruleFileName);
			RuleConfig ruleConfig = (RuleConfig) ruleConfigMap.get(ruleFileName);
			if(ruleConfig == null){
				this.reLoadOne(ruleFileName);//重新加载
				ruleConfig = (RuleConfig) ruleConfigMap.get(ruleFileName);
				if(ruleConfig == null)
					throw new RuntimeException("Can not find ruleFile named \""+ruleFileName+"\"");
			}	
			StatefulKnowledgeSession ksession = ruleConfig.getKbase().newStatefulKnowledgeSession();	
			//循环设置参数
			ksession.insert(ruleBean);
			ksession.insert(this.jsonToMap(ruleBean.getJsonParamStr()));		
	        //触发规则引擎
	        ksession.fireAllRules();
	        ksession.dispose();  
	        return true;
		}catch(Exception e){
			e.printStackTrace();
			ruleBean.setResponse(e.toString());
		}
		
		return false;
	}
	
	public boolean reLoadOne(String ruleFileName) throws Exception{
		System.out.println("reload rule:"+ruleFileName);
		if(drlFilePath == null)
			drlFilePath=SystemConfig.getString("DrlFilePath");
		
		RuleConfig ruleConfig = new RuleConfig(drlFilePath+"/"+ruleFileName+".drl");
		ruleConfig.getKbase();
		ruleConfigMap.put(ruleFileName, ruleConfig);	
		return true;
			
		
	}
	
	public RuleBean validateRule(RuleBean ruleBean){
		try{
			if(drlFilePath == null)
				drlFilePath=SystemConfig.getString("DrlFilePath");
			
			RuleConfig ruleConfig = new RuleConfig(drlFilePath+"/test/"+ruleBean.getRuleFileName()+"_"+ruleBean.getRuleName()+"_TEST.drl");
			StatefulKnowledgeSession ksession = ruleConfig.getKbase().newStatefulKnowledgeSession();//将拼装的文件进行验证
			
			ruleBean.setResStatus(0);//验证的时候，只要不报错，说明规则没有语法错误			
	        ksession.dispose();
	        
		}catch(Exception e){
			e.printStackTrace();
			ruleBean.setResponse(e.toString());
		}
		return ruleBean;
	}
	
	/**
	 * 
	 * @Title: testRule
	 * @Description: TODO(规则测试)
	 * @param @param ruleFileName
	 * @param @param ruleName
	 * @param @param content
	 * @param @param jsonParamStr
	 * @param @return    设定文件
	 * @return RuleBean    返回类型
	 * @throw
	 */
	public RuleBean testRule(RuleBean ruleBean,String jsonParamStr){
		try{
			if(drlFilePath == null)
				drlFilePath=SystemConfig.getString("DrlFilePath");
			
			RuleConfig ruleConfig = new RuleConfig(drlFilePath+"/test/"+ruleBean.getRuleFileName()+"_"+ruleBean.getRuleName()+"_TEST.drl");
			StatefulKnowledgeSession ksession = ruleConfig.getKbase().newStatefulKnowledgeSession();
			
			ksession.insert(ruleBean);	
			ksession.insert(jsonToMap(jsonParamStr));		
		        //触发规则引擎
		    ksession.fireAllRules();
			
	        ksession.dispose();
	        
		}catch(Exception e){
			e.printStackTrace();
			ruleBean.setResponse(e.toString());
		}
		return ruleBean;
	}
	
	/**
	 * 
	 * @Title: jsonToMap
	 * @Description: TODO(将json字符串转成Map,递归调用，如果不能转成Map，直接返回String)
	 * @param @param jsonString
	 * @param @return Map或者String
	 * @param @throws JSONException    设定文件
	 * @return Object    返回类型
	 * @throw
	 */
	public Object jsonToMap(String jsonString) throws JSONException {

    	try{
	        JSONObject jsonObject = new JSONObject();
	        jsonObject=jsonObject.fromObject(jsonString);
	        
	        Map result = new HashMap();
	        Iterator iterator = jsonObject.keys();
	        String key = null;
	        String value = null;
	        
	        while (iterator.hasNext()) {
	
	            key = (String) iterator.next();
	            value = jsonObject.getString(key);
	            result.put(key, jsonToMap(value));
	
	        }
	        return result;
    	}catch(Exception e){
    		return jsonString;
    	}

    }
	
}
