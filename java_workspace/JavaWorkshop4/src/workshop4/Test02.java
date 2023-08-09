package workshop4;

import java.util.Arrays;

public class Test02 {

	public static void main(String[] args) {
		new Test02().option();
	}

	public void option() {
		int[] arr = new int[5];
		int sum = 0;
		outer: for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) (Math.random() * 10 + 1);
			System.out.println(arr[i]);
			for (int j = 0; j < i; j++) {
				if (arr[i] == arr[j]) {
					i--;
					continue outer;
				}
			}
			sum += arr[i];
		}
		System.out.println(Arrays.toString(arr));
		System.out.println("sum: " + sum + ", avg: " + (double) (sum / arr.length));
	}
}
