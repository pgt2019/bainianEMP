package com.ruoyi.console.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.console.domain.*;
import com.ruoyi.console.service.IZhDeviceuserService;
import com.ruoyi.console.service.IZhEquipmentService;
import com.ruoyi.console.service.IZhFaceimageService;
import com.ruoyi.console.service.IZhUserDeviceService;
import com.ruoyi.console.utils.GetMD5;
import com.ruoyi.console.utils.GetZhToken;
import com.ruoyi.console.utils.ZhOnlineApi;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysUser;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  在线api管理设备Controller
 */
@Controller
@RequestMapping("/console/zhOnlineDevice")
public class ZhOnlineDeviceController extends BaseController {

    @Autowired
    private IZhEquipmentService zhEquipmentService;
    @Autowired
    private IZhUserDeviceService zhUserDeviceService;
    @Autowired
    private IZhDeviceuserService zhDeviceuserService;
    @Autowired
    private IZhFaceimageService faceimageService;

//    private String prefix = "console/zhOnlineDevice";
    private String prefix = "console/zhOnlineDevice";
    GetZhToken getZhToken = GetZhToken.getGetZhToken();

    /**
     * 设备管理跳转
     * @return
     */
    @RequiresPermissions("console:zhOnlineDevice")
    @RequestMapping("/deviceView")
    public String deviceView(){
        return prefix + "/deviceList";
    }

    /**
     * 设备添加页面跳转
     * @return
     */
    @RequiresPermissions("console:zhOnlineDevice")
    @RequestMapping("/deviceAddView")
    public String deviceAddView(){
        return prefix + "/deviceAddView";
    }

    /**
     * 人员添加页面跳转
     * @return
     */
    @RequiresPermissions("console:zhOnlineDevice")
    @RequestMapping("/personAddView")
    public String personAddView(){
        return prefix + "/personAddView";
    }

    /**
     * 人员管理跳转
     * @return
     */
    @RequiresPermissions("console:zhOnlineDevice")
    @RequestMapping("/personView")
    public String personView(){
        return prefix + "/personList";
    }

