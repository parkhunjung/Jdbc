package com.project2.teacher;

/**
 * 강사 - 담당 과정 객체 클래스
 * 
 * @author sist56
 *
 */
public class CourseDTO {
	private String courseID;
	private String coursename;
	private String coursedate;
	private String status;
	private String num;

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getCourseID() {
		return courseID;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCoursename() {
		return coursename;
	}

	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}

	public String getCoursedate() {
		return coursedate;
	}

	public void setCoursedate(String coursedate) {
		this.coursedate = coursedate;
	}

}
