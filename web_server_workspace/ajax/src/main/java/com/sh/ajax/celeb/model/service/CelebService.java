package com.sh.ajax.celeb.model.service;

import static com.sh.ajax.common.JdbcTemplate.*;
import java.sql.Connection;
import java.util.List;

import com.sh.ajax.celeb.model.dao.CelebDao;
import com.sh.ajax.celeb.model.vo.Celeb;

public class CelebService {
//	private final CelebManager celebManager = CelebManager.getInstance();
//	
//	public List<Celeb> findAll() {
////		CelebManager celebManager1 = CelebManager.getInstance();
////		CelebManager celebManager2 = CelebManager.getInstance();
////		System.out.println(celebManager1 == celebManager2); // true
//		
//		return celebManager.getCelebs();
//	}
	
	private final CelebDao celebDao = new CelebDao();
	
	public List<Celeb> findAll() {
		Connection conn = getConnection();
		List<Celeb> celebs = celebDao.findAll(conn);
		close(conn);
		return celebs;
	}

	public int insertCeleb(Celeb celeb) {
		Connection conn = getConnection();
		int result = 0;
		try {
			// 1. insert
			result = celebDao.insertCeleb(conn, celeb);
			// 2. 최근 발급된 시퀀스 번호 조회(pk)
			celeb.setNo(celebDao.getLastCelebNo(conn));
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
		return result;
	}

	public Celeb findOne(int no) {
		Connection conn = getConnection();
		Celeb celeb = celebDao.findOne(conn, no);
		close(conn);
		return celeb;
	}

	public int updateCeleb(Celeb celeb) {
		Connection conn = getConnection();
		int result = 0;
		try {
			result = celebDao.updateCeleb(conn, celeb);
			commit(conn);
		} catch(Exception e) {
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
		return result;
	}

	public int deleteCeleb(int no) {
		Connection conn = getConnection();
		int result = 0;
		try {
			result = celebDao.deleteCeleb(conn, no);
			commit(conn);
		} catch(Exception e) {
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
		return result;
	}
}
