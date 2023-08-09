package com.sh.member.view;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

import com.sh.member.controller.MemberController;
import com.sh.member.model.dto.Member;
import com.sh.member.model.dto.MemberDel;

public class MemberMenu {
	
	private Scanner sc = new Scanner(System.in);
	// 의존객체 생성
	private MemberController memberController = new MemberController();

	public void mainMenu() {
		String menu = "===== 회원관리 프로그램 =====\n"
					+ "1. 전체조회\n"
					+ "2. 아이디조회\n"
					+ "3. 이름검색\n"
					+ "4. 회원가입\n"
					+ "5. 회원정보변경\n"
					+ "6. 회원탈퇴\n"
					+ "7. 탈퇴회원조회\n"
					+ "0. 프로그램종료\n"
					+ "=========================\n"
					+ "선택 : ";
		
		while(true) {
			System.out.print(menu);
			String choice = sc.next();
			
			List<Member> memberList = null;
			Member member = null;
			int result = 0;
			String name = null;
			String id = null;
			
			switch(choice) {
			case "1" : 
				memberList = memberController.findAll();
				displayMemberList(memberList);
				break;
			case "2" : 
				id = inputId();
				member = memberController.findById(id);
				displayMember(member);
				break;
			case "3" : 
				name = inputName();
				memberList = memberController.findByName(name);
				displayMemberList(memberList);
				break;
			case "4" : 
				member = inputMember();
				result = memberController.insertMember(member);
				displayResult("회원가입", result);
				break;
			case "5" : 
				 updateMenu();
				 break;
			case "6" : 
				id = inputId();
				result = memberController.deleteMember(id);
				displayResult("회원탈퇴", result);
				break;
			case "7" : 
				// del_date컬럼값도 함께 출력할 것.
				displayMemberDelList(memberController.findAllMemberDel());
				break;
			case "0" : return; // 현재메소드를 호출한 곳으로 return
			default: System.out.println("> 잘못 입력하셨습니다.");
			}
			
		}
		
	}
	
	/**
	 * 회원정보 수정 메뉴 
	 * 1. 수정메뉴 보이기전에 입력한 사용자 id에 해당하는 정보를 조회/출력
	 * 		- 조회된 회원이 없다면 메인메뉴로 돌아갈것
	 * 2. 선택한 메뉴에 해당하는 컬럼을 수정처리
	 * 3. 다시 수정메뉴가 보이기전에 해당 회원 정보 출력
	 */
	private void updateMenu() {
		String menu = "+++++++++ 회원 정보수정 +++++++++\n"
					+ "1. 이름수정\n"
					+ "2. 생일수정\n"
					+ "3. 이메일수정\n"
					+ "0. 메인메뉴로 돌아가기\n"
					+ "++++++++++++++++++++++++++++++\n"
					+ "선택 : ";
		
		String id = inputId();
		
		while(true) {
			Member member = memberController.findById(id);
			if(member == null) {
				System.out.println("> 조회된 회원이 없습니다.");
				return;
			}
			else {
				displayMember(member);
			}
			
			System.out.print(menu);
			String choice = sc.next();
			String colName = null;
			Object newValue = null; // String, Date도 동시에 처리하기 위함.
			
			switch(choice) {
			case "1" : 
				System.out.print("> 변경할 이름 : ");
				colName = "name";
				newValue = sc.next();
				break;
			case "2" : 
				System.out.print("> 변경할 생일 (19990909) : ");
				LocalDate temp = LocalDate.parse(sc.next(), DateTimeFormatter.ofPattern("yyyyMMdd"));
				colName = "birthday";
				newValue = Date.valueOf(temp); // java.time.LocalDate -> java.sql.Date
				break;
			case "3" : 
				System.out.print("> 변경할 이메일 : ");
				colName = "email";
				newValue = sc.next();
				break;
			case "0" : return;
			default : 
				System.out.println("잘못 입력하셨습니다.");
				continue;
			}
			
			int result = memberController.updateMember(id, colName, newValue);
			System.out.println(result > 0 ? "> 회원정보 수정 성공!" : "> 회원정보 수정 실패!");
		}
		
	}
	
	/**
	 * 1건의 조회결과를 출력하는 메소드
	 * @param member
	 */
	private void displayMember(Member member) {
		if(member == null) {
			System.out.println("> 조회된 회원이 없습니다.");
		}
		else {
			System.out.println("--------------------------------");
			System.out.println("아이디 : " + member.getId());
			System.out.println("이름 : " + member.getName());
			System.out.println("성별 : " + member.getGender());
			System.out.println("생일 : " + member.getBirthday());
			System.out.println("이메일 : " + member.getEmail());
			System.out.println("포인트 : " + member.getPoint());
			System.out.println("등록일 : " + member.getRegDate());
			System.out.println("--------------------------------");
		}
	}
	
