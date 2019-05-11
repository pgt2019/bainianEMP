package com.ruoyi.console.controller;

import java.util.Calendar;
import java.util.List;
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
import com.ruoyi.console.domain.ZhEquipment;
import com.ruoyi.console.service.IZhEquipmentService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 振汇开放平台 设备接口绑定 信息操作处理
 * 
 * @author bainian
 * @date 2019-05-10
 */
@Controller
@RequestMapping("/console/zhEquipment")
public class ZhEquipmentController extends BaseController
{
    private String prefix = "console/zhEquipment";
	
	@Autowired
	private IZhEquipmentService zhEquipmentService;
	
	@RequiresPermissions("console:zhEquipment:view")
	@GetMapping()
	public String zhEquipment()
	{
	    return prefix + "/zhEquipment";
	}

	/**
	 * 控制台
	 * @return
	 */
	@RequiresPermissions("console:zhEquipment:view")
	@RequestMapping("/index")
	public String myEquipment()
	{
		return prefix + "/myEquipment";
	}

	/**
	 * 查询振汇开放平台 设备接口绑定列表
	 */
	@RequiresPermissions("console:zhEquipment:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ZhEquipment zhEquipment)
	{
		startPage();
        List<ZhEquipment> list = zhEquipmentService.selectZhEquipmentList(zhEquipment);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出振汇开放平台 设备接口绑定列表
	 */
	@RequiresPermissions("console:zhEquipment:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ZhEquipment zhEquipment)
    {
    	List<ZhEquipment> list = zhEquipmentService.selectZhEquipmentList(zhEquipment);
        ExcelUtil<ZhEquipment> util = new ExcelUtil<ZhEquipment>(ZhEquipment.class);
        return util.exportExcel(list, "zhEquipment");
    }
	
	/**
	 * 新增振汇开放平台 设备接口绑定
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存振汇开放平台 设备接口绑定
	 */
	@RequiresPermissions("console:zhEquipment:add")
	@Log(title = "振汇开放平台 设备接口绑定", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ZhEquipment zhEquipment)
	{
		Calendar calendar = Calendar.getInstance();
		zhEquipment.setCreateTime(calendar.getTime());
		return toAjax(zhEquipmentService.insertZhEquipment(zhEquipment));
	}

	/**
	 * 修改振汇开放平台 设备接口绑定
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		ZhEquipment zhEquipment = zhEquipmentService.selectZhEquipmentById(id);
		mmap.put("zhEquipment", zhEquipment);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存振汇开放平台 设备接口绑定
	 */
	@RequiresPermissions("console:zhEquipment:edit")
	@Log(title = "振汇开放平台 设备接口绑定", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ZhEquipment zhEquipment)
	{		
		return toAjax(zhEquipmentService.updateZhEquipment(zhEquipment));
	}
	
	/**
	 * 删除振汇开放平台 设备接口绑定
	 */
	@RequiresPermissions("console:zhEquipment:remove")
	@Log(title = "振汇开放平台 设备接口绑定", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(zhEquipmentService.deleteZhEquipmentByIds(ids));
	}
	
}
