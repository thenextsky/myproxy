package cn.sky.myproxy.bean;

public class User implements Runnable{
	private String name;

	public void printName() {
		System.out.println(name);
	}

	public User() {}
	public User(String name) {
		this.name = name;
	}

	@Override
	public void run() {
		System.out.println("run...");
	}

}
