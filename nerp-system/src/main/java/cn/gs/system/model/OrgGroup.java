package cn.gs.system.model;

import java.util.Date;
import javax.persistence.*;

import cn.gs.base.entity.IBaseEntity;
import lombok.Data;

@Data
@Table(name = "org_group")
public class OrgGroup  implements IBaseEntity {
    /**
     * 主键(UUID)
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private String groupId;

    /**
     * 群组名称
     */
    @Column(name = "group_name")
    private String groupName;

    /**
     * 所属部门编码
     */
    @Column(name = "group_deptcode")
    private String groupDeptcode;

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

}