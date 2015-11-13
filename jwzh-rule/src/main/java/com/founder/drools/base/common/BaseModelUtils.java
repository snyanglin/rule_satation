package com.founder.drools.base.common;

import com.founder.drools.base.model.BaseModle;
import com.founder.framework.utils.DateUtils;

public class BaseModelUtils {

	public static void setSaveProperty(BaseModle baseModel){
		baseModel.setCreateTime(DateUtils.getSystemDateTimeString());
	}
	
	public static void setUpdateProperty(BaseModle baseModel){
		baseModel.setUpdateTime(DateUtils.getSystemDateTimeString());
	}
}
