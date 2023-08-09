package ncs.test8;

import ncs.test7.Book;

/**
 * 실행클래스
 * 
 * @author user1
 *
 */
public class BookStore {

	public static void main(String[] args) {
		Book bookdata; // 관리할 책객체
		BookUpdater bookupdater;  // 책 관리자(가격 갱신)

		// Book 객체 생성  
		bookdata = new Book("IT","HTML5",30000, .15);
		
		// Book 객체 기본 정보 출력  
		System.out.println("기본정보");
		System.out.println(bookdata.getBookName() + "\t" + bookdata.getBookPrice());
		
		// BookUpdate 객체 생성 ( 생성시 Book 객체를 생성자를 통해 셋팅 )
		bookupdater = new BookUpdater(bookdata);
		
		// updateBookPrice 함수를 통해 Book 객체의 할인율을 적용시켜 가격 변경
		bookupdater.updateBookPrice(); // 실제 판매가격으로 갱신
		
         // Book 객체의 변경된 정보 출력  
		System.out.println("변경된정보");
		System.out.println(bookdata.getBookName() + "\t" + bookdata.getBookPrice());


	}

}
