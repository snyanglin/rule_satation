package com.founder.drools.core.model;

import java.util.Map;

import org.apache.http.HttpEntity;

import com.founder.drools.base.service.DroolsRequestService;
import com.founder.drools.core.request.HttpRequestBean;
import com.founder.framework.config.SpringCreator;

/**
 * ****************************************************************************
 * @Package:      [com.founder.drools.core.model.ServiceRequestBean.java]  
 * @ClassName:    [ServiceRequestBean]   
 * @Description:  [规则中科调用的应用服务请求工具类]   
 * @Author:       [zhang.hai@founder.com.cn]  
 * @CreateDate:   [2015年12月2日 上午9:48:04]   
 * @UpdateUser:   [ZhangHai(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2015年12月2日 上午9:48:04，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
public class ServiceRequestBean {
	
	private DroolsRequestService droolsRequestService;
	
	
	public Object requestByGet(String methodId,Map paramMap){
		String serviceUrl = getDroolsRequestService().getServiceUrl(methodId);
		String paraString = getDroolsRequestService().getGetParam(methodId, paramMap);
		
		HttpRequestBean httpRequestBean = getDroolsRequestService().getHttpRequestBean();
		
		try {
			httpRequestBean.setServiceUrl(serviceUrl);
			return httpRequestBean.doHttpGet(paraString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Object requestByPost(String methodId,Map paramMap){
		String serviceUrl = getDroolsRequestService().getServiceUrl(methodId);
		HttpEntity httpEntity = getDroolsRequestService().getPostParam(methodId, paramMap);
		
		HttpRequestBean httpRequestBean = getDroolsRequestService().getHttpRequestBean();
		
		try {
			httpRequestBean.setServiceUrl(serviceUrl);
			return httpRequestBean.doHttpPost(httpEntity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private DroolsRequestService getDroolsRequestService(){
		if(this.droolsRequestService==null){
			this.droolsRequestService=(DroolsRequestService) SpringCreator.getBean("droolsRequestService");
		}
		return droolsRequestService;
	}
}
