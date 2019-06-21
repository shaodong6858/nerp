package cn.gs.system.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import cn.gs.base.entity.IBaseEntity;
import lombok.Data;

@Data
@Table(name = "sys_nav")
public class SysNav  implements IBaseEntity {
    /**
     * 主键
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nav_id")
    private Integer navId;

    /**
     * 应用名称
     */
    @Column(name = "nav_name")
    private String navName;

    /**
     * 应用ID
     */
    @Column(name = "fkapp_id")
    private Integer fkappId;

    /**
     * 单位编码
     */
    @Column(name = "nav_deptcode")
    private String navDeptcode;

    /**
     * 分类名称
     */
    @Column(name = "nav_cat")
    private String navCat;

    /**
     * 子分类名称
     */
    @Column(name = "nav_subcat")
    private String navSubcat;

    /**
     * 排序列
     */
    @Column(name = "nav_order")
    private Integer navOrder;

    /**
     * 模块描述
     */
    @Column(name = "nav_comment")
    private String navComment;

    /**
     * 创建人
     */
    @Column(name = "created_by")
    private String createdBy;

    /**
     * 创建时间
     */
    @Column(name = "created_time")
    private Date createdTime;

    /**
     * 更新人
     */
    @Column(name = "updated_by")
    private String updatedBy;

    /**
     * 更新时间
     */
    @Column(name = "updated_time")
    private Date updatedTime;
    
    @Transient
    private SysApp app;
    
}