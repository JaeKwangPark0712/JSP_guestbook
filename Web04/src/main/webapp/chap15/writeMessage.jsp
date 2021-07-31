<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="guestbook.model.Message" %>
<%@ page import="guestbook.service.WriteMessageService" %>
<%
	/* 파라메터로 불러오는 값 인코딩! */
	request.setCharacterEncoding("UTF-8");
%>

<!-- 자바빈 객체 불러오기! -->
<jsp:useBean id="message" class="guestbook.model.Message">
	<!-- 자바빈 객체의 모든 프로퍼티 가져오기 -->
	<jsp:setProperty name="message" property="*" />
</jsp:useBean>

<%
	/* WriteMessageService의 싱글톤 객체 가져와서 메세지 작성(MessageDao의 쿼리문 실행) */
	WriteMessageService writeService = WriteMessageService.getInstance();
	writeService.write(message);
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>방명록 메세지 남김</title>
	</head>
	<body>
		방명록에 메세지를 남겼습니다. <br /> 
		<a href="list.jsp">[목록 보기]</a>
	</body>
</html>