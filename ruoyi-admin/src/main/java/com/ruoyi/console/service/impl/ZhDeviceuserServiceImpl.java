package com.ruoyi.console.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.console.mapper.ZhDeviceuserMapper;
import com.ruoyi.console.domain.ZhDeviceuser;
import com.ruoyi.console.service.IZhDeviceuserService;
import com.ruoyi.common.core.text.Convert;

/**
 * 设备人员 服务层实现
 * 
 * @author bainian
 * @date 2019-06-05
 */
@Service
public class ZhDeviceuserServiceImpl implements IZhDeviceuserService 
{
	@Autowired
	private ZhDeviceuserMapper zhDeviceuserMapper;

	/**
     * 查询设备人员信息
     * 
     * @param id 设备人员ID
     * @return 设备人员信息
     */
    @Override
	public ZhDeviceuser selectZhDeviceuserById(Integer id)
	{
	    return zhDeviceuserMapper.selectZhDeviceuserById(id);
	}
	
	/**
     * 查询设备人员列表
     * 
     * @param zhDeviceuser 设备人员信息
     * @return 设备人员集合
     */
	@Override
	public List<ZhDeviceuser> selectZhDeviceuserList(ZhDeviceuser zhDeviceuser)
	{
	    return zhDeviceuserMapper.selectZhDeviceuserList(zhDeviceuser);
	}
	
    /**
     * 新增设备人员
     * 
     * @param zhDeviceuser 设备人员信息
     * @return 结果
     */
	@Override
	public int insertZhDeviceuser(ZhDeviceuser zhDeviceuser)
	{
	    return zhDeviceuserMapper.insertZhDeviceuser(zhDeviceuser);
	}
	
	/**
     * 修改设备人员
     * 
     * @param zhDeviceuser 设备人员信息
     * @return 结果
     */
	@Override
	public int updateZhDeviceuser(ZhDeviceuser zhDeviceuser)
	{
	    return zhDeviceuserMapper.updateZhDeviceuser(zhDeviceuser);
	}

	/**
     * 删除设备人员对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteZhDeviceuserByIds(String ids)
	{
		return zhDeviceuserMapper.deleteZhDeviceuserByIds(Convert.toStrArray(ids));
	}

	@Override
	public int deleteZhDeviceuserByPersonNumber(String personNumber) {
		return zhDeviceuserMapper.deleteZhDeviceuserByPersonNumber(personNumber);
	}

	@Override
	public List<ZhDeviceuser> selectunAuthUser(String deviceNumber,String loginUser) {
		return zhDeviceuserMapper.selectunAuthUser(deviceNumber,loginUser);
	}

}
