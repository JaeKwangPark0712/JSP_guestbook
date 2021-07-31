package guestbook.service;

import java.util.List;

import guestbook.model.Message;

public class MessageListView {
	
	//멤버 필드
	private int messageTotalCount;
	private int currentPageNumber;
	private List<Message> messageList;
	private int pageTotalCount;
	private int messageCountPerPage;
	private int firstRow;
	private int endRow;
	
	//생성자
	public MessageListView(int messageTotalCount, int currentPageNumber, List<Message> messageList,
			int messageCountPerPage, int firstRow, int endRow) {
		super();
		this.messageTotalCount = messageTotalCount;
		this.currentPageNumber = currentPageNumber;
		this.messageList = messageList;
		this.messageCountPerPage = messageCountPerPage;
		this.firstRow = firstRow;
		this.endRow = endRow;
		
		calculatePageTotalCount();//생성자 안에서 총 페이지 수를 계산하는 메서드 실행
	}
	//메서드 : 총 페이지 수를 계산
	private void calculatePageTotalCount() {
		if(messageTotalCount == 0) { // 메세지가 0개이면 페이지도 0페이지!
			pageTotalCount = 0;
		} else {
			pageTotalCount = messageTotalCount / messageCountPerPage; //전체 메세지 수를 페이지 당 메세지 수로 나눔
			if(messageTotalCount % messageCountPerPage > 0) { // 나누어 떨어지지 않을 경우 페이지 수 1 추가!
				pageTotalCount++;
			}
		}
	}
	//Getter 메서드 생성!
	public int getMessageTotalCount() {
		return messageTotalCount;
	}
	public int getCurrentPageNumber() {
		return currentPageNumber;
	}
	public List<Message> getMessageList() {
		return messageList;
	}
	public int getPageTotalCount() {
		return pageTotalCount;
	}
	public int getMessageCountPerPage() {
		return messageCountPerPage;
	}
	public int getFirstRow() {
		return firstRow;
	}
	public int getEndRow() {
		return endRow;
	}
	
	public boolean isEmpty() {
		return messageTotalCount == 0;
	}
	
}
