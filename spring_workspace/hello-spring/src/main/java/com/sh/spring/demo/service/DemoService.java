package com.sh.spring.demo.service;

import java.util.List;

import com.sh.spring.demo.dto.DevCreateDto;
import com.sh.spring.demo.dto.DevUpdateDto;
import com.sh.spring.demo.entity.Dev;

public interface DemoService {

	int insertDev(DevCreateDto devDto);

	List<Dev> findAllDev();

	Dev findDevById(int id);

	int updateDev(DevUpdateDto dev);

	int deleteDev(int id);

}
