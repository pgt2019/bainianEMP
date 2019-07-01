package com.ruoyi.console.utils;

import com.ruoyi.console.domain.ZhDeviceuser;
import com.ruoyi.console.domain.ZhEquipment;
import okhttp3.FormBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *   在线Api
 */
public class ZhOnlineApi {


    private static final String companyid = "4496eae6f5477cf5";
    private static String url = "http://open.heils.cn/heilsopenconsole/v1/4496eae6f5477cf5";

    /* 人员管理api */

    /**
     *  人员添加
     * @param token
     * @return
     */
    public static String personAdd(String token, ZhDeviceuser zhuser){
        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("token",token);
        paramMap.put("name",zhuser.getName());
        paramMap.put("personNumber",zhuser.getPersonNumber());

        if(zhuser.getIcCard() != null){
            paramMap.put("icCard",zhuser.getIcCard());
        }if(zhuser.getIdCard() != null){
            paramMap.put("idCard",zhuser.getIdCard());
        }if(zhuser.getExtendInfo() != null){
            paramMap.put("extendInfo",zhuser.getExtendInfo());
        }

        return HttpRequest.postBody(url+"/person/add",paramMap);
    }

    /**
     * 批量删除人员
     * @param token
     * @param personNumber   人员编号  多个使用","拼接
     * @return
     */
    public static String personDelete(String token,String personNumber){
        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("token",token);
        paramMap.put("personNumber",personNumber);
        return HttpRequest.postBody(url+"/person/delete",paramMap);
    }

    /**
     * 查询人员
     * @param token
     * @param personNumber
     * @param startTime
     * @param endTime
     * @param keyWord
     * @param startIndex
     * @param length
     * @return
     */
    public static String personQuery(String token,String personNumber,String startTime,String endTime,String keyWord,String startIndex,String length){

        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("token",token);
        paramMap.put("startTime",startTime);
        paramMap.put("endTime",endTime);
        paramMap.put("startIndex",startIndex);
        paramMap.put("length",length);
        if(personNumber != null){
            paramMap.put("personNumber",personNumber);
        }if(keyWord != null){
            paramMap.put("keyWord",keyWord);
        }

        return HttpRequest.postBody(url+"/person/query",paramMap);
    }

    /**
     * 人员修改
     * @param token
     * @param personNumber
     * @param name
     * @param icCard
     * @param idCard
     * @param extendInfo
     * @return
     */
    public static String personUpdate(String token,ZhDeviceuser zhuser) {
        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("token",token);
        paramMap.put("personNumber",zhuser.getPersonNumber());
        if(zhuser.getName() != null){
            paramMap.put("name",zhuser.getName());
        }if(zhuser.getIcCard() != null){
            paramMap.put("icCard",zhuser.getIcCard());
        }if(zhuser.getIdCard() != null){
            paramMap.put("idCard",zhuser.getIdCard());
        }if(zhuser.getExtendInfo() != null){
            paramMap.put("extendInfo",zhuser.getExtendInfo());
        }
        return HttpRequest.postBody(url+"/person/update",paramMap);
    }

    /**
     * 验证人脸照片
     * @param token
     * @param personNumber
     * @param faceImage    人脸图片base64  不加前缀
     * @return
     */
    public static String checkFaceBase64(String token,String personNumber,String faceImage) throws IOException {
        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("token",token);
        paramMap.put("personNumber",personNumber);
        paramMap.put("faceImage",faceImage);
        return  HttpRequest.postBody(url+"/person/face/checkFaceBase64",paramMap);
    }

    /**
     * 增加人脸照片
     * @param token
     * @param personNumber
     * @param faceImage
     * @return
     */
    public static String addFaceBase64(String token,String personNumber,String faceImage){
        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("token",token);
        paramMap.put("personNumber",personNumber);
        paramMap.put("faceImage",faceImage);
        return  HttpRequest.postBody(url+"/person/face/addFaceBase64",paramMap);
    }

    /**
     * 删除人脸照片
     * @param token
     * @param personNumber
     * @param faceId      人脸图片id 不传删人员所有人脸
     * @return
     */
    public static String faceDelete(String token,String personNumber,String faceId){
        return "";
    }

    /**
     * 查询人脸照片
     * @param token
     * @param personNumber
     * @return
     */
    public static String faceQuery(String token,String personNumber) throws IOException {
        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("token",token);
        paramMap.put("personNumber",personNumber);
        return HttpRequest.postBody(url+"/person/face/query",paramMap);
    }


    /* 设备管理类 */

    /**
     * 设备增加
     * @param token
     * @return
     */
    public static String deviceAdd(String token, ZhEquipment zhEquipment){
        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("token",token);
        paramMap.put("deviceNumber",zhEquipment.getMeid());
        paramMap.put("deviceName",zhEquipment.getDeviceName());
        String res = HttpRequest.postBody(url+"/device/add",paramMap);
        return res;
    }

