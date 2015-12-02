package com.founder.drools.base.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.founder.drools.base.model.Drools_method;
import com.founder.drools.base.model.Drools_method_parameter;
import com.founder.drools.base.model.Drools_service;
import com.founder.drools.base.model.Drools_url;
import com.founder.drools.core.request.HttpClientUtil;
import com.founder.drools.core.request.HttpRequestBean;
import com.founder.framework.config.SystemConfig;

/**
 * ****************************************************************************
 * @Package:      [com.founder.drools.base.service.DroolsRequestService.java]  
 * @ClassName:    [DroolsRequestService]   
 * @Description:  [规则中请求远程应用系统的服务类]   
 * @Author:       [zhang.hai@founder.com.cn]  
 * @CreateDate:   [2015年12月2日 下午5:19:28]   
 * @UpdateUser:   [ZhangHai(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2015年12月2日 下午5:19:28，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
@Service
public class DroolsRequestService {
	@Autowired
	private DroolsMethodService droolsMethodService;
	@Autowired
	private DroolsParamService droolsParamService;
	@Autowired
	private DroolsServiceService droolsServiceService;
	@Autowired
	private DroolsUrlService droolsUrlService;
	
	private HttpClientUtil httpClientUtil;
	private HttpRequestBean httpRequestBean;
	/**
	 * 
	 * @Title: getServiceUrl
	 * @Description: TODO(获取系统中方法对应的请求url)
	 * @param @param methodId
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throw
	 */
	public String getServiceUrl(String methodId){
		try{
			Drools_method methodEntity = droolsMethodService.queryMethodById(methodId);//方法		
			
			
			
			Drools_service serviceEntity = droolsServiceService.queryServiceById(methodEntity.getServiceid());//服务
			
			Drools_url urlEntity = droolsUrlService.queryUrlById(serviceEntity.getUrlid());//地址
			
			return urlEntity.getUrl()+"/"+serviceEntity.getServicename()+"/"+methodEntity.getMethodname();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public String getGetParam(String methodId,Map paramMap){
		List<Drools_method_parameter> paramList=droolsParamService.getParamList(methodId);//参数
		String paramname;
		String res="";
		
		if(paramList!=null)
			for(int i=0;i<paramList.size();i++){
				paramname=paramList.get(i).getParamname();
				if(!"".equals(res))	res+="&";
				res+=paramname+"="+paramMap.get(paramname);
			}
		return res;
	}
	
	private HttpClient getHttpClient(){
		if(httpClientUtil==null){
			String maxCon=SystemConfig.getString("HttpServiceMaxCon");			
			
			String minCon=SystemConfig.getString("HttpServiceMinCon");			
			
			String serverIp=SystemConfig.getString("HttpServiceIp");			
			
			String serverPort=SystemConfig.getString("HttpServicePort");			
			
			String releaseTime=SystemConfig.getString("HttpServiceReleaseTime");			
			
			String releaseTimeOut=SystemConfig.getString("HttpServiceReleaseTimeOut");			
			
			httpClientUtil = new HttpClientUtil(Integer.valueOf(maxCon), Integer.valueOf(minCon), serverIp, Integer.valueOf(serverPort), Integer.valueOf(releaseTime), Integer.valueOf(releaseTimeOut));	
		}
		
		if(this.httpClientUtil==null)
			return HttpClients.createDefault();
			
		return this.httpClientUtil.getHttpClient();
	}
	
	public HttpRequestBean getHttpRequestBean(){
		if(httpRequestBean==null){
			String connectTimeout=SystemConfig.getString("HttpServiceConnectTimeout");
			String connectionRequestTimeout=SystemConfig.getString("HttpServiceConnectionRequestTimeout");
			String socketTimeout=SystemConfig.getString("HttpServiceSocketTimeout");
			httpRequestBean = new HttpRequestBean("127.0.0.1",Integer.valueOf(connectTimeout),Integer.valueOf(connectionRequestTimeout),Integer.valueOf(socketTimeout));
		}
		httpRequestBean.setHttpClient(this.getHttpClient());
		return httpRequestBean;
	}

	public HttpEntity getPostParam(String methodId, Map paramMap) {
		List<Drools_method_parameter> paramList=droolsParamService.getParamList(methodId);//参数
		List params = new ArrayList();
		String paramname;		
		
		if(paramList!=null)
			for(int i=0;i<paramList.size();i++){
				paramname=paramList.get(i).getParamname();
				params.add(new BasicNameValuePair(paramname, (String)paramMap.get(paramname)));		
			}

    	try {
			return new UrlEncodedFormEntity(params,HTTP.UTF_8);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
