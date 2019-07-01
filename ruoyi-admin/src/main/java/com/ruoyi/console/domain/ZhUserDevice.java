package com.ruoyi.console.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备人员授权表 zh_user_device
 * 
 * @author bainian
 * @date 2019-06-24
 */
public class ZhUserDevice extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Integer id;
	/**  */
	private String deviceNumber;
	/**  */
	private String personNumber;

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
	public void setPersonNumber(String personNumber) 
	{
		this.personNumber = personNumber;
	}

	public String getPersonNumber() 
	{
		return personNumber;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("deviceNumber", getDeviceNumber())
            .append("personNumber", getPersonNumber())
            .toString();
    }
}
