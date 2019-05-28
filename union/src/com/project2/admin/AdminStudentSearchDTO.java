package com.project2.admin;

/**
 * 관리자 - 교육생 조회 관련 객체 클래스
 * @author sist47
 *
 */
public class AdminStudentSearchDTO {
	
	private String name;
	private String ssn;
	private String tel;
	private String address;
	private String learningCourse;
	private String coursePeriod;
	private String roomName;
	private String ing;
	private String ingdate;
	private String status;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLearningCourse() {
		return learningCourse;
	}
	public void setLearningCourse(String learningCourse) {
		this.learningCourse = learningCourse;
	}
	public String getCoursePeriod() {
		return coursePeriod;
	}
	public void setCoursePeriod(String coursePeriod) {
		this.coursePeriod = coursePeriod;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getIng() {
		return ing;
	}
	public void setIng(String ing) {
		this.ing = ing;
	}
	public String getIngdate() {
		return ingdate;
	}
	public void setIngdate(String ingdate) {
		this.ingdate = ingdate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
