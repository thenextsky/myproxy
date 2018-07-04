package cn.sky.myproxy.test2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory {
	private static ProxyFactory factory = new ProxyFactory();
	private ProxyFactory() {}
	public static ProxyFactory getInstance() {
		return factory;
	}
	public <T>T createProxy(T target) {
		InvocationHandler invocationHandler = new MyInvocationHandler<T>(target);
		Object o = Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), invocationHandler);
		@SuppressWarnings("unchecked")
		T proxy = (T) o;
		return proxy;
	}
	
	class MyInvocationHandler<T> implements InvocationHandler{
		T target;
		MyInvocationHandler(T target){
			this.target = target;
		}
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			System.out.println("before");
			Object o = method.invoke(target, args);
			System.out.println("after");
			return o;
		}
	}
}
