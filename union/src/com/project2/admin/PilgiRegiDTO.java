package com.project2.admin;

/**
 * 관리자 - 필기 등록 여부 관련 객체 클래스
 * @author sist47
 *
 */
public class PilgiRegiDTO {
	
	private String subjectName;
	private String examRegistry;
	private String gradeRegistry;
	
	
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getExamRegistry() {
		return examRegistry;
	}
	public void setExamRegistry(String examRegistry) {
		this.examRegistry = examRegistry;
	}
	public String getGradeRegistry() {
		return gradeRegistry;
	}
	public void setGradeRegistry(String gradeRegistry) {
		this.gradeRegistry = gradeRegistry;
	}
	
	
	
}
