package com.founder.drools.core.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;

import com.founder.drools.base.service.DroolsRequestService;
import com.founder.drools.core.request.HttpRequestBean;
import com.founder.drools.core.request.RuleBean;
import com.founder.framework.config.SpringCreator;
import com.founder.framework.organization.assign.service.OrgAssignPublic;
import com.founder.framework.organization.assign.service.OrgOrgAssignUserService;
import com.founder.framework.organization.assign.vo.OrgUserInfo;
import com.founder.framework.organization.department.bean.OrgOrganization;
import com.founder.framework.organization.department.service.OrgOrganizationService;
import com.founder.framework.organization.position.service.OrgPositionService;
import com.founder.framework.organization.user.bean.OrgUser;
import com.founder.framework.organization.user.service.OrgUserService;

/**
 * ****************************************************************************
 * @Package:      [com.founder.drools.core.model.DroolsTools.java]  
 * @ClassName:    [DroolsTools]   
 * @Description:  [规则中调用的工具类]   
 * @Author:       [zhang.hai@founder.com.cn]  
 * @CreateDate:   [2015年12月2日 上午9:48:04]   
 * @UpdateUser:   [ZhangHai(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2015年12月2日 上午9:48:04，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
public class DroolsTools {
	
	private static DroolsRequestService droolsRequestService;
	
	private static OrgOrganizationService orgOrganizationService;
	
	private static OrgPositionService orgPositionService;
	
	private static OrgOrgAssignUserService orgOrgAssignUserService;
	
	private static OrgUserService orgUserService;
	
	private static OrgAssignPublic orgAssignPublic=new OrgAssignPublic();
	
	/**
	 * 
	 * @Title: requestByGet
	 * @Description: TODO(GET方式请求远程应用服务器)
	 * @param @param methodId
	 * @param @param paramMap
	 * @param @return    设定文件
	 * @return Object    返回类型
	 * @throw
	 */
	public static Object requestByGet(String methodId,Map<?, ?> paramMap){
		String serviceUrl = getDroolsRequestService().getServiceUrl(methodId);
		
		//处理RestfulAnnotation接口 的请求方式
		int index=serviceUrl.indexOf('{');
		String paraName;
		while(index>0){
			paraName=serviceUrl.substring(index+1,serviceUrl.indexOf('}'));			
			serviceUrl=serviceUrl.replace('{'+paraName+'}', (String)paramMap.get(paraName));
			index=serviceUrl.indexOf('{');
		}
		
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

	/**
	 * 
	 * @Title: requestByPost
	 * @Description: TODO(Post方式请求远程服务器)
	 * @param @param methodId
	 * @param @param paramMap
	 * @param @return    设定文件
	 * @return Object    返回类型
	 * @throw
	 */
	public static Object requestByPost(String methodId,Map<?, ?> paramMap){
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
	
	/**
	 * 
	 * @Title: getOrgName
	 * @Description: TODO(获取机构对象)
	 * @param @param orgCode
	 * @param @return    设定文件
	 * @return OrgOrganization    返回类型
	 * @throw
	 */
	public static OrgOrganization getOrganization(String orgCode){		
		return getOrgOrganizationService().queryByOrgcode(orgCode);
	}
	
	/**
	 * 
	 * @Title: getOrgName
	 * @Description: TODO(获取机构名称)
	 * @param @param orgCode
	 * @param @return    设定文件
	 * @return OrgOrganization    返回类型
	 * @throw
	 */
	public static String getOrgName(String orgCode){		
		OrgOrganization org = getOrganization(orgCode);
		if(org!=null)
			return org.getOrgname();
		return null;
	}
	
	/**
	 * 
	 * @Title: getParentOrg
	 * @Description: TODO(获取上级机构)
	 * @param @param orgCode
	 * @param @return    设定文件
	 * @return OrgOrganization    返回类型
	 * @throw
	 */
	public static OrgOrganization getParentOrg(String orgCode){
		return getOrgOrganizationService().queryParentOrgByOrgcode(orgCode);	
	}
	
	/**
	 * 
	 * @Title: getParentOrgName
	 * @Description: TODO(获取上级机构名)
	 * @param @param orgCode
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throw
	 */
	public static String getParentOrgName(String orgCode){
		OrgOrganization org = getParentOrg(orgCode);
		if(org!=null){
			return org.getOrgname();
		}			
		
		return null;
	}
	
	/**
	 * 
	 * @Title: getOrgList
	 * @Description: TODO(获取包括当前机构的所有上级机构列表)
	 * @param @param orgCode
	 * @param @return    设定文件
	 * @return List    返回类型
	 * @throw
	 */
	public static List<OrgOrganization> getParentOrgList(String orgCode){
		
		return getOrgOrganizationService().queryUpOrgsByOrgcode(orgCode);
	}
	
	/**
	 * 
	 * @Title: getUserOrgByUserId
	 * @Description: TODO(获取用户所在的机构)
	 * @param @param userId
	 * @param @return    设定文件
	 * @return OrgOrganization    返回类型
	 * @throw
	 */
	public static OrgOrganization getUserOrgByUserId(String userId){
		return getOrgOrgAssignUserService().queryUserOrganization(userId);
	}
	
	/**
	 * 
	 * @Title: getSZ
	 * @Description: TODO(获取某个机构所在的派出所所长)
	 * @param @param orgCode
	 * @param @return    设定文件
	 * @return List<OrgUserInfo>    返回类型
	 * @throw
	 */
	public static List<OrgUserInfo> getSZ(String orgCode){
		OrgOrganization obj = getOrgOrganizationService().queryUpOrgByLevel(orgCode,"32");
		if(obj==null) 
			return new ArrayList<OrgUserInfo>();
		String szOrgCode = obj.getOrgcode();//派出所
		List<OrgUserInfo> list = orgAssignPublic.queryUserByOrgAndPos(szOrgCode, "SZ");
		return list;
	}
	
	/**
	 * 
	 * @Title: getOrgLeader
	 * @Description: TODO(获取部门领导)
	 * @param @param orgCode
	 * @param @param pos
	 * @param @return    设定文件
	 * @return List<OrgUserInfo>    返回类型
	 * @throw
	 */
	public static List<OrgUserInfo> getOrgLeader(String orgCode,String pos){		
		List<OrgUserInfo> list = orgAssignPublic.queryUserByOrgAndPos(orgCode, pos);
		return list;
	}
	
	/**
	 * 
	 * @Title: getSameLeader
	 * @Description: TODO(获取相同的领导，裁决时用)
	 * @param @param orgCode
	 * @param @param orgCode2
	 * @param @return    设定文件
	 * @return List<OrgUserInfo>    返回类型
	 * @throw
	 */
	public static List<OrgUserInfo> getSameLeader(String orgCode,String orgCode2){
		OrgOrganization sameOrg = getOrgOrganizationService().querySameParentOrg(orgCode, orgCode2);
		
		if(sameOrg!=null){
			if("10".equals(sameOrg.getOrglevel())){//市公安局
				return orgAssignPublic.queryUserByOrgAndPos(sameOrg.getOrgcode(), "JZ");
			}else if("21".equals(sameOrg.getOrglevel())){//分局
				return orgAssignPublic.queryUserByOrgAndPos(sameOrg.getOrgcode(), "JZ");
			}else if("32".equals(sameOrg.getOrglevel())){//派出所
				return orgAssignPublic.queryUserByOrgAndPos(sameOrg.getOrgcode(), "SZ");
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @Title: getOrgUserByUserId
	 * @Description: TODO(通过UserId查询OrgUser对象)
	 * @param @param userId
	 * @param @return    设定文件
	 * @return OrgUser    返回类型
	 * @throw
	 */
	public static OrgUser getOrgUserByUserId(String userId){
		return getOrgUserService().queryByUserid(userId);		
	}
	
	/**
	 * 
	 * @Title: jsonToMap
	 * @Description: TODO(将json字符串转成Map,递归调用，如果不能转成Map，直接返回String)
	 * @param @param jsonString
	 * @param @return Map或者String
	 * @param @throws JSONException    设定文件
	 * @return Object    返回类型
	 * @throw
	 */
	public static Object jsonToMap(String jsonString) throws JSONException {
    	try{
    		JSONObject jsonObject=JSONObject.fromObject(jsonString);
	        
	        Map<String, Object> result = new HashMap<String, Object>();
	        Iterator<?> iterator = jsonObject.keys();
	        String key = null;
	        String value = null;
	        
	        while (iterator.hasNext()) {	
	            key = (String) iterator.next();
	            value = jsonObject.getString(key);
	            result.put(key, jsonToMap(value));	
	        }
	        return result;
    	}catch(Exception e){
    		return jsonString;
    	}

    }
	
	/**
	 * 
	 * @Title: isEmpty
	 * @Description: TODO(判断String是否为空)
	 * @param @param str
	 * @param @return    设定文件
	 * @return boolean    返回类型
	 * @throw
	 */
	public static boolean isEmpty(String str){
		if(str==null || str.length()==0)
			return false;
		else
			return true;
	}
	
	/**
	 * 
	 * @Title: getDroolsRequestService
	 * @Description: TODO(获取远程请求地址的服务)
	 * @param @return    设定文件
	 * @return DroolsRequestService    返回类型
	 * @throw
	 */
	private static DroolsRequestService getDroolsRequestService(){
		if(droolsRequestService==null){
			droolsRequestService=(DroolsRequestService) SpringCreator.getBean("droolsRequestService");
		}
		return droolsRequestService;
	}
	
	
	private static OrgOrganizationService getOrgOrganizationService(){
		if(orgOrganizationService == null){
			orgOrganizationService=(OrgOrganizationService) SpringCreator.getBean("orgOrganizationService");
		}
		
		return orgOrganizationService;
	}
	
	private static OrgPositionService getOrgPositionService(){
		if(orgPositionService == null){
			orgPositionService=(OrgPositionService) SpringCreator.getBean("orgPositionService");
		}
		
		return orgPositionService;
	}
	
	private static OrgOrgAssignUserService getOrgOrgAssignUserService(){
		if(orgOrgAssignUserService == null){
			orgOrgAssignUserService=(OrgOrgAssignUserService) SpringCreator.getBean("orgOrgAssignUserService");
		}
		
		return orgOrgAssignUserService;
	}
	
	private static OrgUserService getOrgUserService(){
		if(orgUserService == null){
			orgUserService=(OrgUserService) SpringCreator.getBean("orgUserService");
		}
		
		return orgUserService;
	}
	
	/**
	 *  response the message use the Map 
	 * @param msgTitle
	 * @param msgContent
	 * @param xxlb
	 * @param jslx
	 * @param msgJsr
	 * @return
	 */
	public static Map resMapToUser(String msgTitle,String msgContent,String xxlb,String jslx,String[] mesJsr){
		Map resMap = new HashMap();
		resMap.put("msgTitle",msgTitle);
		resMap.put("msgContent",msgContent);
		resMap.put("xxlb",xxlb);
		resMap.put("jslx",jslx);
		resMap.put("msgJsr",mesJsr);
		return resMap;
		
	}
	
	/**
	 *  response the many message use the Map 
	 * @param msgTitle
	 * @param msgContent
	 * @param xxlb
	 * @param jslx
	 * @param msgJsr
	 * @return
	 */
	public static Map resMapToUserAny(String msgTitle,String msgContent,String xxlb,String jslx,String[][] mesJsr){
		Map resMap = new HashMap();
		resMap.put("msgTitle",msgTitle);
		resMap.put("msgContent",msgContent);
		resMap.put("xxlb",xxlb);
		resMap.put("jslx",jslx);
		resMap.put("msgJsr",mesJsr);
		return resMap;
		
	}
	/**
	 * judge the parameter is right or not
	 * @param map
	 * @param ruleBean
	 * @param arg
	 * @return
	 */
	public static boolean orSatisfy(Map map,RuleBean ruleBean,String ... arg){
		Map<String,String> hashMap = map;
		for(Map.Entry<String,String> key : hashMap.entrySet()){
			if(key.getValue() ==null){
				ruleBean.setResponse("传入参数"+key.getKey()+"为空值！");
				return false;
			}
		}
		for(int i = 0;i < arg.length;i ++){
		if(!(map.containsKey(arg[i]))){
			ruleBean.setResponse("传入的参数缺少"+arg[i]);
			return false;
			}
		}
		return true;
		
	}
}
