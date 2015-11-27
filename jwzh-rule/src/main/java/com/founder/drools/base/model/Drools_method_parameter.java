package com.founder.drools.base.model;

import java.io.Serializable;
import java.util.Date;

import com.founder.framework.annotation.DBInfoAnnotation;
import com.founder.framework.annotation.FieldDesc;
import com.founder.framework.base.entity.BaseEntity;


@DBInfoAnnotation(tableName = "DROOLS_METHOD_PARAMETER")
public class Drools_method_parameter extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@FieldDesc("参数ID") private String id;
	@FieldDesc("方法ID") private String methodid;
	@FieldDesc("参数名") private String paramname;
	@FieldDesc("参数类型") private String paramclass;	
	@FieldDesc("备注") private String bz;
	@FieldDesc("创建时间") private Date createtime;
	@FieldDesc("更新时间") private Date updatetime;
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMethodid() {
		return methodid;
	}
	public void setMethodid(String methodid) {
		this.methodid = methodid;
	}
	public String getParamname() {
		return paramname;
	}
	public void setParamname(String paramname) {
		this.paramname = paramname;
	}
	public String getParamclass() {
		return paramclass;
	}
	public void setParamclass(String paramclass) {
		this.paramclass = paramclass;
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
