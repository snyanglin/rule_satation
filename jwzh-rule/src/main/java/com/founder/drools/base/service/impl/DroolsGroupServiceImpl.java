package com.founder.drools.base.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.founder.drools.base.dao.DroolsGroupDao;
import com.founder.drools.base.model.DroolsGroup;
import com.founder.drools.base.service.DroolsGroupService;
import com.founder.framework.utils.EasyUIPage;

@Transactional
@Service("droolsGroupService")
public class DroolsGroupServiceImpl implements DroolsGroupService {

	@Resource(name="droolsGroupDao")
	private DroolsGroupDao droolsGroupDao;
	
	@Override
	public void save(DroolsGroup entity) {
		this.droolsGroupDao.insert(entity);
	}

	@Override
	public void update(DroolsGroup entity) {
		this.droolsGroupDao.update(entity);
	}

	@Transactional(readOnly=true)
	@Override
	public EasyUIPage queryPageList(EasyUIPage page, DroolsGroup entity) {
		return this.droolsGroupDao.queryList(entity, page);
	}

}