	/**
	 * 아이디를 입력받는 메소드
	 * @return
	 */
	private String inputId() {
		System.out.print("> 아이디 입력 : ");
		return sc.next();
	}
	
	/**
	 * 검색할 이름 입력
	 * @return
	 */
	private String inputName() {
		System.out.print("> 조회할 회원 이름 입력 : ");
		return sc.next();
	}

	/**
	 * 자바객체
	 * 1. 매개변수생성자
	 * 2. 기본생성자 + setter값대입
	 * 
	 * @return
	 */
	private Member inputMember() {
		Member member = new Member();
		
		System.out.println("> 회원정보를 입력하세요.");
		member.setId(checkIdDuplicate()); // 중복아이디체크
		System.out.print("> 이름 : ");
		member.setName(sc.next());
		System.out.print("> 성별(M/F) : ");
		member.setGender(sc.next());
		System.out.print("> 생일(19900909) : ");
		String temp = sc.next();
		// Calendar객체생성 -> java.sql.Date변환
		int year = Integer.parseInt(temp.substring(0, 4));
		int month = Integer.parseInt(temp.substring(4, 6));
		int date = Integer.parseInt(temp.substring(6));
		Calendar cal = new GregorianCalendar(year, month - 1, date); // 0 ~ 11월
		long millis = cal.getTimeInMillis(); // utc초 - 1970년 1월 1일 자정이후로 누적된 밀리초
		Date birthday = new Date(millis);
		member.setBirthday(birthday);
		System.out.print("> 이메일 : ");
		member.setEmail(sc.next());
		return member;
	}
	
	private String checkIdDuplicate() {
        String id = null;
        do {
            if(id != null)
                System.out.printf("> [%s]는 이미 사용중입니다.%n", id);
            System.out.print("> 아이디 : ");
            id = sc.next();
        } while(memberController.findById(id) != null);
        return id;
    }

	/**
	 * DML 처리결과를 출력하는 메소드 
	 * @param name
	 * @param result
	 */
	private void displayResult(String name, int result) {
		if(result > 0)
			System.out.println("> " + name + " 성공!");
		else 
			System.out.println("> " + name + " 실패!");
	}

	/**
	 * n건의 조회결과를 출력하는 메소드
	 * @param memberList
	 */
	private void displayMemberList(List<Member> memberList) {
		if(memberList == null || memberList.isEmpty()) {
			System.out.println("> 조회된 결과가 없습니다.");
		}
		else {
			System.out.println("--------------------------------------------------------------------------------------------");
			System.out.printf("%-10s %-10s %-10s %-10s %-20s %-10s %-20s\n", 
							  "id", "name", "gender", "birthday", "email", "point", "regDate");
			System.out.println("--------------------------------------------------------------------------------------------");
			for(Member member : memberList) {
				System.out.printf("%-10s %-10s %-10s %-10s %-20s %-10s %-20s\n", 
						  member.getId(), member.getName(), member.getGender(),
						  member.getBirthday(), member.getEmail(), member.getPoint(), 
						  new SimpleDateFormat("yy-MM-dd HH:mm").format(member.getRegDate()));
			}
			System.out.println("--------------------------------------------------------------------------------------------");
			
		}
	}

	/**
	 * 조회된 n행의 탈퇴회원정보를 출력메소드
	 * @param list
	 */
	private void displayMemberDelList(List<MemberDel> list) {
		if(list.isEmpty()) {
			// 조회된 행이 없는 경우
			System.out.println("> 조회된 행이 없습니다.");
		}
		else {
			// 조회된 행이 있는 경우
			System.out.println("-------------------------------------------------------------------------------------------------------------------------");
			System.out.printf("%-10s %-10s %-10s %-10s %-20s %-20s %-20s %n", 
							  "id", "name", "gender", "birthday", "email", "regDate", "delDate");
			System.out.println("-------------------------------------------------------------------------------------------------------------------------");
			
			for(MemberDel m : list) {
				System.out.printf("%-10s %-10s %-10s %-10s %-20s %-20s %-20s%n", 
								  m.getId(), 
								  m.getName(), 
								  "M".equals(m.getGender()) ? "남" : "여", 
								  m.getBirthday(), 
								  m.getEmail(),  
								  new SimpleDateFormat("yy-MM-dd HH:mm").format(m.getRegDate()),
								  new SimpleDateFormat("yy-MM-dd HH:mm").format(m.getDelDate()));
			}
			
			System.out.println("-------------------------------------------------------------------------------------------------------------------------");
		}
	}
}