    /**
     * 人员编辑跳转
     * @return
     */
    @RequiresPermissions("console:zhOnlineDevice")
    @GetMapping("/personUpdateView/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap)
    {
        ZhDeviceuser zhDeviceuser = zhDeviceuserService.selectZhDeviceuserById(id);
        mmap.put("zhDeviceuser", zhDeviceuser);
        return prefix + "/personUpdateView";
    }



    /**
     * 设备列表
     * @param zhEquipment
     * @return
     */
    @RequiresPermissions("console:zhOnlineDevice")
    @RequestMapping("/deviceList")
    @ResponseBody
    public TableDataInfo deviceList(ZhEquipment zhEquipment){
        startPage();
        SysUser user = ShiroUtils.getSysUser();
        String loginName = user.getLoginName();
        zhEquipment.setCreateBy(loginName);
        List<ZhEquipment> list = zhEquipmentService.selectZhEquipmentList(zhEquipment);
        return getDataTable(list);
    }


    /**
     * 查询绑定的设备
     * @param zhEquipment
     * @return
     */
    @RequiresPermissions("console:zhOnlineDevice")
    @RequestMapping("/queryDevice")
    @ResponseBody
    public AjaxResult queryDevice(ZhEquipment zhEquipment,String personNumber,ModelMap mmap){
        SysUser user = ShiroUtils.getSysUser();
        String loginName = user.getLoginName();
        zhEquipment.setCreateBy(loginName);
        List<ZhEquipment> list = zhEquipmentService.selectZhEquipmentList(zhEquipment);
        ZhUserDevice zhUserDevice = new ZhUserDevice();
        zhUserDevice.setPersonNumber(personNumber);
        List<ZhUserDevice> zhUserDeviceList = zhUserDeviceService.selectZhUserDeviceList(zhUserDevice);
        Map map = new HashMap();
        map.put("zhEquipmentList",list);
        map.put("zhUserDeviceList",zhUserDeviceList);
        mmap.put("zhEqList",list);
        return AjaxResult.success(map);
    }




    /**
     * 设备添加
     * @param zhEquipment
     * @return
     */
    @RequiresPermissions("console:zhOnlineDevice")
    @RequestMapping("/deviceAdd")
    @ResponseBody
    public AjaxResult deviceAdd(ZhEquipment zhEquipment){
        return insertDevice(zhEquipment);
    }


    /**
     * 设备删除
     * @return
     */
    @RequiresPermissions("console:zhOnlineDevice")
    @RequestMapping("/personDel")
    @ResponseBody
    public AjaxResult personDel(String ids){
        return resMes(deletePerson(ids));
    }


    /**
     * 人员添加
     * @return
     */
    @RequiresPermissions("console:zhOnlineDevice")
    @RequestMapping("/personAdd")
    @ResponseBody
    public AjaxResult personAdd(ZhDeviceuser zhDeviceuser,String name,String icCard,String idCard ,MultipartFile file,String deviceNumber){
        zhDeviceuser.setName(name);
        if(icCard.length()>4){
            zhDeviceuser.setIcCard(icCard);
        }else{
            zhDeviceuser.setIcCard(null);
        }
        if(idCard.length()>4){
            zhDeviceuser.setIdCard(idCard);
        }else{
            zhDeviceuser.setIdCard(null);
        }
        try {
            if(!file.isEmpty()){
                BASE64Encoder encoder = new BASE64Encoder();// 通过base64来转化图片
                String data = encoder.encode(file.getBytes());
                return insertDeviceUser(zhDeviceuser,data,deviceNumber);
            }else{
                return insertDeviceUser(zhDeviceuser,null,deviceNumber);
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return AjaxResult.error("添加失败 检查设备联网状态");
    }

    /**
     * 人员编辑
     * @return
     */
    @RequiresPermissions("console:zhOnlineDevice")
    @RequestMapping("/personUpdate")
    @ResponseBody
    public AjaxResult personUpdate(ZhDeviceuser zhDeviceuser,String id,String name,String icCard,String idCard ,String deviceNumber,String personNumber){
        zhDeviceuser.setId(Integer.parseInt(id));
        zhDeviceuser.setName(name);
        zhDeviceuser.setPersonNumber(personNumber);
        if(icCard.length()>4){
            zhDeviceuser.setIcCard(icCard);
        }else{
            zhDeviceuser.setIcCard(null);
        }
        if(idCard.length()>4){
            zhDeviceuser.setIdCard(idCard);
        }else{
            zhDeviceuser.setIdCard(null);
        }
        return updateDeviceUser(zhDeviceuser,null,deviceNumber);

    }

    /**
     * 人员列表
     * @return
     */
    @RequiresPermissions("console:zhOnlineDevice")
    @RequestMapping("/personList")
    @ResponseBody
    public TableDataInfo personList(ZhDeviceuser zhDeviceuser){
        startPage();
        SysUser user = ShiroUtils.getSysUser();
        String loginName = user.getLoginName();
        zhDeviceuser.setCreateBy(loginName);
        List<ZhDeviceuser> list = zhDeviceuserService.selectZhDeviceuserList(zhDeviceuser);
        for(ZhDeviceuser deviceuser:list){
            ZhFaceimage zhFaceimage = new ZhFaceimage();
            zhFaceimage.setPersonNumber(deviceuser.getPersonNumber());
            List<ZhFaceimage> faceList = faceimageService.selectZhFaceimageList(zhFaceimage);
            if(faceList.size()>0){
                deviceuser.setFaceImage(faceList.get(0).getFaceImage());
            }
        }
        return getDataTable(list);
    }

    @RequiresPermissions("console:zhOnlineDevice")
    @RequestMapping("/unauthUserList/{deviceNumber}")
    @ResponseBody
    public TableDataInfo unauthUserList(@PathVariable("deviceNumber") String deviceNumber){
        startPage();
        SysUser user = ShiroUtils.getSysUser();
        String loginName = user.getLoginName();
        List<ZhDeviceuser> list = zhDeviceuserService.selectunAuthUser(deviceNumber,loginName);
        return getDataTable(list);
    }

    /**
     * 设备授权用户
     * @return
     */
    @RequiresPermissions("console:zhOnlineDevice")
    @GetMapping("/authUserView/{deviceNumber}")
    public String authUserView(@PathVariable("deviceNumber") String deviceNumber, ModelMap mmap)
    {
        deviceNumber = deviceNumber.substring(1,deviceNumber.length()-1);
//        List<ZhDeviceuser> zhDeviceuserList = zhDeviceuserService.selectunAuthUser(deviceNumber);
        mmap.put("deviceNumber", deviceNumber);
        return prefix + "/authUserView";
    }

    /*  页面控制 ↑  实现逻辑 ↓ */


    /**
     *  通用解析json 返回message
     * @param res
     * @return
     */
    private AjaxResult resMes(String res){
        JSONObject jsonObject = JSONObject.parseObject(res);
        Object resCode = jsonObject.get("code").toString();
        if(resCode.equals("0")){
            return AjaxResult.success(jsonObject.getString("message"));
        }
        return AjaxResult.error(jsonObject.getString("message"));
    }


    /**
     * 设备新增
     * @param zhEquipment
     * @return
     */
    private AjaxResult insertDevice(ZhEquipment zhEquipment){
        String token = getZhToken.getToken();
        String res = ZhOnlineApi.deviceAdd(token,zhEquipment);
        JSONObject jsonObject = JSONObject.parseObject(res);
        Object resCode = jsonObject.get("code").toString();
        if(resCode.equals("0")){            //添加成功  本地化
            Calendar calendar = Calendar.getInstance();
            zhEquipment.setCreateTime(calendar.getTime());        //添加时间
            zhEquipment.setCreateBy(ShiroUtils.getLoginName());          //获取登录用户
            zhEquipmentService.insertZhEquipment(zhEquipment);
        }
        return resMes(res);
    }

    /**
     * 设备修改
     * @param zhEquipment
     * @return
     */
    private String updateDevice(ZhEquipment zhEquipment){
        String token = getZhToken.getToken();
        String res = ZhOnlineApi.deviceUpdate(token,zhEquipment);
        JSONObject jsonObject = JSONObject.parseObject(res);
        Object resCode = jsonObject.get("code").toString();
        if(resCode.equals("0")){            //添加成功  本地化
            zhEquipment.setId(6);
            zhEquipmentService.updateZhEquipment(zhEquipment);
        }
        return res;
    }

    /**
     * 设备删除
     * @param deviceNumber
     * @return
     */
    private String deleteDevice(String deviceNumber){
        String token = getZhToken.getToken();
        String res = ZhOnlineApi.deviceDelete(token,deviceNumber);
        JSONObject jsonObject = JSONObject.parseObject(res);
        Object resCode = jsonObject.get("code").toString();
        if(resCode.equals("0")){            //添加成功  本地化
            zhEquipmentService.deleteZhEquipmentByIds("6");
        }
        return res;
    }

    /**
     * 获取设备列表
     * @param deviceNumber  根据设备号条件查询  可以为null
     * @return
     */
    private String getEqDevice(String deviceNumber){
        String token = getZhToken.getToken();
        String res = ZhOnlineApi.deviceQuery(token,deviceNumber);
        JSONObject jsonObject = JSONObject.parseObject(res);
        Object resCode = jsonObject.get("code").toString();
        if(resCode.equals("0")){
            String tempdata = jsonObject.getString("data");
            if(tempdata.length() > 2){
                JSONArray jsonArray = JSONArray.parseArray(tempdata);
                List<ZhDeviceOption> zhDevices = jsonArray.toJavaList(ZhDeviceOption.class);
                for (ZhDeviceOption zhDeviceOption:zhDevices){
                    System.out.println(zhDeviceOption.getDeviceName() + " , " + zhDeviceOption.getDeviceNumber());
                }
            }else{
                System.out.println("没有绑定设备");
            }
        }

        return "";
    }

    /**
     * 人员新增  人脸上传  授权设备
     * @param zhuser         人员信息
     * @param imgBase64      人脸图片
     * @param deviceNumber   授权设备
     * @return
     */
    private AjaxResult insertDeviceUser(ZhDeviceuser zhuser,String imgBase64,String deviceNumber){
        String token = getZhToken.getToken();
        Calendar calendar = Calendar.getInstance();
        String personNumber = GetMD5.MD5(zhuser.getName()+System.currentTimeMillis()).substring(8, 24); //生成personNumber
        zhuser.setPersonNumber(personNumber);
        zhuser.setCreateBy(ShiroUtils.getLoginName());              //获取登陆人
        zhuser.setCreateTime(calendar.getTime());

        String res = ZhOnlineApi.personAdd(token,zhuser);
        JSONObject jsonObject = JSONObject.parseObject(res);
        Object resCode = jsonObject.get("code").toString();
        if(resCode.equals("0")){            //添加成功  执行人脸上传 授权设备 以及本地化处理
            zhDeviceuserService.insertZhDeviceuser(zhuser);   //存到本地人员库
            //添加人脸照片
            if(imgBase64 != null ){
                String addFaceRes = ZhOnlineApi.addFaceBase64(token,personNumber,imgBase64);
                JSONObject jsonObject1 = JSONObject.parseObject(addFaceRes);
                Object resCode1 = jsonObject1.get("code").toString();
                if(resCode1.equals("0")){
                    System.out.println("人脸添加成功");
                    //存到本地人人脸库
                    ZhFaceimage zhFaceimage = new ZhFaceimage();
                    zhFaceimage.setPersonNumber(personNumber);
                    zhFaceimage.setFaceImage(imgBase64);
                    faceimageService.insertZhFaceimage(zhFaceimage);
                }else{
                    System.out.println("人脸添加失败");
                    return AjaxResult.error("人脸照片不合格 请上传人脸正面照片（不戴帽子，头发不要遮挡头部，头发不要遮挡耳朵）");
                }
            }

            if(deviceNumber != null){
                //人员授权
                String authuserRes = ZhOnlineApi.authrizationAdd(token,deviceNumber,personNumber);
                JSONObject jsonObject2 = JSONObject.parseObject(authuserRes);
                Object resCode2 = jsonObject2.get("code").toString();
                if(resCode2.equals("0")){
                    System.out.println("授权成功");
                    //授权成功本地化
                    ZhUserDevice zhUserDevice = new ZhUserDevice();
                    zhUserDevice.setDeviceNumber(deviceNumber);
                    zhUserDevice.setPersonNumber(personNumber);
                    zhUserDeviceService.insertZhUserDevice(zhUserDevice);
                }else{
                    System.out.println("授权失败");
                    return AjaxResult.error("人员授权失败 检查设备网络 或者重新授权");
                }
            }
        }else{
            System.out.println("添加失败");
            return AjaxResult.error("人员添加失败 检查设备网络");
        }
        return AjaxResult.error("人员添加失败 检查设备网络");
    }

    /**
     * 人员信息修改
     * @param zhuser
     * @return
     */
    private AjaxResult updateDeviceUser(ZhDeviceuser zhuser,String imgBase64,String deviceNumber){
        String token = getZhToken.getToken();
        String personNumber = zhuser.getPersonNumber();
        String res = ZhOnlineApi.personUpdate(token,zhuser);
        JSONObject jsonObject = JSONObject.parseObject(res);
        Object resCode = jsonObject.get("code").toString();
        if(resCode.equals("0")){            //添加成功  执行人脸上传 授权设备 以及本地化处理
            zhDeviceuserService.updateZhDeviceuser(zhuser);   //存到本地人员库
            //添加人脸照片
            if(imgBase64 != null ){
                String addFaceRes = ZhOnlineApi.addFaceBase64(token,personNumber,imgBase64);
                JSONObject jsonObject1 = JSONObject.parseObject(addFaceRes);
                Object resCode1 = jsonObject1.get("code").toString();
                if(resCode1.equals("0")){
                    System.out.println("人脸添加成功");
                    //存到本地人人脸库
                    ZhFaceimage zhFaceimage = new ZhFaceimage();
                    zhFaceimage.setPersonNumber(personNumber);
                    zhFaceimage.setFaceImage(imgBase64);
                    faceimageService.insertZhFaceimage(zhFaceimage);
                }else{
                    System.out.println("人脸添加失败");
                    return AjaxResult.error("人脸照片不合格 请上传人脸正面照片（不戴帽子，头发不要遮挡头部，头发不要遮挡耳朵）");
                }
            }

            if(deviceNumber != null){
                //人员授权
                String authuserRes = ZhOnlineApi.authrizationAdd(token,deviceNumber,personNumber);
                JSONObject jsonObject2 = JSONObject.parseObject(authuserRes);
                Object resCode2 = jsonObject2.get("code").toString();
                if(resCode2.equals("0")){
                    System.out.println("授权成功");
                    //授权成功本地化
                    ZhUserDevice zhUserDevice = new ZhUserDevice();
                    zhUserDevice.setDeviceNumber(deviceNumber);
                    zhUserDevice.setPersonNumber(personNumber);
                    zhUserDeviceService.insertZhUserDevice(zhUserDevice);
                }else{
                    System.out.println("授权失败");
                    return AjaxResult.error("人员授权失败 检查设备网络 或者重新授权");
                }
            }

        }else{
            System.out.println("修改失败");
        }

        return AjaxResult.success();
    }

    /**
     * 所有人员查询
     *
     * @param startTime
     * @param endTime
     * @return
     */
    private String queryPerson(String startTime,String endTime){
        String token = getZhToken.getToken();
        String res = ZhOnlineApi.personQuery(token,null,startTime,endTime,null,"0","1000");
        JSONObject jsonObject = JSONObject.parseObject(res);
        Object resCode = jsonObject.get("code").toString();
        if(resCode.equals("0")){
            String tempdata = jsonObject.getString("data");
            if(tempdata.length() > 2){
                JSONArray jsonArray = JSONArray.parseArray(tempdata);
                List<ZhDeviceuser> zhDeviceusers = jsonArray.toJavaList(ZhDeviceuser.class);
                for (ZhDeviceuser zhDeviceuser:zhDeviceusers){
                    System.out.println(zhDeviceuser.getPersonNumber() + " , " + zhDeviceuser.getName());
                }
            }else{
                System.out.println("不存在人员");
            }
        }
        return "";
    }

    /**
     * 删除人员
     * @param personNumber
     * @return
     */
    private String deletePerson(String personNumber){
        String token = getZhToken.getToken();
        String res = ZhOnlineApi.personDelete(token,personNumber);
        JSONObject jsonObject = JSONObject.parseObject(res);
        Object resCode = jsonObject.get("code").toString();
        if(resCode.equals("0")){
            zhDeviceuserService.deleteZhDeviceuserByPersonNumber(personNumber);     //删除本地人员库
            faceimageService.deleteZhFaceimageByPersonNumber(personNumber);         //删除本地人员人脸库
            zhUserDeviceService.deleteZhUserDeviceByPersonNumber(personNumber);     //删除本地人员授权关联表
        }
        return res;
    }

    /**
     * 设备授权人员
     *
     * @param deviceNumber
     * @param personNumber
     * @return
     */
    private String authUserDevice(String deviceNumber,String personNumber){
        String token = getZhToken.getToken();
        String res = ZhOnlineApi.authrizationAdd(token,deviceNumber,personNumber);
        JSONObject jsonObject = JSONObject.parseObject(res);
        Object resCode = jsonObject.get("code").toString();
        if(resCode.equals("0")){
            ZhUserDevice zhUserDevice = new ZhUserDevice();
            zhUserDevice.setDeviceNumber(deviceNumber);
            zhUserDevice.setPersonNumber(personNumber);
            zhUserDeviceService.insertZhUserDevice(zhUserDevice);
        }
        return "";
    }


    private String deleteAuthDeviceUser(String deviceNumber,String personNumber){
        String token = getZhToken.getToken();
        String res = ZhOnlineApi.authorizationDelete(token,deviceNumber,personNumber);
        JSONObject jsonObject = JSONObject.parseObject(res);
        Object resCode = jsonObject.get("code").toString();
        if(resCode.equals("0")){
            zhUserDeviceService.deleteZhUserDeviceByPersonNumber(personNumber);
        }
        return "";
    }

    /**
     * 授权人员查询
     * @return
     */
    private String authDeviceUser(String deviceNumber){
        String token = getZhToken.getToken();
        String res = ZhOnlineApi.authorizationQuery(token,deviceNumber);
        JSONObject jsonObject = JSONObject.parseObject(res);
        Object resCode = jsonObject.get("code").toString();
        if(resCode.equals("0")){
            String tempdata = jsonObject.get("data").toString();
            if(tempdata.length() > 2){
                ZhDeviceuser data =jsonObject.getObject("data",ZhDeviceuser.class);
                System.out.println(data);
            }else{
                System.out.println("没有授权人员");
            }
        }
        return res;
    }





    public static void main(String[] args){
        ZhOnlineDeviceController zodc = new ZhOnlineDeviceController();
        ZhEquipment zhEquipment = new ZhEquipment();
        zhEquipment.setCreateBy("user001");
        zhEquipment.setMeid("AC0018077604");
        zhEquipment.setDeviceName("user001");
        zodc.insertDevice(zhEquipment);
    }
}
