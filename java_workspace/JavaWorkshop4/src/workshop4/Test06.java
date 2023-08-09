package workshop4;

class Test06 {
	public static void main(String[] args) {
		if (args.length < 2 || args.length > 2) {
			System.out.println("다시입력하세요");
			return;
		}
		int inx = Integer.parseInt(args[0]);
		int jnx = Integer.parseInt(args[1]);
		if (inx > 5 || inx < 1 || jnx > 5 || jnx < 0) {
			System.out.println("숫자를 확인하세요");
			return;
		}
		int[][] arr = new int[inx][jnx];
		double sum = 0;
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++) {
				arr[i][j] = (int) (Math.random() * 5) + 1;
			}
		}
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++) {
				System.out.print(arr[i][j] + " ");
				sum += arr[i][j];
			}
			System.out.println("");
		}
		System.out.println(" ");
		System.out.println("sum=" + sum);
		System.out.println("avg=" + sum / (arr.length + arr[0].length));
	}
}
