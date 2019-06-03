package com.ruoyi.console.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 振汇开放平台api  人员表 zh_user
 * 
 * @author bainian
 * @date 2019-05-10
 */
public class ZhUser extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Integer id;
	/** 人员编号(JSON) */
	private String personNumber;
	/** 姓名 */
	private String name;
	/** ic卡号 */
	private String icCard;
	/** id卡号 */
	private String idCard;
	/** 扩展字段 */
	private Object extendInfo;
	/** 创建时间 */
	private Date createTime;
	/** 用户人脸照片 */
	private String faceImages;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPersonNumber() {
		return personNumber;
	}

	public void setPersonNumber(String personNumber) {
		this.personNumber = personNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcCard() {
		return icCard;
	}

	public void setIcCard(String icCard) {
		this.icCard = icCard;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Object getExtendInfo() {
		return extendInfo;
	}

	public void setExtendInfo(Object extendInfo) {
		this.extendInfo = extendInfo;
	}

	@Override
	public Date getCreateTime() {
		return createTime;
	}

	@Override
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getFaceImages() {
		return faceImages;
	}

	public void setFaceImages(String faceImages) {
		this.faceImages = faceImages;
	}

	@Override
	public String toString() {
		return "ZhUser{" +
				"id=" + id +
				", personNumber='" + personNumber + '\'' +
				", name='" + name + '\'' +
				", icCard='" + icCard + '\'' +
				", idCard='" + idCard + '\'' +
				", extendInfo=" + extendInfo +
				", createTime=" + createTime +
				", faceImages=" + faceImages +
				'}';
	}
}
