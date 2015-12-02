package com.founder.drools.base.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.founder.drools.base.dao.Drools_ruleHisDao;
import com.founder.drools.base.model.Drools_ruleHis;

/**
 * ****************************************************************************
 * @Package:      [com.founder.drools.base.service.DroolsRuleHisService.java]  
 * @ClassName:    [DroolsRuleHisService]   
 * @Description:  [规则历史服务]   
 * @Author:       [zhang.hai@founder.com.cn]  
 * @CreateDate:   [2015年12月2日 下午5:20:08]   
 * @UpdateUser:   [ZhangHai(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2015年12月2日 下午5:20:08，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
@Service
public class DroolsRuleHisService{

	@Resource(name = "drools_ruleHisDao")
	private Drools_ruleHisDao drools_ruleHisDao;
	
	public void insert(Drools_ruleHis entity){		
		drools_ruleHisDao.insert(entity);
	}
	
	public Drools_ruleHis queryRuleHis(Drools_ruleHis entity){
		return drools_ruleHisDao.queryRuleHis(entity);
	}

	public List<Drools_ruleHis> queryRuleHisList(Drools_ruleHis entity) {
		return drools_ruleHisDao.queryRuleHisList(entity);
	}
	
	public List<Drools_ruleHis> queryRuleHisManagerList(Drools_ruleHis entity) {
		return drools_ruleHisDao.queryDroolsRuleHisManagerList(entity);
	}
	
	/**
	 * 
	 * @Title: queryRuleHisGroup
	 * @Description: TODO(查询分组列表)
	 * @param @return    设定文件
	 * @return List<Drools_ruleHis>    返回类型
	 * @throw
	 */
	public List<Drools_ruleHis> queryRuleHisGroup(){
		return drools_ruleHisDao.queryRuleHisGroup();
	}
}
