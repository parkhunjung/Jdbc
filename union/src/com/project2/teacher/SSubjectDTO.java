package com.project2.teacher;

/**
 * 강사 - 과목별 출석 통계값 객체 클래스
 * 
 * @author sist56
 *
 */
public class SSubjectDTO {

	private String inoutday;
	private String subject;

	public String getInoutday() {
		return inoutday;
	}

	public void setInoutday(String inoutday) {
		this.inoutday = inoutday;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

}
