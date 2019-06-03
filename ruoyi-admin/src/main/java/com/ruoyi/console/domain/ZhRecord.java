package com.ruoyi.console.domain;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 识别记录实体
 */
public class ZhRecord extends BaseEntity {

    private int id;
    private String name;                    // 人员名称
    private String recognizeType;          // 识别类型
    private String recognizeTime;           // 识别时间
    private String recognizePersonNumber;  // 识别人员编号
    private String path;                    // 人脸图片
    private String age;
    private String content;
    private boolean isCheck;
    private int sex;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRecognizeType() {
        return recognizeType;
    }

    public void setRecognizeType(String recognizeType) {
        this.recognizeType = recognizeType;
    }

    public String getRecognizeTime() {
        return recognizeTime;
    }

    public void setRecognizeTime(String recognizeTime) {
        this.recognizeTime = recognizeTime;
    }

    public String getRecognizePersonNumber() {
        return recognizePersonNumber;
    }

    public void setRecognizePersonNumber(String recognizePersonNumber) {
        this.recognizePersonNumber = recognizePersonNumber;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "ZhRecord{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", recognizeType='" + recognizeType + '\'' +
                ", recognizeTime='" + recognizeTime + '\'' +
                ", recognizePersonNumber='" + recognizePersonNumber + '\'' +
                ", path='" + path + '\'' +
                ", age='" + age + '\'' +
                ", content='" + content + '\'' +
                ", isCheck=" + isCheck +
                ", sex=" + sex +
                '}';
    }
}
