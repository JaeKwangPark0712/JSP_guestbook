<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="guestbook.model.Message" %>
<%@ page import="guestbook.service.MessageListView" %>
<%@ page import="guestbook.service.GetMessageListService" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String pageNumberStr = request.getParameter("page");
	int pageNumber = 1;
	if(pageNumberStr != null) {
		pageNumber = Integer.parseInt(pageNumberStr);
	}
	
	GetMessageListService messageListService = GetMessageListService.getInstance();
	MessageListView viewData = messageListService.getMessageList(pageNumber);
	
	int totalPage = viewData.getPageTotalCount();
	int pagePerList = 5;
	int firstPage = ((pageNumber - 1) / pagePerList) * pagePerList + 1;
	int lastPage = firstPage + pagePerList - 1;
	if(lastPage > totalPage) {
		lastPage = totalPage;
	}
%>
<c:set var="viewData" value="<%= viewData %>" />

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>방명록 메세지 목록</title>
	</head>
	<body>
		<!-- writeMessage 창으로 파라메터의 내용 보내기 -->
		<form action="writeMessage.jsp" method="post"> 
			이름: <input type="text" name="guestName"> <br />
			암호: <input type="password" name="password"> <br />
			메세지: <br /> <textarea name="message" rows="3" cols="30"></textarea> <br />
			<input type="submit" value="메세지 남기기">
		</form>
		<hr>
		<c:if test="${viewData.isEmpty() }">
			등록된 메세지가 없습니다.
		</c:if>
		
		<c:if test="${!viewData.isEmpty() }">
			<table border="1">
				<c:forEach var="message" items="${viewData.messageList }">
				<tr>
					<td>
						메세지 번호: ${message.id } <br>
						손님 이름: ${message.guestName } <br>
						메세지: ${message.message } <br>
						<a href="confirmDeletion.jsp?messageId=${message.id }">[삭제하기]</a>
					</td>
				</tr>
				</c:forEach>
			</table>
			<%
				if(firstPage > pagePerList) {
			%>		
					<a href="list.jsp?page=<%= firstPage -  pagePerList%>">[이전]</a>	
			<%		
				}
				for(int i = firstPage; i <= lastPage; i++) {
			%>		
					<a href="list.jsp?page=<%= i%>">
						[<%= i %>]
					</a>	
			<%	
				}
				if(lastPage < totalPage) {
			%>		
					<a href="list.jsp?page=<%= firstPage +  pagePerList%>">[다음]</a>	
			<%		
				}
			%>
		</c:if>
	</body>
</html>