package com.sh.app.board.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sh.app.board.dto.BoardCreateDto;
import com.sh.app.board.entity.Attachment;
import com.sh.app.board.entity.BoardDetails;
import com.sh.app.board.service.BoardService;
import com.sh.app.commons.HelloSpringUtils;
import com.sh.app.member.entity.MemberDetails;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/board")
@Slf4j
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	// application.yml 변수 가져오기
	@Value("${spring.servlet.multipart.location}")
	private String multipartLocation;
	
	@GetMapping("/boardList.do")
	public void boardList(
			@RequestParam(defaultValue = "1") int page,
			Model model) {
		int limit = 10;
		Map<String, Object> params = Map.of(
				"page", page,
				"limit", limit
			);
		List<BoardDetails> boards = boardService.findAll(params);
		// log.debug("boards = {}", boards);
		model.addAttribute("boards", boards);
	}
	
	@GetMapping("/boardCreate.do")
	public void boardCreate() {
		
	}
	
	/**
	 * input[type=file]을 작성하지 않아도 빈 MultipartFile객체가 넘어온다.
	 * 
	 * @param _board
	 * @param bindingResult
	 * @param member
	 * @param upFiles
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@PostMapping("/boardCreate.do")
	public String boardCreate(
			@Valid BoardCreateDto _board, 
			BindingResult bindingResult,
			@AuthenticationPrincipal MemberDetails member,
			@RequestParam(value = "upFile", required = false) List<MultipartFile> upFiles)
					throws IllegalStateException, IOException {
		
		// 1. 파일저장
		List<Attachment> attachments = new ArrayList<>();
		for(MultipartFile upFile : upFiles) {			
			if(!upFile.isEmpty()) {
				String originalFilename = upFile.getOriginalFilename();
				String renamedFilename = HelloSpringUtils.getRenameFilename(originalFilename); // 20230807_142828888_123.jpg
				File destFile = new File(renamedFilename); // 부모디렉토리 생략가능. spring.servlet.multipart.location 값을 사용
				upFile.transferTo(destFile); // 실제파일 저장
				
				Attachment attach = 
						Attachment.builder()
						.originalFilename(originalFilename)
						.renamedFilename(renamedFilename)
						.build();
				attachments.add(attach);
			}
			
		}
		
		// 2. db저장
//		Board board = _board.toBoard();
//		board.setMemberId(member.getMemberId());
		BoardDetails board = BoardDetails.builder()
				.title(_board.getTitle())
				.content(_board.getContent())
				.memberId(member.getMemberId())
				.attachments(attachments)
				.build();
		
		log.debug("board = {}", board);
		int result = boardService.insertBoard(board);
		
		return "redirect:/board/boardDetail.do?id=" + board.getId() ;
	}
	
	@GetMapping("/boardDetail.do")
	public void boardDetail(@RequestParam int id, Model model) {
		BoardDetails board = boardService.findById(id);
		log.debug("board = {}", board);
		model.addAttribute("board", board);
	}
	
	
	/**
	 * Resource 
	 * - 스프링에서 여러 출처의 자원에 접근하기 위해 추상화레이어 PSA
	 * - UrlResource 웹상의 자원
	 * - ClassPathResource
	 * - FileSystemResource 서버컴퓨터상의 자원
	 * - ServletContextResource 웹루트하위의 자원 
	 * 
	 * - ResourceLoader객체에 전달한 location값에 따라 구현객체를 자동으로 얻어낼수 있다.
	 * 
	 * 
	 * 
	 * @param id
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	@GetMapping("/fileDownload.do")
	public ResponseEntity<Resource> fileDownload(@RequestParam int id) 
			throws UnsupportedEncodingException, FileNotFoundException {
		// 1. db조회
		Attachment attach = boardService.findAttachmentById(id);
		log.debug("attach = {}", attach);
		log.debug("multipartLocation = {}", multipartLocation);
		
		// 2. Resource객체 생성
		File downFile = new File(multipartLocation, attach.getRenamedFilename());
		
		if(!downFile.exists())
			throw new FileNotFoundException(attach.getRenamedFilename());
		
		String location = "file:" + downFile; // FileSystemResource 구현객체
		// "file:C:\\Workspaces\\spring_workspace\\hello-springboot\\src\\main\\webapp\\resources\\upload\\board\\20230807_150949398_036.jpg"
		Resource resource = resourceLoader.getResource(location);
		
		// 3. 응답헤더 작성
		String filename = URLEncoder.encode(attach.getOriginalFilename(), "utf-8");
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.headers(headers)
				.body(resource);
	}
	
//	@GetMapping("/fileDownload.do")
//	@ResponseBody // 리턴객체를 응답메세지에 직접 출력
	public Resource fileDownload(@RequestParam int id, HttpServletResponse response) 
			throws UnsupportedEncodingException, FileNotFoundException {
		// 1. db조회
		Attachment attach = boardService.findAttachmentById(id);
		log.debug("attach = {}", attach);
		log.debug("multipartLocation = {}", multipartLocation);
		
		// 2. Resource객체 생성
		File downFile = new File(multipartLocation, attach.getRenamedFilename());
		
		if(!downFile.exists())
			throw new FileNotFoundException(attach.getRenamedFilename());
		
		String location = "file:" + downFile; // FileSystemResource 구현객체
		// "file:C:\\Workspaces\\spring_workspace\\hello-springboot\\src\\main\\webapp\\resources\\upload\\board\\20230807_150949398_036.jpg"
		Resource resource = resourceLoader.getResource(location);
		
		// 3. 응답헤더 작성
		response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
		String filename = URLEncoder.encode(attach.getOriginalFilename(), "utf-8");
		response.addHeader(HttpHeaders.CONTENT_DISPOSITION , "attachment; filename=" + filename);
		
		return resource;
	}
	
}





