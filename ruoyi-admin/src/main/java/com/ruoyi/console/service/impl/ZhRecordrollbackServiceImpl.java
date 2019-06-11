package com.ruoyi.console.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.console.mapper.ZhRecordrollbackMapper;
import com.ruoyi.console.domain.ZhRecordrollback;
import com.ruoyi.console.service.IZhRecordrollbackService;
import com.ruoyi.common.core.text.Convert;

/**
 * 识别记录 服务层实现
 * 
 * @author bainian
 * @date 2019-06-04
 */
@Service
public class ZhRecordrollbackServiceImpl implements IZhRecordrollbackService 
{
	@Autowired
	private ZhRecordrollbackMapper zhRecordrollbackMapper;

	/**
     * 查询识别记录信息
     * 
     * @param id 识别记录ID
     * @return 识别记录信息
     */
    @Override
	public ZhRecordrollback selectZhRecordrollbackById(Integer id)
	{
	    return zhRecordrollbackMapper.selectZhRecordrollbackById(id);
	}
	
	/**
     * 查询识别记录列表
     * 
     * @param zhRecordrollback 识别记录信息
     * @return 识别记录集合
     */
	@Override
	public List<ZhRecordrollback> selectZhRecordrollbackList(ZhRecordrollback zhRecordrollback)
	{
	    return zhRecordrollbackMapper.selectZhRecordrollbackList(zhRecordrollback);
	}
	
    /**
     * 新增识别记录
     * 
     * @param zhRecordrollback 识别记录信息
     * @return 结果
     */
	@Override
	public int insertZhRecordrollback(ZhRecordrollback zhRecordrollback)
	{
	    return zhRecordrollbackMapper.insertZhRecordrollback(zhRecordrollback);
	}
	
	/**
     * 修改识别记录
     * 
     * @param zhRecordrollback 识别记录信息
     * @return 结果
     */
	@Override
	public int updateZhRecordrollback(ZhRecordrollback zhRecordrollback)
	{
	    return zhRecordrollbackMapper.updateZhRecordrollback(zhRecordrollback);
	}

	/**
     * 删除识别记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteZhRecordrollbackByIds(String ids)
	{
		return zhRecordrollbackMapper.deleteZhRecordrollbackByIds(Convert.toStrArray(ids));
	}
	
}
