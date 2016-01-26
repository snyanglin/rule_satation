package com.founder.drools.test.springtest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.sql.DataSource;
/**
 * ****************************************************************************
 * @Package:      [com.founder.demo.springtest.SpringTransactionalTestCase.java]  
 * @ClassName:    [SpringTransactionalTestCase]   
 * @Description:  [ Spring的支持数据库访问, 事务控制和依赖注入的JUnit4 集成测试基类.
 * 					相比Spring原基类名字更短并保存了dataSource变量,子类需要定义applicationContext文件的位置
 * 					@ContextConfiguration(locations = { "/applicationContext-test.xml" })]   
 * @Author:       [wei.wen@founder.com.cn]  
 * @CreateDate:   [2015年7月26日 上午9:45:57]   
 * @UpdateUser:   [lenovo(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2015年7月26日 上午9:45:57，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
@ActiveProfiles("test")
public abstract class SpringTransactionalTestCase extends AbstractTransactionalJUnit4SpringContextTests {

    protected DataSource dataSource;

    @Override
    @Autowired
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
        this.dataSource = dataSource;
    }
}
