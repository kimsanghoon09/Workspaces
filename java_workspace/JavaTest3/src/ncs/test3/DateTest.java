package ncs.test3;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 * java.util.Date 국제화이슈 (기본생성자, long타입생성자만 사용)
 * java.util.Calendar
 * 
 * java.time.LocalDate
 * java.time.LocalDateTime
 * java.time.LocalTime
 * 
 * 시간차이 
 * - Duration 두 시각 사이의 차이
 * - Period 두 날짜 사이의 차이
 * - ChronoUnit 날짜/시각 차이
 *
 */
public class DateTest {

	public static void main(String[] args) {
		LocalDate birthday = LocalDate.of(1987, 5, 27);
		LocalDate today = LocalDate.now();
		
		System.out.println(birthday);
		System.out.println(today);

		System.out.println("생일 : " + birthday.format(DateTimeFormatter.ofPattern("yyyy년 M월 dd일 E요일")));
		System.out.println("현재 : " + today.format(DateTimeFormatter.ofPattern("yyyy년 M월 dd일")));

		Period period = birthday.until(today);
		System.out.println(period);
		int age = period.getYears();
		System.out.println("나이 : " + age + "세");
	}

}
