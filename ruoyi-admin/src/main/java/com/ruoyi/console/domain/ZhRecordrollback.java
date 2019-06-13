package com.ruoyi.console.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 识别记录表 zh_recordrollback
 * 
 * @author bainian
 * @date 2019-06-04
 */
public class ZhRecordrollback extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Integer id;
	/** 设备编号 */
	private String deviceNumber;
	/** 识别时间 */
	private Date recognizeTime;
	/** 识别人员姓名 */
	private String name;
	/** 识别人员编号 */
	private String personNumber;
	/** 相似度 */
	private String similarity;
	/** 识别图 */
	private String faceImage;
	/** ic卡 */
	private String icCard;
	/** id卡 */
	private String idCard;
	/** 识别类型 */
	private String recognizedType;
	/** 扩展字段 */
	private String extendInfo;

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Integer getId() 
	{
		return id;
	}
	public void setDeviceNumber(String deviceNumber) 
	{
		this.deviceNumber = deviceNumber;
	}

	public String getDeviceNumber() 
	{
		return deviceNumber;
	}
	public void setRecognizeTime(Date recognizeTime) 
	{
		this.recognizeTime = recognizeTime;
	}

	public Date getRecognizeTime() 
	{
		return recognizeTime;
	}
	public void setName(String name) 
	{
		this.name = name;
	}

	public String getName() 
	{
		return name;
	}
	public void setPersonNumber(String personNumber) 
	{
		this.personNumber = personNumber;
	}

	public String getPersonNumber() 
	{
		return personNumber;
	}
	public void setSimilarity(String similarity) 
	{
		this.similarity = similarity;
	}

	public String getSimilarity() 
	{
		return similarity;
	}
	public void setFaceImage(String faceImage)
	{
		this.faceImage = faceImage;
	}

	public String getFaceImage()
	{
		return faceImage;
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
	public void setRecognizedType(String recognizedType) 
	{
		this.recognizedType = recognizedType;
	}

	public String getRecognizedType() 
	{
		return recognizedType;
	}
	public void setExtendInfo(String extendInfo)
	{
		this.extendInfo = extendInfo;
	}

	public String getExtendInfo()
	{
		return extendInfo;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("deviceNumber", getDeviceNumber())
            .append("recognizeTime", getRecognizeTime())
            .append("name", getName())
            .append("personNumber", getPersonNumber())
            .append("similarity", getSimilarity())
            .append("faceImage", getFaceImage())
            .append("icCard", getIcCard())
            .append("idCard", getIdCard())
            .append("recognizedType", getRecognizedType())
            .append("extendInfo", getExtendInfo())
            .toString();
    }
}
