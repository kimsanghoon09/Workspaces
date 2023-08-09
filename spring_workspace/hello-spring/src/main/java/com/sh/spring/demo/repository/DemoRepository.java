package com.sh.spring.demo.repository;

import java.util.List;

import javax.validation.Valid;

import com.sh.spring.demo.dto.DevCreateDto;
import com.sh.spring.demo.dto.DevUpdateDto;
import com.sh.spring.demo.entity.Dev;

public interface DemoRepository {

	int insertDev( DevCreateDto devDto);

	List<Dev> findAllDev();

	Dev findDevById(int id);

	int updateDev(DevUpdateDto dev);

	int deleteDev(int id);


}

