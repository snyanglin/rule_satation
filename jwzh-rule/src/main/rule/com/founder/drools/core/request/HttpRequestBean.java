package com.founder.drools.core.request;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * ****************************************************************************
 * @Package:      [com.founder.drools.core.request.HttpRequestBean.java]  
 * @ClassName:    [HttpRequestBean]   
 * @Description:  [httpclient调用的简单封装]   
 * @Author:       [zhang.hai@founder.com.cn]  
 * @CreateDate:   [2015年11月24日 上午11:08:48]   
 * @UpdateUser:   [ZhangHai(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2015年11月24日 上午11:08:48，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
public class HttpRequestBean {
	private String serviceUrl;//服务地址
	private int connectTimeout=3000;
	private int connectionRequestTimeout=3000;
	private int socketTimeout=3000;
	private HttpClient httpClient=null;
	private RequestConfig requestConfig;
	
	/**
	 * 
	 * <p>Title:HttpRequestBean </p>
	 * <p>Description: 构造HttpRequestBean对象</p>
	 * @param serviceUrl 服务请求地址
	 * @param connectTimeout 连接超时时间，毫秒
	 * @param connectionRequestTimeout 请求超时时间，毫秒
	 * @param socketTimeout 返回超时时间，毫秒
	 */
	public HttpRequestBean(String serviceUrl,int connectTimeout,int connectionRequestTimeout,int socketTimeout){
		this.serviceUrl=serviceUrl;
		if("/".equals(serviceUrl.substring(serviceUrl.length()-1)))
			this.serviceUrl = serviceUrl.substring(0,serviceUrl.length()-1);
		
		this.connectTimeout = connectTimeout;		
		this.connectionRequestTimeout = connectionRequestTimeout;
		this.socketTimeout=socketTimeout;
		init();
	}
	
	/**
	 * 
	 * <p>Title: HttpRequestBean</p>
	 * <p>Description:构造HttpRequestBean对象 </p>
	 * @param serviceUrl 服务请求地址，使用默认超时时间3秒
	 */
	public HttpRequestBean(String serviceUrl){
		this.serviceUrl=serviceUrl;
		if("/".equals(serviceUrl.substring(serviceUrl.length()-1)))
			this.serviceUrl = serviceUrl.substring(0,serviceUrl.length()-1);
		init();
	}	
	
	private void init(){
		Builder builder = RequestConfig.custom();
		builder.setConnectTimeout(connectTimeout);//连接超时			
		builder.setConnectionRequestTimeout(connectionRequestTimeout);//请求超时
		builder.setSocketTimeout(socketTimeout);//返回超时
		this.requestConfig = builder.build();
	}
		
	/**
	 * 
	 * @Title: doHttpGet
	 * @Description: TODO(通过HttpGet请求服务方法)
	 * @param @param params GET方式的参数String
	 * @param @return
	 * @param @throws Exception    设定文件
	 * @return String    返回类型
	 * @throw
	 */
	public String doHttpGet(String params) throws Exception{
		String url=this.serviceUrl;
		if(url==null){
			throw new Exception("ServiceUrl can not be null!");			
		}
		
		if("/".equals(url.substring(url.length()-1)))
			url=url.substring(0,url.length()-1);
		
		if(params!=null && params.length()>0)
			url =url+"?"+params;
		System.out.println("Get request from:"+url);
		
		HttpGet httpRequst = new HttpGet(url);	

		return this.doHttp(httpRequst);
	}		
	
	/**
	 * 
	 * @Title: doHttpPost
	 * @Description: TODO(通过HttpPost请求服务方法)
	 * @param @param httpEntity
	 * @param @return
	 * @param @throws Exception    设定文件
	 * @return String    返回类型
	 * @throw
	 */
	public String doHttpPost(HttpEntity httpEntity) throws Exception{	
		if(serviceUrl==null){
			throw new Exception("ServiceUrl can not be null!");					
		}
		
		System.out.println("Post request from:"+serviceUrl);
		
		HttpPost httpRequst = new HttpPost(serviceUrl);//创建HttpPost对象
		
		httpRequst.setEntity(httpEntity);
		
    	return this.doHttp(httpRequst);		    	
	}
	
	private String doHttp(HttpRequestBase httpRequst) throws Exception{
		try {
			
			httpRequst.setConfig(this.requestConfig);
			
			HttpResponse httpResponse = getHttpClient().execute(httpRequst);
		    if(httpResponse.getStatusLine().getStatusCode() == 200){
		    	HttpEntity httpEntity = httpResponse.getEntity();
		    	String res=EntityUtils.toString(httpEntity);//取出应答字符串
		    	return 	res;	    	    	  
		    }else{
		    	throw new Exception("Service request faild");	
		    }
		    
		} catch (Exception e) {			
			e.printStackTrace();
			throw e;
		}finally{
			httpRequst.releaseConnection();			
		}
	}
	
	public HttpClient getHttpClient() {
		if(this.httpClient==null){
			return HttpClients.createDefault();
		}else{
			return this.httpClient;
		}
	}

	public void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
	
	
	
}
