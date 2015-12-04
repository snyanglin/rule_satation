package com.founder.drools.core.model;

import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;

import com.founder.drools.base.service.DroolsRequestService;
import com.founder.drools.core.request.HttpRequestBean;
import com.founder.framework.config.SpringCreator;
import com.founder.framework.organization.assign.service.OrgAssignPublic;
import com.founder.framework.organization.assign.service.OrgOrgAssignUserService;
import com.founder.framework.organization.assign.vo.OrgUserInfo;
import com.founder.framework.organization.department.bean.OrgOrganization;
import com.founder.framework.organization.department.service.OrgOrganizationService;
import com.founder.framework.organization.position.service.OrgPositionService;

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
	
	private DroolsRequestService droolsRequestService;
	
	private OrgOrganizationService orgOrganizationService;
	
	private OrgPositionService orgPositionService;
	
	private OrgOrgAssignUserService orgOrgAssignUserService;
	
	private OrgAssignPublic orgAssignPublic=new OrgAssignPublic();
	
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
	
	/**
	 * 
	 * @Title: getOrgName
	 * @Description: TODO(获取机构对象)
	 * @param @param orgCode
	 * @param @return    设定文件
	 * @return OrgOrganization    返回类型
	 * @throw
	 */
	public OrgOrganization getOrganization(String orgCode){		
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
	public String getOrgName(String orgCode){		
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
	public OrgOrganization getParentOrg(String orgCode){
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
	public String getParentOrgName(String orgCode){
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
	public List<OrgOrganization> getParentOrgList(String orgCode){
		
		return this.getOrgOrganizationService().queryUpOrgsByOrgcode(orgCode);
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
	public OrgOrganization getUserOrgByUserId(String userId){
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
	public List<OrgUserInfo> getSZ(String orgCode){
		String szOrgCode = this.getOrgOrganizationService().queryUpOrgByLevel(orgCode,"32").getOrgcode();//派出所
		List<OrgUserInfo> list = orgAssignPublic.queryUserByOrgAndPos(szOrgCode, "SZ");
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
	public List<OrgUserInfo> getSameLeader(String orgCode,String orgCode2){
		List<OrgOrganization> orgList = this.getParentOrgList(orgCode);
		List<OrgOrganization> orgList2 = this.getParentOrgList(orgCode2);
		
		OrgOrganization sameOrg = null;
		
		for(int i=0;i<orgList.size();i++){
			if(this.isContainsOrg(orgList2, orgList.get(i))){
				if(sameOrg == null)	sameOrg=orgList.get(i);
				else{
					if(Integer.valueOf(sameOrg.getOrglevel())<Integer.valueOf(orgList.get(i).getOrglevel())){//orglevel越大，级别越低
						sameOrg = orgList.get(i);//保存最小的级别
					}
				}
			}
		}
		
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
	 * @Title: isContainsOrg
	 * @Description: TODO(list是否包含机构)
	 * @param @param list 机构列表
	 * @param @param org 机构
	 * @param @return    设定文件
	 * @return boolean    返回类型
	 * @throw
	 */
	private boolean isContainsOrg(List<OrgOrganization> list,OrgOrganization org){
		for(int i=0;i<list.size();i++){
			if(list.get(i).getId().equals(org.getId())){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 
	 * @Title: getDroolsRequestService
	 * @Description: TODO(获取远程请求地址的服务)
	 * @param @return    设定文件
	 * @return DroolsRequestService    返回类型
	 * @throw
	 */
	private DroolsRequestService getDroolsRequestService(){
		if(this.droolsRequestService==null){
			this.droolsRequestService=(DroolsRequestService) SpringCreator.getBean("droolsRequestService");
		}
		return droolsRequestService;
	}
	
	
	private OrgOrganizationService getOrgOrganizationService(){
		if(this.orgOrganizationService == null){
			this.orgOrganizationService=(OrgOrganizationService) SpringCreator.getBean("orgOrganizationService");
		}
		
		return orgOrganizationService;
	}
	
	private OrgPositionService getOrgPositionService(){
		if(this.orgPositionService == null){
			this.orgPositionService=(OrgPositionService) SpringCreator.getBean("orgPositionService");
		}
		
		return orgPositionService;
	}
	
	private OrgOrgAssignUserService getOrgOrgAssignUserService(){
		if(this.orgOrgAssignUserService == null){
			this.orgOrgAssignUserService=(OrgOrgAssignUserService) SpringCreator.getBean("orgOrgAssignUserService");
		}
		
		return orgOrgAssignUserService;
	}
}
