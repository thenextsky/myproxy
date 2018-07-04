package cn.sky.myproxy.test1;

import org.junit.Test;

import cn.sky.myproxy.bean.Animal;

public class AnimalTest {
	@Test
	public void test1() {
		Animal a = new Animal();
		a.run();
	}
	@Test
	public void test2() {
		Animal a = new Animal();
		AnimalProxy proxy = new AnimalProxy(a);
		proxy.run();
	}
}
