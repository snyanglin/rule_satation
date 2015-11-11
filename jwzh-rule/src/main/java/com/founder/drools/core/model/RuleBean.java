package com.founder.drools.core.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
/**
 * ****************************************************************************
 * @Package:      [com.founder.drools.core.model.RuleBean.java]  
 * @ClassName:    [RuleBean]   
 * @Description:  [规则对象，保存规则和返回值]   
 * @Author:       [zhang.hai@founder.com.cn]  
 * @CreateDate:   [2015年11月6日 下午4:57:39]   
 * @UpdateUser:   [ZhangHai(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2015年11月6日 下午4:57:39，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
public class RuleBean {
	private String ruleFileName;//规则文件名
	private String ruleName;//规则名
	private int resStatus=1;//0成功 1失败
	private Object response;//返回对象
	
	public String getRuleFileName() {
		return ruleFileName;
	}
	public void setRuleFileName(String ruleFileName) {
		this.ruleFileName = ruleFileName;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}	
	public int getResStatus() {
		return resStatus;
	}
	public void setResStatus(int resStatus) {
		this.resStatus = resStatus;
	}
	public Object getResponse() {
		return response;
	}
	public void setResponse(Object response) {
		this.response = response;
	}
	
	/**
	 * 
	 * @Title: executeServiceByHttp
	 * @Description: TODO(HTTP GET方式调用远程服务)
	 * @param @param methodName 方法名
	 * @param @param params 参数
	 * @param @return    设定文件
	 * @return String    
	 * @throw
	 */
	public Map executeServiceByHttp(String methodName,String params){
		Map map=new HashMap();
		map.put("status", "fail");
		
		
		map.put("errorMessage", "no error info");
		String url="http://192.168.1.161:8080/syrk/ruleSys/"+methodName+"?"+params;
		System.out.println(url);
		String resStr=null;
		GetMethod method = new GetMethod(url);		

		try {
			/*******************************************************************
			 * 创建与网关的HTTP连接，发送认证请求报文，并接收认证响应报文*
			 ******************************************************************/
			/** *  创建与网关的HTTP连接 ** 开始 * */
			int statusCode = 500;
			HttpClient httpClient =  new HttpClient();
			httpClient.setTimeout(10000);
			httpClient.setConnectionTimeout(10000);
			
			//设置报文传送的编码格式
			method.setRequestHeader("Content-Type","text/xml;charset=UTF-8");	
			
				/** * 发送通讯报文与网关通讯 ** 开始 * */
			statusCode = httpClient.executeMethod(method);				
				/** *  发送通讯报文与网关通讯 ** 结束 * */
				
			if (statusCode == HttpStatus.SC_OK
					|| statusCode == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
					
				resStr=method.getResponseBodyAsString();
				map.put("status", "success");
				map.put("resStr", resStr);
			}
			
		} catch (Exception e) {
			//e.printStackTrace();			
			map.put("errorMessage", e.getLocalizedMessage());
		}finally{
			method.releaseConnection();
		}
		
		return map;
	}		
}
