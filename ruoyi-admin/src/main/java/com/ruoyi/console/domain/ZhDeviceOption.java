package com.ruoyi.console.domain;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 振汇设备配置信息entity
 */

public class ZhDeviceOption extends BaseEntity {

    private String deviceNumber;
    private String deviceName;
    private String title;                  //标语
    private String logo;                   //logo路径
    private String startLogo;
    private String threshold;              //识别阈值

    /*
        0:只能刷脸,
        1:只能刷 IC 卡,
        2:只能刷身份证,
        3:先刷 IC 卡再刷脸,
        4:先刷身份证再刷脸,
        5:刷脸或刷 IC 卡皆可,
        6:刷脸或刷身份证皆可,
        7:刷 IC 卡或刷身份证皆可,
        8:刷脸或刷 IC 卡或刷身份证皆可
     */
    private String identificationType;     //识别类型
    private String verifyIdCard;            //是否人证合验
    private String voiceHint;               //是否开启语音
    private String isOutDoor;                 //是否户外
    private String isOpenLiving;            //是否开启活体
    private String isOpenAgeGender;         //是否开启年龄性别
    private String isOpenSafetyHat;         //是否开启安全帽
    private String callBackAddress;         //记录回调地址
    private String saveLocalRecordTime;     //识别记录本地保存时间
    private String idVerifyThreshold;       //人证合验阈值
    private String unRegisteredIdentificationType;  //未注册人员识别方式
    private String startFigure;

    private String screenPapersSwitch;     //屏保显示开关
    private String startIntervalTime;      //未捕捉到人脸启动时间
    private String circleTime;             //屏保轮播时间
    private String screenImgList;          //屏保图片数据

    private String registeredIdentificationType;
    private String isVoicePrompt;

    public String getStartLogo() {
        return startLogo;
    }

    public void setStartLogo(String startLogo) {
        this.startLogo = startLogo;
    }

    public String getUnRegisteredIdentificationType() {
        return unRegisteredIdentificationType;
    }

    public void setUnRegisteredIdentificationType(String unRegisteredIdentificationType) {
        this.unRegisteredIdentificationType = unRegisteredIdentificationType;
    }

    public String getRegisteredIdentificationType() {
        return registeredIdentificationType;
    }

    public void setRegisteredIdentificationType(String registeredIdentificationType) {
        this.registeredIdentificationType = registeredIdentificationType;
    }

    public String getIsVoicePrompt() {
        return isVoicePrompt;
    }

    public void setIsVoicePrompt(String isVoicePrompt) {
        this.isVoicePrompt = isVoicePrompt;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getScreenPapersSwitch() {
        return screenPapersSwitch;
    }

    public void setScreenPapersSwitch(String screenPapersSwitch) {
        this.screenPapersSwitch = screenPapersSwitch;
    }

    public String getStartIntervalTime() {
        return startIntervalTime;
    }

    public void setStartIntervalTime(String startIntervalTime) {
        this.startIntervalTime = startIntervalTime;
    }

    public String getCircleTime() {
        return circleTime;
    }

    public void setCircleTime(String circleTime) {
        this.circleTime = circleTime;
    }

    public String getScreenImgList() {
        return screenImgList;
    }

    public void setScreenImgList(String screenImgList) {
        this.screenImgList = screenImgList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    public String getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(String identificationType) {
        this.identificationType = identificationType;
    }

    public String getVerifyIdCard() {
        return verifyIdCard;
    }

    public void setVerifyIdCard(String verifyIdCard) {
        this.verifyIdCard = verifyIdCard;
    }

    public String getVoiceHint() {
        return voiceHint;
    }

    public void setVoiceHint(String voiceHint) {
        this.voiceHint = voiceHint;
    }

    public String getIsOutDoor() {
        return isOutDoor;
    }

    public void setIsOutDoor(String isOutDoor) {
        this.isOutDoor = isOutDoor;
    }


    public String getStartFigure() {
        return startFigure;
    }

    public void setStartFigure(String startFigure) {
        this.startFigure = startFigure;
    }

    public String getIsOpenLiving() {
        return isOpenLiving;
    }

    public void setIsOpenLiving(String isOpenLiving) {
        this.isOpenLiving = isOpenLiving;
    }

    public String getIsOpenAgeGender() {
        return isOpenAgeGender;
    }

    public void setIsOpenAgeGender(String isOpenAgeGender) {
        this.isOpenAgeGender = isOpenAgeGender;
    }

    public String getIsOpenSafetyHat() {
        return isOpenSafetyHat;
    }

    public void setIsOpenSafetyHat(String isOpenSafetyHat) {
        this.isOpenSafetyHat = isOpenSafetyHat;
    }

    public String getCallBackAddress() {
        return callBackAddress;
    }

    public void setCallBackAddress(String callBackAddress) {
        this.callBackAddress = callBackAddress;
    }

    public String getSaveLocalRecordTime() {
        return saveLocalRecordTime;
    }

    public void setSaveLocalRecordTime(String saveLocalRecordTime) {
        this.saveLocalRecordTime = saveLocalRecordTime;
    }

    public String getIdVerifyThreshold() {
        return idVerifyThreshold;
    }

    public void setIdVerifyThreshold(String idVerifyThreshold) {
        this.idVerifyThreshold = idVerifyThreshold;
    }


    @Override
    public String toString() {
        return "ZhDeviceOption{" +
                "deviceNumber='" + deviceNumber + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", title='" + title + '\'' +
                ", logo='" + logo + '\'' +
                ", startLogo='" + startLogo + '\'' +
                ", threshold='" + threshold + '\'' +
                ", identificationType='" + identificationType + '\'' +
                ", verifyIdCard='" + verifyIdCard + '\'' +
                ", voiceHint='" + voiceHint + '\'' +
                ", isOutDoor='" + isOutDoor + '\'' +
                ", isOpenLiving='" + isOpenLiving + '\'' +
                ", isOpenAgeGender='" + isOpenAgeGender + '\'' +
                ", isOpenSafetyHat='" + isOpenSafetyHat + '\'' +
                ", callBackAddress='" + callBackAddress + '\'' +
                ", saveLocalRecordTime='" + saveLocalRecordTime + '\'' +
                ", idVerifyThreshold='" + idVerifyThreshold + '\'' +
                ", unRegisteredIdentificationType='" + unRegisteredIdentificationType + '\'' +
                ", startFigure='" + startFigure + '\'' +
                ", screenPapersSwitch='" + screenPapersSwitch + '\'' +
                ", startIntervalTime='" + startIntervalTime + '\'' +
                ", circleTime='" + circleTime + '\'' +
                ", screenImgList='" + screenImgList + '\'' +
                ", registeredIdentificationType='" + registeredIdentificationType + '\'' +
                ", isVoicePrompt='" + isVoicePrompt + '\'' +
                '}';
    }
}
