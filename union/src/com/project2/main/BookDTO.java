package com.project2.main;

/**
 * 메인 - 교재 객체 클래스
 * @author sist57
 *
 */
public class BookDTO {

	private String seq;
	private String title;
	private String subjectseq;
	private String publisher;

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubjectseq() {
		return subjectseq;
	}

	public void setSubjectseq(String subjectseq) {
		this.subjectseq = subjectseq;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

}
