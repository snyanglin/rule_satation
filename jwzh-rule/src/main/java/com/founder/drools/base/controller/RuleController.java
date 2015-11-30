package com.founder.drools.base.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.founder.drools.base.model.Drools_group;
import com.founder.drools.base.model.Drools_rule;
import com.founder.drools.base.model.Drools_ruleHis;
import com.founder.drools.base.service.DroolsGroupService;
import com.founder.drools.base.service.DroolsRuleHisService;
import com.founder.drools.base.service.DroolsRuleService;
import com.founder.drools.core.inteface.RuleService;
import com.founder.drools.core.model.Paginator;
import com.founder.drools.core.request.RuleBean;
import com.founder.framework.base.controller.BaseController;
/**
 * ****************************************************************************
 * @Package:      [com.founder.zdrygl.base.controller.RuleTestController.java]  
 * @ClassName:    [RuleTestController]   
 * @Description:  [规则引擎测试控制器]   
 * @Author:       [zhang.hai@founder.com.cn]  
 * @CreateDate:   [2015年10月14日 下午2:34:49]   
 * @UpdateUser:   [ZhangHai(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2015年10月14日 下午2:34:49，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
@Controller
@RequestMapping("ruleManager")
public class RuleController extends BaseController {					
	
	@Autowired
	private RuleService ruleService;
	
	@Autowired
	private DroolsRuleService droolsRuleService;
	
	@Autowired
	private DroolsRuleHisService droolsRuleHisService;
	
	@Resource(name="droolsGroupService")
	private DroolsGroupService droolsGroupService;
	
	@RequestMapping(value = "/ruleManager", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView ruleManager(Drools_rule entity){
		ModelAndView mv = new ModelAndView("drools/edit/ruleManager");	
		List<Drools_group> list=droolsGroupService.queryListByEntity(null);
		mv.addObject("Entity",entity);
		mv.addObject("GroupList",list);
		return mv;
	}
	
	@RequestMapping(value = "/getRuleManagerList", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView getRuleManagerList(Drools_rule entity,Paginator paginator){
		ModelAndView mv = new ModelAndView("drools/edit/ruleManagerList");	
		entity.setRulefilename(entity.getRulefilename().trim());
		List<Drools_rule> list = droolsRuleService.queryRuleManagerList(entity);
		paginator.setList(list);
		mv.addObject("Paginator",paginator);	
		mv.addObject("Entity",entity);
		
		return mv;
	}
	
	@RequestMapping(value = "/ruleEditPre", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView ruleEditPre(String rulefilename){
		ModelAndView mv = new ModelAndView("drools/edit/ruleEdit");	
		Drools_rule entity= new Drools_rule();
		entity.setRulefilename(rulefilename);//规则文件名
		entity.setRuletype("0");//规则头
		Drools_rule ruleHead = droolsRuleService.queryRuleByEntity(entity);
		
		entity.setRuletype("1");//规则体		
		List<Drools_rule> list = droolsRuleService.queryRuleListByEntity(entity);
		
		mv.addObject("ruleHead",ruleHead);
		mv.addObject("List",list);
		return mv;
	}	
	
	@RequestMapping(value = "/ruleEdit", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Map ruleEdit(Drools_rule entity){
		Map map = new HashMap();
		map.put("resStatus", "0");//成功
		try{
			if("add".equals(entity.getId())){//新增规则体
				
				//验证
				if(entity.getRulename()==null || entity.getRulename().trim().length()==0){
					map.put("resStatus", "1");//失败
					map.put("errorMsg", "【规则名称】不能为空");//失败
					return map;
				}
				
				
				Drools_rule query_Entity = new Drools_rule();
				query_Entity.setRulefilename(entity.getRulefilename());
				query_Entity.setRulename(entity.getRulename());
				List list=droolsRuleService.queryRuleListByEntity(query_Entity);
				if(list==null || list.size()>0){
					map.put("resStatus", "1");//失败
					map.put("errorMsg", entity.getRulename()+"已存在");//失败
					return map;
				}
				
				entity.setRuletype("1");//规则体，规则头在新增规则的时候就有了
				droolsRuleService.addRule(entity);
			}else{
				entity.setStatus("1");
				droolsRuleService.updateRule(entity);	
			}
		}catch(Exception e){
			e.printStackTrace();
			map.put("resStatus", "1");//失败
			map.put("errorMsg", e.toString());//失败
		}
		return map;
	}
	
	@RequestMapping(value = "/ruleDelete", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView ruleDelete(Drools_rule entity){		
		droolsRuleService.deleteRule(entity.getId());
		return this.ruleEditPre(entity.getRulefilename());		
	}
	
	@RequestMapping(value = "/ruleRelease", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Map ruleRelease(String rulefilename){	
		Map map = new HashMap();
		map.put("resStatus", "0");//成功
		String res=droolsRuleService.ruleRelease(rulefilename);
		if(res!=null){
			map.put("resStatus", "1");//失败
			map.put("errorMsg", res);//失败
		}
		return map;		
	}
	
	@RequestMapping(value = "/validateRule", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody RuleBean validateRule(String ruleFileName,String ruleName,String paramStr){
		RuleBean ruleBean = new RuleBean();
        ruleBean.setRuleFileName(ruleFileName);
        ruleBean.setRuleName(ruleName);
        
        
        try{
        	droolsRuleService.ruleTestRelease(ruleFileName,ruleName);
        	ruleService.validateRule(ruleBean);
        }catch(Exception e){
        	e.printStackTrace();
        	ruleBean.setResponse(e.toString());  
        	return ruleBean;
        }		       
        
        if(0==ruleBean.getResStatus()){//验证成功
        	Drools_rule entity=new Drools_rule();
        	entity.setRulefilename(ruleFileName);
        	entity.setRulename(ruleName);
        	entity.setRuletype("1");
        	entity = droolsRuleService.queryRuleByEntity(entity);
        	
        	Drools_rule entity2=new Drools_rule();
        	entity2.setStatus("3");//已验证，未发布
        	entity2.setId(entity.getId());
        	droolsRuleService.updateRule(entity2);        	        	
        }
		return ruleBean;
	}
	
	@RequestMapping(value = "/testRule", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody RuleBean testRule(String ruleFileName,String ruleName,String paramStr){
		RuleBean ruleBean = new RuleBean();
        ruleBean.setRuleFileName(ruleFileName);
        ruleBean.setRuleName(ruleName);
        
        
        try{
        	droolsRuleService.ruleTestRelease(ruleFileName,ruleName);
        	ruleService.testRule(ruleBean, paramStr);
        }catch(Exception e){
        	e.printStackTrace();
        	ruleBean.setResponse(e.toString());  
        	return ruleBean;
        }		       
        
		return ruleBean;
	}
	
	
	
	@RequestMapping(value = "/ruleListQuery", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView ruleListQuery(Drools_rule entity){
		ModelAndView mv = new ModelAndView("drools/query/ruleListQuery");	
		List<Drools_group> list=droolsGroupService.queryListByEntity(null);
		mv.addObject("Entity",entity);;
		mv.addObject("GroupList",list);
		return mv;
	}
	
	@RequestMapping(value = "/getRuleList", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView getRuleList(Drools_rule entity,Paginator paginator){
		ModelAndView mv = new ModelAndView("drools/query/ruleList");	
		entity.setRulefilename(entity.getRulefilename().trim());
		List<Drools_rule> list = droolsRuleService.queryRuleManagerList(entity);
		paginator.setList(list);
		mv.addObject("Paginator",paginator);
		
		return mv;
	}
	
	@RequestMapping(value = "/ruleQuery", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView ruleQuery(String rulefilename){
		rulefilename=rulefilename.trim();
		
		ModelAndView mv = new ModelAndView("drools/query/ruleQuery");	
		Drools_rule entity= new Drools_rule();
		entity.setRulefilename(rulefilename);//规则文件名
		entity.setRuletype("0");//规则头
		Drools_rule ruleHead = droolsRuleService.queryRuleByEntity(entity);
		
		entity.setRuletype("1");//规则体		
		List<Drools_rule> list = droolsRuleService.queryRuleListByEntity(entity);
		
		//合并成一个规则显示
		String content=ruleHead.getContent();
		for(int i=0;i<list.size();i++){
			content+="\r\n\r\nrule \""+list.get(i).getRulename()+"\"\r\n"+list.get(i).getContent()+"\r\nend";
		}
		ruleHead.setContent(content);
		mv.addObject("ruleObj",ruleHead);		
		return mv;
	}
	
	@RequestMapping(value = "/ruleAddPre", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView ruleAddPre(){
		ModelAndView mv = new ModelAndView("drools/edit/ruleAdd");	
		List<Drools_group> list=droolsGroupService.queryListByEntity(null);		
		mv.addObject("GroupList",list);
		return mv;
	}
	
	@RequestMapping(value = "/ruleAdd", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Map ruleAdd(Drools_rule entity){
		Map map = new HashMap();
		map.put("resStatus", "0");//成功
		
		//验证
		if(entity.getRulefilename()==null || entity.getRulefilename().trim().length()==0){
			map.put("resStatus", "1");//失败
			map.put("errorMsg", "【规则文件名称】不能为空");//失败
			return map;
		}
		
		try{
			entity.setRuletype("0");//规则头	
			Drools_rule query_Entity = new Drools_rule();
			query_Entity.setRulefilename(entity.getRulefilename());
			List list=droolsRuleService.queryRuleListByEntity(query_Entity);
			if(list==null || list.size()>0){
				map.put("resStatus", "1");//失败
				map.put("errorMsg", entity.getRulefilename()+"已存在");//失败
				return map;
			}
			droolsRuleService.addRule(entity);
		}catch(Exception e){
			map.put("resStatus", "1");//失败
			map.put("errorMsg", e.toString());//失败
		}
		
		return map;
	}
	
	/**
	 * 
	 * @Title: ruleFile
	 * @Description: TODO(归档)
	 * @param @param ruleFileName
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throw
	 */
	@RequestMapping(value = "/ruleFile", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView ruleFile(String rulefilename){		
		droolsRuleService.ruleFile(rulefilename);
		return this.ruleManager(null);	
	}
	
	@RequestMapping(value = "/ruleHisManager", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView ruleHisManager(Drools_ruleHis entity){
		ModelAndView mv = new ModelAndView("drools/ruleHis/ruleHisManager");		
		List list = droolsRuleHisService.queryRuleHisGroup();
		mv.addObject("Entity",entity);
		mv.addObject("GroupList",list);
		return mv;
	}
	@RequestMapping(value = "/getRuleHisManagerList", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView getRuleHisManagerList(Drools_ruleHis entity,Paginator paginator){
		ModelAndView mv = new ModelAndView("drools/ruleHis/ruleHisManagerList");
		entity.setRulefilename(entity.getRulefilename().trim());
		List<Drools_ruleHis> list = droolsRuleHisService.queryRuleHisManagerList(entity);
		paginator.setList(list);
		mv.addObject("Paginator",paginator);
		return mv;
	}
	
	@RequestMapping(value = "/ruleHisListQuery", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView ruleHisListQuery(Drools_ruleHis entity){
		ModelAndView mv = new ModelAndView("drools/ruleHis/ruleHisListQuery");	
		
		mv.addObject("Entity",entity);
		
		return mv;
	}
	@RequestMapping(value = "/getRuleHisList", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView getRuleHisList(Drools_ruleHis entity,Paginator paginator){
		ModelAndView mv = new ModelAndView("drools/ruleHis/ruleHisList");	
		
		List<Drools_ruleHis> list = droolsRuleHisService.queryRuleHisList(entity);
		paginator.setList(list);
		mv.addObject("Paginator",paginator);	
		mv.addObject("Entity",entity);
		return mv;
	}
	
	@RequestMapping(value = "/ruleHisQuery", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView ruleHisQuery(Drools_ruleHis entity){
		ModelAndView mv = new ModelAndView("drools/ruleHis/ruleHisQuery");	
		
		entity = droolsRuleHisService.queryRuleHis(entity);
		mv.addObject("ruleHis",entity);
		
		return mv;
	}
}
