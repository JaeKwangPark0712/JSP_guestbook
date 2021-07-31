package guestbook.service;

import java.sql.Connection;
import java.sql.SQLException;

import guestbook.dao.MessageDao;
import guestbook.model.Message;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class WriteMessageService {
	//멤버 필드 //싱글톤 객체 생성
	private static WriteMessageService instance = new WriteMessageService();
	
	//생성자 : 외부에서 겍체 생성을 방지하기 위해 private 접근제한 설정
	private WriteMessageService() { }
	
	//메서드
	//외부에서 싱글톤 객체에 접근할 수 있는 getInstance() 메서드 생성
	public static WriteMessageService getInstance() {
		return instance;
	}
	
	//메세지 작성 메서드 생성 -> Dao객체에서 쿼리 실행 메서드 빌려오기!
	public void write(Message message) {
		Connection conn  = null;
		try {
			conn = ConnectionProvider.getConnection(); //ConnectionProvider 클래스(커넥션 풀)에서 Connection 가져옴
			MessageDao messageDao = MessageDao.getInstance(); //MessageDao 싱글톤 객체를 가져옴
			messageDao.insert(conn, message); //싱글톤 객체에서 insert() 메서드 사용 -> insert 쿼리문 실행!
		} catch (SQLException e) {
			throw new ServiceException("메세지 등록 실패" + e.getMessage(), e); // 올바른 값이 들어오지 않을 경우 예외처리
		} finally {
			JdbcUtil.close(conn); // Connection 객체 사용 후 닫아주기!
		}
	}
}
