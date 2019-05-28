package com.project2.teacher;

/**
 * 강사 - 담당과목의 필기 답안 객체 클래스
 * @author sist56
 *
 */
public class AnswerPilgiJohaeDTO {

	private String seq;
	private String question;
	private String answer;

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuesiton(String quesiton) {
		this.question = quesiton;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

}
