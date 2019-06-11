package com.ruoyi.console.controller;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.console.domain.ZhFaceimage;
import com.ruoyi.console.domain.ZhUser;
import com.ruoyi.console.domain.ZhUserFaceImages;
import com.ruoyi.console.service.IZhFaceimageService;
import com.ruoyi.console.utils.GetMD5;
import com.ruoyi.console.utils.ImgStrToBase64;
import com.ruoyi.console.utils.ZhEquipmentUtil;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysUser;
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
import com.ruoyi.console.domain.ZhDeviceuser;
import com.ruoyi.console.service.IZhDeviceuserService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 设备人员 信息操作处理
 * 
 * @author bainian
 * @date 2019-06-05
 */
@Controller
@RequestMapping("/console/zhDeviceuser")
public class ZhDeviceuserController extends BaseController
{
    private String prefix = "console/zhDeviceuser";
	
	@Autowired
	private IZhDeviceuserService zhDeviceuserService;
	@Autowired
	private IZhFaceimageService zhFaceimageService;

	@RequiresPermissions("console:zhDeviceuser:view")
	@GetMapping()
	public String zhDeviceuser()
	{
	    return prefix + "/zhDeviceuser";
	}
	
	/**
	 * 查询设备人员列表
	 */
	@RequiresPermissions("console:zhDeviceuser:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ZhDeviceuser zhDeviceuser)
	{
		startPage();
        List<ZhDeviceuser> list = zhDeviceuserService.selectZhDeviceuserList(zhDeviceuser);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出设备人员列表
	 */
	@RequiresPermissions("console:zhDeviceuser:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ZhDeviceuser zhDeviceuser)
    {
    	List<ZhDeviceuser> list = zhDeviceuserService.selectZhDeviceuserList(zhDeviceuser);
        ExcelUtil<ZhDeviceuser> util = new ExcelUtil<ZhDeviceuser>(ZhDeviceuser.class);
        return util.exportExcel(list, "zhDeviceuser");
    }
	
	/**
	 * 新增设备人员
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存设备人员
	 */
	@RequiresPermissions("console:zhDeviceuser:add")
	@Log(title = "设备人员", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ZhDeviceuser zhDeviceuser)
	{
		SysUser user = ShiroUtils.getSysUser();
		String loginName = user.getLoginName();
		Calendar calendar = Calendar.getInstance();
		zhDeviceuser.setCreateTime(calendar.getTime());
		String personNumber = GetMD5.MD5(zhDeviceuser.getName()+zhDeviceuser.getCreateTime());
		zhDeviceuser.setPersonNumber(personNumber);
		zhDeviceuser.setCreateBy(loginName);
		return toAjax(zhDeviceuserService.insertZhDeviceuser(zhDeviceuser));
	}

	/**
	 * 修改设备人员
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		ZhDeviceuser zhDeviceuser = zhDeviceuserService.selectZhDeviceuserById(id);
		mmap.put("zhDeviceuser", zhDeviceuser);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存设备人员
	 */
	@RequiresPermissions("console:zhDeviceuser:edit")
	@Log(title = "设备人员", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ZhDeviceuser zhDeviceuser)
	{		
		return toAjax(zhDeviceuserService.updateZhDeviceuser(zhDeviceuser));
	}
	
	/**
	 * 删除设备人员
	 */
	@RequiresPermissions("console:zhDeviceuser:remove")
	@Log(title = "设备人员", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(zhDeviceuserService.deleteZhDeviceuserByIds(ids));
	}

	/**
	 * 人员同步
	 * @return
	 */
	@GetMapping("/userSync")
	@ResponseBody
	public AjaxResult userSync(HttpServletRequest request) throws ClassCastException, UnknownHostException, UnsupportedEncodingException {
		String id = request.getParameter("ids");
		HttpSession session = request.getSession();
		SysUser user = ShiroUtils.getSysUser();
		String loginName = user.getLoginName();
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
			return getDeviceUserList(jsonObject.get("data").toString(),deviceIp,0,loginName);
		}

		return AjaxResult.error("同步失败 请检查设备网络连接");
	}

	/**
	 *  人员同步防重复
	 * @param token
	 * @param deviceIp
	 * @param startIndex
	 * @param loginName
	 * @return
	 */
	private AjaxResult getDeviceUserList(String token,String deviceIp,int startIndex,String loginName) throws UnsupportedEncodingException {
		String userRes = ZhEquipmentUtil.queryUserList(null,null,startIndex,token,deviceIp);
		JSONObject userJson = JSONObject.parseObject(userRes);
		Object userResCode = userJson.get("code").toString();
		if(userResCode.equals("0")){
			ZhDeviceuser zhDeviceuser = new ZhDeviceuser();
			zhDeviceuser.setCreateBy(loginName);							//根据登录人来区分权限
			List<ZhDeviceuser> zhDeviceuserList = zhDeviceuserService.selectZhDeviceuserList(zhDeviceuser);    //数据库中所有记录
			String userData = userJson.getString("data");							//获取设备人员
			JSONArray jsonArray = JSONArray.parseArray(userData);
			List<ZhDeviceuser> userList = jsonArray.toJavaList(ZhDeviceuser.class);
			forOne:for(ZhDeviceuser zhUser:userList){								//人员上传到数据库
				Calendar calendar = Calendar.getInstance();
				zhUser.setId(null);
				zhUser.setCreateBy(loginName);
				zhUser.setCreateTime(calendar.getTime());
				for(ZhDeviceuser tempUser:zhDeviceuserList){
					if(tempUser.getPersonNumber().equals(zhUser.getPersonNumber())){
						continue forOne;
					}
				}
				zhDeviceuserService.insertZhDeviceuser(zhUser);
				syncFaceImage(token,zhUser.getPersonNumber(),deviceIp);
			}

			forOne:for(ZhDeviceuser tempUser:zhDeviceuserList){					//数据库下发人员
				for(ZhDeviceuser zhUser:userList){
					if(tempUser.getPersonNumber().equals(zhUser.getPersonNumber())){
						continue forOne;
					}
				}
				ZhUser zhUser = new ZhUser();										//	无重复进行设备插入人员操作
				zhUser.setPersonNumber(tempUser.getPersonNumber());
				zhUser.setName(tempUser.getName());
				zhUser.setIcCard(tempUser.getIcCard());
				zhUser.setIdCard(tempUser.getIdCard());
				String addUserRes = ZhEquipmentUtil.addUser(zhUser,token,deviceIp);						//人员插入设备
				JSONObject addUserJson = JSONObject.parseObject(addUserRes);		//判断插入状态
				Object addUserResCode = addUserJson.get("code").toString();
				if(!addUserResCode.equals("0")){									//添加失败
					return AjaxResult.error(addUserJson.get("message").toString());
				}
				syncDeviceFaceImage(token,zhUser.getPersonNumber(),deviceIp);
			}

			if(userList.size()>=10){
				getDeviceUserList(token,deviceIp,startIndex+1,loginName);
			}

			return AjaxResult.success("同步完成");
		}
		return AjaxResult.error("同步失败 请检查设备网络连接");
	}

	/**
	 * 人脸图片同步到数据库（防重复）
	 * @param token
	 * @param personNumber
	 * @param deviceIp
	 */
	private void syncFaceImage(String token,String personNumber,String deviceIp){
		JSONObject json = JSONObject.parseObject(ZhEquipmentUtil.getFaceImage(personNumber,token,deviceIp));
		String data = json.getString("data");
		JSONArray jsonArray = JSONArray.parseArray(data);            //将Array解析JSONArray
		List<ZhUserFaceImages> list = jsonArray.toJavaList(ZhUserFaceImages.class);         //将json解析成java对象集合
		if(list.size() !=0 ){
			forOne:for(int j=0;j<list.size();j++){
				ZhFaceimage zhFaceimage = new ZhFaceimage();
				ZhUserFaceImages tempMap = list.get(j);
				String faceImages = deviceIp+tempMap.getUrl();
				String imgBase64 = ImgStrToBase64.getImgStrToBase64(faceImages,token);
				zhFaceimage.setId(list.get(j).getId());
				zhFaceimage.setPersonNumber(personNumber);
				zhFaceimage.setFaceImage(imgBase64);
				ZhFaceimage zhFaceimage1 = new ZhFaceimage();
				zhFaceimage1.setPersonNumber(personNumber);
				List<ZhFaceimage> zhFaceimageList = zhFaceimageService.selectZhFaceimageList(zhFaceimage1);
				for(ZhFaceimage tempFaceImage:zhFaceimageList){
					if(tempFaceImage.getId().equals(tempFaceImage.getId())){
						continue forOne;
					}
				}
				zhFaceimageService.insertZhFaceimage(zhFaceimage);
			}
		}
	}

	/**
	 * 人脸图片同步到设备（防重复）
	 * @param token
	 * @param personNumber
	 * @param deviceIp
	 */
	private void syncDeviceFaceImage(String token,String personNumber,String deviceIp) throws UnsupportedEncodingException {

		ZhFaceimage zhFaceimage = new ZhFaceimage();
		zhFaceimage.setPersonNumber(personNumber);
		List<ZhFaceimage> faceImageList = zhFaceimageService.selectZhFaceimageList(zhFaceimage);		//查询数据库中存在的人脸信息

		for(ZhFaceimage tempFaceImage:faceImageList){
			String faceImagedata = tempFaceImage.getFaceImage().substring(22);
			faceImagedata = URLEncoder.encode(faceImagedata,"utf-8");
			String checkFaceImageRes = ZhEquipmentUtil.checkFace(faceImagedata,token,deviceIp);
			JSONObject json = JSONObject.parseObject(checkFaceImageRes);
			String checkCode = json.getString("code");
			if(checkCode.equals("0")){
				String addFaceImageRes = ZhEquipmentUtil.addBase64Image(personNumber,faceImagedata,token,deviceIp);
				JSONObject addFaceImageJson = JSONObject.parseObject(addFaceImageRes);
				String addFaceResCode = addFaceImageJson.getString("code");
				if(!addFaceResCode.equals("0")){
					System.out.println(addFaceImageJson.getString("message"));
				}
			}
		}
	}
}
