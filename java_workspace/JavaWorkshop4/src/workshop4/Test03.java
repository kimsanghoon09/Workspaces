package workshop4;

public class Test03 {

	public static void main(String[] args) {
		new Test03().test();
	}

	public void test() {
		int[] arr = { 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 };
		for (int i = 0; i < 10; i++) {
			System.out.print(arr[arr.length - 1 - i] + " ");
		}
	}

}
