package com.sh.app;

import static com.sh.app.common.JdbcTemplate.close;
import static com.sh.app.common.JdbcTemplate.getConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.sh.app.news.entity.News;

/**
 * Servlet implementation class ClobTestServlet
 */
@WebServlet("/read")
public class ClobReadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@Override
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json; charset=utf-8");

		Connection conn = getConnection();
		News news = null;
		try (PreparedStatement pstmt = conn.prepareStatement("select * from news where id = (select max(id) from news)")) {
			try (ResultSet rset = pstmt.executeQuery()) {
				if (rset.next()) {
					int id = rset.getInt("id");
					Clob _content = rset.getClob("content");
					String content = "";
					try (BufferedReader br = new BufferedReader(_content.getCharacterStream())) {						
						content = br.lines().collect(Collectors.joining());
					}
					news = new News(id, content);
				}
				
				new Gson().toJson(news, response.getWriter());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		close(conn);
	}

}