    /**
     * 修改设备
     * @param token
     * @return
     */
    public static String deviceUpdate(String token,ZhEquipment zhEquipment){
        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("token",token);
        paramMap.put("deviceNumber",zhEquipment.getMeid());
        paramMap.put("deviceName",zhEquipment.getDeviceName());
        return HttpRequest.postBody(url+"/device/update",paramMap);
    }

    /**
     * 设备删除
     * @param token
     * @param deviceNumber
     * @return
     */
    public static String deviceDelete(String token,String deviceNumber){
        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("token",token);
        paramMap.put("deviceNumber",deviceNumber);
        return HttpRequest.postBody(url+"/device/delete",paramMap);
    }

    /**
     * 设备查询
     * @param token
     * @param deviceNumber
     * @return
     */
    public static String deviceQuery(String token,String deviceNumber){
        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("token",token);
        if(deviceNumber != null){
            paramMap.put("deviceNumber",deviceNumber);
        }
        return HttpRequest.postBody(url+"/device/query",paramMap);
    }

    /**
     * 设备授权人员
     * @param token
     * @param deviceNumber
     * @param personNumber
     * @return
     */
    public static String authrizationAdd(String token,String deviceNumber,String personNumber){
        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("token",token);
        paramMap.put("deviceNumber",deviceNumber);
        paramMap.put("personNumber",personNumber);
        return HttpRequest.postBody(url+"/device/authorization/add",paramMap);
    }

    /**
     * 设备销权人员
     * @param token
     * @param deviceNumber
     * @param personNumber
     * @return
     */
    public static String authorizationDelete(String token,String deviceNumber,String personNumber){
        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("token",token);
        paramMap.put("deviceNumber",deviceNumber);
        paramMap.put("personNumber",personNumber);
        return HttpRequest.postBody(url+"/device/authorization/delete",paramMap);
    }

    /**
     * 设备授权人员查询接口
     * @param token
     * @param deviceNumber
     * @return
     */
    public static String authorizationQuery(String token,String deviceNumber){
        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("token",token);
        paramMap.put("deviceNumber",deviceNumber);
        return HttpRequest.postBody(url+"/device/authorization/query",paramMap);
    }

    /**
     * 修改设备配置
     * @param token
     * @param deviceNumber
     * @param title
     * @param logo
     * @param logofile
     * @param threshold
     * @param registeredIdentificationType
     * @param unRegisteredIdentificationType
     * @param isOpenLiving
     * @param isVoicePrompt
     * @param saveLocalRecordTime
     * @param isOpenSafetyHat
     * @param isOpenAgeGender
     * @param screenPapersSwitch
     * @param startIntervalTime
     * @param circleTime
     * @param idverifythreshold
     * @return
     */
    public static String settingUpdate(String token,String deviceNumber,String title,String logo,String logofile,String threshold,String registeredIdentificationType,String unRegisteredIdentificationType,String isOpenLiving,String isVoicePrompt,String saveLocalRecordTime,String isOpenSafetyHat,String isOpenAgeGender,String screenPapersSwitch,String startIntervalTime,String circleTime,String idverifythreshold) throws IOException {
        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("token",token);
        paramMap.put("deviceNumber",deviceNumber);
        if(title != null){
            paramMap.put("title",title);
        }if(logo != null){
            paramMap.put("logo",logo);
        }if(logofile != null){
            paramMap.put("logofile",logofile);
        }if(threshold != null){
            paramMap.put("threshold",threshold);
        }if(registeredIdentificationType != null){
            paramMap.put("registeredIdentificationType",registeredIdentificationType);
        }if(unRegisteredIdentificationType != null){
            paramMap.put("unRegisteredIdentificationType",unRegisteredIdentificationType);
        }if(isOpenLiving != null){
            paramMap.put("isOpenLiving",isOpenLiving);
        }if(isVoicePrompt != null){
            paramMap.put("isVoicePrompt",isVoicePrompt);
        }if(saveLocalRecordTime != null){
            paramMap.put("saveLocalRecordTime",saveLocalRecordTime);
        }if(isOpenSafetyHat != null){
            paramMap.put("isOpenSafetyHat",isOpenSafetyHat);
        }if(isOpenAgeGender != null){
            paramMap.put("isOpenAgeGender",isOpenAgeGender);
        }if(screenPapersSwitch != null){
            paramMap.put("screenPapersSwitch",screenPapersSwitch);
        }if(startIntervalTime != null){
            paramMap.put("startIntervalTime",startIntervalTime);
        }if(circleTime != null){
            paramMap.put("circleTime",circleTime);
        }if(idverifythreshold != null){
            paramMap.put("idverifythreshold",idverifythreshold);
        }

        return HttpRequest.postBody(url+"/device/setting/update",paramMap);
    }

    /**
     * 设备配置查询
     * @param token
     * @param deviceNumber
     * @return
     */
    public static String settingQuery(String token,String deviceNumber) throws IOException {
        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("token",token);
        paramMap.put("deviceNumber",deviceNumber);
        return HttpRequest.postBody(url+"/device/setting/query",paramMap);
    }

