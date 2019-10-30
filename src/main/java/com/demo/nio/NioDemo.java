package com.demo.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author zeng
 * <p>
 * nio包下常用方法
 */
public class NioDemo {

    public static void main(String[] args) throws Exception {

        //
//        readWrite();
        readWriteUtils();
    }

    /**
     * nio 使用普通方式 读取加载到缓存中
     **/
    public static void readWrite() throws Exception {
        File file = new File("D:\\var\\11");
        //获取该文件下所有文件
        String[] fileList = file.list();
        for (String s : fileList) {
            String fileName = file.getPath() + "\\" + s;
            //判断后缀
            String fileSuffix = s.substring(s.indexOf("."));
            if (".txt".equals(fileSuffix)) {

                String outFileName = s.substring(0, s.indexOf("."));
                //读取文件
                FileInputStream readFile = new FileInputStream(fileName);
                //写入文件
                FileOutputStream outFile = new FileOutputStream(file.getPath() + "\\" + outFileName + ".bat");
                //获取通道
                FileChannel readChannel = readFile.getChannel();
                FileChannel outChannel = outFile.getChannel();

                // 3. 创建缓冲区对象
                ByteBuffer buff = ByteBuffer.allocate(1024);
                while (true) {
                    int readNumber = readChannel.read(buff);
                    if (readNumber == -1) {
                        break;
                    }
                    // 5. 传出数据准备：调用flip()方法
                    buff.flip();

                    // 6. 从 Buffer 中读取数据 & 传出数据到通道
                    outChannel.write(buff);

                    // 7. 重置缓冲区
                    buff.clear();
                }
            }

        }
    }

    /**
     * nio 读取文件 使用File和Path工具
     */
    public static void readWriteUtils() throws Exception {
        File file = new File("D:\\var\\11");
        //获取该文件下所有文件
        String[] fileList = file.list();
        for (String s : fileList) {
            String fileName = file.getPath() + "\\" + s;
            //判断后缀
            String fileSuffix = s.substring(s.indexOf("."));
            if (".txt".equals(fileSuffix)) {
                String outFileName = file.getPath() + "\\" + s.substring(0, s.indexOf(".")) + ".bat";

                Path readPath = Paths.get(fileName);
                Path writePath = Paths.get(outFileName);
                //copy文件
//                Files.copy(readPath, writePath);
                //读取文件
                Files.write(writePath, Files.readAllBytes(readPath));
            }
        }
    }
}
