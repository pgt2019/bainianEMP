package com.ruoyi.console.mapper;

import com.ruoyi.console.domain.ZhDeviceuser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 设备人员 数据层
 * 
 * @author bainian
 * @date 2019-06-05
 */
public interface ZhDeviceuserMapper 
{
	/**
     * 查询设备人员信息
     * 
     * @param id 设备人员ID
     * @return 设备人员信息
     */
	public ZhDeviceuser selectZhDeviceuserById(Integer id);
	
	/**
     * 查询设备人员列表
     * 
     * @param zhDeviceuser 设备人员信息
     * @return 设备人员集合
     */
	public List<ZhDeviceuser> selectZhDeviceuserList(ZhDeviceuser zhDeviceuser);
	
	/**
     * 新增设备人员
     * 
     * @param zhDeviceuser 设备人员信息
     * @return 结果
     */
	public int insertZhDeviceuser(ZhDeviceuser zhDeviceuser);
	
	/**
     * 修改设备人员
     * 
     * @param zhDeviceuser 设备人员信息
     * @return 结果
     */
	public int updateZhDeviceuser(ZhDeviceuser zhDeviceuser);
	
	/**
     * 删除设备人员
     * 
     * @param id 设备人员ID
     * @return 结果
     */
	public int deleteZhDeviceuserById(Integer id);
	
	/**
     * 批量删除设备人员
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteZhDeviceuserByIds(String[] ids);

	public int deleteZhDeviceuserByPersonNumber(String personNumber);

	public List<ZhDeviceuser> selectunAuthUser(@Param("deviceNumber") String deviceNumber,@Param("loginUser") String loginUser);
}