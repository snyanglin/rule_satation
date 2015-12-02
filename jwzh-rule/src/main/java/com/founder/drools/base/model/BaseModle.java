package com.founder.drools.base.model;

import java.util.Date;

import com.founder.framework.annotation.FieldDesc;

public class BaseModle {

	@FieldDesc("创建时间") private Date createtime;
	@FieldDesc("更新时间") private Date updatetime;
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	
}
