package cn.sky.myproxy.test1;

import org.junit.Test;

import cn.sky.myproxy.SuperTest;

public class UserTest extends SuperTest {
	@Test
	public void test1() {
		User user = new User("呵呵1");
		String name = user.getName();
		System.out.println(name);
	}

	@Test
	public void test2() {
		User user = new User("呵呵1");
		UserProxy proxy = new UserProxy(user);
		System.out.println(proxy.getName());
	}
}
