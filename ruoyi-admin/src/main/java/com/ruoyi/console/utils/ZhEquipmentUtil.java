package com.ruoyi.console.utils;

import com.ruoyi.console.domain.ZhUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 振汇人脸识别设备 Api
 */

public class ZhEquipmentUtil {

    private static final String serverIp = "http://192.168.1.199:8089";    //设备ip暂时写死  日后需要持久化

   /* 设备配置信息 api  start */

    /**
     * 1.6.4.1.OFFLINE-API-DEVICE-01 修改设备
     * http://ip:8089/device/setting/update, method：POST
     * @param token             鉴权token
     * @param deviceNumber      设备号（必传）
     * @param logo              logo
     * @param startLogo         起始页
     * @param threshold         识别阈值
     * @param identificationType 识别类型
     * @param verifyIdCard      是否人证合验
     * @param voiceHint         是否语音提示
     * @param outDoor           是否户外模式
     * @param isOpenLiving      是否开启活体
     * @param isOpenAgeGender   是否启用年龄性别
     * @param isOpenSafetyHat   是否启用安全帽识别
     * @param callBackAddress   回调地址
     * @param saveLocalRecord   识别记录本地保存时间
     * @param idVerifyThreshold 人证合验阈值
     * @return
     */
    public static String updateDeviceOption(String token,String deviceNumber,String title, File logo,File startLogo,String threshold,String identificationType,String
            verifyIdCard ,String voiceHint,String outDoor,String isOpenLiving,String isOpenAgeGender,String isOpenSafetyHat,
                                            String callBackAddress,String saveLocalRecord,String idVerifyThreshold){
        String params = "deviceNumber="+deviceNumber;
        if(logo != null){
            params += "&logo="+logo;
        } if(title != null){
            params += "&title="+title;
        } if(startLogo != null){
            params += "&startLogo="+startLogo;
        }if(threshold != null){
            params += "&threshold="+threshold;
        }if(identificationType != null){
            params += "&identificationType="+identificationType;
        }if(verifyIdCard != null){
            params += "&verifyIdCard="+verifyIdCard;
        }if(voiceHint != null){
            params += "&voiceHint="+voiceHint;
        }if(outDoor != null){
            params += "&outDoor="+outDoor;
        }if(isOpenLiving != null){
            params += "&isOpenLiving="+isOpenLiving;
        }if(isOpenAgeGender != null){
            params += "&isOpenAgeGender="+isOpenAgeGender;
        }if(isOpenSafetyHat != null){
            params += "&isOpenSafetyHat="+isOpenSafetyHat;
        }if(callBackAddress != null){
            params += "&callBackAddress="+callBackAddress;
        }if(saveLocalRecord != null){
            params += "&saveLocalRecord="+saveLocalRecord;
        }if(idVerifyThreshold != null){
            params += "&idVerifyThreshold="+idVerifyThreshold;
        }
        String result =HttpRequest.sendPost(serverIp+"/device/setting/update",params,token);
        System.out.println(result);
        return result;
    }

    /**
     *  OFFLINE-API-DEVICE-02 查询设备配置
     *  http://ip:8089/device/setting/query, method：Get
     * @param deviceNumber              设备号
     * @param token                     token
     * @return
     */
    public static String getDeviceOptions(String deviceNumber,String token){
        String params = "deviceNumber="+deviceNumber;
        String result =HttpRequest.sendGet(serverIp+"/device/setting/query",params,token);
        System.out.println(result);
        return result;
    }


   /* 设备配置信息 api  END  */

    /*          人脸库处理api   start     */

    /**
     * OFFLINE-API-PERSON-05 人脸照片验证（BASE64）
     * http://ip:8089/person/face/checkFace, method：POST
     * @param faceImage    人脸照片（base64）
     * @param token         token
     * @return
     *          {"code": "1","message": “图片人脸姿势不达标"}
     */
    public static String checkFace(String faceImage,String token) throws UnsupportedEncodingException {
        String params = "faceImage="+faceImage;
        System.out.println(token);
        String result =HttpRequest.sendPost(serverIp+"/person/face/checkFace",params,token);
        return result;
    }

    /**
     *  OFFLINE-API-PERSON-06 增加人脸照片（BASE64）
     *  http://ip:8089/person/face/addBase64, method：POSt
     * @param personNumber      人员编号
     * @param faceImage         人脸照片（BASE64）
     * @param token             token
     * @return
     *          {"code": "0","message": "操作成功"}
     */
    public static synchronized String addBase64Image(String personNumber,String faceImage,String token) throws UnsupportedEncodingException {
        String params = "personNumber="+personNumber+"&faceImage="+faceImage;
        String result =HttpRequest.sendPost(serverIp+"/person/face/addBase64",params,token);
        System.out.println(result);
        return result;
    }

    /**
     *  OFFLINE-API-PERSON-09 查询人脸照片
     *  http://ip:8089/person/face/query, method：Get
     * @param personNumber   人员编号
     * @param token           token
     * @return              {
     *                          "code": "0",
     *                          "message": "操作成功"
     *                          data[{
     *                                  "id": "0",
     *                                  "url": "/person/face/image?id=31&url=4_114_4123123123123"
     *                          }]
     *                       }
     */
    public static String getFaceImage(String personNumber, String token){
        String params = "personNumber="+personNumber;
        String result =HttpRequest.sendGet(serverIp+"/person/face/query",params,token);
        System.out.println(result);
        return result;
    }

