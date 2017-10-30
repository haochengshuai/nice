package haocs.utils.reflect.entity;

public class Test {

	public static final String str = "Test.....str";
	private static final String strPri = "Test.....str";

	public final String tr = "Test.....str";
	private final String trPri = "Test.....str";

	public static void getPubStatic(Long ins) {
		System.out.println(strPri + ins);
	}

	public static void getPubStatic() {
		System.out.println(strPri);
	}

	private static void getPriStatic() {
		System.out.println(strPri);
	}

	public void getPubNotSta() {
		System.out.println(str);
	}

	private void getPriNotSta() {
		System.out.println(strPri);
	}

}
