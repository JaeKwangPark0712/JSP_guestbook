package guestbook.model;

public class Message {// 자바빈 클래스 작성
	//멤버 필드
	private int id;
	private String guestName;
	private String password;
	private String message;
	
	//메서드(Getter & Setter)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGuestName() {
		return guestName;
	}
	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	//password 관련 클래스 작성
	public boolean hasPassword() {
		return password != null && !password.isEmpty();
	}
	public boolean matchPassword(String password) {
		return password != null && this.password.equals(password);
	}
	
}
