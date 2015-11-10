package com.founder.drools.core.model;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.Resource;
import org.drools.io.ResourceFactory;
import org.drools.io.impl.UrlResource;

import com.founder.framework.utils.StringUtils;

public class DroolsUtils {
	
	private static KnowledgeBase buildKnowledgeBase( Resource resource,ResourceType resourceType){
		 KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
	        kbuilder.add(resource,resourceType);
	        KnowledgeBuilderErrors errors = kbuilder.getErrors();
	        if (errors.size() > 0) {
	        	System.err.println("============  drl error message,start  =============");
	            for (KnowledgeBuilderError error: errors) {
	                System.err.println(error);
	            }
	            System.err.println("============  drl error message,end  =============");
	            throw new IllegalArgumentException("Could not parse knowledge.");
	        }
	        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
	        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
	        return kbase;
	}

	public static KnowledgeBase buildKnowledgeBaseByResource(String drlPath) throws Exception {
		return DroolsUtils.buildKnowledgeBase(ResourceFactory.newClassPathResource(drlPath, "utf-8"), ResourceType.DRL);
    }
	
	public static KnowledgeBase buildKnowledgeBaseByFile(String filePath) throws Exception {
		return DroolsUtils.buildKnowledgeBase(ResourceFactory.newFileResource(filePath), ResourceType.DRL);
    }
	
	/**
	 * 
	 * @param drlPackageUrl drl可视化管理服务url (not blank)
	 * @param userName 服务的用户名(default:'admin')
	 * @param passwrd 服务的密码(default:'admin')
	 * @return org.drools.KnowledgeBase
	 */
	public static KnowledgeBase buildKnowledgeBaseByUrl(String drlPackageUrl,String userName,String passwrd){
		UrlResource urlResource = (UrlResource) ResourceFactory.newUrlResource(drlPackageUrl);
		urlResource.setBasicAuthentication("enabled");
		if(StringUtils.isBlank(userName)){
			urlResource.setUsername("admin");
		}else{
			urlResource.setUsername(userName);
		}
		if(StringUtils.isBlank(passwrd)){
			urlResource.setPassword("admin");
		}else{
			urlResource.setPassword(passwrd);
		}
		return DroolsUtils.buildKnowledgeBase(urlResource, ResourceType.PKG);
	}
	
}
