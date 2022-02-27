package com.lxc.tim.Controller;


import org.springframework.web.bind.annotation.RestController;

import io.minio.MinioClient;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletResponse;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class MinioController {
    private static String url = "";  //minio服务的IP端口
    private static String accessKey = "";
    private static String secretKey = "";

    @RequestMapping("/uploadheadpic")
    public String upload(MultipartFile file ,String username)  {
        try {
         //   String username =(String)session.getAttribute("username");
            String a = file.getContentType();  //类型
            if (a.equals("image/jpeg") || a.equals("image/bmp")|| a.equals("image/jpg")|| a.equals("image/png")) {
            MinioClient minioClient = new MinioClient(url, accessKey, secretKey);//创建客户端对象
            InputStream is= file.getInputStream(); //得到文件流
           // String fileName = file.getOriginalFilename(); //文件名
            String contentType = file.getContentType();  //类型
            minioClient.putObject("test",username,is,contentType); //把文件放置Minio桶(文件夹)
            return "上传成功";}
            else{
                return "上传图片类型jpg/png/bmp";
            }
        }catch (Exception e){
            return "上传失败";
        }
    }

    //聊天时发送图片

    @RequestMapping("/uploadchatpic")
    public String uploadchatpic(MultipartFile file)  {

        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String s =dateFormat.format(date);
        try {
            //   String username =(String)session.getAttribute("username");
            String a = file.getContentType();  //类型
            if (a.equals("image/jpeg") || a.equals("image/bmp")|| a.equals("image/jpg")|| a.equals("image/png")) {
                MinioClient minioClient = new MinioClient(url, accessKey, secretKey);//创建客户端对象
                InputStream is= file.getInputStream(); //得到文件流
                // String fileName = file.getOriginalFilename(); //文件名
                String contentType = file.getContentType();  //类型
                minioClient.putObject("chatpic",s,is,contentType); //把文件放置Minio桶(文件夹)
                return s;}
            else{
                return "上传图片类型jpg/png/bmp";
            }
        }catch (Exception e){
            return "上传失败";
        }
    }

    @GetMapping("/downloadchatpic")
    public String downloadchatpic(HttpServletResponse response ,String filename){

        try {
            MinioClient minioClient = new MinioClient(url, accessKey, secretKey);


            InputStream fileInputStream = minioClient.getObject("chatpic", filename);
            response.setHeader("Content-Disposition", "attachment;filename=" + filename+".jpg");
            response.setContentType("application/force-download");
            response.setCharacterEncoding("UTF-8");
            IOUtils.copy(fileInputStream,response.getOutputStream());
            return "下载完成";
        }catch (Exception e){
            return "下载失败";
        }
    }



    @RequestMapping("/uploadmp3")
    public String uploadmp3(MultipartFile file)  {
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String s =dateFormat.format(date);
        try {
            //   String username =(String)session.getAttribute("username");
            String a = file.getContentType();  //类型
          if (a.equals("audio/mpeg") ) {
                MinioClient minioClient = new MinioClient(url, accessKey, secretKey);//创建客户端对象
                InputStream is= file.getInputStream(); //得到文件流
                // String fileName = file.getOriginalFilename(); //文件名
                String contentType = file.getContentType();  //类型
                minioClient.putObject("timfile",s,is,contentType); //把文件放置Minio桶(文件夹)
                return s;
            }
            else{
                return "上传文件类型mp3";
            }
        }
        catch (Exception e){
            return "上传失败";
        }
    }

    @RequestMapping("/uploadmp4")
    public String uploadmp4(MultipartFile file )  {
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String s =dateFormat.format(date);
        try {
            //   String username =(String)session.getAttribute("username");
            String a = file.getContentType();  //类型
            if (a.equals("video/mp4") ) {
                MinioClient minioClient = new MinioClient(url, accessKey, secretKey);//创建客户端对象
                InputStream is= file.getInputStream(); //得到文件流
                // String fileName = file.getOriginalFilename(); //文件名
                String contentType = file.getContentType();  //类型
                minioClient.putObject("timfilemp4",s,is,contentType); //把文件放置Minio桶(文件夹)
                return s;
            }
            else{
                return "上传文件类型mp4";
            }
        }
        catch (Exception e){
            return "上传失败";
        }
    }
    //下载minio服务的文件
    @GetMapping("/downloadmp4")
    public String download4(HttpServletResponse response ,String filename){

        try {
            MinioClient minioClient = new MinioClient(url, accessKey, secretKey);


            InputStream fileInputStream = minioClient.getObject("timfilemp4", filename);
            response.setHeader("Content-Disposition", "attachment;filename=" + filename+".mp4");
            response.setContentType("application/force-download");
            response.setCharacterEncoding("UTF-8");
            IOUtils.copy(fileInputStream,response.getOutputStream());
            return "下载完成";
        }catch (Exception e){
            return "下载失败";
        }
    }

    //下载minio服务的文件
    @GetMapping("/downloadmp3")
    public String download(HttpServletResponse response ,String filename){

        try {
            MinioClient minioClient = new MinioClient(url, accessKey, secretKey);


            InputStream fileInputStream = minioClient.getObject("timfile", filename);
            response.setHeader("Content-Disposition", "attachment;filename=" + filename+".mp3");
            response.setContentType("application/force-download");
            response.setCharacterEncoding("UTF-8");
            IOUtils.copy(fileInputStream,response.getOutputStream());
            return "下载完成";
        }catch (Exception e){
            return "下载失败";
        }
    }

    @GetMapping("/headpicurl")
    public String  getUrl(String username){
        try {


            MinioClient minioClient = new MinioClient(url, accessKey, secretKey);
            String url = minioClient.presignedGetObject("test", username);

            return url;
        }catch (Exception e){
            return null;
        }
    }

}
