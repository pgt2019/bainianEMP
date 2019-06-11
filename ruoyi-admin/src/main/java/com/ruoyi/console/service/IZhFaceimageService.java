package com.ruoyi.console.service;

import com.ruoyi.console.domain.ZhFaceimage;
import java.util.List;

/**
 * 人脸图片识别库 服务层
 * 
 * @author bainian
 * @date 2019-06-05
 */
public interface IZhFaceimageService 
{
	/**
     * 查询人脸图片识别库信息
     * 
     * @param id 人脸图片识别库ID
     * @return 人脸图片识别库信息
     */
	public ZhFaceimage selectZhFaceimageById(Integer id);
	
	/**
     * 查询人脸图片识别库列表
     * 
     * @param zhFaceimage 人脸图片识别库信息
     * @return 人脸图片识别库集合
     */
	public List<ZhFaceimage> selectZhFaceimageList(ZhFaceimage zhFaceimage);
	
	/**
     * 新增人脸图片识别库
     * 
     * @param zhFaceimage 人脸图片识别库信息
     * @return 结果
     */
	public int insertZhFaceimage(ZhFaceimage zhFaceimage);
	
	/**
     * 修改人脸图片识别库
     * 
     * @param zhFaceimage 人脸图片识别库信息
     * @return 结果
     */
	public int updateZhFaceimage(ZhFaceimage zhFaceimage);
		
	/**
     * 删除人脸图片识别库信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteZhFaceimageByIds(String ids);
	
}
