package ncs.test4;

import java.util.Arrays;

public class Test4 {

	public static void main(String[] args) {
		int[] numArr = new int[5];
		for (int i = 0; i < numArr.length; i++) {
			numArr[i] = (int) (Math.random() * 50) + 51; // 51 ~ 100 난수
		}
		System.out.println(Arrays.toString(numArr));
		
		new Test4().selectionSort(numArr);
//		new Test4().sequentialSort(numArr);
		System.out.println(Arrays.toString(numArr));
	}
	
	private void print(int[] arr, int nth) {
		System.out.printf("%d : %s\n", nth, Arrays.toString(arr));
	}

	/**
	 * 순차정렬 : 회차에 해당하는 인덱스에 알맞은 수를 찾음(오름차순) 한 회차가 끝나면, 그 자리는 정렬이 완료됨.
	 * 
	 * @param arr
	 */
	private void sequentialSort(int[] arr) {
		// 0,1,2,3번지까지만 순회(마지막은 자동정렬)
		for (int i = 0; i < arr.length - 1; i++) {
			// 현재회차 다음인덱스부터 검사시작.
			for (int j = i + 1; j < arr.length; j++) {
				if (arr[i] < arr[j])// 내림차순
					swap(arr, i, j);
				
				print(arr, i);
			}
		}
	}

	/**
	 * 선택정렬 : 매 회차에 최소값에 해당하는 변수를 선정하고, 이를 차례로 비교해서 정렬함. 보다 작은수가 발견되면, swap이 일어나면,
	 * 해당수가 최소값이 됨.
	 * 
	 * 데이터의 양이 적을 때 성능 좋음(교환회수가 적음) 하지만 배열을 전부 탐색하여 최소값을 찾아야 하기 때문에 100개 이상의 자료에서는
	 * 급격하게 속도가 저하된다. innerLoop 순회후에 min과 현재자리(i)의 자리교환이 일어남.
	 * 
	 * @param arr
	 */
	private void selectionSort(int[] arr) {
		int min; // 최소값을 가진 데이터의 인덱스 저장 변수

		for (int i = 0; i < arr.length - 1; i++) {
			min = i;
			// 해당 인덱스 다음 인덱스부터 끝까지 반복을 한다
			for (int j = i + 1; j < arr.length; j++) {
				// i의 인덱스 보다 작은 값이 있는 인덱스의 경우 min을 변경한다
				if (arr[min] < arr[j]) {
					min = j;
				}
			}
			swap(arr, min, i);
			print(arr, i);
		}
	}

	public void swap(int[] arr, int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

}
