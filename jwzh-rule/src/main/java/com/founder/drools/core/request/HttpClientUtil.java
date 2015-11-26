package com.founder.drools.core.request;

import java.util.concurrent.TimeUnit;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * ****************************************************************************
 * @Package:      [com.founder.drools.core.request.HttpClientUtil.java]  
 * @ClassName:    [HttpClientUtil]   
 * @Description:  [httpclient连接池管理类]   
 * @Author:       [zhang.hai@founder.com.cn]  
 * @CreateDate:   [2015年11月24日 下午4:37:41]   
 * @UpdateUser:   [ZhangHai(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2015年11月24日 下午4:37:41，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
public class HttpClientUtil {
	private PoolingHttpClientConnectionManager connManager;	
	private HttpClientBuilder httpClientBuilder;
	public HttpClientUtil(int maxCon,int minCon,String server,int port,int releaseTime,int releaseTimeOut) {
		connManager= new PoolingHttpClientConnectionManager();
		connManager.setMaxTotal(maxCon);//连接池最大连接数
		connManager.setDefaultMaxPerRoute(minCon);//每个路由的基础连接数
		HttpHost localhost = new HttpHost(server, port);//路由
		connManager.setMaxPerRoute(new HttpRoute(localhost), maxCon);
		httpClientBuilder = HttpClients.custom().setConnectionManager(connManager);
		new connManagerRelease(connManager, releaseTime, releaseTimeOut).start();
	}
	public HttpClient getHttpClient() {						
		return httpClientBuilder.build();
	}	
}

/**
 * ****************************************************************************
 * @Package:      [com.founder.drools.core.request.HttpClientUtil.java]  
 * @ClassName:    [connManagerRelease]   
 * @Description:  [定时清理连接池连接的线程]   
 * @Author:       [zhang.hai@founder.com.cn]  
 * @CreateDate:   [2015年11月24日 下午4:38:32]   
 * @UpdateUser:   [ZhangHai(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2015年11月24日 下午4:38:32，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
class connManagerRelease extends Thread{
	private HttpClientConnectionManager connManager;
	private volatile boolean shutdown;
	private int releaseTime=5000;//每多少毫秒清理一次
	private int releaseTimeOut=3000;//需要清理的超时链接的超时时间，毫秒
	
	public connManagerRelease(HttpClientConnectionManager connManager,int releaseTime,int releaseTimeOut){
		super();
		this.connManager=connManager;
		this.releaseTime=releaseTime;
		this.releaseTimeOut=releaseTimeOut;
	}
	
	public void run() {
	      try {
	        while (!shutdown) {
	          synchronized (this) {
	            wait(releaseTime);	            
	            //System.out.println("清理失效的HttpClient连接");
	            // 关闭失效连接
	            connManager.closeExpiredConnections();
	            //关闭空闲超过releaseTimeOut毫秒的连接
	            connManager.closeIdleConnections(releaseTimeOut, TimeUnit.MICROSECONDS);
	          }
	        }
	      } catch (InterruptedException ex) {
	      }
	}
	
	public void shutdown() {
	      shutdown = true;
	      synchronized (this) {
	        notifyAll();
	      }
	}


}