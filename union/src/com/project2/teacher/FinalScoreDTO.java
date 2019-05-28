package com.project2.teacher;

/**
 * 강사 - 담당 교육생별 최종 성적 객체 클래스
 * 
 * @author sist56
 *
 */
public class FinalScoreDTO {

	private String seq;
	private String name;
	private String subjectname;
	private String attendancescore;
	private String pilgi;
	private String silgi;
	private String total;

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

	public String getAttendancescore() {
		return attendancescore;
	}

	public void setAttendancescore(String attendancescore) {
		this.attendancescore = attendancescore;
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

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

}
