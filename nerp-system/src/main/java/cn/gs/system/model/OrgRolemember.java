package cn.gs.system.model;

import javax.persistence.*;

import cn.gs.base.entity.IBaseEntity;
import lombok.Data;

@Data
@Table(name = "org_rolemember")
public class OrgRolemember  implements IBaseEntity {
    /**
     * 主键
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rolmem_id")
    private Integer rolmemId;

    /**
     * 角色ID
     */
    @Column(name = "role_id")
    private String roleId;

    /**
     * 成员ID
     */
    @Column(name = "user_id")
    private String userId;

}