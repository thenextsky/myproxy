package cn.sky.myproxy;

import java.util.Map;
import java.util.Set;

public interface IMan {
	void eat();
	String go(String name,int age);
	Map<String,Object> getMap(String name,Integer age,Set<IMan> set);
}
