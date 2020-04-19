package com.example.demo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class FileController {

    private static final String filePath = "D://test//";

    @RequestMapping("/")
    public String index(ModelMap modelMap) {
        modelMap.addAttribute("msg", "文件上传下载");
        return "index";
    }

    /**
     * 
     * @Description: 单文件上传
     * @param file
     * @return
     * @return: String
     */
    @RequestMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return "文件为空";
        }
        //获取文件名
        String fileName = file.getOriginalFilename();
        System.out.println("文件上传名称：" + fileName);
        String path = filePath + fileName;
        File dest = new File(path);
        //监测是否存在目录
        if (dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (Exception e) {
            e.printStackTrace();
            return "上传失败";
        }

        return "上传成功";
    }

    /**
     * 
     * @Description: 文件批量上传
     * @param request
     * @return
     * @return: String
     */
    @RequestMapping("/batch")
    @ResponseBody
    public String uploadBatch(HttpServletRequest request) {
        List<MultipartFile> fileList = ((MultipartHttpServletRequest) request).getFiles("file");
        MultipartFile file = null;
        BufferedOutputStream outStream = null;
        for (int i = 0; i < fileList.size(); i++) {
            file = fileList.get(i);
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    outStream = new BufferedOutputStream(
                        new FileOutputStream(new File(filePath + file.getOriginalFilename())));
                    outStream.write(bytes);
                } catch (Exception e) {
                    outStream = null;

                } finally {
                    if (outStream != null) {
                        try {
                            outStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            } else {
                return "第" + i + "个文件上传失败，因为是空文件";
            }
        }
        return "文件上传成功";
    }

    @RequestMapping("/download")
    @ResponseBody
    public String downLoadFile(HttpServletResponse response) throws UnsupportedEncodingException {
        String filename = "我们.xlsx";//文件名称
        if (filename != null) {
            //设置文件路径
            File file = new File(filePath + filename);
            if (file.exists()) {
                response.setContentType("application/force-download");
                response.addHeader("Content-Disposition",
                    "attachment;fileName=" + new String(filename.getBytes(), "ISO8859-1"));
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {

                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i;
                    while ((i = bis.read(buffer)) != -1) {
                        os.write(buffer, 0, i);
                        //                        i = bis.read(buffer);
                    }
                    return "下载成功";
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (Exception e2) {
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        return "下载失败";
    }

}
