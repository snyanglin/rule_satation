package com.founder.drools.core.model;

import java.util.List;

/**
 * ****************************************************************************
 * @Package:      [com.founder.drools.core.model.Paginator.java]  
 * @ClassName:    [Paginator]   
 * @Description:  [分页对象]   
 * @Author:       [zhang.hai@founder.com.cn]  
 * @CreateDate:   [2015年12月2日 上午9:47:24]   
 * @UpdateUser:   [ZhangHai(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2015年12月2日 上午9:47:24，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
public class Paginator {
	private int totalCount=0;//分页的总条目数
	private int pageIndex=1;//当前页码
	private int pageSize=10;//每一页的条目数
	private int visiblePages=5;//最多显示的页码数
	private List list = null;//原始List
	private List subList = null;//分页List
	
	public Paginator(){}
	public Paginator(List list){
		this.setList(list);
	}
	
	
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
		initList();
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		initList();
	}
	public int getVisiblePages() {
		return visiblePages;
	}
	public void setVisiblePages(int visiblePages) {
		this.visiblePages = visiblePages;
	}
	public List getList() {
		return subList;
	}
	public void setList(List list) {
		this.list = list;
		initList();
	}		
	
	//分页list截取
	private void initList(){
		if(list==null) return;
		totalCount=list.size();
		int fromIndex = (pageIndex-1)*pageSize;
		int toIndex=fromIndex+pageSize;
		if(toIndex>totalCount){
			toIndex=totalCount;
		}
		subList = list.subList(fromIndex, toIndex);
	}
}
