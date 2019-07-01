package com.ruoyi.ImageSearch;

import com.baidu.aip.imagesearch.AipImageSearch;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * 获取百度access_token
 */
public class Simple {

    public static void main(String[] args) {
        AipImageSearch client = AipImageSearchObject.getInstance();
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

//        HashMap<String, String> options = new HashMap<String, String>();
//        options.put("brief", "https://www.bilibili.com/video/av56584148/?spm_id_from=333.334.b_686f6d655f706f70756c6172697a65.3");
//        options.put("tags", "1,1");
//
//        String image = "D:/test001.jpg";
//        JSONObject res = client.sameHqAdd(image, options);
//        System.out.println(res.toString(2));

        HashMap<String,String> options = new HashMap<String,String>();
        options.put("tars","1,1");

        String image = "D:/0011.jpg";
        JSONObject res = client.sameHqSearch(image,options);
        JSONArray jsonArray = res.getJSONArray("result");
        if(jsonArray.length()>0){
            for(Object object:jsonArray){

            }
        }
    }
}
