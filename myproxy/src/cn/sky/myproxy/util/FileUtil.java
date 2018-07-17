package cn.sky.myproxy.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {
	public static void writeToFile(String filepath,byte[] data) {
		File file = new File(filepath);
		File parent = file.getParentFile();
		if(!parent.exists()) {
			if(!parent.mkdirs()) {
				throw new RuntimeException("创建路径失败！");
			}
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file,false);
			fos.write(data);
			fos.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(fos!=null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
