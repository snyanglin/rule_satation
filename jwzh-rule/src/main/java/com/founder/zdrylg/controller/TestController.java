package com.founder.zdrylg.controller;

import org.drools.KnowledgeBase;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.founder.common.DroolsUtils;
import com.founder.zdrylg.bean.ZdryZdryzb;

@Controller
public class TestController {

	@RequestMapping(value="/test.do",method=RequestMethod.GET)
	public void test(){
		try {
            // load up the knowledge base
            KnowledgeBase kbase = DroolsUtils.readKnowledgeBase("rule/test.drl");
            StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
            KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "test");
            //Fact
            ZdryZdryzb fact = new ZdryZdryzb();
            // go !
            ksession.insert(fact);
            ksession.fireAllRules();
            logger.close();
            
        } catch (Throwable t) {
            t.printStackTrace();
        }
	}
	
}
