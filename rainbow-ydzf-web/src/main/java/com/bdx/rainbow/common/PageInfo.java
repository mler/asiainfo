/**
 * 
 */
package com.bdx.rainbow.common;


/**
 * @author yangqx
 *
 */
public class PageInfo {

	private Integer pageStart;
	
	private Integer pageCount = 10;
	
	private Integer totalCount;
	
	private Integer pageNumber = 1;
	
	private Integer pageSize;
	
	public Integer getPageSize() {
		if(totalCount!=null&&totalCount>0){
            pageSize = totalCount / pageCount;
            if (totalCount % pageCount > 0) {
                pageSize++;
            }
		}else{
            pageSize=1;
        }
		return pageSize;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getPageStart() {
		if (pageStart == null || pageStart <= 1)
			return 0;
		else
			return (pageStart - 1) * pageCount;
	}

	public void setPageStart(Integer pageStart) {
		this.pageStart = pageStart;
		this.pageNumber = pageStart;
	}

	/*public Integer getPageCount() {
		return pageCount;
	}*/

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	
	public Integer getPageNumber() {
		return this.pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		/*if (pageNumber < 1)
			pageNumber = 1;*/
		//this.pageNumber = pageNumber;
		this.pageNumber = pageStart;
	}
	
	public void setPageSize(Integer pageSize) {
		if (pageSize==null|| pageSize<= 1)
			pageSize = 1;
		this.pageSize = pageSize;
	}
	
	
	public Integer getPageCount() {
		if (pageCount == null)
			return 10;
		else
			return this.pageCount;
	}
}
