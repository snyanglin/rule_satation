package com.founder.drools.base.dao;


import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.founder.drools.base.model.DroolsGroup;
import com.founder.drools.base.model.DroolsGroup;
import com.founder.framework.base.dao.BaseDaoImpl;
import com.founder.framework.utils.EasyUIPage;
import com.founder.framework.utils.StringUtils;


@Repository("droolsGroupDao")
public class DroolsGroupDao extends BaseDaoImpl {
	
	
	public void insert(DroolsGroup entity) {
		super.insert("DroolsGroup.save", entity);
	}

	
	public void update(DroolsGroup entity) {
		super.update("DroolsGroup.update", entity);
	}


	public EasyUIPage queryList(DroolsGroup entity, EasyUIPage page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("begin", page.getBegin());
		map.put("end", page.getEnd());
		String sort = page.getSort();
		String order = page.getOrder();
		if (StringUtils.isBlank(sort)) {
			sort = "id";
			order = "asc";
		}
		map.put("sort", sort);
		map.put("order", order);
		map.put("entity", entity);
		
		page.setRows(queryForList("DroolsGroup.query", map));
		page.setTotal((Integer) queryForObject("DroolsGroup.queryCount", map));
		return page;
	}

}
