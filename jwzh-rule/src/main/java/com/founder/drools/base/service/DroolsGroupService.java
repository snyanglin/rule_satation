package com.founder.drools.base.service;

import com.founder.drools.base.model.DroolsGroup;
import com.founder.framework.utils.EasyUIPage;

public interface DroolsGroupService {

	public void save(DroolsGroup entity);
	
	public void update(DroolsGroup entity);
	
	public EasyUIPage queryPageList(EasyUIPage page,DroolsGroup entity);
	
}
