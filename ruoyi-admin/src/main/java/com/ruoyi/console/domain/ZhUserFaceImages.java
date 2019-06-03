package com.ruoyi.console.domain;

/**
 * zhUser的人脸图片字段
 */
public class ZhUserFaceImages {
    private Integer id;
    private String url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ZhUserFaceImages{" +
                "id=" + id +
                ", url='" + url + '\'' +
                '}';
    }
}
