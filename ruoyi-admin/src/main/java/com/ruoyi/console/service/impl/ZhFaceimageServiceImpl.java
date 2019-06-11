package com.ruoyi.console.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.console.mapper.ZhFaceimageMapper;
import com.ruoyi.console.domain.ZhFaceimage;
import com.ruoyi.console.service.IZhFaceimageService;
import com.ruoyi.common.core.text.Convert;

/**
 * 人脸图片识别库 服务层实现
 * 
 * @author bainian
 * @date 2019-06-05
 */
@Service
public class ZhFaceimageServiceImpl implements IZhFaceimageService 
{
	@Autowired
	private ZhFaceimageMapper zhFaceimageMapper;

	/**
     * 查询人脸图片识别库信息
     * 
     * @param id 人脸图片识别库ID
     * @return 人脸图片识别库信息
     */
    @Override
	public ZhFaceimage selectZhFaceimageById(Integer id)
	{
	    return zhFaceimageMapper.selectZhFaceimageById(id);
	}
	
	/**
     * 查询人脸图片识别库列表
     * 
     * @param zhFaceimage 人脸图片识别库信息
     * @return 人脸图片识别库集合
     */
	@Override
	public List<ZhFaceimage> selectZhFaceimageList(ZhFaceimage zhFaceimage)
	{
	    return zhFaceimageMapper.selectZhFaceimageList(zhFaceimage);
	}
	
    /**
     * 新增人脸图片识别库
     * 
     * @param zhFaceimage 人脸图片识别库信息
     * @return 结果
     */
	@Override
	public int insertZhFaceimage(ZhFaceimage zhFaceimage)
	{
	    return zhFaceimageMapper.insertZhFaceimage(zhFaceimage);
	}
	
	/**
     * 修改人脸图片识别库
     * 
     * @param zhFaceimage 人脸图片识别库信息
     * @return 结果
     */
	@Override
	public int updateZhFaceimage(ZhFaceimage zhFaceimage)
	{
	    return zhFaceimageMapper.updateZhFaceimage(zhFaceimage);
	}

	/**
     * 删除人脸图片识别库对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteZhFaceimageByIds(String ids)
	{
		return zhFaceimageMapper.deleteZhFaceimageByIds(Convert.toStrArray(ids));
	}
	
}
