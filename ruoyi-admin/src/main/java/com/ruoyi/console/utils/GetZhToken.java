package com.ruoyi.console.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GetZhToken {

    private static final String secret = "4b4ca059298e8d3de0446c96c2";
    private static final String companyid = "4496eae6f5477cf5";


    private volatile static GetZhToken getZhToken;
    private static String token;
    private GetZhToken(){}
    public static GetZhToken getGetZhToken(){
        if(getZhToken == null || token == null){
            synchronized (GetZhToken.class){
                if(getZhToken == null || token == null){
                    getZhToken = new GetZhToken();
                    getZhToken.getOnlineToken();
                }
            }
        }
        return getZhToken;
    }

    public String getToken(){
        return token;
    }

    private void getOnlineToken(){
        Map m = new HashMap();
        m.put("companyid", companyid);//企业KEY
        m.put("timestamp", System.currentTimeMillis()+"");
        String sign = SignUtil.sign(m);
        String params = "companyid="+ companyid + "&timestamp=" + m.get("timestamp") + "&sign="+sign;
        String result = HttpRequest.sendPost("http://open.heils.cn/heilsopenconsole/v1/"+companyid+"/auth",params,null);
        JSONObject json = JSONObject.parseObject(result);
        String checkCode = json.getString("code");
        if(checkCode.equals("0")){
            String data = json.getString("data");
            System.out.println(data);
            token = data;
        }
    }

    public static void main(String[] args){
        GetZhToken getZhToken = GetZhToken.getGetZhToken();
        String token = getZhToken.getToken();
        String result = HttpRequest.sendPost("http://open.heils.cn/heilsopenconsole/v1/"+companyid+"/person/query",null,token);
        System.out.println(result);
    }

}
