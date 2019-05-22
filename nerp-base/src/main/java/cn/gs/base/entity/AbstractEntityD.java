package cn.gs.base.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 实体基类
 * @author wangshaodong
 * 2019年05月13日
 */
@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper=false)
@MappedSuperclass
public abstract class AbstractEntityD extends AbstractEntity implements IEntityDeletable{
	
	/**
     * 删除时间
     */
	@ApiModelProperty(hidden = true)
    @Column(name="DELETE_TIME_", insertable=false, updatable=false)
    private java.util.Date deleteTime;
    
    /**
     * 删除人员ID
     */
	@ApiModelProperty(hidden = true)
    @Column(name="DELETE_USER_ID_", insertable=false, updatable=false)
    private String deleteUserId;
    
    /**
     * 删除状态：
     * 0-未删除
     * 1-已删除
     */
	@ApiModelProperty(hidden = true)
    @Column(name="IS_DELETE_", insertable=false, updatable=false)
    private String isDelete = AbstractEntityD.UNDELETED;
	
}
