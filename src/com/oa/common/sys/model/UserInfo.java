package com.oa.common.sys.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * 
 * ClassName: UserInfo
 * 
 * @Description: TODO
 * @author 陈建国
 * @date 2016-8-21
 */
@Entity
@Table(name = "system_userinfo")
public class UserInfo implements java.io.Serializable
{

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 2445878473032010079L;

	//构造方法，初始化一些参数
	public UserInfo()
	{
		super();
		this.image ="/images/user.jpg";
		
	}

	
	
	// Fields
	@Id
	@TableGenerator(name = "table_seq", table = "SYSTEM_GENERATOR_TABLE", pkColumnName = "PK_KEY", pkColumnValue = "SYS_USERINFO_PK", valueColumnName = "PK_VALUE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "table_seq")
	// @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true)
	private Integer id;

	
	@Column(name = "userName", length = 50, nullable = false)
	private String userName;// 用户名

	@Column(name = "compellation")
	private String compellation;// 姓名

	@Column(name = "userPassword", length = 50, nullable = false)
	private String userPassword;// 用户密码

	@Column(name = "sex", length = 2)
	private String sex;// 性别 1为男，2为女

	@Column(name = "email", length = 100)
	private String email;// email

	@Column(name = "qq", length = 20)
	private String qq;// qq

	@Column(name = "isEnable")
	private Integer isEnable = 1;// 是否可用

	@Column(name = "onlinetime")
	private long online;// 在线时长

	@Column(name = "score")
	private Integer score;// 积分

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createTime", nullable = false)
	private Date createTime;// 注册时间

	@Column(name = "content", length = 1000)
	private String content;// 个性签名

	// 是否为高级用户,1是,0不是
	@Column(name = "isBetter")
	private Integer isBetter = 0;// 是否为高级用户

	@Column(name = "IMAGE")
	private String image;

	@Column(name = "TEL")
	private String tel;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "POSITION")
	private String position;// 职位

	// 所属单位,0为系统管理员用,非0为有单位的用
	@Column(name = "BELONG_UNIT")
	private Long belongUnit = 0L;// 所属单位

	// add ljk
	@Column(name = "SIGNATURE_PIC")
	private String signaturePic;// 电子签名

	@Column(name = "REMARKS")
	private String remarks;// 备注

	@Column(name = "IS_BBS_MANAGER")
	private Integer isBbsManager = 0;// 是否为BBS管理员,1是，0不是

	// 不映射进数据库
	@Transient
	private String extensionField1;
	@Transient
	// 不映射进数据库
	private String extensionField2;

	/**
	 * @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch =
	 *                     FetchType.LAZY)
	 * @JoinTable(name = "system_userrole", inverseJoinColumns = {
	 *                 @JoinColumn(name = "roleId") }, joinColumns = {
	 *                 @JoinColumn(name = "userId") }) private Set<Role> roles =
	 *                 new HashSet<Role>(0);
	 **/
	/*
	 * @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch =
	 * FetchType.LAZY)
	 * 
	 * @JoinTable(name = "system_user_userGroup", inverseJoinColumns = {
	 * 
	 * @JoinColumn(name = "groupId") }, joinColumns = { @JoinColumn(name =
	 * "userId") }) private Set<UserGroup> userGroup=new HashSet<UserGroup>(0);
	 */

	/**
	 * @return the id
	 */
	public Integer getId()
	{
		return id;
	}

	public Integer getUserId()
	{
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id)
	{
		this.id = id;
	}

	/**
	 * @return the userName
	 */
	public String getUserName()
	{
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	/**
	 * @return the userPassword
	 */
	public String getUserPassword()
	{
		return userPassword;
	}

	/**
	 * @param userPassword
	 *            the userPassword to set
	 */
	public void setUserPassword(String userPassword)
	{
		this.userPassword = userPassword;
	}

	/**
	 * @return the sex
	 */
	public String getSex()
	{
		return sex;
	}

	/**
	 * @param sex
	 *            the sex to set
	 */
	public void setSex(String sex)
	{
		this.sex = sex;
	}

	/**
	 * @return the email
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}

	/**
	 * @return the qq
	 */
	public String getQq()
	{
		return qq;
	}

	/**
	 * @param qq
	 *            the qq to set
	 */
	public void setQq(String qq)
	{
		this.qq = qq;
	}

	/**
	 * @return the isEnable
	 */
	public Integer getIsEnable()
	{
		return isEnable;
	}

	/**
	 * @param isEnable
	 *            the isEnable to set
	 */
	public void setIsEnable(Integer isEnable)
	{
		this.isEnable = isEnable;
	}

	/**
	 * @return the online
	 */
	public long getOnline()
	{
		return online;
	}

	/**
	 * @param online
	 *            the online to set
	 */
	public void setOnline(long online)
	{
		this.online = online;
	}

	/**
	 * @return the score
	 */
	public Integer getScore()
	{
		return score;
	}

	/**
	 * @param score
	 *            the score to set
	 */
	public void setScore(Integer score)
	{
		this.score = score;
	}

	/**
	 * @return the content
	 */
	public String getContent()
	{
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content)
	{
		this.content = content;
	}

	/**
	 * @return the isBetter
	 */
	public Integer getIsBetter()
	{
		return isBetter;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime()
	{
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	/**
	 * @param isBetter
	 *            the isBetter to set
	 */
	public void setIsBetter(Integer isBetter)
	{
		this.isBetter = isBetter;
	}

	/**
	 * @return the roles
	 */
	/**
	 * public Set<Role> getRoles() { return roles; }
	 **/

	/**
	 * @param roles
	 *            the roles to set
	 */
	/**
	 * public void setRoles(Set<Role> roles) { this.roles = roles; }
	 **/

	/*
	 * public Set<UserGroup> getUserGroup() { return userGroup; }
	 * 
	 * public void setUserGroup(Set<UserGroup> userGroup) { this.userGroup =
	 * userGroup; }
	 */

	public String getImage()
	{
		return image;
	}

	public void setImage(String image)
	{
		this.image = image;
	}

	public String getTel()
	{
		return tel;
	}

	public void setTel(String tel)
	{
		this.tel = tel;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getPosition()
	{
		return position;
	}

	public void setPosition(String position)
	{
		this.position = position;
	}

	public Long getBelongUnit()
	{
		return belongUnit;
	}

	public void setBelongUnit(Long belongUnit)
	{
		this.belongUnit = belongUnit;
	}

	public String getCompellation()
	{
		return compellation;
	}

	public void setCompellation(String compellation)
	{
		this.compellation = compellation;
	}

	public String getSignaturePic()
	{
		return signaturePic;
	}

	public void setSignaturePic(String signaturePic)
	{
		this.signaturePic = signaturePic;
	}

	public String getRemarks()
	{
		return remarks;
	}

	public void setRemarks(String remarks)
	{
		this.remarks = remarks;
	}

	public Integer getIsBbsManager()
	{
		return isBbsManager;
	}

	public void setIsBbsManager(Integer isBbsManager)
	{
		this.isBbsManager = isBbsManager;
	}

	public String getExtensionField1()
	{
		return extensionField1;
	}

	public void setExtensionField1(String extensionField1)
	{
		this.extensionField1 = extensionField1;
	}

	public String getExtensionField2()
	{
		return extensionField2;
	}

	public void setExtensionField2(String extensionField2)
	{
		this.extensionField2 = extensionField2;
	}

}
