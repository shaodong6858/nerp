package cn.gs.system.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.gs.base.entity.IBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description: 用户实体
 * @author wangshaodong
 * @date 2019年06月17日
 */
@Entity
@Table(name="org_user")
@Data
@EqualsAndHashCode(callSuper=false)
public class User implements IBaseEntity {

	@ApiModelProperty(hidden = true)
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
	private String userId;
	
	@Column(name="user_empnumber")
	private String userEmpnumber;
	
	@Column(name="user_name")
	private String userName;
	
	@Column(name="user_email")
	private String userEmail;
	
	@Column(name="user_mobile")
	private String userMobile;
	
	@Column(name="user_telphone")
	private String userTelphone;
	
	@Column(name="user_order")
	private String userOrder;
	
	@Column(name="user_deptcode")
	private String userDeptcode;
    
}
