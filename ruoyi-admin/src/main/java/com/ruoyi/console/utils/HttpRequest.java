package com.ruoyi.console.utils;

import com.alibaba.druid.support.json.JSONUtils;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import okhttp3.*;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.output.WriterOutputStream;
import org.apache.http.util.TextUtils;
import sun.net.www.http.HttpClient;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 问题未设定超时时间  长时间没有响应
 */

public class HttpRequest {
    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @param token
     *             没有token 传null
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param,String token) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = null;
            if (param != null){
                urlNameString = url + "?" + param;
            }else{
                urlNameString = url;
            }
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setConnectTimeout(20000);
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            if (token != null){
                connection.setRequestProperty("token",token);
            }
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static String sendPostHttpClient(String params,String url){
        try {
            HttpResponse<String> response = Unirest.post(url)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("cache-control", "no-cache")
                    .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                    .header("Accept", "*/*")
                    .header("accept-encoding", "gzip, deflate")
                    .header("Connection", "keep-alive")
                    .body(params)
                    .asString();
            System.out.println(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param,String token) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setConnectTimeout(20000);
            conn.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("ContentType","application/x-www-form-urlencoded");
            conn.setRequestProperty("user-agent",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
            if (token != null){
                conn.setRequestProperty("token",token);
            }

            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());

           // 发送请求参数
            if (param != null){
                out.print(param);
            }

            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }

        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }


    public static String postBody(String actionUrl,Map<String,String> params) {

        String BOUNDARY = java.util.UUID.randomUUID().toString();
        String PREFIX = "--";
        String LINEND = "\r\n";
        String MULTIPART_FROM_DATA = "multipart/form-data";
        String CHARSET = "UTF-8";
        try{


        URL uri = new URL(actionUrl);
        HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
        conn.setReadTimeout(30 * 1000); // 缓存的最长时间
        conn.setDoInput(true);// 允许输入
        conn.setDoOutput(true);// 允许输出
        conn.setUseCaches(false); // 不允许使用缓存
        conn.setRequestMethod("POST");
        conn.setRequestProperty("connection", "keep-alive");
        conn.setRequestProperty("Charsert", "UTF-8");
        conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);
        StringBuilder sb = new StringBuilder();
        if (params!=null) {
            // 首先组拼文本类型的参数
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINEND);
                sb.append("Content-Disposition: form-data; name=\""
                        + entry.getKey() + "\"" + LINEND);
                sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
                sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
                sb.append(LINEND);
                sb.append(entry.getValue());
                sb.append(LINEND);
            }

        }

        DataOutputStream outStream = new DataOutputStream(
                conn.getOutputStream());
        if (!TextUtils.isEmpty(sb.toString())) {
            outStream.write(sb.toString().getBytes());
        }
        // 请求结束标志
        byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
        outStream.write(end_data);
        outStream.flush();

        // 得到响应码
        int res = conn.getResponseCode();
        InputStream in = conn.getInputStream();
        if (res == 200) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = bufferedReader.readLine()) != null){
                buffer.append(line);
            }

//          int ch;
//          StringBuilder sb2 = new StringBuilder();
//          while ((ch = in.read()) != -1) {
//              sb2.append((char) ch);
//          }
            return buffer.toString();
        }
            outStream.close();
            conn.disconnect();
            return in.toString();
        }  catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
       return "";
    }


    /**
     * 检查设备连接状态  ping
     * @param ip
     * @return
     */
    public static boolean isConnect(String ip){
        boolean connect = false;
        Runtime runtime = Runtime.getRuntime();
        Process process;
        try {
            process = runtime.exec("ping -n 1 -w 300 " + ip);
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            System.out.println("返回值为:"+sb);
            is.close();
            isr.close();
            br.close();

            if (null != sb && !sb.toString().equals("")) {
                String logString = "";
                if (sb.toString().indexOf("TTL") > 0) {
                    // 网络畅通
                    connect = true;
                } else {
                    // 网络不畅通
                    connect = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return connect;
    }

    public static void main(String[] args) throws IOException {
//        //发送 POST 请求
//        String sr=HttpRequest.sendPost("http://192.168.1.199:8089/auth", "",null);
//        System.out.println(sr);

//        System.out.println(HttpRequest.isConnect("192.168.1.198"));

        File file = new File("C:/Users/Administrator/Pictures/logo.png");
        URL imgUrl = new URL("file:///" + file.getPath()); // file为该图片文件对象,通过getPath()方法可以获取该图片的地址）
        Image img = Toolkit.getDefaultToolkit().getImage(imgUrl);// 获取图片对象
        System.out.println(img);
    }

}
