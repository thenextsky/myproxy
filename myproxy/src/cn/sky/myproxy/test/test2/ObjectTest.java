package cn.sky.myproxy.test.test2;

import org.junit.Test;

public class ObjectTest {
	@Test
	public void test1() {
		Object o = new Object();
		o = ProxyFactory.getInstance().createProxy(o);
		o.getClass();//native method cannot use proxy
		o.equals("");
		o.hashCode();
		o.toString();
		Class<?>[] cs = o.getClass().getInterfaces();
		System.out.println(cs.length);
		//Object是没有实现任何接口的，但是也能使用jdk的动态代理。
	}
	@Test
	public void test2() throws Throwable {
		//换言之，任何对象都可以使用jdk的动态代理，只是，代理的方法只有Object的这几个方法，没什么暖用
		Object t = new ObjectTest();
		t = ProxyFactory.getInstance().createProxy(t);
		t.toString();
	}
}
