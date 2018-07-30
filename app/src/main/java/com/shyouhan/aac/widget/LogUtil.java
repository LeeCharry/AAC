package com.shyouhan.aac.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;


import com.shyouhan.aac.base.BaseApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 类名：日志工具类
 * 主要实现功能：记录程序中所有日志
 *
 * @author 李小平
 *         创建时间：2014-6-4
 */
@SuppressLint("SimpleDateFormat")
public class LogUtil {
    public static LogUtil logUtil;
    private Context con;
    private final boolean isPorductionVersion = false;// 是否为生产版本
    private final boolean isLogEncrypt = false;// 日志是否加密

    public static LogUtil getInstance(Context context) {
        if (logUtil == null) {
            logUtil = new LogUtil(context.getApplicationContext());
        }
        return logUtil;
    }

    public LogUtil(Context context) {
        con = context;
    }

    /**
     * debug日志记录
     *
     * @param content 日志内容
     */
    public void debug(String content) {
        writeLog(content, "debug");
    }

    /**
     * info日志记录
     *
     * @param content 日志内容
     */
    public void info(String content) {
        writeLog(content, "info");
    }

    /**
     * warning日志记录
     *
     * @param content 日志内容
     */
    public void warning(String content) {
        writeLog(content, "warning");
    }

    /**
     * error日志记录
     *
     * @param content 日志内容
     */
    public void error(String content) {
        writeLog(content, "error");
    }

    /**
     * 将日志内容写入文件
     *
     * @param msg   日志内容
     * @param level 日志层级
     */
    private void writeLog(String msg, String level) {
        //判断是否存在Sdcard
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (msg == null)
                return;
            File file = checkLogFileIsExist();
            if (file == null)
                return;
            if (level.equalsIgnoreCase("debug")) {
                if (isPorductionVersion) {
                    return;
                }
            }

            FileOutputStream fos = null;
            try {
                StackTraceElement[] stacks = new Throwable().getStackTrace();
                String className = stacks[2].getClassName();
                String methodName = stacks[2].getMethodName();
                int lineNumber = stacks[2].getLineNumber();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateStr = sdf.format(new Date());

                fos = new FileOutputStream(file, true);
                String content = dateStr + "[" + level + "]" + " " + className
                        + ":" + methodName + "(" + lineNumber + "行) --- " + msg;
                if (isLogEncrypt) {
                    try {
//						content = datades.encryptDES(content);
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                        e.printStackTrace();
                        LogUtil.getInstance(con).info(e.getMessage());
                    }
                }
                fos.write(content.getBytes());
                fos.write("\r\n".getBytes());
                System.out.println("记录日志：" + content);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                e.printStackTrace();
                LogUtil.getInstance(con).info(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                e.printStackTrace();
                LogUtil.getInstance(con).info(e.getMessage());
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                        fos = null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    e.printStackTrace();
                    LogUtil.getInstance(con).info(e.getMessage());
                }
            }
        }
    }

    /**
     * 判断日志文件是否存在
     *
     * @return 日志文件
     */
    private File checkLogFileIsExist() {

        File file = new File(BaseApp.savePath + "/log/");
        if (!file.exists()) {
            file.mkdirs();
        }
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
        file = new File(BaseApp.savePath + "/log/" + date + ".txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                e.printStackTrace();
                LogUtil.getInstance(con).info(e.getMessage());
            }
        }
        return file;
    }
}