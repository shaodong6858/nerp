package cn.gs.common.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.gs.base.entity.AbstractEntityManualId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件实体
 * @author xiapengtao
 * @date 2018年2月6日
 */
@SuppressWarnings("serial")
@Entity
@Table(name="COMMON_FILE")
@Data
@EqualsAndHashCode(callSuper=false)
public class FileInstance extends AbstractEntityManualId{

	public static final String TEMP = "temp";
	
	/**
	 * 唯一标识
	 */
	@Id
    @Column(name="ID_")
    private String id;
	
    /**
     * 姓名
     */
    @Column(name="NAME_")
    private String name;
    
    /**
     * 文件路径
     */
    @Column(name="PATH_")
    private String path;

    /**
     * 类型
     */
    @Column(name="TYPE_")
    private String type;
    
    /**
     * 状态
     */
    @Column(name="STATUS_")
    private String statue;
}
