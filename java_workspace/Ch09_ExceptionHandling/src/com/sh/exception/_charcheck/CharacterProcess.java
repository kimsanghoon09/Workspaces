package com.sh.exception._charcheck;

import com.sh.exception.charcheck.CharCheckException;

public class CharacterProcess {
	
	/**
	 * 전달된 문자열에서 알파벳(소문자/대문자)의 개수를 세어서 반환
	 * @param str
	 * @return
	 */
	public int countAlpha(String str) {
		int count = 0;
		for(int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			
			if(ch == ' ')
				throw new CharCheckException("체크할 문자열 안에 공백을 포함할 수 없습니다.");
			
//			if((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z'))
//			if(isUpperCase(ch) || isLowerCase(ch))
			if(isAlphabetic(ch))
				count++;
		}
		
		return count;
	}
	
	private boolean isAlphabetic(char ch) {
		return isUpperCase(ch) || isLowerCase(ch);
	}

	public boolean isUpperCase(char ch) {
		return (ch >= 'A' && ch <= 'Z');
	}
	public boolean isLowerCase(char ch) {
		return (ch >= 'a' && ch <= 'z');
	}
}
