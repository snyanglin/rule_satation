package com.founder.drools.core.inteface.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.drools.runtime.StatefulKnowledgeSession;
import org.springframework.stereotype.Service;

import com.founder.drools.core.inteface.RuleService;
import com.founder.drools.core.model.RuleBean;
import com.founder.drools.core.model.RuleConfig;
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
			
			//循环设置全局变量
			if(ruleBean.getGlobalParamMap()!=null){
				Object[] keyAry = ruleBean.getGlobalParamMap().keySet().toArray();
				for(int i=0;i<keyAry.length;i++){
					ksession.setGlobal((String)keyAry[i], ruleBean.getGlobalParamMap().get(keyAry[i]));					
				}
				
			}
			
			//循环设置参数
			ksession.insert(ruleBean);
			if(ruleBean.getParamObj()!=null){
				if(ruleBean.getParamObj() instanceof List){		
					List list=(List)ruleBean.getParamObj();
					for(int i=0;i<list.size();i++){
						ksession.insert(list.get(i));					
					}
				}else{
					ksession.insert(ruleBean.getParamObj());		
				}
				
			}
	
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
	
	/**
	 * 
	 * @Title: testRule
	 * @Description: TODO(新规则测试验证，验证的时候规则名传validateRuleFile)
	 * @param @param ruleFileName
	 * @param @param ruleName
	 * @param @param content
	 * @param @param paramStr
	 * @param @return    设定文件
	 * @return RuleBean    返回类型
	 * @throw
	 */
	public RuleBean testRule(RuleBean ruleBean,String paramStr){
		try{
			if(drlFilePath == null)
				drlFilePath=SystemConfig.getString("DrlFilePath");
			
			RuleConfig ruleConfig = new RuleConfig(drlFilePath+"/test/"+ruleBean.getRuleFileName()+"_"+ruleBean.getRuleName()+"_TEST.drl");
			StatefulKnowledgeSession ksession = ruleConfig.getKbase().newStatefulKnowledgeSession();
			
			if(!"validateRuleFile".equals(ruleBean.getRuleName())){//测试，否则是验证语法是否正确
				ksession.insert(ruleBean);	
				ksession.insert(Str2Map(paramStr));		
				
		        //触发规则引擎
		        ksession.fireAllRules();
			}else{//验证的时候，只要不报错，说明规则没有语法错误，业务错误不在这验证
				ruleBean.setResStatus(0);
			}
	        ksession.dispose();
	        
		}catch(Exception e){
			e.printStackTrace();
			ruleBean.setResponse(e.toString());
		}
		return ruleBean;
	}
	public Map Str2Map(String paramStr){
		//String paramStr="{p3=p3, p2=p2, p1=p1}";
		paramStr=paramStr.replace("{", "");
		paramStr=paramStr.replace("}", "");
		Map map=new HashMap();
		String keyAndValueAry[]=paramStr.split(",");
		for(int i=0;i<keyAndValueAry.length;i++){
			String keyAdnValue[] = keyAndValueAry[i].split(":");
			if(keyAdnValue.length==2){
				map.put(keyAdnValue[0].substring(1, keyAdnValue[0].length()-1), keyAdnValue[1].trim());
			}
		}
		
		return map;
	}
	
}
