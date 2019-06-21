package cn.gs.system.model;

import javax.persistence.*;

import cn.gs.base.entity.IBaseEntity;
import lombok.Data;

@Data
@Table(name = "sys_authorbutton")
public class SysAuthorbutton  implements IBaseEntity {
    /**
     * 主键
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authbtn_id")
    private Integer authbtnId;

    /**
     * 功能按钮编码
     */
    @Column(name = "fkbutton_id")
    private Integer fkbuttonId;

    /**
     * 可视ID 可以是单位、角色、群组、人员的ID
     */
    @Column(name = "authbtn_reader")
    private String authbtnReader;
}