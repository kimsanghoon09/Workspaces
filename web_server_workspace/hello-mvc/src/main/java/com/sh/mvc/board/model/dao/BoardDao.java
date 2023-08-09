package com.sh.mvc.board.model.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.sh.mvc.board.model.exception.BoardException;
import com.sh.mvc.board.model.vo.Attachment;
import com.sh.mvc.board.model.vo.Board;
import com.sh.mvc.board.model.vo.BoardComment;

public class BoardDao {
	private Properties prop = new Properties();
	
	public BoardDao() {
		String filename = 
			BoardDao.class.getResource("/sql/board/board-query.properties").getPath();
		try {
			prop.load(new FileReader(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	
	public List<Board> findAll(Connection conn, int start, int end) {
		List<Board> boards = new ArrayList<>();
		String sql = prop.getProperty("findAll");
		try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			try(ResultSet rset = pstmt.executeQuery()) {
				while(rset.next()) {
					Board board = handleBoardResultSet(rset);
					board.setAttachCnt(rset.getInt("attach_cnt"));
					board.setCommentCnt(rset.getInt("comment_cnt"));
					boards.add(board);
				}
			}
		} catch (SQLException e) {
			throw new BoardException(e);
		}
		return boards;
	}


	private Board handleBoardResultSet(ResultSet rset) throws SQLException {
		Board board = new Board();
		board.setNo(rset.getInt("no"));
		board.setTitle(rset.getString("title"));
		board.setWriter(rset.getString("writer"));
		board.setContent(rset.getString("content"));
		board.setReadCount(rset.getInt("read_count"));
		board.setRegDate(rset.getDate("reg_date"));
		return board;
	}


	public int getTotalContent(Connection conn) {
		int totalContent = 0;
		String sql = prop.getProperty("getTotalContent"); // select count(*) from board
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			try (ResultSet rset = pstmt.executeQuery()) {
				while(rset.next())
					totalContent = rset.getInt(1);
			}
		} catch (SQLException e) {
			throw new BoardException(e);
		}
		return totalContent;
	}


	public int insertBoard(Connection conn, Board board) {
		int result = 0;
		String sql = prop.getProperty("insertBoard");
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getWriter());
			pstmt.setString(3, board.getContent());
			
			result = pstmt.executeUpdate(); 
		} catch (SQLException e) {
			throw new BoardException(e);
		}
		
		return result;
	}


	public int getLastBoardNo(Connection conn) {
		int boardNo = 0;
		String sql = prop.getProperty("getLastBoardNo");
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			try (ResultSet rset = pstmt.executeQuery()) {
				if(rset.next()) {
					boardNo = rset.getInt(1);
				}
			}
		} catch (SQLException e) {
			throw new BoardException(e);
		}
		
		return boardNo;
	}


	public int insertAttachment(Connection conn, Attachment attach) {
		int result = 0;
		String sql = prop.getProperty("insertAttachment");
		// insert into attachment(no, board_no, original_filename, renamed_filename) values(seq_attachment_no.nextval, ?, ?, ?)
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, attach.getBoardNo());
			pstmt.setString(2, attach.getOriginalFilename());
			pstmt.setString(3, attach.getRenamedFilename());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new BoardException(e);
		}
		return result;
	}


	public Board findById(Connection conn, int no) {
		Board board = null;
		String sql = prop.getProperty("findById");
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, no);
			try (ResultSet rset = pstmt.executeQuery()) {
				if (rset.next())
					board = handleBoardResultSet(rset);
			}
		} catch (SQLException e) {
			throw new BoardException(e);
		}
		return board;
	}


	public List<Attachment> findAttachmentByBoardNo(Connection conn, int boardNo) {
		List<Attachment> attachments = new ArrayList<>();
		String sql = prop.getProperty("findAttachmentByBoardNo");
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, boardNo);
			try (ResultSet rset = pstmt.executeQuery()) {
				while (rset.next()) {
					Attachment attach = handleAttachmentResultSet(rset);
					attachments.add(attach);
				}
			}
		} catch (Exception e) {
			throw new BoardException(e);
		}
		return attachments;
	}


	private Attachment handleAttachmentResultSet(ResultSet rset) throws SQLException {
		Attachment attach = new Attachment();
		attach.setNo(rset.getInt("no"));
		attach.setBoardNo(rset.getInt("board_no"));
		attach.setOriginalFilename(rset.getString("original_filename"));
		attach.setRenamedFilename(rset.getString("renamed_filename"));
		attach.setRegDate(rset.getDate("reg_date"));
		return attach;
	}


	public int updateReadCount(Connection conn, int no) {
		int result = 0;
		String sql = prop.getProperty("updateReadCount");
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, no);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new BoardException(e);
		}
		return result;
	}


	public Attachment findAttachmentById(Connection conn, int no) {
		Attachment attach = null;
		String sql = prop.getProperty("findAttachmentById");
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, no);
			try (ResultSet rset = pstmt.executeQuery()) {
				while (rset.next()) 
					attach = handleAttachmentResultSet(rset);
			}
			
		} catch (SQLException e) {
			throw new BoardException(e);
		}
		return attach;
	}


	public int updateBoard(Connection conn, Board b) {
		int result = 0;
		String query = prop.getProperty("updateBoard");

		try (PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, b.getTitle());
			pstmt.setString(2, b.getContent());
			pstmt.setInt(3, b.getNo());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new BoardException(e);
		} 
		return result;
	}
	
	public int deleteBoard(Connection conn, int no) {
		int result = 0;
		String sql = prop.getProperty("deleteBoard");

		try (PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, no);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new BoardException(e);
		} 
		return result;
	}


	public int deleteAttachment(Connection conn, int no) {
		int result = 0;
		String sql = prop.getProperty("deleteAttachment");
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, no);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new BoardException(e);
		}
		return result;
	}


	public int insertBoardComment(Connection conn, BoardComment boardComment) {
		int result = 0;
		String sql = prop.getProperty("insertBoardComment");
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, boardComment.getCommentLevel());
			pstmt.setString(2, boardComment.getWriter());
			pstmt.setString(3, boardComment.getContent());
			pstmt.setInt(4, boardComment.getBoardNo());
			pstmt.setObject(5, boardComment.getCommentRef() != 0 ? boardComment.getCommentRef() : null);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new BoardException(e);
		}
		return result;
	}


	public List<BoardComment> findBoardCommentByBoardNo(Connection conn, int boardNo) {
		List<BoardComment> boardComments = new ArrayList<>();
		String sql = prop.getProperty("findBoardCommentByBoardNo");
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, boardNo);
			try (ResultSet rset = pstmt.executeQuery()) {
				while(rset.next()) {
					BoardComment boardComment = handleBoardCommentResultSet(rset);
					boardComments.add(boardComment);
				}
			}
		} catch (SQLException e) {
			throw new BoardException(e);
		}
		
		return boardComments;
	}


	private BoardComment handleBoardCommentResultSet(ResultSet rset) throws SQLException {
		int no = rset.getInt("no");
		int commentLevel = rset.getInt("comment_level");
		String writer = rset.getString("writer");
		String content = rset.getString("content");
		int boardNo = rset.getInt("board_no");
		int commentRef = rset.getInt("comment_ref"); // null인 경우, 0이 반환
		Date regDate = rset.getDate("reg_date");
		return new BoardComment(no, commentLevel, writer, content, boardNo, commentRef, regDate);
	}


	public int deleteBoardComment(Connection conn, int no) {
		int result = 0;
		String sql = prop.getProperty("deleteBoardComment");
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, no);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new BoardException(e);
		}
		return result;
	}



}