    /**
     * .OFFLINE-API-PERSON-08 删除人脸照片
     *  http://ip:8089/person/face/delete, method：POST
     * @param personNumber  人员编号
     * @param faceId        人脸编号 （不传删除全部）
     * @param token         token
     * @return
     *          {"code": "0","message": "操作成功"}
     */
    public static synchronized String deleteImage(String personNumber,String faceId,String token){
        String params = "personNumber="+personNumber;
        if(faceId != null){
            params += "&faceId="+faceId;
        }
        String result =HttpRequest.sendPost(serverIp+"/person/face/delete",params,token);
        System.out.println(result);
        return result;
    }

    /*       人脸库处理api   end     */

    /*      人员管理api  start   */

    /**
     * .OFFLINE-API-PERSON-04 人员修改
     * http://ip:8089/person/update, method：POST
     * @param zhUser            设备用户对象
     * @param token             token
     * @return
     *          {"code": "0","message": "操作成功"}
     */
    public static synchronized String updateUser(ZhUser zhUser, String token){
        String params = "personNumber="+zhUser.getPersonNumber();
        if(zhUser.getName() != null){
            params += "&name="+zhUser.getName();
        }
        if(zhUser.getIcCard() != null){
            params += "&icCard="+zhUser.getIcCard();
        }
        if(zhUser.getIdCard() != null){
            params += "&idCard="+zhUser.getIdCard();
        }
        if(zhUser.getExtendInfo() != null){
            params += "&extendInfo="+zhUser.getExtendInfo();
        }
        String result =HttpRequest.sendPost(serverIp+"/person/update",params,token);
        System.out.println(result);
        return result;
    }

    /**
     * .OFFLINE-API-PERSON-01 增加人员
     * http://ip:8089/person/add, method：POST
     * @param zhUser            设备用户对象
     * @param token             token
     * @return
     *          {"code": "0","message": "操作成功"}
     */
    public static synchronized String addUser(ZhUser zhUser, String token){
        String params = "";

        if(zhUser.getName() != null){
            params += "name="+zhUser.getName();
        }
        if(zhUser.getPersonNumber() != null){
            params += "personNumber="+zhUser.getPersonNumber();
        }
        if(zhUser.getIcCard() != null){
            params += "&icCard="+zhUser.getIcCard();
        }
        if(zhUser.getIdCard() != null){
            params += "&idCard="+zhUser.getIdCard();
        }
        if(zhUser.getExtendInfo() != null){
            params += "&extendInfo="+zhUser.getExtendInfo();
        }
        String result =HttpRequest.sendPost(serverIp+"/person/add",params,token);
        System.out.println(result);
        return result;
    }


    /**
     * OFFLINE-API-PERSON-02 人员删除（批量）
     * http://ip:8089/person/delete, method：POST
     *
     * @param personNumber  人员编号 多人以逗号分隔
     * @param token         token
     * @return
     *              {"code": "0","message": "操作成功"}
     */
    public static synchronized String deleteUser(String personNumber,String token){
        String params = "personNumber="+personNumber;
        String result =HttpRequest.sendPost(serverIp+"/person/delete",params,token);
        System.out.println(result);
        return result;
    }


    /**
     * OFFLINE-API-PERSON-03 人员查询
     * http://ip:8089/person/queryList, method：GET
     * @param personNumber      人员编码
     * @param token             token
     * @return
     */
    public static String queryUserList(String personNumber,String token){
        String params = "";
        if(personNumber != null){
            params += "personNumber="+personNumber;
        }
        String result =HttpRequest.sendGet(serverIp+"/person/queryList",params,token);
        System.out.println(result);
        return result;
    }

    /*      人员管理api  END   */
    /*      日志管理api  start   */

    /**
     * OFFLINE-API-RECORD-02 查询识别记录
     * http://ip:8089/record/query, method：Get
     * @param personNumber          人员编号
     * @param startTime             开始时间
     * @param endTime               结束时间
     * @param startIndex            起始页
     * @param length                返回条数
     * @param token                 token
     * @return
     */
    public static String getRecord(String personNumber,String startTime,String endTime,String startIndex,String length,String token){
        String params = "";
        if(personNumber != null){
            params += "personNumber="+personNumber;
        }
        if(startTime != null){
            if(params.length() == 0){
                params += "startTime="+Long.parseLong(startTime);
            }else{
                params += "&startTime="+Long.parseLong(startTime);
            }
        }
        if(startIndex != null){
            if(params.length() == 0){
                params += "startIndex="+startIndex;
            }else {
                params += "&startIndex="+startIndex;
            }
        }
        if(endTime != null){
            if(params.length() == 0){
                params += "endTime="+Long.parseLong(endTime);
            }else {
                params += "&endTime="+Long.parseLong(endTime);
            }
        }
        if(length != null){
            if(params.length() == 0){
                params += "length="+length;
            }else {
                params += "&length="+length;
            }
        }
        String result =HttpRequest.sendGet(serverIp+"/record/query",params,token);
        System.out.println(result);
        return result;
    }

    /*      日志管理api  end   */

    /**
     * 设备鉴权
     * @param deviceNumber   设备号
     * @param activationCode     激活码
     * @param timestamp         时间戳
     * @param sign              MD5-32(activationCode + deviceNumber + timestamp)
     * @return  token
     */
    public static String getToken(String ip,String deviceNumber,String  activationCode,long timestamp,String sign){
        String params = "ip="+ip
                +"&activationCode="+activationCode
                +"&deviceNumber="+deviceNumber
                +"&timestamp="+timestamp
                +"&sign="+sign;
        String result =HttpRequest.sendPost(serverIp+"/auth",params,null);
        System.out.println(result);
        return result;
    }


}
