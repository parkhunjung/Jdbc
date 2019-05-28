package com.project2.teacher;

/**
 * 강사 - 담당 과목 리스트 객체 클래스
 * 
 * @author sist56
 *
 */
public class SubjectListDTO {

	private String subjectseq;
	private String subjectname;
	private String subjectperiod;
	private String description;

	public String getSubjectseq() {
		return subjectseq;
	}

	public void setSubjectseq(String subjectseq) {
		this.subjectseq = subjectseq;
	}

	public String getSubjectname() {
		return subjectname;
	}

	public void setSubjectname(String subjectname) {
		this.subjectname = subjectname;
	}

	public String getSubjectperiod() {
		return subjectperiod;
	}

	public void setSubjectperiod(String subjectperiod) {
		this.subjectperiod = subjectperiod;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
