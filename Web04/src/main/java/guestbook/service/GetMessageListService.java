package guestbook.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import guestbook.dao.MessageDao;
import guestbook.model.Message;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class GetMessageListService {
	//멤버 필드
	private static GetMessageListService instance = new GetMessageListService();
	private static final int MESSAGE_COUNT_PER_PAGE = 3;
	
	//생성자
	private GetMessageListService() {}
	
	//메서드
	public static GetMessageListService getInstance() {//싱글톤 객체를 얻어오는 getInstance() 메서드
		return instance;
	}
	
	public MessageListView getMessageList(int pageNumber) {//페이지 번호를 매개변수로 받아 메세지 리스트를 얻어오는 메서드
		Connection conn = null;
		int currentPageNumber = pageNumber;
		try {
			conn = ConnectionProvider.getConnection();
			MessageDao messageDao = MessageDao.getInstance();
			
			int messageTotalCount = messageDao.selectCount(conn);
			
			List<Message> messageList = null;
			int firstRow = 0;
			int endRow = 0;
			if(messageTotalCount > 0) {//페이지의 첫 번째 게시글과 마지막 게시글 번호를 계산
				firstRow = (pageNumber - 1) * MESSAGE_COUNT_PER_PAGE + 1;
				endRow = firstRow + MESSAGE_COUNT_PER_PAGE - 1;
				messageList = messageDao.selectList(conn, firstRow, endRow);//messageDao 객체를 이용해서 메세지 리스트를 얻어옴
			} else {//메세지가 하나도 없을 경우 비어있는 리스트를 반환함
				currentPageNumber = 0;
				messageList = Collections.emptyList();
			}
			return new MessageListView(messageTotalCount, currentPageNumber, messageList, MESSAGE_COUNT_PER_PAGE, firstRow, endRow);
		} catch (SQLException e) {//메세지 리스트를 구하지 못 했을 때 예외처리
			throw new ServiceException("목록 구하기 실패: " + e.getMessage(), e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
	
	
}
