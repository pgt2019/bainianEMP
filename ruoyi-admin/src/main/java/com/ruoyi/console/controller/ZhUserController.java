package com.ruoyi.console.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.console.utils.GetMD5;
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
import com.ruoyi.console.domain.ZhUser;
import com.ruoyi.console.service.IZhUserService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 人员 信息操作处理
 * 
 * @author bainian
 * @date 2019-06-05
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
	 * 查询人员列表
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
	 * 导出人员列表
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
	 * 新增人员
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存人员
	 */
	@RequiresPermissions("console:zhUser:add")
	@Log(title = "人员", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ZhUser zhUser)
	{		
		return toAjax(zhUserService.insertZhUser(zhUser));
	}

	/**
	 * 修改人员
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		ZhUser zhUser = zhUserService.selectZhUserById(id);
		mmap.put("zhUser", zhUser);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存人员
	 */
	@RequiresPermissions("console:zhUser:edit")
	@Log(title = "人员", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ZhUser zhUser)
	{		
		return toAjax(zhUserService.updateZhUser(zhUser));
	}
	
	/**
	 * 删除人员
	 */
	@RequiresPermissions("console:zhUser:remove")
	@Log(title = "人员", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(zhUserService.deleteZhUserByIds(ids));
	}

	/**
	 * 设备管理器（设备控制  人员管理  在线状态 。。 ）
	 * @return
	 */
	@GetMapping("/userSync")
	public AjaxResult userSync(HttpServletRequest request) throws ClassCastException, UnknownHostException {
		String id = request.getParameter("ids");
		HttpSession session = request.getSession();
		String ids = id.substring(0,id.indexOf(","));                                   //data-id 所选绑定的设备id获取
		String meid = id.substring(id.indexOf(",")+1,id.indexOf(",",id.indexOf(",")+1));         //设备号
		String activationCode = id.substring(id.indexOf(",",id.indexOf(",")+1)+1,id.lastIndexOf(","));         //设备号
		String deviceIp = id.substring(id.lastIndexOf(",")+1);                //设备ip
		session.setAttribute("dataId",ids);
		session.setAttribute("meid",meid);
		session.setAttribute("deviceIp",deviceIp);
		deviceIp = "http://"+deviceIp+":8089";           //设备ip
		String ip = InetAddress.getLocalHost().getHostAddress();                        //获得本地ip
		long timestamp = new Date().getTime()/1000;                                    //时间戳
		String sign = GetMD5.MD5(activationCode + meid + timestamp);          //鉴权md5
		String result = ZhEquipmentUtil.getToken(ip,meid,activationCode,timestamp,sign,deviceIp);//鉴权请求
		JSONObject jsonObject = JSONObject.parseObject(result);
		Object resCode = jsonObject.get("code").toString();
		if(resCode.equals("0")){
			session.setAttribute("token",jsonObject.get("data"));                   //token存入session
			return getDeviceUserList(jsonObject.get("data").toString(),deviceIp,0);
		}

		return AjaxResult.error("同步失败 请检查设备网络连接");
	}

	private AjaxResult getDeviceUserList(String token,String deviceIp,int startIndex){
		String userRes = ZhEquipmentUtil.queryUserList(null,null,startIndex,token,deviceIp);
		JSONObject userJson = JSONObject.parseObject(userRes);
		Object userResCode = userJson.get("code").toString();
		if(userResCode.equals("0")){
			String userData = userJson.getString("data");
			JSONArray jsonArray = JSONArray.parseArray(userData);
			List<ZhUser> userList = jsonArray.toJavaList(ZhUser.class);
			for(ZhUser zhUser:userList){
				zhUserService.insertZhUser(zhUser);
			}
			if(userList.size()>=10){
				getDeviceUserList(token,deviceIp,startIndex+1);
			}
			return AjaxResult.success("同步完成");
		}
		return AjaxResult.error("同步失败 请检查设备网络连接");
	}
}
