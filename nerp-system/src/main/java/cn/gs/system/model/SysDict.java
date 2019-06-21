package cn.gs.system.model;

import javax.persistence.*;

import cn.gs.base.entity.IBaseEntity;
import lombok.Data;

@Data
@Table(name = "sys_dict")
public class SysDict  implements IBaseEntity {
    /**
     * ID
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dict_id")
    private Integer dictId;

    /**
     * 字典分类
     */
    @Column(name = "dict_type")
    private String dictType;

    /**
     * 上级
     */
    @Column(name = "dict_parentid")
    private String dictParentid;

    /**
     * 字典内容
     */
    @Column(name = "dict_value")
    private String dictValue;
}