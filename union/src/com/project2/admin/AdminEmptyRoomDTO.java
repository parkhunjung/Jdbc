package com.project2.admin;

/**
 * 관리자 - 빈 강의실 객체 클래스
 * @author sist47
 *
 */
public class AdminEmptyRoomDTO {
	private String seq;
	private String name;
	private int limit;

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

}
