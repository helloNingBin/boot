package tools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    /**
     * 返回目录下所有的文件，不包含文件夹
     * @param direFile 要查询的文件夹
     * @param fileList 用于接收结果
     * @return
     */
    public static List<File> findAllList(File direFile,List<File> fileList){
        return findAllList(direFile,fileList,null);
    }
    /**
     * 返回目录下所有的文件，不包含文件夹
     * @param direFile 要查询的文件夹
     * @param fileList 用于接收结果
     * @param filterStr 只选择带该后缀的文件
     * @return
     */
    public static List<File> findAllList(File direFile,List<File> fileList,String filterStr){
        //查询出当下所有的文件
        File[] listFiles = direFile.listFiles();
        for(File file : listFiles){
            if(file.isFile()){
                if(filterStr == null || file.getName().endsWith(filterStr)){
                    fileList.add(file);
                }
            }else{
                findAllList(file,fileList,filterStr);
            }
        }
        return fileList;
    }
}
