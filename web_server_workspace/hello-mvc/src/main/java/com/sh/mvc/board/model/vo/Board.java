package com.sh.mvc.board.model.vo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Board extends BoardEntity {
	private int attachCnt;
	private List<Attachment> attachments = new ArrayList<>();
	private int commentCnt;

	public int getAttachCnt() {
		return attachCnt;
	}

	public void setAttachCnt(int attachCnt) {
		this.attachCnt = attachCnt;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public int getCommentCnt() {
		return commentCnt;
	}

	public void setCommentCnt(int commentCnt) {
		this.commentCnt = commentCnt;
	}

	public void addAttachment(Attachment attach) {
		if(attach != null)
			this.attachments.add(attach);
	}

	@Override
	public String toString() {
		return "Board [attachCnt=" + attachCnt + ", attachments=" + attachments + ", commentCnt=" + commentCnt
				+ ", toString()=" + super.toString() + "]";
	}

	

	
	
	
	
}
