package cn.sky.myproxy;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import cn.sky.myproxy.util.FileUtil;

public class MyProxy {
	private MyProxy() {
	}

	public static Object newProxyInstance(cn.sky.myproxy.IMan target) throws IOException, Exception {
		//1.生成java文件
		String className = "cn.sky.myproxy.ManProxy";
		String proxyCode = 
				"package cn.sky.myproxy;\r\n" + 
				"public class ManProxy implements cn.sky.myproxy.IMan{\r\n" + 
				"	private cn.sky.myproxy.IMan target;\r\n" + 
				"	public ManProxy(cn.sky.myproxy.IMan target){\r\n" + 
				"		this.target = target;\r\n" + 
				"	}\r\n" + 
				"	public void eat(){\r\n" + 
				"		System.out.println(\"before\");\r\n" + 
				"		this.target.eat();\r\n" + 
				"		System.out.println(\"after\");\r\n" + 
				"	}\r\n" + 
				"}";
		String rootpath = "D:/myclass";//作为URL的根目录，相当于classpath，编译后的class文件需要放在该目录以下。
		String classfilename = rootpath+"/cn/sky/myproxy/ManProxy.java";//编译后的class文件路径，需要完整的包结构，否则报错找不到类。
		FileUtil.writeToFile(classfilename, proxyCode.getBytes());
		//2.编译类
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
		File[] files = {new File(classfilename)};
		Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjects(files);
		CompilationTask task = compiler.getTask(null, fileManager, null, null, null, compilationUnits);
		System.out.println(task.call());
		fileManager.close();
		//3.加载类
		URL url = new URL("file:/"+rootpath);//url:相当于classpath，需要以/结束，否则找不到类；建议用new File("").toURI().toURL()，因为会自动加/
		System.out.println(url);//-->file:/D:/myclass
		url = new File(rootpath).toURI().toURL();
		System.out.println(url);//-->file:/D:/myclass/
		URLClassLoader loader = new URLClassLoader(new URL[] {url});
		Class<?> clazz = null;
//		clazz = Class.forName(className);//报错，找不到类，因为class文件在d盘，不在classpath下
		clazz = loader.loadClass(className);
		loader.close();
		Constructor<?> c = clazz.getConstructor(cn.sky.myproxy.IMan.class);
		return c.newInstance(target);
	}
}
