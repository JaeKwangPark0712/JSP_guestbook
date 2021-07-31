package guestbook.service;

public class ServiceException extends RuntimeException {
	
	//생성자 오버로딩!
	public ServiceException(String message) {
		super(message);
	}
	
	public ServiceException(String message, Exception cause) {
		super(message, cause);
	}
}
