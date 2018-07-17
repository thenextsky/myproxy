package cn.sky.myproxy.test.test2;

import org.junit.Test;

import cn.sky.myproxy.test.SuperTest;
import cn.sky.myproxy.test.bean.User;

public class ProxyTest extends SuperTest{
	@Test
	public void test1() {
		Runnable user = new User("呵呵1");
		Runnable r =  ProxyFactory.getInstance().createProxy(user);
		r.run();
//		User u = ProxyFactory.getInstance().createProxy(new User("呵呵1"));//运行时发生类型转换异常
	}
}
