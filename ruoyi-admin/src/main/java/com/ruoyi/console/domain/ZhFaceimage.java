package com.ruoyi.console.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 人脸图片识别库表 zh_faceimage
 * 
 * @author bainian
 * @date 2019-06-05
 */
public class ZhFaceimage extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Integer id;
	/** 人员编号 */
	private String personNumber;
	/** 图片base64 */
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
            .append("faceImage", getFaceImage())
            .toString();
    }
}
