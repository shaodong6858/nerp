package cn.gs.system.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import cn.gs.base.entity.IBaseEntity;
import lombok.Data;

@Data
@Table(name = "sys_app")
public class SysApp  implements IBaseEntity {
    /**
     * 主键
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "app_id")
    private Integer appId;

    /**
     * 应用名称
     */
    @Column(name = "app_name")
    private String appName;

    /**
     * 应用所在主机
     */
    @Column(name = "app_host")
    private String appHost;

    /**
     * 应用协议
     */
    @Column(name = "app_protocol")
    private String appProtocol;

    /**
     * 端口号
     */
    @Column(name = "app_port")
    private String appPort;

    /**
     * 应用path
     */
    @Column(name = "app_path")
    private String appPath;

    /**
     * 分类名称
     */
    @Column(name = "app_cat")
    private String appCat;

    /**
     * 子分类名称
     */
    @Column(name = "app_subcat")
    private String appSubcat;

    /**
     * 模块描述
     */
    @Column(name = "app_comment")
    private String appComment;

    /**
     * 外部链接
     */
    @Column(name = "app_openurl")
    private String appOpenurl;

    /**
     * 模块类型，INNER，OUTER
     */
    @Column(name = "app_moduletype")
    private String appModuletype;

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
    private List<SysApp> subList;
}