package com.founder.drools.core.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.founder.framework.config.SystemConfig;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class HttpRequestBean {
	private String serviceUrl;//服务地址
	private int connectTimeout=3000;
	private int connectionRequestTimeout=3000;
	
	public HttpRequestBean(String serviceUrl) throws Exception{
		this.serviceUrl=serviceUrl;
		
		String httpConnectTimeout = SystemConfig.getString("httpConnectTimeout");
		String httpConnectionRequestTimeout = SystemConfig.getString("httpConnectionRequestTimeout");
		
		if(httpConnectTimeout!=null && httpConnectTimeout.length()>0)
			connectTimeout = Integer.valueOf(httpConnectTimeout).intValue();
		if(httpConnectionRequestTimeout!=null && httpConnectionRequestTimeout.length()>0)
			connectionRequestTimeout = Integer.valueOf(httpConnectionRequestTimeout).intValue();
	}	
	
	/**
	 * 
	 * @Title: doHttpGet
	 * @Description: TODO(通过HttpGet请求服务方法)
	 * @param @param params
	 * @param @return
	 * @param @throws Exception    设定文件
	 * @return String    返回类型
	 * @throw
	 */
	public Map doHttpGet(String params){
		Map map = new HashMap();
		map.put("resStatus", "1");//失败
		if(serviceUrl==null){
			map.put("errorMsg", "ServiceUrl can not be null!");
			return map;		
		}
		
		if("/".equals(serviceUrl.substring(serviceUrl.length()-1)))
			serviceUrl = serviceUrl.substring(0,serviceUrl.length()-1);
		
		serviceUrl +="?"+params;
		System.out.println("Get request from:"+serviceUrl);
		try{
			HttpGet httpRequst = new HttpGet(serviceUrl);	
			map.put("resStr",this.doHttp(httpRequst));
			map.put("resStatus", "0");//成功
		}catch(Exception e){
			e.printStackTrace();
			map.put("errorMsg", e.toString());
		}
		return map;
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
	public Map doHttpPost(HttpEntity httpEntity) {	
		Map map = new HashMap();
		map.put("resStatus", "1");//失败
		if(serviceUrl==null){
			map.put("errorMsg", "ServiceUrl can not be null!");
			return map;		
		}
		
		if("/".equals(serviceUrl.substring(serviceUrl.length()-1)))
			serviceUrl = serviceUrl.substring(0,serviceUrl.length()-1);
		
		
		System.out.println("Post request from:"+serviceUrl);
		
		try{
			HttpPost httpRequst = new HttpPost(serviceUrl);//创建HttpPost对象						
			
			httpRequst.setEntity(httpEntity);
			
			map.put("resStr",this.doHttp(httpRequst));
			map.put("resStatus", "0");//成功
		}catch(Exception e){
			e.printStackTrace();
			map.put("errorMsg", e.toString());
		}
		return map;
	}
	
	private String doHttp(HttpRequestBase httpRequst) throws Exception{
		try {
			Builder builder = RequestConfig.custom();
			builder.setConnectTimeout(connectTimeout);//连接超时			
			builder.setConnectionRequestTimeout(connectionRequestTimeout);//请求超时
			
			httpRequst.setConfig(builder.build());
			
			HttpResponse httpResponse = HttpClients.createDefault().execute(httpRequst);
		    if(httpResponse.getStatusLine().getStatusCode() == 200){
		    	HttpEntity httpEntity = httpResponse.getEntity();
		    	String res=EntityUtils.toString(httpEntity);//取出应答字符串		
		    	return res;
		    }else{
		    	throw new Exception("Service("+httpRequst.getURI()+") request faild");	
		    }
		    
		} catch (Exception e) {			
			e.printStackTrace();
			throw e;
		}finally{
			httpRequst.releaseConnection();			
		}
	}
	
	public String Obj2XStream(Object paramObj) throws Exception{
		XStream xStream = new XStream(new DomDriver());
		String xmlStr = xStream.toXML(paramObj);
		return xmlStr;
	}
}
