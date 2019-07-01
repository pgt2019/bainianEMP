package com.ruoyi.console.utils;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * 签名工具类
 * 
 * @author Binjie.Qian
 * 
 */
public class SignUtil {

    // 企业秘钥，在企业控制台之账号里查看(非常重要)
	private static final String SECRET = "4b4ca059298e8d3de0446c96c2";

	/**
	 * 使用默认secret签名
	 * 
	 * @param paramValues
	 * @return
	 */
	public static String sign(Map<String, String> paramValues) {
		return sign(paramValues, null);
	}


	/**
	 * 对paramValues进行签名，其中ignoreParamNames这些参数不参与签名
	 * 
	 * @param paramValues
	 * @param ignoreParamNames
	 * @param secret
	 * @return
	 */
	public static String sign(Map<String, String> paramValues, List<String> ignoreParamNames) {
		StringBuilder sb = new StringBuilder();
		List<String> paramNames = new ArrayList<String>(paramValues.size());
		paramNames.addAll(paramValues.keySet());
		if (ignoreParamNames != null && ignoreParamNames.size() > 0) {
			for (String ignoreParamName : ignoreParamNames) {
				paramNames.remove(ignoreParamName);
			}
		}
		Collections.sort(paramNames);
		sb.append(SECRET);
		for (String paramName : paramNames) {
			sb.append(paramName).append(paramValues.get(paramName));
		}
		sb.append(SECRET);
		try {
			byte[] sha1Digest = getMD5Digest(sb.toString());
			return byte2hex(sha1Digest);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	private static byte[] getMD5Digest(String data) throws IOException {
		byte[] bytes = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			bytes = md.digest(data.getBytes("UTF-8"));
		} catch (GeneralSecurityException gse) {
			throw new IOException(gse);
		}
		return bytes;
	}

	private static String byte2hex(byte[] bytes) {
		StringBuilder sign = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if (hex.length() == 1) {
				sign.append("0");
			}
			sign.append(hex.toUpperCase());
		}
		return sign.toString();
	}

	  public static void main(String[] args) {
        Map m = new HashMap();
        m.put("companyid", "873034b5bc17e6fb");//企业KEY
        m.put("timestamp", System.currentTimeMillis()+"");
        m.put("sign", SignUtil.sign(m));
//        String s = HttpUtils.doPost("http://test.open.heils.cn/heilsopenconsole/v1/873034b5bc17e6fb/auth", m);
//        System.out.println(s);

    }
}
