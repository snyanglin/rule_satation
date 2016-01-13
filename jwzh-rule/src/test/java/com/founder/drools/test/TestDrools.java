package com.founder.drools.test;

import java.util.HashMap;
import java.util.Map;

import com.founder.drools.core.request.DroolsRequest;
import com.founder.drools.core.request.RuleBean;

public class TestDrools {

	public static void main(String[] args) {
		//调用新规则平台
		Map map=new HashMap();
		map.put("result", "lgSuccess");
		map.put("zdrylxName", "一般关注对象");
		map.put("zdryGllxdm", "04");
		map.put("fsrOrgName", "解放责任区2");
		map.put("fsrOrgCode", "210204410102");
		map.put("nrjy", "你好！");
		map.put("zdryName", "郭天麟");
		map.put("fsrUserCode", "210204195804210491");
		map.put("fsrName", "李长荣");
		map.put("zdryId", "891da755b6fb4f189e6824145e43da2f");		
		map.put("suggestion", "ninininininninni");
		//{"fsrOrgName":"解放责任区2","zdryId":"891da755b6fb4f189e6824145e43da2f","fsrOrgCode":"210204410102","fsrName":"李长荣","nrjy":"你好"}
		try {
			//定义了DoolsRequest的IP和端口
			DroolsRequest droolsRequest = new DroolsRequest("192.168.1.135","9080");	
			
			//下面的参数（   int maxCon,int minCon,int releaseTime,int releaseTimeOut  ）
			droolsRequest.initHttpClientPool(20, 10, 5000, 3000);
             //{"fsrOrgName":"解放责任区2","zdryName":"郭天麟","fsrOrgCode":"210204410102","fsrName":"李长荣"，"zdryGllxdm":"04"}
			RuleBean ruleBean=droolsRequest.requestDroolsServer("210200_ZDRY_MESSAGE", " MESSAGE_ZDRYGL_JGDXPSJDTX", map);
			
			System.out.println("返回状态"+ruleBean.getResStatus());
			System.out.println("返回的消息标题"+ruleBean.getRuleFileName());
			System.out.println("返回的内容"+ruleBean.getResponse());
			System.out.println("返回信息："+ruleBean.getResponse());//此错误保存在getGlobalError中				
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

	}

}

