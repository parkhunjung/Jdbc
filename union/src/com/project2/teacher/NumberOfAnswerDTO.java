package com.project2.teacher;

/**
 * 강사 - 담당 교육생별 맞힌 개수 객체 클래스
 * 
 * @author sist56
 *
 */
public class NumberOfAnswerDTO {
	private String seq;
	private String name;
	private String subjectname;
	private String pilgi;
	private String silgi;
	private String attendance;

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

	public String getSubjectname() {
		return subjectname;
	}

	public void setSubjectname(String subjectname) {
		this.subjectname = subjectname;
	}

	public String getPilgi() {
		return pilgi;
	}

	public void setPilgi(String pilgi) {
		this.pilgi = pilgi;
	}

	public String getSilgi() {
		return silgi;
	}

	public void setSilgi(String silgi) {
		this.silgi = silgi;
	}

	public String getAttendance() {
		return attendance;
	}

	public void setAttendance(String attendance) {
		this.attendance = attendance;
	}

}
