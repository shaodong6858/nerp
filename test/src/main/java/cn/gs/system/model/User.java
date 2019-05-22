package cn.gs.system.model;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import cn.gs.base.entity.AbstractEntityD;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description: 用户实体
 * @author wangshaodong
 * @date 2018年2月6日
 */
@Entity
@Table(name="SYSTEM_USER")
@Data
@EqualsAndHashCode(callSuper=false)
public class User extends AbstractEntityD {
	
	/**
	 * 设置系统用户
	 */
	public static final User SYSTEM;
	static {
		SYSTEM = new User();
		SYSTEM.setUsername("system");
		SYSTEM.setName("system");
	}
	
	public static final String ATTRIBUTE_PREFIX = "__user__";
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6276365046392930372L;
	public static final String DEFAULT_PASSWORD = "123456";
	
	/**
	 * 用户状态
	 */
	public static final String FLAG_NORMAL = "0";//正常
	public static final String FLAG_LIMITED = "1";//受限
	public static final String FLAG_FROZEN = "2";//冻结
	public static final String FLAG_DISABLED = "4";//禁用

    /**
     * 登录名
     */
    @Column(name="USERNAME_")
    private String username;
    
    /**
     * 姓名
     */
    @Column(name="NAME_")
    private String name;
    
    /**
     * 密码 MD5加密密文,
     */
    @Column(name="PASSWORD_")
    private String password;
    
    /**
     * 最后变更密码时间
     */
    @Column(name="CHG_PWD_TIME_")
    private Timestamp chgPwdTime;

    /**
     * 绑定工号，数字
     */
    @Column(name="AGENT_NO_")
    private String agentNo;

	/**
     * 绑定分机号，数字
     */
    @Column(name="EXT_NO_")
    private String extNo;
    
    /**
     * 1-受限
     * 2-冻结
     * 4-禁用
     */
    @Column(name="FLAG_")
    private String flag = FLAG_NORMAL;
    
    @Transient
    private String groupId;
    
    @Transient
    private String accessToken;
    
    /**
     * 权限列表
     */
    @Transient
    private List<String> permissions;
    
    /**
     * 附件属性参数存储
     */
    @Transient
    private Map<String, Object> extras;
    
    /**
     * 数据权限
     */
    @Transient
    private Map<String, Integer> dataPermissionMap;
    

    /**
     * vdn参数映射
     */
    @Transient
    private Map<String, String> vdnMappings;
    
}
