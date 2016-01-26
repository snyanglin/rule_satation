package com.founder.drools.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.founder.drools.core.model.DroolsTools;
import com.founder.framework.organization.assign.vo.OrgUserInfo;
import com.founder.framework.organization.department.bean.OrgOrganization;
import com.founder.framework.organization.user.bean.OrgUser;


public class DroolsToolsTest extends TestCase{	
	@Before
	public void setUp() throws Exception {
	}

	//@Test
//	public void testRequestByGet() {
//		droolsTools.requestByGet("", null);
//		//fail("Not yet implemented");
//	}

//	@Test
//	public void testRequestByPost() {
		
//	}

	@Test
	public void testGetOrganization() {
		System.out.println("====testGetOrganization======start====");
		OrgOrganization org = DroolsTools.getOrganization("210204410102");				
		System.out.println("组织机构名称："+org.getOrgname());
		//assertEquals("组织机构获取失败",org==null);
		System.out.println("====testGetOrganization======end====");
	}

	@Test
	public void testGetParentOrg() {
		System.out.println("====testGetParentOrg======start====");
		OrgOrganization org = DroolsTools.getParentOrg("210204410102");				
		System.out.println("组织机构名称："+org.getOrgname());
		//assertEquals("组织机构获取失败",org==null);
		System.out.println("====testGetParentOrg======end====");
	}

	@Test
	public void testGetParentOrgList() {
		System.out.println("====testGetParentOrgList======start====");
		List<OrgOrganization> orgList = DroolsTools.getParentOrgList("210204410102");
		for(int i=0;i<orgList.size();i++){
			System.out.println("组织机构级别："+orgList.get(i).getOrglevel()+"  组织机构名称："+orgList.get(i).getOrgname()+"  组织机构代码："+orgList.get(i).getOrgcode());
		}
		//assertEquals("组织机构获取失败",org==null);
		System.out.println("====testGetParentOrgList======end====");
	}

	@Test
	public void testGetUserOrgByUserId() {
		System.out.println("====testGetUserOrgByUserId======start====");
		OrgOrganization org = DroolsTools.getUserOrgByUserId("210203194703112293");		
		System.out.println("组织机构级别："+org.getOrglevel()+"  组织机构名称："+org.getOrgname()+"组织机构代码："+org.getOrgcode());		
		//assertEquals("组织机构获取失败",org==null);
		System.out.println("====testGetUserOrgByUserId======end====");
	}

	@Test
	public void testGetSZ() {
		System.out.println("====testGetSZ======start====");
		List<OrgUserInfo> userList = DroolsTools.getSZ("210204410102");	
		for(int i=0;i<userList.size();i++){
			System.out.println("用户姓名："+userList.get(i).getUsername()+"  组织机构名称："+userList.get(i).getOrgname());
		}
		//assertEquals("组织机构获取失败",org==null);
		System.out.println("====testGetSZ======end====");
	}

	@Test
	public void testGetSameLeader() {
		System.out.println("====testGetSameLeader======start====");
		
		//组织机构级别：50  组织机构名称：解放责任区2  组织机构代码：210204410102
		//组织机构级别：32  组织机构名称：白山路派出所  组织机构代码：210204410000
		//组织机构级别：21  组织机构名称：沙河口分局  组织机构代码：210204000000
		//组织机构级别：10  组织机构名称：大连市公安局  组织机构代码：210200000000
		List<OrgUserInfo> userList = DroolsTools.getSameLeader("210204410102","210204000000");	
		for(int i=0;i<userList.size();i++){
			System.out.println("用户姓名："+userList.get(i).getUsername()+"  组织机构名称："+userList.get(i).getOrgname());
		}		
		//assertEquals("组织机构获取失败",org==null);
		System.out.println("====testGetSameLeader======end====");
	}
	
	@Test
	public void testGetOrgUserByUserId() {
		System.out.println("====testGetOrgUserByUserId======start====");
		
		//组织机构级别：50  组织机构名称：解放责任区2  组织机构代码：210204410102
		//组织机构级别：32  组织机构名称：白山路派出所  组织机构代码：210204410000
		//组织机构级别：21  组织机构名称：沙河口分局  组织机构代码：210204000000
		//组织机构级别：10  组织机构名称：大连市公安局  组织机构代码：210200000000
		OrgUser user=DroolsTools.getOrgUserByUserId("210203194703112293");

		System.out.println("用户姓名："+user.getUsername());
		System.out.println("====testGetOrgUserByUserId======end====");
	}

}
