package com.meiya.utils.File;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Enumeration;

public class FileUtil {

    protected static Logger log = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 压缩文件
     * @param inputFileName 要压缩的文件或文件夹路径
     * @param outputFileName 输出zip文件的路径
     */
    public static void zip(String inputFileName, String outputFileName) throws IOException {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outputFileName));
        zip(out,new File(inputFileName),"");
    }

    /**
     * 压缩文件
     * @param out org.apache.tools.zip.ZipOutputStream
     * @param file 待压缩的文件
     * @param base 压缩的根目录
     */
    private static void zip(ZipOutputStream out, File file, String base) throws IOException {
        if(file.isDirectory()) {
            File[] fList = file.listFiles();
            base = base.length() == 0? "" : base+File.separator;
            for (int i = 0;i < fList.length; i++) {
                zip(out,fList[i],base + fList[i].getName());
            }
        } else {
            out.putNextEntry(new ZipEntry(base));  //写入此目录的Entry  这句应该默认是将文件压入zip文件的根目录下
            FileInputStream in = new FileInputStream(file);
            int b;
            while((b = in.read())!=-1) {
                out.write(b);
            }
        }


    }

    /**
     * 解压zip文件
     * @param zipFileName 待解压的zip文件路径
     * @param outputDirectory
     * @throws IOException
     */
    public static void unZip(String zipFileName, String outputDirectory) throws IOException {
        ZipFile zipFile = new ZipFile(zipFileName);
        try{
        Enumeration<?> e = zipFile.getEntries();
        ZipEntry zipEntry = null;
        createDirectory(outputDirectory, "");
        while (e.hasMoreElements()) {
            zipEntry = (ZipEntry) e.nextElement();
            log.debug("解压：" + zipEntry.getName());
            if (zipEntry.isDirectory()) {
                String name = zipEntry.getName();
                name = name.substring(0, name.length() - 1);
                File f = new File(outputDirectory + File.separator + name);
                f.mkdir();
                log.debug("创建目录：" + outputDirectory + File.separator + name);
            } else {
              String fileName = zipEntry.getName();
              fileName = fileName.replace("\\", "/");
              if (fileName.indexOf("/") != -1) {
                  createDirectory(outputDirectory, fileName.substring(0, fileName.lastIndexOf("/")));
                  fileName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.length());
              }
              File f = new File(outputDirectory + File.separator + zipEntry.getName());
              f.createNewFile();
              InputStream in = zipFile.getInputStream(zipEntry);
              FileOutputStream out = new FileOutputStream(f);
              byte[] by = new byte[1024];
              int c;
              while((c = in.read(by)) != -1) {
                  out.write(by, 0, c);
                  out.flush();
              }
              out.close();
              in.close();
            }
        }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            zipFile.close();
            log.debug("解压完成");
        }
    }


    private static void createDirectory(String directory, String subDirectory) {
        String dir[] = {};
        File f1 = new File(directory);
        if (subDirectory == "" && f1.exists() != true) {
            f1.mkdir();
        } else if (subDirectory != "") {
            dir = subDirectory.replace("\\", "/").split("/");
        }
        for (int i = 0;i < dir.length; i++) {
            File subFile;
            if (directory.endsWith(File.separator)) {
                subFile = new File(directory + dir[i]);
            } else {
                subFile = new File(directory + File.separator + dir[i]);
            }
            if (subFile.exists() == false) {
                subFile.mkdir();
            }
            directory += File.separator + dir[i];
        }
    }


    /**
     * 拷贝文件夹中的所有文件到另外一个文件夹
     * @param srcDirector 源文件夹
     * @param desDirector 目标文件夹
     */
    public static void copyFileWithDirector(String srcDirector, String desDirector) throws IOException {
        (new File(desDirector)).mkdirs();
        File[] file = (new File(srcDirector)).listFiles();
        if(file == null) {
            throw new NullPointerException("file为空指针");
        }
        for (int i = 0;i < file.length;i++) {
            if (file[i].isFile()) {
                log.debug("拷贝：" + file[i].getAbsolutePath() + "-->" + desDirector + "/" + file[i].getName());
                FileInputStream fileInputStream = new FileInputStream(file[i]);
                FileOutputStream fileOutputStream = new FileOutputStream(desDirector + "/" + file[i].getName());
                byte[] b = new byte[1024*5];
                int len;
                while((len = fileInputStream.read(b)) != -1) {
                    fileOutputStream.write(b, 0 ,len);
                }
                fileOutputStream.flush();
                fileOutputStream.close();
                fileInputStream.close();
            }
            if (file[i].isDirectory()) {
                log.debug("拷贝：" + file[i].getAbsolutePath() + "-->" + desDirector + "/" + file[i].getName());
                copyFileWithDirector(srcDirector + "/"+file[i].getName(), desDirector + "/" + file[i].getName());
            }
        }
    }

    /**
     * 删除文件夹
     * @param folderPath 文件夹完整路径
     */
    public static void delFolder(String folderPath) {
        delAllFile(folderPath);
        String filePath = folderPath;
        filePath = filePath.toString();
        File myFilePath = new File(filePath);
        myFilePath.delete();
    }

    /**
     * 删除指定文件夹下所有文件
     * @param path 文件夹完整路径
     */

    public static boolean delAllFile(String path) {
        boolean flag  = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }

        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0;i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }

            if(temp.isFile()) {
                temp.delete();
            }

            if(temp.isDirectory()) {
                //先删除文件夹里面的文件
                delAllFile(path + "/" + tempList[i]);
                delFolder(path + "/" + tempList[i]);
                flag = true;
            }
        }
        return flag;
    }
}

















































































