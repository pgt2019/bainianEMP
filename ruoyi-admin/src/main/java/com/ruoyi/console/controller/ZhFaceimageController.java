package com.ruoyi.console.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.console.utils.ZhEquipmentUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.console.domain.ZhFaceimage;
import com.ruoyi.console.service.IZhFaceimageService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 人脸图片识别库 信息操作处理
 * 
 * @author bainian
 * @date 2019-06-05
 */
@Controller
@RequestMapping("/console/zhFaceimage")
public class ZhFaceimageController extends BaseController
{
    private String prefix = "console/zhFaceimage";
	
	@Autowired
	private IZhFaceimageService zhFaceimageService;
	
	@RequiresPermissions("console:zhFaceimage:view")
	@GetMapping("/{personNumber}")
	public String zhFaceimage(@PathVariable("personNumber") String personNumber, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		session.setAttribute("personNumber",personNumber);
	    return prefix + "/zhFaceimage";
	}
	
	/**
	 * 查询人脸图片识别库列表
	 */
	@RequiresPermissions("console:zhFaceimage:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ZhFaceimage zhFaceimage,HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		String personNumber = session.getAttribute("personNumber").toString();
		if(!personNumber.equals("") && personNumber != null){
			zhFaceimage.setPersonNumber(personNumber);
		}
		startPage();
        List<ZhFaceimage> list = zhFaceimageService.selectZhFaceimageList(zhFaceimage);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出人脸图片识别库列表
	 */
	@RequiresPermissions("console:zhFaceimage:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ZhFaceimage zhFaceimage)
    {
    	List<ZhFaceimage> list = zhFaceimageService.selectZhFaceimageList(zhFaceimage);
        ExcelUtil<ZhFaceimage> util = new ExcelUtil<ZhFaceimage>(ZhFaceimage.class);
        return util.exportExcel(list, "zhFaceimage");
    }
	
	/**
	 * 新增人脸图片识别库
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存人脸图片识别库
	 */
	@RequiresPermissions("console:zhFaceimage:add")
	@Log(title = "人脸图片识别库")
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(MultipartFile faceImage,HttpServletRequest request) throws IOException {
		if (!faceImage.isEmpty()) {
			  	HttpSession session = request.getSession();
			  	String personNumber = session.getAttribute("personNumber").toString();
				BASE64Encoder encoder = new BASE64Encoder();
				String data = encoder.encode(faceImage.getBytes());                        // 通过base64来转化图片
				ZhFaceimage zhFaceimage = new ZhFaceimage();
				zhFaceimage.setFaceImage("data:image/png;base64,"+data);
				zhFaceimage.setPersonNumber(personNumber);
				return toAjax(zhFaceimageService.insertZhFaceimage(zhFaceimage));
		}
		return AjaxResult.error("上传失败");
	}

	/**
	 * 修改人脸图片识别库
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		ZhFaceimage zhFaceimage = zhFaceimageService.selectZhFaceimageById(id);
		mmap.put("zhFaceimage", zhFaceimage);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存人脸图片识别库
	 */
	@RequiresPermissions("console:zhFaceimage:edit")
	@Log(title = "人脸图片识别库", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ZhFaceimage zhFaceimage)
	{		
		return toAjax(zhFaceimageService.updateZhFaceimage(zhFaceimage));
	}
	
	/**
	 * 删除人脸图片识别库
	 */
	@RequiresPermissions("console:zhFaceimage:remove")
	@Log(title = "人脸图片识别库", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(zhFaceimageService.deleteZhFaceimageByIds(ids));
	}
	
}
