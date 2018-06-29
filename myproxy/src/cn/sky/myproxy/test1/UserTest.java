package cn.sky.myproxy.test1;

import org.junit.Test;

import cn.sky.myproxy.SuperTest;
import cn.sky.myproxy.bean.User;

public class UserTest extends SuperTest {
	@Test
	public void test1() {
		User user = new User("呵呵1");
		user.printName();
	}

	@Test
	public void test2() {
		User user = new User("呵呵1");
		UserProxy proxy = new UserProxy(user);
		proxy.printName();
	}
}
