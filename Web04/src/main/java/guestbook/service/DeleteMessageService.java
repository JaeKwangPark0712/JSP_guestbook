package guestbook.service;

import java.sql.Connection;
import java.sql.SQLException;

import guestbook.dao.MessageDao;
import guestbook.model.Message;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class DeleteMessageService {
	//싱글톤 객체 생성
	private static DeleteMessageService instance = new DeleteMessageService();
	//생성자 - 외부에서 객체를 생성하지 못하도록 private 접근 제한 설정
	private DeleteMessageService() {}
	//메서드 - 싱글톤 객체에 접근할 수 있는 getInstance() 메서드
	public static DeleteMessageService getInstance() {
		return instance;
	}
	
	public void deleteMessage(int messageId, String password) {//게시글 번호와 비밀번호를 매개변수로 받아 글을 삭제하는 메서드
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);//트랜지션 처리를 위해 오토커밋 비활성화
			
			MessageDao messageDao = MessageDao.getInstance();
			Message message = messageDao.select(conn, messageId);//삭제 할 방명록 선택
			if(message == null) {//삭제 할 메세지가 없을 경우 예외 발생
				throw new MessageNotFoundException("메세지 없음");
			}
			if(!message.matchPassword(password)) {//비밀번호가 잘못됐을 경우 예외 발생
				throw new InvalidPasswordException("잘못된 비밀번호");
			}
			messageDao.delete(conn, messageId);//선택된 방명록 삭제
			conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new ServiceException("삭제 실패: " + e.getMessage(), e);
		} catch (InvalidPasswordException | MessageNotFoundException e) {
			JdbcUtil.rollback(conn);
			throw e;
		} finally {
			JdbcUtil.close(conn);
		}
		
	}
}
