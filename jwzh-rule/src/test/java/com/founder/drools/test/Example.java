package com.founder.drools.test;

import org.drools.runtime.StatefulKnowledgeSession;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import com.founder.drools.core.model.DroolsUtils;
import com.founder.drools.test.model.Clock;
import com.founder.drools.test.springtest.SpringTransactionalTestCase;

@ContextConfiguration(locations = { "/applicationContext-test.xml" })
public class Example extends SpringTransactionalTestCase{



	@Before
	public void setUp() throws Exception {
	}


	@Test
	public void test() {
		Clock c = new Clock();
		StatefulKnowledgeSession ksession;
		try {
			ksession = DroolsUtils.buildKnowledgeBaseByFile("src\\test\\resources\\test.drl").newStatefulKnowledgeSession();
			//循环设置参数
			ksession.insert(c);
			//触发规则引擎
			ksession.fireAllRules();
			ksession.dispose();  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
