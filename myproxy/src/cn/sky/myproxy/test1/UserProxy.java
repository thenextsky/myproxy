package cn.sky.myproxy.test1;

import cn.sky.myproxy.bean.User;

public class UserProxy extends User {
	private User target;

	private UserProxy() {}
	public UserProxy(User target) {
		this.target = target;
	}

	@Override
	public void printName() {
		System.out.println("before");
		target.printName();
		System.out.println("after");
	}
}
