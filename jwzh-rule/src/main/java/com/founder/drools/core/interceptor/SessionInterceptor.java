package com.founder.drools.core.interceptor;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import com.founder.drools.base.model.DroolsUser;
import com.founder.framework.base.entity.SessionBean;
import com.founder.framework.components.AppConst;
import com.founder.framework.httpService.BaseHttpClient;
import com.founder.framework.httpService.HttpClientResultBean;
import com.founder.framework.httpService.SSOAuthHttpClient;

/**
 * ****************************************************************************
 * @Package:      [com.founder.framework.interceptor.SessionInterceptor.java]  
 * @ClassName:    [SessionInterceptor]   
 * @Description:  [一句话描述该类的功能]   
 * @Author:       [weiwen ]   
 * @CreateDate:   [2014-1-7 下午6:51:42]   
 * @UpdateUser:   [weiwen(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2015-1-12 (如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [增加用外接服务接口的调用验证，外接服务不需要验证SESSION]  
 * @Version:      [v1.0]
 */
public class SessionInterceptor extends HandlerInterceptorAdapter{

	private List<String> allowUrls;
	
	private SSOAuthHttpClient client = new SSOAuthHttpClient();
	
	private static final ThreadLocal<String> threadLocal = new ThreadLocal<String>();  
	
	public List<String> getAllowUrls() {
		return allowUrls;
	}

	public void setAllowUrls(List<String> allowUrls) {
		this.allowUrls = allowUrls;
	}

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		//允许通过的URL不验session
		String requestUrl = request.getRequestURI();  
		for(String url : allowUrls) {  
			if(Pattern.matches(url, requestUrl)) {  
				return true;  
			}  
		} 
		
		Object user=request.getSession().getAttribute("LoginUser");
		if(user==null || !(user instanceof DroolsUser)){
			
			PrintWriter out = response.getWriter();  
			StringBuilder builder = new StringBuilder();  
			String path = request.getContextPath();
			builder.append("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /></head>");
			builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");  
			builder.append("alert(\"\u7528\u6237\u64CD\u4F5C\u8D85\u65F6,\u8BF7\u91CD\u65B0\u767B\u5F55\uFF01\");");  
			builder.append("window.top.location.href=\"");  
			builder.append(path+"/LoginFirst.jsp\";</script>");  
			out.print(builder.toString());  
			out.close();
			return false;
		}
		
		return true;
	}
}
