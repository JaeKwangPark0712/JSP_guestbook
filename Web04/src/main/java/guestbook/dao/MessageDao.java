package guestbook.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import guestbook.model.Message;
import jdbc.JdbcUtil;

public class MessageDao {//DAO 클래스 작성
	//멤버 필드 : 싱글톤 객체 생성
	private static MessageDao messageDao = new MessageDao(); //private 로 접근제한! -> 외부에서 마음대로 접근할 수 없음
	
	//생성자 : private 로 접근제한! -> 외부에서 객체 생성 불가!
	private MessageDao() {}
	
	//메서드
	public static MessageDao getInstance() {// 싱글톤 객체에 접근 가능한 getInstance() 메서드
		return messageDao;
	}
	
	//쿼리 실행 메서드
	//insert Query
	public int insert(Connection conn, Message message) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("insert into guestbook_message values(no_seq.nextval, ?, ?, ?)");
			pstmt.setString(1, message.getGuestName());
			pstmt.setString(2, message.getPassword());
			pstmt.setString(3, message.getMessage());
			return pstmt.executeUpdate();
		} finally {
			JdbcUtil.close(pstmt);
		}
		
	}
	//select count(*)
	public int selectCount(Connection conn) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT count(*) from GUESTBOOK_MESSAGE");
			rs.next();
			return rs.getInt(1);
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}

	}
	
	//ResultSet에서 메세지 객체를 가져오는 메서드
	private Message makeMessageFromResult(ResultSet rs) throws SQLException {
		Message message = new Message();
		message.setId(rs.getInt("message_id"));
		message.setGuestName(rs.getString("guest_name"));
		message.setPassword(rs.getString("password"));
		message.setMessage(rs.getString("message"));
		return message;
	}
	//페이지 안의 message 전체를 리스트로 가져오는 쿼리문을 실행하는 메서드
	public List<Message> selectList(Connection conn, int firstRow, int endRow) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from "
					+ "(select ROWNUM rnum,MESSAGE_ID,guest_name,password,message from "
					+ "(SELECT * from GUESTBOOK_MESSAGE ORDER by message_id desc) where rownum<=?) where rnum>=?");
			pstmt.setInt(1, endRow);
			pstmt.setInt(2, firstRow);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {//ResultSet에 message 객체가 남아있을 경우
				List<Message> messageList = new ArrayList<Message>();
				do {
					messageList.add(makeMessageFromResult(rs));//리스트에 message 객체 추가
				} while(rs.next());//ResultSet에 남아있는 message 객체가 없을 때 까지 반복
				return messageList;//messageList 반환
			} else {//ResultSet에 message 객체가 없을 경우
				return Collections.emptyList();//비어있는 리스트 반환
			}
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
	}

	public Message select(Connection conn, int messageId) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from guestbook_message where message_id=?");
			pstmt.setInt(1, messageId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return makeMessageFromResult(rs);
			} else {
				return null;
			}
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
	}

	public int delete(Connection conn, int messageId) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("delete from guestbook_message where message_id=?");
			pstmt.setInt(1, messageId);
			return pstmt.executeUpdate();
		} finally {
			JdbcUtil.close(pstmt);
		}
		
		
	}
	
}
