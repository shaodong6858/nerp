package cn.gs.base.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 实体基类，无id
 * @author wangshaodong
 * 2019年05月13日
 * http://kruders.com/hibernate/mappedsuperclass-override-column-identifier/
 */
@SuppressWarnings("serial")
@Data
@MappedSuperclass
public abstract class AbstractEntityManualId implements IBaseEntityManualId{
	
	/**
     * 创建时间
     */
	@ApiModelProperty(hidden = true)
    @Column(name="CREATE_TIME_", insertable=false, updatable=false)
    private java.util.Date createTime;
    
    /**
     * 创建人员ID， 默认值 SYSTEM
     */
	@ApiModelProperty(hidden = true)
    @Column(name="CREATE_USER_ID_", updatable=false)
    private String createUserId;
    
    /**
     * 最后修改时间
     */
	@ApiModelProperty(hidden = true)
    @Column(name="LAST_MODIFIED_TIME_", insertable=false, updatable=false)
    private java.util.Date lastModifiedTime;
    
    /**
     * 最后修改人员ID
     */
	@ApiModelProperty(hidden = true)
    @Column(name="LAST_MODIFIED_USER_ID_", insertable=false)
    private String lastModifiedUserId;
	
    /**
     * 备用字段1
     */
	@ApiModelProperty(hidden = true)
    @Column(name="COL1_")
    private String col1;
    
    /**
     * 备用字段2
     */
	@ApiModelProperty(hidden = true)
    @Column(name="COL2_")
    private String col2;
    
    /**
     * 备用字段3
     */
	@ApiModelProperty(hidden = true)
    @Column(name="COL3_")
    private String col3;
    
    /**
     * 备用字段4
     */
	@ApiModelProperty(hidden = true)
    @Column(name="COL4_")
    private String col4;
    
    /**
     * 备用字段5
     */
	@ApiModelProperty(hidden = true)
    @Column(name="COL5_")
    private String col5;
	
}
