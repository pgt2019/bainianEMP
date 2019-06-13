package com.ruoyi.console.controller;

import java.io.File;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.console.domain.*;
import com.ruoyi.console.service.IZhRecordrollbackService;
import com.ruoyi.console.utils.*;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysUser;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.console.service.IZhEquipmentService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 设备ZH 人员管理（增删改 人脸（增删））  设备管理
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
    @Autowired
    private IZhRecordrollbackService zhRecordrollbackService;
	
	@RequiresPermissions("console:zhEquipment:view")
	@GetMapping()
	public String zhEquipment()
	{
	    return prefix + "/zhEquipment";
	}


    /**
     * 设备识别回调接口
     */
    @PostMapping(value = "/callBackRecord")
    @ResponseBody
    public void callBackRecord(HttpServletRequest request) throws ParseException {
        ZhRecordrollback zhRecordrollback = new ZhRecordrollback();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String deviceNumber=request.getParameter("deviceNumber");        //设备编号
        String faceImage=request.getParameter("faceImage");              //人脸照片
        String name=request.getParameter("name");                        //人员名称
        String recognizeTime = request.getParameter("recognizeTime");    //识别时间
        if(recognizeTime.length()>10){
            recognizeTime = recognizeTime.substring(0,10);
        }
        String personNumber = request.getParameter("personNumber");      //人员编号
        String similarity = request.getParameter("similarity");          //相似度
        String icCard = request.getParameter("icCard");                  //IC卡
        String idCard = request.getParameter("idCard");                  //id卡
        String recognizedType = request.getParameter("recognizedType");  //识别类型
        String extendInfo = request.getParameter("extendInfo");          //扩展字段

        String loginName = "";
        ZhEquipment zhEquipment = new ZhEquipment();
        zhEquipment.setMeid(deviceNumber);
        List<ZhEquipment> zhEquipmentList = zhEquipmentService.selectZhEquipmentList(zhEquipment);
        for(ZhEquipment zhEquipment1:zhEquipmentList){
            loginName = zhEquipment1.getCreateBy();
        }
        zhRecordrollback.setDeviceNumber(deviceNumber);
        zhRecordrollback.setFaceImage(faceImage);
        zhRecordrollback.setName(name);
        zhRecordrollback.setPersonNumber(personNumber);
        zhRecordrollback.setSimilarity(similarity);
        zhRecordrollback.setIcCard(icCard);
        zhRecordrollback.setIdCard(idCard);
        zhRecordrollback.setRecognizedType(recognizedType);
        zhRecordrollback.setExtendInfo(extendInfo);
        zhRecordrollback.setRecognizeTime(format.parse(TimestampToDateStr.timeStamp2Date(recognizeTime,"yyyy-MM-dd HH:mm:ss")));
        zhRecordrollback.setCreateBy(loginName);
        zhRecordrollbackService.insertZhRecordrollback(zhRecordrollback);

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
     * 检查在线状态
     * @return
     */
	@GetMapping("/onlineStatus")
    @ResponseBody
    public AjaxResult onlineStatus(HttpServletRequest request){
	    String ip = request.getParameter("deviceIp");
	    boolean isOnline = HttpRequest.isConnect(ip);
	    if(isOnline){
            return AjaxResult.success("当前设备在线");
        }
	    return AjaxResult.error("设备不在线");
    }

	/**
	 * 设备管理器（设备控制  人员管理  在线状态 。。 ）
	 * @return
	 */
	@GetMapping("/manage/{id}")
	public String myEqManage(@PathVariable("id") String id,HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            String ids = id.substring(0,id.indexOf(","));                                   //data-id 所选绑定的设备id获取
            String meid = id.substring(id.indexOf(",")+1,id.indexOf(",",id.indexOf(",")+1));         //设备号
            String activationCode = id.substring(id.indexOf(",",id.indexOf(",")+1)+1,id.lastIndexOf(","));         //设备号
            String deviceIp = id.substring(id.lastIndexOf(",")+1);                //设备ip
            session.setAttribute("dataId",ids);
            session.setAttribute("meid",meid);
            session.setAttribute("deviceIp",deviceIp);
            deviceIp = "http://"+deviceIp+":8089";           //设备ip
            String ip = null;                        //获得本地ip
            ip = InetAddress.getLocalHost().getHostAddress();
            long timestamp = new Date().getTime()/1000;                                    //时间戳
            String sign = GetMD5.MD5(activationCode + meid + timestamp);          //鉴权md5
            String result = ZhEquipmentUtil.getToken(ip,meid,activationCode,timestamp,sign,deviceIp);//鉴权请求
            JSONObject jsonObject = JSONObject.parseObject(result);
            Object resCode = jsonObject.get("code").toString();
            if(resCode.equals("0")){
                session.setAttribute("token",jsonObject.get("data"));                   //token存入session
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


	    return prefix + "/myEquipment";
	}

    /**
     * 跳转设备识别记录
     */
    @RequiresPermissions("console:zhEquipment:list")
    @RequestMapping("/toRecord")
    public String toRecord(){
        return prefix + "/RecordList";
    }

    /**
     *  识别记录
     */
    @RequiresPermissions("console:zhEquipment:list")
    @PostMapping("/getRecord")
    @ResponseBody
    public TableDataInfo getRecord(ZhRecord zhRecord,HttpServletRequest request)
    {

        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置格式
        HttpSession session = request.getSession();
        Object token = session.getAttribute("token");                 //获取token
        String deviceIp = "http://"+session.getAttribute("deviceIp")+":8089";           //设备ip

        String startTime = null;            //时间条件查询
        String endTime = null;
        String queryLength = null;          //查询返回条数 默认为10条
        String personNumber = null;         //查询某个人的识别记录

        if(zhRecord.getParams().get("beginTime") != null && !zhRecord.getParams().get("beginTime").toString().equals("")){                  //转换成时间戳
            startTime = zhRecord.getParams().get("beginTime").toString();
            startTime += " 00:00:00";
            startTime = TimestampToDateStr.date2TimeStamp(startTime,"yyyy-MM-dd HH:mm:ss");
        }
        if(zhRecord.getParams().get("endTime") != null && !zhRecord.getParams().get("endTime").toString().equals("")){
            endTime = zhRecord.getParams().get("endTime").toString();
            endTime += " 23:59:59";
            endTime = TimestampToDateStr.date2TimeStamp(endTime,"yyyy-MM-dd HH:mm:ss");
        }
        if(zhRecord.getParams().get("queryLength") != null && !zhRecord.getParams().get("queryLength").toString().equals("")){
            queryLength = zhRecord.getParams().get("queryLength").toString();
        }
        if(zhRecord.getRecognizePersonNumber() != null && !zhRecord.getRecognizePersonNumber().equals("")){
            personNumber = zhRecord.getRecognizePersonNumber();
        }

        String result = ZhEquipmentUtil.getRecord(personNumber,startTime,endTime,null,queryLength,token.toString(),deviceIp);
        JSONObject json = JSONObject.parseObject(result);
        String data = json.getString("data");
        JSONArray jsonArray = JSONArray.parseArray(data);            //将Array解析JSONArray
        List<ZhRecord> list = jsonArray.toJavaList(ZhRecord.class);         //将json解析成java对象集合
        if(list.size() !=0 ){
            for(int j=0;j<list.size();j++){
                ZhRecord tempMap = list.get(j);
                Long stamptime = Long.parseLong(tempMap.getRecognizeTime());
                String timeText=format.format(stamptime);                                //获得带格式的字符串
                list.get(j).setRecognizeTime(timeText);
                if(tempMap.getRecognizeType().equals("0")){
                    list.get(j).setRecognizeType("人脸识别");
                }
                if(tempMap.getRecognizeType().equals("1")){
                    list.get(j).setRecognizeType("IC卡");
                }
                if(tempMap.getRecognizeType().equals("2")){
                    list.get(j).setRecognizeType("身份证");
                }
            }
        }
        return getDataTable(list);
    }



    /**
     * 跳转设备配置信息
     */
    @RequiresPermissions("console:zhEquipment:list")
    @RequestMapping("/toDeviceView")
    public String toDeviceView(HttpServletRequest request,ModelMap mmap){
        HttpSession session = request.getSession();
        Object token = session.getAttribute("token");                 //获取token
        Object meid = session.getAttribute("meid");                   //当前操作的设备
        String deviceIp = "http://"+session.getAttribute("deviceIp")+":8089";           //设备ip
        String result = ZhEquipmentUtil.getDeviceOptions(meid.toString(),token.toString(),deviceIp);
        JSONObject jsonObject = JSONObject.parseObject(result);
        Object resCode = jsonObject.get("code").toString();
        if(resCode.equals("0")){
            ZhDeviceOption data =jsonObject.getObject("data",ZhDeviceOption.class);
            String startFigure = deviceIp+data.getStartFigure();
            startFigure = ImgStrToBase64.getImgStrToBase64(startFigure,token.toString());
            data.setStartFigure(startFigure);

            String logo = deviceIp+data.getLogo();
            logo = ImgStrToBase64.getImgStrToBase64(logo,token.toString());
            data.setLogo(logo);

            mmap.put("ZhDeviceOption",data);
            mmap.put("deviceNumber",meid);
        }

        return prefix + "/deviceOption_show";
    }

    String logoUploadData = null;
    String startLogoUploadData = null;

    /**
     * 配置设备信息  保存
     */
    @RequiresPermissions("console:zhEquipment:list")
    @PostMapping(value = "/deviceOption/save" , consumes = "application/json")
    @ResponseBody
    public AjaxResult saveDeviceOption(@RequestBody JSONObject deviceOption, HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        Object token = session.getAttribute("token");                 //获取token
        String deviceIp = "http://"+session.getAttribute("deviceIp")+":8089";           //设备ip

        String deviceNumber = deviceOption.get("deviceNumber").toString();
        String title = deviceOption.get("title").toString();
        String identificationType = deviceOption.get("identificationType").toString();
        String threshold = deviceOption.get("threshold").toString();
        String callBackAddress = deviceOption.get("callBackAddress").toString();
        String saveLocalRecordTime = deviceOption.get("saveLocalRecordTime").toString();
        String isOutDoor = deviceOption.get("isOutDoor").toString();
        String isOpenLiving = deviceOption.get("isOpenLiving").toString();
        String verifyIdCard = deviceOption.get("verifyIdCard").toString();
        String voiceHint = deviceOption.get("voiceHint").toString();

        String result = ZhEquipmentUtil.updateDeviceOption(token.toString(),deviceNumber,title,logoUploadData,startLogoUploadData,threshold,identificationType,verifyIdCard,voiceHint,isOutDoor,isOpenLiving,null,null,callBackAddress,saveLocalRecordTime,null,deviceIp);
        JSONObject jsonObject = JSONObject.parseObject(result);
        Object resCode = jsonObject.get("code").toString();
        if(resCode.equals("0")){
            return AjaxResult.success(jsonObject.get("message").toString());
        }
        return AjaxResult.error(jsonObject.get("message").toString());
    }


    /**
     *  设备logo上传
     */
    @RequiresPermissions("console:zhEquipment:list")
    @PostMapping("/deviceOption/logoUpload")
    @ResponseBody
    public AjaxResult logoUpload(MultipartFile uploadImg,String deviceNumber,HttpServletRequest request)
    {
        if (!uploadImg.isEmpty()) {
            try {
                HttpSession session = request.getSession();
                Object token = session.getAttribute("token");                           //获取token
                Object personNumber = session.getAttribute("personNumber");
                String deviceIp = "http://"+session.getAttribute("deviceIp")+":8089";           //设备ip
                BASE64Encoder encoder = new BASE64Encoder();
                String data = encoder.encode(uploadImg.getBytes());                        // 通过base64来转化图片
                data = URLEncoder.encode(data,"utf-8");
                logoUploadData = data;
                return AjaxResult.success("提交成功");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return AjaxResult.error("网络错误 照片上传失败");
    }

    /**
     *  设备startlogo上传
     */
    @RequiresPermissions("console:zhEquipment:list")
    @PostMapping("/deviceOption/startFigureUpload")
    @ResponseBody
    public AjaxResult startFigureUpload(MultipartFile uploadImg,String deviceNumber,HttpServletRequest request)
    {
        if (!uploadImg.isEmpty()) {
            try {
                // 获取文件名
//                String fileName = uploadImg.getOriginalFilename();
//                // 获取文件后缀
//                String prefix=fileName.substring(fileName.lastIndexOf("."));
//                // 用uuid作为文件名，防止生成的临时文件重复
//                startLogoUploadFile = File.createTempFile(fileName, prefix);
//                // MultipartFile to File
//                uploadImg.transferTo(startLogoUploadFile);
//                //程序结束时，删除临时文件
                return AjaxResult.success("提交成功");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return AjaxResult.error("网络错误 照片上传失败");
    }

    /**
     * 临时文件删除
     * @param files
     */
    private void deleteFile(File... files) {
        for (File file : files) {
            if (file.exists()) {
                file.delete();
            }
        }
    }


    /**
     *  设备人员管理 添加  页面跳转
     */
    @GetMapping("/addUser")
    public String addUser()
    {
        return prefix + "/addUser";
    }

    /**
     * 设备人员管理 添加
     */
    @RequiresPermissions("console:zhEquipment:add")
    @Log(title = "设备增加人员", businessType = BusinessType.INSERT)
    @PostMapping("/addUser")
    @ResponseBody
    public AjaxResult addUserSave(ZhUser zhUser,HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        Object token = session.getAttribute("token");                 //获取token
        String deviceIp = "http://"+session.getAttribute("deviceIp")+":8089";           //设备ip

        String result = ZhEquipmentUtil.addUser(zhUser,token.toString(),deviceIp);
        JSONObject jsonObject = JSONObject.parseObject(result);
        Object resCode = jsonObject.get("code").toString();
        if(resCode.equals("0")){
            return AjaxResult.success(jsonObject.get("message").toString());
        }
        return AjaxResult.error(jsonObject.get("message").toString());
    }


    /**
	 *  设备列表
	 */
	@RequiresPermissions("console:zhEquipment:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ZhEquipment zhEquipment)
	{
		startPage();
        SysUser user = ShiroUtils.getSysUser();
        String loginName = user.getLoginName();
        zhEquipment.setCreateBy(loginName);
        List<ZhEquipment> list = zhEquipmentService.selectZhEquipmentList(zhEquipment);
		return getDataTable(list);
	}

    /**
     *  人员人脸库 页面
     */
    @RequiresPermissions("console:zhEquipment:list")
    @GetMapping("/faceImageManage/{personNumber}")
    public String faceImageManage(@PathVariable("personNumber") String personNumber, HttpServletRequest request)
    {
    	HttpSession session = request.getSession();
    	session.setAttribute("personNumber",personNumber);
        return prefix + "/faceImageManage";
    }

	/**
	 *  人员人脸库 添加 页面
	 */
	@RequiresPermissions("console:zhEquipment:list")
	@GetMapping("/faceImageManage/add")
	public String addfaceImageManage(HttpServletRequest request)
	{
		return prefix + "/addfaceImageManage";
	}

    /**
     *  人员人脸库 添加
     */
    @RequiresPermissions("console:zhEquipment:list")
    @PostMapping("/faceImageManage/add")
    @ResponseBody
    public AjaxResult addFace(MultipartFile faceImage, HttpServletRequest request)
    {
        if (!faceImage.isEmpty()) {
            try {
                HttpSession session = request.getSession();
                Object token = session.getAttribute("token");                           //获取token
                Object personNumber = session.getAttribute("personNumber");
                String deviceIp = "http://"+session.getAttribute("deviceIp")+":8089";           //设备ip

                BASE64Encoder encoder = new BASE64Encoder();
                String data = encoder.encode(faceImage.getBytes());                        // 通过base64来转化图片
                data = URLEncoder.encode(data,"utf-8");
                String checkresult = ZhEquipmentUtil.checkFace(data,token.toString(),deviceIp);   //检查人脸照片是否符合
                JSONObject checkResultJson = JSONObject.parseObject(checkresult);
                Object resCode = checkResultJson.get("code").toString();
                if(resCode.equals("0")){
                    String addResult = ZhEquipmentUtil.addBase64Image(personNumber.toString(),data,token.toString(),deviceIp);   //添加人脸照片操作
                    JSONObject addResultJson = JSONObject.parseObject(addResult);
                    Object addResCode = addResultJson.get("code").toString();
                    if(addResCode.equals("0")){
                        return AjaxResult.success(addResultJson.get("message").toString());
                    }
                    return AjaxResult.error(addResultJson.get("message").toString());
                }
                return AjaxResult.error(checkResultJson.get("message").toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return AjaxResult.error("网络错误 照片上传失败");
    }

    /**
     *  人员人脸库 删除
     */
    @RequiresPermissions("console:zhEquipment:list")
    @PostMapping("/faceImageManage/remove/{id}")
    @ResponseBody
    public AjaxResult removeFace(@PathVariable("id") String id, HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        Object token = session.getAttribute("token");                           //获取token
        Object personNumber = session.getAttribute("personNumber");
        String deviceIp = "http://"+session.getAttribute("deviceIp")+":8089";           //设备ip

        String result = ZhEquipmentUtil.deleteImage(personNumber.toString(),id,token.toString(),deviceIp);   //删除人脸照片操作
        JSONObject resultJson = JSONObject.parseObject(result);
        Object resCode = resultJson.get("code").toString();
        if(resCode.equals("0")){
            return AjaxResult.success(resultJson.get("message").toString());
        }
        return AjaxResult.error(resultJson.get("message").toString());
    }

    /**
     *  人员人脸库 查询
     */
    @RequiresPermissions("console:zhEquipment:list")
    @PostMapping("/faceImageManage")
	@ResponseBody
    public TableDataInfo getFaceImageManage(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        Object token = session.getAttribute("token");                                    //获取token
		Object personNumber = session.getAttribute("personNumber");
        String deviceIp = "http://"+session.getAttribute("deviceIp")+":8089";           //设备ip

		JSONObject json = JSONObject.parseObject(ZhEquipmentUtil.getFaceImage(personNumber.toString(),token.toString(),deviceIp));

		String data = json.getString("data");
		JSONArray jsonArray = JSONArray.parseArray(data);            //将Array解析JSONArray
		List<ZhUserFaceImages> list = jsonArray.toJavaList(ZhUserFaceImages.class);         //将json解析成java对象集合
        if(list.size() !=0 ){
            for(int j=0;j<list.size();j++){
                ZhUserFaceImages tempMap = list.get(j);
                String faceImages = deviceIp+tempMap.getUrl();
                String ImgBase64 = ImgStrToBase64.getImgStrToBase64(faceImages,token.toString());
                faceImages = ("<a onclick='imgBigShow(\"img"+tempMap.getId()+"\")'> <img id='img"+tempMap.getId()+"' src='"+ImgBase64+"' style='width:100px;height:100px;'/> </a>");
                list.get(j).setUrl(faceImages);
            }
        }
        return getDataTable(list);
    }

    /**
     * 跳转成员列表
     */
    @RequiresPermissions("console:zhEquipment:list")
    @RequestMapping("/toUserView")
    public String toUserView(String data,HttpServletRequest request){
        return prefix + "/userList";
    }

    /**
     * 设备成员删除
     */
    @RequiresPermissions("console:zhEquipment:remove")
    @PostMapping( "zhUser/remove")
    @ResponseBody
    public AjaxResult zhUserRemove(String ids,HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        Object token = session.getAttribute("token");                 //获取token
        String deviceIp = "http://"+session.getAttribute("deviceIp")+":8089";           //设备ip

        String resstr = ZhEquipmentUtil.deleteUser(ids,token.toString(),deviceIp);
        JSONObject jsonObject = JSONObject.parseObject(resstr);
        Object resCode = jsonObject.get("code").toString();
        if(resCode.equals("0")){
            return AjaxResult.success(jsonObject.get("message").toString());
        }
        return AjaxResult.error(jsonObject.get("message").toString());
    }

    /**
     * 设备成员修改 跳转
     */
    @GetMapping("zhUser/update/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap,HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        Object token = session.getAttribute("token");                       //获取token
        String deviceIp = "http://"+session.getAttribute("deviceIp")+":8089";           //设备ip

        String res = ZhEquipmentUtil.queryUserList(id,null,null,token.toString(),deviceIp);        //数据回显
        JSONObject resJson = JSONObject.parseObject(res);                       //String 转 JSONObject
        JSONArray jsonArray = JSONArray.parseArray(resJson.get("data").toString()); //data String  转  JSONArray   用于解析成ZhUser对象
        List<ZhUser> list = jsonArray.toJavaList(ZhUser.class);
        ZhUser zhUser = new ZhUser();
        if(list.size() > 0){
            zhUser = list.get(0);
        }
        mmap.put("zhUser", zhUser);
        return prefix + "/editUser";
    }

    /**
     * 设备人员  修改
     */
    @RequiresPermissions("console:zhEquipment:edit")
    @PostMapping("zhUser/update")
    @ResponseBody
    public AjaxResult editSave(ZhUser zhUser,HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        Object token = session.getAttribute("token");                       //获取token
        String deviceIp = "http://"+session.getAttribute("deviceIp")+":8089";           //设备ip

        String resstr = ZhEquipmentUtil.updateUser(zhUser,token.toString(),deviceIp);
        JSONObject jsonObject = JSONObject.parseObject(resstr);
        Object resCode = jsonObject.get("code").toString();
        if(resCode.equals("0")){
            return AjaxResult.success(jsonObject.get("message").toString());
        }
        return AjaxResult.error(jsonObject.get("message").toString());
    }

    /**
     *   设备获取成员
     */
    @RequiresPermissions("console:zhEquipment:list")
    @PostMapping("/getUser")
    @ResponseBody
    public TableDataInfo getUser(ZhUser zhUser, HttpServletRequest request) throws UnknownHostException {
    	HttpSession session = request.getSession();
    	Object token = session.getAttribute("token");                 //获取token
        String deviceIp = "http://"+session.getAttribute("deviceIp")+":8089";           //设备ip
        String result = ZhEquipmentUtil.queryUserList(zhUser.getPersonNumber(),zhUser.getKeyWord(),null,token.toString(),deviceIp);
        JSONObject json = JSONObject.parseObject(result);
        String data = json.getString("data");
        JSONArray jsonArray = JSONArray.parseArray(data);
        List<ZhUser> list = jsonArray.toJavaList(ZhUser.class);
        for(int i = 0;i < list.size();i++){
            JSONArray tempJson = JSONArray.parseArray(list.get(i).getFaceImages());
            if(tempJson.size() !=0 ){
                Map tempMap = (Map) tempJson.get(0);
                String faceImages = deviceIp+tempMap.get("url");
                String ImgBase64 = ImgStrToBase64.getImgStrToBase64(faceImages,token.toString());
                list.get(i).setFaceImages("<a onclick='imgBigShow(\"img"+tempMap.get("id")+"\")'> <img id='img"+tempMap.get("id")+"' src='"+ImgBase64+"' style='width:100px;height:100px;'/> </a>");
            }else{
                list.get(i).setFaceImages("");
            }
        }
        return getDataTable(list);
    }

    /**
     *   设备鉴权操作
     */
    public  void getToken(ZhEquipment zhEquipment,HttpServletRequest request) throws UnknownHostException {
        List<ZhEquipment> list = zhEquipmentService.selectZhEquipmentList(zhEquipment);
        HttpSession session = request.getSession();   //获取session
        session.setAttribute("zhEqObject",zhEquipment);   //将对象存到session  方便后续操作判断
        /*
           鉴权属性
         */
        String ip = InetAddress.getLocalHost().getHostAddress();
        String regkey = list.get(0).getRegkey();
        String meid = list.get(0).getMeid();
        long timestamp = System.currentTimeMillis();
        String sign = GetMD5.MD5(regkey + meid + timestamp);
        String param = "ip="+ip
                +"&activationCode="+regkey
                +"&deviceNumber="+meid
                +"&timestamp="+timestamp
                +"&sign="+sign;
        String result =HttpRequest.sendPost("http://192.168.1.199:8089/auth", param,null);
		JSONObject json = JSONObject.parseObject(result);
		String code = json.getString("code");
		Object token = json.get("data");
		/*
			获取成功  将token存入session
		 */
        if(code.equals("0")){
        	session.setAttribute("token",token);
		}

    }

	
	/**
	 * 导出设备绑定信息
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
	 * 新增设备绑定页面跳转
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增设备绑定
	 */
	@RequiresPermissions("console:zhEquipment:add")
	@Log(title = "绑定设备", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ZhEquipment zhEquipment)
	{
		Calendar calendar = Calendar.getInstance();
        SysUser user = ShiroUtils.getSysUser();
        String loginName = user.getLoginName();
		zhEquipment.setCreateTime(calendar.getTime());
		zhEquipment.setCreateBy(loginName);
		return toAjax(zhEquipmentService.insertZhEquipment(zhEquipment));
	}

	/**
	 * 设备绑定信息修改页面跳转
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		ZhEquipment zhEquipment = zhEquipmentService.selectZhEquipmentById(id);
		mmap.put("zhEquipment", zhEquipment);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改设备绑定信息
	 */
	@RequiresPermissions("console:zhEquipment:edit")
	@Log(title = "设备绑定信息修改", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ZhEquipment zhEquipment)
	{		
		return toAjax(zhEquipmentService.updateZhEquipment(zhEquipment));
	}
	
	/**
	 * 删除设备绑定信息
	 */
	@RequiresPermissions("console:zhEquipment:remove")
	@Log(title = "删除设备绑定信息", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(zhEquipmentService.deleteZhEquipmentByIds(ids));
	}
	
}
