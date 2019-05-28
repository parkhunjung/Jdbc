package com.project2.admin;

/**
 * 관리자 - 과목 객체 클래스
 * @author sist47
 *
 */
public class AdminCourseInfoDTO {

	private String courseSeq;
	private String courseTitle;
	private String begindate;
	private String enddate;
	private String roomName;
	private String status;
	private String etc;
	
	public String getCourseSeq() {
		return courseSeq;
	}
	public void setCourseSeq(String courseSeq) {
		this.courseSeq = courseSeq;
	}
	public String getCourseTitle() {
		return courseTitle;
	}
	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
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
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEtc() {
		return etc;
	}
	public void setEtc(String etc) {
		this.etc = etc;
	}
	
}
