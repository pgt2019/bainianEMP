package com.ruoyi.console.controller;

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
import com.ruoyi.console.domain.ZhRecordrollback;
import com.ruoyi.console.service.IZhRecordrollbackService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 识别记录 信息操作处理
 * 
 * @author bainian
 * @date 2019-06-04
 */
@Controller
@RequestMapping("/console/zhRecordrollback")
public class ZhRecordrollbackController extends BaseController
{
    private String prefix = "console/zhRecordrollback";
	
	@Autowired
	private IZhRecordrollbackService zhRecordrollbackService;
	
	@RequiresPermissions("console:zhRecordrollback:view")
	@GetMapping()
	public String zhRecordrollback()
	{
	    return prefix + "/zhRecordrollback";
	}

	@RequiresPermissions("console:zhRecordrollback:view")
	@GetMapping("/view")
	public String zhRecordrollbackView(ModelMap mmap)
	{
		mmap.put("hiddenStatue","1");
		return prefix + "/zhRecordrollback";
	}

	/**
	 * 查询识别记录列表
	 */
	@RequiresPermissions("console:zhRecordrollback:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ZhRecordrollback zhRecordrollback, HttpServletRequest request)
	{
		startPage();
		HttpSession session = request.getSession();
		Object deviceNumber = session.getAttribute("meid");
		if(deviceNumber != null){
			zhRecordrollback.setDeviceNumber(deviceNumber.toString());
		}
        List<ZhRecordrollback> list = zhRecordrollbackService.selectZhRecordrollbackList(zhRecordrollback);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出识别记录列表
	 */
	@RequiresPermissions("console:zhRecordrollback:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ZhRecordrollback zhRecordrollback)
    {
    	List<ZhRecordrollback> list = zhRecordrollbackService.selectZhRecordrollbackList(zhRecordrollback);
        ExcelUtil<ZhRecordrollback> util = new ExcelUtil<ZhRecordrollback>(ZhRecordrollback.class);
        return util.exportExcel(list, "zhRecordrollback");
    }
	
	/**
	 * 新增识别记录
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存识别记录
	 */
	@RequiresPermissions("console:zhRecordrollback:add")
	@Log(title = "识别记录", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ZhRecordrollback zhRecordrollback)
	{		
		return toAjax(zhRecordrollbackService.insertZhRecordrollback(zhRecordrollback));
	}

	/**
	 * 修改识别记录
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		ZhRecordrollback zhRecordrollback = zhRecordrollbackService.selectZhRecordrollbackById(id);
		mmap.put("zhRecordrollback", zhRecordrollback);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存识别记录
	 */
	@RequiresPermissions("console:zhRecordrollback:edit")
	@Log(title = "识别记录", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ZhRecordrollback zhRecordrollback)
	{		
		return toAjax(zhRecordrollbackService.updateZhRecordrollback(zhRecordrollback));
	}
	
	/**
	 * 删除识别记录
	 */
	@RequiresPermissions("console:zhRecordrollback:remove")
	@Log(title = "识别记录", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(zhRecordrollbackService.deleteZhRecordrollbackByIds(ids));
	}
	
}
