package com.sidney.devlib.utils;

import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;

/**
 * 文件计算工具类
 */
public class FileUtil {

    private static FileUtil inst;

    public static FileUtil getInstance() {
        if (inst == null) {
            inst = new FileUtil();
        }
        return inst;
    }

    /**
     * 获取指定文件夹内所有文件大小的和
     *
     * @param file file
     * @return size
     */
    public long getFolderSize(File file) {
        long size = 0;
        File[] fileList = file.listFiles();
        for (File aFileList : fileList) {
            if (aFileList.isDirectory()) {
                size = size + getFolderSize(aFileList);
            } else {
                size = size + aFileList.length();
            }
        }
        return size;
    }

    /**
     * 删除指定目录下的文件，这里用于缓存的删除
     *
     * @param filePath       filePath
     * @param deleteThisPath deleteThisPath
     */
    public void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {
                    File files[] = file.listFiles();
                    for (File file1 : files) {
                        deleteFolderFile(file1.getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {
                        file.delete();
                    } else {
                        if (file.listFiles().length == 0) {
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 格式化单位
     *
     * @param size size
     * @return size
     */
    public String getFormatSize(long size) {

        long kiloByte = size / 1024;
//        if (kiloByte < 1) {
//            return size + "Byte";
//        }

        long megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Long.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        long gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Long.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        long teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Long.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);

        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    /**
     * 复制
     *
     * @param delete 目标文件存在，是否删除替换
     * @throws Exception
     * @from 源
     * @to 目标
     */
    public void copy(File inFile, File outFile, boolean delete) throws Exception {
        copy(new FileInputStream(inFile), outFile, delete);
    }

    /**
     * 复制
     *
     * @param delete 目标文件存在，是否删除替换
     * @throws Exception
     * @from 源
     * @to 目标
     */
    public void copy(InputStream in, File outFile, boolean delete) throws Exception {
        if (outFile.exists() && delete == false) {
            // 存在且不删除，则不复制
            return;
        }
        OutputStream out = new FileOutputStream(outFile);
        copy(in, out);
    }

    /**
     * 重命名
     *
     * @param from   原文件
     * @param to     目标文件
     * @param delete 如果目标文件存在，是否删除
     */
    public void rename(String from, String to, boolean delete) {
        rename(new File(from), new File(to), delete);
    }

    /**
     * 重命名
     *
     * @param from   原文件
     * @param to     目标文件
     * @param delete 如果目标文件存在，是否删除
     */
    public void rename(File from, File to, boolean delete) {
        if (!from.exists()) {
            return;
        }
        boolean isRename = false;
        if (to.exists()) {
            if (delete) {
                to.delete();
                isRename = true;
            } else {
                isRename = false;
            }
        } else {
            isRename = true;
        }
        if (isRename) {
            File parent = to.getParentFile();
            if (!parent.exists()) {
                parent.mkdirs();
            }
            from.renameTo(to);
        }
    }

    /**
     * 复制
     *
     * @param in  源
     * @param out 目标
     * @throws Exception
     */
    private void copy(InputStream in, OutputStream out) throws Exception {
        BufferedInputStream bis = new BufferedInputStream(in);
        BufferedOutputStream bos = new BufferedOutputStream(out);
        byte[] buffer = new byte[10 * 1024];
        while (bis.read(buffer) != -1) {
            bos.write(buffer, 0, buffer.length);
        }
        bis.close();
        bos.close();
    }

    public void deleteFile(String file) {
        File f = new File(file);
        if (!f.isDirectory()) {
            f.deleteOnExit();
        }
    }

    public void deleteDir(String file) {
        File f = new File(file);
        if (f.isDirectory()) {
            File[] files = f.listFiles();
            if (files != null) {
                for (File ff : files) {
                    if (ff.isDirectory()) {
                        deleteDir(ff.getAbsolutePath());
                    } else {
                        deleteFile(ff.getAbsolutePath());
                    }
                }
            }
        } else {
            deleteFile(f.getAbsolutePath());
        }
    }
}