package com.ruoyi.console.mapper;

import com.ruoyi.console.domain.ZhUser;
import java.util.List;	

/**
 * 振汇开放平台api  人员 数据层
 * 
 * @author bainian
 * @date 2019-05-10
 */
public interface ZhUserMapper 
{
	/**
     * 查询振汇开放平台api  人员信息
     * 
     * @param id 振汇开放平台api  人员ID
     * @return 振汇开放平台api  人员信息
     */
	public ZhUser selectZhUserById(Integer id);
	
	/**
     * 查询振汇开放平台api  人员列表
     * 
     * @param zhUser 振汇开放平台api  人员信息
     * @return 振汇开放平台api  人员集合
     */
	public List<ZhUser> selectZhUserList(ZhUser zhUser);
	
	/**
     * 新增振汇开放平台api  人员
     * 
     * @param zhUser 振汇开放平台api  人员信息
     * @return 结果
     */
	public int insertZhUser(ZhUser zhUser);
	
	/**
     * 修改振汇开放平台api  人员
     * 
     * @param zhUser 振汇开放平台api  人员信息
     * @return 结果
     */
	public int updateZhUser(ZhUser zhUser);
	
	/**
     * 删除振汇开放平台api  人员
     * 
     * @param id 振汇开放平台api  人员ID
     * @return 结果
     */
	public int deleteZhUserById(Integer id);
	
	/**
     * 批量删除振汇开放平台api  人员
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteZhUserByIds(String[] ids);
	
}