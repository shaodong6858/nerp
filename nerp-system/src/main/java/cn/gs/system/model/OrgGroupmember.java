package cn.gs.system.model;

import javax.persistence.*;

import cn.gs.base.entity.IBaseEntity;
import lombok.Data;

@Data
@Table(name = "org_groupmember")
public class OrgGroupmember  implements IBaseEntity {
    /**
     * 主键
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grpmem_id")
    private Integer grpmemId;

    /**
     * 群组ID
     */
    @Column(name = "group_id")
    private String groupId;

    /**
     * 成员ID
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 获取主键
     *
     * @return grpmem_id - 主键
     */
    public Integer getGrpmemId() {
        return grpmemId;
    }

    /**
     * 设置主键
     *
     * @param grpmemId 主键
     */
    public void setGrpmemId(Integer grpmemId) {
        this.grpmemId = grpmemId;
    }

    /**
     * 获取群组ID
     *
     * @return group_id - 群组ID
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * 设置群组ID
     *
     * @param groupId 群组ID
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * 获取成员ID
     *
     * @return user_id - 成员ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置成员ID
     *
     * @param userId 成员ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
}