public class A extends B {
	// 静态变量
	static int i = 1;
	// 静态语句块
	static {
		System.out.println("Class A1:static blocks" + i);
	}
	// 非静态变量
	int j = 1;
	// 静态语句块
	static {
		i++;
		System.out.println("Class A2:static blocks" + i);
	}

	// 构造函数
	public A() {
		super();
		i++;
		j++;
		System.out.println("constructor A: " + "i=" + i + ",j=" + j);
	}

	// 非静态语句块
	{
		i++;
		j++;
		System.out.println("Class A:common blocks" + "i=" + i + ",j=" + j);
	}

	// 非静态方法
	public void aDisplay() {
		i++;
		System.out.println("Class A:static void aDisplay():	" + "i=" + i
				+ ",j=" + j);
		return;
	}

	// 静态方法
	public static void aTest() {
		i++;
		System.out.println("Class A:static void aTest():	" + "i=" + i);
		return;
	}

	/**
	 *  1.若要加载类A，则先加载执行其父类B(Object)的静态变量以及静态语句块(执行先后顺序按排列的先后顺序)。
		2.然后再加载执行类A的静态变量以及静态语句块。(并且1、2步骤只会执行1次)
		3.若需实例化类A，则先调用其父类B的构造函数,并且在调用其父类B的构造函数前,依次先调用父类B中的非静态变量及非静态语句块.最后再调用父类B中的构造函数初始化。
		4.然后再依次调用类A中的非静态变量及非静态语句块.最后调用A中的构造函数初始化。( 并且3、4步骤可以重复执行)
		5.而对于静态方法和非静态方法都是被动调用,即系统不会自动调用执行,所以用户没有调用时都不执行,主要区别在于静态方法可以直接用类名直接调用(实例化对象也可以),而非静态方法只能先实例化对象后才能调用。
	 * @param args
	 */
	public static void main(String[] args) {
		
		A a = new A();
		a.aDisplay();
	}

}
