package cn.gs.system.model;

import javax.persistence.*;

import cn.gs.base.entity.IBaseEntity;
import lombok.Data;

@Data
@Table(name = "sys_navreader")
public class SysNavreader  implements IBaseEntity {
    /**
     * 主键
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "navrdr_id")
    private Integer navrdrId;

    /**
     * 导航模块id
     */
    @Column(name = "fknav_id")
    private Integer fknavId;

    /**
     * 导航模块可视权限
     */
    @Column(name = "nav_reader")
    private String navReader;
}