package cn.gs.system.model;

import java.util.Date;
import javax.persistence.*;

import cn.gs.base.entity.IBaseEntity;
import lombok.Data;

@Data
@Table(name = "org_user")
public class OrgUser implements IBaseEntity{
    /**
     * 主键
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 工号
     */
    @Column(name = "user_empnumber")
    private String userEmpnumber;

    /**
     * 所属部门编码
     */
    @Column(name = "user_deptcode")
    private String userDeptcode;

    /**
     * 职务
     */
    @Column(name = "user_jobtitle")
    private String userJobtitle;

    /**
     * 人员姓名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 电子邮件
     */
    @Column(name = "user_email")
    private String userEmail;

    /**
     * 移动电话
     */
    @Column(name = "user_mobile")
    private String userMobile;

    /**
     * 固定电话
     */
    @Column(name = "user_telphone")
    private String userTelphone;

    @Column(name = "user_mainpage")
    private String userMainpage;

    @Column(name = "user_manager")
    private String userManager;

    /**
     * 排序列
     */
    @Column(name = "user_order")
    private Integer userOrder;

    /**
     * 删除状态
     */
    @Column(name = "user_del")
    private String userDel;

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