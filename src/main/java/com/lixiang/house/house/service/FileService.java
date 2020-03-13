package com.lixiang.house.house.service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

/**
 * @author lixiang1234_李祥
 * @site www.lixiang.com
 * @create 2020-03-09 13:32
 */
@Service
public class FileService {

    @Value("${file.path}")
    private String filePath;

    //获取图片的路径
    public List<String> getImgPath(List<MultipartFile> files){
        if (Strings.isNullOrEmpty(filePath)) {
            filePath = getResourcePath();
        }
        List<String> paths = Lists.newArrayList();
        for (MultipartFile file : files) {
            File localFile = null;
            if (!file.isEmpty()){
                try {
                    localFile = saveToLocal(file, filePath);
                    String path = StringUtils.substringAfterLast(localFile.getAbsolutePath(), filePath);
                    paths.add(path);
                }catch (Exception e){
                    throw new IllegalArgumentException(e);
                }
            }
        }
        return paths;
    }

    public static String getResourcePath(){
        File file = new File(".");
        String absolutePath = file.getAbsolutePath();
        return absolutePath;
    }

    private File saveToLocal(MultipartFile file, String filePath) throws IOException {
        //instant.now().getEpochSecond()获得一个时间戳
        File newFile = new File(filePath + "\\" + Instant.now().getEpochSecond() + "\\" + file.getOriginalFilename());
        if (!newFile.exists()){
            newFile.getParentFile().mkdirs();
            newFile.createNewFile();
        }
        Files.write(file.getBytes(),newFile);
        return newFile;
    }
}
