package tools;

import java.io.*;
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

    public static void main(String[] args) throws IOException {
        splitTextFile("C:\\Users\\chichao\\Desktop\\log\\catalina.out","C:\\Users\\chichao\\Desktop\\log\\t",100);
    }

    /**
     *
     * @param fullSourceFileName  全路径
     * @param targetFileName      仅文件名就可以
     * @throws IOException
     */
    public static void splitTextFile(String fullSourceFileName,String targetFileName,int mb) throws IOException {
        File file = new File(fullSourceFileName);
        System.out.println(file.exists());
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        System.out.println(file.length());
        int fileIndex = 0;
        FileWriter fileWriter = null;
        File targetFile = null;
        while (true){
//            if(fileWriter == null || fileWriter.)
            if(targetFile == null || targetFile.length() > (mb * 1000 * 1000)){
                targetFile = new File(targetFileName + fileIndex + ".txt");
                fileIndex++;
                if(fileWriter != null){
                    fileWriter.flush();
                }
                fileWriter = new FileWriter(targetFile);
            }
            String newLine = bufferedReader.readLine();
            System.out.println("newLine:" + newLine);
            if(newLine != null){
                fileWriter.write(newLine);
                fileWriter.write("\n");
            }else{
                fileWriter.flush();
                break;
            }
        }
    }
}
