package cn.gs.base.entity;

/**
 * 实体基类
* @author wangshaodong
 * 2019年05月13日
 */

public interface IEntityDeletable extends IBaseEntity{
	/**
	 * UNDELETED ： 未删除
	 * DELETED   ： 已删除
	 */
	public final String UNDELETED  = "0";
	public final String DELETED = "1";
	
	public java.util.Date getDeleteTime();
	
	public void setDeleteTime(java.util.Date deleteTime);
	
	public void setDeleteUserId(String deleteUserId);
	
	public String getIsDelete();
	
	public void setIsDelete(String isDelete);
}
