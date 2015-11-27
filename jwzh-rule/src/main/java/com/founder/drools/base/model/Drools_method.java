package com.founder.drools.base.model;

import java.io.Serializable;
import java.util.Date;

import com.founder.framework.annotation.DBInfoAnnotation;
import com.founder.framework.annotation.FieldDesc;
import com.founder.framework.base.entity.BaseEntity;


@DBInfoAnnotation(tableName = "DROOLS_METHOD", pk = "id")
public class Drools_method extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@FieldDesc("方法ID") private String id;
	@FieldDesc("服务ID") private String serviceid;
	@FieldDesc("方法名") private String methodname;
	@FieldDesc("备注") private String bz;
	@FieldDesc("创建时间") private Date createtime;
	@FieldDesc("更新时间") private Date updatetime;
	@FieldDesc("方法名") private String methodresponse;
	
	@FieldDesc("服务名称") private String servicename;
	@FieldDesc("地址id") private String urlid;
	@FieldDesc("地址名称") private String urlname;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getServiceid() {
		return serviceid;
	}
	public void setServiceid(String serviceid) {
		this.serviceid = serviceid;
	}
	public String getMethodname() {
		return methodname;
	}
	public void setMethodname(String methodname) {
		this.methodname = methodname;
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
	public String getUrlid() {
		return urlid;
	}
	public void setUrlid(String urlid) {
		this.urlid = urlid;
	}
	public String getServicename() {
		return servicename;
	}
	public void setServicename(String servicename) {
		this.servicename = servicename;
	}
	public String getUrlname() {
		return urlname;
	}
	public void setUrlname(String urlname) {
		this.urlname = urlname;
	}
	public String getMethodresponse() {
		return methodresponse;
	}
	public void setMethodresponse(String methodresponse) {
		this.methodresponse = methodresponse;
	}

	
}
