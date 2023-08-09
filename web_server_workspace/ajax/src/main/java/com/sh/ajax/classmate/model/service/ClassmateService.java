package com.sh.ajax.classmate.model.service;

import java.util.ArrayList;
import java.util.List;

import com.sh.ajax.classmate.model.manager.ClassmateManager;
import com.sh.ajax.classmate.model.vo.Classmate;

public class ClassmateService {
	private final ClassmateManager classmateManager = ClassmateManager.getInstance();
	
	public List<Classmate> findAll() {
		return classmateManager.getClassmates();
	}

	public List<Classmate> findByName(String term) {
		List<Classmate> classmates = findAll();
		List<Classmate> results = new ArrayList<>();
		for(Classmate classmate : classmates) {
			// String#contains: boolean 문자열 포함여부
//			if(classmate.getName().contains(term)) {
			if(classmate.getName().indexOf(term) > -1) {
				results.add(classmate);
			}
		}
		return results;
	}
}
