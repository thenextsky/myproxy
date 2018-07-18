package cn.sky.myproxy;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
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
		if(target==null) {
			return null;
		}
		//1.生成java文件
		String proxy_packageString = "cn.sky.myproxy666";
		String proxy_packageString2 = proxy_packageString.replace(".", "/");
		String proxy_simpleName = target.getClass().getSimpleName()+"Proxy";
		String proxy_qualifiedName = proxy_packageString+"."+proxy_simpleName;
		String proxyCode = createProxyCode(proxy_qualifiedName,proxy_simpleName,target,proxy_packageString);
		String rootpath = "D:/myclass";//作为URL的根目录，相当于classpath，编译后的class文件需要放在该目录以下。
		String classfilename = rootpath+"/"+proxy_packageString2+"/"+proxy_simpleName+".java";//编译后的class文件路径，需要完整的包结构，否则报错找不到类。
		FileUtil.writeToFile(classfilename, proxyCode.getBytes());
		//2.编译类
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
		File[] files = {new File(classfilename)};
		Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjects(files);
		CompilationTask task = compiler.getTask(null, fileManager, null, null, null, compilationUnits);
		System.out.println("编译结果："+task.call());
		fileManager.close();
		//3.加载类
		URL url = new URL("file:/"+rootpath);//url:相当于classpath，需要以/结束，否则找不到类；建议用new File("").toURI().toURL()，因为会自动加/
		url = new File(rootpath).toURI().toURL();
		URLClassLoader loader = new URLClassLoader(new URL[] {url});
		Class<?> clazz = loader.loadClass(proxy_qualifiedName);
		loader.close();
		Constructor<?> c = clazz.getConstructor(cn.sky.myproxy.IMan.class);
		return c.newInstance(target);
	}

	private static String createProxyCode(String proxy_qualifiedName, String proxy_simpleName, IMan target, String proxy_packageString) throws InvocationTargetException, Exception {
		String targetQualifiedName = target.getClass().getInterfaces()[0].getName();//暂定只有一个接口
		String proxyCode = 
				"package "+proxy_packageString+";\r\n" + 
				"public class "+proxy_simpleName+" implements "+targetQualifiedName+"{\r\n" + 
				"	private "+targetQualifiedName+" target;\r\n" + 
				"	public "+proxy_simpleName+"("+targetQualifiedName+" target){\r\n" + 
				"		this.target = target;\r\n" + 
				"	}\r\n" + 
				createProxyMethodCode(target)+"\r\n"+
				"}";
		return proxyCode;
	}
	
	private static String createProxyMethodCode(IMan target) throws Exception, InvocationTargetException {
		String proxyMethodCode = "";
		Class<?>[] interfaces = target.getClass().getInterfaces();
		if(interfaces!=null&&interfaces.length>0) {
			for(Class<?> interface0:interfaces) {
				Method[] ms = interface0.getMethods();
				if(ms!=null&&ms.length>0) {
					for(Method m:ms) {
						boolean hasreturn = !m.getReturnType().toString().equals("void");
						String methodString = "";
						String paramString = "";
						String args = "";
						if(m.getParameterCount()>0) {
							Parameter[] parameterTypes = m.getParameters();
							Parameter param = null;
							for(int i=0;i<parameterTypes.length-1;i++) {
								param = parameterTypes[i];
								paramString+=param.getType().getName()+" "+param.getName()+",";
								args+=param.getName()+",";
							}
							param = parameterTypes[parameterTypes.length-1];
							paramString+=param.getType().getName()+" "+param.getName();
							args+=param.getName();
						}
						methodString = 
								"	public "+m.getReturnType().getName()+" "+m.getName()+"("+paramString+"){\r\n"+
								"		System.out.println(\"before\");\r\n";
						if(hasreturn) {
							methodString+=
									"		"+m.getReturnType().getName()+" returnobj = this.target."+m.getName()+"("+args+");\r\n";
						}else {
							methodString+=
									"		this.target."+m.getName()+"("+args+");\r\n";
						}
						methodString+=
								"		System.out.println(\"after\");\r\n" + 
										(hasreturn?"		return returnobj;\r\n":"\r\n")+
								"	}\r\n";
						proxyMethodCode+=methodString;
					}
				}
			}
		}
		return proxyMethodCode;
	}
}
