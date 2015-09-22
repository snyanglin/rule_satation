package com.founder.zdrylg.bean;

import java.io.Serializable;

import com.founder.framework.base.entity.BaseEntity;

/***
 * ****************************************************************************
 * 
 * @Package: [com.founder.zdrygl.bean.Zdyzb.java]
 * @ClassName: [Zdyzb]
 * @Description: [一句话描述该类的功能]
 * @Author: [wu_chunhui@founder.com.cn]
 * @CreateDate: [2015-3-11 下午5:39:52]
 * @UpdateUser: [Administrator(如多次修改保留历史记录，增加修改记录)]
 * @UpdateDate: [2015-3-11 下午5:39:52，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]
 * @Version: [v1.0]
 */

public class ZdryZdryzb extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = -5971506890685259930L;
	//("重点人员ID") 
	private String id;
	//("人员ID") 
	private String ryid;
	//("重点人员类别") 
	private String zdrylb;
	//("实有人口总表ID") 
	private String syrkid;
	//("重点人员管理类型代码") 
	private String zdrygllxdm;
	//("管理状态:1列管申请中,2已列管,3撤管申请中,4已撤管,5转递申请中,6涉公安访下发中,7转类申请中") 
	private String glzt;
	//("主要问题及现实表现") 
	private String zywtjxsbx;
	//("指定列管部门ID") 
	private String zdlgbmid;
	//("指定列管部门名称") 
	private String zdlgbmmc;
	//("是否加密") 
	private String sfjm;
	//("是否在控代码") 
	private String sfzkdm;
	//("管理部门") 
	private String glbm;
	//("管辖部门") 
	private String gxbm;
	//("查看权限") 
	private String cxbm;
	//("采集时间") 
	private String xt_cjsj;
	//("录入时间") 
	private String xt_lrsj;
	//("录入人姓名") 
	private String xt_lrrxm;
	//("录入人ID") 
	private String xt_lrrid;
	//("录入人部门") 
	private String xt_lrrbm;
	//("录入人部门ID") 
	private String xt_lrrbmid;
	//("录入IP") 
	private String xt_lrip;
	//("最后修改时间") 
	private String xt_zhxgsj;
	//("最后修改人姓名") 
	private String xt_zhxgrxm;
	//("最后修改人ID") 
	private String xt_zhxgrid;
	//("最后修改人部门") 
	private String xt_zhxgrbm;
	//("最后修改人部门ID") 
	private String xt_zhxgrbmid;
	//("最后修改IP") 
	private String xt_zhxgip;
	//("注销标志") 
	private String xt_zxbz;
	//("注销原因") 
	private String xt_zxyy;
	//("籍贯省市县（区）代码") 
	private String jgssxdm;
	//("户籍地所属县级公安机关代码") 
	private String hjd_ssxjgajgdm;
	//("户籍地所属派出所代码") 
	private String hjd_sspcsdm;
	//("户籍地所属社区") 
	private String hjd_sssq;
	//("居住地所在地市代码") 
	private String jzd_szdsdm;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRyid() {
		return ryid;
	}
	public void setRyid(String ryid) {
		this.ryid = ryid;
	}
	public String getZdrylb() {
		return zdrylb;
	}
	public void setZdrylb(String zdrylb) {
		this.zdrylb = zdrylb;
	}
	public String getSyrkid() {
		return syrkid;
	}
	public void setSyrkid(String syrkid) {
		this.syrkid = syrkid;
	}
	public String getZdrygllxdm() {
		return zdrygllxdm;
	}
	public void setZdrygllxdm(String zdrygllxdm) {
		this.zdrygllxdm = zdrygllxdm;
	}
	public String getGlzt() {
		return glzt;
	}
	public void setGlzt(String glzt) {
		this.glzt = glzt;
	}
	public String getZywtjxsbx() {
		return zywtjxsbx;
	}
	public void setZywtjxsbx(String zywtjxsbx) {
		this.zywtjxsbx = zywtjxsbx;
	}
	public String getZdlgbmid() {
		return zdlgbmid;
	}
	public void setZdlgbmid(String zdlgbmid) {
		this.zdlgbmid = zdlgbmid;
	}
	public String getZdlgbmmc() {
		return zdlgbmmc;
	}
	public void setZdlgbmmc(String zdlgbmmc) {
		this.zdlgbmmc = zdlgbmmc;
	}
	public String getSfjm() {
		return sfjm;
	}
	public void setSfjm(String sfjm) {
		this.sfjm = sfjm;
	}
	public String getSfzkdm() {
		return sfzkdm;
	}
	public void setSfzkdm(String sfzkdm) {
		this.sfzkdm = sfzkdm;
	}
	public String getGlbm() {
		return glbm;
	}
	public void setGlbm(String glbm) {
		this.glbm = glbm;
	}
	public String getGxbm() {
		return gxbm;
	}
	public void setGxbm(String gxbm) {
		this.gxbm = gxbm;
	}
	public String getCxbm() {
		return cxbm;
	}
	public void setCxbm(String cxbm) {
		this.cxbm = cxbm;
	}
	public String getXt_cjsj() {
		return xt_cjsj;
	}
	public void setXt_cjsj(String xt_cjsj) {
		this.xt_cjsj = xt_cjsj;
	}
	public String getXt_lrsj() {
		return xt_lrsj;
	}
	public void setXt_lrsj(String xt_lrsj) {
		this.xt_lrsj = xt_lrsj;
	}
	public String getXt_lrrxm() {
		return xt_lrrxm;
	}
	public void setXt_lrrxm(String xt_lrrxm) {
		this.xt_lrrxm = xt_lrrxm;
	}
	public String getXt_lrrid() {
		return xt_lrrid;
	}
	public void setXt_lrrid(String xt_lrrid) {
		this.xt_lrrid = xt_lrrid;
	}
	public String getXt_lrrbm() {
		return xt_lrrbm;
	}
	public void setXt_lrrbm(String xt_lrrbm) {
		this.xt_lrrbm = xt_lrrbm;
	}
	public String getXt_lrrbmid() {
		return xt_lrrbmid;
	}
	public void setXt_lrrbmid(String xt_lrrbmid) {
		this.xt_lrrbmid = xt_lrrbmid;
	}
	public String getXt_lrip() {
		return xt_lrip;
	}
	public void setXt_lrip(String xt_lrip) {
		this.xt_lrip = xt_lrip;
	}
	public String getXt_zhxgsj() {
		return xt_zhxgsj;
	}
	public void setXt_zhxgsj(String xt_zhxgsj) {
		this.xt_zhxgsj = xt_zhxgsj;
	}
	public String getXt_zhxgrxm() {
		return xt_zhxgrxm;
	}
	public void setXt_zhxgrxm(String xt_zhxgrxm) {
		this.xt_zhxgrxm = xt_zhxgrxm;
	}
	public String getXt_zhxgrid() {
		return xt_zhxgrid;
	}
	public void setXt_zhxgrid(String xt_zhxgrid) {
		this.xt_zhxgrid = xt_zhxgrid;
	}
	public String getXt_zhxgrbm() {
		return xt_zhxgrbm;
	}
	public void setXt_zhxgrbm(String xt_zhxgrbm) {
		this.xt_zhxgrbm = xt_zhxgrbm;
	}
	public String getXt_zhxgrbmid() {
		return xt_zhxgrbmid;
	}
	public void setXt_zhxgrbmid(String xt_zhxgrbmid) {
		this.xt_zhxgrbmid = xt_zhxgrbmid;
	}
	public String getXt_zhxgip() {
		return xt_zhxgip;
	}
	public void setXt_zhxgip(String xt_zhxgip) {
		this.xt_zhxgip = xt_zhxgip;
	}
	public String getXt_zxbz() {
		return xt_zxbz;
	}
	public void setXt_zxbz(String xt_zxbz) {
		this.xt_zxbz = xt_zxbz;
	}
	public String getXt_zxyy() {
		return xt_zxyy;
	}
	public void setXt_zxyy(String xt_zxyy) {
		this.xt_zxyy = xt_zxyy;
	}
	public String getJgssxdm() {
		return jgssxdm;
	}
	public void setJgssxdm(String jgssxdm) {
		this.jgssxdm = jgssxdm;
	}
	public String getHjd_ssxjgajgdm() {
		return hjd_ssxjgajgdm;
	}
	public void setHjd_ssxjgajgdm(String hjd_ssxjgajgdm) {
		this.hjd_ssxjgajgdm = hjd_ssxjgajgdm;
	}
	public String getHjd_sspcsdm() {
		return hjd_sspcsdm;
	}
	public void setHjd_sspcsdm(String hjd_sspcsdm) {
		this.hjd_sspcsdm = hjd_sspcsdm;
	}
	public String getHjd_sssq() {
		return hjd_sssq;
	}
	public void setHjd_sssq(String hjd_sssq) {
		this.hjd_sssq = hjd_sssq;
	}
	public String getJzd_szdsdm() {
		return jzd_szdsdm;
	}
	public void setJzd_szdsdm(String jzd_szdsdm) {
		this.jzd_szdsdm = jzd_szdsdm;
	}

	
}
