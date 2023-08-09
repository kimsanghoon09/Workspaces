package com.sh.ajax.celeb.model.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.sh.ajax.celeb.model.exception.CelebException;
import com.sh.ajax.celeb.model.vo.Celeb;
import com.sh.ajax.celeb.model.vo.CelebType;

public class CelebDao {
	private Properties prop = new Properties();
	
	public CelebDao() {
		String filename = 
				CelebDao.class.getResource("/sql/celeb/celeb-query.properties").getPath();
		try {
			prop.load(new FileReader(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	public List<Celeb> findAll(Connection conn) {
		List<Celeb> celebs = new ArrayList<>();
		String sql = prop.getProperty("findAll");
		
		try (
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rset = pstmt.executeQuery();
		) {
			while(rset.next()) {
				Celeb celeb = handleCelebResultSet(rset);
				celebs.add(celeb);
			}
		} catch (SQLException e) {
			throw new CelebException(e);
		}
		return celebs;
	}

	private Celeb handleCelebResultSet(ResultSet rset) throws SQLException {
		int no = rset.getInt("no");
		String name = rset.getString("name");
		String profile = rset.getString("profile");
		CelebType celebType = null;
		if (rset.getString("celeb_type") != null) {
			celebType = CelebType.valueOf(rset.getString("celeb_type"));
		}
		return new Celeb(no, name, profile, celebType);
	}

	public int insertCeleb(Connection conn, Celeb celeb) {
		int result = 0;
		String sql = prop.getProperty("insertCeleb");
		try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, celeb.getName());
			pstmt.setString(2, celeb.getProfile());
			pstmt.setString(3, celeb.getCelebType().name());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CelebException(e);
		}
		return result;
	}

	public int getLastCelebNo(Connection conn) {
		int celebNo = 0;
		String sql = prop.getProperty("getLastCelebNo");
		
		try(
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rset = pstmt.executeQuery();
		) {
			if(rset.next())
				celebNo = rset.getInt(1);
		} catch (SQLException e) {
			throw new CelebException(e);
		}		
		return celebNo;
	}

	public Celeb findOne(Connection conn, int no) {
		Celeb celeb = null;
		String sql = prop.getProperty("findOne");
		try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, no);
			try(ResultSet rset = pstmt.executeQuery()) {
				while(rset.next())
					celeb = handleCelebResultSet(rset);
			}
		} catch(SQLException e) {
			throw new CelebException(e);
		}
		return celeb;
	}

	public int updateCeleb(Connection conn, Celeb celeb) {
		int result = 0;
		String sql = prop.getProperty("updateCeleb");
		try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, celeb.getName());
			pstmt.setString(2, celeb.getProfile());
			pstmt.setString(3, celeb.getCelebType().toString());
			pstmt.setInt(4, celeb.getNo());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CelebException(e);
		}
		
		return result;
	}

	public int deleteCeleb(Connection conn, int no) {
		int result = 0;
		String sql = prop.getProperty("deleteCeleb");
		try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, no);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CelebException(e);
		}
		return result;
	}
	
}
