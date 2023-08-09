package com.sh.app.board.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sh.app.board.entity.Attachment;
import com.sh.app.board.entity.Board;
import com.sh.app.board.entity.BoardDetails;
import com.sh.app.board.repository.BoardRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardRepository boardRepository;
	
	@Override
	public List<BoardDetails> findAll(Map<String, Object> params) {
		int limit = (int) params.get("limit");
		int page = (int) params.get("page");
		int offset = (page - 1) * limit; // 한 페이지당 범위를 나눌때 적용되는 공식 
		RowBounds rowBounds = new RowBounds(offset, limit);
		return boardRepository.findAll(rowBounds);
	}
	
	@Transactional(rollbackFor = Exception.class) // 어떤 오류가 발생하던지 무조건 롤백
	@Override
	public int insertBoard(Board board) {
		// board 저장
		int result = 0;
		result = boardRepository.insertBoard(board);
		log.debug("board = {}", board);
		// attachment 저장
		List<Attachment> attachments = ((BoardDetails) board).getAttachments(); // 자식에 있는 메소드이기때문에 BoardDetails 다운캐스팅
		if(attachments != null && !attachments.isEmpty()) {
			for(Attachment attach : attachments) {
				attach.setBoardId(board.getId());
				result = boardRepository.insertAttachment(attach);
			}
		}
		return result;
	}

	@Override
	public BoardDetails findById(int id) {
		return boardRepository.findById(id);
	}
	
	@Override
	public Attachment findAttachmentById(int id) {
		return boardRepository.findAttachmentById(id);
	}
}
