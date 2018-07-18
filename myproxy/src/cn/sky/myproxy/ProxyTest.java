package cn.sky.myproxy;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import cn.sky.myproxy.test.SuperTest;

public class ProxyTest extends SuperTest{
	@Test
	public void test1() throws Exception {
		IMan man = new Man();
		Object o = MyProxy.newProxyInstance(man);
		man = (IMan) o;
		man.eat();
		man.go("hehe", 11);
		Set<IMan> set = new HashSet<IMan>();
		set.add(new Man());
		set.add(new Man());
		man.getMap("hehe", 11, set );
	}
}
