package com.founder.drools.core.model;

import org.drools.KnowledgeBase;

/**
 * ****************************************************************************
 * @Package:      [com.founder.drools.core.model.RuleConfig.java]  
 * @ClassName:    [RuleConfig]   
 * @Description:  [规则引擎配置]   
 * @Author:       [zhang.hai@founder.com.cn]  
 * @CreateDate:   [2015年10月21日 下午5:14:50]   
 * @UpdateUser:   [ZhangHai(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2015年10月21日 下午5:14:50，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
public class RuleConfig {
	//规则服务URL	
	private String url = null;

	//规则服务对象
	private KnowledgeBase kbase = null;
	
	public RuleConfig(){
		
	}
	
	/**
	 * 
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param url 规则引擎URL
	 * @param userName 规则服务器用户名
	 * @param userPassword 规则服务器密码
	 */
	public RuleConfig(String url){
		this.url=url;
	}		

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 
	 * @Title: getKbase
	 * @Description: TODO(获取KnowledgeBase，第一次调用的时候初始化)
	 * @param @return    设定文件
	 * @return KnowledgeBase    返回类型
	 * @throws Exception 
	 * @throw
	 */
	public KnowledgeBase getKbase() throws Exception {
		if(kbase == null){
			//kbase = DroolsUtils.buildKnowledgeBaseByUrl(url, userName, userPassword);
		
				kbase = DroolsUtils.buildKnowledgeBaseByFile(url);
			
		}
		return kbase;
	}

	public void setKbase(KnowledgeBase kbase) {
		this.kbase = kbase;
	}
			
}
