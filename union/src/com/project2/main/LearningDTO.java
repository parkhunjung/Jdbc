package com.project2.main;

/**
 * 메인 - 수강중인 정보 객체 클래스
 * @author sist57
 *
 */
public class LearningDTO {
	private String seq;
	private String courseseq;
	private String studentseq;
	private String ing;
	private String ingdate;

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getCourseseq() {
		return courseseq;
	}

	public void setCourseseq(String courseseq) {
		this.courseseq = courseseq;
	}

	public String getStudentseq() {
		return studentseq;
	}

	public void setStudentseq(String studentseq) {
		this.studentseq = studentseq;
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

}
