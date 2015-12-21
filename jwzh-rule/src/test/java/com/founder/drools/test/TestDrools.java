package com.founder.drools.test;

import java.util.HashMap;
import java.util.Map;

import com.founder.drools.core.request.DroolsRequest;
import com.founder.drools.core.request.RuleBean;

public class TestDrools {

	public static void main(String[] args) {
		//调用新规则平台
		Map map=new HashMap();
		map.put("result", "lgFail");
		map.put("zdrylxName", "非正常上访重点人员");
		map.put("zdryGllxdm", "06");
		map.put("fsrOrgName", "治安管理支队派出所工作大队");
		map.put("zdryName", "王紫祺");
		map.put("fsrUserCode", "210203196110304790");
		map.put("fsrName", "周德深");
		map.put("zdryId", "b5922e9b63df4015ba6bdad59a53dd38");		
		
		try {
			DroolsRequest droolsRequest = new DroolsRequest("192.168.1.135","9080");	
			droolsRequest.initHttpClientPool(20, 10, 5000, 3000);

			RuleBean ruleBean=droolsRequest.requestDroolsServer("210200_ZDRY_MESSAGE", "MESSAGE_ZDRYGL_LGSPJG", map);
			System.out.println("返回信息："+ruleBean.getResponse());//此错误保存在getGlobalError中				
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

	}

}

