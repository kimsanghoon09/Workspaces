package com.sh.app.board.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.session.RowBounds;

import com.sh.app.board.entity.Attachment;
import com.sh.app.board.entity.Board;
import com.sh.app.board.entity.BoardDetails;

@Mapper
public interface BoardRepository {

	List<BoardDetails> findAll(RowBounds rowBounds);

	@SelectKey( // 게시글이 db에 들어가는 순간 시퀀스가 입력 그 시퀀스번호 = board의 id 게시글 입력하면서 넣었던 파일의 정보는
			before = false, // board테이블이 아닌 attachment테이블에 들어감 attachment 테이블은 board테이블의 id참조(몇번째 게시글에 어떤 파일이 들어갔는지)
			keyProperty = "id", // 게시글을 입력하면 board테이블과 attachment 테이블에 동시에 들어가야하기때문에 board에 입력이 되어야 시퀀스(board의 id)를 알수있음.
			resultType = int.class,
			statement = "select seq_board_id.currval from dual") // int.class 기본형에도 클래스 객체가 있음 
	@Insert("insert into board values(seq_board_id.nextval, #{title}, #{memberId}, #{content}, default)")
	int insertBoard(Board board);

	@Insert("insert into attachment values(seq_attachment_id.nextval, #{boardId}, #{originalFilename}, #{renamedFilename}, default)")
			int insertAttachment(Attachment attach);

	BoardDetails findById(int id);

	@Select("select * from attachment where id = #{id}")
	Attachment findAttachmentById(int id);

}
