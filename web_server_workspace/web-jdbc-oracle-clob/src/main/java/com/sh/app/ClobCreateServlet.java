package com.sh.app;

import static com.sh.app.common.JdbcTemplate.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sh.app.news.entity.News;

/**
 * Servlet implementation class ClobTestServlet
 */
@WebServlet("/create")
public class ClobCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = getConnection();
		try {
			int result = 0;
			try (PreparedStatement pstmt = conn
					.prepareStatement("insert into news values(seq_news_id.nextval, empty_clob())")) {
				result = pstmt.executeUpdate();
			}
			Integer id = null;
			try (PreparedStatement pstmt = conn.prepareStatement("select seq_news_id.currval from dual")) {
				try (ResultSet rset = pstmt.executeQuery()) {
					if (rset.next()) {
						id = rset.getInt(1);
						System.out.println(id);
					}
				}
			}
			if (id != null) {
				try (PreparedStatement pstmt = conn.prepareStatement("select content from news where id = ? for update")) {
					pstmt.setInt(1, id);
					try (ResultSet rset = pstmt.executeQuery()) {
						if (rset.next()) {
							// 1. dbcp 미사용시 
							// OracleResultSet#getCLOB:CLOB - CLOB#getCharacterOutputStream() 사용
//							CLOB clob = ((OracleResultSet) rset).getCLOB("content");
//							try (BufferedWriter bw = new BufferedWriter(clob.getCharacterOutputStream())) {
//								bw.write("뉴우우우으으으으으으으으으스으으으으으");							
//							}
							// 2. dbcp사용시
							// org.apache.tomcat.dbcp.dbcp2.DelegatingResultSet은 oracle.jdbc.OracleResultSet로 형변환 불가
							Clob clob =	rset.getClob("content"); 
							try (BufferedWriter bw = new BufferedWriter(clob.setCharacterStream(0))) {
								bw.write(getBigContent()); // content 값 쓰기							
							}

						}
					}
				}
			}
		
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		close(conn);
	}
	private String getBigContent() {
		StringBuilder sb = new StringBuilder();
		while(sb.length() < 1000000)
			sb.append((char) (new Random().nextInt(26) + 97));
		return sb.toString();
	}

}
