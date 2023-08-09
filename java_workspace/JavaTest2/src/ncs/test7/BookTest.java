package ncs.test7;

public class BookTest {

	public static void main(String[] args) {
		Book bookArray [] = new Book[5];
		
		// Book 객체를 5개 생성하여 배열에 넣는다 .
		bookArray[0] = new Book("IT","SQL Plus", 50000, .05);
		bookArray[1] = new Book("IT","Java 2.0", 	40000, .03);
		bookArray[2] = new Book("IT","JSP Servlet", 60000, .06);
		bookArray[3] = new Book("Novel","davincicode", 30000, .1);
		bookArray[4] = new Book("Novel","cloven hoof", 50000, .15);
				
		// 배열에 있는 객체 정보를 실행결과 형식으로 모두 출력한다. – for loop를 이용할 것
		// 합계를 출력한다 .
		double sum = 0;
		for (int i = 0; i < bookArray.length; i++) {
			System.out.printf("%s %s %.1f원 %.1f%% %.1f %n", 
							  bookArray[i].getCategory(), 
							  bookArray[i].getBookName(), 
							  bookArray[i].getBookPrice(), 
							  bookArray[i].getBookDiscountRate() * 100,
							  bookArray[i].getBookPrice() * (1 - bookArray[i].getBookDiscountRate()));
			
			// 실제 판매금액 : 가격 - (1 - 할인율)
			sum += bookArray[i].getBookPrice() * (1 - bookArray[i].getBookDiscountRate());
		}
		System.out.println("책가격들의 합 : "+sum+"원");
		
	}

}
