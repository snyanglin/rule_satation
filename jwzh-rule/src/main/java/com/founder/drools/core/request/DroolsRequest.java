package com.founder.drools.core.request;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * ****************************************************************************
 * @Package:      [com.founder.drools.core.request.DroolsRequest.java]  
 * @ClassName:    [DroolsRequest]   
 * @Description:  [规则请求类，封装了规则平台调用的方法]   
 * @Author:       [zhang.hai@founder.com.cn]  
 * @CreateDate:   [2015年11月24日 下午2:14:26]   
 * @UpdateUser:   [ZhangHai(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2015年11月24日 下午2:14:26，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
public class DroolsRequest {
	private String serverIp;//规则平台IP
	private String serverPort;//规则平台端口
	private int connectTimeout=3000;
	private int connectionRequestTimeout=3000;
	private int socketTimeout=3000;
	private int maxCon=20;
	private int minCon=10;
	private int releaseTime=5000;
	private int releaseTimeOut=3000;
	private HttpRequestBean httpRequestBean=null;
	private HttpClientUtil httpClientUtil=null;
	
	public DroolsRequest(){		
	}
	
	/**
	 * 
	 * <p>Title: DroolsRequest</p>
	 * <p>Description:构造DroolsRequest对象 </p>
	 * @param serviceUrl 服务请求地址，使用默认连接、请求、返回超时时间3秒
	 * @param serverPort 规则平台端口
	 */
	public DroolsRequest(String serverIp,String serverPort){
		this.serverIp=serverIp;
		this.serverPort=serverPort;
		this.initHttpRequestBean();
	}
	
	/**
	 * 
	 * <p>Title: DroolsRequest</p>
	 * <p>Description: 构造DroolsRequest对象</p>
	 * @param serverIp 规则平台IP
	 * @param serverPort 规则平台端口
	 * @param connectTimeout 连接超时时间，毫秒
	 * @param connectionRequestTimeout 请求超时时间，毫秒
	 * @param socketTimeout 返回超时时间，毫秒
	 */
	public DroolsRequest(String serverIp,String serverPort,int connectTimeout,int connectionRequestTimeout,int socketTimeout){
		this.serverIp=serverIp;		
		this.connectTimeout = connectTimeout;		
		this.connectionRequestTimeout = connectionRequestTimeout;
		this.socketTimeout=socketTimeout;
		this.initHttpRequestBean();
	}
	
	
	public DroolsRequest(String serverIp,String serverPort,int connectTimeout,int connectionRequestTimeout,int socketTimeout,int maxCon,int minCon,int releaseTime,int releaseTimeOut){
		this.serverIp=serverIp;		
		this.connectTimeout = connectTimeout;		
		this.connectionRequestTimeout = connectionRequestTimeout;
		this.socketTimeout=socketTimeout;
	}
	
	public void init(){
		this.initHttpRequestBean();
		this.initHttpClientPool(this.maxCon,this.minCon,this.releaseTime,this.releaseTimeOut);
	}
	
	private void initHttpRequestBean(){
		String serviceUrl="http://"+this.serverIp+":"+this.serverPort+"/jwzh-rule/founderRule/executeRule";
		httpRequestBean = new HttpRequestBean(serviceUrl,connectTimeout,connectionRequestTimeout,socketTimeout);
	}
	
	/**
	 * 
	 * @Title: initHttpClientPool
	 * @Description: TODO((如果调用了此方法，http链接将会采用连接池来管理连接))
	 * @param @param maxCon 最大连接数
	 * @param @param minCon 最小连接数
	 * @param @param releaseTime 连接池清理间隔时间，毫秒
	 * @param @param releaseTimeOut  连接池需要清理的连接超时时间，毫秒
	 * @return void    返回类型
	 * @throw
	 */
	public void initHttpClientPool(int maxCon,int minCon,int releaseTime,int releaseTimeOut){
		httpClientUtil = new HttpClientUtil(maxCon, minCon, this.serverIp, Integer.valueOf(this.serverPort), releaseTime, releaseTimeOut);		
	}
	
	/**
	 * 
	 * @Title: requestDroolsServer
	 * @Description: TODO(向规则服务器请求规则)
	 * @param @param ruleFileName 规则文件名
	 * @param @param ruleName 规则名
	 * @param @param paramObj 参数对象
	 * @param @return
	 * @param @throws Exception    设定文件
	 * @return RuleBean    返回类型
	 * @throw
	 */
	public RuleBean requestDroolsServer(String ruleFileName,String ruleName,Object paramObj) throws Exception{
		RuleBean ruleBean = new RuleBean();
		
		//规则文件名
		if(ruleFileName==null || ruleFileName.trim().length()==0)
			throw new Exception("RuleFileName can not be null!");
		ruleBean.setRuleFileName(ruleFileName);
		
		//规则名
		if(ruleName==null || ruleName.trim().length()==0)
			throw new Exception("RuleName can not be null!");
		ruleBean.setRuleName(ruleName);
		
		//参数对象，要处理成json字符串
		if(paramObj!=null){			
			String str=new Gson().toJson(paramObj);
			System.out.println("Drools request parameter string:"+str);
			
			ruleBean.setJsonParamStr(str);
		}
		
		if(ruleBean.getRuleFileName()==null || ruleBean.getRuleFileName().length()==0)
			throw new Exception("RuleFileName can not be null!");
		
		if(ruleBean.getRuleName()==null || ruleBean.getRuleName().length()==0)
			throw new Exception("RuleName can not be null!");
		
		
		if(httpClientUtil!=null){//采用连接池生成HttpClient
			httpRequestBean.setHttpClient(httpClientUtil.getHttpClient());
		}
		
		String res= httpRequestBean.doHttpPost(this.getPostEntity(ruleBean));		
		Object resObj=this.XStream2Obj(res);
		return (RuleBean)resObj;
	}
	
	

	/**
	 * 
	 * @Title: Obj2XStream
	 * @Description: TODO(将java Object转成xml格式的String)
	 * @param @param paramObj
	 * @param @return
	 * @param @throws Exception    设定文件
	 * @return String    返回类型
	 * @throw
	 */
	public static String Obj2XStream(Object paramObj) throws Exception{
		XStream xStream = new XStream(new DomDriver());
		String xmlStr = xStream.toXML(paramObj);
		return xmlStr;
	}
	
	/**
	 * 
	 * @Title: XStream2Obj
	 * @Description: TODO(将xml格式的String转成对象)
	 * @param @param xStr
	 * @param @return
	 * @param @throws Exception    设定文件
	 * @return Object    返回类型
	 * @throw
	 */
	public static Object XStream2Obj(String xStr) throws Exception{
		XStream xStream = new XStream(new DomDriver());
		return xStream.fromXML(xStr);		
	}
	
	/**
	 * 
	 * @Title: getPostEntity
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param ruleBean
	 * @param @return
	 * @param @throws Exception    设定文件
	 * @return HttpEntity    返回类型
	 * @throw
	 */
	private HttpEntity getPostEntity(Object paramObj) throws Exception{		
		String xmlSstr = this.Obj2XStream(paramObj);
		
		List params = new ArrayList();    	
    	params.add(new BasicNameValuePair("ruleBeanXmlStr", xmlSstr));
    	return new UrlEncodedFormEntity(params,HTTP.UTF_8);
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public int getConnectionRequestTimeout() {
		return connectionRequestTimeout;
	}

	public void setConnectionRequestTimeout(int connectionRequestTimeout) {
		this.connectionRequestTimeout = connectionRequestTimeout;
	}

	public int getSocketTimeout() {
		return socketTimeout;
	}

	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	public int getMaxCon() {
		return maxCon;
	}

	public void setMaxCon(int maxCon) {
		this.maxCon = maxCon;
	}

	public int getMinCon() {
		return minCon;
	}

	public void setMinCon(int minCon) {
		this.minCon = minCon;
	}

	public int getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(int releaseTime) {
		this.releaseTime = releaseTime;
	}

	public int getReleaseTimeOut() {
		return releaseTimeOut;
	}

	public void setReleaseTimeOut(int releaseTimeOut) {
		this.releaseTimeOut = releaseTimeOut;
	}
	
}
