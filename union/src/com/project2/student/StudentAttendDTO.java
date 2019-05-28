package com.project2.student;

/***
 * 교육생 출석기록 객체 클래스
 * @author 유병현
 *
 */
public class StudentAttendDTO {
	private String courseTitle;
	private String intime;
	private String outime;
	private String status;
	
	public String getCourseTitle() {
		return courseTitle;
	}
	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}
	public String getIntime() {
		return intime;
	}
	public void setIntime(String intime) {
		this.intime = intime;
	}
	public String getOutime() {
		return outime;
	}
	public void setOutime(String outime) {
		this.outime = outime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
