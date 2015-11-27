package com.founder.drools.base.model;

import java.io.Serializable;
import java.util.Date;

import com.founder.framework.annotation.DBInfoAnnotation;
import com.founder.framework.annotation.FieldDesc;
import com.founder.framework.base.entity.BaseEntity;


@DBInfoAnnotation(tableName = "DROOLS_SERVICE", pk = "id")
public class Drools_service extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@FieldDesc("地址ID") private String urlid;
	@FieldDesc("服务ID") private String id;
	@FieldDesc("服务名称") private String servicename;
	@FieldDesc("备注") private String bz;
	@FieldDesc("创建时间") private Date createtime;
	@FieldDesc("更新时间") private Date updatetime;
	public String getUrlid() {
		return urlid;
	}
	public void setUrlid(String urlid) {
		this.urlid = urlid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getServicename() {
		return servicename;
	}
	public void setServicename(String servicename) {
		this.servicename = servicename;
	}
	
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
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
