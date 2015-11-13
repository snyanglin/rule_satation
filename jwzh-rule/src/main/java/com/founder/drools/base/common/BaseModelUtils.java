package com.founder.drools.base.common;

import java.util.Date;

import com.founder.drools.base.model.BaseModle;

public class BaseModelUtils {

	public static void setSaveProperty(BaseModle baseModel){
//		baseModel.setCreateTime(DateUtils.getSystemDateTimeString());
		baseModel.setCreateTime(new Date());
	}
	
	public static void setUpdateProperty(BaseModle baseModel){
//		baseModel.setUpdateTime(DateUtils.getSystemDateTimeString());
		baseModel.setUpdateTime(new Date());
	}
}
