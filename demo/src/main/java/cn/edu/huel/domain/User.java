package cn.edu.huel.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户表
 *
 * @TableName acl_user
 */
@TableName(value = "acl_user")
@Data
public class User implements Serializable {
	/**
	 * 会员id
	 */
	@TableId
	private String id;

	/**
	 * 微信openid
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 用户头像
	 */
	private String salt;

	/**
	 * 用户签名
	 */
	private String token;

	/**
	 * 逻辑删除 1（true）已删除， 0（false）未删除
	 */
	private Integer isDeleted;

	/**
	 * 创建时间
	 */
	private Date gmtCreate;

	/**
	 * 更新时间
	 */
	private Date gmtModified;

	@TableField(exist = false)
	private List<String> permissions;

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
}