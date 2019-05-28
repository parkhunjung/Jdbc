package com.project2.main;

/**
 * 메인 - 과정 객체 클래스
 * @author sist57
 *
 */
public class CourseDTO {
	private String seq;
	private String coursetitleseq;
	private String roomseq;
	private String status;
	private String begindate;
	private String enddate;
	private String etc;

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getCoursetitleseq() {
		return coursetitleseq;
	}

	public void setCoursetitleseq(String coursetitleseq) {
		this.coursetitleseq = coursetitleseq;
	}

	public String getRoomseq() {
		return roomseq;
	}

	public void setRoomseq(String roomseq) {
		this.roomseq = roomseq;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBegindate() {
		return begindate;
	}

	public void setBegindate(String begindate) {
		this.begindate = begindate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getEtc() {
		return etc;
	}

	public void setEtc(String etc) {
		this.etc = etc;
	}

}
