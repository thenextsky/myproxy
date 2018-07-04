package cn.sky.myproxy.test1;

import cn.sky.myproxy.bean.Animal;

public class AnimalProxy extends Animal{
	private Animal target;
	private AnimalProxy() {}
	public AnimalProxy(Animal target) {
		this.target = target;
	}

	@Override
	public void run() {
		System.out.println("before");
		target.run();
		System.out.println("after");
	}
}
