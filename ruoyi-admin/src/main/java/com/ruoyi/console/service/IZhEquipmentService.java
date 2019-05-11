package com.ruoyi.console.service;

import com.ruoyi.console.domain.ZhEquipment;
import java.util.List;

/**
 * 振汇开放平台 设备接口绑定 服务层
 * 
 * @author bainian
 * @date 2019-05-10
 */
public interface IZhEquipmentService 
{
	/**
     * 查询振汇开放平台 设备接口绑定信息
     * 
     * @param id 振汇开放平台 设备接口绑定ID
     * @return 振汇开放平台 设备接口绑定信息
     */
	public ZhEquipment selectZhEquipmentById(Integer id);
	
	/**
     * 查询振汇开放平台 设备接口绑定列表
     * 
     * @param zhEquipment 振汇开放平台 设备接口绑定信息
     * @return 振汇开放平台 设备接口绑定集合
     */
	public List<ZhEquipment> selectZhEquipmentList(ZhEquipment zhEquipment);
	
	/**
     * 新增振汇开放平台 设备接口绑定
     * 
     * @param zhEquipment 振汇开放平台 设备接口绑定信息
     * @return 结果
     */
	public int insertZhEquipment(ZhEquipment zhEquipment);
	
	/**
     * 修改振汇开放平台 设备接口绑定
     * 
     * @param zhEquipment 振汇开放平台 设备接口绑定信息
     * @return 结果
     */
	public int updateZhEquipment(ZhEquipment zhEquipment);
		
	/**
     * 删除振汇开放平台 设备接口绑定信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteZhEquipmentByIds(String ids);
	
}
