package cn.sky.myproxy.test;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;

public class SuperTest {
	@Rule
	public TestName name = new TestName();
	
	@Before
	public void begin() {
		System.out.println("↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓"+name.getMethodName());
	}
	@After
	public void end() {
		System.out.println("↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑"+name.getMethodName());
	}
}
