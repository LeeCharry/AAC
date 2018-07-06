package com.shyohan.aac.widget;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.shyohan.aac.constant.AppConstant;


public class Log
{
	private static Log _lg;
	//single 
	public static Log Instance(){
		if(_lg == null){
			_lg = new Log();
		}
		return _lg;
	}
	
	public int logSaveNum=10;
	
	public void WriteLog(String msg){
		System.out.println("日志信息" + msg);
		if(LOG_SWITCH){
			File file = checkLogFileIsExist();
			if(file == null)
				return;
			FileOutputStream fos = null;
			try
			{
				fos = new FileOutputStream(file, true);
				fos.write((new Date().toLocaleString() + "	" + msg).getBytes("gbk"));
				fos.write("\r\n".getBytes("gbk"));
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			finally{
				try
				{
					if(fos != null){
						fos.close();
						fos = null;
					}
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				fos = null;
				file = null;
			}
		}
	}
	/**
	 * 写入异常信息日志
	 * @param e
	 * @return
	 */
	public void WriteStackTrace(Throwable e){
        if(e != null){
            WriteLog(e.getStackTrace().toString());
        }
    }
	
	public void WriteLog(String head , Throwable ex , String tail){
		try {
			String result = null;
			Writer info = new StringWriter();
			PrintWriter printWrite = new PrintWriter(info);
			ex.printStackTrace(printWrite);

			Throwable cause = ex.getCause();
			while (cause != null) {
				cause.printStackTrace(printWrite);
				cause = cause.getCause();
			}

			result = info.toString();
			WriteLog(head+result+tail);
			printWrite.close();
		}
		catch(Exception e){
			e.printStackTrace();
			WriteLog("writeLog error");
		}
	}
	
	public void WriteLog(Throwable ex){
		WriteLog("", ex, "");
	}
	
	
	private Log()
	{
		CheckLogDele();
		
	}
	
	private void CheckLogDele()
	{
	}
	
	/**写入日志的地址*/
	private static final String LOG_SAVE_PATH = AppConstant.PARENTDIR+"crash/";
	/**是否打开写日志的标志位*/
	private static final boolean LOG_SWITCH = true;		
	
	/**检查当天日志是否存在*/
	private File checkLogFileIsExist(){
		File file = new File(LOG_SAVE_PATH);
		if(!file.exists()){
			file.mkdirs();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(new Date());
		file = new File(LOG_SAVE_PATH + dateStr + ".txt");
		if(!isLogExist(file)){
			try
			{
				file.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		sdf = null;
		return file;
	}
	
	/**
	 * 判断当前目录下是否存在日志信息
	 * @param file
	 * @return
	 */
	private boolean isLogExist(File file){
		File tempFile = new File(LOG_SAVE_PATH);
		File[] files = tempFile.listFiles();
		if(files!=null){
			for(int i = 0; i < files.length; i++){
				if(files[0].getName().trim().equalsIgnoreCase(file.getName())){
					return true;
				}
			}
		}
		return false;
	}

}