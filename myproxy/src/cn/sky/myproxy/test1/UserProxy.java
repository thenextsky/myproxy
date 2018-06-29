package cn.sky.myproxy.test1;

public class UserProxy extends User {
	private User target;

	private UserProxy() {}
	public UserProxy(User target) {
		this.target = target;
	}

	@Override
	public String getName() {
		long t1 = System.currentTimeMillis();
		String name = target.getName();
		long t2 = System.currentTimeMillis();
		System.out.println("it takes time :"+(t2-t1)+"ms");
		return name;
	}
}
