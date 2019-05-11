package com.ruoyi.console.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.console.mapper.ZhEquipmentMapper;
import com.ruoyi.console.domain.ZhEquipment;
import com.ruoyi.console.service.IZhEquipmentService;
import com.ruoyi.common.core.text.Convert;

/**
 * 振汇开放平台 设备接口绑定 服务层实现
 * 
 * @author bainian
 * @date 2019-05-10
 */
@Service
public class ZhEquipmentServiceImpl implements IZhEquipmentService 
{
	@Autowired
	private ZhEquipmentMapper zhEquipmentMapper;

	/**
     * 查询振汇开放平台 设备接口绑定信息
     * 
     * @param id 振汇开放平台 设备接口绑定ID
     * @return 振汇开放平台 设备接口绑定信息
     */
    @Override
	public ZhEquipment selectZhEquipmentById(Integer id)
	{
	    return zhEquipmentMapper.selectZhEquipmentById(id);
	}
	
	/**
     * 查询振汇开放平台 设备接口绑定列表
     * 
     * @param zhEquipment 振汇开放平台 设备接口绑定信息
     * @return 振汇开放平台 设备接口绑定集合
     */
	@Override
	public List<ZhEquipment> selectZhEquipmentList(ZhEquipment zhEquipment)
	{
	    return zhEquipmentMapper.selectZhEquipmentList(zhEquipment);
	}
	
    /**
     * 新增振汇开放平台 设备接口绑定
     * 
     * @param zhEquipment 振汇开放平台 设备接口绑定信息
     * @return 结果
     */
	@Override
	public int insertZhEquipment(ZhEquipment zhEquipment)
	{
	    return zhEquipmentMapper.insertZhEquipment(zhEquipment);
	}
	
	/**
     * 修改振汇开放平台 设备接口绑定
     * 
     * @param zhEquipment 振汇开放平台 设备接口绑定信息
     * @return 结果
     */
	@Override
	public int updateZhEquipment(ZhEquipment zhEquipment)
	{
	    return zhEquipmentMapper.updateZhEquipment(zhEquipment);
	}

	/**
     * 删除振汇开放平台 设备接口绑定对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteZhEquipmentByIds(String ids)
	{
		return zhEquipmentMapper.deleteZhEquipmentByIds(Convert.toStrArray(ids));
	}
	
}
