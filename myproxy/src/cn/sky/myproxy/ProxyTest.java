package cn.sky.myproxy;

import org.junit.Test;

import cn.sky.myproxy.test.SuperTest;

public class ProxyTest extends SuperTest{
	@Test
	public void test1() throws Exception {
		IMan man = new Man();
		Object o = MyProxy.newProxyInstance(man);
		man = (IMan) o;
		man.eat();
	}
}
