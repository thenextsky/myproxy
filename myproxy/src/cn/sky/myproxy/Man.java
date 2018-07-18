package cn.sky.myproxy;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Man implements IMan {

	@Override
	public void eat() {
		System.out.println("Man.eat()");
	}

	@Override
	public String go(String name, int age) {
		return name+":"+age;
	}

	@Override
	public Map<String, Object> getMap(String name, Integer age, Set<IMan> set) {
		Map<String,Object> m = new HashMap<String, Object>();
		System.out.println(name);
		System.out.println(age);
		System.out.println(set);
		return m;
	}

}
