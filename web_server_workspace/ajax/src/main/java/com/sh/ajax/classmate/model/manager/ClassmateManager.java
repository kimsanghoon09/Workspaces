package com.sh.ajax.classmate.model.manager;

import java.util.Arrays;
import java.util.List;

import com.sh.ajax.classmate.model.vo.Classmate;

/**
 * 싱글턴패턴 
 * - 생성자
 * - 필드
 * - getInstance 
 *
 */
public class ClassmateManager {
	private static ClassmateManager INSTANCE;
	private List<Classmate> classmates;
	
	private ClassmateManager () {
		this.classmates = Arrays.asList(
				new Classmate("강선모"),
				new Classmate("김나영"),
				new Classmate("김담희"),
				new Classmate("김대원"),
				new Classmate("김동찬"),
				new Classmate("김상훈"),
				new Classmate("김윤아"),
				new Classmate("김준한"),
				new Classmate("김창환"),
				new Classmate("남현우"),
				new Classmate("박주혜"),
				new Classmate("배종호"),
				new Classmate("신종환"),
				new Classmate("신희진"),
				new Classmate("양소영"),
				new Classmate("유성근"),
				new Classmate("이경빈"),
				new Classmate("이장준"),
				new Classmate("이태현"),
				new Classmate("이혜령"),
				new Classmate("전수경"),
				new Classmate("전예라"),
				new Classmate("정건룡"),
				new Classmate("정민희"),
				new Classmate("정상윤"),
				new Classmate("한미루"),
				new Classmate("홍승영"),
				new Classmate("황대호")
			);
	}
	
	public static ClassmateManager getInstance () {
		if(INSTANCE == null) {
			INSTANCE = new ClassmateManager();
		}
		return INSTANCE;
	}
	
	public List<Classmate> getClassmates() {
		return classmates;
	}
}
