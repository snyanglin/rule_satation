package com.founder.drools.test;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.founder.framework.config.SpringCreator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class TestCase extends AbstractJUnit4SpringContextTests {
	
	@Before
	public void setUp() throws Exception {
		SpringCreator.setApplicationContext(this.applicationContext);		
	}

	@After
	public void tearDown() throws Exception {
	}

}
