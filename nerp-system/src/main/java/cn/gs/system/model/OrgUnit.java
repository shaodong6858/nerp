package cn.gs.system.model;

import javax.persistence.*;

import cn.gs.base.entity.IBaseEntity;
import lombok.Data;

@Data
@Table(name = "org_unit")
public class OrgUnit  implements IBaseEntity {
    /**
     * 主键
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unit_id")
    private Integer unitId;

    /**
     * 单位mdm编码
     */
    @Column(name = "unit_mdmcode")
    private String unitMdmcode;

    /**
     * OA单位编码
     */
    @Column(name = "unit_deptcode")
    private String unitDeptcode;

    /**
     * 机构简称
     */
    @Column(name = "unit_name")
    private String unitName;

    /**
     * 机构全称
     */
    @Column(name = "unit_fullname")
    private String unitFullname;

    /**
     * 层次名称
     */
    @Column(name = "unit_hierarchyname")
    private String unitHierarchyname;

    /**
     * 上级单位编码
     */
    @Column(name = "unit_parentcode")
    private String unitParentcode;

    /**
     * 机构位置
     */
    @Column(name = "unit_address")
    private String unitAddress;

    /**
     * 所属站点
     */
    @Column(name = "unit_sitename")
    private String unitSitename;

    /**
     * 顶级单位编码
     */
    @Column(name = "unit_topcode")
    private String unitTopcode;

    /**
     * 单位类型
     */
    @Column(name = "unit_businesstype")
    private String unitBusinesstype;

    /**
     * 单位级别
     */
    @Column(name = "unit_propertylevel")
    private Integer unitPropertylevel;

    /**
     * 备注
     */
    @Column(name = "unit_comment")
    private String unitComment;

    /**
     * 排序列
     */
    @Column(name = "unit_order")
    private Integer unitOrder;

    /**
     * 删除标志
     */
    @Column(name = "unit_del")
    private String unitDel;

    /**
     * 部门领导
     */
    @Column(name = "unit_deptleader")
    private String unitDeptleader;

    /**
     * 机构分类 部门：dept,单位:unit
     */
    @Column(name = "unit_category")
    private String unitCategory;

    /**
     * 创建人
     */
    @Column(name = "created_by")
    private String createdBy;

    /**
     * 创建时间
     */
    @Column(name = "created_time")
    private String createdTime;

    /**
     * 更新人
     */
    @Column(name = "updated_by")
    private String updatedBy;

    /**
     * 更新时间
     */
    @Column(name = "updated_time")
    private String updatedTime;

}