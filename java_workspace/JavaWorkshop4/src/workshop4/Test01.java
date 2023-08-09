package workshop4;

public class Test01 {

	public static void main(String[] args) {

		int[][] arr2 = { { 5, 5 }, { 10, 10, 10, 10, 10 }, { 20, 20, 20 }, { 30, 30, 30, 30 } };

		int sum = 0;
		int len = 0;
		for (int[] i : arr2) {
			len += i.length;
			for (int j : i) {
				sum += j;
			}
		}
		double avg = sum / len;

		System.out.println("sum = " + sum + "\navg = " + avg);
	}

}
