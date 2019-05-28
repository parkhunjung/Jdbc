package com.project2.teacher;

/**
 * 강사 - 담당 과목별 배점 객체 클래스
 * 
 * @author sist56
 *
 */
public class PerScoreListDTO {

	private String seq;
	private String subjectname;
	private String notattendance;
	private String halfattendance;
	private String attendance;
	private String attscore;
	private String pilgiscore;
	private String silgiscore;

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getSubjectname() {
		return subjectname;
	}

	public void setSubjectname(String subjectname) {
		this.subjectname = subjectname;
	}

	public String getNotattendance() {
		return notattendance;
	}

	public void setNotattendance(String notattendance) {
		this.notattendance = notattendance;
	}

	public String getHalfattendance() {
		return halfattendance;
	}

	public void setHalfattendance(String halfattendance) {
		this.halfattendance = halfattendance;
	}

	public String getAttendance() {
		return attendance;
	}

	public void setAttendance(String attendance) {
		this.attendance = attendance;
	}

	public String getAttscore() {
		return attscore;
	}

	public void setAttscore(String attscore) {
		this.attscore = attscore;
	}

	public String getPilgiscore() {
		return pilgiscore;
	}

	public void setPilgiscore(String pilgiscore) {
		this.pilgiscore = pilgiscore;
	}

	public String getSilgiscore() {
		return silgiscore;
	}

	public void setSilgiscore(String silgiscore) {
		this.silgiscore = silgiscore;
	}

}
