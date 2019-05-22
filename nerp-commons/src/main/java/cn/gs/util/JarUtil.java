package cn.gs.util;

import java.io.File;
import java.util.jar.JarFile;
import java.util.zip.ZipException;

/**
 * 分析jar包是否损坏
 * @author wangshaodong
 * 2019年05月13日
 */
public class JarUtil {
	/**
	 * 分析jar包是否损坏
	 *
	 * @param path
	 *            jar文件所在目录或jar文件路径
	 * @throws Exception
	 */
	public static void analyze(String path) {
		File dir = new File(path);
		File[] files = null;
		if (dir.isDirectory()) {
			files = new File[] { dir };
		} else {
			files = dir.listFiles();
		}

		for (File file : files) {
			try {
				JarFile jarFile = new JarFile(file);
				jarFile.getManifest();
				jarFile.close();
			} catch (ZipException e) {
				System.out.println("损坏的jar文件：" + file.getName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
