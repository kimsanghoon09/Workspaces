package com.sh.app.board.entity;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Board {
	private int id;
	private String title;
	private String memberId;
	private String content;
	private LocalDateTime createdAt;
}
