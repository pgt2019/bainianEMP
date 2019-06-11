package com.ruoyi.console.service;

import com.ruoyi.console.domain.ZhRecordrollback;
import java.util.List;

/**
 * 识别记录 服务层
 * 
 * @author bainian
 * @date 2019-06-04
 */
public interface IZhRecordrollbackService 
{
	/**
     * 查询识别记录信息
     * 
     * @param id 识别记录ID
     * @return 识别记录信息
     */
	public ZhRecordrollback selectZhRecordrollbackById(Integer id);
	
	/**
     * 查询识别记录列表
     * 
     * @param zhRecordrollback 识别记录信息
     * @return 识别记录集合
     */
	public List<ZhRecordrollback> selectZhRecordrollbackList(ZhRecordrollback zhRecordrollback);
	
	/**
     * 新增识别记录
     * 
     * @param zhRecordrollback 识别记录信息
     * @return 结果
     */
	public int insertZhRecordrollback(ZhRecordrollback zhRecordrollback);
	
	/**
     * 修改识别记录
     * 
     * @param zhRecordrollback 识别记录信息
     * @return 结果
     */
	public int updateZhRecordrollback(ZhRecordrollback zhRecordrollback);
		
	/**
     * 删除识别记录信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteZhRecordrollbackByIds(String ids);
	
}
