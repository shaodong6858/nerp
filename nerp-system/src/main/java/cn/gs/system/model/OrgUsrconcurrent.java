package cn.gs.system.model;

import javax.persistence.*;

import cn.gs.base.entity.IBaseEntity;
import lombok.Data;

@Data
@Table(name = "org_usrconcurrent")
public class OrgUsrconcurrent  implements IBaseEntity {
    /**
     * 主键
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usrcon_id")
    private Integer usrconId;

    /**
     * 用户工号
     */
    @Column(name = "usrcon_empnumber")
    private String usrconEmpnumber;

    /**
     * 用户兼职部门
     */
    @Column(name = "usrcon_deptcode")
    private String usrconDeptcode;

}