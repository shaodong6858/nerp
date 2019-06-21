package cn.gs.system.model;

import javax.persistence.*;

import cn.gs.base.entity.IBaseEntity;
import lombok.Data;

@Data
@Table(name = "sys_orgsite")
public class SysOrgsite  implements IBaseEntity {
    /**
     * 主键
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orgsite_id")
    private Integer orgsiteId;

    /**
     * 站点名称
     */
    @Column(name = "orgsite_name")
    private String orgsiteName;

    /**
     * 站点所在主机
     */
    @Column(name = "orgsite_host")
    private String orgsiteHost;

    /**
     * 应用协议
     */
    @Column(name = "orgsite_protocol")
    private String orgsiteProtocol;

    /**
     * 端口号
     */
    @Column(name = "orgsite_port")
    private String orgsitePort;

    /**
     * 默认路径
     */
    @Column(name = "orgsite_path")
    private String orgsitePath;

}