    /**
     * 增加设备屏保图片
     * @param token
     * @param deviceNumber
     * @param screenImage
     * @return
     */
    public static String settingAddScreenBase64(String token,String deviceNumber,String screenImage) throws IOException {
        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("token",token);
        paramMap.put("deviceNumber",deviceNumber);
        paramMap.put("screenImage",screenImage);
        return HttpRequest.postBody(url+"/device/setting/addScreenBase64",paramMap);
    }

    /**
     * 删除设备屏保图片
     * @param token
     * @param deviceNumber
     * @param id            不填写删除所有屏保照片
     * @return
     */
    public static String settingDelScreenImg(String token,String deviceNumber,String id){
        return "";
    }

    /**
     * 直接控制开门
     * @param token
     * @param deviceNumber
     * @return
     */
    public static String settingPenDoor(String token,String deviceNumber) throws IOException {
        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("token",token);
        paramMap.put("deviceNumber",deviceNumber);
        return HttpRequest.postBody(url+"/device/setting/penDoor",paramMap);
    }


    /* 识别记录类 */

    /**
     * 设置记录回调地址
     * @param token
     * @param deviceNumber
     * @param callBackAddress
     * @return
     */
    public static String recordSetCallBackAddr(String token,String deviceNumber,String callBackAddress) throws IOException {
        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("token",token);
        paramMap.put("deviceNumber",deviceNumber);
        paramMap.put("callBackAddress",callBackAddress);
        return HttpRequest.postBody(url+"/record/setCallBackAddr",paramMap);
    }

    /**
     * 查询识别记录
     * @param token
     * @param personNumber
     * @param startTime
     * @param endTime
     * @param startIndex
     * @param length
     * @return
     */
    public static String recordQuery(String token,String personNumber,String startTime,String endTime,String startIndex,String length) throws IOException {
        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("token",token);
        paramMap.put("startTime",startTime);
        paramMap.put("endTime",endTime);
        paramMap.put("startIndex",startIndex);
        paramMap.put("length",length);
        if(personNumber != null){
            paramMap.put("personNumber",personNumber);
        }
        return HttpRequest.postBody(url+"/record/query",paramMap);
    }

    /**
     * 删除识别记录
     * @param token
     * @param personNumber
     * @param startTime
     * @param endTime
     * @return
     */
    public static String recordDelete(String token,String personNumber,String startTime,String endTime) throws IOException {
        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("token",token);
        paramMap.put("startTime",startTime);
        paramMap.put("endTime",endTime);
        if(personNumber != null){
            paramMap.put("personNumber",personNumber);
        }
        return HttpRequest.postBody(url+"/record/delete",paramMap);
    }


    public static void main(String[] args) throws IOException {
        GetZhToken getZhToken = GetZhToken.getGetZhToken();
        String token = getZhToken.getToken();
        System.out.println(ZhOnlineApi.personQuery(token,null,"2019-06-01","2019-06-30",null,"0","10"));
//        System.out.println(ZhOnlineApi.recordDelete(token,null,"2019-06-01","2019-06-21"));
        System.out.println(ZhOnlineApi.recordQuery(token,null,"2019-06-01","2019-06-30","0","10"));
        //        System.out.println(ZhOnlineApi.recordSetCallBackAddr(token,"AC0018077604","http://localhost:8888/"));
//        System.out.println(ZhOnlineApi.settingQuery(token,"AC0018077604"));
//        System.out.println(ZhOnlineApi.settingUpdate(token,"AC0018077604","人脸识别签到设备",null,null,null,null,null,null,null,null,null,null,null,null,null,null));
        //        System.out.println(ZhOnlineApi.faceQuery(token,"RY00000001"));
//        System.out.println(ZhOnlineApi.deviceQuery(token,null));
//        System.out.println(ZhOnlineApi.authrizationAdd(token,"AC0018077604","121811178;RY00000001"));
//        System.out.println(ZhOnlineApi.authorizationDelete(token,"AC0018077604","121811178;1223232"));
//        System.out.println(ZhOnlineApi.authorizationQuery(token,"AC0018077604"));
//        System.out.println(ZhOnlineApi.deviceUpdate(token,"AC0018077604","test00000"));
//        System.out.println(ZhOnlineApi.deviceDelete(token,"AC0018077604"));
//        System.out.println(ZhOnlineApi.deviceAdd(token,"AC0018077604","test"));

//        System.out.println(ZhOnlineApi.personAdd(token,"121811178","t1a4o0",null,null,null));

//        System.out.println(ZhOnlineApi.personUpdate(token,"121811178","tao1111111",null,null,null));
//        System.out.println(ZhOnlineApi.personQuery(token,null,"2019-06-01","2019-06-30",null,"0","10"));
//        System.out.println(ZhOnlineApi.personDelete(token,"121878,1223232111"));

    }

}
