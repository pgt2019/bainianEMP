package com.ruoyi.console.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备人员表 zh_deviceuser
 * 
 * @author bainian
 * @date 2019-06-05
 */
public class ZhDeviceuser extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Integer id;
	/** 人员编号 */
	private String personNumber;
	/** 人员名 */
	private String name;
	/** ic卡 */
	private String icCard;
	/** id卡号 */
	private String idCard;
	/** 扩展字段 */
	private String extendInfo;
	/** 人脸照片（实体类用） */
	private String faceImage;

	public void setId(Integer id) 
	{
		this.id = id;
	}

	public Integer getId() 
	{
		return id;
	}
	public void setPersonNumber(String personNumber) 
	{
		this.personNumber = personNumber;
	}

	public String getPersonNumber() 
	{
		return personNumber;
	}
	public void setName(String name) 
	{
		this.name = name;
	}

	public String getName() 
	{
		return name;
	}
	public void setIcCard(String icCard) 
	{
		this.icCard = icCard;
	}

	public String getIcCard() 
	{
		return icCard;
	}
	public void setIdCard(String idCard) 
	{
		this.idCard = idCard;
	}

	public String getIdCard() 
	{
		return idCard;
	}
	public void setExtendInfo(String extendInfo)
	{
		this.extendInfo = extendInfo;
	}

	public String getExtendInfo()
	{
		return extendInfo;
	}
	public void setFaceImage(String faceImage) 
	{
		this.faceImage = faceImage;
	}

	public String getFaceImage() 
	{
		return faceImage;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("personNumber", getPersonNumber())
            .append("name", getName())
            .append("icCard", getIcCard())
            .append("idCard", getIdCard())
            .append("extendInfo", getExtendInfo())
            .append("faceImage", getFaceImage())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .toString();
    }
}
