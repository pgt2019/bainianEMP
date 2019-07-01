package com.ruoyi.console.controller;

import com.baidu.aip.imagesearch.AipImageSearch;
import com.ruoyi.ImageSearch.AipImageSearchObject;
import com.ruoyi.common.config.Global;
import com.ruoyi.common.config.ServerConfig;
import com.ruoyi.common.core.domain.AjaxResult;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@Controller
@RequestMapping("/console/AipImageSearch")
public class AipImageSearchController {

    @Autowired
    private ServerConfig serverConfig;

    public static final String UPLOAD_PATH = "/ruoyi/profileupload/";

    @GetMapping()
    public String zhDeviceuser()
    {
        return "console/AipImageSearch/uploadview";
    }

    @PostMapping("/logoUpload")
    @ResponseBody
    public AjaxResult logoUpload(MultipartFile uploadImg, String personNumber, HttpServletRequest request){
        if (!uploadImg.isEmpty()) {
            try {

                String fileName = uploadImg.getOriginalFilename();
                fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" + fileName;
                String url = serverConfig.getUrl() + UPLOAD_PATH + fileName;;
                String filePath = Global.getUploadPath()+fileName;
                File dest = new File(filePath);

                //判断文件是否已经存在
                if (dest.exists()) {
                    System.out.println("wenjiancunzai");
                }

                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdir();
                }


//上传文件
                uploadImg.transferTo(dest); //保存文件
                System.out.print("保存文件路径"+filePath+"\n");
                System.out.println(url);

//                AipImageSearch client = AipImageSearchObject.getInstance();
//
//                HashMap<String, String> options = new HashMap<String, String>();
//                options.put("brief", personNumber);
//                options.put("tags", "2,1");
//                //本地路径
////                String image = "D:/test001.jpg";
////                JSONObject res = client.sameHqAdd(image, options);
////                System.out.println(res.toString(2));
//
//                //二进制路径
//                byte[] file = uploadImg.getBytes();
//                JSONObject res = client.sameHqAdd(file, options);
//                System.out.println(res.toString(2));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return AjaxResult.error("0000");
    }

}
