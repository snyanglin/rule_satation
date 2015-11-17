package com.founder.drools.base.model;

import java.util.Date;

public class BaseModle {

	private Date createtime;
	
	private Date updatetime;

	public Date getCreateTime() {
		return createtime;
	}

	public void setCreateTime(Date createTime) {
		this.createtime = createTime;
	}

	public Date getUpdateTime() {
		return updatetime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updatetime = updateTime;
	}
	
}
