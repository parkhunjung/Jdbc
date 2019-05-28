package com.project2.main;

/**
 * 메인 - 과목 객체 클래스
 * @author sist57
 *
 */
public class SubjectDTO {

	private String seq;
	private String subject_name;
	private String delete_status;

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getSubject_name() {
		return subject_name;
	}

	public void setSubject_name(String subject_name) {
		this.subject_name = subject_name;
	}

	public String getDelete_status() {
		return delete_status;
	}

	public void setDelete_status(String delete_status) {
		this.delete_status = delete_status;
	}

}
