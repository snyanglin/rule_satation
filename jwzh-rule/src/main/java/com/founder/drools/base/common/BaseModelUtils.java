package com.founder.drools.base.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.founder.drools.base.model.BaseModle;

public class BaseModelUtils {

	public static void setSaveProperty(BaseModle baseModel){
//		baseModel.setCreateTime(DateUtils.getSystemDateTimeString());
		baseModel.setCreatetime(new Date());
	}
	
	public static void setUpdateProperty(BaseModle baseModel){
//		baseModel.setUpdateTime(DateUtils.getSystemDateTimeString());
		baseModel.setUpdatetime(new Date());
	}
	
	/**
	 * 
	 * @Title: getTimeString
	 * @Description: TODO(获取yyyyMMddHHmmss字符串)
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throw
	 */
	public static String getTimeString(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date());
	}
}
