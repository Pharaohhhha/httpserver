package com.feng.controller;

import com.feng.common.CommonConstant;
import com.feng.Lock.LockEnum;
import com.feng.domain.Term;
import com.feng.domain.Version;
import com.feng.domain.VersionListener;
import com.feng.tool.ConfigUtil;
import com.feng.tool.Util;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by wangxl6 on 2018/8/20.
 */
@Controller
public class ExpandWordsController {

    static final int MAX_SIZE = 1024000000;
    private static VersionListener listener;

    private static Version verison ;
    static {
        System.out.println("init ....");
        File versionFile = new File(ConfigUtil.getPath()+File.separator+CommonConstant.VERSIONFILE);
        if (versionFile.exists()){
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(versionFile)));
                verison =  new Version(Integer.valueOf(br.readLine()));
                br.close();
            }catch (Exception e){
                verison = new Version(0);
            }finally {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else{
            verison = new Version(0);
        }
        listener  = new VersionListener();
        verison.addObserver(listener);
    }
    private static final Logger logger = Logger.getLogger(ExpandWordsController.class);


    @RequestMapping(value = "append.do", method = RequestMethod.POST)
    public void addExpandWord(@RequestBody  String str,HttpServletRequest request, HttpServletResponse response) {

        try {
            String word = new JSONObject(str).getString("word");
            word = word.replace("\n", "\r\n");
            if("".equals(word)||word==null){
                return;
            }
            LockEnum.LOCK.getLock().lock();//此lock效果和synchronized一样，都可以同步执行，lock方法获得锁，unlock方法释放锁
            String path = ConfigUtil.getPath()+File.separator+ CommonConstant.FILENAME ;//File.separator 分隔符，代表（/或\）适用于windows和linux
            // path为词集文件的地址
            File wfile = new File(path);
            //File.separatorChar与File.separator类似
            File writelock = new File(ConfigUtil.getPath()+File.separatorChar+CommonConstant.WRITE_LOCK);//expendwords所在文件写锁
            writelock.createNewFile();//如果不存在则创建文件，存在就忽略
            System.out.println(path);
            FileOutputStream fos = new FileOutputStream(wfile,true);
            OutputStreamWriter os= new OutputStreamWriter(fos,"utf-8");
            PrintWriter pw = new PrintWriter(new BufferedWriter(os));
            //校验
            if(!wfile.getParentFile().exists()){ //判断文件父目录是否存在
                wfile.getParentFile().mkdir();
            }

            pw.println(word);
            pw.close();
            PrintWriter pw1 = new PrintWriter(ConfigUtil.getPath()+File.separator+CommonConstant.VERSIONFILE);
            pw1.println(verison.incrementAndGet());
            pw1.close();
            writelock.delete();
            response.getWriter().print("succeed!");
            LockEnum.LOCK.getLock().unlock();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "update.do", method = RequestMethod.POST)

    public void  updateWordsTxt(@RequestParam("fileName") MultipartFile file,  HttpServletResponse response) {//MultipartFile 实现文件上传的类
//        System.out.println("222");
        if(file.isEmpty()){
            return;
        }
        try {
            String filecharset = Util.getFilecharset(file.getInputStream());//获得文件编码
            System.out.println("charset --" + filecharset);
            if(!"UTF-8".equals(filecharset)){//如果不是UTF-8就不进行读取。
                response.getOutputStream().print("ERROR! This Filecharset is not UTF-8, please change the Filecharset");
                return;
            }
            LockEnum.LOCK.getLock().lock();//此lock效果和synchronized一样，都可以同步执行，lock方法获得锁，unlock方法释放锁
            String path = ConfigUtil.getPath()+File.separator+ CommonConstant.FILENAME ;//File.separator 分隔符，代表（/或\）适用于windows和linux
            // path为词集文件的地址
            File wfile = new File(path);
                                                                            //File.separatorChar与File.separator类似
            File writelock = new File(ConfigUtil.getPath()+File.separatorChar+CommonConstant.WRITE_LOCK);//expendwords所在文件写锁
            writelock.createNewFile();//如果不存在则创建文件，存在就忽略
            System.out.println(file.getOriginalFilename());
            System.out.println(path);
            FileOutputStream fos = new FileOutputStream(wfile,true);//在文件里追加内容
            OutputStreamWriter os= new OutputStreamWriter(fos,"utf-8");
            PrintWriter pw = new PrintWriter(new BufferedWriter(os));
            //校验
            if(!wfile.getParentFile().exists()){ //判断文件父目录是否存在
                wfile.getParentFile().mkdir();
            }

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            String line =null;
            while((line= bufferedReader.readLine())!=null){
                line = line.trim();//可以去掉两端的空格，换行符等，换行符不等于换行
                if(!"".equals(line)){
                    pw.println(line);//一行一行的写到pw里面
                }
            }

            pw.close();
            bufferedReader.close();
            PrintWriter pw1 = new PrintWriter(ConfigUtil.getPath()+File.separator+CommonConstant.VERSIONFILE);
            pw1.println(verison.incrementAndGet());
            pw1.close();
            writelock.delete();
//            writelock.deleteOnExit();
            response.getWriter().print("succeed!");

            LockEnum.LOCK.getLock().unlock();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @RequestMapping(value = "/getWords", method = RequestMethod.GET)
    public ResponseEntity<String> wordlist() {
//        long time = System.currentTimeMillis();
//        //校验
//        String str = "各地校车\n" +
//                "留给伊拉克\n" +
//                "亚裔男子";
//        System.out.println(str);
        HttpHeaders headers = new HttpHeaders();
//        headers.add("Last-Modified", String.valueOf(time));
        System.out.println(listener.getVersion());
        headers.add("ETag", listener.getVersion()+"");
//        headers.add("Last-Modified",);
//        ResponseEntity.ok().headers();
        StringBuffer sb = new StringBuffer();
        HashSet<String> wordSet= listener.getWordSet();//从扩展词文件中读取了数据
        Iterator<String> iterator = wordSet.iterator();
        while (iterator.hasNext()){
            sb.append(iterator.next().trim());
            sb.append("\n");
        }
        String body =  sb.lastIndexOf("\n") != -1 ?sb.subSequence(0,sb.lastIndexOf("\n")).toString():sb.toString();//去掉最后一个/n
        System.out.println( body);
        System.out.println("--------------");
        return ResponseEntity.ok().
                headers(headers).
//                body("各地校车\r\n" +
//                        "留给伊拉克\r\n" +
//                        "亚裔男子");
        body(body);
//        ArrayList
//        BigDecimal b = new BigDecimal("123");
    }
}
