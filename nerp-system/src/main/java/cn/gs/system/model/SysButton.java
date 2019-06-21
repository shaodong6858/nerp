package cn.gs.system.model;

import javax.persistence.*;

import cn.gs.base.entity.IBaseEntity;
import lombok.Data;

@Data
@Table(name = "sys_button")
public class SysButton  implements IBaseEntity {
    /**
     * 主键
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "button_id")
    private Integer buttonId;

    /**
     * 应用ID sys_app表的外键
     */
    @Column(name = "fkapp_id")
    private String fkappId;

    /**
     * 按钮编码
     */
    @Column(name = "button_code")
    private String buttonCode;

    /**
     * 按钮名称
     */
    @Column(name = "button_name")
    private String buttonName;

}