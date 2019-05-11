package com.ruoyi.console.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.console.mapper.ZhUserMapper;
import com.ruoyi.console.domain.ZhUser;
import com.ruoyi.console.service.IZhUserService;
import com.ruoyi.common.core.text.Convert;

/**
 * 振汇开放平台api  人员 服务层实现
 * 
 * @author bainian
 * @date 2019-05-10
 */
@Service
public class ZhUserServiceImpl implements IZhUserService 
{
	@Autowired
	private ZhUserMapper zhUserMapper;

	/**
     * 查询振汇开放平台api  人员信息
     * 
     * @param id 振汇开放平台api  人员ID
     * @return 振汇开放平台api  人员信息
     */
    @Override
	public ZhUser selectZhUserById(Integer id)
	{
	    return zhUserMapper.selectZhUserById(id);
	}
	
	/**
     * 查询振汇开放平台api  人员列表
     * 
     * @param zhUser 振汇开放平台api  人员信息
     * @return 振汇开放平台api  人员集合
     */
	@Override
	public List<ZhUser> selectZhUserList(ZhUser zhUser)
	{
	    return zhUserMapper.selectZhUserList(zhUser);
	}
	
    /**
     * 新增振汇开放平台api  人员
     * 
     * @param zhUser 振汇开放平台api  人员信息
     * @return 结果
     */
	@Override
	public int insertZhUser(ZhUser zhUser)
	{
	    return zhUserMapper.insertZhUser(zhUser);
	}
	
	/**
     * 修改振汇开放平台api  人员
     * 
     * @param zhUser 振汇开放平台api  人员信息
     * @return 结果
     */
	@Override
	public int updateZhUser(ZhUser zhUser)
	{
	    return zhUserMapper.updateZhUser(zhUser);
	}

	/**
     * 删除振汇开放平台api  人员对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteZhUserByIds(String ids)
	{
		return zhUserMapper.deleteZhUserByIds(Convert.toStrArray(ids));
	}
	
}
