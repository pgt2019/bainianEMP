package com.ruoyi.web.controller.console;

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
import com.ruoyi.console.domain.ZhUser;
import com.ruoyi.console.service.IZhUserService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 振汇开放平台api  人员 信息操作处理
 * 
 * @author bainian
 * @date 2019-05-10
 */
@Controller
@RequestMapping("/console/zhUser")
public class ZhUserController extends BaseController
{
    private String prefix = "console/zhUser";
	
	@Autowired
	private IZhUserService zhUserService;
	
	@RequiresPermissions("console:zhUser:view")
	@GetMapping()
	public String zhUser()
	{
	    return prefix + "/zhUser";
	}
	
	/**
	 * 查询振汇开放平台api  人员列表
	 */
	@RequiresPermissions("console:zhUser:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ZhUser zhUser)
	{
		startPage();
        List<ZhUser> list = zhUserService.selectZhUserList(zhUser);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出振汇开放平台api  人员列表
	 */
	@RequiresPermissions("console:zhUser:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ZhUser zhUser)
    {
    	List<ZhUser> list = zhUserService.selectZhUserList(zhUser);
        ExcelUtil<ZhUser> util = new ExcelUtil<ZhUser>(ZhUser.class);
        return util.exportExcel(list, "zhUser");
    }
	
	/**
	 * 新增振汇开放平台api  人员
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存振汇开放平台api  人员
	 */
	@RequiresPermissions("console:zhUser:add")
	@Log(title = "振汇开放平台api  人员", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ZhUser zhUser)
	{		
		return toAjax(zhUserService.insertZhUser(zhUser));
	}

	/**
	 * 修改振汇开放平台api  人员
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		ZhUser zhUser = zhUserService.selectZhUserById(id);
		mmap.put("zhUser", zhUser);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存振汇开放平台api  人员
	 */
	@RequiresPermissions("console:zhUser:edit")
	@Log(title = "振汇开放平台api  人员", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ZhUser zhUser)
	{		
		return toAjax(zhUserService.updateZhUser(zhUser));
	}
	
	/**
	 * 删除振汇开放平台api  人员
	 */
	@RequiresPermissions("console:zhUser:remove")
	@Log(title = "振汇开放平台api  人员", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(zhUserService.deleteZhUserByIds(ids));
	}
	
}
