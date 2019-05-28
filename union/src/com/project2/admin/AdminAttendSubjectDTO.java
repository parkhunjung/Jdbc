package com.project2.admin;
/**
 * 관리자 - 과목별 출결 관련 객체 클래스
 * @author sist47
 *
 */
public class AdminAttendSubjectDTO {
	
	private String name;
	private String attendStatus;
	private String attendTime;
	private String courseSeq;
	private String courseName;
	private String coursePeriod;
	
	
	public String getCourseSeq() {
		return courseSeq;
	}
	public void setCourseSeq(String courseSeq) {
		this.courseSeq = courseSeq;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAttendStatus() {
		return attendStatus;
	}
	public void setAttendStatus(String attendStatus) {
		this.attendStatus = attendStatus;
	}
	public String getAttendTime() {
		return attendTime;
	}
	public void setAttendTime(String attendTime) {
		this.attendTime = attendTime;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCoursePeriod() {
		return coursePeriod;
	}
	public void setCoursePeriod(String coursePeriod) {
		this.coursePeriod = coursePeriod;
	}
	
	
	
}
