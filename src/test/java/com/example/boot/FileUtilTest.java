package com.example.boot;

import org.junit.jupiter.api.Test;
import tools.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtilTest {
    @Test
    public void findAllListTest(){
        File file = new File("G:/BaiduNetdiskDownload");
        List<File> fileList = new ArrayList<File>();
        List<File> allList = FileUtil.findAllList(file, fileList,".java");
        for (File f : allList){
            System.out.println(f.getName());
        }
    }
}
