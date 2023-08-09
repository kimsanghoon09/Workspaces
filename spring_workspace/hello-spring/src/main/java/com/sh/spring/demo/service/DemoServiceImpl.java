package com.sh.spring.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sh.spring.demo.dto.DevCreateDto;
import com.sh.spring.demo.dto.DevUpdateDto;
import com.sh.spring.demo.entity.Dev;
import com.sh.spring.demo.repository.DemoRepository;

/**
 * 
 * @Service
 * - @Component 상속
 * - mvc구조의 service클래스 등록(트랜잭션처리 가능)
 */
@Service
public class DemoServiceImpl implements DemoService {
	@Autowired
	private DemoRepository demoRepository;
	
	@Override
	public int insertDev(DevCreateDto devDto) {
		return demoRepository.insertDev(devDto);
	}
	
	@Override
	public List<Dev> findAllDev() {
		return demoRepository.findAllDev();
	}
	
	@Override
	public Dev findDevById(int id) {
		return demoRepository.findDevById(id);
	}

	@Override
	public int updateDev(DevUpdateDto dev) {
		// TODO Auto-generated method stub
		return demoRepository.updateDev(dev);
	}

	@Override
	public int deleteDev(int id) {
		// TODO Auto-generated method stub
		return demoRepository.deleteDev(id);
	}
	


}
