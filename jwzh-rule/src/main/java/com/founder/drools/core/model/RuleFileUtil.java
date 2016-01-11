package com.founder.drools.core.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.founder.drools.base.model.Drools_group;
import com.founder.drools.base.model.Drools_rule;
import com.founder.drools.core.request.DroolsRequest;
import org.apache.commons.codec.binary.Base64;

public class RuleFileUtil {
	/**
	 * 
	 * @Title: readRuleFromDir
	 * @Description: TODO(从某个路径下读取规则)
	 * @param @param file 存放规则的最上层路劲
	 * @param @param groupMap    设定文件
	 * @return void    返回类型
	 * @throw
	 */
	public static void readImportListFromDir(File file,Map<String,Object> groupMap){
		try{
			if(file.isDirectory()){//目录
				File[] files = file.listFiles();
				for(int i=0;i<files.length;i++){
					readImportListFromDir(files[i],groupMap);
				}
			}else{//文件
				String fileName=file.getName();
				
				String fileType = "";//文件类型
				int atI = fileName.lastIndexOf(".");
				if (atI != -1) {
					fileType = fileName.substring(atI + 1);
					fileType = fileType.toLowerCase();
				}
				if(!"drl".equals(fileType) && !"xml".equals(fileType)){//只读取规则文件和配置文件
					return;
				}
				
				//获取分组名，文件夹名既是分组名，分组不可重复，所以存公共Map中
				String groupName=fileName.substring(0,atI);				
				//读取文件内容
				String fileContent=readContent(file);
				Object obj=DroolsRequest.XStream2Obj(fileContent);//Drools__group对象或者List
				groupMap.put(groupName, obj);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @Title: readRule
	 * @Description: TODO(解析读取到的文件内容)
	 * @param @param ruleFileName 规则文件名
	 * @param @param content 文件内容
	 * @param @return    设定文件
	 * @return List<Drools_rule>    返回类型
	 * @throw
	 */
	public List<Drools_rule> readRule(String ruleFileName,String content){
		Drools_rule rule_head=new Drools_rule();//规则头
		rule_head.setRuletype("0");
        rule_head.setRulefilename(ruleFileName);
        rule_head.setContent(content.substring(0,content.indexOf("rule")));
        
        int beginIndex=content.indexOf("rule");
        int endIndex=content.indexOf("end");
        String ruleName,ruleContent;
        List<Drools_rule> ruleList=new LinkedList<Drools_rule>();
        while(beginIndex>0){//循环解析规则
        	Drools_rule rule_entity=new Drools_rule();//规则体
        	rule_head.setRuletype("1");
        	rule_entity.setRulefilename(ruleFileName);
        	
        	ruleContent=content.substring(beginIndex+4,endIndex);
        	content=content.substring(endIndex+3);//删除已读取的内容
        	
        	int whenIndex=ruleContent.indexOf("when");
        	ruleName=ruleContent.substring(0,whenIndex);
        	ruleName=ruleName.substring(ruleName.indexOf('"'),ruleName.lastIndexOf('"'));
        	
        	rule_entity.setRulename(ruleName.trim());
        	rule_entity.setContent(ruleContent.substring(whenIndex+4).trim());
        	ruleList.add(rule_entity);
        }
        ruleList.add(0, rule_head);
        
        return ruleList;
	}
	
	/**
	 * 
	 * @Title: readRuleFromFile
	 * @Description: TODO(以UTF-8编码读取规则文件并解析)
	 * @param @param groupId
	 * @param @param ruleFileName
	 * @param @param filePath
	 * @param @return    设定文件
	 * @return List<Drools_rule>    返回类型
	 * @throw
	 */
	public static List<Drools_rule> readRuleFromFile(String groupId,String ruleFileName,String filePath){
		List<Drools_rule> list=new LinkedList<Drools_rule>();
		Drools_rule rule_head=new Drools_rule();//规则头
		rule_head.setRuletype("0");
		rule_head.setRulefilename(ruleFileName);
		rule_head.setGroupid(groupId);
        
        BufferedReader br = null;
        boolean readHead=true;
		try { 
			FileInputStream fis = new FileInputStream(filePath); 
	        InputStreamReader isr = new InputStreamReader(fis, "UTF-8"); 
			 br = new BufferedReader(isr);
			 StringBuffer content=new StringBuffer();//规则内容Buf
	         String line = ""; 
	         String ruleName;
	         Drools_rule entity=null;
	            
	         while ((line = br.readLine()) != null) {
	        	 line=new String(line.getBytes(),"UTF-8");
	        	 if(readHead){//读取规则头
	        		 if(line.contains("RULE HEAD END")){//规则头结束的注释
	        			 readHead=false;
	        			 rule_head.setContent(content.toString());
	        			 list.add(rule_head);
	        			 continue;
	        		 }
	        		 
	        		 if(line.contains("/*BZ:")){//备注
	        			 rule_head.setBz(line.substring(line.indexOf("/*BZ:")+5,line.indexOf("*/")));
	        			 continue;
	        		 }
	        		 
	        		 if(line.contains("/*") || line.length()==0){//注释和空行不要
		        		 continue;
		        	 }
	        		 content.append(line).append("\r\n");
	        	 }else{//读取规则
	        		 if(line.contains("RULE START")){//规则开始
	        			 entity=new Drools_rule();//规则体
	        	         entity.setRuletype("1");
	        	         entity.setRulefilename(ruleFileName);
	        	         entity.setGroupid(groupId);
	        	         content=new StringBuffer();
	        	         continue;
	        		 }
	        		 
	        		 if(line.startsWith("rule")){
	        			 ruleName=line.substring(line.indexOf('"')+1,line.lastIndexOf('"'));
	        			 entity.setRulename(ruleName);
	        			 continue;
	        		 }
	        		 
	        		 if(line.startsWith("end")){
	        			 entity.setContent(content.toString().trim());
	        			 list.add(entity);
	        			 continue;
	        		 }
	        		 
	        		 content.append(line).append("\r\n");
	        	 }
	         }
		 }catch (Exception e) {
	        	e.printStackTrace();
	     }finally{
	            if(br!=null){
	                try {
	                    br.close();
	                    br=null;
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	     }
        
      
        return list;
	}
	
	/**
	 * 
	 * @Title: readSysConfigFromFile
	 * @Description: TODO(读取配置的xml，转成list)
	 * @param @param filePath
	 * @param @return    设定文件
	 * @return List    返回类型
	 * @throw
	 */
	public static List readSysConfigFromFile(String filePath){
        BufferedReader br = null;
		try { 
			FileInputStream fis = new FileInputStream(filePath); 
	        InputStreamReader isr = new InputStreamReader(fis, "UTF-8"); 
			 br = new BufferedReader(isr);
			 StringBuffer content=new StringBuffer();//文件内容
	         String line = ""; 
	            
	         while ((line = br.readLine()) != null) {
	        	 line=new String(line.getBytes(),"UTF-8");
        		 content.append(line).append("\r\n");
	         }
	         
	         return (List) DroolsRequest.XStream2Obj(content.toString());
		 }catch (Exception e) {
	        	e.printStackTrace();
	     }finally{
	            if(br!=null){
	                try {
	                    br.close();
	                    br=null;
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	     }
		
		return null;
	}
	
	
	
	/**
	 * 
	 * @Title: readBZ
	 * @Description: TODO(读取备注)
	 * @param @param file
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throw
	 */
	private static String readBZ(File file){
		BufferedReader br = null;
		try { 
			 br = new BufferedReader(new FileReader(file));
	         String line = ""; 
	            
	         while ((line = br.readLine()) != null) {
	        	 line=new String(line.getBytes(),"UTF-8");
	        	 if(line.contains("/*BZ:")){
	        		 return line.substring(line.indexOf("/*BZ:")+5,line.indexOf("*/"));
	        	 }
	         }
		 }catch (Exception e) {
	        	e.printStackTrace();
	     }finally{
	            if(br!=null){
	                try {
	                    br.close();
	                    br=null;
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	     }
		return null;
	}
	
	/**
	 * 
	 * @Title: readContent
	 * @Description: TODO(读取文件内容)
	 * @param @param file
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throw
	 */
	public static String readContent(File file){
		try{
			FileInputStream fis = new FileInputStream(file); 
	        byte[] res=new byte[0];
	        byte[] res2;
	        byte[] bytes=new byte[1024];
	        int readLength=fis.read(bytes);	        
	        while(readLength>0){
	        	res2=new byte[res.length+readLength];
	        	System.arraycopy(res,0,res2,0,res.length);
	        	System.arraycopy(bytes,0,res2,res.length,readLength);
	        	res=res2;
	        	readLength=fis.read(bytes);	
	        }
	        return new String(res);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args) throws Exception {
//		Map<String,Object> groupMap=new HashMap<String,Object>();
//		RuleFileUtil.readImportListFromDir(new File("D:/work/drl/import"), groupMap);
//		
//		Object keyAry[] = groupMap.keySet().toArray();
//		List<List> ruleFileList;
//		for(int i=0;i<keyAry.length;i++){
//			String groupName=keyAry[i].toString();
//			Object drools_group = groupMap.get(groupName);
//			System.out.println("规则分组"+groupName);
//			
//			if(drools_group instanceof Drools_group){
//			
//				ruleFileList=((Drools_group)drools_group).getRuleFileList();
//				for(Drools_rule rule:(List<Drools_rule>)ruleFileList.get(0)){
//					System.out.println("	规则文件："+rule.getRulefilename());
//				}
//			}
//			
//			
//		}
		String str="重点人员查询服务器";
		byte[] baseStr = Base64.encodeBase64(str.getBytes("UTF-8"));
		byte[] bytes = Base64.decodeBase64("6YeN54K55Lq65ZGY5p+l6K+i5pyN5Yqh5Zmo".getBytes());
		System.out.println(str);
		System.out.println(new String(baseStr,"UTF-8"));
		System.out.println(new String(bytes,"UTF-8"));
	}
}
