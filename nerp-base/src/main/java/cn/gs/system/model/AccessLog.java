package cn.gs.system.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import cn.gs.base.entity.IBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 访问日志，根据配置的映射进行过滤保存
 * @author wangshaodong
 * 2019年05月13日
 */
@SuppressWarnings("serial")
@Entity
@Table(name="ACCESS_LOG")
@Data
@EqualsAndHashCode(callSuper=false)
public class AccessLog implements IBaseEntity {
	
	/**
	 * 唯一标识
	 */
	@ApiModelProperty(hidden = true)
	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name="ID_")
    private String id;
	
	/**
     * 创建时间
     */
	@ApiModelProperty(hidden = true)
    @Column(name="CREATE_TIME_", insertable=false, updatable=false)
    private java.util.Date createTime;
    
    /**
     * 创建人员ID， 默认值 SYSTEM
     */
	@ApiModelProperty(hidden = true)
    @Column(name="CREATE_USER_ID_", updatable=false)
    private String createUserId;
    
	/**
     * url参数
     */
	@Column(name="QUERY_STRING_", updatable=false)
	private String queryString;
	
	/**
     * 请求时间
     */
	@Column(name="REQUEST_TIME_", updatable=false)
	private Timestamp requestTime;

	/**
     * 响应时间
     */
	@Column(name="RESPONSE_TIME_", updatable=false)
	private Timestamp responseTime;
	
	/**
     * 用时
     */
	@Column(name="ELAPSED_TIME_", updatable=false)
	private long elapsedTime;
	
	/**
	 * 客户端ip
	 */
	@Column(name="CLIENT_ADDR_", updatable=false)
	private String clientAddr;
	
	
	/**
	 * 请求方法
	 */
	@Column(name="METHOD_", updatable=false)
	private String method;
	
	/**
	 * 响应码
	 */
	@Column(name="RESPONSE_STATUS_", updatable=false)
	private int responseStatus;
	
	/**
	 * 操作
	 */
	@Column(name="OPERATION_", updatable=false)
	private String operation;
	
	/**
	 * 请求地址
	 */
	@Column(name="URI_", updatable=false)
	private String uri;
	
	/**
	 * accessToken
	 */
	@Column(name="ACCESS_TOKEN_", updatable=false)
	private String accessToken;
	
	/**
	 * 请求头
	 */
	@Column(name="HEADERS_", updatable=false)
	private String headers;
	
	/**
	 * 请求参数
	 */
	@Column(name="PARAMETERS_", updatable=false)
	private String parameters;
	
	/**
	 * 响应数据
	 */
	@Column(name="RESULT_", updatable=false)
	private String result;
	
}
