package com.ruoyi.ImageSearch;

import com.baidu.aip.imagesearch.AipImageSearch;

/**
 *    单例获取 access_token
 */
public class AipImageSearchObject {
    private static final String APP_ID = "16639265";
    private static final String API_KEY = "WGtfu880hwmWR14Wnf1jYWH5";
    private static final String SECRET_KEY = "Ckw3bioMdgZtzEGvFBTFLKDpTfXDwAQI";

    private static AipImageSearch aipImageSearch = null;

    public static AipImageSearch getInstance(){
        if(aipImageSearch == null){
            synchronized (AipImageSearchObject.class){
                if(aipImageSearch == null){
                    aipImageSearch = new AipImageSearch(APP_ID,API_KEY,SECRET_KEY);
                    aipImageSearch.setConnectionTimeoutInMillis(2000);
                    aipImageSearch.setSocketTimeoutInMillis(60000);
                    return aipImageSearch;
                }
            }
        }
        return aipImageSearch;
    }

}
