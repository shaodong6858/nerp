package cn.gs.system.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import cn.gs.base.entity.IBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 访问请求与业务操作映射
 * @author wangshaodong
 * 2019年05月13日
 */
@SuppressWarnings("serial")
@Entity
@Table(name="ACCESS_MAPPING")
@Data
@EqualsAndHashCode(callSuper=false)
public class AccessMapping implements IBaseEntity {
	
	/**
	 * 唯一标识
	 */
	@ApiModelProperty(hidden = true)
	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name="ID_")
    private String id;
	
	/**
     * uri参数
     */
	@Column(name="URI_", updatable=false)
	private String uri;
	
	/**
	 * 业务操作
	 */
	@Column(name="OPERATION_", updatable=false)
	private String operation;
	
}
