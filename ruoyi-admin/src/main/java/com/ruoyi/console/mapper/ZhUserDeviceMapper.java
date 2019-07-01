package com.ruoyi.console.mapper;

import com.ruoyi.console.domain.ZhUserDevice;
import java.util.List;	

/**
 * 设备人员授权 数据层
 * 
 * @author bainian
 * @date 2019-06-24
 */
public interface ZhUserDeviceMapper 
{
	/**
     * 查询设备人员授权信息
     * 
     * @param id 设备人员授权ID
     * @return 设备人员授权信息
     */
	public ZhUserDevice selectZhUserDeviceById(Integer id);
	
	/**
     * 查询设备人员授权列表
     * 
     * @param zhUserDevice 设备人员授权信息
     * @return 设备人员授权集合
     */
	public List<ZhUserDevice> selectZhUserDeviceList(ZhUserDevice zhUserDevice);
	
	/**
     * 新增设备人员授权
     * 
     * @param zhUserDevice 设备人员授权信息
     * @return 结果
     */
	public int insertZhUserDevice(ZhUserDevice zhUserDevice);
	
	/**
     * 修改设备人员授权
     * 
     * @param zhUserDevice 设备人员授权信息
     * @return 结果
     */
	public int updateZhUserDevice(ZhUserDevice zhUserDevice);
	
	/**
     * 删除设备人员授权
     * 
     * @param id 设备人员授权ID
     * @return 结果
     */
	public int deleteZhUserDeviceById(Integer id);
	
	/**
     * 批量删除设备人员授权
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteZhUserDeviceByIds(String[] ids);

	/**
	 * 根据人员编号查询
	 * @param personNumber
	 * @return
	 */
	public int deleteZhUserDeviceByPersonNumber(String personNumber);

}