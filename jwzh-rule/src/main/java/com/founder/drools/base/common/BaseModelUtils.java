package com.founder.drools.base.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.founder.drools.base.model.BaseModle;

/**
 * ****************************************************************************
 * @Package:      [com.founder.drools.base.common.BaseModelUtils.java]  
 * @ClassName:    [BaseModelUtils]   
 * @Description:  [数据库对象父类]   
 * @Author:       [zhang.hai@founder.com.cn]  
 * @CreateDate:   [2015年12月2日 下午5:21:31]   
 * @UpdateUser:   [ZhangHai(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2015年12月2日 下午5:21:31，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
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
