package com.founder.drools.core.model;

import java.util.Map;

import org.apache.http.HttpEntity;

import com.founder.drools.base.service.DroolsRequestService;
import com.founder.drools.core.request.HttpRequestBean;
import com.founder.framework.config.SpringCreator;

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
