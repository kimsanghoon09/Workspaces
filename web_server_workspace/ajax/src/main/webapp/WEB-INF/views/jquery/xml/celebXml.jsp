<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.sh.ajax.celeb.model.vo.Celeb"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/xml; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	List<Celeb> celebs = (List<Celeb>) request.getAttribute("celebs");
%>
<celebs>
	<%	
		if(celebs != null && !celebs.isEmpty()) { 
			for(Celeb celeb : celebs) {
	%>
		<celeb>
			<no><%= celeb.getNo() %></no>
			<name><%= celeb.getName() %></name>
			<profile><%= celeb.getProfile() %></profile>
			<celebType><%= celeb.getCelebType() %></celebType>
		</celeb>
	
	<%	
			}
		} 
	%>
</celebs>