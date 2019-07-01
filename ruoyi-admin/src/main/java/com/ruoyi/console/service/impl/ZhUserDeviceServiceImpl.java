package com.ruoyi.console.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.console.mapper.ZhUserDeviceMapper;
import com.ruoyi.console.domain.ZhUserDevice;
import com.ruoyi.console.service.IZhUserDeviceService;
import com.ruoyi.common.core.text.Convert;

/**
 * 设备人员授权 服务层实现
 * 
 * @author bainian
 * @date 2019-06-24
 */
@Service
public class ZhUserDeviceServiceImpl implements IZhUserDeviceService 
{
	@Autowired
	private ZhUserDeviceMapper zhUserDeviceMapper;

	/**
     * 查询设备人员授权信息
     * 
     * @param id 设备人员授权ID
     * @return 设备人员授权信息
     */
    @Override
	public ZhUserDevice selectZhUserDeviceById(Integer id)
	{
	    return zhUserDeviceMapper.selectZhUserDeviceById(id);
	}
	
	/**
     * 查询设备人员授权列表
     * 
     * @param zhUserDevice 设备人员授权信息
     * @return 设备人员授权集合
     */
	@Override
	public List<ZhUserDevice> selectZhUserDeviceList(ZhUserDevice zhUserDevice)
	{
	    return zhUserDeviceMapper.selectZhUserDeviceList(zhUserDevice);
	}
	
    /**
     * 新增设备人员授权
     * 
     * @param zhUserDevice 设备人员授权信息
     * @return 结果
     */
	@Override
	public int insertZhUserDevice(ZhUserDevice zhUserDevice)
	{
	    return zhUserDeviceMapper.insertZhUserDevice(zhUserDevice);
	}
	
	/**
     * 修改设备人员授权
     * 
     * @param zhUserDevice 设备人员授权信息
     * @return 结果
     */
	@Override
	public int updateZhUserDevice(ZhUserDevice zhUserDevice)
	{
	    return zhUserDeviceMapper.updateZhUserDevice(zhUserDevice);
	}

	/**
     * 删除设备人员授权对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteZhUserDeviceByIds(String ids)
	{
		return zhUserDeviceMapper.deleteZhUserDeviceByIds(Convert.toStrArray(ids));
	}

	@Override
	public int deleteZhUserDeviceByPersonNumber(String personNumber) {
		return zhUserDeviceMapper.deleteZhUserDeviceByPersonNumber(personNumber);
	}

}
