package cn.gs.system.model;

import java.util.List;
import java.util.Map;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import cn.gs.base.entity.IBaseEntity;
import lombok.Data;

@Data
@Table(name = "org_usraccount")
public class OrgUsraccount  implements IBaseEntity {
    /**
     * 主键
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usracc_id")
    private Integer usraccId;

    /**
     * 用户工号
     */
    @Column(name = "usracc_empnumber")
    private String usraccEmpnumber;

    /**
     * 用户密码
     */
    @JsonProperty(access = Access.WRITE_ONLY)
    @Column(name = "usracc_pass")
    private String usraccPass;
    
    @Transient
    private OrgUser userInfo;
    
    @Transient
    private OrgUnit userUnit;
    
    @Transient
    private Map<String,List<SysApp>> navList;
    
}