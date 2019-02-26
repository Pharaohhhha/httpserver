package com.feng.domain;

import com.feng.common.CommonConstant;
import com.feng.tool.ConfigUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by wangxl6 on 2018/8/24.
 */
public class VersionListener implements Observer{
    private final Logger logger = Logger.getLogger(Observer.class);

    private  HashSet<String> wordSet = new HashSet<>();

    public int getVersion() {
        return version;
    }

    private  int version = 0;

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("VersionListener update...");
        if (o instanceof Version) {
            version = (int) arg;
//            display();
            System.out.println("VersionListener read...");
            File file =new File(ConfigUtil.getPath()+File.separatorChar+ CommonConstant.FILENAME);
            if(!file.exists()){
                return;
            }
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String line = null;
                HashSet<String> set = new HashSet<>();
                while((line = br.readLine())!=null){
                    line = line.trim();
                    if(!"".equals(line)){
                        set.add(line);
                    }
                }
                br.close();
                wordSet = set;
            }catch (Exception e){
                logger.error(e.getMessage(),e);
            }finally {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    public HashSet<String> getWordSet() {
        return wordSet;
    }
}
